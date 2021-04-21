package view_pckg;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JPanel;

import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller_pckg.Controller;
import model_pckg.Bill;
import view_pckg.SignupPanel.DateLabelFormatter;

/**
 * Klasa za stvaranje dijela pogleda s poljima sa odabir datuma i dugmetom za akciju.
 * @author Kristian Knezevic
 *
 */
public class DateReviewerPanel extends JPanel{
	
	private JLabel dateFromLbl;
	private JLabel dateToLbl;
	
	private JDatePickerImpl datePickerFrom;
	private JDatePickerImpl datePickerTo;
	private JDatePanelImpl datePanelFrom;
	private JDatePanelImpl datePanelTo;
	private UtilDateModel modelFrom;
	private UtilDateModel modelTo;
	
	private JButton searchBtn;
	
	public DateReviewerPanel() {
		createComps();
		layoutComps();
		activate();
		setBorders();
		
	}
	
	/**
	 * Metoda za kreiranje komponenti pogleda.
	 */
	private void createComps() {
		
		dateFromLbl = new JLabel("Datum od:");
		dateToLbl  = new JLabel("Datum do:");
		
		modelFrom = new UtilDateModel();
		modelFrom.setSelected(true);
		modelTo = new UtilDateModel();
		modelTo.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Danas");
		p.put("text.month", "Mjesec");
		p.put("text.year", "Godina");
		datePanelFrom = new JDatePanelImpl(modelFrom, p);
		datePanelTo = new JDatePanelImpl(modelTo, p);
		datePickerFrom = new JDatePickerImpl(datePanelFrom, new DateLabelFormatter());
		datePickerTo = new JDatePickerImpl(datePanelTo, new DateLabelFormatter());
		
		searchBtn = new JButton("OK");
	}
	
	/**
	 * Metoda za razmještaj komponenti na pogledu.
	 */
	private void layoutComps() {
		
		setLayout(null);
		dateFromLbl.setBounds(170, 40, 100, 20);
		dateToLbl.setBounds(450, 40, 100, 20);
		datePickerFrom.setBounds(120, 80, 170, 30);
		datePickerTo.setBounds(400, 80, 170, 30);
		searchBtn.setBounds(295, 130, 100, 30);
		
		add(datePickerFrom);
		add(dateFromLbl);
		add(dateToLbl);
		add(datePickerTo);
		add(searchBtn);
	}
	
	private void setBorders() {

		Border inner = BorderFactory.createTitledBorder("");
		Border outer = BorderFactory.createEmptyBorder(30, 50, 30, 50);
		Border end = BorderFactory.createCompoundBorder(outer, inner);
		setBorder(end);
	

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
	 * Metoda za aktivaciju dugmadi.
	 */
	private void activate() {
		
		searchBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				java.util.Date selectedDate1 = (java.util.Date) datePickerFrom.getModel().getValue();
				java.util.Date selectedDate2 = (java.util.Date) datePickerTo.getModel().getValue();
				
				 
			    DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");  
			    String strDate = dateFormat.format(selectedDate1); 
			    String strDate2 = dateFormat.format(selectedDate2); 
				
				if(selectedDate1.getTime() > selectedDate2.getTime()) {
					JOptionPane.showMessageDialog(new JFrame(), "POGREŠKA -> drugi datum mora biti veći od prvoga!");
				}else {
					ArrayList<Bill> bills = Controller.getBillByDate(strDate, strDate2);
					DataReviwierTextPanel.result2Txt(bills);
				}
				
			}
		});
	}

}
