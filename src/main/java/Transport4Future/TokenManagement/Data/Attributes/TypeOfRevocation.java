package Transport4Future.TokenManagement.Data.Attributes;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class TypeOfRevocation extends Attribute {

	public TypeOfRevocation (String Value) throws TokenManagementException {
		this.errorMessage = "Error: invalid type of revocation.";
		this.value = this.validate(Value);
	}
	
	protected String validate (String Value) throws TokenManagementException{
		if (!(Value.equalsIgnoreCase("Temporal") || Value.equalsIgnoreCase("Final"))) {
			throw new TokenManagementException(this.errorMessage);	
		} 
		return Value;
	}
}
