package model_pckg;

import java.sql.Date;

/**
 * Model dostupnih proizvoda za kupnju.
 * Proizvod ima ime, cijenu, količinu u kojoj dolazi i datum unosa u bazu podataka.
 * Količina se sastoji od broja i mjerne jedinice te je zato korišten tip podataka String.
 * @author Kristian Knežević
 *
 */
public class Product {
	
	public int id;
	public String name;
	public double price;
	public String measure;
	public java.util.Date dateOfArrival;
	
	
	
	

}
