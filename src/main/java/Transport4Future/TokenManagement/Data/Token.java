package Transport4Future.TokenManagement.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

import Transport4Future.TokenManagement.Data.Attributes.Device;
import Transport4Future.TokenManagement.Data.Attributes.EMail;
import Transport4Future.TokenManagement.Data.Attributes.RequestDate;
import Transport4Future.TokenManagement.Exceptions.TokenManagementException;
import Transport4Future.TokenManagement.IO.TokenParser;
import Transport4Future.TokenManagement.Store.TokensRequestStore;
import Transport4Future.TokenManagement.Store.TokensStore;
import Transport4Future.Utils.Decoder.TokenDecoder;
import Transport4Future.Utils.Hasher.SHA256Hasher;


public class Token {
	public String alg;
	public String typ;
	public Device device;
	public RequestDate requestDate;
	public EMail notificationEmail;
	public long iat;
	public long exp;
	public String signature;
	public String stateOfRevocation;
	
	public Token (String FileName) throws TokenManagementException {
		TokenParser myParser = new TokenParser();
		HashMap<String, String> items = myParser.Parse(FileName);
		this.alg = "HS256";
		this.typ = "PDS";
		this.device = new Device(items.get(TokenParser.TOKEN_REQUEST));
		this.requestDate = new RequestDate(items.get(TokenParser.REQUEST_DATE));
		this.notificationEmail = new EMail(items.get(TokenParser.NOTIFICATION_E_MAIL));
		this.checkTokenRequestEmmision();
		this.stateOfRevocation = "Not revoked Token";
//		this.iat = System.currentTimeMillis();
		// SOLO PARA PRUEBAS
		testIATEXP();
		this.signature = this.generateSignature();
		Store();
	}

	public Token() {

	}

	private void Store() throws TokenManagementException {
		TokensStore myStore = TokensStore.getInstance();
		myStore.Add(this);
	}

	public boolean Decode (String TokenStringRepresentation) {
		TokensStore myStore = TokensStore.getInstance();
		TokenDecoder decoder = new TokenDecoder();
		String decodedSignature= decoder.decode(TokenStringRepresentation);
		Token tokenFound = myStore.Find(decodedSignature);
		if (tokenFound != null) {
			this.alg = tokenFound.alg;
			this.typ = tokenFound.typ;
			this.device = tokenFound.device;
			this.requestDate = tokenFound.requestDate;
			this.notificationEmail = tokenFound.notificationEmail;
			this.iat = tokenFound.iat;
			this.exp = tokenFound.exp;
			this.signature = tokenFound.signature;
			return true;
		}
		else{
			return false;
		}
	}
	
	public void testIATEXP() {
		this.iat = 1584523340892l; 
		if ((this.device.getValue().startsWith("5"))){
			this.exp = this.iat + 604800000l;
		}
		else {
			this.exp = this.iat + 65604800000l;
		}
	}
	
	private String generateSignature () throws TokenManagementException {
		SHA256Hasher myHasher = new SHA256Hasher();
		return (myHasher.Hash(this.getHeader() + this.getPayload()));
	}
	
	private void checkTokenRequestEmmision() throws TokenManagementException {
		TokensRequestStore myStore = TokensRequestStore.getInstance(); 
        if (!myStore.Find(this.getDevice())) {
        	throw new TokenManagementException("Error: Token Request Not Previously Registered");
        }
	}
	public String getLongAsDate(long data) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Date date = new Date(data);
		return df.format(date);
	}
	
	public String getDevice() {
		return device.getValue();
	}
	public void setDevice(String Value) throws TokenManagementException {
		this.device = new Device(Value);
	}

	public String getRequestDate() {
		return requestDate.getValue();
	}

	public String getNotificationEmail() {
		return notificationEmail.getValue();
	}
	
	public boolean isValid () {
		if ((this.iat < System.currentTimeMillis()) && (this.exp > System.currentTimeMillis())){
			return true;
		}
		else {
			return false;
		}
	}

	public String getHeader () {
		return	"Alg=" + this.alg + "\\n Typ=" + this.typ + "\\n";
	}
	
	public String getPayload () {
		Date iatDate = new Date(this.iat);
		Date expDate = new Date(this.exp);
		
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		return	"Dev=" + this.device.getValue() 
				+ "\\n iat=" + df.format(iatDate)
				+ "\\n exp=" + df.format(expDate);
	}

	public String getSignature() {
		return this.signature;
	}
		
	public String setTokenValue() {
		String stringToEncode = this.getHeader() + this.getPayload() + this.getSignature();
		String result  = Base64.getUrlEncoder().encodeToString(stringToEncode.getBytes());
		return result;
	}
	
	public void setSignature(String value) {
		this.signature = value;
	}
	
	public String getStateOfRevocation() {
		return this.stateOfRevocation;
	}
	
	public void setStateOfRevocation(String value) throws TokenManagementException {
		if (getStateOfRevocation() == value) {
			throw new TokenManagementException("The token to be revoked is already revoked in the same mode");
		} else {
			this.stateOfRevocation = value;
		}
	}
	
	public long getIat() {
		return iat;
	}
	
	public long getExp() {
		return exp;
	}
	
	public void setIat(long value) {
		this.iat = value;
	}
	
	public void setExp(long value) {
		this.exp = value;
	}

}
