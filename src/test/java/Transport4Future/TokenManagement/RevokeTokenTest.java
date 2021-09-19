package Transport4Future.TokenManagement;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import Transport4Future.TokenManagement.Data.Token;
import Transport4Future.TokenManagement.Exceptions.TokenManagementException;
import Transport4Future.TokenManagement.Store.TokensStore;
import Transport4Future.TokenManagement.TestUtil.TestUtil;
import Transport4Future.Utils.Decoder.TokenDecoder;


class RevokeTokenTest {
	
	TestUtil util = new TestUtil();
	private TokenManager myManager;
	
	public RevokeTokenTest () throws TokenManagementException {
		 myManager = util.getManager();
		 this.util.resetTokenStore();
		 this.util.insertFirstToken();
		 this.util.insertSecondToken();
	}
	
	//Test de analisis sintactico y casos validos analisis estructural.
	
	@DisplayName ("Valid Test Cases Sintax Analisis And Basic Path Cases")
	@ParameterizedTest(name = "{index} - {2}")
	@CsvFileSource(resources = "/validTestCasesRevokeTokenTest.csv")
	void ValidTestCases(String InputFilePath, String Result, String motivation) throws TokenManagementException {
		String myResult = myManager.RevokeToken(InputFilePath);
		assertEquals (Result, myResult);
	}
	@DisplayName ("Invalid Test Cases Sintax Analisis")
	@ParameterizedTest(name = "{index} - {2}")
	@CsvFileSource(resources = "/invalidTestCasesRevokeTokenTest.csv")
	void InvalidTestCases(String InputFilePath, String expectedMessage, String motivation) {
		TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
			myManager.RevokeToken(InputFilePath);
		});
		assertEquals (expectedMessage, ex.getMessage());
	}
	
	
	//TEST DE CAMINO MINIMO
	//Solo casos no validos, los validos ya se contemplan en anteriores test parametrizados.
	
	
	/* Caso de Prueba: <CP_RF04_NV_51 - Revoca un token que no existe>
	* Técnica de prueba: Análisis Estructural - <Caminos Básicos>
	* Resultado Esperado: <Excepcion>
	*/
	@Test
	@DisplayName("RF04-NV-51-Revoca un token que no existe")
	void TokenDontExistTest() {
		String expectedMessage = "The token to be revoked does not exist";
		String InputFile = System.getProperty("user.dir") + "/TestData/RevokeTokenTest/TokenDontExist.json";
		TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
		myManager.RevokeToken(InputFile);
		});
		assertEquals (expectedMessage, ex.getMessage());		
	}
	
	/* Caso de Prueba: <CP_RF04_NV_52 - Revoca un token expirado>
	* Técnica de prueba: Análisis Estructural - <Caminos Básicos>
	* Resultado Esperado: <Excepcion>
	*/
	@Test
	@DisplayName("RF04-NV-52-Revoca un token expirado")
	void AlreadyExpiredTest() {
		String InputFile = System.getProperty("user.dir") + "/TestData/RevokeTokenTest/CorrectFileFinal.json";
		TokensStore myStore = TokensStore.getInstance();
		TokenDecoder decoder = new TokenDecoder();
		String decodedSignature= decoder.decode("QWxnPUhTMjU2XG4gVHlwPVBEU1xuRGV2PWY0ZDNmYmY0NDc4ZjJmNTZjNmU2Yzk4M2ZkOTQwYjRjXG4gaWF0PTE4LTAzLTIwMjAgMTA6MjI6MjBcbiBleHA9MTYtMDQtMjAyMiAxODo1NTo0MGYxNjg2MjQ5MDdjYTM2ZTc2N2ZiYzFjM2NiMjM5ODNhNjdkMTlkNWRhMjVmYzJmZGUyOWJhNTlmNzE2MDdiYmM=");
		Token token = myStore.Find(decodedSignature);
		long originalIat = token.getIat();
		long originalExp = token.getExp();
		String expectedMessage = "The token to be revoked has already expired";
		TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
		token.setIat(token.getIat());
		token.setExp(token.getExp()-99000000000000l);
		myManager.RevokeToken(InputFile);
		});
		assertEquals (expectedMessage, ex.getMessage());
		token.setIat(originalIat);
		token.setExp(originalExp);
		
	}
	
	/* Caso de Prueba: <CP_RF04_NV_53 - Revoca un Token ya revocado modalidad temporal>
	* Técnica de prueba: Análisis Estructural - <Caminos Básicos>
	* Resultado Esperado: <Excepcion>
	*/
	@Test
	@DisplayName("RF04-NV-53-Revoca un Token ya revocado modalidad temporal")
	void AlreadyRevokedTestUno() {
		String InputFile = System.getProperty("user.dir") + "/TestData/RevokeTokenTest/CorrectFileTemporal.json";
		String expectedMessage = "The token to be revoked is already revoked in the same mode";
		TokensStore myStore = TokensStore.getInstance();
		TokenDecoder decoder = new TokenDecoder();
		String decodedSignature= decoder.decode("QWxnPUhTMjU2XG4gVHlwPVBEU1xuRGV2PTZjODgzZjFjYjJlNmUxNTQ0N2RkMWM1MDg3YzU3ZjBkXG4gaWF0PTE4LTAzLTIwMjAgMTA6MjI6MjBcbiBleHA9MTYtMDQtMjAyMiAxODo1NTo0MDc3NDU5ZjFlOWQ2MmVmNTBhMzk1YmY1YjdiYjZlZmY4NTIwMTU5ZTdiNTA0YTZiNDJjZGY2OWMwZGEyNTAxMjE=");
		Token token = myStore.Find(decodedSignature);
		TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
		token.setStateOfRevocation("Not revoked Token");
		myManager.RevokeToken(InputFile);
		myManager.RevokeToken(InputFile);
		});
		assertEquals (expectedMessage, ex.getMessage());
	}
	
	/* Caso de Prueba: <CP_RF04_NV_54 - Revoca un Token ya revocado modalidad final>
	* Técnica de prueba: Análisis Estructural - <Caminos Básicos>
	* Resultado Esperado: <Excepcion>
	*/
	@Test
	@DisplayName("RF04-NV-54-Revoca un Token ya revocado modalidad final")
	void AlreadyRevokedTestDos() {
		String InputFile = System.getProperty("user.dir") + "/TestData/RevokeTokenTest/CorrectFileFinal.json";
		String expectedMessage = "The token to be revoked is already revoked in the same mode";
		TokensStore myStore = TokensStore.getInstance();
		TokenDecoder decoder = new TokenDecoder();
		String decodedSignature= decoder.decode("QWxnPUhTMjU2XG4gVHlwPVBEU1xuRGV2PWY0ZDNmYmY0NDc4ZjJmNTZjNmU2Yzk4M2ZkOTQwYjRjXG4gaWF0PTE4LTAzLTIwMjAgMTA6MjI6MjBcbiBleHA9MTYtMDQtMjAyMiAxODo1NTo0MGYxNjg2MjQ5MDdjYTM2ZTc2N2ZiYzFjM2NiMjM5ODNhNjdkMTlkNWRhMjVmYzJmZGUyOWJhNTlmNzE2MDdiYmM=");
		Token token = myStore.Find(decodedSignature);
		TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
		token.setStateOfRevocation("Not revoked Token");
		myManager.RevokeToken(InputFile);
		myManager.RevokeToken(InputFile);
		});
		assertEquals (expectedMessage, ex.getMessage());
	}

}
