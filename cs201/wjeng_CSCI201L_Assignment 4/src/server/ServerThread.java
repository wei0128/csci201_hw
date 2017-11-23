package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread{
	private PrintWriter pw;
	private BufferedReader br;
	private BjServer bjs;
	private BjGame bjg;
	String gameName = null;
	String option = null;
	String playerNum =null;
	String username = null;
	
	//ServerThread Constructor
	//Takes in socket and server
	//Set a server instance
	//Connect printwriter and reader to socket
	//start the thread
	public ServerThread(Socket s, BjServer bjs) {
		try {
			this.bjs = bjs;
			pw = new PrintWriter(s.getOutputStream());
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.start();
		}catch(IOException ioe) {
			System.out.println("ioe in Server Thread constructor: "+ioe.getMessage());
		}
	}//end of constructor
	
	//SendMessage
	//Input string message
	//Output: none
	//Send message to associated client
	public void sendMessage(String message) {
		pw.println(message);
		pw.flush();
	}
	
	//getMessage
	//get message from client input
	//return the message as string
	public String getMessage() {
		String message = null;
		try {
			message = br.readLine();
		}catch(IOException ioe) {
			System.out.println("ioe in getMessage: "+ioe.getMessage());
		}
		return message;
	}
	
	//thread implementation
	//construct game 
	public void run() {
		//getting option from client
		try {
			option =br.readLine();
		}catch(IOException ioe) {
			System.out.println("ioe is serverthread option" + ioe.getMessage());
		}
		if(option.equals("1")) {
			//getting player number from client
			try {
				playerNum = br.readLine();
			}catch(IOException ioe) {
				System.out.println("ioe is serverthread playernum" + ioe.getMessage());
			}
			//getting game name from client
			boolean exist = false;
			boolean gameCreated = false;
			while(!gameCreated) {
				try {
					gameName=br.readLine();
				}catch(IOException ioe) {
					System.out.println("ioe in Server thread gamename: "+ ioe.getMessage());
				}
				//reset boolean
				exist=false;
				//check existence
				exist = bjs.checkGameExist(gameName);
				if(!exist) {
					sendMessage("available");					
					gameCreated=true;	

					bjg = new BjGame(playerNum, gameName, this);					
					bjs.addGame(bjg);
				}
				else {
					sendMessage("fail");
				}
			}//end while check
			//getting username
			try {
				username = br.readLine();
			}catch(IOException ioe) {
				System.out.println("ioe in Server thread username: "+ ioe.getMessage());
			}

			bjg.addCreator(username);
			

		}
		//option 2 join game
		else {
			bjg = null;
			//getting game name to join
			boolean exist = false;
			boolean gameJoined =false;
			while(!gameJoined) {
				exist = false;
				try {
					gameName = br.readLine();
				}catch(IOException ioe) {
					System.out.println("ioe in join game name: "+ioe.getMessage());
				}
				exist = bjs.checkGameReadyToJoin(gameName);
				if(exist) {
					sendMessage("available");
					gameJoined = true;
					bjg = bjs.findGame(gameName);
					bjg.incrementPlayer();
				}
				else {
				sendMessage("fail");
				}
			}	//end join check		
			
			//getting username
			
			exist = false;
			while(!exist) {
				exist=false;
				try {
					username=br.readLine();
				}catch(IOException ioe) {
					System.out.println("ioe in Server thread username: "+ ioe.getMessage());
				}
				exist=bjg.checkName(username);
				//add player to game if username not exist in game
				if(!exist) {
					pw.println("available");
					pw.flush();
					exist = true;
					
				}
				else {
					pw.println("fail");
					pw.flush();
					exist=false;
				}
			}
		bjg.addPlayer(this, username);
		}//end option 2
	}//end run
}
