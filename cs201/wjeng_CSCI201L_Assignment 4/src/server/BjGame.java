package server;

import java.util.Collections;
import java.util.Set;
import java.util.Vector;

public class BjGame {
	private String gameName;
	private int playerNum;
	private int playerIn = 0;
	private boolean waiting = true;
	private Vector<ServerThread> playerList = new Vector<ServerThread>();
	private Vector<String> playerNames = new Vector<String>();
	private Vector<Integer> chips = new Vector<Integer>();
	private Vector<Integer> currentBet = new Vector<Integer>();
	private Vector<Vector<String>> gameFace = new Vector<Vector<String>>();
	private Vector<String> gameStatus = new Vector<String>();
	private Vector<Integer> finalValue = new Vector<Integer>();
	Vector<String> cards;
	String firstLoser;
	String lastWinner = "DEALER";
	
	//Constructor of game
	//Input: player number, game name, and the creator thread
	//Set up the name, parse the number into int, and add creator the player list
	//Initiate chip amount based on the player amount
	public BjGame(String playerNum, String gameName, ServerThread st) {
		this.gameName = gameName;
		int pn = Integer.parseInt(playerNum);
		this.playerNum = pn;
		for(int i = 0; i < pn; i++) {
			chips.add(500);
			currentBet.add(0);
		}
		for(int i = 0; i < pn+1; i++) {
			gameStatus.add("");
			finalValue.add(0);
		}
		playerList.add(st);
		playerIn += 1;
	}
	
	//Actual game of black jack
	public void game() {
		broadCast("Let the game commence. Good luck to all players!");
		int round = 1;
		do {
			broadCast("ROUND " + round);
			round+=1;
			broadCast("Dealer is shuffling cards..");
			resetGame();
			//call getBet
			getBet();	
			//call dealHand
			dealHand();
			printBoard();
			//each player's turn
			for(int j = 0; j < playerIn; j++) {
				takesTurn(j);
			}
			dealerTurn();
			checkResult();
		}while(checkGameEnds());
		//Broadcasting winner and loser
		broadCast("Winner of the game: " + lastWinner);
		broadCast("Loser of the game: " + firstLoser);
		//signal client to close down
		broadCast("end");
	}//end of game
	
	
	//Game Name getter
	public String getGameName() {
		return gameName;
	}
	
	//Boolean
	//Input = username
	//Check if a name exist in the game
	//Return true if so, false otherwise
	public boolean checkName(String username) {
		for(String name : playerNames) {
			if(name.equals(username)) {
				return true;
			}
		}
		return  false;
	}
	
	//Boolean
	//No input
	//Return true if game is waiting for player to join
	//False if it has started
	public boolean waiting() {
		if(playerIn!=playerNum && playerNames.size()!=0 && waiting) {
			return true;
		}
		return false;
	}
	
	//Increment player number currently in game
	//Call when a player find a game that is not full
	//Ensure position before entering username
	public void incrementPlayer() {
		playerIn += 1;
	}
	
	//Send message to the creator regarding new incoming player
	public void updateCreator() {
		ServerThread creator = playerList.get(0);
		creator.sendMessage(playerNames.get(playerNames.size()-1) +" has joined the game");
		int remaining = playerNum - playerList.size();
		if(remaining == 1) {
			creator.sendMessage("Waiting for 1 other player to join...");
		}
		else if(remaining >1){
			creator.sendMessage("Waiting for " + remaining +" other players to join...");
		}
		
	}
	
	//Add the creator's username after the creation of the game
	//This was made two step to ensure game exist before the creator has to enter his / her name
	//prevent room name taken mid construction
	//Add creator, check the number of player against exisiting player
	//Start game if match
	//Print waiting if not
	public void addCreator(String username) {
		playerNames.add(username);
		if(playerNum==1) {
			game();
		}
		else if(playerNum==2) {
			playerList.get(0).sendMessage("Waiting for 1 other player to join...");
		}
		else {
			playerList.get(0).sendMessage("Waiting for " +(playerNum-1) +" other players to join...");
		}
	}
	
