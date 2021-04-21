package view_pckg;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * Klasa za kreiranje pogleda za pretragu informacija o kupcu.
 * @author Korisnik
 *
 */
public class BuyerReviewerForm extends JFrame{
	
	private BuyerReviewerTextPanel buyerReviewerTextPanel;
	private BuyerReviewerPanel buyerReviewerPanel;
	
	public BuyerReviewerForm() {
		super("Pregled po kupcu");
		setSize(550, 500);
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
		buyerReviewerTextPanel = new BuyerReviewerTextPanel();
		buyerReviewerPanel = new BuyerReviewerPanel();
	}
	
	/**
	 * Metoda za pozicioniranje pogleda.
	 */
	private void layoutComps() {
		
		setLayout(new BorderLayout());
		add(buyerReviewerTextPanel, BorderLayout.SOUTH);
		add(buyerReviewerPanel, BorderLayout.CENTER);
	}

}
