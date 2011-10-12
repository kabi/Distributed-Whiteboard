package whiteboard.comms;

/**
 * Author: Eric Nyachae
 * ID:543645
 * Project: Distributed Computing Assignment 2: Distributed Application (Shared Whiteboard)
 * 
 * This class is simply a graphical user interface for the server. It creates a frame object when called
 * and instantiates the gui components for the server. 
 * The class layout has been formatted using the WindowBuilder Editor plug-in for Eclipse.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

public class ServerGUI{

	// declare the swing objects to be used in the GUI
	static JFrame f;
	static JPanel pnlNorth;
	static JPanel pnlSouth;
	static JPanel pnlCenter;
			
	static JTextField hostName;
	static JTextField portNumber;
		
	static JList<String> jlClients;
	static JButton btnStart;
	static JButton btnStop;
	static JButton btnLaunch;
	static JButton btnEject;
	
	static JLabel lblHost;
	static JLabel lblPort;
	static Vector<String> connectedClients = WhiteboardServer.getClientNames();
	private static JPanel panel;
	static JTextArea txtFeedback;
	
	/**
	 * @param void
	 * @return frame
	 * 
	 * This method simply creates a frame, formats the layout and sends it back to the server.
	 */
	public static JFrame serverGUI()
	{
		
		//the next block of code initializes GUI's swing components		
		f = new JFrame("Dictionary Server Settings:");
		f.setTitle("Whiteboard Server");
		pnlNorth = new JPanel();  
		pnlSouth = new JPanel(); 
		pnlCenter = new JPanel();
		pnlCenter.setBorder(new TitledBorder(null, "Connected Users", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		
		GridBagLayout gbl_pnlNorth = new GridBagLayout();
		gbl_pnlNorth.columnWidths = new int[]{79, 201, 36, 73, 108, 0, 0};
		gbl_pnlNorth.rowHeights = new int[]{28, 0, 0, 0, 0, 0, 0};
		gbl_pnlNorth.columnWeights = new double[]{1.0, 100.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlNorth.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlNorth.setLayout(gbl_pnlNorth);
		
		lblHost = new JLabel("Server Host:");
		lblHost.setPreferredSize(new Dimension(96, 16));
		GridBagConstraints gbc_lblHost = new GridBagConstraints();
		gbc_lblHost.anchor = GridBagConstraints.WEST;
		gbc_lblHost.insets = new Insets(0, 0, 5, 5);
		gbc_lblHost.gridx = 0;
		gbc_lblHost.gridy = 0;
		pnlNorth.add(lblHost, gbc_lblHost);
		hostName = new JTextField(20);
		hostName.setEditable(false);
		GridBagConstraints gbc_hostName = new GridBagConstraints();
		gbc_hostName.anchor = GridBagConstraints.WEST;
		gbc_hostName.insets = new Insets(0, 0, 5, 5);
		gbc_hostName.gridx = 1;
		gbc_hostName.gridy = 0;
		pnlNorth.add(hostName, gbc_hostName);
		
		lblPort = new JLabel("Port Number:");
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.anchor = GridBagConstraints.WEST;
		gbc_lblPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPort.gridx = 3;
		gbc_lblPort.gridy = 0;
		pnlNorth.add(lblPort, gbc_lblPort);
		portNumber = new JTextField(10);
		portNumber.setEditable(true);
		GridBagConstraints gbc_portNumber = new GridBagConstraints();
		gbc_portNumber.insets = new Insets(0, 0, 5, 5);
		gbc_portNumber.anchor = GridBagConstraints.WEST;
		gbc_portNumber.gridx = 4;
		gbc_portNumber.gridy = 0;
		pnlNorth.add(portNumber, gbc_portNumber);
		pnlCenter.setLayout(new BorderLayout(0, 0));
		
		jlClients = new JList<String>();
		jlClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlClients.setLayoutOrientation(JList.VERTICAL);
		jlClients.setVisibleRowCount(-1);
		jlClients.setBackground(Color.WHITE);
		jlClients.setListData(connectedClients);
		
		
		JScrollPane pScroll = new JScrollPane();
		pScroll.setPreferredSize(new Dimension(200, 150));
		pScroll.setAutoscrolls(true);
		pScroll.setViewportView(jlClients);
		pnlCenter.add(pScroll);

		btnEject = new JButton("Eject Client");
		btnEject.setToolTipText("Eject a connected client");
		btnEject.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {  
	        	   
				WhiteboardServer.ejectClients(jlClients.getSelectedIndex(), jlClients.getSelectedValue());	               
	          
		               
				} });
		
		btnStart = new JButton("Start Server");
		btnStart.setToolTipText("Start the Whiteboard server at the given port");
		btnStart.addActionListener(new ActionListener() { //add an event handler to start the server
			public void actionPerformed(ActionEvent e) {
				try
				{	//Initialize the whiteboard server at the given port
					WhiteboardServer.startServer(Integer.parseInt(portNumber.getText().toString())); 
				    
				}catch(NumberFormatException nf)
				{
					txtFeedback.setText("Please enter a valid port number");
				}
				  
				    
				} 
				} );
		pnlSouth.add(btnStart);
		
		btnStop = new JButton("Stop & Exit");
		btnStop.setToolTipText("Stop the server and exit");
		btnStop.addActionListener(new ActionListener() { //add event handler to stop the server
			public void actionPerformed(ActionEvent e) { 
				try 
				{
					WhiteboardServer.stopServer();//try and close the server port and unregister from registry
					portNumber.setEnabled(true);
				    f.getContentPane().repaint();
				    btnStart.setEnabled(true);
					btnStop.setEnabled(false);
				}
				catch (Exception e1) {
					
					e1.printStackTrace();
				}
			} 
		} );
		pnlSouth.add(btnStop);
		
				btnLaunch = new JButton("Set Manager");
				btnLaunch.setToolTipText("Start the manager client application");
				btnLaunch.addActionListener(new ActionListener() { //add an event handler for the close button
					public void actionPerformed(ActionEvent e) { 
						
						WhiteboardServer.setManager(jlClients.getSelectedIndex(), jlClients.getSelectedValue());
					  } 
					});
				pnlSouth.add(btnLaunch);
		
		pnlSouth.add(btnEject);
		
		f.getContentPane().add(pnlNorth, BorderLayout.NORTH);
		
		panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 5;
		gbc_panel.gridwidth = 6;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		pnlNorth.add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		
		txtFeedback = new JTextArea();
		txtFeedback.setDisabledTextColor(Color.LIGHT_GRAY);
		txtFeedback.setForeground(Color.BLACK);
		txtFeedback.setBackground(Color.WHITE);
		txtFeedback.setRows(3);
		txtFeedback.setEditable(false);
		
		JScrollPane pScroll2 = new JScrollPane();
		pScroll2.setPreferredSize(new Dimension(200, 70));
		pScroll2.setAutoscrolls(true);
		pScroll2.setViewportView(txtFeedback);
		
		
		panel.add(pScroll2);
		f.getContentPane().add(pnlCenter, BorderLayout.CENTER);
		f.getContentPane().add(pnlSouth, BorderLayout.SOUTH);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.pack(); 
		f.setVisible(true);
		
		return f;		
	}

	public static void updateClientList(Vector<String> clientNameList) {

		connectedClients = clientNameList;
		jlClients.setListData(connectedClients);
		f.revalidate();
		f.repaint();
		
	}
	
}
