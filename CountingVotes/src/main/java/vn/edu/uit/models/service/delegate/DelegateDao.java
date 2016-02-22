package vn.edu.uit.models.service.delegate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.TextSegement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.edu.uit.extra.DataConfig;
import vn.edu.uit.extra.SupportMethods;
import vn.edu.uit.models.Delegate;
import vn.edu.uit.models.DelegateType;
import vn.edu.uit.models.Unit;
import vn.edu.uit.models.common.AbstractDao;
import vn.edu.uit.models.service.delegate.support.AnotationColumnDetecter;
import vn.edu.uit.models.service.delegate.support.AnotationDetecter;
import vn.edu.uit.models.service.delegate.support.AnotationField;
import vn.edu.uit.models.service.delegate.support.AnotationParagraphDetecter;
import vn.edu.uit.models.service.delegate.support.EnumDelegateField;
import vn.edu.uit.models.service.delegate_type.IDelegateTypeDao;
import vn.edu.uit.models.service.unit.IUnitDao;
import vn.edu.uit.models.service.unit.UnitDao;

@Repository
public class DelegateDao extends AbstractDao implements IDelegateDao {

	private static final Logger logger = LoggerFactory.getLogger(DelegateDao.class);
	
	@Autowired
	private IUnitDao unitDao;

	@Autowired
	private IDelegateTypeDao delegateTypeDao;

	@Override
	public boolean persist(Delegate delegate) {
		return this.saveOrUpdate(delegate);
	}

