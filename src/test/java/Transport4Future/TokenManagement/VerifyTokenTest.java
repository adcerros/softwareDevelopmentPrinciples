package Transport4Future.TokenManagement;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;
import Transport4Future.TokenManagement.TestUtil.TestUtil;

class VerifyTokenTest {
	
	private TokenManager myManager;
	TestUtil util = new TestUtil();
	
	public VerifyTokenTest () {
		 myManager = util.getManager();
	}
		
	@Test
	@DisplayName("RF03 - TC01 - Buscar un token en un almacén de tokens vacío")
	void VerifyTokenEmptyTokenStore() throws TokenManagementException {
		this.util.resetTokenStore();
		String tokenToVerify = "ABxnPUhTMjU2XG4gVHlwPVBEU1xuRGV2PTI3OGU3ZTI3NzMyYzRlNTM5NDZjZjIwMDU4YWQ4MTM4XG4gaWF0PTE4LTAzLTIwMjAgMTA6MjI6MjBcbiBleHA9MjUtMDMtMjAyMCAxMDoyMjoyMGIzYWMwYjRkNjQyOTE1OGRhMTI4NzYxZTk4Y2U4MzE3ZmE5MjhkNDJjNzEwYTlkMDZmNjRjOTY2N2JkZDM3MWM=";
		boolean result = myManager.VerifyToken(tokenToVerify);
		assertEquals (false, result);
	}
	
	@Test
	@DisplayName("RF03 - TC02 - Buscar un token que no existe en un almacén de registros que contiene un elemento")
	void VerifyTokenNonExistingToken() throws TokenManagementException {
		this.util.insertFirstToken();
		String tokenToVerify = "ABxnPUhTMjU2XG4gVHlwPVBEU1xuRGV2PTI3OGU3ZTI3NzMyYzRlNTM5NDZjZjIwMDU4YWQ4MTM4XG4gaWF0PTE4LTAzLTIwMjAgMTA6MjI6MjBcbiBleHA9MjUtMDMtMjAyMCAxMDoyMjoyMGIzYWMwYjRkNjQyOTE1OGRhMTI4NzYxZTk4Y2U4MzE3ZmE5MjhkNDJjNzEwYTlkMDZmNjRjOTY2N2JkZDM3MWM=";
		boolean result = myManager.VerifyToken(tokenToVerify);
		assertEquals (false, result);
	}
	
	@Test
	@DisplayName("RF03 - TC03 - Buscar un token que existe en almacén con registros")
	void VerifyTokenCorrectTest() throws TokenManagementException {
		this.util.insertFirstToken();
		String tokenToVerify = "QWxnPUhTMjU2XG4gVHlwPVBEU1xuRGV2PTI3OGU3ZTI3NzMyYzRlNTM5NDZjZjIwMDU4YWQ4MTM4XG4gaWF0PTE4LTAzLTIwMjAgMTA6MjI6MjBcbiBleHA9MjUtMDMtMjAyMCAxMDoyMjoyMGIzYWMwYjRkNjQyOTE1OGRhMTI4NzYxZTk4Y2U4MzE3ZmE5MjhkNDJjNzEwYTlkMDZmNjRjOTY2N2JkZDM3MWM=";
		boolean result = myManager.VerifyToken(tokenToVerify);
		assertEquals (false, result);
	}

	@Test
	@DisplayName("RF03 - TC04 - Buscar un token cuando el almacén no existe")
	void VerifyTokenNonExitingTokenStore() throws TokenManagementException {
		String storePath = System.getProperty("user.dir") + "/Store/tokenStore.json";
		File file = new File(storePath);
        file.delete();
		String tokenToVerify = "ABxnPUhTMjU2XG4gVHlwPVBEU1xuRGV2PTI3OGU3ZTI3NzMyYzRlNTM5NDZjZjIwMDU4YWQ4MTM4XG4gaWF0PTE4LTAzLTIwMjAgMTA6MjI6MjBcbiBleHA9MjUtMDMtMjAyMCAxMDoyMjoyMGIzYWMwYjRkNjQyOTE1OGRhMTI4NzYxZTk4Y2U4MzE3ZmE5MjhkNDJjNzEwYTlkMDZmNjRjOTY2N2JkZDM3MWM=";
		boolean result = myManager.VerifyToken(tokenToVerify);
		assertEquals (false, result);
	}
	
	@Test
	@DisplayName("RF03 - TC05 - Buscar un token válido")
	void VerifyValidToken() throws TokenManagementException {
		this.util.insertFirstToken();
		this.util.insertSecondToken();
		String tokenToVerify = "QWxnPUhTMjU2XG4gVHlwPVBEU1xuRGV2PTZjODgzZjFjYjJlNmUxNTQ0N2RkMWM1MDg3YzU3ZjBkXG4gaWF0PTE4LTAzLTIwMjAgMTA6MjI6MjBcbiBleHA9MTYtMDQtMjAyMiAxODo1NTo0MDc3NDU5ZjFlOWQ2MmVmNTBhMzk1YmY1YjdiYjZlZmY4NTIwMTU5ZTdiNTA0YTZiNDJjZGY2OWMwZGEyNTAxMjE=";
		boolean result = myManager.VerifyToken(tokenToVerify);
		assertEquals (true, result);
	}
}
