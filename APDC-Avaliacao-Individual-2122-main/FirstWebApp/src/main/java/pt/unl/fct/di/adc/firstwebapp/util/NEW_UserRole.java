package pt.unl.fct.di.adc.firstwebapp.util;

public enum NEW_UserRole {
	SU("SU"), GS("GS"), USER("USER"), GBO("GBO");
	
	private String role;
	
	private NEW_UserRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
        return role;
    }
	
}
