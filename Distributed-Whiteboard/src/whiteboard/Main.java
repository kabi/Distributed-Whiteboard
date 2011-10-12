package whiteboard;


public class Main{
	
	private static Whiteboard parentFrame;


	//===============================================Create the GUI and display for user
	private static void createAndShowGUI() {
        //Create and set up the window.
        parentFrame = new Whiteboard();
        parentFrame.setTitle("Distributed Whiteboard");

        //Display the window.
        parentFrame.setVisible(true);
    }//end createAndShowGUI

    
    
    //========================================================Entry point for application
    public static  void main(String[] args) throws Exception  {
    	//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }//end main


}
