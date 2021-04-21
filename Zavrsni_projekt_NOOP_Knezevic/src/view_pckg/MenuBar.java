package view_pckg;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jdatepicker.impl.JDatePickerImpl;

import controller_pckg.Controller;
import model_pckg.Product;

/**
 * Klasa s padajucim izbornikom u kojemu se nalaze botuni koji korisnika vode do uvida u djelatnike, kretanje prometa te mogucnosti za unos novih proizvoda.
 * @author Kristian Knezevic
 *
 */
public class MenuBar extends JPanel{
	
	private JMenuBar menuBar;
	private JMenu menuEdit;
	private JMenu menuMore;
	private JMenuItem djelatniciBtn;
	private JMenuItem prometBtn;
	private JMenuItem unosProizvodaBtn;
	private JMenuItem dateBtn;
	private JMenuItem productbtn;
	private JMenuItem buyerBtn;
	
	public MenuBar() {
		initializeComps();
		addComps();
		activate();
		
		
	}
	/**
	 * Metoda za inicijalizaciju komponenti
	 */
	public void initializeComps() {
		
		menuBar = new JMenuBar();
		menuEdit = new JMenu("Više");
		djelatniciBtn = new JMenuItem("Djelatnici");
		prometBtn = new JMenuItem("Kretanje prometa");
		unosProizvodaBtn = new JMenuItem("Unos proizvoda");
		
		menuMore = new JMenu("Pregled");
		dateBtn = new JMenuItem("Po datumu");
		productbtn = new JMenuItem("Po proizvodu");
		buyerBtn = new JMenuItem("Po kupcu");
	}
	
	/**
	 * Metoda za dodavanje botuna u izbornik pod nazivom "Vise".
	 */
	public void addComps() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		menuBar.add(menuEdit);
		menuBar.add(menuMore);
		add(menuBar);
		menuEdit.add(djelatniciBtn);
		menuEdit.add(prometBtn);
		menuEdit.add(unosProizvodaBtn);
		
		menuMore.add(dateBtn);
		menuMore.add(productbtn);
		menuMore.add(buyerBtn);
		
	}
	
	/**
	 * Metoda za aktivaciju botuna.
	 * Botun djelatnici vodi korisnika do uvida u djeltanike, botun promet do grafa koji prikazuje kretanje prometa po datumima, a botun unos proizvoda do mogucnosti za unos novih prizvoda u bazu podataka.
	 */
	
	public void activate() {
		
		djelatniciBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Controller.employeesView();
					}
				});
				
			}
		});
		
		prometBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Controller.chartView();
				
				
			}
		});
		
		unosProizvodaBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
										
				Controller.newProductsView();
				
				
			}
		});
		
		dateBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.dateReviewView();
				
			}
		});
		
		productbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Controller.productRewievView();
				
			}
		});
		
		buyerBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.buyerReviewView();
				
			}
		});
		
		
	}

}
