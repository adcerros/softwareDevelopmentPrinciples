package Transport4Future.Utils.Checker;

import Transport4Future.TokenManagement.Data.Token;
import Transport4Future.TokenManagement.Exceptions.TokenManagementException;
import Transport4Future.TokenManagement.Store.TokensStore;
import Transport4Future.Utils.TokensComparator;

public abstract class GenericTokenChecker implements IChecker {
	
	public GenericTokenChecker() {
		super();
	}
	
	public Token checkDecodedToken(Token decodedToken) throws TokenManagementException {
		TokensStore myStore = TokensStore.getInstance();
		Token tokenFound = myStore.Find(decodedToken.getSignature());
		if (tokenFound == null){
			throw new TokenManagementException("The token does not exist");
		}
		compare(decodedToken, tokenFound);
		if (tokenFound.isValid() != true || tokenFound.stateOfRevocation != "Not revoked Token"){
			throw new TokenManagementException("The token is not valid");
		}
		return tokenFound;
	}
	
	public void compare(Token decodedToken, Token tokenFound) throws TokenManagementException {
		TokensComparator comparator = new TokensComparator();
		comparator.compare(decodedToken, tokenFound);
	}
	
}
