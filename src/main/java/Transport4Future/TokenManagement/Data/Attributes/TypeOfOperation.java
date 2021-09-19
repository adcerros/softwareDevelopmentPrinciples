package Transport4Future.TokenManagement.Data.Attributes;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class TypeOfOperation extends Attribute {

	public TypeOfOperation (String Value) throws TokenManagementException {
		this.errorMessage = "Error: invalid type of operation.";
		this.value = this.validate(Value);
	}
	
	protected String validate (String Value) throws TokenManagementException{
		if (!(Value.equalsIgnoreCase("Send Information from Sensor") 
				|| Value.equalsIgnoreCase("Send Request to Actuator")
				|| Value.equalsIgnoreCase("Check State"))) {
			throw new TokenManagementException(this.errorMessage);	
		} 
		return Value;
	}
}
