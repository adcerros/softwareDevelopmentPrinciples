package Transport4Future.TokenManagement.Data;

import java.util.HashMap;

import Transport4Future.TokenManagement.Data.Attributes.DeviceName;
import Transport4Future.TokenManagement.Data.Attributes.DriverVersion;
import Transport4Future.TokenManagement.Data.Attributes.EMail;
import Transport4Future.TokenManagement.Data.Attributes.MACAddress;
import Transport4Future.TokenManagement.Data.Attributes.SerialNumber;
import Transport4Future.TokenManagement.Data.Attributes.TypeOfDevice;
import Transport4Future.TokenManagement.Exceptions.TokenManagementException;
import Transport4Future.TokenManagement.IO.TokenRequestParser;
import Transport4Future.TokenManagement.Store.TokensRequestStore;
import Transport4Future.Utils.Hasher.MD5Hasher;

public class TokenRequest {
	

	private DeviceName deviceName;
	private TypeOfDevice typeOfDevice;
	private DriverVersion driverVersion;
	private EMail supportEMail;
	private SerialNumber serialNumber;
	private MACAddress macAddress;
	private String hash;
	
	public TokenRequest (String InputFile) throws TokenManagementException {
		TokenRequestParser myParser = new TokenRequestParser();
		HashMap<String, String> items = (HashMap<String, String>) myParser.Parse(InputFile);
		this.deviceName = new DeviceName (items.get(TokenRequestParser.DEVICE_NAME));
		this.typeOfDevice = new TypeOfDevice (items.get(TokenRequestParser.TYPE_OF_DEVICE));
		this.driverVersion = new DriverVersion(items.get(TokenRequestParser.DRIVER_VERSION));
		this.supportEMail = new EMail(items.get(TokenRequestParser.SUPPORT_E_MAIL));
		this.serialNumber = new SerialNumber(items.get(TokenRequestParser.SERIAL_NUMBER));
		this.macAddress = new MACAddress(items.get(TokenRequestParser.MAC_ADDRESS));		
		this.hash = generateHash();
		this.Store();
	}
	
	private String generateHash() throws TokenManagementException {
		MD5Hasher myHasher = new MD5Hasher();
		return myHasher.Hash(this.toString());	
	}
	
	private void Store () throws TokenManagementException {
		TokensRequestStore myStore = TokensRequestStore.getInstance();
		myStore.saveTokenRequest(this, this.hash);
	}
	
	public String getDeviceName() {
		return deviceName.getValue();
	}

	public String getTypeOfDevice() {
		return typeOfDevice.getValue();
	}

	public String getDriverVersion() {
		return driverVersion.getValue();
	}

	public String getSupportEMail() {
		return supportEMail.getValue();
	}

	public String getSerialNumber() {
		return serialNumber.getValue();
	}

	public String getMacAddress() {
		return macAddress.getValue();
	}
	
	public String getHash() {
		return this.hash;
	}
	
	@Override
	public String toString() {
		return "TokenRequest [\\n\\" + TokenRequestParser.DEVICE_NAME + "=" + this.getDeviceName() +
				",\n\t\\" + TokenRequestParser.TYPE_OF_DEVICE + "=" + this.getTypeOfDevice() +
				",\n\t\\" + TokenRequestParser.DRIVER_VERSION + "=" + this.getDriverVersion() +	
				",\n\t\\" + TokenRequestParser.SUPPORT_E_MAIL + "=" + this.getSupportEMail() +	
				",\n\t\\" + TokenRequestParser.SERIAL_NUMBER + "=" + this.getSerialNumber() +
				",\n\t\\" + TokenRequestParser.MAC_ADDRESS + "=" + this.getMacAddress() + "\n]";
	}
}
