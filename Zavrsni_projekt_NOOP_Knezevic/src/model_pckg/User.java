package model_pckg;

import java.sql.Date;

public class User {
	
	public int id;
	public String name;
	public String surname;
	public java.util.Date dateOfBirth;
	public String address;
	public int phoneNum;
	private String password;
	public String username;
	
	
	
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		// TODO : provjera tko postavlja šifru
		this.password = password;
	}
	
	
	

}
