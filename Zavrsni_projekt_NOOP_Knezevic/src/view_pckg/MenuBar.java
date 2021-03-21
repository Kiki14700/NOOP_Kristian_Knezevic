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
	
	public void initializeComps() {
		
		menuBar = new JMenuBar();
		menuEdit = new JMenu("Više");
		djelatniciBtn = new JMenuItem("Djelatnici");
		prometBtn = new JMenuItem("Kretanje prometa");
		unosProizvodaBtn = new JMenuItem("Unos proizvoda");
	}
	
	public void addComps() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		menuBar.add(menuEdit);
		add(menuBar);
		menuEdit.add(djelatniciBtn);
		menuEdit.add(prometBtn);
		menuEdit.add(unosProizvodaBtn);
		
		
	}
	
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
