package pt.unl.fct.di.adc.firstwebapp.util;

public class RegisterData2 {

	public String username;
	public String password;
	public String confirmation;
	public String email;
	public String name;

	public RegisterData2() {

	}

	public RegisterData2(String username, String password, String confirmation, String email, String name) {
		this.username = username;
		this.password = password;
		this.confirmation = confirmation;
		this.email = email;
		this.name = name;
	}

	public boolean validRegistration() {
		if (username == null || password == null || confirmation == null || email == null || name == null) {
			return false;
		} else if (!password.equals(confirmation)) {
			return false;
		}
		return true;

	}

}
