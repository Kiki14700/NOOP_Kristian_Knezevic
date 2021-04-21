package model_pckg;

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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import controller_pckg.Controller;
import view_pckg.LoginForm;
import view_pckg.NewProductsForm;
import view_pckg.SignupForm;

/**
 * Klasa za stvaranje upita prema bazi podataka.
 * U ovoj klasu omogucena je komunikacija sa bazom podataka i izmjena podataka.
 * @author Kristian Knezevic
 *
 */
public class DataBase {

	public static Connection conn;
	
	
	public DataBase() {
		Controller.currentUser = new User();
	}
	
	
	public void connecting2DB() throws SQLException{
		
		try {
			// load driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// obtain connection
			String url = "jdbc:mysql://localhost:3306/mydb";
			String user = "root";
			String password = "XnshdST9jg"; 
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("Connected to -> " + conn.toString());
		} catch (ClassNotFoundException e) {
			System.out.println("Could not load driver!!!");
		} 

		
	}
	
	
	/**
	 * Metoda koja omogucuje registriranim korisnicima da se prijave u aplikaciju.
	 * Uspoređuju se uneseni podatci s onima u bazi.
	 * Tocnije provjerava se odgovaraju li uneseno korisnicko ime i lozinka onima u bazi podataka.
	 * Ako je sve uredu s podatcima za prijavu korisnik može pristupiti glavnom pogledu aplikacije, a ako podatci nisu tocni korisnik je obavijesten da podatci za prijavu nisu ispravni.
	 * 
	 */
	
	public static void login(String username, String password) {
		
		String q = "SELECT password FROM Users WHERE uname = \"" + username + "\";";
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(q);
			String pass = "";
			while(rs.next())
				pass = rs.getString("password");
			
			
			if(!pass.equals("") && password.equals(pass)) {
				Controller.mainframeView();
			}else {
				System.out.println(pass + ": " + password);
				throw new Exception();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Invalid connection!!!");
			e.printStackTrace();
		}catch (Exception ex) {
			JOptionPane.showMessageDialog(new JFrame(), "Nevazece korisnicko ime ili zaporka!!!");
	
		}
		
		Controller.currentUser =  getUserByUsername(username);
	
	}
	
	
	/**
	 * Metoda koja omogucuje novim korisnicima da se registriraju.
	 * Uneseni podatci o korisniku salju se u bazu podataka, a ako je s registracijom sve uredu korisnik se preusmjerava na pogled za prijavu.
	 * Podatci se unose u tablicu pod nazivom "Users".
	 */
	
	public static void signup(User user) {
		
		if(conn != null) {
			
			String q = "insert into Users (name, surname, address, phoneNum, password, dateOfBirth, uname) values (?,?,?,?,?,?,?)";
			
			
		try {
			
			PreparedStatement stmt = conn.prepareStatement(q);
			stmt.setString(1, user.name);
			stmt.setString(2, user.surname);
			stmt.setString(3, user.address);
			stmt.setInt(4, user.phoneNum);
			stmt.setString(5, new String(user.getPassword()));
			stmt.setDate(6, new java.sql.Date(user.dateOfBirth.getTime()));
			stmt.setString(7, user.username);
			stmt.execute();
			JOptionPane.showMessageDialog(new JFrame(), "Uspješno ste se registrirali");
			stmt.close();
			Controller.currentSignUpForm.dispose();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Invalid connection!!!");
			e.printStackTrace();
		}
		}else {
			System.out.println("konekcija s bazom nije otovorena");
		}
		
	}
	
	/**
	 * Metoda koja omogucuje unos novih proizvoda u bazu podataka.
	 * Za novi proizvod potrebno je unijeti naziv, cijenu, kolicinu u kojoj dolazi te datum unosa.
	 * Podatci se unose u tablicu pod nazivom "Product".
	 */
	public static void inputProducts(Product product) {
		
		if(conn != null) {
			String q = "insert into product (name, price, measure ,dateOfArrival) values (?,?,?,?)";
			
			try {
				
				PreparedStatement stmt = conn.prepareStatement(q);
				stmt.setString(1, product.name);
				stmt.setDouble(2, product.price);
				stmt.setString(3, product.measure);
				stmt.setDate(4, new java.sql.Date(product.dateOfArrival.getTime()));
				stmt.execute();
				JOptionPane.showMessageDialog(new JFrame(), "Uspješno unesen novi proizvod!");
				stmt.close();
				//!!!GREŠKA-->currentProductForm.dispose();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(new JFrame(), "Invalid connection!!!");
				e.printStackTrace();
			}
			}else {
				System.out.println("konekcija s bazom nije otovorena");
			}
		
		
	}
	
