package view_pckg;

import java.awt.BorderLayout;

import javax.swing.JFrame;

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
	
	private void initializeComps() {
		loginPanel = new LoginPanel();
		
	}
	
	private void layoutComps() {
		setLayout(new BorderLayout());
		add(loginPanel, BorderLayout.CENTER);
		
	}

}
