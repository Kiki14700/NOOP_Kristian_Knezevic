package view_pckg;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class NewProductsForm extends JFrame{
	
	private NewProductsPanel newProductsPanel;
	
	public NewProductsForm() {
		
		super("Unos proizvoda");
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		
		initializeComps();
		layoutComps();
	}
	
	private void initializeComps() {
		newProductsPanel = new NewProductsPanel();
	}
	
	private void layoutComps() {
		
		setLayout(new BorderLayout());
		add(newProductsPanel, BorderLayout.CENTER);
		
		
	}

}
