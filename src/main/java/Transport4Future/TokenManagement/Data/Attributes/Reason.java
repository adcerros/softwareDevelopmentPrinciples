package Transport4Future.TokenManagement.Data.Attributes;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class Reason extends Attribute {
	public Reason (String Value) throws TokenManagementException{
		this.errorMessage = "Error: invalid String length for reason.";
		this.value = validate(Value);
	}
	protected String validate (String Value) throws TokenManagementException{
		if (Value.length() < 1 || Value.length() > 100) {
			throw new TokenManagementException(this.errorMessage);
		}
		return Value;
	}
}