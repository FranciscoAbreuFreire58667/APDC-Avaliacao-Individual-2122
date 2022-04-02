package pt.unl.fct.di.adc.firstwebapp.util;

public class SINGLE_USERNAME_DATA {
	public String username;


	public SINGLE_USERNAME_DATA() {

	}

	public SINGLE_USERNAME_DATA(String username) {
		this.username = username;
	
	}

	public boolean validRegistration() {
		if (username == null) {
			return false;
		} else
			return true;

	}
}
