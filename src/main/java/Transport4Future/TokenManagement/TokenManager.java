package Transport4Future.TokenManagement;


import Transport4Future.TokenManagement.Data.Token;
import Transport4Future.TokenManagement.Data.TokenAction;
import Transport4Future.TokenManagement.Data.TokenRequest;
import Transport4Future.TokenManagement.Data.TokenRevocation;
import Transport4Future.TokenManagement.Exceptions.TokenManagementException;



public class TokenManager implements ITokenManagement {
	
	private static TokenManager manager;
	
	private TokenManager () {
		
	}
	
	public static TokenManager getInstance() {
		if (manager == null) {
			manager = new TokenManager();
		}
		return manager;
	}
	
	@Override
	public TokenManager clone () {
		try {
			throw new CloneNotSupportedException();
		}
		catch (CloneNotSupportedException ex) {
//			System.out.println("Token Manager Object cannot be cloned");
		}
		return null;
	}

	public String TokenRequestGeneration (String InputFile) throws TokenManagementException{
		TokenRequest req = new TokenRequest(InputFile);
		return req.getHash();
	}
	
	public String RequestToken (String InputFile) throws TokenManagementException{	
		Token myToken = new Token (InputFile);
		return myToken.setTokenValue();
	}
	
	public boolean VerifyToken (String TokenString) throws TokenManagementException{
		Token token = new Token ();
		if (token.Decode(TokenString)) {
			return token.isValid();	
		}
		else {
			return false;
		}
	}
	
	public String RevokeToken (String InputFile) throws TokenManagementException {
		TokenRevocation revocation = new TokenRevocation(InputFile);
		return revocation.getDecodedTokenEmail();
	}
	
	public boolean ExecuteAction (String InputFile) throws TokenManagementException {
		TokenAction action = new TokenAction(InputFile);
		return action.operation();
	}
}