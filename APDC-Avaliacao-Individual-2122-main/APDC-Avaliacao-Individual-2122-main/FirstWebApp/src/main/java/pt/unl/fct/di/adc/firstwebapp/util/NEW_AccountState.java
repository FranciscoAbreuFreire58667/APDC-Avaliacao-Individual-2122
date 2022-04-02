package pt.unl.fct.di.adc.firstwebapp.util;

public enum NEW_AccountState {
ACTIVE ("ACTIVE"), INACTIVE("INACTIVE");
	
	private String value;
	
	private NEW_AccountState(String value) {
		this.value = value;
	}
	
	public String getValue() {
        return value;
    }
}
