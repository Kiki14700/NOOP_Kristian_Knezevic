package model_pckg;

/**
 * Model kupljenog proizvoda.
 * Jedan model uparuje proizvod i račun na kojem je izdan po identifikacijskim oznakama(id) te zapisuje koliko komada proizvoda je kupljeno(quantity).
 * Jedan račun ima isti bill_id pa ćemo znati koji su kupljeni proizvodi(prepoznajemo ih po product_id) na kojem računu.
 * @author Kristian Knežević
 *
 */
public class BoughtProduct {
	
	private int id;
	public int productId;
	public int billId;
	public int quantity;

}
