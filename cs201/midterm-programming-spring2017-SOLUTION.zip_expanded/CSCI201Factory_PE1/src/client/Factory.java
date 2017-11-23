/*
 * Factory.java
	•	This class implements Serializable, which means it can be packaged and sent as data.
	•	At the top of the file are its data members. All members without the keyword “transient” are included in the object when it is sent. They are stored similar to key-value pairs, for example the key “width” will have a corresponding int value that is set somewhere in Factory.java (in this case, its constructor).
	•	The first constructor (there are two) is called.
 
 	send(Message message):
	•	String json = mGson.toJson(message): the message passed into send() is converted into JSON format by Google’s convenient Gson library. Web protocols can only send certain data formats and browsers can parse only these formats. JSON is the one (the most popular), XML is another.
	•	mWebSocketEndpoint.sendToSession(mSession, json): calls WebSocketEndpoint.java’s sendToSession() method to send the JSON message over the WebSocket to be received by WebSocketEndpoint.js.

	listen():
	•	JsonObject msg…: creates a JSONObject out of the action string received from socket.send().
	•	Action action…: calls ActionFactory.java’s getAction() method
	•	if (action != null)…: if the getAction() method returned an Action, the action exists so call its execute() method. We check if it exists because the client can easily write their own JavaScript to send a non-existent Action.
 */


package client;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

import javax.websocket.Session;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import actions.Action;
import actions.ActionFactory;
import messages.FactoryMessage;
import messages.Message;
import messages.UpdateResourcesMessage;
import messages.UpdateTaskBoardMessage;
import messages.WorkerMoveToPathMessage;
import server.WebSocketEndpoint;
import utilities.Constants;

/**
 * CSCI-201 Web Factory
 * Factory.java
 * Purpose: serves as the Model for the Factory simulation
 * 
 * @version 2.0 
 * @since 01/12/2017
 */
public class Factory implements Serializable {
	public static final long serialVersionUID = 1;

	// server-side
	private transient Session mSession;
	private transient WebSocketEndpoint mWebSocketEndpoint;
	private transient Gson mGson = new Gson(); //Convenient library provided by Google to convert Java objects to JSON
	private transient ActionFactory mActionFactory = new ActionFactory();

	// serialized data members
	int width; // number of cells per row
	int height; // number of rows
	private String name;
	private int numberOfWorkers;
	private Vector<FactoryResource> resources;
	private Vector<FactoryProduct> products;
	private ArrayList<FactoryWorker> workers = new ArrayList<>();
	private FactoryTaskBoard taskBoard;
	//PE1
	private ManagerOffice managerOffice;

	// transient data members
	private transient ArrayList<FactoryObject> mFObjects = new ArrayList<>();
	private transient Map<String, FactoryNode> mFNodeMap = new HashMap<>();
	private transient FactoryNode mFNodes[][];
	private transient Point mTaskBoardLocation;

	/**
	 * Creates a default, empty factory
	 * @param inSession saves a reference to the session that created this factory
	 * @param inWSE saves a reference to the WebSocketEndpoint to relay messages to the front-end
	 */
	public Factory(Session inSession, WebSocketEndpoint inWSE) {
		mSession = inSession;
		mWebSocketEndpoint = inWSE;

		name = "";
		numberOfWorkers = 0;
		width = 0;
		height = 0;
		resources = new Vector<FactoryResource>();
		products = new Vector<FactoryProduct>();
	}

	/**
	 * Creates a filled factory
	 * @param inSession reference to the session that created this factory
	 * @param inWSE to relay messages to the front-end
	 * @param name of the factory
	 * @param numberOfWorkers to generate this many workers
	 * @param width number of cells per row
	 * @param height number of rows in the factory
	 * @param resources vector of FactoryResources (to be placed)
	 * @param products vector of FactoryProducts (tasks to be fulfilled)
	 * @param taskBoardLocation allows us to generate a FactoryTaskBoard
	 */
	public Factory(
		Session inSession,
		WebSocketEndpoint inWSE,
		String name,
		int numberOfWorkers,
		int width, int height,
		Vector<FactoryResource> resources,
		Vector<FactoryProduct> products,
		Point taskBoardLocation) {
		
		mSession = inSession;
		mWebSocketEndpoint = inWSE;

		// setup based on some information
		setName(name);
		setNumberOfWorkers(numberOfWorkers);
		setDimensions(width, height);
		setResources(resources);
		setProducts(products);

		// create the nodes of the factory
		createNodes();

		// send factory object to client
		sendFactory();
	}

	/* ***** WEBSOCKET COMMUNCATION METHODS ***** */

	/**
	 * Receives Strings from the WebSocketEndpoint and converts them into JsonObjects.
	 * From the JsonObject, we find and execute its respective action
	 * @param rawJson (String)
	 */
	public void listen(String rawJson) {
		JsonObject msg = mGson.fromJson(rawJson, JsonObject.class);
		Action action = mActionFactory.getAction(msg.get("action").getAsString());
		if (action != null) action.execute(this, msg);
	}
	/**
	 * Converts Message into a JSON String, and 
	 * sends to current session via the WebSocketEndpoint
	 * @param message
	 */
	private void send(Message message) {
		String json = mGson.toJson(message);
		mWebSocketEndpoint.sendToSession(mSession, json);
	}
	/**
	 * Sends entire Factory object to the client (use this only once!)
	 */
	public void sendFactory() {
		send(new FactoryMessage(this));
	}
	/**
	 * Sends the resources vector to the client, and its updates
	 */
	public void sendResources() {
		send(new UpdateResourcesMessage(this.resources));
	}
	/**
	 * Sends the taskboard to the client, primarily to update the ProductTable
	 */
	public void sendProductTable() {
		send(new UpdateTaskBoardMessage(this.taskBoard));
	}
	public void sendWorkerMoveToPath(FactoryWorker worker, Stack<FactoryNode> pathStack) {
		send(new WorkerMoveToPathMessage(worker, pathStack));
	}


