package view_pckg;

import java.awt.BorderLayout;

import javax.swing.JFrame;


/**
 * Klasa za stavranje prozora(okvira) na kojemu se nalazi pogled s komponentama koje omogućuju unos novog proizvoda.
 * @author Kristian Knezevic
 *
 */

public class NewProductsForm extends JFrame{
	
	private NewProductsPanel newProductsPanel;
	
	public NewProductsForm() {
		
		super("Unos proizvoda");
		setSize(500, 400);
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
		newProductsPanel = new NewProductsPanel();
	}
	
	/**
	 * Metoda za pozicioniranje pogleda.
	 */
	private void layoutComps() {
		
		setLayout(new BorderLayout());
		add(newProductsPanel, BorderLayout.CENTER);
		
		
	}

}
