package network;

import java.net.*;
import java.util.*;
import java.io.*;

/*
 * 实现定时发送
 * */

public class SendQueue{
	private Queue<Frame> q;
	private Frame frameInput;
	private Frame frameOutput;
	private int sumNumber;				//发送帧的总数
	private int successNumber;			//一次性成功发送的数量
	private int characterNumber;		//发送字符的数量
	public SendQueue() {
		this.q = new LinkedList<Frame>();
	}
	public String readFile() {
		StringBuffer strBuf = new StringBuffer();
		try {
			FileReader fr = new FileReader("./src/network/article.txt");
			BufferedReader br = new BufferedReader(fr);
			String content;
			while((content=br.readLine())!=null) {
				strBuf.append(content);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("无此文件");
			System.exit(0);
		}
		return strBuf.toString();
	}
	public void initQueue() {
		String s = readFile();
		this.characterNumber = s.length();
		char[] c = s.toCharArray();
		for(int i=0; i<c.length; i++) {
			Frame f = new Frame();
			f.setSeq(i);
			f.setData(c[i]);
			f.setSize(1);
			this.q.offer(f);
		}
	}
	public Frame peek() {
		return this.q.peek();
	}
	public Frame remove() {
		return this.q.remove();
	}
	public boolean isEmpty() {
		return this.q.isEmpty();
	}
	public void send(Socket s) {
		try {
			this.frameOutput = this.peek();
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			System.out.println(this.frameOutput.getSeq());
			out.writeObject(frameOutput);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Frame receive(Socket s) {
		Frame f = new Frame();
		try {
			ObjectInputStream input = new ObjectInputStream(s.getInputStream());
			f=(Frame) input.readObject();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("服务器端校验出错...");
		} 
		return f;
	}
	public boolean contains(int seq) {
		for(Frame f : q) {
			if(f.getSeq()==seq) {
				return true;
			} 
		}
		return false;
	}
	public Frame getFrameInput() {
		return frameInput;
	}
	public void setFrameInput(Frame frameInput) {
		this.frameInput = frameInput;
	}
	public int length() {
		return this.q.size();
	}
	public int getSumNumber() {
		return sumNumber;
	}
	public int getSuccessNumber() {
		return successNumber;
	}
	public int getCharacterNumber() {
		return characterNumber;
	}
}
