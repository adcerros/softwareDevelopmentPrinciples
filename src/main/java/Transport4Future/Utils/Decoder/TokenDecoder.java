package Transport4Future.Utils.Decoder;

public class TokenDecoder extends GenericDecoder implements IDecoder {
	
	static final int SIGNATURE_START = 109;
	
	public String decode(String TokenStringRepresentation) {
		String decodedString = super.Decode(TokenStringRepresentation);
		String decodedSignature = decodedString.substring(SIGNATURE_START, decodedString.length());
		return decodedSignature;
	}
}
