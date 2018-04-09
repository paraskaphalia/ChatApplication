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
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.net.*;
import static javax.swing.JFrame.*;


public class signup{
    JButton sign;
    JTextField name1;
    JTextField name2;
    JTextField handle;
    JLabel l;
    JLabel l1; 
    JLabel l2;
    JLabel l3;
    JLabel l4;
    JPasswordField p1;
    Socket socket1;
    PrintWriter out;
    String first;
    String last;
    String user;
    String passwrd;
    BufferedReader in;
    JFrame f;
    signup(Socket socket,String hostName,int portNumber)
    {
        try
        {
            try{
            f=new JFrame();
            socket1 = socket;
            out = new PrintWriter(socket.getOutputStream(), true);   
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            }
            catch(Exception ex){
                System.out.println("socket not created");
            }
            initcomponent();
            f.setTitle("Signup Form");
            f.setSize(500,500);
            f.setDefaultCloseOperation(EXIT_ON_CLOSE);
            f.setLayout(null);
            //setLayout(new BorderLayout()); //???????????
            f.setVisible(true);
        }
        catch(Exception e)
        {
            System.out.println("exception occured"+e);
        }
    }
 
    void initcomponent()
    {
       try
       {
           sign=new JButton("Sign up");
           name1=new JTextField();
           name2=new JTextField();
           handle=new JTextField();
           
           l=new JLabel("Enter your details");
           l1=new JLabel("First Name");
           l2=new JLabel("Last Name");
           l3=new JLabel("Handle Name");
           l4=new JLabel("Enter your Password");
           
           p1=new JPasswordField();
           
           l.setBounds(175,30,150,40);
           f.add(l);
           
           l1.setBounds(50,100,150,20);
           f.add(l1);
           
           name1.setBounds(200,100,150,20);
           f.add(name1);
           

           l2.setBounds(50,150,150,20);
           f.add(l2);
           
           name2.setBounds(200,150,150,20);
           f.add(name2);
           
           
           l3.setBounds(50,200,150,20);
           f.add(l3);
           
           handle.setBounds(200,200,150,20);
           f.add(handle);
           
           
           l4.setBounds(50,250,150,20);
           f.add(l4);
           
           p1.setBounds(200,250,150,20);
           f.add(p1);
           
           
           sign.setBounds(175,325,100,40);
           f.add(sign);
                      
           
           
           sign.addMouseListener(new MouseListener(){
               @Override
               public void mouseClicked(MouseEvent e)
               {
                   try
                   {
                      first = name1.getText();
                      last = name2.getText();
                      user = handle.getText();
                      passwrd = String.valueOf(p1.getPassword());
                      
                      String header = "s:" + first +  "," + last + "," + user + "," + passwrd;
                      System.out.println(header);
                      writeThread(header);
                      readThread();
                      sleep(600);
                      
                   }
                   catch(Exception e1)
                   {
                      System.out.println(e1);
                   }
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
                  // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
               }

               @Override
               public void mouseExited(MouseEvent e) {
                   //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
               }
           }
           );
           
                  
       }
       catch(Exception e)
       {
            System.out.println("exception occured"+e);
       }
    
    }
    void writeThread(String msg) {
        try {
            synchronized(socket1) {
                out.println(msg);
                sleep(10);
            }
   	}
        catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }	
}
    void readThread() {
		Thread read = new Thread() {
			public void run() {
                            try {
                                boolean flag=false;
                                String msg=null;
                                while((msg=in.readLine())!=null){
                                    System.out.println("Server reply : "+msg);
                                    
                                    if(msg.equals("signup successfull")){
                                        JOptionPane.showMessageDialog(null, msg);
                                        f.dispose();
                                        Client t = new Client(socket1,user);
                                    }
                                    else if(msg.equals("user already exists")){
                                        JOptionPane.showMessageDialog(null, msg);
                                        handle.setText("");
                                    }
                                    msg=null;
                                    break;
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
        String hostName="192.168.171.135";
        int portNumber=5239;    
    }*/
}
