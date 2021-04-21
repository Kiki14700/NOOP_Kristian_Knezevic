package view_pckg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import controller_pckg.Controller;

/**
 * Klasa za stvaranje dijela pogleda za odabir kupca i pretragu informacija o njemu.
 * @author Kristian Knezevic
 *
 */
public class BuyerReviewerPanel extends JPanel{
	
	private JComboBox<String> comboProducts;
	private JButton OKBtn;
	private JLabel searchLlb;
	
	public BuyerReviewerPanel() {
		createComps();
		layoutComps();
		activate();
		setBorders();
	}
	
	private void setBorders() {

		Border inner = BorderFactory.createTitledBorder("");
		Border outer = BorderFactory.createEmptyBorder(30, 50, 30, 50);
		Border end = BorderFactory.createCompoundBorder(outer, inner);
		setBorder(end);
	

	}
	
	/**
	 * Metoda za kreiranje komponenti pogleda.
	 */
	private void createComps() {
		
		comboProducts = new JComboBox<String>();
		DefaultComboBoxModel<String> defCombo = new DefaultComboBoxModel<String>();
		
		for(String product : Controller.getAllBuyers()) {
			defCombo.addElement(product);
		}
		
		comboProducts.setModel(defCombo);
		
		OKBtn = new JButton("Pregledaj");
		searchLlb = new JLabel("Odaberi kupca");
	}
	
	/**
	 * Metoda za razmještaj komponenti na pogledu.
	 */
	private void layoutComps() {
		setLayout(null);
		searchLlb.setBounds(230, 40, 100, 20);
		comboProducts.setBounds(190, 70, 170, 30);
		OKBtn.setBounds(210, 130, 130, 30);
		
		add(searchLlb);
		add(comboProducts);
		add(OKBtn);
		
	}
	
	/**
	 * Metoda za aktivaciju dugmadi
	 */
	private void activate() {
		
		OKBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				ArrayList<String> res = Controller.getBuyerInfo(comboProducts.getSelectedItem().toString());
				BuyerReviewerTextPanel.txtArea.setText("");
				
				for (String r : res) {
					BuyerReviewerTextPanel.txtArea.append(r + "\n");
				}
				
			}
		});
	}

}
