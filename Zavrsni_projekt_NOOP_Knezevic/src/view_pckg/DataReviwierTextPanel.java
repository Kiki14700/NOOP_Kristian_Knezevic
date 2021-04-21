package view_pckg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import controller_pckg.Controller;
import model_pckg.Bill;
import model_pckg.BoughtProduct;

/**
 * Klasa za stvaranje tekstualnog polja u pogledu za prikaz podataka po datumu.
 * @author Kristian Knezevic
 *
 */
public class DataReviwierTextPanel extends JPanel{
	
	private static JTextArea txtArea;
	private JScrollPane scPane;
	
	public DataReviwierTextPanel() {
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
	
	/**
	 * Metoda za oblikovanje teksta koji se postavlja na tekstualno podrucje
	 */
	public static void result2Txt(ArrayList<Bill> bills) {
		
		String txt = "Datum\tKupac\tKupljeni proizvod\tKolicina\tCijena\n";
		
		for (Bill bill : bills) {
			for (BoughtProduct bp : bill.boughtproducts) {
				txt += bill.dateOfPrint + "\t" + bill.buyerName + "\t" + Controller.getPorductById(bp.productId).name + "\t" + bp.quantity + "\t" + 
						Controller.getPorductById(bp.productId).price + "\n";
			} 
		}
		
		txtArea.setText(txt);
		
	}
	
	

}
