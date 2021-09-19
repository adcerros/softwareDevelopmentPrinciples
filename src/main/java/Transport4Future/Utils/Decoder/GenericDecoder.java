package Transport4Future.Utils.Decoder;

import java.util.Base64;

public abstract class GenericDecoder implements IDecoder {

	public GenericDecoder() {
		super();
	}

	public String Decode(String TokenStringRepresentation) {
	byte[] decodedBytes = Base64.getDecoder().decode(TokenStringRepresentation);
	String decodedString = new String(decodedBytes);
	return decodedString;
	}

}