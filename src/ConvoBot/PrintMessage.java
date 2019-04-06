package ConvoBot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

/**
 * This class makes it easier to interact with the interface for the Android app.
 * @author Owner
 *
 */
public class PrintMessage {

	public static PrintStream writer;
	public static BufferedReader reader;
	/**
	 * This reads the text the user types into the console when responding to the bot.
	 * @param input from user
	 */
	
	public static String messageFromUser() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}return "";
	}
	/**
	 * This sends the message from the bot to print out to the console.
	 * @param output from bot
	 */
	public static void messageFromBot(String output) {
		writer.println(output);
	}

}
