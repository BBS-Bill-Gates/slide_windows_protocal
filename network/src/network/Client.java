package network;

import java.util.*;

import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.net.*;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.io.*;

public class Client extends JFrame{
    public DefaultTableModel model = null;
    public JTable table = null;
 
    public JButton addBtn = null;
    public Client(int sendSum, int characterNumber,long time)
    {
       super("TableDemo");
       setTitle("传输结果统计");
       String[][] datas = {};
       String[] titles = { "统计指标", "统计结果" };
       model = new DefaultTableModel(datas, titles);
       table = new JTable(model);

       model.addRow(new String[] {"传输总帧数",Integer.toString(sendSum)});
       model.addRow(new String[] {"一次性传输成功的个数",Integer.toString(characterNumber)});
       model.addRow(new String[] {"失败的个数",Integer.toString(sendSum-characterNumber)});
       model.addRow(new String[] {"传输数据总大小:",new DecimalFormat("0.00").format((float)(characterNumber/1024.0)) +"KB"});
       model.addRow(new String[] {"正确率", new DecimalFormat("0.00%").format((float)characterNumber/sendSum)});
       model.addRow(new String[] {"时间",Long.toString(time)});
       model.addRow(new String[] {"速率",new DecimalFormat("0.00").format((float)characterNumber/(float)time * 1000) + "KB/S"});
       add(new JScrollPane(table));
       setSize(400, 300);
       setLocationRelativeTo(null);
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       setVisible(true);

    }
	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		long end;
		SendQueue sq = new SendQueue();
		sq.initQueue();
		Group g = new Group(sq);
		while(!sq.isEmpty())
		{
			g.slideWindow();
		}
		end = System.currentTimeMillis();
		new Client(g.getSendNumber(),sq.getCharacterNumber(),end-start);
	}
}
