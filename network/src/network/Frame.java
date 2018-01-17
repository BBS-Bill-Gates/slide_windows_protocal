package network;

import java.io.*;

public class Frame implements Serializable{
	/**
	 * By Bill
	 */
	private static final long serialVersionUID = 1L;
	private int kind;			//确认帧的类型: 1--->ack,
	private int seq;			//序号
	private int ack;			//确认序号
	private char data;			//内容
	private int size;			//大小
	public Frame() {
	}
	public Frame(int seq, int ack, char data,int size) {
		this.seq = seq;
		this.ack = ack;
		this.data = data;
		this.size = size;
	}
	public char getData() {
		return data;
	}
	public void setData(char data) {
		this.data = data;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getAck() {
		return ack;
	}
	public void setAck(int ack) {
		this.ack = ack;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	
}
