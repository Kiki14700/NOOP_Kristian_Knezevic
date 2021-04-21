package model_pckg;

import java.sql.Date;

/**
 * Model dostupnih proizvoda za kupnju.
 * Proizvod ima ime, cijenu, kolicinu u kojoj dolazi i datum unosa u bazu podataka.
 * Kolicina se sastoji od broja i mjerne jedinice te je zato koristen tip podataka String.
 * @author Kristian Knezevic
 *
 */
public class Product {
	
	public int id;
	public String name;
	public double price;
	public String measure;
	public java.util.Date dateOfArrival;
	
	
	
	

}
