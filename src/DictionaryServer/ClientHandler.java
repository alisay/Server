package DictionaryServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {
	final BufferedReader in;
	final BufferedWriter out;
	final Socket clientSocket;

	public ClientHandler(Socket clientSocket, BufferedReader in, BufferedWriter out) {
		this.clientSocket = clientSocket;
		this.in = in;
		this.out = out;
	}

	@Override
	public void run() {
		String received;
		while (true) {
			try {
				out.write("Select from the following:[Query Meaning | Add Word | Remove Word]..\n"
						+ "Type Exit to terminate connection.");
				received = in.readLine();
				
				if(received.equalsIgnoreCase("Exit")) {
					System.out.println("Client " + this.clientSocket + " sends exit...");
					System.out.println("Closing this connection.");
					this.clientSocket.close();
					System.out.println("Connection closed");
					break;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			// closing resources
			this.in.close();
			this.out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
