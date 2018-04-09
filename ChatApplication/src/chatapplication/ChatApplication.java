/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author KafaN
 */
public class ChatApplication {
    Socket socket;
    static String hostName;
    static int portNumber;
    JFrame f;
    JLabel l,lip,lport;
    JTextField tip,tport;
    JButton connect;
    ChatApplication() {
        try{
            f=new JFrame();
            initComponent();
            f.setTitle("Chat Application");
            f.setSize(500,500);
            f.setDefaultCloseOperation(EXIT_ON_CLOSE);
            f.setLayout(null); //???????????
            f.setVisible(true);
        }
        catch(Exception ex){
            
        }
    }
    public void initComponent(){
        l=new JLabel("Chat Application");
        l.setBounds(190,70, 100,30);
        f.add(l);
        lip=new JLabel("Server ip Addr");
        lip.setBounds(35,150, 100,30);
        f.add(lip);
        lport=new JLabel("Port Number");
        lport.setBounds(35,200, 100,30);
        f.add(lport);
        tip=new JTextField();
        tip.setBounds(120,150,350,30);
        f.add(tip);
        tport=new JTextField("5239");
        tport.setBounds(120,200,350,30);
        f.add(tport);
        connect=new JButton("Connect");
        connect.setBounds(140,300,200,30);
        f.add(connect);
        connect.addMouseListener(new MouseListener(){
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        System.out.println("right Click on the button");
                        int b=e.getButton();
                        if(b==MouseEvent.BUTTON1){
                           connectToServer();
                        }
                    }
                    catch(Exception ex){
                        
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

            }
        );
    }
    public void connectToServer(){
        hostName=tip.getText();
                           portNumber=Integer.parseInt(tport.getText());
                           try{
                               
                                socket = new Socket(hostName, portNumber);
                                System.out.println("Connected to Server ...");
                                f.dispose();
                                SignIn s=new SignIn(socket);
                            }
                            catch(Exception ex){
                                System.out.println("socket not created...Cannot connect to the server");
                                tip.setText("");
                                System.exit(0);
                            }
    }
    static public void main(String args[])  {
        ChatApplication c=new ChatApplication();
    }
}
