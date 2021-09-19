package Transport4Future.Utils.Decoder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Transport4Future.TokenManagement.Data.Token;
import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class Decoder extends GenericDecoder implements IDecoder {
	
	private static final int EXP_END = 109;
	private static final int EXP_START = 90;
	private static final int IAT_END = 83;
	private static final int IAT_START = 64;
	private static final int SIGNATURE_START = 109;
	private static final int DEV_END = 57;
	private static final int DEV_START = 25;
	private static final int TYP_END = 19;
	private static final int TYP_START = 16;
	private static final int ALG_END = 9;
	private static final int ALG_START = 4;

	public Token decode(String TokenStringRepresentation) throws TokenManagementException {
		return generateToken(super.Decode(TokenStringRepresentation));
	}

	public Token generateToken(String decodedString) throws TokenManagementException {
		Token token = new Token();
		token.alg = decodedString.substring(ALG_START, ALG_END);
		token.typ = decodedString.substring(TYP_START, TYP_END);
		token.setDevice(decodedString.substring(DEV_START, DEV_END));
		token.setSignature(decodedString.substring(SIGNATURE_START, decodedString.length()));
		token.setIat(stringDateToLong(decodedString.substring(IAT_START, IAT_END)));
		token.setExp(stringDateToLong(decodedString.substring(EXP_START, EXP_END)));
		return token;
	}
	
	public long stringDateToLong (String stringDate) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date stringAsDate = null;
		try {
			stringAsDate = df.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long date = stringAsDate.getTime();
		return date;
	}
}
