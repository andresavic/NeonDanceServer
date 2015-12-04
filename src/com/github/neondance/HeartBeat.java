package com.github.neondance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HeartBeat extends Thread {
	
	/**
	 * 
	 */
	private Socket socket;
	private SuitThread suitThread;
	private Boolean cleanInterrupt;

	public HeartBeat(Socket socket, SuitThread suitThread) {
		super();
		this.suitThread = suitThread;
		this.socket = socket;
		this.cleanInterrupt = false;
	}

	@Override
	public void run() {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:SS.ss");
		try {
			while(true) {
				//Timout for not recieving data
				socket.setSoTimeout(10000);
				//Reading data
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				reader.readLine();
				//Flash if data found
				suitThread.getSuitPanel().flashHearbeat();
				suitThread.getSuitPanel().getHeartbeatTime().setText(df.format(new Date(System.currentTimeMillis())));
			}
		} catch (SocketException e) {
			//If the interrupt was intentional, do nothing else SELFDESTRUCT
			if (!cleanInterrupt) {
				suitThread.getSuitPanel().getTxtOutput().setText("HEARTBEAT ERROR");
				suitThread.interrupt();
				e.printStackTrace();
			}
		} catch (IOException e) {
			if (!cleanInterrupt) {
				suitThread.getSuitPanel().getTxtOutput().setText("HEARTBEAT ERROR");
				suitThread.interrupt();
				e.printStackTrace();
			}
		}
	}

	public void interrupt(Boolean cleanInterrupt) {
		this.cleanInterrupt = cleanInterrupt;
		super.interrupt();
	}
	
}