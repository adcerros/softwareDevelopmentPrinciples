package Transport4Future.Utils.Checker;

import Transport4Future.TokenManagement.Data.Token;
import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public interface IChecker {

	public Token checkDecodedToken(Token decodedToken) throws TokenManagementException;
	
	public void compare(Token decodedToken, Token tokenFound) throws TokenManagementException;
}
