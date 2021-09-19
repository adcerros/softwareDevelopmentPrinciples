package Transport4Future.Utils.Hasher;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class SHA256Hasher extends GenericHasher implements IHash {
	
	public SHA256Hasher() {
		this.algorithm = "SHA-256";
		this.format = "%064x";
	}
	
	@Override
	public String Hash(String text) throws TokenManagementException {
		return super.Hash(text);
	}
}
