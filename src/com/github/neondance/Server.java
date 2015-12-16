package com.github.neondance;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPort;
import com.illposed.osc.OSCPortOut;

public class Server extends Thread {
	
	ServerSocket socket = null;
	CopyOnWriteArrayList<SuitThread> suitThreads = null;
	Logger log;
	OSCPortOut osc;
	MainFrame mainFrame;
	
	public static final String version = "0.1.0";
	
	// SERVER START
	@Override
	public void run() {
		mainFrame = MainFrame.getInstance();
		//All threads are saved here
		suitThreads = new CopyOnWriteArrayList<SuitThread>();
		//Logger is initialized
		log = Logger.getInstance();
		log.log(Logger.INFO, "Server starting...");
		try {
			osc = new OSCPortOut(InetAddress.getByName("192.168.1.2"),53000);
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
				mainFrame.getLblConnectedClients().setText("Connected Clients: " + suitThreads.size());
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
			} else {
				log.log(Logger.ERROR, "Removing of thread failed! Thread not dead!");
			}
		}
	}
	
	public void oscGo(){
		try {
			Thread.sleep(290);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Runtime.getRuntime().exec("open -a VLC.app /Users/mcand007/Downloads/NeonWireDanceRemixVersion2.mp3");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void sendCommandToAll(String command) {
		log.log(Logger.INFO, "Send Time: " + System.currentTimeMillis());
		for (SuitThread suitThread : suitThreads) {
			suitThread.sendCommand(command);
			log.log(Logger.INFO, suitThread.getName() + " - " + System.currentTimeMillis());
			switch (command) {
			case SuitThread.START_SHOW:
				suitThread.setShowRunning(true);
			
				break;
				
			case SuitThread.STOP_SHOW:
				suitThread.setShowRunning(false);
				break;
				
			case SuitThread.ON:
				suitThread.setiO(true);
				break;
				
			case SuitThread.OFF:
				suitThread.setiO(false);
				break;
			}
		}
		if (command.equals(SuitThread.START_SHOW)){
			oscGo();
		}
		
	}

}
