package Transport4Future.Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Transport4Future.TokenManagement.Exceptions.TokenManagementException;

public class TextReader {
	public String getStringFromFile(String InputFile) throws TokenManagementException {
		BufferedReader reader;
		String fileContents = "";
		try {
			reader = new BufferedReader(new FileReader(InputFile));
		} catch (FileNotFoundException e) {
			throw new TokenManagementException("Error: input file not found.");
		}
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				fileContents += line;
			}
		} catch (IOException e) {
			throw new TokenManagementException("Error: input file could not be accessed.");
		}
		try {
			reader.close();
		} catch (IOException e) {
			throw new TokenManagementException("Error: input file could not be closed.");
		}
		return fileContents;
	}
}
