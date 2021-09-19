package Transport4Future.Utils.Hasher;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class MD5Hasher extends GenericHasher implements IHash {

	private String key = "Stardust-";

	public MD5Hasher () {
		this.algorithm="MD5";
		this.format = "%32x";
	}
	
	@Override
	public String Hash(String text) throws TokenManagementException {

		String input =  key + text;
		
		return super.Hash(input);
	}
}
