package vn.edu.uit.models.service.delegate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.edu.uit.models.Delegate;
import vn.edu.uit.models.Unit;
import vn.edu.uit.models.common.AbstractDao;
import vn.edu.uit.models.service.delegate.support.DelegateFieldPosition;
import vn.edu.uit.models.service.delegate.support.EnumDelegateField;
import vn.edu.uit.models.service.unit.UnitDao;

@Repository
public class DelegateDao extends AbstractDao implements IDelegateDao {

	private static final Logger logger = LoggerFactory.getLogger(DelegateDao.class);

	@Autowired
	UnitDao unitDao;

	@Override
	public boolean persist(Delegate delegate) {
		return this.persist(delegate);
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
	public List<Delegate> getByDocument(String filePath) {

		List<Delegate> delegates = new ArrayList<Delegate>(0);

		try {
			FileInputStream is = new FileInputStream(new File(filePath));
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

			if (table == null)
				return delegates;

			// Detect title form
			XWPFTableRow title = table.getRow(0);
			Map<EnumDelegateField, DelegateFieldPosition> format = getFormat(title);

			List<XWPFTableRow> rows = table.getRows();
			for (int i = 1; i < rows.size(); i++) {
				try {
					XWPFTableRow row = rows.get(i);
					Delegate delegate = new Delegate();
					fillDataDelegate(delegate, row, i, format);
					delegates.add(delegate);
				} catch (Exception e) {

				}
			}

			return delegates;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return delegates;
		} catch (IOException e) {
			e.printStackTrace();
			return delegates;
		}
	}

	// Support Method
	private Map<EnumDelegateField, DelegateFieldPosition> getFormat(XWPFTableRow title) {
		Map<EnumDelegateField, DelegateFieldPosition> map = new HashMap<EnumDelegateField, DelegateFieldPosition>();
		List<XWPFTableCell> columns = title.getTableCells();

		for (int i = 0; i < columns.size(); i++) {
			List<XWPFParagraph> paragraphs = columns.get(i).getParagraphs();

			int cell = 0;
			for (int j = 0; j < paragraphs.size(); j++) {
				String text = paragraphs.get(j).getText();
				String[] split1 = text.split("\\(");
				String[] split2 = split1[split1.length - 1].split("\\)");
				String field = split2[0];

				try {
					EnumDelegateField enumField = EnumDelegateField.getEnumByDescription(field);
					if (enumField != null) {
						DelegateFieldPosition position = new DelegateFieldPosition(i, cell++);
						map.put(enumField, position);

						/*logger.info(enumField.getDescription() + " | column : " + position.getColumn() + " | cell : "
								+ position.getCell());*/
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return map;
	}

	private void fillDataDelegate(Delegate delegate, XWPFTableRow row , int rowOrdinal, 
			Map<EnumDelegateField, DelegateFieldPosition> format) {
		
		List<XWPFTableCell> columns = row.getTableCells();
		for (Map.Entry<EnumDelegateField, DelegateFieldPosition> entry : format.entrySet()) {
			try {
				DelegateFieldPosition pos = entry.getValue();
				XWPFTableCell column = columns.get(pos.getColumn());
				
				List<XWPFParagraph> paragraphs = column.getParagraphs();
				String content = paragraphs.get(pos.getCell()).getParagraphText();
				
				switch (entry.getKey()) {
				case Achievement:
					delegate.setAchievement(content);
					break;
				case DateOfBirth:
					delegate.setDateOfBirth(content);
					break;
				case DateOfParty:
					delegate.setDateOfParty(content);
					break;
				case DateOfYouthUnion:
					delegate.setDateOfYouthUnion(content);
					break;
				case Ethnic:
					delegate.setEthnic(content);
					break;
				case FieldOfStudy:
					delegate.setFieldOfStudy(content);
					break;
				case Gender:
					delegate.setGender(content);
					break;
				case Name:
					delegate.setName(content);
					break;
				case PlaceOfBirth:
					delegate.setPlaceOfBirth(content);
					break;
				case PoliticalTheory:
					delegate.setPoliticalTheory(content);
					break;
				case Position:
					delegate.setPosition(content);
					break;
				case Religion:
					delegate.setReligion(content);
					break;
				case Ordinal:
					if(content == null || content.isEmpty()) content = String.valueOf(rowOrdinal);
					delegate.setOrdinal(content);
					break;
				case Unit:
					Unit unit = unitDao.fetch(content);

					if (unit == null) {
						unit = new Unit();
						unit.setName(content);
					}

					delegate.setUnit(unit);
					break;
				default:
					break;
				}
			} catch (Exception e) {

			}
		}
	}
}
