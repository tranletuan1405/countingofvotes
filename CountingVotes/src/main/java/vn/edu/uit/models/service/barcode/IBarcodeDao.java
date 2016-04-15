package vn.edu.uit.models.service.barcode;

import vn.edu.uit.models.Barcode;

public interface IBarcodeDao {

	boolean persist(Barcode barcode);
	
	boolean delete(long id);
}
