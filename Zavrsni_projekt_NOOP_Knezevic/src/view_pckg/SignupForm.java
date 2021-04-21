package view_pckg;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

/**
 * Klasa za stvaranje prozora(okvira) na kojemu se nalazi pogled s komponentama za registraciju novog korisnika. 
 * @author Kristian Knezevic 
 *
 */
public class SignupForm extends JFrame{
	
	private SignupPanel signupPanel;
	
	public SignupForm() {
		
		super("SignUp");
		setSize(665, 500);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		
		initializeComps();
		layoutComps();
		
	}
	
	/**
	 * Metoda za inicijalizaciju pogleda.
	 */
	private void initializeComps() {
		
		signupPanel = new SignupPanel();
		
	}
	
	/**
	 * Metoda za pozicinoranje pogleda na okviru.
	 */
	private void layoutComps() {
		setLayout(new BorderLayout());
		
		add(signupPanel, BorderLayout.CENTER);
	}

}
