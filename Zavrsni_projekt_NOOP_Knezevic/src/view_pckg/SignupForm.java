package view_pckg;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class SignupForm extends JFrame{
	
	private SignupPanel signupPanel;
	
	public SignupForm() {
		
		super("Signin");
		setSize(470, 1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		
		initializeComps();
		layoutComps();
		
	}
	
	private void initializeComps() {
		
		signupPanel = new SignupPanel();
	}
	
	private void layoutComps() {
		setLayout(new BorderLayout());
		
		add(signupPanel, BorderLayout.CENTER);
	}

}
