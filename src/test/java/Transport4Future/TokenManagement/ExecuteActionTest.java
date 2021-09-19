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


class ExecuteActionTest {
	
	TestUtil util = new TestUtil();
	private TokenManager myManager;
	
	public ExecuteActionTest () throws TokenManagementException {
		 myManager = util.getManager();
		 this.util.resetTokenStore();
		 this.util.insertFirstToken();
		 this.util.insertSecondToken();
	}
	
	//Test de analisis sintactico y casos validos analisis estructural.
	
	@DisplayName ("Valid Test Cases Sintax Analisis And Basic Path Cases")
	@ParameterizedTest(name = "{index} - {2}")
	@CsvFileSource(resources = "/validTestCasesExecuteActionTest.csv")
	void ValidTestCases(String InputFilePath, boolean Result, String motivation) throws TokenManagementException {
		boolean myResult = myManager.ExecuteAction(InputFilePath);
		assertEquals (Result, myResult);
	}
	@DisplayName ("Invalid Test Cases Sintax Analisis")
	@ParameterizedTest(name = "{index} - {2}")
	@CsvFileSource(resources = "/invalidTestCasesExecuteActionTest.csv")
	void InvalidTestCases(String InputFilePath, String expectedMessage, String motivation) {
		TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
			myManager.ExecuteAction(InputFilePath);
		});
		assertEquals (expectedMessage, ex.getMessage());
	}
	
	
	//TEST DE CAMINO MINIMO
	//Solo casos no validos, los validos ya se contemplan en anteriores test parametrizados.


	
	/* Caso de Prueba: <CP_RF04_NV_45 - Revoca un token que no existe>
	* Técnica de prueba: Análisis Estructural - <Caminos Básicos>
	* Resultado Esperado: <Excepcion>
	*/
	@Test
	@DisplayName("RF04-NV-45-Accion sobre un token que no existe")
	void TokenDontExistTest() {
		String InputFile = System.getProperty("user.dir") + "/TestData/ExecuteActionTest/TokenDontExist.json";
		String expectedMessage = "The token does not exist";
		TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
		myManager.ExecuteAction(InputFile);
		});
		assertEquals (expectedMessage, ex.getMessage());		
	}
	
	/* Caso de Prueba: <CP_RF04_NV_46 - Revoca un token expirado>
	* Técnica de prueba: Análisis Estructural - <Caminos Básicos>
	* Resultado Esperado: <Excepcion>
	*/
	@Test
	@DisplayName("RF04-NV-46-Accion sobre un token expirado")
	void AlreadyExpiredTest() {
		String InputFile = System.getProperty("user.dir") + "/TestData/ExecuteActionTest/CorrectFileSensor.json";
		TokensStore myStore = TokensStore.getInstance();
		TokenDecoder decoder = new TokenDecoder();
		String decodedSignature= decoder.decode("QWxnPUhTMjU2XG4gVHlwPVBEU1xuRGV2PWY0ZDNmYmY0NDc4ZjJmNTZjNmU2Yzk4M2ZkOTQwYjRjXG4gaWF0PTE4LTAzLTIwMjAgMTA6MjI6MjBcbiBleHA9MTYtMDQtMjAyMiAxODo1NTo0MGYxNjg2MjQ5MDdjYTM2ZTc2N2ZiYzFjM2NiMjM5ODNhNjdkMTlkNWRhMjVmYzJmZGUyOWJhNTlmNzE2MDdiYmM=");
		Token token = myStore.Find(decodedSignature);
		long originalIat = token.getIat();
		long originalExp = token.getExp();
		String expectedMessage = "The token is not valid";
		TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
		token.setIat(token.getIat());
		token.setExp(token.getExp()-99000000000000l);
		myManager.ExecuteAction(InputFile);
		});
		assertEquals (expectedMessage, ex.getMessage());
		token.setIat(originalIat);
		token.setExp(originalExp);
	}
	
	/* Caso de Prueba: <CP_RF04_NV_47 - Accion sobre tipo de dispositivo no sensor>
	* Técnica de prueba: Análisis Estructural - <Caminos Básicos>
	* Resultado Esperado: <Excepcion>
	*/
	@Test
	@DisplayName("RF04-NV-47-Accion sobre tipo de dispositivo no sensor")
	void TypeNotSensor() {
		String InputFile = System.getProperty("user.dir") + "/TestData/ExecuteActionTest/TypeOfDeviceNonSensor.json";
		String expectedMessage = "The requested operation cannot be performed with the token attached to the request.";
		TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
		myManager.ExecuteAction(InputFile);
		});
		assertEquals (expectedMessage, ex.getMessage());
	}
	/* Caso de Prueba: <CP_RF04_NV_48 - Accion sobre tipo de dispositivo no actuator>
	* Técnica de prueba: Análisis Estructural - <Caminos Básicos>
	* Resultado Esperado: <Excepcion>
	*/
	@Test
	@DisplayName("RF04-NV-48-Accion sobre tipo de dispositivo no actuator")
	void TypeNotActuator() {
		String InputFile = System.getProperty("user.dir") + "/TestData/ExecuteActionTest/TypeOfDeviceNonActuator.json";
		String expectedMessage = "The requested operation cannot be performed with the token attached to the request.";
		TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
		myManager.ExecuteAction(InputFile);
		});
		assertEquals (expectedMessage, ex.getMessage());
	}
	/* Caso de Prueba: <CP_RF04_NV_49 - Accion sobre un token revocado>
	* Técnica de prueba: Análisis Estructural - <Caminos Básicos>
	* Resultado Esperado: <Excepcion>
	*/
	@Test
	@DisplayName("RF04-NV-49-Accion sobre un token revocado")
	void AlreadyRevoked() throws TokenManagementException {
		util.inserThirdToken();
		TokensStore myStore = TokensStore.getInstance();
		TokenDecoder decoder = new TokenDecoder();
		String decodedSignature= decoder.decode("QWxnPUhTMjU2XG4gVHlwPVBEU1xuRGV2PTYzNjEwYzBmZGQwYjk3Mzg3NTgzMTA4MTM3ZjFmMGI5XG4gaWF0PTE4LTAzLTIwMjAgMTA6MjI6MjBcbiBleHA9MTYtMDQtMjAyMiAxODo1NTo0MDAxZGEwOWM1OGYxMDQ0NDk2ODg4MzQ3MTMyYjM2ODQ0NTc0MzcyYzVmMzg4YWVhNDljMmIwNGM0YWUwYmVlOGI=");
		Token token = myStore.Find(decodedSignature);
		token.setStateOfRevocation("Temporal");
		String InputFile = System.getProperty("user.dir") + "/TestData/ExecuteActionTest/TokenAlreadyRevoked.json";
		String expectedMessage = "The token is not valid";
		TokenManagementException ex = Assertions.assertThrows(TokenManagementException.class, ()-> {
		myManager.ExecuteAction(InputFile);
		});
		assertEquals (expectedMessage, ex.getMessage());
	}
}
