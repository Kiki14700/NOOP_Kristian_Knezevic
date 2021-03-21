package view_pckg;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
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
	}
	
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
		dateOfBirthLbl = new JLabel("Datum rođenja");
		
		uNameLbl = new JLabel("Korisničko ime");
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
	
	private void layoutComps() {
		setLayout(null);
		
		namelbl.setBounds(193, 30, 100, 30);
		nameTxt.setBounds(140, 70, 170, 30);
		surnameLbl.setBounds(180, 140, 100, 30);
		surnameTxt.setBounds(140, 180, 170, 30);
		addressLbl.setBounds(180, 250, 100, 30);
		addressTxt.setBounds(140, 290, 170, 30);
		phoneNumLbl.setBounds(165, 360, 150, 30);
		phoneNumTxt.setBounds(140, 400, 170, 30);
		passwordLbl.setBounds(180, 470, 100, 30);
		passwordTxt.setBounds(140, 510, 170, 30);
		confirmPasswordLbl.setBounds(180, 580, 100, 30);
		confirmPasswordTxt.setBounds(140, 620, 170, 30);
		dateOfBirthLbl.setBounds(180, 690, 100, 30);
		datePicker.setBounds(140, 730, 170, 30);
		uNameLbl.setBounds(180, 800, 100, 30);
		uNameTxt.setBounds(140, 840, 170, 30);
		submitBtn.setBounds(160, 920, 130, 30);
		
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
	
	private void activate() {
		
		submitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
                
				User user = new User();
				user.name = nameTxt.getText();
				user.surname = surnameTxt.getText();
				user.address = addressTxt.getText();
				user.phoneNum = Integer.parseInt(phoneNumTxt.getText());
				user.setPassword(new String(passwordTxt.getPassword()));
				user.dateOfBirth = selectedDate;
				user.username = uNameTxt.getText();
				
				Controller.signup(user);
				
			}
		});
	}
	
	

}
