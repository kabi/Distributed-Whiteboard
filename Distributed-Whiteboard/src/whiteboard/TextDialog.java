package whiteboard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class TextDialog extends JDialog implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1995582829412904130L;
	/**
	 * 
	 */
	JLabel lblText;
    JTextField txtText;
	int fontSize;
	double pixels;
    
    JButton btnOK;
    JPanel northPanel, southPanel1, southPanel;
    JFrame parent;
    JButton btnFont;
 // create a font chooser
    JFontChooser fontChooser = new JFontChooser();
	
    String text;
	private Font selectedFont;
    
    
    public TextDialog(JFrame parent, int _currentStartX2, int _currentStartY2)
    {
	    super(parent, "Text Area", true);
	    this.parent = parent;
	    northPanel = new JPanel();
	    northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	    
	    southPanel1 = new JPanel();
	    southPanel1.setLayout(new GridLayout(1, 2));
	    southPanel = new JPanel();
	    southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	    
	    String fontText = "<html><font face=\"Bodoni MT\"><b><i>font..</b><i></font>";

        text = "  ";
	    
	    lblText = new JLabel("Text :");
	    lblText.setHorizontalAlignment(SwingConstants.LEFT);
	    
	    txtText = new JTextField(text);
	    txtText.setPreferredSize(new Dimension(340, 40));
	    txtText.setBorder(BorderFactory.createLoweredBevelBorder());
	    txtText.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
            	txtText.selectAll();
              }

			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}});
	    
	    btnFont = new JButton(fontText);
	    btnFont.setPreferredSize(new Dimension(30, 20));
	    btnFont.addActionListener(this);
	    btnFont.setToolTipText("Change the title font");
	    
	    northPanel.add(lblText, FlowLayout.LEFT);
	    northPanel.add(txtText, FlowLayout.CENTER);
	    northPanel.add(btnFont, FlowLayout.RIGHT);

	    btnOK = new JButton("OK");
	    btnOK.setMnemonic('O');
	    btnOK.addActionListener(this);
	    
	    southPanel.add(btnOK);
	
	    this.getContentPane().setLayout(new GridLayout(4,1));
	    this.getContentPane().add(northPanel);
	    this.getContentPane().add(southPanel1);
	    this.getContentPane().add(southPanel, BorderLayout.SOUTH);
	
    
	    this.setSize(430, 180);
	    this.setLocationRelativeTo(null);
	    this.setLocation(_currentStartX2, _currentStartY2);
	    this.setResizable(false);
	    this.setVisible(true);
    }
    
    
    
    public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
    	if(e.getSource() == btnOK)
    	{
    		
        	try
        	{
        		text  = txtText.getText().toString();

        	}
        	catch (Exception ex)
        	{
        		//ex.printStackTrace();
        	}
        	
            this.setVisible(false);
            //System.out.println("Width: " + pageWidth + "Height: " + pageHeight);
    	}else if(e.getSource() == btnFont)
    	{
    		// show a dialog that contains JFontChooser component
    	    int retValue = fontChooser.showDialog(null);
    	    
    	    
    	    if(retValue == JFontChooser.OK_OPTION) {
    	    	selectedFont = fontChooser.getSelectedFont();
    	        txtText.setFont(selectedFont);
    	        this.repaint();
    	    }    

    	    
    	}
    	
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public Font getSelectedFont()
	{
		return selectedFont;
	}

}
