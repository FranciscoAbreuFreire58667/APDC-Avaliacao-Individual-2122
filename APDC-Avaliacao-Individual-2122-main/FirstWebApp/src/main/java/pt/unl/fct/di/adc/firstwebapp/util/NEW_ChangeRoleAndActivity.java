package pt.unl.fct.di.adc.firstwebapp.util;

public class NEW_ChangeRoleAndActivity {

	public String username;
	public String role;
	public String state;

	public NEW_ChangeRoleAndActivity(String username, Object role, Object state) {
		this.username = username;

		if (role == null)
			this.role = "UNDEFINED";
		else
			this.role = (String) role;

		if (state == null)
			this.state = "UNDEFINED";
		else
			this.state = (String) state;

	}

	public NEW_ChangeRoleAndActivity() {
	}
	
	public String getRole() {
		return this.role;
	}
	
	public String getState() {
		return this.state;
	}

}
