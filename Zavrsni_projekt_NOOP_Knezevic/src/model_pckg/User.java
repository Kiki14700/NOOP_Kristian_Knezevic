package model_pckg;

import java.sql.Date;

/**
 * Model korisnika sustava(trgovca).
 * Svaki korisnik ime svoje ime i prezime, datum rodenja, adresu stanovanja, broj telefona te korisnicko ime i sifru.
 * @author Kristian Knezevic
 *
 */
public class User {
	
	public int id;
	public String name;
	public String surname;
	public java.util.Date dateOfBirth;
	public String address;
	public int phoneNum;
	private String password;
	public String username;
	
	public User() {
		
	}
	
	public User(int id, String name, String surname, Date dateOfBirth, String address, int phoneNum, String username) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.phoneNum = phoneNum;
		this.username = username;
	}
	
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		// TODO : provjera tko postavlja šifru
		this.password = password;
	}


	public int getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public String getSurname() {
		return surname;
	}


	public java.util.Date getDateOfBirth() {
		return dateOfBirth;
	}


	public String getAddress() {
		return address;
	}


	public int getPhoneNum() {
		return phoneNum;
	}


	public String getUsername() {
		return username;
	}
	
	
	
	
	

}
