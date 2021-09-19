package Transport4Future.TokenManagement.IO;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public interface ITokenManagementParser {
	public Object Parse (String FileName) throws TokenManagementException;
}
