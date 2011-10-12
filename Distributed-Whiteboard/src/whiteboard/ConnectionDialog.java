package whiteboard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public class ConnectionDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7866229586921060520L;
	private static JFrame parent;
	private JTextField txtServer;
	private JTextField txtPort;
	private JTextField txtUsername;
	private JButton btnConnect;
	DrawingCanvas canvas;
	int portNo = 0;
	String userName;
	String serverAddress;
	boolean fieldCorrectOrNot = false;

	@SuppressWarnings("static-access")
	public ConnectionDialog(Whiteboard parentFrame, DrawingCanvas canvas) {
		
		super(parent, "Connection Settings", true);
	    this.parent = parentFrame;
	    this.canvas = canvas; 
		setResizable(false);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 291, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblServer = new JLabel("Server:");
		GridBagConstraints gbc_lblServer = new GridBagConstraints();
		gbc_lblServer.insets = new Insets(0, 0, 5, 5);
		gbc_lblServer.gridx = 1;
		gbc_lblServer.gridy = 1;
		getContentPane().add(lblServer, gbc_lblServer);
		
		txtServer = new JTextField();
		txtServer.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_txtServer = new GridBagConstraints();
		gbc_txtServer.insets = new Insets(0, 0, 5, 5);
		gbc_txtServer.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtServer.gridx = 2;
		gbc_txtServer.gridy = 1;
		getContentPane().add(txtServer, gbc_txtServer);
		txtServer.setColumns(10);
		
		JLabel lblPort = new JLabel("Port:");
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPort.gridx = 1;
		gbc_lblPort.gridy = 3;
		getContentPane().add(lblPort, gbc_lblPort);
		
		txtPort = new JTextField();
		txtPort.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_txtPort = new GridBagConstraints();
		gbc_txtPort.insets = new Insets(0, 0, 5, 5);
		gbc_txtPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPort.gridx = 2;
		gbc_txtPort.gridy = 3;
		getContentPane().add(txtPort, gbc_txtPort);
		txtPort.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 5;
		getContentPane().add(lblUsername, gbc_lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_txtUsername = new GridBagConstraints();
		gbc_txtUsername.insets = new Insets(0, 0, 5, 5);
		gbc_txtUsername.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUsername.gridx = 2;
		gbc_txtUsername.gridy = 5;
		getContentPane().add(txtUsername, gbc_txtUsername);
		txtUsername.setColumns(10);
		
		btnConnect = new JButton("Connect");
		GridBagConstraints gbc_btnConnect = new GridBagConstraints();
		gbc_btnConnect.insets = new Insets(0, 0, 0, 5);
		gbc_btnConnect.gridx = 2;
		gbc_btnConnect.gridy = 7;
		getContentPane().add(btnConnect, gbc_btnConnect);
		btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connect(evt);
            }
        });
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    this.setLocationRelativeTo(parent);
	    this.pack();
	    this.setResizable(false);
	    this.setVisible(true);
		
	}

	protected void connect(ActionEvent evt) {
        // Information provided by the user(Server address, port number and username)
        String serverAddress = txtServer.getText();
        String portNumber = txtPort.getText();
        String userName = txtUsername.getText();
        

        // Parse the input values and check for correctness
        userName.trim();
        serverAddress.trim();
        portNumber.trim();

        if ((userName.length() <= 0) || (serverAddress.length() <= 0) || portNumber.length() <= 0) {
            JOptionPane.showMessageDialog(null, "Please enter all the fields.");
            txtServer.setText("");
            txtPort.setText("");
            txtUsername.setText("");
            fieldCorrectOrNot = false;
        }else
        {
        	for (int i = 0; i < portNumber.length(); i++) {
        		if (!Character.isDigit(portNumber.charAt(i))) {
                JOptionPane.showMessageDialog(null, "Please enter a valid port number.");
                fieldCorrectOrNot = false;
                break;
            } else fieldCorrectOrNot = true;
        }
        }
        // If the provided information is correct, then attempt to connect
        if (fieldCorrectOrNot == true) 
        {
        	
            this.portNo  = Integer.parseInt(txtPort.getText());
            this.userName = userName;
            this.serverAddress = serverAddress;
            this.setVisible(false);
            this.getParent().setVisible(true);
        }		
	}
	
	

}
