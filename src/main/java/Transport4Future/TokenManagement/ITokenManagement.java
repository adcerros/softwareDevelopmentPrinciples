package Transport4Future.TokenManagement;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public interface ITokenManagement {
	
	String TokenRequestGeneration (String InputFile) throws TokenManagementException;

	String RequestToken (String InputFile) throws TokenManagementException;

	boolean VerifyToken (String Token) throws TokenManagementException;

	String RevokeToken (String InputFile) throws TokenManagementException;
	
	boolean ExecuteAction (String InputFile) throws TokenManagementException;
	
}
