package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
	public static void main(String[] args) throws IOException{
		ServerSocket webServer = new ServerSocket(1024);
		System.out.println("Server start on port 1024...");
		while(true){
			Socket client = webServer.accept();
			//no need to network so doesn't need to manage the thread
			new ServerThread(client, webServer);
		}
	}
	
}