	//Add subsequent player after the creator
	//Send message to new player and update the creator
	//If addition of new player cause game to be full, initiate the game
	//Input, incoming thread and its username
	//Output: nothing
	public void addPlayer(ServerThread st, String username) {
		playerList.add(st);
		playerNames.add(username);
		st.sendMessage("The game will start shortly. Waiting for other players to join...");
		updateCreator();
		if(playerNum == playerList.size()) {
			waiting = false;
			game();
		}
	}
	


	
	//Check result 
	//Send message of status
	//Deduct chip if losing
	public void checkResult() {
		int dealerValue = finalValue.get(playerNum);
		//go through each player
		for(int i = 0; i < playerNum ; i++) {
			//busted case
			if(finalValue.get(i) > 21) {
				for(ServerThread st: playerList) {
					if(st == playerList.get(i)) {
						st.sendMessage("You busted. " + currentBet.get(i) + " chips were deducted from your total");
					}
					else {
						st.sendMessage(playerNames.get(i) + " busted. " + currentBet.get(i) + " chips were deducted from " + playerNames.get(i) + "'s total");
					}
				}
				playerList.get(i).sendMessage("bet " + currentBet.get(i) + " chips " + chips.get(i));
				chips.set(i, chips.get(i) - currentBet.get(i));
			}//end busted case
			//blackjack case
			else if(finalValue.get(i) == 21) {
				if(dealerValue == 21) {
					for(ServerThread st: playerList) {
						if(st == playerList.get(i)) {
							st.sendMessage("You tied with the dealer. Your total remains the same");
						}
						else {
							st.sendMessage(playerNames.get(i) + " tied with the dealer. " + playerNames.get(i) + "'s chip total remains the same");
						}
					}
				}
				else {
					for(ServerThread st: playerList) {
						if(st == playerList.get(i)) {
							st.sendMessage("You had blackjack. " + (currentBet.get(i)*2) + " chips were added to your total");
						}
						else {
							st.sendMessage(playerNames.get(i) + " had blackjack. "  + (currentBet.get(i)*2) + " chips were added to " + playerNames.get(i) + "'s total");
						}
					}
					chips.set(i, chips.get(i) + (currentBet.get(i)*2));
				}
			}//end blackjack case
			//smaller than 21 cases
			else {
				//dealer busted
				if(dealerValue > 21) {
					for(ServerThread st: playerList) {
						if(st == playerList.get(i)) {
							st.sendMessage("Dealer busted. " + currentBet.get(i) + " chips were added to your total");
						}
						else {
							st.sendMessage("Dealer busted. " + currentBet.get(i) + " chips were added to " + playerNames.get(i) + "'s total");
						}
					}
					chips.set(i, chips.get(i) + currentBet.get(i));
				}
				//smaller than dealer
				else if(finalValue.get(i)< dealerValue) {
					for(ServerThread st: playerList) {
						if(st == playerList.get(i)) {
							st.sendMessage("You had a sum smaller than dealer's. "  + currentBet.get(i) + " chips were deducted from your total");
						}
						else {
							st.sendMessage(playerNames.get(i) + " had a sum smaller than dealer's. " + currentBet.get(i) + " chips were deducted from " + playerNames.get(i) + "'s total");
						}
					}
					chips.set(i, chips.get(i) - currentBet.get(i));
				}
				//equal to dealer
				else if(finalValue.get(i) == dealerValue) {
					for(ServerThread st: playerList) {
						if(st == playerList.get(i)) {
							st.sendMessage("You tied with the dealer. Your total remains the same");
						}
						else {
							st.sendMessage(playerNames.get(i) + " tied with the dealer. " + playerNames.get(i) + "'s chip total remains the same");
						}
					}
				}
				//bigger than dealer
				else {
					for(ServerThread st: playerList) {
						if(st == playerList.get(i)) {
							st.sendMessage("You had a sum greater than the dealer's. " + currentBet.get(i) + " chips were added to your total");
						}
						else {
							st.sendMessage(playerNames.get(i) + " had a sum greater than the dealer's. "  + currentBet.get(i) + " chips were added to " + playerNames.get(i) + "'s total");
						}
					}
					chips.set(i, chips.get(i) + currentBet.get(i));
				}
			}
		}
		
	}
	
	
	//checkGameEnds
	//check if the game ends
	//return false if  a player has 0
	//true if no player has 0
	public boolean checkGameEnds() {
		int countOfZero = 0;
		boolean loser = false;
		int max = 0;
		for(int i = 0; i < playerNum; i++) {
			if(chips.get(i) <= 0) {
				countOfZero +=1;
				if(!loser) {
					firstLoser = playerNames.get(i);
					loser=true;
				}
			}
			//update on max chip
			if(chips.get(i)>max) {
				max = chips.get(i);
				lastWinner = playerNames.get(i);
			}
			if(countOfZero == playerNum && playerNum !=1) {
				lastWinner = "DEALER";
				firstLoser = "EVERYONE";
			}
		}
		if(playerNum == 1) {
			
			lastWinner = "DEALER";
		}
		if(countOfZero > 0) {
			return false;
		}
		else {
			return true;
		}	
	}
	
	
	
