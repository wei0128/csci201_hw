package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;
import client.BjClient;

public class BjServer {
	private Vector<ServerThread> serverThreads;
	private static Vector<BjGame> games = new Vector<BjGame>();
	private PrintWriter pw;
	private BufferedReader br;
	public BjServer() {
		Scanner sc = new Scanner(System.in);
		
		boolean success = false;
		//loop until valid port
		while(!success) {
			System.out.println("Please enter a port");
			while(!sc.hasNextInt()) {
				System.out.println("Invalid port number");
				sc.next();
			}
			int port = sc.nextInt();
			try {
				ServerSocket ss = new ServerSocket(port);
				System.out.println("Successfully started the Black jack server on port " + port);
				success=true;
				serverThreads = new Vector<ServerThread>();
				//accepting socket and add to vector
				while(true) {
					Socket s = ss.accept();
					ServerThread st = new ServerThread(s, this);
				}
					
			} catch(IOException ioe){
				System.out.println("Invalid port number ioe " + ioe.getMessage());
				success=false;
			} catch(IllegalArgumentException iae) {
				System.out.println("Invalid port number ale");
				success=false;
			}
			
		}
		sc.close();	
	}
	

	//Check game exist
	//return true if game name already exit
	//false otherwise
	public boolean checkGameExist(String gameName) {
		for(BjGame g : games) {
			if(g.getGameName().equals(gameName)){
				return true;
			}
		}
		return false;
	}
	
	//check if game is reaady to join
	//return true if ready
	//false otherwise
	public boolean checkGameReadyToJoin(String gameName) {
		for(BjGame g: games) {
			if(g.getGameName().equals(gameName)&&g.waiting()) {
				return true;
			}
		}
		return false;
	}
	
	//Find instance of a game
	//return the game instance
	public BjGame findGame(String gameName) {
		BjGame bjg= null;
		for(BjGame g : games) {
			if(g.getGameName().equals(gameName)){
				bjg = g;
			}
		}
		return bjg;
	}
	
	//add a gmae instance
	public void addGame(BjGame newGame) {
		games.add(newGame);
	}
	
	public static void main(String [] args) {
		System.out.println("Welcome to the Black Jack Server!");
		BjServer bjs = new BjServer();
	}
		
}
