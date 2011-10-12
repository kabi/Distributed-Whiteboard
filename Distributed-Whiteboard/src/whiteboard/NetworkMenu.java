package whiteboard;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;

import whiteboard.comms.ServerBoardInterface;
import whiteboard.comms.WhiteboardInterface;


public class NetworkMenu {

	
	DrawingCanvas canvas;
	private ConnectionDialog connectionDialog;
	private ServerBoardInterface server;
	private Registry registry;
	private Whiteboard parent;
	private String userName;
	
	
	/**
	 * Constructor for the Network menu object
	 * @param canvas to get the state of the canvas for network operations
	 */
	public NetworkMenu(DrawingCanvas canvas) {
		this.canvas = canvas;
	}

	public void setCanvas(Whiteboard whiteboard, DrawingCanvas canvas) {
				this.canvas = canvas;
			}

	public void connect(Whiteboard parentFrame) {
		// Gets the information provided by the user to connect to the Whiteboard server 
		parent = parentFrame;
        connectionDialog = new ConnectionDialog(parent, canvas);
        if(connectionDialog.fieldCorrectOrNot)
        	connectServer(connectionDialog.serverAddress, connectionDialog.portNo, connectionDialog.userName);
        
	}
	
	
	/**
     * Helper method for connecting to the server
     * @param serverAddr the Whiteboard server address
     * @param portNo the server port
     * @param userName user name of the client using this Whiteboard
     */
    void connectServer(String serverAddr, int portNo, String userName) {
        try 
        {
        	this.userName = userName;
        	
        	// Find the remote server on the registry of the provided host
            registry = LocateRegistry.getRegistry(serverAddr, (new Integer(portNo)).intValue());
            
            this.server = (ServerBoardInterface) (registry.lookup("WhiteboardServer"));
            
            // Export the Whiteboard as a remote object so that 
            // the server will be able to call it's functions when needed
            UnicastRemoteObject.exportObject((WhiteboardInterface) (parent), 0);
            if (this.canvas.objects.isEmpty()) {
            } else 
            {
                server.setGlobalShapeList(this.canvas.objects);
            }
            int disp = server.registerClient((WhiteboardInterface) (parent), userName);
            if (disp == 500) 
            {
                JOptionPane.showMessageDialog(parent, userName + " is already connected");
                UnicastRemoteObject.unexportObject(parent, true);
    			parent.mntmConnect.setEnabled(true);
            	parent.mntmDisconnect.setEnabled(false);
            } else if (disp == -999) 
            {
            	JOptionPane.showMessageDialog(parent, "Client with name " + userName + " already connected");
            	UnicastRemoteObject.unexportObject(parent, true);
    			parent.mntmConnect.setEnabled(true);
            	parent.mntmDisconnect.setEnabled(false);
            } else if (disp == -2) 
            {
            	JOptionPane.showMessageDialog(parent, "Connection refused by the server.");
            	UnicastRemoteObject.unexportObject(parent, true);
    			parent.mntmConnect.setEnabled(true);
            	parent.mntmDisconnect.setEnabled(false);
            }else 
            {
            	JOptionPane.showMessageDialog(parent, "Connected: " + userName + " to server " + serverAddr);
            	parent.mntmConnect.setEnabled(false);
            	parent.mntmDisconnect.setEnabled(true);
            	parent.setManager(false);
            	parent.setTitle("Distributed Whiteboard (ID:" + disp +", Username: " + userName+ ")");
            }
            
        } catch (RemoteException ex) {
        	JOptionPane.showMessageDialog(parent, ex.getMessage() + " Server Message");
        	try {
				UnicastRemoteObject.unexportObject(parent, true);
			} catch (NoSuchObjectException e) {
				e.printStackTrace();
			}
        } catch (NotBoundException e) {
        	JOptionPane.showMessageDialog(parent, e.getMessage() + " Server Message");
        	try {
				UnicastRemoteObject.unexportObject(parent, true);
			} catch (NoSuchObjectException e1) {
				e1.printStackTrace();
			}
        }
    }

	/**
     * Disconnects from the server
     * @param cause to inform the server of why the client was disconnected
     */
	@SuppressWarnings("static-access")
	public void disconnect(String cause) {
		// Provided so when a user disconnects or exits the session, then the server is notified 
		// and the Whiteboard set up for stand alone use.
		try
		{
			server.clientExit((WhiteboardInterface) (parent), userName, cause, parent.manager);
			UnicastRemoteObject.unexportObject(parent, true);
			parent.mntmConnect.setEnabled(true);
        	parent.mntmDisconnect.setEnabled(false);
        	parent.setManager(false);
        	parent.setTitle("Distributed Whiteboard");
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}catch(Exception e)
		{
			
		}
		
	}


	
	
}
