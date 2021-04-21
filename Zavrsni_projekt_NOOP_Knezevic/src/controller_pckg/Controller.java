 package controller_pckg;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import javax.swing.JOptionPane;


import javax.swing.SwingUtilities;

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;


import model_pckg.Bill;
import model_pckg.BoughtProduct;
import model_pckg.Chart;
import model_pckg.DataBase;
import model_pckg.Product;
import model_pckg.User;
import view_pckg.BuyerReviewerForm;
import view_pckg.DateReviewerForm;
import view_pckg.EmployeesForm;
import view_pckg.FormPanel;
import view_pckg.LoginForm;
import view_pckg.MainFrame;
import view_pckg.NewProductsForm;
import view_pckg.ProductReviewerForm;
import view_pckg.SignupForm;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.sql.Statement;

/**
 * Glavna klasa cijele aplikacije koja upravlja sa svime sto se događa izmedu ostala dva dijela MVC-a, a to su model i pogled(view).
 * Klasa Controller komunicira s klasom "DataBase" odnosno omogucuje slanje podataka u bazu i njihovo dohvacanje kako bi se sama aplikacija i korisnik njima sluzili. 
 * Za lakse shvacanje moglo bi se reci da Controller ustvari puni modele odgovarajucim podatcima iz baze podataka te ih stavlja na odgovarajuci pogled, ali takoder na zahtjev korisnika izmjenjuje podatke u modelima te ih vraca u bazu podataka preko klase "DataBase".
 * @author Kristian Knezevic
 *
 */
