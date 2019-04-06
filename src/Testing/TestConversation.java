package Testing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import ConvoBot.Conversation;
import ConvoBot.MyConversation;
import ConvoBot.PrintMessage;

public class TestConversation {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(8089);
		Socket socket = serverSocket.accept();
        PrintMessage.writer = new PrintStream(socket.getOutputStream());
        PrintMessage.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintMessage.messageFromBot("what do you want to do,a psychological test or a psychotherapy");
        String response = PrintMessage.messageFromUser();
        while (true){
			if (response.contains("psychotherapy")) {
				new Conversation();
				break;
			}else if (response.contains("psychological test")){
				new MyConversation();
				break;
			}else {
				PrintMessage.messageFromBot("Without this feature, please re-enter");
				response = PrintMessage.messageFromUser();
			}
        }
	}
 
}
