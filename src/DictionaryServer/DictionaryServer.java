package DictionaryServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class DictionaryServer {

	public static void main(String[] args) {

		ServerSocket listeningSocket = null;
		Socket clientSocket = null;

		try {
			// Create a server socket listening on port 4444
			listeningSocket = new ServerSocket(4444);
			int i = 0; // counter to keep track of the number of clients

			// Listen for incoming connections for ever
			while (true) {
				System.out.println("Server listening on port 4444 for a connection");
				// Accept an incoming client connection request
				clientSocket = listeningSocket.accept(); // This method will block until a connection request is
															// received
				i++;

				logConnection(i, clientSocket.getLocalPort(), clientSocket.getInetAddress().getHostName());

				// Get the input/output streams for reading/writing data from/to the socket
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

				executeThread(i, in, out);

				clientSocket.close();

			}
		} catch (SocketException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			System.out.println("here");
			e.printStackTrace();
		} finally {
			if (listeningSocket != null) {
				try {
					// close the server socket
					listeningSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void logConnection(int clientNumber, int port, String hostname) {
		System.out.println("Client conection number " + clientNumber + " accepted:");
		// System.out.println("Remote Port: " + clientSocket.getPort());
		System.out.println("Remote Hostname: " + hostname);
		System.out.println("Local Port: " + port);
	}

	private void executeThread(int clientNumber, BufferedReader inputStream, BufferedWriter outputStream)
			throws IOException {
		String clientMsg = null;
		try {
			while ((clientMsg = inputStream.readLine()) != null) {
				System.out.println("Message from client " + clientNumber + ": " + clientMsg);
				outputStream.write("Server Ack " + clientMsg + "\n");
				outputStream.flush();
				System.out.println("Response sent");
			}
			System.out.println("Server closed the client connection - received null");
		}

		catch (SocketException e) {
			System.out.println("closed...");
		}
	}

}
