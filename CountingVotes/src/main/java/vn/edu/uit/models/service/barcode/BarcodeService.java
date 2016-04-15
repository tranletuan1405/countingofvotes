package vn.edu.uit.models.service.barcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.uit.models.Barcode;

@Service("barcodeService")
@Transactional
public class BarcodeService implements IBarcodeDao {

	@Autowired
	private IBarcodeDao barcodeDao;

	@Override
	public boolean persist(Barcode barcode) {
		return barcodeDao.persist(barcode);
	}

	@Override
	public boolean delete(long id) {
		return barcodeDao.delete(id);
	}
	
	
}
