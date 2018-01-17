package network;
import java.net.*;
import java.util.*;
import java.io.*;

public class Server {
	public static void main(String[] args) throws Exception{
		ServerSocket server = new ServerSocket(9000);
		ReceiveQueue rq = new ReceiveQueue();
		Socket s;
		while(true){
			s = server.accept();
			Frame f = new Frame();
			f=rq.receive(s);
			switch(randomNumber()) {
				case 0:
					/*
					 * 1.分组出错,
					 * */
					System.out.println("校验出错,直接丢弃...");
					break;
				default:
					/*
					 * 2.帧正常且正常接收
					 * */
					if(rq.contains(f.getSeq())) {
						System.out.println("收到重复帧...");
					} else {
						System.out.println("收到正常帧...");
						rq.offer(f);
						rq.addContent(f.getData());
						f.setKind(1);
						rq.send(s, f);
					}
					break;
			}
			FileWriter fw = new FileWriter("./src/network/copy.txt");
			fw.write(rq.readContent() + "\n");
			fw.close();
		}
	}
	public static int randomNumber() {
		Random r = new Random();
		int number = r.nextInt(1000);
		return number%100;
	}
}
