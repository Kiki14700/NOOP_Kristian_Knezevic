package view_pckg;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller_pckg.Controller;
import model_pckg.Product;

/**
 * Klasa za stvaranje, razmještaj i aktivaciju komponenti pogleda "NewProductsPanel".
 * @author Kristian Knežević
 *
 */
public class NewProductsPanel extends JPanel{
	
	private JLabel productNameLbl;
	private JLabel measureLbl;
	private JLabel priceLbl;
	private JLabel dateLbl;
	
	private JTextField productNameTxt;
	private JTextField measureTxt;
	private JTextField priceTxt;
	
	private JDatePickerImpl datePicker;
	private JDatePanelImpl datePanel;
	private UtilDateModel model;
	
	private JButton confirmBtn;
	
	private Product product;
	
	public NewProductsPanel() {
		createComps();
		layoutComps();
		designComps();
		activate();
		product = new Product();
		
	}
	
	/**
	 * Metoda za kreiranje i imenovanje komponenti.
	 */
	private void createComps() {
		productNameLbl = new JLabel("Naziv proizvoda");
		productNameTxt = new JTextField();
		measureLbl = new JLabel("Količina");
		measureTxt = new JTextField();
		priceLbl = new JLabel("Cijena");
		priceTxt = new JTextField();
		dateLbl = new JLabel("Datum unosa");
		confirmBtn = new JButton("Unesi u bazu");
		
		model = new UtilDateModel();
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Danas");
		p.put("text.month", "Mjesec");
		p.put("text.year", "Godina");
		datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
	}
	
	/**
	 * Metoda za dimenzioniranje i pozicioniranje elemenata pogleda.
	 */
	private void layoutComps() {
		setLayout(null);
		
		productNameLbl.setBounds(60, 30, 100, 30);
		productNameTxt.setBounds(40, 70, 130, 30);
		measureLbl.setBounds(330, 30, 100, 30);
		measureTxt.setBounds(290, 70, 130, 30);
		priceLbl.setBounds(85, 160, 100, 30);
		priceTxt.setBounds(40, 200, 130, 30);
		dateLbl.setBounds(315, 160, 100 ,30);
		datePicker.setBounds(290, 200, 130, 30);
		confirmBtn.setBounds(167, 285, 130, 30);
		
		
		add(productNameLbl);
		add(productNameTxt);
		add(measureLbl);
		add(measureTxt);
		add(priceLbl);
		add(priceTxt);
		add(dateLbl);
		add(datePicker);
		add(confirmBtn);
	}
	
	private void designComps() {
		
		confirmBtn.setBackground(Color.YELLOW);
	}
	
	/**
	 * Klasa koja postavlja format u JDatePicker.
	 * 
	 * @author Kristian Knezevic
	 *
	 */
	public class DateLabelFormatter extends AbstractFormatter {

		private String datePattern = "dd/MM/yyyy";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}

			return "";
		}

	}
	
	/**
	 * Metoda za aktivaciju botuna koji podatke o novom proizvodu preko Controllera šalje u bazu podataka.
	 */

	public void activate() {
		
		confirmBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue() ;
				
				product.name = productNameTxt.getText();
				product.price = Double.parseDouble(priceTxt.getText());
				product.measure = measureTxt.getText();
				product.dateOfArrival = selectedDate;
				
				Controller.inputProducts(product);
				
			}
		});
	}
	
	
	
	

}
