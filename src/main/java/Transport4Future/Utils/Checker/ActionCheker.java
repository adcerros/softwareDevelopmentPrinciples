package Transport4Future.Utils.Checker;

import Transport4Future.TokenManagement.Data.Token;
import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class ActionCheker extends GenericTokenChecker implements IChecker {

	public Token checkDecodedToken(Token decodedToken) throws TokenManagementException {
		return super.checkDecodedToken(decodedToken);
	}

	public void compare(Token decodedToken, Token tokenFound) throws TokenManagementException {
		super.compare(decodedToken, tokenFound);
	}
}