	//dealer's turn
	//If hand size < 17, continue hitting
	//Print final state
	public void dealerTurn() {
		for(ServerThread st: playerList) {
			st.sendMessage("It is now time for the dealer to play.");
		}
		//reveal the covered card
		gameFace.get(playerNum).set(0, cards.get(0));
		cards.remove(0);
		//Determine the hand size
		Vector<Integer> dealerHand = determineHand(playerNum, false);
		int maxValue = Collections.max(dealerHand);
		int count = 0;
		//if hand size < 17, deal more cards
		while(maxValue < 17) {
			gameFace.get(playerNum).add(cards.get(0));
			String card = "the " + cards.get(0);
			cards.remove(0);
			dealerHand = determineHand(playerNum, false);
			maxValue = Collections.max(dealerHand);
			count +=1;
			//State the # of time he hits and the card dealt at that time
			for(ServerThread st: playerList) {
				if(count == 1) {
					st.sendMessage("The dealer hit " + count +" time. They were dealt: " + card);
				}
				else {
					st.sendMessage("The dealer hit " + count +" times. They were dealt: " + card);
				}
			}
		}
		//check dealer's state
		if(gameStatus.get(playerNum).equals("bust")) {
			for(ServerThread st: playerList) {
				st.sendMessage("DEALER busted!");
			}
		}else {
			for(ServerThread st: playerList) {
				st.sendMessage("DEALER stayed.");
			}
		}
		finalHand(playerNum);
		printPlayer(playerNum);
	}
	
	
	//player's turn
	//take player code as input
	//ask for input from player
	//print out final state
	public void takesTurn(int playerCode) {
		String option = "0";
		for(ServerThread st: playerList) {
			if(st==playerList.get(playerCode)) {
				st.sendMessage("It is your turn to add cards to your hand");
				
			}
			else {
				st.sendMessage("It is " + playerNames.get(playerCode) +"'s turn to add cards to their hand.");
			}
		}
		//while not staying keeps on asking for input
		while(true) {
			for(ServerThread st: playerList) {
				if(st == playerList.get(playerCode)) {
					st.sendMessage("Enter either '1' or 'stay' to stay. Enter either '2' or 'hit' to hit.");
					option = st.getMessage();
				}
			}		
			//deal one card if hit
			if(option.equals("2") || option.equals("hit")) {
				gameFace.get(playerCode).add(cards.get(0));
				cards.remove(0);
			}
			else if(option.equals("1") || option.equals("stay")){
				break;
			}
			//message
			for(ServerThread st: playerList) {
				if(st == playerList.get(playerCode)) {
					st.sendMessage("You hit. You were dealt the " + gameFace.get(playerCode).lastElement());
				}
				else {
					st.sendMessage(playerNames.get(playerCode) + " hit. They were dealt the " + gameFace.get(playerCode).lastElement());
				}
			}	
			//determine hand to check if they busted
			determineHand(playerCode, false);
			if(gameStatus.get(playerCode).equals("bust")) {
				for(ServerThread st: playerList) {
					if(st==playerList.get(playerCode)) {
						st.sendMessage("You busted! You lose " + currentBet.get(playerCode) + " chips.");
					}
					else {
						st.sendMessage(playerNames.get(playerCode) +" busted! They lose " + currentBet.get(playerCode) + " chips.");
					}
				}
				finalHand(playerCode);
				printPlayer(playerCode);
				return;
				
			}			
		}
		for(ServerThread st: playerList) {
			if(st==playerList.get(playerCode)) {
				st.sendMessage("You stayed.");
				
			}
			else {
				st.sendMessage(playerNames.get(playerCode) + "stayed.");
			}	
		}
		//update their final value
		finalHand(playerCode);
		printPlayer(playerCode);
	}
	
	
	
