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
		suitThreads = new ArrayList<SuitThread>();
		log = Logger.getInstance();
		log.log(Logger.INFO, "Server starting...");
		try {
			socket = new ServerSocket(2121);
			socket.setSoTimeout(10000);
			log.log(Logger.INFO, "Server started! Listening to connections...");
			log.setServerStatus(Logger.UP);
			SuitThread st = null;
			while(true) {
				Socket client = socket.accept();
				log.log(Logger.INFO, "Client connecting...");
				st = new SuitThread(client);
				log.log(Logger.INFO, "Client connected! Name: " + st.getName());
				st.setSuitPanel(new SuitPanel(st));
			}
		} catch (IOException e) {
			if (!(e instanceof SocketException)) {
				log.handleError(e);
				e.printStackTrace();
			}
		} 
		log.setServerStatus(Logger.DOWN);
	}

	//SERVER STOP
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

}
