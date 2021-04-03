package view_pckg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import controller_pckg.Controller;

/**
 * Metoda za stvaranje pogleda za prijavu.
 * U uvoj metodi inicijalizirani su, imenovani, pozicionirani i aktivirani elementi pogleda za prijavu.
 * @author Kristian Knežević
 *
 */
public class LoginPanel extends JPanel{
	
	private JLabel unameLbl;
	private JLabel passwordLbl;
	private JTextField unameTxt;
	private JPasswordField passwordTxt;
	private JButton loginBtn;
	private JButton signupBtn;
	
	
	
	public LoginPanel() {
		
		createComps();
		layoutComps();
		activate();
		setBorders();
		
		
	
	}
	
	/**
	 * Metoda za kreiranje komponenti pogleda i njihovo imenovanje.
	 */
	private void createComps() {
		unameLbl = new JLabel("Unesite ime");
		unameTxt = new JTextField();
		passwordLbl = new JLabel("Unesite lozinku");
		passwordTxt = new JPasswordField();
		loginBtn = new JButton("Prijavi se");
		signupBtn = new JButton("Registriraj se");
	}
	
	/**
	 * Metoda za razmještaj komponenti.
	 */
	private void layoutComps() {
		setLayout(null);
		
		unameLbl.setBounds(310, 110, 100, 30);
		loginBtn.setBounds(280, 400, 130, 30);
		signupBtn.setBounds(260, 450, 170, 30);
		unameTxt.setBounds(260, 160, 170, 30);
		passwordLbl.setBounds(300, 220, 100, 30);
		passwordTxt.setBounds(260, 270, 170, 30);
	
		add(unameLbl);
		add(loginBtn);
		add(signupBtn);
		add(unameTxt);
		add(passwordLbl);
		add(passwordTxt);
	}
	
	
	private void setBorders() {

		Border inner = BorderFactory.createTitledBorder("");
		Border outer = BorderFactory.createEmptyBorder(50, 100, 150, 100);
		Border end = BorderFactory.createCompoundBorder(outer, inner);
		setBorder(end);
	

	}
	
	/**
	 * Metoda za aktivaciju komponenti pogleda za prijavu.
	 * Aktivirani su botuni za prijavu korisnika i otvaranje panela za regitraciju novog korisnika.
	 * Botun za prijavu nakon unosa podataka poziva metodu Login iz Controllera koja provejerava točnost podataka.
	 */
	
	private void activate() {
		
		
		loginBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						Controller.login(unameTxt.getText(), new String(passwordTxt.getPassword()));
						
					}
				});
				
			}
		});
		
		signupBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						Controller.registrationFormView();
						
					}
				});
				
			}
		});
		
		
	}

}
