package Transport4Future.TokenManagement.Data.Attributes;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class Device extends Attribute {
	public Device (String Value) throws TokenManagementException{
		this.pattern = "([A-Fa-f0-9]{32})";
		this.errorMessage = "Error: invalid Device in token request.";
		this.value = validate(Value);
	}
}
