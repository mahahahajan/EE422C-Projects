package assignment2;

import java.util.Random;

public class SecretCodeGenerator {
	private static SecretCodeGenerator instance = new SecretCodeGenerator();
	public static SecretCodeGenerator getInstance() { return instance; }
    //Use this method for each game only once.
	//The correct way to call this is: SecretCodeGenerator.getInstance().getNewSecretCode()
	public String getNewSecretCode()
	{
		return "BGOP";
	}
}