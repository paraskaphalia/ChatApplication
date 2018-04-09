/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import static java.lang.Thread.sleep;
import java.net.*;
import javax.swing.*;
import static javax.swing.JFrame.*;


/**
 *
 * @author KafaN
 */
public class SignIn {
    Socket socket;
    PrintWriter out;
    BufferedReader in;
    static String hostName;
    static int portNumber;
    JFrame f;
    JLabel l,l1,l2;
    JTextField handle;
    JPasswordField pass;
    JButton enter,back;
    
    /*public SignIn(){
        try{
            try{
                socket = new Socket(hostName, portNumber);
                System.out.println("Connected...");
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            }
            catch(Exception ex){
                System.out.println("socket not created...");
                System.exit(0);
            }
            f=new JFrame();
            initComponent();
            f.setTitle("Sign In");
            f.setSize(500,500);
            f.setDefaultCloseOperation(EXIT_ON_CLOSE);
            f.setLayout(null); //???????????
            f.setVisible(true);
        }
        catch(Exception ex){
            
        }
    }
    */
    public SignIn(Socket socket){
        try{
            try{
                this.socket = socket;
                System.out.println("Connected...");
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            }
            catch(Exception ex){
                System.out.println("socket not created...");
                System.exit(0);
            }
            f=new JFrame();
            initComponent();
            f.setTitle("Sign In");
            f.setSize(500,500);
            f.setDefaultCloseOperation(EXIT_ON_CLOSE);
            f.setLayout(null); //???????????
            f.setVisible(true);
        }
        catch(Exception ex){
            
        }
    }
    
    
    public void initComponent(){
        try{
            l=new JLabel("SIGN IN");
            l.setBounds(225,70, 100,30);
            f.add(l);
            l1=new JLabel("handle");
            l1.setBounds(35,150, 100,30);
            f.add(l1);
            l2=new JLabel("password");
            l2.setBounds(35,200, 100,30);
            f.add(l2);
            handle=new JTextField();
            handle.setBounds(100,150,350,30);
            f.add(handle);
            pass=new JPasswordField();
            pass.setBounds(100,200,350,30);
            f.add(pass);
            enter=new JButton("Enter Chat Room");
            enter.setBounds(70,300, 370,35);
            f.add(enter);
            back=new JButton("Not Registered yet");
            back.setBounds(70,350, 370,35);
            f.add(back);
            enter.addMouseListener(new MouseListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    int b=e.getButton();
                    if(b==MouseEvent.BUTTON1){
                       System.out.println("Right click pressed");
                       //if match is found in the database;
                       String username=handle.getText();
                       String password;
                       password = String.valueOf(pass.getPassword());
                       String reg="l:"+username+","+password;
                       System.out.println(reg);
                       writeThread(reg);
                       listentoResponse();    //custom thread which listens for the response of the server
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
            back.addMouseListener(new MouseListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    
                       //System.out.println(" pressed");
                        f.dispose();
                        signup obj = new signup(socket,hostName,portNumber);                       
                    
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
    void writeThread(String msg) {
        try {
            synchronized(socket) {
                out.println(msg);
                sleep(10);
            }
   	}
        catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }	
	}
    void listentoResponse(){
        //will listen to the response of the server
        System.out.println("waiting for server response...");
        Thread read = new Thread() {
            String msg;
            boolean flag=false;
            public void run() {
                try {
                    while((msg=in.readLine())!=null)
                    {
                        System.out.println("received msg : "+msg);
                        if(msg.equals("login success")){
                            flag=true;
                            break;
                        }
                        else if(msg.equals("login failed")){
                            System.out.println("inside the condition ");
                            flag=false;
                            break;
                        }
                        msg=null;
                    }
                    if(flag==true){
                        JOptionPane.showMessageDialog(null, "Successfully Logged In");
                        f.dispose();
                        Client c=new Client(socket,handle.getText());
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "!!!!!Login Failed!!!!!");
                        handle.setText("");
                        pass.setText("");
                    }
                }
                catch(Exception e)
                {
                    System.out.println(e);
                    System.exit(0);
                }
            }
            
	};
		read.setPriority(Thread.MAX_PRIORITY);
		read.start();
    }
    
    /*static public void main(String args[]){
        portNumber=5239;
        hostName="192.168.171.157";
        SignIn s=new SignIn();
    }*/
}