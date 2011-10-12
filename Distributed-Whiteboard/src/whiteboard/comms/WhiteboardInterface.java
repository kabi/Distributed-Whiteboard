package whiteboard.comms;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import whiteboard.object.AWhiteboardObject;

// Remote interface for the server to be able to access the client methods
public interface WhiteboardInterface extends Remote {

    /**
     * Synchronizes the client shapes when new shapes are drawn/ deleted
     * @param globalShapeList ShapeInfo object to be appended
     * @throws java.rmi.RemoteException exception
     */
	
    public void setShapeList(ArrayList<AWhiteboardObject> globalShapeList) throws RemoteException;

    /**
     * Server calls this method to send a new object to the clients
     * @param item shape object received from the Whiteboard server
     * @throws java.rmi.RemoteException exception
     */
    public void appendChartItem(AWhiteboardObject item) throws RemoteException;
    
    /**
     * Server calls this method to send messages to the clients
     * @param serverMessage message the server wishes to communicate with the clients
     * @throws java.rmi.RemoteException exception
     */
    public void setServerMessage(String serverMessage) throws RemoteException;
}
