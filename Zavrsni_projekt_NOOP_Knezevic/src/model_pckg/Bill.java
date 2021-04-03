package model_pckg;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Model računa kupnje.
 * Model računa kupnje sadrži listu kupljenih proizvoda, datum ispisa računa, identifikacijsku oznaku korisnika sustava(trgovca) koji je ispisao račun, ime kupca, opis načina plaćanja(gotovina, kartica...) i ukupnu cijenu. 
 * @author Kristian Knežević
 *
 */
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