	//Determine hand value
	//Takes in player code
	//check the value from String
	//return a vector of integers that represents least amount of legal status
	public Vector<Integer> determineHand(int playerCode, boolean stay) {
		Vector<Integer> handValue = new Vector<Integer>();
		//Initiate two 0s to represent possible combination considering ace
		handValue.add(0);
		handValue.add(0);
		Vector<String> hand = gameFace.get(playerCode);
		boolean haveAce = false;
		//parsing the string of card
		//for each card, determine its value and add it to the vector
		for(String card: hand) {
			String[] words = card.split(" ");
			if(words[0].equals("ACE")&& !haveAce) {
				int tempValue = handValue.get(0) + 1;
				int tempValue2 = handValue.get(1) + 11;
				handValue.set(0, tempValue);
				handValue.set(1, tempValue2);
				haveAce = true;
			}
			else if(words[0].equals("ACE") && haveAce) {
				int tempValue = handValue.get(0) + 1;
				int tempValue2 = handValue.get(1) + 1;
				handValue.set(0, tempValue);
				handValue.set(1, tempValue2);
			}
			else if(words[0].equals("TWO")) {
				int tempValue = handValue.get(0) + 2;
				int tempValue2 = handValue.get(1) + 2;
				handValue.set(0, tempValue);
				handValue.set(1, tempValue2);
			}
			else if(words[0].equals("THREE")) {
				int tempValue = handValue.get(0) + 3;
				int tempValue2 = handValue.get(1) + 3;
				handValue.set(0, tempValue);
				handValue.set(1, tempValue2);
			}
			else if(words[0].equals("FOUR")) {
				int tempValue = handValue.get(0) + 4;
				int tempValue2 = handValue.get(1) + 4;
				handValue.set(0, tempValue);
				handValue.set(1, tempValue2);
			}
			else if(words[0].equals("FIVE")) {
				int tempValue = handValue.get(0) + 5;
				int tempValue2 = handValue.get(1) + 5;
				handValue.set(0, tempValue);
				handValue.set(1, tempValue2);
			}
			else if(words[0].equals("SIX")) {
				int tempValue = handValue.get(0) + 6;
				int tempValue2 = handValue.get(1) + 6;
				handValue.set(0, tempValue);
				handValue.set(1, tempValue2);
			}
			else if(words[0].equals("SEVEN")) {
				int tempValue = handValue.get(0) + 7;
				int tempValue2 = handValue.get(1) + 7;
				handValue.set(0, tempValue);
				handValue.set(1, tempValue2);
			}
			else if(words[0].equals("EIGHT")) {
				int tempValue = handValue.get(0) + 8;
				int tempValue2 = handValue.get(1) + 8;
				handValue.set(0, tempValue);
				handValue.set(1, tempValue2);
			}
			else if(words[0].equals("NINE")) {
				int tempValue = handValue.get(0) + 9;
				int tempValue2 = handValue.get(1) + 9;
				handValue.set(0, tempValue);
				handValue.set(1, tempValue2);
			}
			else if(words[0].equals("TEN") || words[0].equals("JACK") || words[0].equals("QUEEN") || words[0].equals("KING")) {
				int tempValue = handValue.get(0) + 10;
				int tempValue2 = handValue.get(1) + 10;
				handValue.set(0, tempValue);
				handValue.set(1, tempValue2);
			}
		}
		//determine number of status to display
		//if not have ace, second value busted, or first value busted,  only show the lowest result
		if(!haveAce || ( haveAce && handValue.get(0)<=21 && handValue.get(1)>21) || (haveAce && handValue.get(0)>21)) {
			handValue.remove(1);
		}
		else if(handValue.get(0)==21) {
			handValue.remove(1);
		}
		else if(handValue.get(1)==21 || (stay && handValue.get(1)<21)) {
			handValue.remove(0);
		}
		//set the status
		setStatus(handValue, playerCode);
		return handValue;
	}
	
	
	
