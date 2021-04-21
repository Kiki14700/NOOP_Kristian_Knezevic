package main_pckg;

import java.sql.SQLException;

import javax.swing.SwingUtilities;

import controller_pckg.Controller;
import model_pckg.DataBase;
import view_pckg.LoginForm;

public class Main {
	
	public static void main(String[] args) {
		
		DataBase db = new DataBase();

		try {
			db.connecting2DB();;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		Controller.loginView();
	}

}
