package Transport4Future.TokenManagement.Data;

import java.util.HashMap;
import Transport4Future.TokenManagement.Data.Attributes.TokenValue;
import Transport4Future.TokenManagement.Data.Attributes.TypeOfOperation;
import Transport4Future.TokenManagement.Exceptions.TokenManagementException;
import Transport4Future.TokenManagement.IO.TokenActionParser;
import Transport4Future.TokenManagement.Store.TokensRequestStore;
import Transport4Future.Utils.Checker.ActionCheker;
import Transport4Future.Utils.Decoder.Decoder;


public class TokenAction {
	private static final String NOT_OPERATION_MESSAGE = "The requested operation cannot be performed with "
			+ "the token attached to the request.";
	private TokenValue tokenValue;
	private TypeOfOperation typeOfOperation;

	public TokenAction (String InputFile) throws TokenManagementException {
		TokenActionParser myParser = new TokenActionParser();
		HashMap<String, String> items = (HashMap<String, String>) myParser.Parse(InputFile);
		this.tokenValue = new TokenValue(items.get("Token Value"));
		this.typeOfOperation = new TypeOfOperation(items.get("Type of operation"));
		}
	
	public boolean operation () throws TokenManagementException {
		Decoder decoder = new Decoder();
		Token decodedToken = decoder.decode(this.getTokenValue());
		ActionCheker checker = new ActionCheker();
		Token tokenFound = checker.checkDecodedToken(decodedToken);
		TokenRequest requestFound = getRequest(tokenFound);
		return checkAction(requestFound);
	}

	private boolean checkAction(TokenRequest requestFound) throws TokenManagementException {
		boolean actionOk = false;
		if (requestFound.getTypeOfDevice().equals("Sensor")) {
			if ((this.getTypeOfOperation().equals("Send Information from Sensor")) 
					|| (this.getTypeOfOperation().equals("Check State"))) {
				actionOk=true;
			} else {
				throw new TokenManagementException(NOT_OPERATION_MESSAGE);
			}
		}
		if (requestFound.getTypeOfDevice().equals("Actuator")) {
			if ((this.getTypeOfOperation().equals("Send Request to Actuator")) 
					|| (this.getTypeOfOperation().equals("Check State"))) {
				actionOk=true;
			} else {
				throw new TokenManagementException(NOT_OPERATION_MESSAGE);
			}
		}
		return actionOk;
	}

	private TokenRequest getRequest(Token tokenFound) throws TokenManagementException {
		TokenRequest requestFound = null;
		TokensRequestStore myRequestStore = TokensRequestStore.getInstance();
		if (myRequestStore.Find(tokenFound.device.getValue()) != true) {
			throw new TokenManagementException("Impossible to know the type of device, there is no associated request");
		}
		requestFound = myRequestStore.findRequest(tokenFound.device.getValue());
		return requestFound;
	}

	public String getTokenValue() {
		return this.tokenValue.getValue();
	}
	
	public String getTypeOfOperation() {
		return this.typeOfOperation.getValue();
	}

}

