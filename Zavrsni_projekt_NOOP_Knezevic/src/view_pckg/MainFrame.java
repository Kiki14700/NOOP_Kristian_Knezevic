package view_pckg;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

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
	
	
	private void initilizeCopms() {
		
		formPanel = new FormPanel();
		menuBar = new MenuBar();
		menuBar.setBackground(Color.DARK_GRAY);
	}
	
	private void layoutComps() {
		setLayout(new BorderLayout());
		add(formPanel, BorderLayout.WEST);
		add(menuBar, BorderLayout.NORTH);
	}
	
	
	
	
}
