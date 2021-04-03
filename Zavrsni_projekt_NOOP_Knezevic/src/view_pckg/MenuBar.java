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
 * Klasa s padajućim izbornikom u kojemu se nalaze botuni koji korisnika vode do uvida u djelatnike, kretanje prometa te mogućnosti za unos novih proizvoda.
 * @author Korisnik
 *
 */
public class MenuBar extends JPanel{
	
	private JMenuBar menuBar;
	private JMenu menuEdit;
	private JMenuItem djelatniciBtn;
	private JMenuItem prometBtn;
	private JMenuItem unosProizvodaBtn;
	
	
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
	}
	
	/**
	 * Metoda za dodavanje botuna u izbornik pod nazivom "Više".
	 */
	public void addComps() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		menuBar.add(menuEdit);
		add(menuBar);
		menuEdit.add(djelatniciBtn);
		menuEdit.add(prometBtn);
		menuEdit.add(unosProizvodaBtn);
		
		
	}
	
	/**
	 * Metoda za aktivaciju botuna.
	 * Botun djelatnici vodi korisnika do uvida u djeltanike, botun promet do grafa koji prikazuje kretanje prometa po datumima, a botun unos proizvoda do mogućnosti za unos novih prizvoda u bazu podataka.
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
				
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						
						Controller.newProductsView();
					}
				});
				
			}
		});
		
		
	}

}
