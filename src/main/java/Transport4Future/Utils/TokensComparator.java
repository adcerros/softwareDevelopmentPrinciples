package Transport4Future.Utils;

import Transport4Future.TokenManagement.Data.Token;
import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class TokensComparator {

	public TokensComparator() {

	}
	
	public void compare(Token decodedToken, Token tokenFound) throws TokenManagementException {
		if (tokenFound.alg != decodedToken.alg &&
			tokenFound.typ != decodedToken.typ &&
			tokenFound.getDevice() != decodedToken.getDevice() &&
			tokenFound.signature != decodedToken.signature &&
			tokenFound.getLongAsDate(tokenFound.getIat()) != decodedToken.getLongAsDate(decodedToken.getIat()) && 
			tokenFound.getLongAsDate(tokenFound.getExp()).length() != decodedToken.getLongAsDate(decodedToken.getExp()).length())
			{
			throw new TokenManagementException("The token to be revoked does not exist");
		}
	}
}
