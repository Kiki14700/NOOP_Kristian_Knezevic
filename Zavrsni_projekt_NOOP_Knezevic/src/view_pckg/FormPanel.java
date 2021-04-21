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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Utilities;

import org.jdatepicker.JDatePanel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller_pckg.Controller;
import model_pckg.Bill;
import model_pckg.BoughtProduct;
import model_pckg.DataBase;
import model_pckg.Product;



/**
 * U klasi FormPanel stvoreni su i aktivirani elementi glavnog pogleda u kojem se odvija prodaja proizvoda.
 * Klasa omogucuje izradu racuna, spremanje racuna u bazu podataka(obavlja Controller) i ispis racuna(pomoću pisaca).
 * @author Kristian Knezevic
 */

public class FormPanel extends JPanel{
	
	private JTextArea txtArea;
	
	private JLabel proizvodiLbl;
	private JLabel kolicinaLbl;
	private JLabel kupacLbl;
	private JLabel opisPlacanjaLbl;
	private JLabel datumLbl;
	

	private JTextField kolicinaTxt;
	private JTextField kupacTxt;
	
	private JButton dodajBtn;
	private JButton potvrdiBtn;
	private JButton ispisiBtn;
	private JButton obrisiBtn;
	private JComboBox<String> opisPlacanjaComboBox;;
	private DefaultComboBoxModel<String> opisPlacanja;
	private JComboBox<String> comboProizvodi;
	
	private Bill bill;
	private String seller;
	
	
	public FormPanel() {
		System.out.println(Controller.currentUser);
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
		kolicinaLbl = new JLabel("Kolicina");
		kupacLbl = new JLabel("Ime Kupca");
		opisPlacanjaLbl = new JLabel("Opis placanja");
		datumLbl = new JLabel("Datum kupnje");
		
		kolicinaTxt = new JTextField();
		kupacTxt = new JTextField();
		dodajBtn = new JButton("Dodaj");
		potvrdiBtn = new JButton("Potvrdi");
		ispisiBtn = new JButton("Ispisi");
		opisPlacanjaComboBox = new JComboBox<>();
		opisPlacanja = new DefaultComboBoxModel<>();
		obrisiBtn = new JButton("Obrisi");
		
		comboProizvodi = new JComboBox<String>();
		DefaultComboBoxModel<String> defCombo = new DefaultComboBoxModel<String>();
		
		for(String product : Controller.getAllProducts()) {
			defCombo.addElement(product);
		}
		
		comboProizvodi.setModel(defCombo);
		
	}
	
	/**
	 * Metoda za razmjestaj komponenti na pogledu te postavljanje dimenzija samih komponenti.
	 */
	
	private void layoutComps() {
		setLayout(null);
		
		txtArea.setBounds(400, 10, 330, 470);
		proizvodiLbl.setBounds(75, 100, 150, 15);
		kolicinaLbl.setBounds(270, 100, 150, 15);
		kupacLbl.setBounds(75, 290, 150, 15);
		comboProizvodi.setBounds(40, 130, 130, 30);
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
		add(comboProizvodi);
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
		
		// Dohvacanje proizvoda po unesenom imenu.
		
		
		dodajBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(kolicinaTxt.getText().equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), "Niste unijeli kolicinu!");
				}else if(!kolicinaTxt.getText().matches("[0-9]")) {
					JOptionPane.showMessageDialog(new JFrame(), "Unesite ispravne podatke!");
					kolicinaTxt.setText("");
				}else {
					Product pr = Controller.getPorductByName(comboProizvodi.getSelectedItem().toString());
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
				
			}
		});
		
		
		
		potvrdiBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(kupacTxt.getText().equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), "Niste unijeli ime kupca!");
				}else {
					
					if(Controller.getBuyer(kupacTxt.getText())) {
						int dialogResult = JOptionPane.showConfirmDialog(new JFrame(), "Je li ovo postojeci kupac?" + kupacTxt.getText());
						int index = 0;
						if(dialogResult == JOptionPane.YES_OPTION) {
		
							
						}
						while(dialogResult == JOptionPane.NO_OPTION){
							kupacTxt.setText(kupacTxt.getText() + index);
							if(Controller.getBuyer(kupacTxt.getText())) {
								dialogResult = JOptionPane.showConfirmDialog(new JFrame(), "Je li ovo postojeci kupac?" + kupacTxt.getText());
								if(dialogResult == JOptionPane.YES_OPTION) {
								
									
								}
								
							}else {
								break;
							}
							index++;
						}
						
					}
					bill.buyerName = kupacTxt.getText();
					bill.paymentMethod = (String) opisPlacanjaComboBox.getSelectedItem();
					txtArea.setText(bill2Text(bill));
					
				}
			
				
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
	 * Metoda za oblikovanje prikaza teksta namjenjenog za prikaz u tekstualnom podrucju.
	 */
	
	private String bill2Text(Bill b) {
		String txt = "";
		double price = 0.0;
		txt += "**************************************************\n";
		txt+= b.dateOfPrint + "\n" + "Prodavac : " + seller + " (" + b.userId + ")\n";
		txt += "**************************************************\n";
		txt += "**************************************************\n";
		txt += "\nProizvod\tKolicina\tCijena\n";
		
		for(BoughtProduct bop : b.boughtproducts) {
			Product pr = Controller.getPorductById(bop.productId);
			txt += "\n" + pr.name + "\t" + bop.quantity + "\t" + (bop.quantity * pr.price);
			price += bop.quantity * pr.price;
		}
		txt += "\n**************************************************\n";
		//upit za provjeru je li korisnik sustava unio ime u tekstualno polje za unos imena kupca ili je slucajno stisnuo gumb
		if(!bill.buyerName.equals("")) {
			txt += "\n\nKupac : " + bill.buyerName + "\nOpis placanja : " + bill.paymentMethod;
		}
		
		txt += "\n\nUkupno : " + price;
		b.finalPrice = price;
		
		return txt;
	}
	
	/**
	 * Metoda za brisanje oznacenog reda u text area
	 */
	private void deleteBill4Panel() {
		if(bill.boughtproducts.size() > 0) {
			
	             bill.boughtproducts.remove(bill.boughtproducts.size() - 1 );
	             txtArea.setText(bill2Text(bill));
		}
		
     }
	

}
