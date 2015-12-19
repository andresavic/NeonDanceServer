package com.github.neondance;

import java.net.InetAddress;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

public class Osc {
	
	private static Osc osc;
	private MainFrame mainFrame;
	private Logger log;

	public Osc() {
		this.mainFrame = MainFrame.getInstance();
		this.log = Logger.getInstance();
	}

	public static Osc getInstance() {
		if(osc == null) {
			osc = new Osc();
		}
		return osc;
	}
	
	public void sendStart() {
		String message = (mainFrame.getQlcTextField().getText().endsWith("/") ? mainFrame.getQlcTextField().getText() : mainFrame.getQlcTextField().getText() + "/") + "start";
		send(message);
	}
	
	public void sendStop() {
		String message = (mainFrame.getQlcTextField().getText().endsWith("/") ? mainFrame.getQlcTextField().getText() : mainFrame.getQlcTextField().getText() + "/") + "stop";
		send(message);
	}
	
	private void send(String message) {
		try {
			OSCPortOut sender = new OSCPortOut(InetAddress.getByName(mainFrame.getOscIpTextField().getText()), 53000);
			Object args[] = new Object[1];
			args[0] = new Float(1F);
			OSCMessage msg = new OSCMessage(message, args);
			sender.send(msg);
			args[0] = new Float(0F);
			msg = new OSCMessage(message, args);
			sender.send(msg);
		} catch (Exception e) {
			log.log(Logger.WARNING, "Sending OSC message failed");
		}
	}
	
}
