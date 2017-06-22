import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class SocketServer {

	private static List<Socket> clients = new LinkedList<Socket>();
	
	static JLabel label1 = new JLabel("    24    ");
	static JLabel label2 = new JLabel("    17    ");
	static JLabel label3 = new JLabel("    167    ");
	

	public static void main(String[] args) {
		swing();
		try {
			ServerSocket server = new ServerSocket(6666);
			System.out.println("listening");
			while (true) {
				Socket client = server.accept();
				clients.add(client);
				System.out.println("connect success");
				receive(client);
			}
		} catch (Exception e) {
		}
	}
	
	private static JTextArea showText;//显示区文本
	 static JFrame frame;
	 private static JTextArea writeText;//输入区文本
	 static JLabel signMM2;

	public static void swing() {
			
			
		JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        JButton button = new JButton("温度：");
        JButton button2 = new JButton("湿度：");
        JButton button3 = new JButton("光照：");
      
        
       
        
        Font font = new Font("黑体",Font.PLAIN,40);
        button.setFont(font);
        button2.setFont(font);
        button3.setFont(font);
        label1.setFont(font);
        label2.setFont(font);
        label3.setFont(font);
        
        frame.setLayout(new GridLayout(3,2));
        
       
        
        frame.add(button);
        frame.add(label1);
        frame.add(button2);
        frame.add(label2);
        frame.add(button3);
        frame.add(label3);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
		
	}

	public static void receive(final Socket client) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					DataInputStream in = new DataInputStream(client.getInputStream());  //������������
					while (true) {
						byte[] b = new byte[1024];
						int len = in.read(b, 0, b.length);  //��ȡ����
						
						String receive = new String(b, 0, len);
						System.out.println(receive);
						String[] strarr = receive.split("/");
						label1.setText(strarr[0]);
						label2.setText(strarr[1]);
						label3.setText(strarr[2]);
						
						for (Socket temp : clients) {
							if (!client.equals(temp)) {
								temp.getOutputStream().write(receive.getBytes());
								temp.getOutputStream().flush();
							}
						}
					}
				} catch (Exception e) {
				}
			}
		}).start();
	}
}