package pt.unl.fct.di.adc.firstwebapp.util;

public class NEW_UserData {

	public String username;
	public String email;
	public String name;
	public String password;
	public String passwordConfirmation;
	public String landlineNumber;
	public String mobilePhoneNumber;
	public String mainAddress;
	public String complementaryAddress;
	public String placeOfAddress;
	public String NIF;
	public String profilePublic;
	public String role;
	public String state;

	public NEW_UserData() {

	}

	public NEW_UserData(String username, String email, String name, String password, String passwordConfirmation,
			Object landlineNumber, Object mobilePhoneNumber, Object mainAddress, Object complementaryAddress,
			Object placeOfAddress, Object NIF, Object profilePublic) {
		this.username = username;
		this.email = email;
		this.name = name;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
		if (landlineNumber == null)
			this.landlineNumber = "UNDEFINED";
		else
			this.landlineNumber = (String) landlineNumber;

		if (mobilePhoneNumber == null)
			this.mobilePhoneNumber = "UNDEFINED";
		else
			this.mobilePhoneNumber = (String) mobilePhoneNumber;

		if (mainAddress == null)
			this.mainAddress = "UNDEFINED";
		else
			this.mainAddress = (String) mainAddress;

		if (complementaryAddress == null)
			this.complementaryAddress = "UNDEFINED";
		else
			this.complementaryAddress = (String) complementaryAddress;

		if (placeOfAddress == null)
			this.placeOfAddress = "UNDEFINED";
		else
			this.placeOfAddress = (String) placeOfAddress;

		if (NIF == null)
			this.NIF = "UNDEFINED";
		else
			this.NIF = (String) NIF;

		if (profilePublic == null)
			this.profilePublic = "UNDEFINED";
		else
			this.profilePublic = (String) profilePublic;

	}

	public NEW_UserData(String username, String email, String name, String password, String passwordConfirmation) {
		this.username = username;
		this.email = email;
		this.name = name;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
		this.landlineNumber = null;
		this.mobilePhoneNumber = null;
		this.mainAddress = null;
		this.complementaryAddress = null;
		this.placeOfAddress = null;
		this.NIF = null;
		this.profilePublic = null;
	}

	public boolean validRegistration() {
		if (username == null || email == null || name == null || password == null || passwordConfirmation == null)
			return false;

		return true;
	}

	public boolean validPassword() {
		if (!password.equals(passwordConfirmation)) {
			return false;
		}
		return true;
	}

	public boolean validPassword2() {
		if (password.length() < 8) {
			return false;
		}
		return true;
	}

}
