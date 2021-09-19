package Transport4Future.TokenManagement.Data.Attributes;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class RequestDate extends Attribute {
	public RequestDate (String Value) throws TokenManagementException{
		this.pattern = "(0[1-9]|[1-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-[0-9]{4}\\s(2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]";
		this.errorMessage = "Error: invalid date data in JSON structure.";
		this.value = validate(Value);
	}
}
