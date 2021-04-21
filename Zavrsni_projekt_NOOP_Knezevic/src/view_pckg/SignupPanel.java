package view_pckg;

import java.awt.Color;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.text.DateFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller_pckg.Controller;
import model_pckg.User;
import view_pckg.NewProductsPanel.DateLabelFormatter;

/**
 * Klasa za stvaranje panela(pogleda) na kojoj se nalaze elementi za registraciju novog korinsnika. 
 * @author Kristian Knezevic
 *
 */
public class SignupPanel extends JPanel{
	
	private JLabel namelbl;
	private JLabel surnameLbl;
	private JLabel addressLbl;
	private JLabel phoneNumLbl;
	private JLabel passwordLbl;
	private JLabel confirmPasswordLbl;
	private JLabel dateOfBirthLbl;
	private JLabel uNameLbl;
	private JTextField nameTxt;
	private JTextField surnameTxt;
	private JTextField addressTxt;
	private JTextField phoneNumTxt;
	private JPasswordField passwordTxt;
	private JPasswordField confirmPasswordTxt;
	private JTextField uNameTxt;
	private JButton submitBtn;
	
	private JDatePickerImpl datePicker;
	private JDatePanelImpl datePanel;
	private UtilDateModel model;
	
	public SignupPanel() {
		
		createComps();
		layoutComps();
		activate();
		style();
	}
	
	/**
	 * Metoda za kreiranje i imenovanje komponenti prikazanih na pogledu.
	 */
	private void createComps() {
		namelbl = new JLabel("Unesite ime");
		nameTxt = new JTextField();
		surnameLbl = new JLabel("Unesite prezime");
		surnameTxt = new JTextField();
		addressLbl = new JLabel("Unesite adresu");
		addressTxt = new JTextField();
		phoneNumLbl = new JLabel("Unesite broj telefona");
		phoneNumTxt = new JTextField();
		passwordLbl = new JLabel("Unesite lozinku");
		passwordTxt = new JPasswordField();
		confirmPasswordLbl = new JLabel("Potvrda lozinke");
		confirmPasswordTxt = new JPasswordField();
		dateOfBirthLbl = new JLabel("Datum rodenja");
		
		uNameLbl = new JLabel("Korisnicko ime");
		uNameTxt = new JTextField();
		submitBtn = new JButton("Potvrdi");
		
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
	 * Metoda za dimenzioniranje i razmjestaj elemenata na pogledu.
	 */
	private void layoutComps() {
		setLayout(null);
		
		namelbl.setBounds(80, 50, 100, 30);
		nameTxt.setBounds(30, 90, 170, 30);
		surnameLbl.setBounds(280, 50, 100, 30);
		surnameTxt.setBounds(240, 90, 170, 30);
		addressLbl.setBounds(490, 50, 100, 30);
		addressTxt.setBounds(450, 90, 170, 30);
		phoneNumLbl.setBounds(55, 270, 150, 30);
		phoneNumTxt.setBounds(30, 310, 170, 30);
		passwordLbl.setBounds(170, 160, 100, 30);
		passwordTxt.setBounds(130, 200, 170, 30);
		confirmPasswordLbl.setBounds(380, 160, 100, 30);
		confirmPasswordTxt.setBounds(340, 200, 170, 30);
		dateOfBirthLbl.setBounds(280, 270, 100, 30);
		datePicker.setBounds(240, 310, 170, 30);
		uNameLbl.setBounds(490, 270, 100, 30);
		uNameTxt.setBounds(450, 310, 170, 30);
		submitBtn.setBounds(260, 400, 130, 30);
		
		add(namelbl);
		add(nameTxt);
		add(surnameLbl);
		add(surnameTxt);
		add(addressLbl);
		add(addressTxt);
		add(phoneNumLbl);
		add(phoneNumTxt);
		add(passwordLbl);
		add(passwordTxt);
		add(submitBtn);
		add(confirmPasswordLbl);
		add(confirmPasswordTxt);
		add(dateOfBirthLbl);
		add(datePicker);
		add(uNameLbl);
		add(uNameTxt);
	}
	
	/**
	 * Stilizacija pogleda i njegovih komponenti.
	 */
	
	private void style() {
		submitBtn.setBackground(Color.CYAN);
		datePicker.setBackground(Color.CYAN);
	}
	
	/**
	 * Klasa koja postavlja format u JDatePicker
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
	 * Metoda za aktivaciju botuna koji podatke o novom korisiku preko Controllera salje u bazu podataka.
	 */
	private void activate() {
		
		
			
		submitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
				User user = new User();
				
				if(nameTxt.getText().equals("") || surnameTxt.getText().equals("") || addressTxt.getText().equals("") || 
						phoneNumTxt.getText().equals("") || passwordTxt.getPassword().equals("") || uNameTxt.getText().equals("") )  {
					JOptionPane.showMessageDialog(new JFrame(), "Niste popunili sva polja!");
				}else {
				
				if(Controller.getUserByUsername(uNameTxt.getText()) != null) {
					JOptionPane.showMessageDialog(new JFrame(), "Korisnik sa unesenim korisnickim imenom vec postoji!");
				}else if(new String(confirmPasswordTxt.getPassword()).equals(new String(passwordTxt.getPassword()))) {
					user.name = nameTxt.getText();
					user.surname = surnameTxt.getText();
					user.address = addressTxt.getText();
					user.phoneNum = Integer.parseInt(phoneNumTxt.getText());
					user.setPassword(new String(passwordTxt.getPassword()));
					user.dateOfBirth = selectedDate;
					user.username = uNameTxt.getText();
					Controller.signup(user);
				}else {
					JOptionPane.showMessageDialog(new JFrame(), "Lozinke se ne podudaraju!");
				}
				
			}
			}
		
		});
		
	}
	
	

}
