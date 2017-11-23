package server;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import java.net.ServerSocket;

public class ServerThread extends Thread{
	private OutputStream out;
	private InputStream in;
	private Socket c;
	public ServerThread(Socket client, ServerSocket server){
		try {
			c = client;
			out = client.getOutputStream();
			in = client.getInputStream();
			this.start();			
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	public void run(){
		
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String firstLine;
		try {
			firstLine = br.readLine();
			System.out.println(firstLine);
			boolean exist = false;
			if(firstLine !=null){
				String fileName = firstLine.split(" ")[1];
				String baseDir = System.getProperty("user.dir");
				System.out.println(baseDir);
				File f = new File(baseDir+fileName);
				InputStream fileIn=null;

				String server = "Java Http Server";
				String statusLine = "HTTP/1.1 404 Not Found" + "\r\n" ;
				String typeLine = "Content-Type: text/HTML  \r\n";
				String lengthLine = "Content-Length: 90  \r\n";
				String body = "<html><head><title>404 not found</title></head><body>404 not found</body></html>";
				if(f.exists() && !f.isDirectory()) { 
					fileIn = new FileInputStream(f);
					exist = true;
					statusLine = "HTTP/1.0 200 OK" + "\r\n";
					typeLine = "Content-type: html/text  \r\n";
					lengthLine = "Content-Length: " + (new Integer(fileIn.available())).toString() + "\r\n";
				}
				//write the headers
				out.write(statusLine.getBytes());
				out.write(server.getBytes());
				out.write(typeLine.getBytes());
				out.write(lengthLine.getBytes());
				out.write("\r\n".getBytes());
				
				if(exist){//write back file content if exist
					byte[] buffer = new byte[1024];	
					int bytes = 0;
					while ((bytes = fileIn.read(buffer)) != -1) {
						out.write(buffer, 0, bytes);
					}
				}else{//write 404 body
					out.write(body.getBytes());
				}
				out.flush();
				c.close();
				
			}
			
		} catch (IOException e) {
			System.out.println("Exception at server thread run");
			e.printStackTrace();
		}
		
	}
}
