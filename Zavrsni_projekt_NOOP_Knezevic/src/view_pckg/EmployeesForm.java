package view_pckg;


import java.awt.FlowLayout;


import javax.swing.JFrame;

import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import controller_pckg.Controller;
import model_pckg.User;

/**
 * Klasa u kojoj su tablicno prikazani podatci o registriranim korisnicima
 * @author Kristian Knezevic
 *
 */

public class EmployeesForm extends JFrame{

	
	JTable table;
	
	/**
	 * Tablica s podatcima stvorena u kontruktoru.
	 * Za izradu tablice potrebnno je izraditi redove i stupce.
	 * Podatci su dohvaceni iz baze podataka u klasi Controller, spremljeni u listu koja je proslijedena do klase EmplyeesForm gdje su i prikazani.
	 */
	
	public EmployeesForm() {
		super("Zaposlenici");
		setSize(700, 500);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		DefaultTableModel model = new DefaultTableModel();
		table = new JTable(model);
				
		model.addColumn("Username");
		model.addColumn("Name");
		model.addColumn("Surname");
		model.addColumn("Phone number");
		model.addColumn("Address");
		model.addColumn("Date of Birth");
		
		
		model.insertRow(0, new Object[]{"Korisnicko ime", "Ime", "Prezime", "Broj telefone", "Adresa", "Datum rođenja"});
		int cnt = 1;
		for ( User u : Controller.getUser()) {
			model.insertRow(1, new Object[]{u.username, u.name, u.surname, u.phoneNum, u.address, u.dateOfBirth});
			cnt++;
		}
		table.setRowHeight(this.getHeight()/(cnt + 1));
		table.getColumnModel().getColumn(0).setPreferredWidth(this.getWidth() / 7);
		table.getColumnModel().getColumn(1).setPreferredWidth(this.getWidth() / 7);
		table.getColumnModel().getColumn(2).setPreferredWidth(this.getWidth() / 7);
		table.getColumnModel().getColumn(3).setPreferredWidth(this.getWidth() / 7);
		table.getColumnModel().getColumn(4).setPreferredWidth(this.getWidth() / 7);
		table.getColumnModel().getColumn(5).setPreferredWidth(this.getWidth() / 7);
		this.add(table);
		
		
		setLayout(new FlowLayout());
	}
	
	
	
	
	


}