	//finalhand
	//if there are between status, pick the largest legal one
	//set the final value
	public void finalHand(int playerCode) {
		Vector<Integer> hand = determineHand(playerCode, true);
		finalValue.set(playerCode, hand.get(0));
	}
	
	
	//Setting status of player according to their hand
	//Takes in the player's code and their hand values
	//return nothing
	public void setStatus(Vector<Integer> handValue, int playerCode) {
		if(handValue.size() == 1) {
			if(handValue.get(0)==21) {
				gameStatus.set(playerCode, "blackjack");
			}
			else if(handValue.get(0)>21) {
				gameStatus.set(playerCode, "bust");
			}
		}
		else {
			gameStatus.set(playerCode, "");
		}
	}
	
	//takes in player code 
	//print out board for single player
	//returns nothing
	public void printPlayer(int playerCode) {
		for(ServerThread st: playerList) {
			
			st.sendMessage("--------------------------------------------------------------------------------");
			if(playerCode < playerNum) {
				st.sendMessage("Players: " + playerNames.get(playerCode));
			}
			else {
				st.sendMessage("DEALER");
			}
			st.sendMessage("");
			String hand = "";
			for(int j = 0; j < gameFace.get(playerCode).size(); j++) {
				if(j!=gameFace.get(playerCode).size()-1) {
					hand += " | " + gameFace.get(playerCode).get(j);
				}
				else {
					hand += " | " + gameFace.get(playerCode).get(j) + " |";
				}
			}
			//getting status
			Vector<Integer> handValue = determineHand(playerCode, true);
			
			if(gameStatus.get(playerCode).length()>0 && handValue.size()==1) {
				String message = handValue.get(0) + " - " + gameStatus.get(playerCode);
				st.sendMessage("Status: "+ message);
			}
			else if(handValue.size()==1) {
				st.sendMessage("Status: "+handValue.get(0));
			}
			else {
				String message = handValue.get(0) + " or " + handValue.get(1);
				st.sendMessage("Status: " + message);
			}
			st.sendMessage("Cards: " + hand);
			st.sendMessage("--------------------------------------------------------------------------------");
		}
		
		
	}
	//Inspect the game face status for first time
	//Print out the card each player have
	public void printBoard() {
		for(ServerThread st: playerList) {
			//dealer's part
			st.sendMessage("--------------------------------------------------------------------------------");
			st.sendMessage("DEALER");
			st.sendMessage("\n");
			String hand = "";
			for(int i=0; i < gameFace.get(playerIn).size();i++) {
				
				if(i!=gameFace.get(playerIn).size()-1) {
					hand += " | " + gameFace.get(playerIn).get(i);
				}
				else {
					hand += " | " + gameFace.get(playerIn).get(i) + " |";
				}
			}
			st.sendMessage("Cards: " + hand);
			st.sendMessage("--------------------------------------------------------------------------------");
			
			//other player's part
			for(int i=0; i< playerIn; i++) {
				st.sendMessage("--------------------------------------------------------------------------------");
				st.sendMessage("Players: " + playerNames.get(i));
				st.sendMessage("");
				hand = "";
				for(int j = 0; j < gameFace.get(i).size(); j++) {
					if(j!=gameFace.get(i).size()-1) {
						hand += " | " + gameFace.get(i).get(j);
					}
					else {
						hand += " | " + gameFace.get(i).get(j) + " |";
					}
				}
				//getting status
				Vector<Integer> handValue = determineHand(i, false);
				
				if(gameStatus.get(i).length()>0 && handValue.size()==1) {
					String message = handValue.get(0) + " - " + gameStatus.get(i);
					st.sendMessage("Status: "+ message);
				}
				else if(handValue.size()==1) {
					st.sendMessage("Status: "+handValue.get(0));
				}
				else {
					String message = handValue.get(0) + " or " + handValue.get(1);
					st.sendMessage("Status: " + message);
				}
				st.sendMessage("Cards: " + hand);
				st.sendMessage("--------------------------------------------------------------------------------");
			}
		}
		
	}
	
