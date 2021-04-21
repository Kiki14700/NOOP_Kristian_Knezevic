package model_pckg;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Model racuna kupnje.
 * Model racuna kupnje sadrzi listu kupljenih proizvoda, datum ispisa racuna, identifikacijsku oznaku korisnika sustava(trgovca) koji je ispisao racun, ime kupca, opis nacina placanja(gotovina, kartica...) i ukupnu cijenu. 
 * @author Kristian Knezevic
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
