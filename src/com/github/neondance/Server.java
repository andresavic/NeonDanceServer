package com.github.neondance;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server extends Thread {
	
	ServerSocket socket = null;
	ArrayList<SuitThread> suitThreads = null;
	Logger log;
	
	public static final String version = "0.0.1";
	
	// SERVER START
	@Override
	public void run() {
		//All threads are saved here
		suitThreads = new ArrayList<SuitThread>();
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
			
			SuitThread st = null;
			while(true) {
				//Listening for connections
				Socket client = socket.accept();
				/*
				 * A client is connecting
				 */
				log.log(Logger.INFO, "Client connecting...");
				//Thread is creating
				st = new SuitThread(client, this);
				st.start();
				log.log(Logger.INFO, "Client connected! Name: " + st.getName());
				//Panel is creating
				st.setSuitPanel(new SuitPanel(st));
				//Thread added to list of threads
				suitThreads.add(st);
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
		for (SuitThread suitThread : suitThreads) {
			log.log(Logger.INFO, "Disconnecting suit: " + suitThread.getName());
			suitThread.interrupt();
		}
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
	public void threadInterrupted(SuitThread thread) {
		log.log(Logger.INFO, "Client disconnected: " + thread.getName());
		for (SuitThread suitThread : suitThreads) {
			if (suitThread.getId() == thread.getId() && !thread.isAlive()) {
				suitThreads.remove(suitThread);
				//TODO: Also remove Panel!
			} else {
				log.log(Logger.ERROR, "Removing of thread failed! Thread not dead!");
			}
		}
	}

}
