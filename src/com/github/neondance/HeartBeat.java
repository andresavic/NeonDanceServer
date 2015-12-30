package com.github.neondance;

import java.awt.Color;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class HeartBeat extends Thread {
	
	/**
	 * 
	 */
	private Socket socket;
	private Suit suit;
	private Logger log;
	private Scanner scan;
	private MainFrame mainFrame;
	private Timer timer;
	private Instant oldTime;

	public HeartBeat(Socket socket, Suit suit) {
		super();
		this.suit = suit;
		this.socket = socket;
		this.log = Logger.getInstance();
		this.mainFrame = MainFrame.getInstance();
	}

	@Override
	public void run() {
		timer = new Timer();
		scan = null;
		oldTime = new Instant();
		PeriodFormatter pf = new PeriodFormatterBuilder()
				.appendMinutes()
				.appendSuffix("m")
				.appendSeparator(",")
				.appendSeconds()
				.appendSuffix("s")
				.appendSeparator(",")
				.appendMillis()
				.appendSuffix("ms")
				.toFormatter();
		try {
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					Interval i = new Interval(oldTime, new Instant());
					if (!(suit.getSuitPanel() == null)) {
						suit.getSuitPanel().getHeartbeatTime().setText(pf.print(i.toPeriod()));
						suit.getSuitPanel().repaint();
					}
				}
			}, 0, 30);
			while(true) {
				//Timout for not recieving data
				socket.setSoTimeout((int)mainFrame.getHeartBeatTimeOutSpinner().getValue());
				//Reading data
				scan = new Scanner(socket.getInputStream());
				scan.useDelimiter(";");
				String input = "";
				try {
					input = scan.next();
				} catch (NoSuchElementException e) {
					if (!suit.isShowRunning()) {
						heartBeatFailed();
					} else {
						continue;
					}
				}
				if (input == null) {
					if (!suit.isShowRunning()) {
						scan.close();
						throw new SocketException();
					} else {
						continue;
					}
				}
				if (!input.contains(":")) {
					log.log(Logger.WARNING, this.getName() + " no delimiter found in message " + input + " skipping...");
					continue;
				}
				String prefix = input.substring(0, input.indexOf(':'));
				String content = input.substring(input.indexOf(':')+1);
				switch (prefix) {
				case "H":
					suit.setSuitName(content);
					this.setName(content+"-Heartbeat");
					System.out.println("Heartbeat recieved: " + this.getName());
					break;
					
				case "E":
					log.log(Logger.ERROR, this.getName() + " reports error");
					break;
					
				case "R":
					log.log(Logger.INFO, this.getName() + " reported: " + content);
					//TODO Add logic what happens with this response
					if (suit.isShowRunning() && content.equals("ShowStart")) {
						suit.setRecievedShowStart(new Instant()	);
					} else if (!suit.isShowRunning() && content.equals("ShowEnd")) {
						suit.getSuitPanel().getTxtOutput().setText("");
						mainFrame.getFlag().setBackground(new Color(238, 238, 238));
					}
					if (content.equals("Reset")) {
						suit.disconnect();
					}
					break;
					
				default:
					log.log(Logger.WARNING, this.getName() + " unknown prefix used! \"" + prefix + "\"");
					break;
				}
				
				//Flash if data found
				suit.getSuitPanel().flashHearbeat();
				oldTime = new Instant();
			}
		} catch (NoSuchElementException|IOException e) {
			heartBeatFailed();
		} finally {
			scan.close();
		}
	}
	
	private void heartBeatFailed() {
		suit.getSuitPanel().getTxtOutput().setText("HEARTBEAT ERROR");
		log.log(Logger.WARNING, suit.parameter.name + " Heartbeat failed! Disconnecting...");
		if (mainFrame.getChckbxAutoRemoveSuit().isSelected()) {
			suit.disconnect();
		}
	}

	@Override
	public void interrupt() {
		try {
			socket.close();
		} catch (IOException e) {
			log.handleError(e);
		}
		timer.cancel();
		timer.purge();
		super.interrupt();
	}
	
	
}