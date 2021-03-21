package model_pckg;

import java.sql.Date;
import java.util.ArrayList;

public class Bill {
	
	private int id;
	public ArrayList<BoughtProduct> boughtproducts;
	public Date dateOfPrint;
	public int userId;
	public String buyerName;
	public String paymentMethod;
	public double finalPrice;
	
	
	public void setId(int id) {
		this.id = id;
	}


	public int getId() {
		return id;
	}
	
	
	
	

}
