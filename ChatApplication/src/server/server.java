/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author KafaN
 */
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.util.HashSet;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class server extends JFrame implements MouseListener
{
	 static Statement stmt;
	  static  Connection conn;
	Thread t;
        boolean serverOn;   //state of server
	int flag=0;
	Socket socket;
	ServerSocket serverSocket;
	private static int portNumber;
	private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
        
	
	 public void initcomponent()
	   {
                    JButton serverstart=new JButton("START");
                    //JButton serverstart1=new JButton("start1 server");
                    setLayout(null);
                    serverstart.setBounds(100,100,100,50);
                    add(serverstart);
                    serverstart.addMouseListener(new MouseAdapter(){
                      @Override
                      public void mouseClicked(MouseEvent e) {
                        try {
                        if(serverOn==false)  {
                            serverOn=true;
                            System.out.println("Starting the server");
                            funct();//To change body of generated methods, choose Tools | Templates.
                        }
                        else {
                            System.out.println("Server is already running");
                        }
                        }
                          catch(Exception em){}
                      }

                      @Override
                      public void mousePressed(MouseEvent e) {
                         // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                      }

                      @Override
                      public void mouseReleased(MouseEvent e) {
                         // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                      }

                      @Override
                      public void mouseEntered(MouseEvent e) {
                         // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                      }

                      @Override
                      public void mouseExited(MouseEvent e) {
                         // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                      }
                      
                  });
		 
                    JButton stopserver=new JButton("STOP");
                    stopserver.setBounds(230,100,100,50);
                    stopserver.addMouseListener(new MouseAdapter(){
                      @Override
                        public void mouseClicked(MouseEvent e) {
                            try{
                                if(serverOn==true)  {
                                    serverOn=false;
                                    System.out.println("Server stopped");
                                    t.stop();
                                    serverSocket.close();
                                    //socket.close();//To change body of generated methods, choose Tools | Templates.
                                }
                            }
                          catch(Exception ex){
                              System.out.println("Exception occurred during closing the server "+ex);
                          }
                      }

                      @Override
                      public void mousePressed(MouseEvent e) {
                         // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                      }

                      @Override
                      public void mouseReleased(MouseEvent e) {
                         // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                      }

                      @Override
                      public void mouseEntered(MouseEvent e) {
                         // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                      }

                      @Override
                      public void mouseExited(MouseEvent e) {
                         // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                      }
                      
                  });
                 stopserver.addActionListener(new ActionListener() {
                     @Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
                  add(stopserver);
		  
		this.addWindowListener(new WindowAdapter()
                {
                    public void windowClosing(WindowEvent e)
                   {
                       try{
                       System.out.println("window closed\nclosing the server...");
                       if(serverOn==true)  {
                            serverOn=false;
                            System.out.println("Server stopped");
                            serverSocket.close();
                  
                            t.stop();
                        }
                       }
                       catch(Exception ex){
                           System.out.println("Exception occured in windowClosing funtion"+ex);
                       }
                   }
                }); 
		}
	public server() {
		flag=0;
                serverOn=false;
		String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		 String DB_URL = "jdbc:mysql://localhost/sonoo";

		
		 String USER = "root";
		 String PASS = "1234";
		
		try
		{
			Class.forName(JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
		}
		catch(Exception e)
		{
		
		}
		  initcomponent();
		  setTitle("server");
		  setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		  setSize(400,300);
		  setVisible(true);
		
	}
	public void funct()
	{
		//System.out.println("hello");
	    try {
	    	//System.out.println("Listening...");
			serverSocket = new ServerSocket(portNumber);
    		System.out.println("Listening...");
    		connect();
	    	} catch (Exception e){System.out.println("exception is "+e);}
	}
  
  /*
    public void connect() {
    	try {
	    	while(true) {
	    		socket=serverSocket.accept();
	    		new Handler(socket).start();
	    		
			}
			
		}catch (SocketException se) {
			System.exit(0);
		}
	    catch (Exception e) {
			System.out.println(e);
		} finally {
			try {serverSocket.close();}
			catch(Exception e){}
		}
		
    }*/
public void connect() {
    	t=new Thread(){
        public void run(){
        try {
	    	while(true) {
	    		socket=serverSocket.accept();
	    		Handler h=new Handler(socket);
	    		h.setDaemon(true);
                        h.start();
		}
			
		}catch (SocketException se) {
			System.exit(0);
		}
	    catch (Exception e) {
			System.out.println(e);
		} finally {
			try {serverSocket.close();}
			catch(Exception e){}
		}
        }
        };
        t.setPriority(Thread.MAX_PRIORITY);
      	t.start();
    }

    

 	public static void main(String[] args) throws Exception {

 		portNumber = 5239;
 		server chatServer = new server();
		//chatServer.connect();
	 		
	}

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


private static class Handler extends Thread {
	Socket socket = null;
	PrintWriter out;
    BufferedReader in ;
    BufferedReader stdIn;

    public Handler(Socket socket) {
            this.socket = socket;
        }
    @Override
    public void run() {
    	try {
    		System.out.println("Connected...");
		    out = new PrintWriter(socket.getOutputStream(), true);
		   

		    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		   // stdIn = new BufferedReader(new InputStreamReader(System.in));
		    readThread();
		    
    	} catch (Exception e) {
    		System.out.println(e);
    		try {
	    		if (out != null) {
                    writers.remove(out);
                    System.out.println("Disconnected...");
                }
                socket.close();}
    		catch(Exception se){}
    	}
    }

 public synchronized void readThread() {
    	Thread read = new Thread() {
            @Override
		public void run() {
		    	String data;
		    	try {
		    	      while((data = in.readLine()) != null) {
		    		     int k=0;
                                     System.out.println("Client send data : "+data);
                                     if(data.charAt(0)=='t')
                                     {
                                         //System.out.println(data);
                                        String name="",mass="";
                                        writers.add(out);
		    			int i=2;
                                        for(;i<data.length();i++)
                                        {
                                            if(data.charAt(i)==',')
                                                break;
                                            name=name+data.charAt(i);
                                        }
                                        i++;
                                        for(;i<data.length();i++)
                                        {
                                         
                                           mass=mass+data.charAt(i);
                                        }
                                        
		    		//name+" : "+mass;
		    			System.out.println(name+" : "+mass);
                                        
                                        for (PrintWriter writer : writers) {
                                                 writer.println(name+" : "+mass);
	                                }
                                        //inserting the message into table message
                                        String mysql="insert into message values('"+name+"','"+mass+ "')";
		    			try
		    			{
		    			stmt.executeUpdate(mysql);
		    			}
		    			catch(Exception e) {}
                                        }
                                        else if(data.charAt(0)=='s')
                                        {
                                           String fname="",lname="",hname="",pass="";
                                           int i=2;
                                           for(;i<data.length();i++)
                                           {
                                               if(data.charAt(i)==',')
                                                   break;
                                               fname=fname+data.charAt(i);
                                           }
                                           i++;
                                           for(;i<data.length();i++)
                                           {
                                               if(data.charAt(i)==',')
                                                   break;
                                               lname=lname+data.charAt(i);
                                           }
                                           i++;
                                           for(;i<data.length();i++)
                                           {
                                               if(data.charAt(i)==',')
                                                   break;
                                               hname=hname+data.charAt(i);
                                           }
                                           i++;
                                           for(;i<data.length();i++)
                                           {
                                               if(data.charAt(i)==',')
                                                   break;
                                               pass=pass+data.charAt(i);
                                           }
                                           
                                            /*for (PrintWriter writer : writers) {
                                                if(writer==out)
	                        writer.println("successfull signup");}*/
                                            
                                          
                                           String mysql1="select * from users where (hname='"+hname+"')";
                                            ResultSet rs = null;
                                            try {
					rs=stmt.executeQuery(mysql1);
				       } catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				       }
                                           
                                            if(rs.next())
                                            {
                                                out.println("user already exists");
                                                System.out.println("user already exists");
                                            }
                                            else
                                            {
                                           String mysql="insert into users values('"+fname+"','"+lname+ "','"+hname+"','"+pass+"')";
                                            out.println("signup successfull");
                                            System.out.println("user signed up as :"+fname+" "+lname+" "+hname+" "+pass);
                                             try
		    			{
		    			stmt.executeUpdate(mysql);
		    			}
		    			catch(Exception e) {}
                                            }
                                           
                                            
                                        }
                                        else if(data.charAt(0)=='l')
                                        {
                                           String hname1="",pass1="";
                                           int i=2;
                                           for(;i<data.length();i++)
                                           {
                                             if(data.charAt(i)==',')
                                                 break;
                                             hname1=hname1+data.charAt(i);
                                           }
                                           i++;
                                           for(;i<data.length();i++)
                                           {
                                             if(data.charAt(i)==',')
                                                 break;
                                             pass1=pass1+data.charAt(i);
                                           }
                                           System.out.println("user logined as : "+"usename :"+hname1+" "+"password :"+pass1);
                                           String mysql="select * from users where (hname='"+hname1+"'and pass='"+pass1+"')";
                                           ResultSet rs = null;
				            try {
					           rs=stmt.executeQuery(mysql);
				                } 
                                            catch (SQLException e1) 
                                                {
					           // TODO Auto-generated catch block
					          e1.printStackTrace();
				                }
                                            try {
                                                  if(rs.next())
                                                    {
					              while(rs.next())
					                {
						           String print="";
						           print=print+rs.getString(1)+":"+rs.getString(2);
						//hist.append(print+"\n");
					                }
                                                    out.println("login success");
                                                    System.out.println("user logged in");
				                    }
                                            else
                                                {
                                                    out.println("login failed");
                                                    System.out.println("login failed ");
                                                }
                                                }
                                            catch (SQLException e1)
                                            {
					           // TODO Auto-generated catch block
					            e1.printStackTrace();
				            }
                                         }
                                        
		    			
		    			                             }
		    			
		    	   		//System.out.println(data);
		    	   	}
                        catch (IOException e) {
		    		System.out.println("Disconnected...");
		    	} catch (SQLException ex) { 
                    Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
                                                  } 
		    		//System.exit(0);
		    	          }   
		    
		                  };
		read.setPriority(Thread.MAX_PRIORITY);
                read.setDaemon(true);
		read.start();
                                    }
                                           }
}