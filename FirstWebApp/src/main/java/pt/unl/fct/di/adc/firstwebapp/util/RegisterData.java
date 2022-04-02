package pt.unl.fct.di.adc.firstwebapp.util;

public class RegisterData {

	public String username;
	public String password;

	public RegisterData() {

	}

	public RegisterData(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public boolean validRegistration() {
		if (username == null || password == null) {
			return false;
		} else
			return true;

	}

}
