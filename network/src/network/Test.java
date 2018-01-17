package network;

import java.io.FileWriter;
import java.io.IOException;

public class Test {
	public static void main(String[] args) {
		try {
			FileWriter fw = new FileWriter("./src/network/copy.txt");
			fw.write("hello,\n");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
