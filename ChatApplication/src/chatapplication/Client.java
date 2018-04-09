/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication;

/**
 *
 * @author KafaN
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import static java.lang.Thread.sleep;
import java.net.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
public class Client extends JFrame{
    JButton send;
    String username;
    Socket socket;
    PrintWriter out;
    BufferedReader in;
    BufferedReader stdIn;
    static String hostName;
    static int portNumber;
    static String name;
    JTextField text;
    JScrollPane v;
    JLabel l1; 
    JTextArea area=new JTextArea();
    
    /*this a temporary constructor which is used to explicitly run 
    this Activity otherwise it is called either by SignIn Activity or by signup activity
    */
    
    /*Client(){
        try {
            try{
                socket = new Socket(hostName, portNumber);
                System.out.println("Connected...");
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                stdIn = new BufferedReader(new InputStreamReader(System.in));

                System.out.print("Enter your screen name: ");
                name = stdIn.readLine();
                out.println(name + " joined chat...");
                readThread();
            }
            catch(Exception ex){
                System.out.println("Socket not created");
                System.exit(0);
            }
            initComponent();
            setTitle("Test");
            setSize(500,500);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout()); //???????????
            setVisible(true);
        }
        catch(Exception ex){
            
        }
    }
    */
    Client(Socket socket,String name){
     try {
            try{
                this.socket=socket;
                this.name=name;
                System.out.println("Connected...");
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out.println(name + " joined chat...");
                readThread();
            }
            catch(Exception ex){
                System.out.println("Socket not created");
                System.exit(0);
            }
            initComponent();
            setTitle("INBOX");
            setSize(500,500);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout()); //???????????
            setVisible(true);
        }
        catch(Exception ex){
            
        }
        
    }
    void writeThread(String msg) {
	try {
                if(msg.equals("")){
                    System.out.println("empty string returning without sending msg to server");
                    return;
                }
                System.out.println("sending message to the server...");
        	synchronized(socket) {
		out.println("t:"+name+","+msg);
   		sleep(10);
                }
        }
        catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }
	
    void initComponent(){ 
        try{
            send= new JButton("Send");
            send.setBounds(400,410,95,30); 
            send.setSize(80,40);
            this.add(send);
            text=new JTextField();
            text.setBounds(4,410, 400,40);  
            this.add(text);
            area.setEditable(false);
            v=new JScrollPane(area);
            v.setBounds(8,8,468,390);
            v.setBackground(Color.white);
            this.getContentPane().add(v);
            display("                                                         MESSAGE INBOX");
            
            send.addMouseListener(new MouseListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    int b=e.getButton();
                    if(b==MouseEvent.BUTTON1){
                        System.out.println("Pressed left Button");
                        String msg=text.getText();
                        System.out.println(msg+" Retrieved from JTextField ");
                        writeThread(msg);
                        text.setText("");
                    }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

            });
        }
        catch(Exception ex){
            
        }
    }
    public void display(String msg){
        try{
            area.append(msg+"\n");
        }
        catch(Exception ex){
            System.out.println("Exception occured during printing label "+ex);
        }
    }
    void readThread() {
	Thread read = new Thread() {
	String msg;
            public void run() {
                    try {
                            while((msg=in.readLine())!=null)
                            {
                                System.out.println("server responded\nprinting the message in the screen");
                                area.append(msg+"\n");
                                msg="";
                            }
                    }
                    catch(Exception e) {
                            System.out.println(e);
                            System.exit(0);
                    }

            }
	};
		read.setPriority(Thread.MAX_PRIORITY);
		read.start();
	}
    /*public static void main(String[] args) {
    	hostName = "192.168.171.157";
	portNumber = 5239;
        Client t=new Client();
    }*/
}