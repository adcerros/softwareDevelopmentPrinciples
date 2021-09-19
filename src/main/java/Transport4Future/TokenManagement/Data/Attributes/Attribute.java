package Transport4Future.TokenManagement.Data.Attributes;

import java.util.regex.Pattern;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class Attribute {
	
	protected String value;
	protected String pattern;
	protected String errorMessage;
	
	protected String validate(String Value) throws TokenManagementException {
		Pattern pattern;
		pattern = Pattern.compile(this.pattern);
		if  (!pattern.matcher(Value).matches()) {
			throw new TokenManagementException(this.errorMessage);	
		}
		return Value;
	}
	
	public String getValue() {
		return this.value;
	}
}
