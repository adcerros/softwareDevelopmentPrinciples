package Transport4Future.TokenManagement.Data;

import java.util.HashMap;

import Transport4Future.TokenManagement.Data.Attributes.Reason;
import Transport4Future.TokenManagement.Data.Attributes.TokenValue;
import Transport4Future.TokenManagement.Data.Attributes.TypeOfRevocation;
import Transport4Future.TokenManagement.Exceptions.TokenManagementException;
import Transport4Future.TokenManagement.IO.TokenRevocationParser;
import Transport4Future.Utils.Checker.RevocationChecker;
import Transport4Future.Utils.Decoder.Decoder;

public class TokenRevocation {
		private TokenValue tokenValue;
		private TypeOfRevocation typeOfRevocation;
		private Reason reason;
	
	
	public TokenRevocation (String InputFile) throws TokenManagementException {
		TokenRevocationParser myParser = new TokenRevocationParser();
		HashMap<String, String> items = (HashMap<String, String>) myParser.Parse(InputFile);	
		this.tokenValue = new TokenValue(items.get(TokenRevocationParser.TOKEN_VALUE));
		this.typeOfRevocation = new TypeOfRevocation(items.get(TokenRevocationParser.TYPE_OF_REVOCATION));
		this.reason = new Reason(items.get(TokenRevocationParser.REASON));
		}
	
	public String getDecodedTokenEmail() throws TokenManagementException {
		Decoder decoder = new Decoder();
		Token decodedToken = decoder.decode(this.getTokenValue());
		RevocationChecker checker = new RevocationChecker();
		Token tokenFound = checker.checkDecodedToken(decodedToken);
		tokenFound.setStateOfRevocation(getTypeOfRevocation());
		return tokenFound.getNotificationEmail();
	}
	
	public String getTokenValue() {
		return this.tokenValue.getValue();
	}
	
	public String getTypeOfRevocation() {
		return this.typeOfRevocation.getValue();
	}
	
	public String getReason() {
		return this.reason.getValue();
	}
}
