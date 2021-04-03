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
import model_pckg.Product;
import model_pckg.User;

import view_pckg.EmployeesForm;
import view_pckg.LoginForm;
import view_pckg.MainFrame;
import view_pckg.NewProductsForm;
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
import java.sql.Statement;

/**
 * Glavna klasa cijele aplikacije koja upravlja sa svime što se događa između ostala dva dijela MVC-a, a to su model i pogled(view).
 * Klasa Controller komunicira s bazom podataka odnosno omogućuje slanje podataka u bazu i njihovo dohvaćanje kako bi se sama aplikacija i korisnik njima služili. 
 * Za lakše shvaćanje moglo bi se reći da Controller ustvari puni modele odgovarajućim podatcima iz baze podataka te ih stavlja na odgovarajući pogled, ali također na zahtjev korisnika izmjenjuje podatke u modelima te ih vraća u bazu podataka.
 * @author Kristian Knežević
 *
 */
public class Controller {
	
	static Connection conn;
	private static SignupForm currentSignUpForm;
	private static NewProductsForm currentProductForm;
	public static User currentUser;
	
	/**
	 * Main metoda za pokretanje aplikacije.
	 * Prilikom pokretanja kao prvi view korisniku se prikazuje prozor za prijavu korisnika.
	 * Također u Main metodi ostvaruje se i konekcija s bazom podataka.
	 */
	
	public static void main(String[] args) throws SQLException {
		
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
	 * Metoda za pokretanje glavnog pogleda(prozora) u kojemu se vrši stvaranje računa.
	 * Glavni pogled daje mogućnost pristupa ostalim pogledima(dijagram, pregled korisnika i prometa).
	 */
	
	public static void mainframeView() {
		

		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new MainFrame();
				
			}
		});
		
	
	}
	
	/**
	 * Metoda za pokretanje pogleda(prozora) s podatcima korisnika čiji podatci su pohranjeni u bazi podataka.
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
	 * Metoda za pokretanje pogleda(prozora) za grafički prikaz kretanja prometa.
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
	 * Metoda koja omogućuje registriranim korisnicima da se prijave u aplikaciju.
	 * Uspoređuju se uneseni podatci s onima u bazi.
	 * Točnije provjerava se odgovaraju li uneseno korisničko ime i lozinka onima u bazi podataka.
	 * Ako je sve uredu s podatcima za prijavu korisnik može pristupiti glavnom pogledu aplikacije, a ako podatci nisu točni korisnik je obaviješten da podatci za prijavu nisu ispravni.
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
				mainframeView();
			}else {
				System.out.println(pass + ": " + password);
				throw new Exception();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Invalid connection!!!");
			e.printStackTrace();
		}catch (Exception ex) {
			JOptionPane.showMessageDialog(new JFrame(), "Wrong username or password!!!");
	
		}
		
		currentUser =  getUserByUsername(username);
	
	}
	
	/**
	 * Metoda koja omogućuje novim korisnicima da se registriraju.
	 * Uneseni podatci o korisniku šalju se u bazu podataka, a ako je s registracijom sve uredu korisnik se preusmjerava na pogled za prijavu.
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
			currentSignUpForm.dispose();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Invalid connection!!!");
			e.printStackTrace();
		}
		}else {
			System.out.println("konekcija s bazom nije otovorena");
		}
		
		
	
	}
	
	/**
	 * Metoda koja omogućuje unos novih proizvoda u bazu podataka.
	 * Za novi proizvod potrebno je unijeti naziv, cijenu, količinu u kojoj dolazi te datum unosa.
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
				currentProductForm.dispose();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(new JFrame(), "Invalid connection!!!");
				e.printStackTrace();
			}
			}else {
				System.out.println("konekcija s bazom nije otovorena");
			}
		
		
	}
	

	
	/**
	 * Metoda za dohvaćanje korisnika iz baze podataka na temelju unesenog korisničkog imena.
	 * Također pretpostavlja se da je korisničko ime jedinstveno pa se dohvaća samo prvi registrirani korisnik s tim korisničkim imenom iz baze podataka.
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
			return user;
		}
		return null;
	}
	
	/**
	 * Metoda za dohvaćanje traženog proizvoda iz baze podataka po unesenoj identifikacijskoj oznaci(id).
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
	 * Metoda za dohvaćanje traženog proizvoda iz baze podataka po unesenom imenu proizvoda.
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
	 * Metoda koja omogućuje unos podataka o stvorenom računu u bazu podataka.
	 * Podatci se unose u dvije tablice pod nazivom "Bill" i "Bought_products".
	 * Unos podataka u dvije tablice o računu potreban je iz razloga vezanog za samu SQL organizaciju podataka unutar tablica.
	 * Tablica "Bill" koristi se za unesene račune, a tablica "Bought products" za kupljene proizvode.
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
				
				JOptionPane.showMessageDialog(new JFrame(), "Uspješno unesen novi račun!");
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
	 * Metoda koja dohvaća podatke o prometu iz baze podataka kako bi se mogli grafički prikazati.
	 * Dohvaćeni podatci spremaju se u Hash Map koja dozvoljava organizaciju podataka po principu ključ vrijednost.
	 * Ključ je u ovom slučaju datum kao jedinstvena vrijednost za koju se sprema ukupan prihod toga datuma.
	 * Grafikon unutar aplikacije izrađen je "from scratch", korišteni su elementi poput ImageIcon, BufferedImage i JOptionPane. 
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
	 * Metoda koja dohvaća podatke o korisnicima i sprema ih u listu kako bi kasnije mogli biti prikazani u tablici.
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
