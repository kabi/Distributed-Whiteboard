package whiteboard.comms;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import whiteboard.object.AWhiteboardObject;

public class WhiteboardServer extends UnicastRemoteObject implements ServerBoardInterface {

	
	private static final long serialVersionUID = 3979246308238362870L;
    private static int port; //Server's port number
    private static String serverAddress; //Server's address
    private static Vector<WhiteboardInterface> clients = new Vector<WhiteboardInterface>();//List of client objects (Whiteboards) connected
    private static Vector<String> clientNameList = new Vector<String>();//List of client user names connected
	private static Registry registry; //Server's registry entry
	private static JFrame serverFrame; //Server GUI frame
    ArrayList<AWhiteboardObject> globalShapeList;//stores the global shapelist
    static String serverStartUpMessage = new String(); //Message from the server when connecting or state changing
    static String manager = "NONE"; //The user name of the manager for the current session

    public WhiteboardServer() throws RemoteException {
        super(port);
    }

    /**
     * Creates an instance of remote Whiteboard server
     * @param port the port number to start the whiteboard server
     * @throws java.rmi.RemoteException exception
     * @throws java.net.UnknownHostException
     */
    public WhiteboardServer(int port) throws RemoteException {
    	WhiteboardServer.port = port;
        globalShapeList = new ArrayList<AWhiteboardObject>();
        
        try 
        {
            // Get the address of the host the server is running on for the clients to connect to.
            serverAddress = Inet4Address.getLocalHost().toString();
            ServerGUI.hostName.setText(serverAddress);
            serverStartUpMessage = "Server up and running at: [" + serverAddress + "] \nOn port: [" + port + "]";
        } catch (UnknownHostException unknownHostException) {
            serverStartUpMessage = "Server failed to start.Please check the configuration";
        }

    }

    
    /**
     * Remote Whiteboard server method
     * Clients call this method when they want to connect to the remote server
     * clients reference and name is stored in the list
     * @param client the Whiteboard object of the client attempting to connect
     * @param clientName the user name of the client attempting to connect
     * @return result the id of the client if successful connection to the session or error code
     * @throws java.rmi.RemoteException exception
     */
    @Override
    public int registerClient(WhiteboardInterface client, String clientName) throws RemoteException {
        // Prompt for permission from the server for a client to connect
    	int confirm = JOptionPane.showConfirmDialog(serverFrame, "Allow " + clientName + " to connect?",
    			"Confirm User" , JOptionPane.YES_NO_OPTION);
    	
    	// If the server allows, check if the client Whiteboard or the user name is already registered
    	// with the server
    	if(confirm == JOptionPane.YES_OPTION)
    	{
	    	for(int a = 0; a < clientNameList.size(); a++)
	            System.out.println(clientNameList.get(a));
	    	int result; 
	    	
			if(clients.contains(client) && clientNameList.contains(clientName)) 
	        {
	            //System.out.println(clientName+" already connected");
	            updateGUIMessage("\n" +clientName + " already connected");
	            result = 500;
	        
	        }else if (clientNameList.contains(clientName)) 
	        {
	            //System.out.println("Client with the name:- "+clientName+" already connected");
	            updateGUIMessage("\nClient with the name:- "+clientName+" already connected");
	            result = -999;
	        }else
	        {
	        
				if((clients.contains(client) == false) && (clientNameList.contains(clientName) == false)) 
				{
		            // Add the client Whiteboard object and user name to the server
		            clients.add(client);
		            clientNameList.add(clientName);
		            
		            //sets the global shapelist to the newly added client
		            client.setShapeList(globalShapeList);
		            
		            // Return the index of newly added client
		            result = clients.indexOf(client);
		            updateGUIMessage("\nClient " + clientName + " joined the session.");
		        } 
				else result = -1;
	        }
			ServerGUI.updateClientList(clientNameList);  
			return result;
    	}else return -2;
    }

    /**
     * Remote Whiteboard server method
     * Client calls it when sending new or modified shape object to all other clients
     * @param item shape object that is sent
     * @throws java.rmi.RemoteException exception
     */
    @Override
    public void broadcastAddShape(AWhiteboardObject item) throws RemoteException {
        globalShapeList.add(item);
        for (int i = 0; i < clients.size(); i++) {
            try {
                ((clients.elementAt(i))).appendChartItem(item);
            } catch (Exception e) {
                System.out.println("error" + e);
            }
        }
    }

    /**
     * Remote Whiteboard server method
     * Client calls it when deleting shapes
     * @param shapelist array of objects on the whiteboard
     * @throws java.rmi.RemoteException exception
     */
    @Override
    public void broadcastDeleteShape(ArrayList<AWhiteboardObject> shapelist) throws RemoteException {
        this.globalShapeList = shapelist;
        for (int i = 0; i < clients.size(); i++) {
            clients.elementAt(i).setShapeList(globalShapeList);
        }
    }

