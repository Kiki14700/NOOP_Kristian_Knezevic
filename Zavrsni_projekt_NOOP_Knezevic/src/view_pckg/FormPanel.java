package view_pckg;


import java.awt.Color;

import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.Border;

import org.jdatepicker.JDatePanel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller_pckg.Controller;
import model_pckg.Bill;
import model_pckg.BoughtProduct;
import model_pckg.Product;



/**
 * U klasi FormPanel stvoreni su i aktivirani elementi glavnog pogleda u kojem se odvija prodaja proizvoda.
 * Klasa omogućuje izradu računa, spremanje računa u bazu podataka(obavlja Controller) i ispis računa(pomoću pisača).
 * @author Kristian Knežević
 */

public class FormPanel extends JPanel{
	
	private JTextArea txtArea;
	
	private JLabel proizvodiLbl;
	private JLabel kolicinaLbl;
	private JLabel kupacLbl;
	private JLabel opisPlacanjaLbl;
	private JLabel datumLbl;
	
	private JTextField proizvodiTxt;
	private JTextField kolicinaTxt;
	private JTextField kupacTxt;
	
	private JButton dodajBtn;
	private JButton potvrdiBtn;
	private JButton ispisiBtn;
	private JButton obrisiBtn;
	private JComboBox<String> opisPlacanjaComboBox;;
	private DefaultComboBoxModel<String> opisPlacanja;
	
	
	private Bill bill;
	private String seller;
	
	public FormPanel() {

		Dimension dim = getPreferredSize();
		setPreferredSize(new Dimension(730, 590));
		bill = new Bill();
		seller = Controller.currentUser.username;
		bill.userId = Controller.currentUser.id;
		bill.boughtproducts = new ArrayList<BoughtProduct>();
		bill.buyerName = "";
		createComps();
		layoutComps();
		setBorders();
		activate();
	}
	
	
	/**
	 * Metoda za stvaranje naslova i obruba oko lijeve strane pogleda
	 */
	private void setBorders() {

		Border inner = BorderFactory.createTitledBorder("Kupnja");
		Border outer = BorderFactory.createEmptyBorder(2, 5, 10, 340);
		Border end = BorderFactory.createCompoundBorder(outer, inner);
		setBorder(end);
		

	}
	
	/**
	 * Metoda za inicijalizaciju i imenovanje komponenti koje će biti prikazane na samom pogledu.
	 */
	private void createComps() {
		txtArea = new JTextArea();
		proizvodiLbl = new JLabel("Proizvodi");
		kolicinaLbl = new JLabel("Količina");
		kupacLbl = new JLabel("Ime Kupca");
		opisPlacanjaLbl = new JLabel("Opis plaćanja");
		datumLbl = new JLabel("Datum kupnje");
		proizvodiTxt = new JTextField();
		kolicinaTxt = new JTextField();
		kupacTxt = new JTextField();
		dodajBtn = new JButton("Dodaj");
		potvrdiBtn = new JButton("Potvrdi");
		ispisiBtn = new JButton("Ispiši");
		opisPlacanjaComboBox = new JComboBox<>();
		opisPlacanja = new DefaultComboBoxModel<>();
		obrisiBtn = new JButton("Obriši");
	
		
		
		
		
		
	}
	
	/**
	 * Metoda za razmještaj komponenti na pogledu te postavljanje dimenzija samih komponenti.
	 */
	
	private void layoutComps() {
		setLayout(null);
		
		txtArea.setBounds(400, 10, 330, 470);
		proizvodiLbl.setBounds(75, 100, 150, 15);
		kolicinaLbl.setBounds(270, 100, 150, 15);
		kupacLbl.setBounds(75, 290, 150, 15);
		proizvodiTxt.setBounds(40, 130, 130, 30);
		kolicinaTxt.setBounds(230, 130, 130, 30);
		dodajBtn.setBounds(120, 200, 150, 30);
		opisPlacanjaLbl.setBounds(250, 290, 150, 15);
		kupacTxt.setBounds(40, 320, 130, 30);
		
		potvrdiBtn.setBounds(120, 420, 150, 30);
		ispisiBtn.setBounds(490, 500, 150, 30);
		obrisiBtn.setBounds(120, 490, 150, 30);;
		
		
		opisPlacanjaComboBox.setBounds(230, 320, 130, 30);
		opisPlacanja.addElement("Gotovina");
		opisPlacanja.addElement("Kartica");
		opisPlacanja.addElement("Na rate");
		opisPlacanjaComboBox.setModel(opisPlacanja);
		opisPlacanjaComboBox.setSelectedItem(0);
		
		add(txtArea);
		add(proizvodiLbl);
		add(kolicinaLbl);
		add(kupacLbl);
		add(proizvodiTxt);
		add(kolicinaTxt);
		add(dodajBtn);
		add(opisPlacanjaLbl);
		add(kupacTxt);
		add(opisPlacanjaComboBox);
		
		
		add(potvrdiBtn);
		add(ispisiBtn);
		add(obrisiBtn);
	}
	
	/**
	 * Metoda za aktivaciju komponenti pogleda.
	 */
	
	private void activate() {
		
		// Dohvaćanje proizvoda po unesenom imenu.
		
		
		dodajBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Product pr = Controller.getPorductByName(proizvodiTxt.getText());
				BoughtProduct bp = new BoughtProduct();
				bp.productId = pr.id;
				bp.quantity = Integer.parseInt(kolicinaTxt.getText());
				//lista kupljenih proizvoda 
				bill.boughtproducts.add(bp);
				//dohvaćanje trenutnog datuma
				if(bill.dateOfPrint == null) {
					bill.dateOfPrint = new java.sql.Date(Calendar.getInstance().getTime().getTime());
				}
				txtArea.setText(bill2Text(bill));
			}
		});
		
		
		
		potvrdiBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				bill.buyerName = kupacTxt.getText();
				bill.paymentMethod = (String) opisPlacanjaComboBox.getSelectedItem();
				txtArea.setText(bill2Text(bill));
				
			}
		});
		
		
		ispisiBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Controller.inputBill(bill);
				
				try {
					txtArea.print();
				} catch (PrinterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		obrisiBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				deleteBill4Panel();
				
			}
		});
	}
	
	/**
	 * 
	 */
	
	private String bill2Text(Bill b) {
		String txt = "";
		double price = 0.0;
		txt += "**************************************************\n";
		txt+= b.dateOfPrint + "\n" + "Prodavač : " + seller + " (" + b.userId + ")\n";
		txt += "**************************************************\n";
		txt += "**************************************************\n";
		txt += "\nProizvod\tKoličina\tCijena\n";
		
		for(BoughtProduct bop : b.boughtproducts) {
			Product pr = Controller.getPorductById(bop.productId);
			txt += "\n" + pr.name + "\t" + bop.quantity + "\t" + (bop.quantity * pr.price);
			price += bop.quantity * pr.price;
		}
		txt += "\n**************************************************\n";
		//upit za provjeru je li korisnik sustava unio ime u tekstualno polje za unos imena kupca ili je slučajno stisnuo gumb
		if(!bill.buyerName.equals("")) {
			txt += "\n\nKupac : " + bill.buyerName + "\nOpis plaćanja : " + bill.paymentMethod;
		}
		
		txt += "\n\nUkupno : " + price;
		b.finalPrice = price;
		
		return txt;
	}
	
	
	private void deleteBill4Panel() {
		
		txtArea.setText("");
	}
	

}