public class Controller {
	
	
	public static SignupForm currentSignUpForm;
	public static NewProductsForm currentProductForm;
	public static User currentUser;
	private static DataBase db;
	private static MainFrame mf;
	
	
	/**
	 * Metoda za pokretanje pogleda za prijavu korisnika
	 */
	public static void loginView() {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new LoginForm();
			}
			
			
		});
	}
	
	/**
	 * Metoda za pokretanje pogleda(prozora) za registraciju novog korisnika.
	 */
	public static void registrationFormView() {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				currentSignUpForm = new SignupForm();
	
				
			}
		});
	}
	
	/**
	 * Metoda za pokretanje glavnog pogleda(prozora) u kojemu se vrsi stvaranje racuna.
	 * Glavni pogled daje mogucnost pristupa ostalim pogledima(dijagram, pregled korisnika i prometa).
	 */
	
	public static void mainframeView() {
		

		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				mf = new MainFrame();
				
			}
		});
		
	
	}
	
	/**
	 * Metoda za pokretanje pogleda(prozora) s podatcima korisnika ciji podatci su pohranjeni u bazi podataka.
	 * To su svi korisinici koji imaju pravo prijave.
	 */
	
	public static void employeesView() {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new EmployeesForm();
				
			}
		});
		
	}
	
	/**
	 * Metoda za pokretanje pogleda(prozora) za graficki prikaz kretanja prometa.
	 * U tom pogledu vidiljiv je linijski grafikon ukupne prodaje po svakom datum u kojemu je ostvarena bilo kakva dobit. 
	 */
	
	public static void chartView() {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				showChart();
				
			}
		});
	}
	
	/**
	 * Metoda za pokretanje pogleda(prozora) za unos novih proizvoda u bazu.
	 */
	
	public static void newProductsView() {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				currentProductForm = new NewProductsForm();
				
			}
		});
		
	}
	
	/**
	 * Metoda za pokretanje pogleda(prozora) za pregled podataka po kupnji za uneseno razdoblje.
	 */
	
	public static void dateReviewView() {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new DateReviewerForm();
				
			}
		});
	}
	
	
	/**
	 * Metoda za pokretanje pogleda(prozora) za pregled podataka po kupnji za odabrani proizvod.
	 */
	public static void productRewievView() {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new ProductReviewerForm();
				
			}
		});
	}
	
	/**
	 * Metoda za pokretanje pogleda(prozora) za pregled podataka po kupnji za odabranog kupca.
	 */
	public static void buyerReviewView() {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new BuyerReviewerForm();
				
			}
		});
	}
	
	/**
	 * Metoda koja poziva metodu iz klase DataBase.
	 * Omogucuje registriranim korisnicima da se prijave u aplikaciju.
	 * Usporeduju se uneseni podatci s onima u bazi.
	 * Tocnije provjerava se odgovaraju li uneseno korisnicko ime i lozinka onima u bazi podataka.
	 * Ako je sve uredu s podatcima za prijavu korisnik moze pristupiti glavnom pogledu aplikacije, a ako podatci nisu tocni korisnik je obavijesten da podatci za prijavu nisu ispravni.
	 */
	public static void login(String username, String password) {
		
		db.login(username, password);
	
	}
	
	/**
	 * Metoda koja poziva metodu iz klase DataBase.
	 * Omogucuje novim korisnicima da se registriraju.
	 * Uneseni podatci o korisniku salju se u bazu podataka, a ako je s registracijom sve uredu korisnik se preusmjerava na pogled za prijavu.
	 * Podatci se unose u tablicu pod nazivom "Users".
	 */
	public static void signup(User user) {
		
		db.signup(user);
	}
	
	/**
	 * Metoda koja poziva metodu iz klase DataBase.
	 * Omogucuje unos novih proizvoda u bazu podataka.
	 * Za novi proizvod potrebno je unijeti naziv, cijenu, kolicinu u kojoj dolazi te datum unosa.
	 * Podatci se unose u tablicu pod nazivom "Product".
	 */
	public static void inputProducts(Product product) {
		
		db.inputProducts(product);
	}
	
	/**
	 * Metoda koja poziva metodu iz klase DataBase.
	 * Sluzi za dohvacanje korisnika iz baze podataka na temelju unesenog korisnickog imena.
	 * Takoder pretpostavlja se da je korisnicko ime jedinstveno pa se dohvaca samo prvi registrirani korisnik s tim korisnickim imenom iz baze podataka.
	 */
	public static User getUserByUsername(String username) {
		
		User user = db.getUserByUsername(username);
		
		return user;
	}
	
	
	/**
	 * Metoda koja poziva metodu iz klase DataBase.
	 * Sluzi za dohvacanje traženog proizvoda iz baze podataka po unesenoj identifikacijskoj oznaci(id).
	 */
	public static Product getPorductById(int id) {
		
		Product product = db.getPorductById(id);
		
		return product;
	}
	
	
	/**
	 * Metoda koja poziva metodu iz klase DataBase.
	 * Sluzi za dohvacanje trazenog proizvoda iz baze podataka po unesenom imenu proizvoda.
	 */
	
	public static Product getPorductByName(String name) {
		
		Product product = db.getPorductByName(name);
		
		return product;
	}
	
	
	
	/**
	 * Metoda koja poziva metodu iz klase DataBase.
	 * Omogucuje unos podataka o stvorenom racunu u bazu podataka.
	 * Podatci se unose u dvije tablice pod nazivom "Bill" i "Bought_products".
	 * Unos podataka u dvije tablice o racunu potreban je iz razloga vezanog za samu SQL organizaciju podataka unutar tablica.
	 * Tablica "Bill" koristi se za unesene racune, a tablica "Bought products" za kupljene proizvode.
	 */
	public static void inputBill(Bill bill) {
		
		db.inputBill(bill);
	}
	
	/**
	 * Metoda koja poziva metodu iz klase DataBase.
	 * Dohvaca podatke o prometu iz baze podataka kako bi se mogli graficki prikazati.
	 * Dohvaceni podatci spremaju se u Hash Map koja dozvoljava organizaciju podataka po principu kljuc vrijednost.
	 * Kljuc je u ovom slucaju datum kao jedinstvena vrijednost za koju se sprema ukupan prihod toga datuma.
	 * Grafikon unutar aplikacije izrađen je "from scratch", koristeni su elementi poput ImageIcon, BufferedImage i JOptionPane. 
	 */
	public static void showChart() {
		
		db.showChart();
		
	}
	
	/**
	 * Metoda koja poziva metodu iz klase DataBase.
	 * Dohvaca podatke o korisnicima i sprema ih u listu kako bi kasnije mogli biti prikazani u tablici.
	 */
	public static ArrayList<User> getUser() {
		
		ArrayList<User> userList = db.getUser();
		
		return userList;
	
		}
	
	/**
	 * Metoda koja poziva metodu iz klase DataBase.
	 * Sluzi za dohvat imena unesenih proizvoda.
	 * Imena se dohvacaju samo jednom i zbog toga se koristi HashSet.
	 */
	public static HashSet<String> getAllProducts(){
		
		
		
		return db.getAllProducts();
	}
	
	/**
	 * Metoda koja poziva metodu iz klase DataBase.
	 * Sluzi za dohvat podataka o kupcu na temelju imena kupca.
	 */
	public static boolean getBuyer(String buyerName) {
		
		if(db.getBuyer(buyerName) == null) {
			return false;
		}
		
		return true;
		
	}
	
	/**
	 * Metoda koja poziva metodu iz klase DataBase.
	 * Dohvaca trazene podatke o ispisanim racunima za odabrano razdoblje.
	 * Trazeni podatci su ukupna cijena, ime kupca, datum izdanja racuna, kolicina i naziv proizvoda.
	 */
	public static ArrayList<Bill> getBillByDate(String date, String date2){
		
		return db.getBillByDate(date, date2);
	}
	
	/**
	 * Metoda koja poziva metodu iz klase DataBase.
	 * Dohvaca trazene podatke o proizvodu na temelju imena proizvoda.
	 * Trazeni podatci su ime proizvoda, cijena, kolicina i ukupna zarada po proizvodu.
	 */
	public static ArrayList<String> getProductInfo(String productName){
		
		return db.getProductInfo(productName);
		
	}
	
	/**
	 * Metoda koja poziva metodu iz klase DataBase.
	 * Dohvaca kupce iz baze podataka.
	 * Ime kupca pojavljuje se samo jednom i zbog toga se koristio HashSet.
	 */
	public static HashSet<String> getAllBuyers(){
		
		return db.getAllBuyers();
	}
	
	/**
	 * Metoda koja poziva metodu iz klase DataBase.
	 * Sluzi za dohvat podataka o kupcu za odabrano ime kupca.
	 * Trazeni podatci su ime kupca, ukupna potrosnja u trgovini, izdani racuni za odabranog kupca, ime prodavaca i ukupna cijena.
	 */
	public static ArrayList<String> getBuyerInfo(String buyerName){
		
		return db.getBuyerInfo(buyerName);
	}
	
	public static void refreshWindow() {
		mf.dispose();
		mainframeView();
	}
	
}
