package model_pckg;

/**
 * Model kupljenog proizvoda.
 * Jedan model uparuje proizvod i racun na kojem je izdan po identifikacijskim oznakama(id) te zapisuje koliko komada proizvoda je kupljeno(quantity).
 * Jedan racun ima isti bill_id pa cemo znati koji su kupljeni proizvodi(prepoznajemo ih po product_id) na kojem racunu.
 * @author Kristian Knezevic
 *
 */
public class BoughtProduct {
	
	private int id;
	public int productId;
	public int billId;
	public int quantity;

}
