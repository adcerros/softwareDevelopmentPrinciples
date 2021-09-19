package Transport4Future.TokenManagement.IO;

import java.util.HashMap;
import javax.json.JsonObject;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class TokenRequestParser extends JSONParser implements ITokenManagementParser  {
	public static final String MAC_ADDRESS = "MAC Address";
	public static final String SERIAL_NUMBER = "Serial Number";
	public static final String SUPPORT_E_MAIL = "Support e-mail";
	public static final String DRIVER_VERSION = "Driver Version";
	public static final String TYPE_OF_DEVICE = "Type of Device";
	public static final String DEVICE_NAME = "Device Name";
	
	public Object Parse (String FileName) throws TokenManagementException{
		HashMap<String, String> items = new HashMap<String, String>();
		
		JsonObject jsonObject = (JsonObject) super.Parse(FileName);
		try {	
			items.put(DEVICE_NAME, jsonObject.getString(DEVICE_NAME));
			items.put(TYPE_OF_DEVICE, jsonObject.getString(TYPE_OF_DEVICE));
			items.put(DRIVER_VERSION, jsonObject.getString(DRIVER_VERSION));
			items.put(SUPPORT_E_MAIL, jsonObject.getString(SUPPORT_E_MAIL));
			items.put(SERIAL_NUMBER, jsonObject.getString(SERIAL_NUMBER));	
			items.put(MAC_ADDRESS, jsonObject.getString(MAC_ADDRESS));				
		} catch (Exception pe) {
			throw new TokenManagementException("Error: invalid input data in JSON structure.");
		}
		return items;
	}
}
