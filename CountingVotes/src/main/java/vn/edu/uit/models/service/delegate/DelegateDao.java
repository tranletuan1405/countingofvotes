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

import vn.edu.uit.extra.DataConfig;
import vn.edu.uit.extra.SupportMethods;
import vn.edu.uit.models.Delegate;
import vn.edu.uit.models.DelegateType;
import vn.edu.uit.models.Unit;
import vn.edu.uit.models.common.AbstractDao;
import vn.edu.uit.models.service.delegate.support.AnotationColumnDetecter;
import vn.edu.uit.models.service.delegate.support.AnotationDetecter;
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

			if (table == null || table.getNumberOfRows() < 1) {
				System.out.println("Table is Null");
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
			for(int i = firstData, j = 1; i < rows.size(); i++, j++){
				
			}
		

			return delegates;
		} catch (FileNotFoundException e) {
			System.out.println("Table is Null");
			e.printStackTrace();
			return delegates;
		} catch (IOException e) {
			System.out.println("Table is Null");
			e.printStackTrace();
			return delegates;
		}
	}

	// Support Method
	private void getDelegateData(XWPFTableRow row, int rowOrdinal, Map<Integer, AnotationColumnDetecter> format) {

		List<XWPFTableCell> columns = row.getTableCells();
		
		for (Map.Entry<Integer, AnotationColumnDetecter> entry : format.entrySet()) {
			
			
		}
	}
}
