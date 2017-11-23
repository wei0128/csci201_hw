package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class BjClient extends Thread{
	private BufferedReader br;
	private PrintWriter pw;
	private Scanner sc;

	private int option = 0;
	private String username = null;
	private String gameName = null;
	private int playerNum = 0;
	
	//Client constructor
	//Obtain all information regarding the client such as their options and username
	//Start thread at the very end
	//While loop at end to allow input
	public BjClient() {
		sc = new Scanner(System.in);
		boolean success = false;
		boolean successInput = false;
		String ipaddress = null;
		int port = 0;
		//Check for overall success
		while(!success) {
			//Checking input success
			while(!successInput) {
				System.out.println("Please enter the ipaddress");
				ipaddress = sc.next();
				System.out.println("Please enter the port");
				//Only move forward if port is actually integer
				if(!sc.hasNextInt()) {
					System.out.println("Unable to connect to server with provided fields int");
					sc.next();
				}
				else {
					port = sc.nextInt();
					successInput = true;
				}
				
			}
			//checking connection
			try {
				Socket s = new Socket(ipaddress, port);
				//success only if move past this part
				success=true;
				br = new BufferedReader (new InputStreamReader(s.getInputStream()));
				pw = new PrintWriter(s.getOutputStream());
			} catch(IOException ioe) {
				//Catch IOException   Input not successful
				System.out.println("Unable to connect to server with provided fields");
				successInput = false;
			} catch(IllegalArgumentException iae) {
				//Check illegal Argument for port range  Input not successful
				System.out.println("Unable to connect to server with provided fields");
				successInput=false;
			}
		}
		//menu
		while(option!=1 && option !=2) {
			System.out.println("Please choose from the options below\n1) Start Game\n2) Join Game");
			while(!sc.hasNextInt()) {
				System.out.println("Invalid choice!");
				System.out.println("Please choose from the options below\n1) Start Game\n2) Join Game");
				sc.next();
			}
			//get the option
			this.option = sc.nextInt();
			//check if within menu range  if not, ask user to re-enter
			if(option != 1 && option!=2) {
				System.out.println("Invalid choice!");
			}
		}
		//give this option to the thread
		pw.println(option);
		pw.flush();
		
		if(option == 1) {
			createGame();
		} //end option 1
		//option 2 , join game
		else {
			joinGame();
		}//end option 2
		this.start();
		while(true) {
			pw.println(sc.next());
			pw.flush();
		}
	}
	
	//Creating Game
	//Ask for number of player, game name, and username
	//Informatino send to server via printwriter
	private void createGame() {
		//get the playerNumber
		while(!(playerNum>=1 && playerNum<=3)) {
			System.out.println("Please choose the number of players in the game(1-3)");
			while(!sc.hasNextInt()) {
				System.out.println("Invalid choice!");
				System.out.println("Please choose the number of players in the game(1-3)");
				sc.next();
			}
			//get the option
			this.playerNum = sc.nextInt();
			//check if within menu range  if not, ask user to re-enter
			if(!(playerNum>=1 && playerNum<=3)) {
				System.out.println("Invalid choice!");
			}
		}//end getplayerNumber
		//give the playernumber to thread
		pw.println(playerNum);
		pw.flush();
		
		
		//Choosing game name
		boolean created = false;
		try {
			while(!created) {
				System.out.println("Please choose a name for your game");
				this.gameName = sc.next();
				while(gameName.length()==0 || gameName == null) {
					System.out.println("Name cannot be empty!");
					System.out.println("Please choose a name for your game");
					this.gameName = sc.next();
				}
				pw.println(gameName);
				pw.flush();
				String reply = br.readLine();
				if(reply.equals("available")) {
					created = true;	
				}
				else {
					System.out.println("Invalid choice. This game name has already been chosen by another user");
				}
				
			}//end check game name
		}catch(IOException ioe) {
			System.out.println("ioe in gameName " + ioe.getMessage());
		}
		
		//setting username
		System.out.println("Please choose a username");
		username = sc.next();
		while(username==null || username.length()==0) {
			System.out.println("Name cannot be empty!");
			System.out.println("Please choose a username");
			username=sc.next();
		}
		//sending username
		pw.println(username);
		pw.flush();
	}
	
	
	//Join Game
	//Ask for game name and user name
	//Information sent to server via print writer
	private void joinGame() {
		boolean joined = false;
		//Loop until finding a game available to join
		while(!joined) {
			System.out.println("Please enter the name of the game you wish to join");
			gameName = sc.next();
			while(gameName.length()==0 || gameName==null) {
				System.out.println("Name cannot be empty!");
				System.out.println("Please enter the name of the game you wish to join");
				gameName = sc.next();
			}
			pw.println(gameName);
			pw.flush();
			String reply = null;
			try {
				reply = br.readLine();
			}catch(IOException ioe) {
				System.out.println("ioe in join gameName " + ioe.getMessage());
			}
			
			if(reply.equals("available")){
				joined = true;					
			}
			else {
				System.out.println("Invalid choice. There are no ongoing games with this name");
			}
		}//end joined check
		//setting username
		boolean nameSet = false;
		while(!nameSet) {
			System.out.println("Please choose a username");
			username = sc.next();
			while(username==null || username.length()==0) {
				System.out.println("Name cannot be empty!");
				System.out.println("Please choose a username");
				username=sc.next();
			}
			//sending username
			pw.println(username);
			pw.flush();
			String reply = null;
			try {
				reply = br.readLine();
			}catch(IOException ioe) {
				System.out.println("ioe in gameName " + ioe.getMessage());
			}
			
			if(reply.equals("available")) {
				nameSet = true;					
			}
			else {
				System.out.println("Invalid choice. This username has already been chosen by another player in this game");
			}
		}	
	}
	
	public static void main(String [] args) {
		System.out.println("Welcome to Black Jack!");
		BjClient bjc = new BjClient();
	}
	
	//Thread
	//Constantly listen to message from server and output to console
	public void run() {
		try {
			while(true) {
				String line = br.readLine();
				if(line.equals("end")) {
					System.exit(0);
				}
				System.out.println(line);
			}
		}catch(IOException ioe) {
			System.out.println("ioe in ChatClient run: " +ioe.getMessage());
		}
	}	
}
