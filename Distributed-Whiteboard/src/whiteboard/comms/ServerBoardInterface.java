package whiteboard.comms;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import whiteboard.object.AWhiteboardObject;

public interface ServerBoardInterface extends Remote {

    /**
     * Register the remote clients to current Whiteboard server
     * @param client the client Whiteboard object
     * @param clientName client user name
     * @return status
     * @throws java.rmi.RemoteException exception
     */
    int registerClient(WhiteboardInterface client, String clientName) throws RemoteException;

    /**
     * Send the drawn shape from one client to other clients
     * @param item shape object to send to other clients
     * @throws java.rmi.RemoteException exception
     */
    void broadcastAddShape(AWhiteboardObject item) throws RemoteException;

    /**
     * Send the information of deleted shape from one client to other clients
     * @param items ArrayList of shapes deleted to send to other clients
     * @throws java.rmi.RemoteException exception
     */
    void broadcastDeleteShape(ArrayList<AWhiteboardObject> items) throws RemoteException;

    /**
     * Stores information of all the shape objects currently on the Whiteboard canvas
     * @param items ArrayList of shapes on the Whiteboard drawing canvas
     * @throws java.rmi.RemoteException exception
     */
    void setGlobalShapeList(ArrayList<AWhiteboardObject> items) throws RemoteException;

    /**
     * Notifies the server on exiting the session
     * @param whiteboardInterface the client Whiteboard
     * @param userName the user name of the exiting client
     * @param cause the reason the client is leaving
     * @param isManager to check whether the leaving client was the session manager
     * @throws java.rmi.RemoteException exception
     */
	void clientExit(WhiteboardInterface whiteboardInterface, String userName, String cause, boolean isManager) throws RemoteException;
    
}
