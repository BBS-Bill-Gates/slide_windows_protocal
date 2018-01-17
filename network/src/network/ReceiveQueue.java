package network;


import java.io.*;
import java.net.*;
import java.util.*;

public class ReceiveQueue {
	private StringBuffer strBuf;
	private Queue<Frame> serverQueue;
	private Frame frameInput;
	private Frame frameOutput; 
	
	public ReceiveQueue() {
		this.strBuf = new StringBuffer();
		this.serverQueue = new LinkedList<Frame>();
	}
	public void send(Socket s, Frame fout) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			out.writeObject(fout);
		} catch(Exception e) {
			e.getMessage();
		}
	}
	public Frame receive(Socket s) {
		try {
			ObjectInputStream input = new ObjectInputStream(s.getInputStream());
			this.frameInput = (Frame) input.readObject();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return frameInput;
	}
	public boolean contains(int seq) {
		for(Frame f : this.serverQueue) {
			if(f.getSeq()==seq) {
				return true;
			} 
		}
		return false;
	}
	public void addContent(char c) {
		this.strBuf.append(Character.toString(c));
	}
	public String readContent() {
		return this.strBuf.toString();
	}
	public void offer(Frame f) {
		this.serverQueue.offer(f);
	}
}
