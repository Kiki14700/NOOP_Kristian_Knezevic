package view_pckg;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * Klasa za stvaranje pogleda za prikaz podataka po trazenom razdoblju.
 * @author Kristian Knezevic
 *
 */
public class DateReviewerForm extends JFrame{
	
	private DateReviewerPanel dateReviewerPanel;
	private DataReviwierTextPanel dataRewierTextPanel;
	
	public DateReviewerForm() {
		super("Pregled po datumu");
		setSize(700, 500);
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
		dateReviewerPanel = new DateReviewerPanel();
		dataRewierTextPanel = new DataReviwierTextPanel();
	}
	
	/**
	 * Metoda za pozicioniranje pogleda.
	 */
	private void layoutComps() {
		
		setLayout(new BorderLayout());
		add(dateReviewerPanel, BorderLayout.CENTER);
		add(dataRewierTextPanel, BorderLayout.SOUTH);
		
		
	}

}
