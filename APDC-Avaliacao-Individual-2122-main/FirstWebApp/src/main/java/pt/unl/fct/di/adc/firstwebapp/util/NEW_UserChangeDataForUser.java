package pt.unl.fct.di.adc.firstwebapp.util;

public class NEW_UserChangeDataForUser {

	public String username;
	public String email;
	public String name;

	public String landlineNumber;
	public String mobilePhoneNumber;
	public String mainAddress;
	public String complementaryAddress;
	public String placeOfAddress;
	public String NIF;
	public String profilePublic;

	public NEW_UserChangeDataForUser() {
		// TODO Auto-generated constructor stub
	} 
	
	public NEW_UserChangeDataForUser(String username, Object email, Object name, Object landlineNumber,
			Object mobilePhoneNumber, Object mainAddress, Object complementaryAddress, Object placeOfAddress,
			Object NIF, Object profilePublic) {

		this.username = username;

		if (email == null)
			this.email = "UNDEFINED";
		else
			this.email = (String) email;

		if (name == null)
			this.name = "UNDEFINED";
		else
			this.name = (String)name;

		if (landlineNumber == null)
			this.landlineNumber = "UNDEFINED";
		else
			this.landlineNumber = (String)landlineNumber;

		if (mobilePhoneNumber == null)
			this.mobilePhoneNumber = "UNDEFINED";
		else
			this.mobilePhoneNumber = (String)mobilePhoneNumber;

		if (mainAddress == null)
			this.mainAddress = "UNDEFINED";
		else
			this.mainAddress = (String)mainAddress;

		if (complementaryAddress == null)
			this.complementaryAddress = "UNDEFINED";
		else
			this.complementaryAddress = (String)complementaryAddress;

		if (placeOfAddress == null)
			this.placeOfAddress = "UNDEFINED";
		else
			this.placeOfAddress = (String)placeOfAddress;

		if (NIF == null)
			this.NIF = "UNDEFINED";
		else
			this.NIF = (String)NIF;

		if (profilePublic == null)
			this.profilePublic = "UNDEFINED";
		else
			this.profilePublic = (String)profilePublic;

	}

}
