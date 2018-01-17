package network;

import java.io.*;
import java.net.*;
import java.util.*;


public class SupportedThread implements Runnable{
	private Frame f;
	private Frame frameInput;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private boolean success;
	public SupportedThread(Frame f) {
		this.f = f;
		this.success = false;
	}
	public void run() {
			Socket s = new Socket();
			try {
				s.connect(new InetSocketAddress("127.0.0.1", 9000),100);
				output = new ObjectOutputStream(s.getOutputStream());
				System.out.println(this.f.getSeq());
				output.writeObject(this.f);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			switch(randomNumber()) {
				case 0:
					/*
					 * 1.发送分组丢失,
					 * */
					System.out.println("发送分组丢失,稍等之后开始重传.");
					try {
						s.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 1:
					/*
					 * 2.确认分组丢失,
					 * */
					System.out.println("确认分组丢失,稍等之后开始重传.");
					try {
						s.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 2:
					/*
					 * 3.确认分组延迟.这些情况都会引起重传,
					 * */
					System.out.println("确认分组延时,稍等之后开始重传.");
					try {
						s.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				default:
					/*
					 * 正常收到分组:1.正常确认,2.重复确认
					 * 接收有可能出现异常,因为客户端可能不会发送帧,所以
					 * */
						try {
							s.setSoTimeout(50);
							input = new ObjectInputStream(s.getInputStream());
							frameInput = (Frame)input.readObject();
							if(frameInput.getKind()==1) {
								System.out.println("正常收到确认...");
								this.success = true;
							} else {
								this.success = true;
								System.out.println("收到重复确认...");
							}
						} catch(Exception e) {
//							e.printStackTrace();
						System.out.println("服务器端校验错误...");
					}
					break;
			}
			try {
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public int randomNumber() {
		Random r = new Random();
		int number = r.nextInt(1000);
		return number%100;
	}
	public boolean returnSuccess() {
		return this.success;
	}
}
