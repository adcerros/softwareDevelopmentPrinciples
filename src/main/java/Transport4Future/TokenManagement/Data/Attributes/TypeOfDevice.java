package Transport4Future.TokenManagement.Data.Attributes;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class TypeOfDevice extends Attribute {

	public TypeOfDevice (String Value) throws TokenManagementException {
		this.errorMessage = "Error: invalid type of sensor.";
		this.value = this.validate(Value);
	}
	
	protected String validate (String Value) throws TokenManagementException{
		if (!(Value.equalsIgnoreCase("Sensor") || Value.equalsIgnoreCase("Actuator"))) {
			throw new TokenManagementException(this.errorMessage);	
		} 
		return Value;
	}
}
