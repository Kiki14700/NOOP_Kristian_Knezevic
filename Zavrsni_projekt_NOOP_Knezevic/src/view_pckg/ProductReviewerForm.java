package view_pckg;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * Klasa za stvaranje pogleda za prikaz podataka po traženom proizvodu
 * @author Kristian Knezevic
 *
 */
public class ProductReviewerForm extends JFrame{
	
	private ProductReviewerTextPanel productRewieverTextPanel;
	private ProductReviewerPanel productRewieverPanel;
	
	public ProductReviewerForm() {
		super("Pregled po proizvodu");
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
		productRewieverTextPanel = new ProductReviewerTextPanel();
		productRewieverPanel = new ProductReviewerPanel();
	}
	
	/**
	 * Metoda za pozicioniranje pogleda.
	 */
	private void layoutComps() {
		
		setLayout(new BorderLayout());
		add(productRewieverTextPanel, BorderLayout.SOUTH);
		add(productRewieverPanel, BorderLayout.CENTER);
		
		
	}

}
