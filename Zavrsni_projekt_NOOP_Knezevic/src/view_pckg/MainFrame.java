package view_pckg;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

/**
 * Klasa glavnog prozora(okvira) na kojemu se nalazi glavni pogled za prodaju i Menu Bar.
 * @author Kristian Knezevic
 *
 */
public class MainFrame extends JFrame{
	
	
	private FormPanel formPanel;
	private MenuBar menuBar;
	
	public MainFrame() {
		super("OPG Knežević");
		setSize(760, 630);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		
		initilizeCopms();
		layoutComps();
	
	}
	
	/**
	 * Metoda za inicijalizaciju pogleda koji se nalaze na MainFrame-u.
	 */
	private void initilizeCopms() {
		
		formPanel = new FormPanel();
		menuBar = new MenuBar();
		menuBar.setBackground(Color.DARK_GRAY);
	}
	
	/**
	 * Metoda za pozicioniranje komponenti tj. pogleda. 
	 */
	
	private void layoutComps() {
		setLayout(new BorderLayout());
		add(formPanel, BorderLayout.WEST);
		add(menuBar, BorderLayout.NORTH);
	}
	
	

	
}