	/**
	 * Metoda za dohvacanje korisnika iz baze podataka na temelju unesenog korisnickog imena.
	 * Takoder pretpostavlja se da je korisnicko ime jedinstveno pa se dohvaca samo prvi registrirani korisnik s tim korisnickim imenom iz baze podataka.
	 */
	
	public static User getUserByUsername(String username) {
		
		if(conn != null) {
			String q = "SELECT * FROM Users WHERE uname = \"" + username + "\";";
			User user = new User();
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(q);
				while(rs.next()) {
					user.id = rs.getInt("id");
					user.name = rs.getString("name");
					user.surname = rs.getString("surname");
					user.username = rs.getString("uname");
				}
					
			}catch (Exception e) {
				JOptionPane.showMessageDialog(new JFrame(), "Invalid connection!!!");
				e.printStackTrace();
				
			}
			if(user.username == null || user.username == "") {
				return null;
			}
			return user;
		}
		return null;
	}
	
	/**
	 * Metoda za dohvacanje korisnika iz baze podataka na temelju trazene identifikacijske oznake.
	 */
	
	public static User getUserById(int id) {
		
		if(conn != null) {
			String q = "SELECT * FROM Users WHERE id = \"" + id + "\";";
			User user = new User();
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(q);
				while(rs.next()) {
					user.id = rs.getInt("id");
					user.name = rs.getString("name");
					user.surname = rs.getString("surname");
					user.username = rs.getString("uname");
				}
					
			}catch (Exception e) {
				JOptionPane.showMessageDialog(new JFrame(), "Invalid connection!!!");
				e.printStackTrace();
				
			}
			if(user.username == null || user.username == "") {
				return null;
			}
			return user;
		}
		return null;
	}
	
	/**
	 * Metoda za dohvacanje traženog proizvoda iz baze podataka po unesenoj identifikacijskoj oznaci(id).
	 */
	
	public static Product getPorductById(int id) {
		
		if(conn != null) {
			String q = "SELECT * FROM Product WHERE id = \"" + id +"\";";
			Product product = new Product();
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(q);
				while(rs.next()) {
					product.id = rs.getInt("id");
					product.name = rs.getString("name");
					product.price = rs.getInt("price");
				}
					
			}catch (Exception e) {
				JOptionPane.showMessageDialog(new JFrame(), "Invalid connection!!!");
				e.printStackTrace();
			}
			return product;
		}
		return null;
	}
	
	/**
	 * Metoda za dohvacanje traženog proizvoda iz baze podataka po unesenom imenu proizvoda.
	 */
	
	public static Product getPorductByName(String name) {
		
		if(conn != null) {
			String q = "SELECT * FROM Product WHERE name = \"" + name +"\";";
			Product product = new Product();
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(q);
				while(rs.next()) {
					product.id = rs.getInt("id");
					product.name = rs.getString("name");
					product.price = rs.getInt("price");
					product.measure = rs.getString("measure");
				}
					
			}catch (Exception e) {
				JOptionPane.showMessageDialog(new JFrame(), "Invalid connection!!!");
				e.printStackTrace();
			}
			return product;
		}
		return null;
	}
	
	/**
	 * Metoda koja omogucuje unos podataka o stvorenom racunu u bazu podataka.
	 * Podatci se unose u dvije tablice pod nazivom "Bill" i "Bought_products".
	 * Unos podataka u dvije tablice o racunu potreban je iz razloga vezanog za samu SQL organizaciju podataka unutar tablica.
	 * Tablica "Bill" koristi se za unesene racune, a tablica "Bought products" za kupljene proizvode.
	 */
	
	public static void inputBill(Bill bill) {
		
		if(conn != null) {
			String q = "insert into bill (dateOfPrint, userId , buyerName, paymentMethod, finalPrice) values (?,?,?,?,?)";
			
			try {
				
				PreparedStatement stmt = conn.prepareStatement(q);
				stmt.setDate(1, new java.sql.Date(bill.dateOfPrint.getTime()));
				stmt.setInt(2, bill.userId);
				stmt.setString(3, bill.buyerName);
				stmt.setString(4, bill.paymentMethod);
				stmt.setDouble(5, bill.finalPrice);
				stmt.execute();
				System.out.println(bill.finalPrice);
				//dohvacanje id zadnje unesenog racuna 
				q = "SELECT id FROM bill ORDER BY id DESC;";
				Statement s = conn.createStatement();
				ResultSet rs = s.executeQuery(q);
				while(rs.next()) {
					bill.setId(rs.getInt("id"));
					break;
				}
				
				// unos kupljenih proizvoda u bazu
				for (BoughtProduct bp : bill.boughtproducts) {
					bp.billId = bill.getId();
					q = "INSERT INTO bought_products(productId, billId, quantity) VALUES (?,?,?)";
					stmt = conn.prepareStatement(q);
					stmt.setInt(1, bp.productId);
					stmt.setInt(2, bp.billId);
					stmt.setInt(3, bp.quantity);
					stmt.execute();
				}
				
				JOptionPane.showMessageDialog(new JFrame(), "Uspjesno unesen novi racun!");
				stmt.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(new JFrame(), "Invalid connection!!!");
				e.printStackTrace();
			}
			}else {
				System.out.println("konekcija s bazom nije otovorena");
			}
		
		
	}
	
	/**
	 * Metoda koja dohvaca podatke o prometu iz baze podataka kako bi se mogli graficki prikazati.
	 * Dohvaceni podatci spremaju se u Hash Map koja dozvoljava organizaciju podataka po principu kljuc vrijednost.
	 * Kljuc je u ovom slucaju datum kao jedinstvena vrijednost za koju se sprema ukupan prihod toga datuma.
	 * Grafikon unutar aplikacije izraden je "from scratch", koristeni su elementi poput ImageIcon, BufferedImage i JOptionPane. 
	 */
	
	public static void showChart() {
		
		if(conn != null) {
			String q = "SELECT dateOfPrint, finalPrice FROM Bill ORDER BY dateOfPrint ASC;";
			Chart chart = new Chart();
			HashMap<Date, Double> dailyPurchase  = new HashMap<Date, Double>();
			ArrayList<Date> keys = new ArrayList<Date>();
			double maxValue = 0;
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(q);
				
				while(rs.next()) {
					Date d = rs.getDate("dateOfPrint");
					if(dailyPurchase.containsKey(d)) {
						dailyPurchase.put(d,dailyPurchase.get(d) + rs.getDouble("finalPrice"));
						
						if(dailyPurchase.get(d) > maxValue) {
							maxValue = dailyPurchase.get(d);
						}
						
					}else {
						dailyPurchase.put(d, rs.getDouble("finalPrice"));
						keys.add(d);
						
						if(rs.getDouble("finalPrice") > maxValue) {
							maxValue = rs.getDouble("finalPrice");
						}
					
					}
					
					
				}
				
				java.util.Collections.sort(keys);
				
				//stvaranje slike za crtanje dijagrama
				Dimension imgDim = new Dimension(650,500);
		        BufferedImage chartImage = new BufferedImage(imgDim.width, imgDim.height, BufferedImage.TYPE_INT_RGB);
		        
		        //kreiranje objekta za crtanje po slici
		        Graphics2D g2d = chartImage.createGraphics();
		        
		        //kreiranje pozadine i podrucja po kojem se crta
		        g2d.setBackground(Color.WHITE);
		        g2d.fillRect(0, 0, imgDim.width, imgDim.height);
		        
		        //postavljanje boje za crtanje linija
		        g2d.setColor(Color.BLACK);
		        
		        // x i y osi
		        g2d.drawLine(40, 480, 630, 480);
		        g2d.drawLine(40, 480, 40, 20);
	            g2d.drawString("0", 10, 495);
		        
		        int fieldSize = 630 / keys.size();
		        fieldSize -= fieldSize/10;
		        
		        
		        
		        int prevHeight = 0, currentPosition = 0;
		        
		        double coef = 1;
				if(maxValue > 450) {
					coef = 450 / maxValue;
					
				}
		        
				for (Date date : keys) {
			        int height = (int)(dailyPurchase.get(date).intValue() * coef), h1 = dailyPurchase.get(date).intValue();
			        g2d.setColor(Color.RED);
			        g2d.setStroke(new BasicStroke(3));
			        g2d.drawLine(currentPosition * fieldSize + 40, 480 - prevHeight , (currentPosition + 1) * fieldSize + 40, 480 - height);
			        currentPosition += 1;
			        g2d.setColor(Color.BLACK);
			        g2d.setStroke(new BasicStroke(1));
			        g2d.drawString(date.toString(), currentPosition * fieldSize + 5, 495);
			        g2d.drawString(String.valueOf(h1), 5, 480 - height);
			        prevHeight = height;
			       
				}
				
				ImageIcon ii = new ImageIcon(chartImage);
		        JOptionPane.showMessageDialog(null, ii, "Kretanje prometa", JOptionPane.PLAIN_MESSAGE);
					
			}catch (Exception e) {
				JOptionPane.showMessageDialog(new JFrame(), "Invalid connection!!!");
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	
	/**
	 * Metoda koja dohvaca podatke o korisnicima i sprema ih u listu kako bi kasnije mogli biti prikazani u tablici.
	 */
	
	public static ArrayList<User> getUser() {
		
		ArrayList<User> userList = new ArrayList<>();
		String q = "SELECT id, name, surname, dateOfBirth, address, phoneNum, uname FROM users ;";
		
		if(conn != null) {
			
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(q);
				User user;
				
				while(rs.next()) {
					user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("surname"), rs.getDate("dateOfBirth"),
							rs.getString("address"), rs.getInt("phoneNum"), rs.getString("uname"));
					
					userList.add(user);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(new JFrame(), "Invalid connection!!!");
				e.printStackTrace();
			}
			
			
		return userList;
		
	}
		return null;
	
		}
	
	/**
	 * Metoda koja služi za dohvat imena unesenih proizvoda.
	 * Imena se dohvacaju samo jednom i zbog toga se koristi HashSet.
	 */
	public static HashSet<String> getAllProducts(){
		
		HashSet<String> retList = new HashSet<>();
		String q = "SELECT name FROM product ;";
		
		if(conn != null) {
			
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(q);
				
				
				while(rs.next()) {
					retList.add(rs.getString("name"));
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(new JFrame(), "Invalid connection!!!");
				e.printStackTrace();
			}
		}
		
		return retList;
	}
	
	/**
	 * Metoda koja sluzi za dohvat podataka o kupcu na temelju imena kupca.
	 */
	public static String getBuyer(String buyerName) {
		
		String q = "SELECT * FROM bill WHERE buyerName = \"" + buyerName + "\";";
		if(conn != null) {
			
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(q);
				
				while(rs.next()) {
					return rs.getString("buyerName");
				}
				return null;
			} catch (Exception e) {
			
			}
		}
		
		return null;
	}
	
	/**
	 * Metoda koja dohvaca trazene podatke o ispisanim racunima za odabrano razdoblje.
	 * Trazeni podatci su ukupna cijena, ime kupca, datum izdanja racuna, kolicina i naziv proizvoda.
	 */
	public static ArrayList<Bill> getBillByDate(String date, String date2) {
		
		ArrayList<Bill> retBill = new ArrayList<>(); 
		
		String q = "SELECT * FROM bill WHERE dateOfPrint BETWEEN \"" + date + "\" AND \"" + date2 + "\";";
		
		if(conn != null) {
			
			try {
				Statement stmt = conn.createStatement();
				Statement stmt2 = conn.createStatement();
				ResultSet rs = stmt.executeQuery(q);
				
				while(rs.next()) {
					Bill b = new Bill();
					b.buyerName = rs.getString("buyerName");
					b.finalPrice = rs.getDouble("finalPrice");
					b.boughtproducts = new ArrayList<BoughtProduct>();
					int id = rs.getInt("id");
					String q2 = "SELECT * FROM bought_products WHERE billId = " + id + ";";
					ResultSet rs2 = stmt2.executeQuery(q2);
					b.dateOfPrint = rs.getDate("dateOfPrint");
					while(rs2.next()) {
						BoughtProduct bp = new BoughtProduct();
						bp.billId = id;
						bp.productId = rs2.getInt("productId");
						bp.quantity = rs2.getInt("quantity");
						b.boughtproducts.add(bp);
						
					}
					retBill.add(b);
					
				}
			
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		return retBill;
		
	}
	
	/**
	 * Metoda koja dohvaća tražene podatke o proizvodu na temelju imena proizvoda.
	 * Trazeni podatci su ime proizvoda, cijena, kolicina i ukupna zarada po proizvodu.
	 */
	public static ArrayList<String> getProductInfo(String productName){
		
		ArrayList<String> list = new ArrayList<>();
		ArrayList<Integer> billIds = new ArrayList<>();
		Product pr = Controller.getPorductByName(productName);
		
		String pocetni = "Proizvod : " + pr.name + " Kolicina : " + pr.measure + " Cijena : " + pr.price + "\nUkupna zarada na proizvodu : " ;
		
		String q = "SELECT * FROM bought_products WHERE productId = " + pr.id;
		
		if(conn != null) {
			
			try {
				
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(q);
				int quant = 0;
				
				while(rs.next()) {
					billIds.add(rs.getInt("billId"));
					quant += rs.getInt("quantity");
				}
				
				pocetni += quant * pr.price + "\n\nDatum izdavanja :\tProdavac :\tKupac :";
				list.add(pocetni);
				
				for (Integer bid : billIds) {
					q = "SELECT * FROM bill WHERE id = " + bid;
					Statement stmt1 = conn.createStatement();
					ResultSet rs1 = stmt1.executeQuery(q);
					// izvršit će se samo jednom
					while(rs1.next()) {
						String s1 = rs1.getString("dateOfPrint") + "\t";
						String q1 = "SELECT * FROM users WHERE id = " + rs1.getInt("userId");
						Statement stmt2 = conn.createStatement();
						ResultSet rs2 = stmt2.executeQuery(q1);
						while(rs2.next()) {
							s1 += rs2.getString("uname") + "\t" + rs1.getString("buyerName");
						}
						list.add(s1);
					}
					
				}
				
				
			} catch (Exception e) {
				
			}
		}
		
		return list;
		
	}
	
	/**
	 * Metoda koja dohvaća kupce iz baze podataka.
	 * Ime kupca pojavljuje se samo jednom i zbog toga se koristio HashSet.
	 * U slucaju pogrešnih zapisa provjerva se je li ime kupca prazno.
	 */
	public static HashSet<String> getAllBuyers(){
		
		HashSet<String> retList = new HashSet<>();
		String q = "SELECT buyerName FROM bill ;";
		
		if(conn != null) {
			
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(q);
				
				
				while(rs.next()) {
					if(rs.getString("buyerName") != null && !rs.getString("buyerName").equals("")) {
						retList.add(rs.getString("buyerName"));
					}
					
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(new JFrame(), "Invalid connection!!!");
				e.printStackTrace();
			}
		}
		
		return retList;
	}
	
	/**
	 * Metoda koja sluzi za dohvat podataka o kupcu za odabrano ime kupca.
	 * Trazeni podatci su ime kupca, ukupna potrosnja u trgovini, izdani racuni za odabranog kupca, ime prodavaca i ukupna cijena.
	 */
	public static ArrayList<String> getBuyerInfo(String buyerName){
		
		ArrayList<String> list = new ArrayList<>();
		ArrayList<String> list2 = new ArrayList<>();
		Product pr = Controller.getPorductByName(buyerName);
		
		String pocetni = "Kupac : " + buyerName + "\nUkupno potrošio u našoj trgovini : " ;
		
		String q = "SELECT * FROM bill WHERE buyerName = \"" + buyerName + "\";";
		
		if(conn != null) {
			
			try {
				
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(q);
				double spentMoney = 0;
				while(rs.next()) {
					spentMoney += rs.getDouble("finalPrice");
					list2.add(rs.getString("dateOfPrint") + "\t" + getUserById(rs.getInt("userId")).username + "\t" + rs.getDouble("finalPrice"));
				}
				pocetni += spentMoney + "\n\nIzdani racuni za odabranog kupca :\n\nDatum izdavanja :\tProdavac :\tUkupna cijena :\n" ;
				list.add(pocetni);
				list.addAll(list2);
				
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		return list;
		
	}
	
	


}
