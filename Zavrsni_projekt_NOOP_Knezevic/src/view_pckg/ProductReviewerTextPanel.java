package view_pckg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Klasa za stvaranje tekstualnog polja u polgedu za prikaz podataka o proizvodu.
 * @author Kristian Knezevic
 *
 */
public class ProductReviewerTextPanel extends JPanel{
	
	public static JTextArea txtArea;
	private JScrollPane scPane;
	
	public ProductReviewerTextPanel() {
		
		setLayout(new BorderLayout());
		txtArea = new JTextArea();
		txtArea.setFont(new Font("monospaced", Font.PLAIN, 13));
		txtArea.setTabSize(20);
		scPane = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		Dimension dim = getPreferredSize();
		dim.height = 300;
		setPreferredSize(new Dimension(550, 250));

		add(scPane);
	}
	
	

}
