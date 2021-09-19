package Transport4Future.Utils.Decoder;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public interface IDecoder {
		public String Decode (String TokenStringRepresentation) throws TokenManagementException;
}
