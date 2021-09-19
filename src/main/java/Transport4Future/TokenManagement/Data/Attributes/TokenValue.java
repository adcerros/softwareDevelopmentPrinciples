package Transport4Future.TokenManagement.Data.Attributes;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class TokenValue extends Attribute {
	public TokenValue (String Value) throws TokenManagementException{
		this.errorMessage = "Error: invalid String length for Token Value.";
		this.value = validate(Value);
	}
	protected String validate (String Value) throws TokenManagementException{
		if (Value.length() < 1) {
			throw new TokenManagementException(this.errorMessage);
		}
		return Value;
	}
}