	@Override
	public boolean delete(Delegate delegate) {
		try {
			delegate.setEnabled(false);
			return this.persist(delegate);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	@Override
	public Delegate fetch(long id) {
		return (Delegate) getSession().get(Delegate.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Delegate> fetch(int min, int max) {
		Criteria crit = getSession().createCriteria(Delegate.class);
		crit.add(Restrictions.eq("isEnabled", true));

		if (min >= 0 && max > 0 && max > min) {
			crit.setFirstResult(min);
			crit.setMaxResults(max);
		}

		crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return crit.list();
	}

	@Override
	public List<Delegate> getByDocument(InputStream is) {

		List<Delegate> delegates = new ArrayList<Delegate>(0);
		try {

			XWPFDocument document = new XWPFDocument(is);

			List<XWPFTable> tables = document.getTables();
			XWPFTable table = null;
			int maxRow = 1;
			for (XWPFTable t : tables) {
				int rowCount = t.getNumberOfRows();
				if (rowCount > maxRow) {
					maxRow = rowCount;
					table = t;
				}
			}

			if (table == null || table.getNumberOfRows() < 1) {
				logger.info("Table is Null");
				return delegates;
			}

			// Detect title format
			XWPFTableRow title = table.getRow(0);
			XWPFTableRow title2 = null;
			int firstData = 1;
			if (AnotationDetecter.isMerged(table)) {
				title2 = table.getRow(1);
				firstData = 2;
			}

			Map<Integer, AnotationColumnDetecter> format = AnotationDetecter.getFormat(title, title2);
			List<XWPFTableRow> rows = table.getRows();

			for (int i = firstData, Ordinal = 1; i < rows.size(); i++, Ordinal++) {
				XWPFTableRow row = rows.get(i);
				Delegate delegate = new Delegate();
				delegate.setOrdinal(String.valueOf(Ordinal));
				fillDataDelegate(row, format, delegate);
				delegates.add(delegate);
			}

			return delegates;
		} catch (FileNotFoundException e) {
			logger.error("Table is Null");
			e.printStackTrace();
			return delegates;
		} catch (IOException e) {
			logger.error("Table is Null");
			e.printStackTrace();
			return delegates;
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public long getNumOfDelegate(long congressId, long unitId, long typeId) {
		String hql = "SELECT count(*) FROM Delegate WHERE congress.id = :congressId AND "
				+ "unit.id = :unitId AND type.id = :typeId AND isEnabled = :isEnabled";
		Query query = getSession().createQuery(hql);
		query.setParameter("congressId", congressId);
		query.setParameter("unitId", unitId);
		query.setParameter("typeId", typeId);
		query.setParameter("isEnabled", true);
	
		long result = (Long) query.uniqueResult();
		return result;
	}

	// Support Method
	private void fillDataDelegate(XWPFTableRow row, Map<Integer, AnotationColumnDetecter> format, Delegate delegate) {

		List<XWPFTableCell> columns = row.getTableCells();

		// Loop column format
		for (Map.Entry<Integer, AnotationColumnDetecter> entryCol : format.entrySet()) {
			XWPFTableCell column = columns.get(entryCol.getKey());

			AnotationColumnDetecter colDetecter = entryCol.getValue();
			Map<Integer, AnotationParagraphDetecter> paraFormat = colDetecter.getParagraphFormat();

			if (colDetecter.getMergeField() != null && !colDetecter.getMergeField().isEmpty()) {
				String fieldName = colDetecter.getMergeField();
				String value = column.getText();
				if (value != null && !value.isEmpty()) {
					fillDataField(fieldName, value, delegate); // Fill Data
				}
			}

			// Loop paragraph format
			List<XWPFParagraph> paragraphs = column.getParagraphs();
			for (Map.Entry<Integer, AnotationParagraphDetecter> entryPara : paraFormat.entrySet()) {

				try {
					XWPFParagraph paragraph = paragraphs.get(entryPara.getKey());
					AnotationParagraphDetecter paragraphDetecter = entryPara.getValue();
					List<AnotationField> fields = paragraphDetecter.getFields();
					String[] values = paragraph.getText().split(paragraphDetecter.getLinkRegexs());

					// Loop anotation field
					for (int i = 0; i < fields.size(); i++) {
						AnotationField field = fields.get(i);
						String fieldName = field.getFieldName();
						String value = "";
						if (paragraphDetecter.getLinkRegexs() != null && !paragraphDetecter.getLinkRegexs().isEmpty()) {
							value = values[i];
						} else {
							value = paragraph.getText();
						}

						if (paragraph.getText() != null && !paragraph.getText().isEmpty()
								&& field.getDefaultValue() != null && !field.getDefaultValue().isEmpty()) {
							value = field.getDefaultValue();
						}

						fillDataField(fieldName, value, delegate); // Fill Data
					}
				} catch (Exception e) {

				}
			}
		}
	}

	private void fillDataField(String field, String value, Delegate delegate) {

		try {
			if (field == null || field.isEmpty())
				return;
			if (value == null || value.isEmpty())
				return;

			//logger.info("Field : " + field + ", value : " + value);

			EnumDelegateField enumField = EnumDelegateField.getEnumByDescription(field);

			switch (enumField) {
			case Address:
				delegate.setAddress(value);
				break;
			case DateOfBirth:
				delegate.setDateOfBirth(value);
				break;
			case DateOfPartyOfficial:
				delegate.setDateOfPartyOfficial(value);
				break;
			case DateOfPartyPreparatory:
				delegate.setDateOfPartyPreparatory(value);
				break;
			case DateOfYouthUnion:
				delegate.setDateOfYouthUnion(value);
				break;
			case Ethnic:
				delegate.setEthnic(value);
				break;
			case Gender:
				delegate.setGender(value);
				break;
			case Name:
				delegate.setName(value);
				break;
			case Ordinal:
				delegate.setOrdinal(value);
				break;
			case PlaceOfBirth:
				delegate.setPlaceOfBirth(value);
				break;
			case Position:
				delegate.setPosition(value);
				break;
			case Religion:
				delegate.setReligion(value);
				break;
			case Type:
				DelegateType type = new DelegateType();
				type.setShortName(value);
				delegate.setType(type);
				break;
			case Unit:
				Unit unit = new Unit();
				unit.setName(value);
				delegate.setUnit(unit);
				break;
			case Note:
				delegate.setNote(value);
			default:
				break;

			}
		} catch (Exception e) {

		}

	}

	

}