	/* ***** GETTERS AND SETTERS (ENCAPSULATION) ***** */

	public void setName(String name) {
		this.name = name;
	}

	// WORKERS
	public void setNumberOfWorkers(int numberOfWorkers) {
		this.numberOfWorkers = numberOfWorkers;
	}
	public ArrayList<FactoryWorker> getWorkers() {
		return workers;
	}
	public FactoryWorker getWorker(int index) {
		return workers.get(index);
	}
	public void createWorkers() {
		//Create the workers, set their first task to look at the task board
		for (int i = 0; i < numberOfWorkers; i++) {
			//Start each worker at the first node (upper left corner)
			//Note, may change this, but all factories have an upper left corner(0,0) so it makes sense
			FactoryWorker fw = new FactoryWorker(i, mFNodes[0][0], this);
			mFObjects.add(fw);
			workers.add(fw);
		}
	}
	
	public void killWorkers() {
		for (FactoryWorker w : this.workers) w.kill();
	}

	// DIMENSIONS
	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

	// NODES
	FactoryNode[][] getNodes() {
		return mFNodes;
	}
	public FactoryNode getNode(String key) {
		return mFNodeMap.get(key);
	}
	/**
	 * Generates the 2-D array of FactoryNodes, and sets up all the
	 * objects that will show up on the Factory simulation board
	 */
	public void createNodes() {
		mFNodes = new FactoryNode[width][height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				mFNodes[x][y] = new FactoryNode(x, y);
				mFObjects.add(mFNodes[x][y]);
			}
		}
		
		linkNeighbors();
		createResources();
		createTaskBoard(mTaskBoardLocation);
		createWorkers();
		//PE1
		createManagerOffice();
	}
	/**
	 * Link nodes to its neighbors to make A* PathFinding easier
	 */
	public void linkNeighbors() {
		for (FactoryNode[] nodes : mFNodes) {
			for (FactoryNode node : nodes) {
				int x = node.getX();
				int y = node.getY();
				if (x != 0) node.addNeighbor(mFNodes[x - 1][y]);
				if (x != width - 1) node.addNeighbor(mFNodes[x + 1][y]);
				if (y != 0) node.addNeighbor(mFNodes[x][y - 1]);
				if (y != height - 1) node.addNeighbor(mFNodes[x][y + 1]);
			}
		}
	}
	
	// TASKBOARD
	public void setTaskBoardLocation(Point taskBoardLocation) {
		mTaskBoardLocation = taskBoardLocation;
	}
	public FactoryTaskBoard getTaskBoard() {
		return taskBoard;
	}
	/**
	 * Not only do we set the taskBoard to its location, we also pass in the products
	 * since the TaskBoard will maintain the database of product building progress
	 * @param taskBoardLocation
	 */
	public void createTaskBoard(Point taskBoardLocation) {
		taskBoard = new FactoryTaskBoard(this, products, taskBoardLocation.x, taskBoardLocation.y);
		mFObjects.add(taskBoard);
		mFNodes[taskBoardLocation.x][taskBoardLocation.y].setObject(taskBoard);
		mFNodeMap.put("Task Board", mFNodes[taskBoardLocation.x][taskBoardLocation.y]);
	}

	// RESOURCE
	public void addResource(FactoryResource resource) {
		if (resources == null) resources = new Vector<>();
		resources.add(resource);
	}
	public void setResources(Vector<FactoryResource> resources) {
		this.resources = new Vector<>();
		resources.forEach(this::addResource);
	}
	public void createResources() {
		// create the resources
		for (FactoryResource resource : resources) {
			mFObjects.add(resource);
			mFNodes[resource.getX()][resource.getY()].setObject(resource);
			mFNodeMap.put(resource.getName(), mFNodes[resource.getX()][resource.getY()]);
		}
	}
	
	//PE1
	//adds a ManagerOffice object to the factory
	public void createManagerOffice() {
		//instantiates a ManagerOffice object at the bottom right of the factory
		managerOffice = new ManagerOffice(width-1,height-1); //hardcoding for exam
		
		//adds managerOffice to the ArrayList of objects
		mFObjects.add(managerOffice);
		//adds managerOffice to the 2D mFNodes so that workers can find a path to it etc.
		mFNodes[managerOffice.getX()][managerOffice.getY()].setObject(managerOffice);
		//adds managerOffice to the mFNodeMap so that we can reference it with getNode("Manager Office")
		mFNodeMap.put(managerOffice.getName(), mFNodes[managerOffice.getX()][managerOffice.getY()]);
		//see createResources() for reference
	}

	// PRODUCT
	public void addProduct(FactoryProduct product) {
		if (products == null) products = new Vector<>();
		products.add(product);
	}
	public void setProducts(Vector<FactoryProduct> products) {
		this.products = new Vector<>();
		products.forEach(this::addProduct);
	}
	
	// OBJECTS
	ArrayList<FactoryObject> getObjects() {
		return mFObjects;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.factoryNameString + ": ").append(name);
		for (FactoryResource resource : resources) {
			sb.append("\n");
			sb.append("\t").append(resource);
		}
		for (FactoryProduct product : products) {
			sb.append("\n");
			sb.append("\t").append(product);
		}
		return sb.toString();
	}

}