	//Get bet from the player
	//Send message from thread and receive reply from thread
	//Set the bet amount to the bet vector at corresponding position
	public void getBet() {
		//betting round
		for(int k =0; k < playerIn; k++) {
			playerList.get(k).sendMessage(playerNames.get(k)+", it is your turn to make a bet. You chip"
					+ " total is " + chips.get(k));
			for(int j = 0; j < playerIn; j++) {
				if(j!=k) {
					playerList.get(j).sendMessage("It is " + playerNames.get(k)+ "'s turn to make a bet.");
				}
			}
			//getting bet from player k
			String betString = playerList.get(k).getMessage();
			int betAmount = Integer.parseInt(betString);
			//Setting bet
			currentBet.set(k, betAmount);
			//state bet amount
			playerList.get(k).sendMessage("You bet "+ betAmount + " chips");
			for(int j = 0; j < playerIn; j++) {
				if(j!=k) {
					playerList.get(j).sendMessage(playerNames.get(k) + " bet " + betAmount + " chips");
				}
			}
		}
	}
	
	//Add two cards to each player and a covered card to dealer
	//Always add the first card from the deck and remove it afterward
	public void dealHand() {
		//dealing cards
		Deck deck = new Deck();
		cards = deck.getDeck();
		//Vector of hands  (1 to playerNum = players  the last one is dealer)
		gameFace = new Vector<Vector<String>>();
		for(int k = 0; k < playerIn; k++) {
			Vector<String> hand = new Vector<String> ();
			String card = cards.get(0);
			hand.add(card);
			cards.remove(0);
			card = cards.get(0);
			hand.add(card);
			cards.remove(0);
			gameFace.add(hand);
		}
		Vector<String> dealerHand = new Vector<String>();
		dealerHand.add("?");
		String card = cards.get(0);
		dealerHand.add(card);
		cards.remove(0);
		gameFace.add(dealerHand);
	}
	
	//Reseting game
	//Clear game face
	//Clear bet
	//Clear status
	public void resetGame() {
		for(int i =0; i< gameFace.size(); i++) {
			gameFace.remove(0);
		}
		
		for(int i = 0; i < playerIn; i++) {
			currentBet.set(i, 0);
		}
		for(int i = 0; i < gameStatus.size(); i++) {
			gameStatus.set(i, "");
		}
		for(int i = 0; i < finalValue.size(); i++){
			finalValue.set(i, 0);
		}
	}
	
	//Broadcasting message to all players
	public void broadCast(String message) {
		for(ServerThread st: playerList) {
			st.sendMessage(message);
		}
	}
	
}
