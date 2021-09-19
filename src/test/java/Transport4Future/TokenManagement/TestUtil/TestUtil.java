package Transport4Future.TokenManagement.TestUtil;

import java.io.FileWriter;
import java.io.IOException;

import Transport4Future.TokenManagement.TokenManager;
import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class TestUtil {
	
	
	private TokenManager myManager;
 
	public TestUtil() {
		myManager = TokenManager.getInstance();
 }
	public void resetTokenStore () throws TokenManagementException {
		String storePath = System.getProperty("user.dir") + "/Store/tokenStore.json";
        FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(storePath);
	        fileWriter.close();
		} catch (IOException e) {
			throw new TokenManagementException("Error: Unable to save a new token in the internal licenses store");
		}		
	}
	
	public void insertFirstToken () throws TokenManagementException {
		this.resetTokenStore();
		String InputFile = System.getProperty("user.dir") + "/TestData/TokenRequestTest/CorrectTokenRequest.json";
		myManager.RequestToken(InputFile);
	}
	
	public void insertSecondToken () throws TokenManagementException {
		String InputFile = System.getProperty("user.dir") + "/TestData/TokenRequestTest/SecondCorrectTokenRequest.json";
		myManager.RequestToken(InputFile);		
	}
	
	public void inserThirdToken () throws TokenManagementException {
		String InputFile = System.getProperty("user.dir") + "/TestData/TokenRequestTest/ThirdCorrectTokenRequest.json";
		myManager.RequestToken(InputFile);
	}
	
	public TokenManager getManager () {
		return myManager;
	}
}
