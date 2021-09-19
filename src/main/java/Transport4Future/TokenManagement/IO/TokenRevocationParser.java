package Transport4Future.TokenManagement.IO;

import java.util.HashMap;
import javax.json.JsonObject;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class TokenRevocationParser extends JSONParser implements ITokenManagementParser  {
	public static final String TOKEN_VALUE = "Token Value";
	public static final String TYPE_OF_REVOCATION = "Type of Revocation";
	public static final String REASON = "Reason";

	
	public Object Parse (String FileName) throws TokenManagementException{
		HashMap<String, String> items = new HashMap<String, String>();
		
		JsonObject jsonObject = (JsonObject) super.Parse(FileName);
		int numberOfTags=jsonObject.size();
		if(numberOfTags != 3) {
			throw new TokenManagementException("Error: JSON object cannot be created due to incorrect representation");
			}
		try {	
			items.put(TOKEN_VALUE, jsonObject.getString(TOKEN_VALUE));
			items.put(TYPE_OF_REVOCATION, jsonObject.getString(TYPE_OF_REVOCATION));	
			items.put(REASON, jsonObject.getString(REASON));				
		} catch (Exception pe) {
			throw new TokenManagementException("Error: invalid input data in JSON structure.");
		}
		return items;
	}
}
