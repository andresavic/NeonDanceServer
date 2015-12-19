package com.github.neondance;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.CopyOnWriteArrayList;

import org.joda.time.Interval;

public class Server extends Thread {
	
	private ServerSocket socket = null;
	private CopyOnWriteArrayList<Suit> suits = null;
	private Logger log;
	private MainFrame mainFrame;
	private Osc osc;
	
	public static final String version = "0.2.0";
	
	public Server() {
		this.mainFrame = MainFrame.getInstance();
		this.osc = Osc.getInstance();
	}
	
	// SERVER START
	@Override
	public void run() {
		this.setName("Server Thread");
		//All threads are saved here
		suits = new CopyOnWriteArrayList<Suit>();
		//Logger is initialized
		log = Logger.getInstance();
		log.log(Logger.INFO, "Server starting...");
		try {
			//Server socket
			socket = new ServerSocket(2121);
			//Server timeout
//			socket.setSoTimeout(10000);
			
			log.log(Logger.INFO, "Server started! Listening to connections...");
			log.setServerStatus(Logger.UP);
			
			Suit st = null;
			while(true) {
				//Listening for connections
				Socket client = socket.accept();
				/*
				 * A client is connecting
				 */
				log.log(Logger.INFO, "Client connecting...");
				//Thread is creating
				st = new Suit(client, this);
				st.connect();
				log.log(Logger.INFO, "Client connected!");
				//Panel is creating
				st.setSuitPanel(new SuitPanel(st));
				//Thread added to list of threads
				suits.add(st);
				mainFrame.getLblConnectedClients().setText("Connected Clients: " + suits.size());
			}
		} catch (IOException e) {
			//If exception is a SocketException, the server was terminated by the user
			if (!(e instanceof SocketException)) {
				//Else handle error
				log.handleError(e);
				e.printStackTrace();
			}
		} finally {
			//Finally set server to down
			log.setServerStatus(Logger.DOWN);
		}
	}

	//SERVER STOP
	//If user stops server
	@Override
	public void interrupt() {
		log.log(Logger.INFO, "Server shutting down...");
		//Disconnect all suits
		for (Suit suit : suits) {
			log.log(Logger.INFO, "Disconnecting suit: " + suit.parameter.name);
			suit.disconnect();
		}
		mainFrame.getLblConnectedClients().setText("Connected Clients: " + suits.size());
		try {
			socket.close();
		} catch (IOException e) {
			log.handleError(e);
			e.printStackTrace();
		}
		log.setServerStatus(Logger.DOWN);
		log.log(Logger.INFO, "Server shut down.");
		super.interrupt();
	}
	
	//Remove thread of list of threads
	public void suitDisconnected(Suit suit) {
		log.log(Logger.INFO, "Client disconnected: " + suit.parameter.name);
		suits.remove(suit);
		mainFrame.getLblConnectedClients().setText("Connected Clients: " + suits.size());
	}
	
//	public void oscGo(){
		
		
//		try {
//			Thread.sleep(290);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		try {
//			Runtime.getRuntime().exec("open -a VLC.app /Users/mcand007/Downloads/NeonWireDanceRemixVersion2.mp3");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//	}
	
	public void sendCommandToAll(String command) {
		for (Suit suit : suits) {
			switch (command) {
			case Suit.START_SHOW:
				suit.setShowRunning(true);
				break;
				
			case Suit.STOP_SHOW:
				suit.setShowRunning(false);
				break;
				
			case Suit.ON:
				suit.setiO(true);
				break;
				
			case Suit.OFF:
				suit.setiO(false);
				break;
			}
			suit.sendCommand(command);
			if (command.equals(Suit.START_SHOW)) {
				verifyShowStartRecieved();
			}
		}
		if (mainFrame.getChckbxUseOsc().isSelected()){
			if (command.equals(Suit.START_SHOW)){
				osc.sendStart();
			} else if (command.equals(Suit.STOP_SHOW)){
				osc.sendStop();
			}
		}
	}
	
	private void verifyShowStartRecieved() {
		try {
			Thread.sleep((int)mainFrame.getStartShowTimeoutSpinner().getValue() + 10);
		} catch (InterruptedException e) {
			log.handleError(e);
		}
		for (Suit suit : suits) {
			if (suit.getSendShowStart() == null || suit.getRecievedShowStart() == null) {
				abortShow();
			}
			Interval i = new Interval(suit.getSendShowStart(), suit.getRecievedShowStart());
			if (i.toDurationMillis() > (int)mainFrame.getStartShowTimeoutSpinner().getValue()) {
				abortShow();
				break;
			}
			suit.getSuitPanel().getTxtOutput().setText("STARTED - " + i.toDurationMillis() + "ms");
		}
	}

	private void abortShow() {
		sendCommandToAll(Suit.STOP_SHOW);
		sendCommandToAll(Suit.STOP_SHOW);
		if (mainFrame.getChckbxUseOsc().isSelected()) {
			osc.sendStop();
		}
		log.log(Logger.WARNING, "Show stopped because recieved message was to late.");
	}
}
