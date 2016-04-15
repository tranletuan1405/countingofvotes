package vn.edu.uit.models.service.barcode;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import vn.edu.uit.models.Barcode;
import vn.edu.uit.models.common.AbstractDao;

@Repository("barcodeDao")
public class BarcodeDao extends AbstractDao implements IBarcodeDao{

	@Override
	public boolean persist(Barcode barcode) {
		return this.persistObject(barcode);
	}

	@Override
	public boolean delete(long id) {
		String hql = "DELETE FROM Barcode WHERE id = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", id);
		int result = query.executeUpdate();
		
		return result > 0 ? true : false;
	}

}