    /**
     * Remote Whiteboard server method
     * Client calls it when refreshing the state of Whiteboard shapes
     * @param shapelist array of objects on the whiteboard
     * @throws java.rmi.RemoteException exception
     */
    @Override
    public void setGlobalShapeList(ArrayList<AWhiteboardObject> shapelist) throws RemoteException {
        this.globalShapeList = shapelist;
    }

    
    /**
     * Remote Whiteboard server method
     * Client calls it when leaving the session
     * @param exitedClient the client's Whiteboard object
     * @param userName the client user name
     * @param cause reason for the client leaving
     * @param isManager to check if client is session Manager
     * @throws java.rmi.RemoteException exception
     */
    @Override
	public void clientExit(WhiteboardInterface exitedClient, String userName, String cause, boolean isManager) {
		updateGUIMessage("\nClient " + userName + cause);
        clients.remove(exitedClient);
        clientNameList.remove(userName);
        if(isManager)
        	manager = "NONE";
        ServerGUI.jlClients.setListData(clientNameList);
	}	
    
    
    /**
     * main method
     * @throws java.net.UnknownHostException
     * @throws java.net.MalformedURLException
     */
    public static void main(String[] args) {
    	
    	javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });      
    }
    
	
	/**
	 *  Method to initialize the server graphical user interface
	 */
  	private static void createAndShowGUI() {
          //Create and set up the window.
          serverFrame = ServerGUI.serverGUI();
          serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          //Display the window.
          serverFrame.setVisible(true);
      }//end createAndShowGUI

  	
  	/**
     * Method to start the Whiteboard server at a given port
     * @param portNumber the port to start the server on
     */
	public static void startServer(int portNumber) 
	{
		port = portNumber;
		
		try 
		{
            // Create and bind the server to the registry at the given port
			// with the name WhiteboardServer
			
            registry = LocateRegistry.createRegistry(port);
            WhiteboardServer server = new WhiteboardServer(port); 
            registry.rebind("WhiteboardServer", server);
            updateGUIMessage("");
            ServerGUI.portNumber.setEnabled(false);
            ServerGUI.btnStart.setEnabled(false);
			ServerGUI.btnStop.setEnabled(true);
        } 
		catch (ExportException ex) 
		{
            updateGUIMessage("\nPort already in use: " +  port);
            ServerGUI.portNumber.setEnabled(true);
            ServerGUI.btnStart.setEnabled(true);
			ServerGUI.btnStop.setEnabled(false);
        } catch (AccessException e) {
			updateGUIMessage("\nPermission denied to start the requested resources");
			ServerGUI.portNumber.setEnabled(true);
            ServerGUI.btnStart.setEnabled(true);
			ServerGUI.btnStop.setEnabled(false);
		} catch (RemoteException e) {
			updateGUIMessage("\n"+ e.getMessage());
			ServerGUI.portNumber.setEnabled(true);
            ServerGUI.btnStart.setEnabled(true);
			ServerGUI.btnStop.setEnabled(false);
		}
		
	}

	/**
     * Method to stop the Whiteboard server
     */
	public static void stopServer() {
		// TODO Fix bug, server not sending message to all clients
		//send message to clients informing them of server shutdown
		try 
		{
			for(int a = 0; a < clients.size(); a++)
			{
				//System.out.println("Exit: " + 
				clients.elementAt(a).setServerMessage("ServerShutdown");
				for(int b = 0; b < clients.size(); b++)
				{
					//System.out.println("Exit: " + 
					clients.elementAt(b).setServerMessage("ServerShutdown");
					for(int c = 0; c < clients.size(); c++)
					{
						//System.out.println("Exit: " + 
						clients.elementAt(c).setServerMessage("ServerShutdown");
						
					}
					for(int c = 0; c < clients.size(); c++)
					{
						//System.out.println("Exit: " + 
						clients.elementAt(c).setServerMessage("ServerShutdown");
					}
				}
			}
			registry.unbind("WhiteboardServer");
			System.exit(0);
			
		}catch (ArrayIndexOutOfBoundsException e) {
			//e.printStackTrace();
		} catch (AccessException e) {
			//e.printStackTrace();
		} catch (RemoteException e) {
			//e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	/**Remote Whiteboard method
     * Method to send server communication to the clients
     * @param client id of the client to communicate with
     * @param message the server message to send to client
     * @throws java.rmi.RemoteException exception
     */
	public static void broadcastMessage(int client, String message) throws RemoteException {
		
                clients.elementAt(client).setServerMessage(message);
                //System.out.println("Exit: " + clients.elementAt(client).toString());            
        	
	}
	
	
	/**
     * Method to provide feedback to server manager through GUI
     * @param message the message to display to the server manager
     */
	static void updateGUIMessage(String message)
	{
		serverStartUpMessage = serverStartUpMessage.concat(message);
		ServerGUI.txtFeedback.setText(serverStartUpMessage);
		ServerGUI.txtFeedback.setCaretPosition(ServerGUI.txtFeedback.getText().length());
	}

	
	/**
     * Method to remove clients from the active session
     * @param ejectedClient ID of client to be ejected
     * @param userName the user name of ejected client
     * @throws java.rmi.RemoteException exception
     */
	public static void ejectClients(int ejectedClient, String userName) {
		try 
		{
			broadcastMessage(ejectedClient, "Ejected");
			clients.remove(ejectedClient);
	        clientNameList.remove(userName);
			
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
        
	}
	
	/**
     * Method to get the user names of connected session's clients
     * @return clientNameList a vector of user names of connected clients
     */
	public static Vector<String> getClientNames() {
		return clientNameList;
	}

	/**
     * Method to appoint a session manager
     * @param clientManager ID of client to be appointed as session manager
     * @param userName user name of client to be appointed as session manager
     * @throws java.rmi.RemoteException exception
     */	
	public static void setManager(int clientManager, String userName) {
	
		try 
		{
			if(!(manager.equalsIgnoreCase("NONE")))
			{
				int prevClient = Integer.parseInt(manager.split("~")[0]);
				broadcastMessage(prevClient, "ChangedManager");
			}
			
			broadcastMessage(clientManager, "IsManager");
			updateGUIMessage("\nClient " + userName + " is managing the session.");
			manager = clientManager + "~" + userName;
	        //ServerGUI.jlClients.setListData(clientNameList);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
        
		
		
	}	
	
	
}
