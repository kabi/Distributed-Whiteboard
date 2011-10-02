package whiteboard;

import javax.swing.JFrame;

public class Main {
	
	//===============================================Create the GUI and display for user
	private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new Whiteboard();
        frame.setTitle("Distributed Whiteboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window.
        frame.setVisible(true);
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
