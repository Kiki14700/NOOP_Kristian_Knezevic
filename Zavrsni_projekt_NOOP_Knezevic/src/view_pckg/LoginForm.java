package view_pckg;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * Metoda za kreiranje forme(okvira) na kojoj se nalazi pogled s komponentama za prijavu korisnika.
 * @author Kristian Knežević
 *
 */

public class LoginForm extends JFrame{
	
	private LoginPanel loginPanel;
	
	public LoginForm() {
		
		super("Login");
		setSize(700, 550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	
		initializeComps();
		layoutComps();
		
	}
	
	/**
	 * Metoda za inicijalizaciju pogleda koji se postavlja na ovu formu.
	 */
	private void initializeComps() {
		loginPanel = new LoginPanel();
		
	}
	
	/**
	 * Metoda za pozicioniranje pogleda.
	 */
	private void layoutComps() {
		setLayout(new BorderLayout());
		add(loginPanel, BorderLayout.CENTER);
		
	}

}
