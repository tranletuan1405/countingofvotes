package vn.edu.uit.extra;

public class App {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		String appKey = "vbwckpsvr2dg80xps02goxk2";//SupportMethods.getRandomString(24);
		String appIv = "87exi599"; //SupportMethods.getRandomString(8);
		
		System.out.println("APP KEY : " + appKey);
		System.out.println("APP IV : " + appIv);
		
		//String encryptStr = new TripleDes(appKey, appIv).encryptText("1_1_1");
		//System.out.println("Encrypt : " + encryptStr);
		
		String decryptStr = new TripleDes(appKey, appIv).decryptText("24OImLuPqys=");
		System.out.println("Decrypt : " + decryptStr);
		
		
	}

}
