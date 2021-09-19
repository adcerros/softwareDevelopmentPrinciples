package Transport4Future.TokenManagement.Data.Attributes;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class SerialNumber extends Attribute {
	public SerialNumber (String Value) throws TokenManagementException{
		this.pattern = "([A-Za-z0-9-]{1,})";
		this.errorMessage = "Error: invalid String length for serial number.";
		this.value = validate(Value);
	}
}
