package Transport4Future.Utils.Checker;

import Transport4Future.TokenManagement.Data.Token;
import Transport4Future.TokenManagement.Exceptions.TokenManagementException;
import Transport4Future.TokenManagement.Store.TokensStore;

public class RevocationChecker extends ActionCheker implements IChecker {

	@Override
	public Token checkDecodedToken(Token decodedToken) throws TokenManagementException {
		TokensStore myStore = TokensStore.getInstance();
		Token tokenFound = myStore.Find(decodedToken.getSignature());
		if (tokenFound == null){
			throw new TokenManagementException("The token to be revoked does not exist");
		}
		compare(decodedToken, tokenFound);
		if (tokenFound.exp < System.currentTimeMillis()) {
			throw new TokenManagementException("The token to be revoked has already expired");	
		}
		return tokenFound;
	}
	
	public void compare(Token decodedToken, Token tokenFound) throws TokenManagementException {
		super.compare(decodedToken, tokenFound);
	}
}
