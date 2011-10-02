package whiteboard;

/**
*Class Whiteboard
*@author eric
*
*GUI development by WindowBuilderPro (Eclipse)
*
*
*/

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;

import whiteboard.object.style.Pen;
import whiteboard.object.style.StyleBox;


//Pre-formatted UI with WindowBuilderPro for Eclipse
public class Whiteboard extends JFrame implements ActionListener{

    /**TODO: Pre-formatted text
     * TODO: 
	 * 
	 * Distributed Whiteboard GUI class. 
	 * NB: Much easier to use WindowBuilder plugin for Eclipse 
	 *if need to edit the UI for this class
	 */
	
	
	private static final long serialVersionUID = -1567147071020745137L;
	DrawingCanvas canvas = new DrawingCanvas();
	private JToggleButton btnFreedraw;
	private JToggleButton btnLine;
	private JToggleButton btnCircle;
	private JToggleButton btnOval;
	private JToggleButton btnRectangle;
	private JToggleButton btnText;
	private JToggleButton btnFill;
	private JToggleButton btnErase;
	private JToggleButton btnSquare;
	private JComboBox<String> cboPenSize;
	private JLabel lblPenSize;
	private JButton btnColor;
	private JLabel lblColor;
	private JPanel pnlColorChooser;
	private Color _bgColor = Color.WHITE;
	private int _penWidth = 5;
	private Pen pen = new Pen();
	private StyleBox styleBox = new StyleBox();
	private FileMenu fileMenu = new FileMenu(canvas);
	public static JTextField txtInput;
	public static boolean changesMade = false;

	public Whiteboard(){
		setTitle("Distributed Whiteboard");
		setResizable(false);
    
		ImageIcon btnIcon = new ImageIcon();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(802, 607);
        
        JSplitPane splitPane = new JSplitPane();
        splitPane.setAutoscrolls(true);
        splitPane.setDividerSize(0);
        getContentPane().add(splitPane, BorderLayout.CENTER);
        
        Container drawingCanvas = new Container();
        drawingCanvas.setBackground(_bgColor );
        drawingCanvas.setForeground(_bgColor);
        splitPane.setRightComponent(drawingCanvas);
        
        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setAutoscrolls(true);
        canvas = new DrawingCanvas();
        canvas.setAutoscrolls(true);
        canvas.setBounds(0, 0, 679, 494);
        canvas.add(scrollPane1);
        drawingCanvas.add(canvas);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 2, 2);
        scrollPane.setAutoscrolls(true);
        drawingCanvas.add(scrollPane);
        
        
        JPanel pnlTools = new JPanel();
        splitPane.setLeftComponent(pnlTools);
        GridBagLayout gbl_pnlTools = new GridBagLayout();
        gbl_pnlTools.columnWidths = new int[]{45, 30, 0, 0};
        gbl_pnlTools.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_pnlTools.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_pnlTools.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        pnlTools.setLayout(gbl_pnlTools);
        
        btnIcon = new ImageIcon("images/free_a.jpg");
        btnFreedraw = new JToggleButton(btnIcon);
        btnFreedraw.setToolTipText("Freedraw");
        btnIcon = new ImageIcon("images/free_b.jpg");
        btnFreedraw.setSelectedIcon(btnIcon);
        btnFreedraw.setActionCommand("Freedraw");
        btnFreedraw.addActionListener(this);
        btnFreedraw.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        GridBagConstraints gbc_tglbtnFreedraw = new GridBagConstraints();
        gbc_tglbtnFreedraw.insets = new Insets(0, 0, 5, 5);
        gbc_tglbtnFreedraw.gridx = 0;
        gbc_tglbtnFreedraw.gridy = 0;
        pnlTools.add(btnFreedraw, gbc_tglbtnFreedraw);
        
        btnIcon = new ImageIcon("images/line_a.jpg");
        btnLine = new JToggleButton(btnIcon);
        btnLine.setToolTipText("Draw line");
        btnIcon = new ImageIcon("images/line_b.jpg");
        btnLine.setSelectedIcon(btnIcon);
        btnLine.setActionCommand("Line");
        btnLine.addActionListener(this);
        btnLine.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        GridBagConstraints gbc_btnLine = new GridBagConstraints();
        gbc_btnLine.insets = new Insets(0, 0, 5, 5);
        gbc_btnLine.gridx = 1;
        gbc_btnLine.gridy = 0;
        pnlTools.add(btnLine, gbc_btnLine);
        
        btnIcon = new ImageIcon("images/circle_a.jpg");
        btnCircle = new JToggleButton(btnIcon);
        btnCircle.setToolTipText("Draw circle");
        btnIcon = new ImageIcon("images/circle_b.jpg");
        btnCircle.setSelectedIcon(btnIcon);
        btnCircle.setActionCommand("Circle");
        btnCircle.addActionListener(this);
        btnCircle.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        GridBagConstraints gbc_btnCircle = new GridBagConstraints();
        gbc_btnCircle.insets = new Insets(0, 0, 5, 5);
        gbc_btnCircle.gridx = 0;
        gbc_btnCircle.gridy = 1;
        pnlTools.add(btnCircle, gbc_btnCircle);
        
        btnIcon = new ImageIcon("images/ellipse_a.jpg");
        btnOval = new JToggleButton(btnIcon);
        btnOval.setToolTipText("Draw ellipse");
        btnIcon = new ImageIcon("images/ellipse_b.jpg");
        btnOval.setSelectedIcon(btnIcon);
        btnOval.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnOval.setActionCommand("Oval");
        btnOval.addActionListener(this);
        GridBagConstraints gbc_btnOval = new GridBagConstraints();
        gbc_btnOval.insets = new Insets(0, 0, 5, 5);
        gbc_btnOval.gridx = 1;
        gbc_btnOval.gridy = 1;
        pnlTools.add(btnOval, gbc_btnOval);
        
        btnIcon = new ImageIcon("images/square_a.jpg");
        btnSquare = new JToggleButton(btnIcon);
        btnSquare.setToolTipText("Square");
        btnIcon = new ImageIcon("images/square_b.jpg");
        btnSquare.setSelectedIcon(btnIcon);
        btnSquare.setActionCommand("Square");
        btnSquare.addActionListener(this);
        btnSquare.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        GridBagConstraints gbc_btnSquare = new GridBagConstraints();
        gbc_btnSquare.insets = new Insets(0, 0, 5, 5);
        gbc_btnSquare.gridx = 0;
        gbc_btnSquare.gridy = 2;
        pnlTools.add(btnSquare, gbc_btnSquare);
        
        btnIcon = new ImageIcon("images/rectangle_a.jpg");
        btnRectangle = new JToggleButton(btnIcon);
        btnRectangle.setToolTipText("Draw rectangle");
        btnIcon = new ImageIcon("images/rectangle_b.jpg");
        btnRectangle.setSelectedIcon(btnIcon);
        btnRectangle.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnRectangle.setActionCommand("Rectangle");
        btnRectangle.addActionListener(this);
        GridBagConstraints gbc_btnRectangle = new GridBagConstraints();
        gbc_btnRectangle.insets = new Insets(0, 0, 5, 5);
        gbc_btnRectangle.gridx = 1;
        gbc_btnRectangle.gridy = 2;
        pnlTools.add(btnRectangle, gbc_btnRectangle);
        
        btnIcon = new ImageIcon("images/text_a.jpg");
        btnText = new JToggleButton(btnIcon);
        btnText.setToolTipText("Insert text");
        btnIcon = new ImageIcon("images/text_b.jpg");
        btnText.setSelectedIcon(btnIcon);
        btnText.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnText.setActionCommand("Text");
        btnText.addActionListener(this);
        GridBagConstraints gbc_btnText = new GridBagConstraints();
        gbc_btnText.insets = new Insets(0, 0, 5, 5);
        gbc_btnText.gridx = 0;
        gbc_btnText.gridy = 3;
        pnlTools.add(btnText, gbc_btnText);
        
        btnIcon = new ImageIcon("images/fill_a.jpg");
        btnFill = new JToggleButton(btnIcon);
        btnFill.setToolTipText("Fill");
        btnIcon = new ImageIcon("images/fill_b.jpg");
        btnFill.setSelectedIcon(btnIcon);
        btnFill.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnFill.setActionCommand("Fill");
        btnFill.addActionListener(this);
        GridBagConstraints gbc_btnFill = new GridBagConstraints();
        gbc_btnFill.insets = new Insets(0, 0, 5, 5);
        gbc_btnFill.gridx = 1;
        gbc_btnFill.gridy = 3;
        pnlTools.add(btnFill, gbc_btnFill);
        
        btnIcon = new ImageIcon("images/erase_a.jpg");
        btnErase = new JToggleButton(btnIcon);
        btnErase.setToolTipText("Erase");
        btnIcon = new ImageIcon("images/erase_b.jpg");
        btnErase.setSelectedIcon(btnIcon);
        btnErase.setActionCommand("Erase");
        btnErase.addActionListener(this);
        btnErase.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        GridBagConstraints gbc_btnErase = new GridBagConstraints();
        gbc_btnErase.insets = new Insets(0, 0, 5, 5);
        gbc_btnErase.gridx = 0;
        gbc_btnErase.gridy = 4;
        pnlTools.add(btnErase, gbc_btnErase);
        
      
        ButtonGroup group = new ButtonGroup();
        group.add(btnFreedraw);
        group.add(btnLine);
        group.add(btnCircle);
        group.add(btnOval);
        group.add(btnSquare);
        group.add(btnRectangle);
        group.add(btnText);
        group.add(btnErase);
         
        
        JPanel pnlPreviews = new JPanel();
        GridBagConstraints gbc_pnlPreviews = new GridBagConstraints();
        gbc_pnlPreviews.gridwidth = 3;
        gbc_pnlPreviews.fill = GridBagConstraints.BOTH;
        gbc_pnlPreviews.gridx = 0;
        gbc_pnlPreviews.gridy = 8;
        pnlTools.add(pnlPreviews, gbc_pnlPreviews);
        GridBagLayout gbl_pnlPreviews = new GridBagLayout();
        gbl_pnlPreviews.columnWidths = new int[]{0, 0};
        gbl_pnlPreviews.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
        gbl_pnlPreviews.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_pnlPreviews.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        pnlPreviews.setLayout(gbl_pnlPreviews);
        
        JSeparator separator_2 = new JSeparator();
        GridBagConstraints gbc_separator_2 = new GridBagConstraints();
        gbc_separator_2.insets = new Insets(0, 0, 5, 0);
        gbc_separator_2.gridx = 0;
        gbc_separator_2.gridy = 1;
        pnlPreviews.add(separator_2, gbc_separator_2);
        
        JLabel label = new JLabel("");
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.insets = new Insets(0, 0, 5, 0);
        gbc_label.gridx = 0;
        gbc_label.gridy = 2;
        pnlPreviews.add(label, gbc_label);
        
        lblPenSize = new JLabel("Pen Size");
        lblPenSize.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        GridBagConstraints gbc_lblPenSize = new GridBagConstraints();
        gbc_lblPenSize.insets = new Insets(0, 0, 5, 0);
        gbc_lblPenSize.gridx = 0;
        gbc_lblPenSize.gridy = 3;
        pnlPreviews.add(lblPenSize, gbc_lblPenSize);
        
        cboPenSize = new JComboBox<String>();
        cboPenSize.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "4", "6", "8", "10", "12", "14", "16", "18", "20"}));
        cboPenSize.setSelectedIndex(1);
        cboPenSize.setToolTipText("Pen and Eraser Size");
        GridBagConstraints gbc_cboPenSize = new GridBagConstraints();
        gbc_cboPenSize.insets = new Insets(0, 0, 5, 0);
        gbc_cboPenSize.fill = GridBagConstraints.HORIZONTAL;
        gbc_cboPenSize.gridx = 0;
        gbc_cboPenSize.gridy = 4;
        pnlPreviews.add(cboPenSize, gbc_cboPenSize);
        cboPenSize.addItemListener(new ItemListener()
        {
        	  public void itemStateChanged(ItemEvent ie)
        	  {
        		  _penWidth  = Integer.parseInt(cboPenSize.getSelectedItem().toString());	 
        		  pen.setPen(_penWidth, pen.getPen().getEndCap(), pen.getPen().getLineJoin());
        		  canvas.setPen(pen);
        		  revalidate();
        	  }
        });
        
        pnlColorChooser = new JPanel();
        getContentPane().add(pnlColorChooser, BorderLayout.SOUTH);
        GridBagLayout gbl_pnlColorChooser = new GridBagLayout();
        gbl_pnlColorChooser.columnWidths = new int[]{0, 0, 0};
        gbl_pnlColorChooser.rowHeights = new int[]{0, 0, 0};
        gbl_pnlColorChooser.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gbl_pnlColorChooser.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        pnlColorChooser.setLayout(gbl_pnlColorChooser);
        
        btnColor = new JButton("Pen Color");
        btnColor.setActionCommand("PenColor");
        btnColor.addActionListener(this);
        GridBagConstraints gbc_btnColor = new GridBagConstraints();
        gbc_btnColor.insets = new Insets(0, 0, 5, 5);
        gbc_btnColor.gridx = 0;
        gbc_btnColor.gridy = 0;
        pnlColorChooser.add(btnColor, gbc_btnColor);
        
        lblColor = new JLabel("");
        lblColor.setPreferredSize(new Dimension(79, 23));
        lblColor.setMinimumSize(new Dimension(79, 23));
        lblColor.setMaximumSize(new Dimension(79, 23));
        lblColor.setOpaque(true);
        lblColor.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, Color.BLACK));
        lblColor.setBackground(Color.BLACK);
        GridBagConstraints gbc_lblColor = new GridBagConstraints();
        gbc_lblColor.insets = new Insets(0, 0, 0, 5);
        gbc_lblColor.gridx = 0;
        gbc_lblColor.gridy = 1;
        pnlColorChooser.add(lblColor, gbc_lblColor);
        pnlColorChooser.setVisible(true);
        
        JMenuBar menuMainMenu = new JMenuBar();
        menuMainMenu.setName("");
        setJMenuBar(menuMainMenu);
        
        JMenu mnFile = new JMenu("File");
        mnFile.setActionCommand("File");
        menuMainMenu.add(mnFile);
        
        JMenuItem mntmNew = new JMenuItem("New");
        mntmNew.setMnemonic(KeyEvent.VK_N);
        mntmNew.setActionCommand("New");
        mntmNew.addActionListener(this);
        mnFile.add(mntmNew);
        
        JMenuItem mntmOpen = new JMenuItem("Open");
        mntmOpen.setMnemonic(KeyEvent.VK_O);
        mntmOpen.setActionCommand("Open");
        mntmOpen.addActionListener(this);
        mnFile.add(mntmOpen);
        
        JSeparator separator = new JSeparator();
        mnFile.add(separator);
        
        JMenuItem mntmSave = new JMenuItem("Save");
        mntmSave.setMnemonic(KeyEvent.VK_S);
        mntmSave.setActionCommand("Save");
        mntmSave.addActionListener(this);
        mnFile.add(mntmSave);
        
        JMenuItem mntmSaveAs = new JMenuItem("Save As");
        mntmSaveAs.setActionCommand("SaveAs");
        mntmSaveAs.addActionListener(this);
        mnFile.add(mntmSaveAs);
        
        JSeparator separator_1 = new JSeparator();
        mnFile.add(separator_1);
        
        JMenuItem mntmExit = new JMenuItem("Exit");
        mntmExit.setMnemonic(KeyEvent.VK_E);
        mntmExit.setActionCommand("Exit");
        mntmExit.addActionListener(this);
        mnFile.add(mntmExit);
    } 
    
	



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 if(e.getActionCommand().equalsIgnoreCase("Freedraw"))
			 {	canvas.setShape(0);
			 	pen.setPen(pen.getPen().getLineWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			 	canvas.setPen(pen);
			 }
		 else if(e.getActionCommand().equalsIgnoreCase("Line"))
			 {	canvas.setShape(1);	 
			 	pen.setPen(pen.getPen().getLineWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			 	canvas.setPen(pen);
			 }
		 else if(e.getActionCommand().equalsIgnoreCase("Rectangle"))
			 {	canvas.setShape(2);
			 	pen.setPen(pen.getPen().getLineWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			 	canvas.setPen(pen);
			 }
		 else if(e.getActionCommand().equalsIgnoreCase("Square"))
			 {	canvas.setShape(3);
			 	pen.setPen(pen.getPen().getLineWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			 	canvas.setPen(pen);
			 }
		 else if(e.getActionCommand().equalsIgnoreCase("Oval"))
			 {	canvas.setShape(4);
			 	pen.setPen(pen.getPen().getLineWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			 	canvas.setPen(pen);
			 }
		 else if(e.getActionCommand().equalsIgnoreCase("Circle"))
			 {	canvas.setShape(5);	
			 	pen.setPen(pen.getPen().getLineWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			 	canvas.setPen(pen);
			 }
		 else if(e.getActionCommand().equalsIgnoreCase("Text"))
			 {	
			 	canvas.setTextDialog(this);
			 	canvas.setShape(6);
				pen.setPen(pen.getPen().getLineWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
				canvas.setPen(pen);
			 }
		 else if(e.getActionCommand().equalsIgnoreCase("Erase"))
		 	{	canvas.setShape(7);
			 	pen.setPen(pen.getPen().getLineWidth(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);	
			 	canvas.setPen(pen);
		 	}
		else if(e.getActionCommand().equalsIgnoreCase("Fill"))
		 	{	JToggleButton btn = (JToggleButton)e.getSource();
			 	styleBox.setMode(btn.isSelected());
			 	canvas.setMode(styleBox.getMode());
		 	}
		 else if(e.getActionCommand().equalsIgnoreCase("PenColor"))
		 {
			 System.out.println(e.getActionCommand().toString());
			 Color prevColor = lblColor.getBackground();
			 Color newColor = JColorChooser.showDialog(this,"Choose Background Color", prevColor);
			 pen.set_penColor(newColor);
			 lblColor.setBackground(newColor);
			 revalidate();
		 }
		 else if(e.getActionCommand().equalsIgnoreCase("Open"))
			{
			 	fileMenu.setCanvas(canvas); 
			 	fileMenu.open();
			}
		 else if(e.getActionCommand().equalsIgnoreCase("Save"))
		 	{
			 	fileMenu.setCanvas(canvas); 
			 	fileMenu.save();
		 	}
		 else if(e.getActionCommand().equalsIgnoreCase("SaveAs"))
		 	{
			 	fileMenu.setCanvas(canvas); 
			 	fileMenu.saveAs();
		 	}
		 else if(e.getActionCommand().equalsIgnoreCase("New"))
		 	{
			 	fileMenu.setCanvas(canvas); 
			 	fileMenu.newCanvas();
		 	}
		 else if(e.getActionCommand().equalsIgnoreCase("Exit"))
		 	{
			 	fileMenu.setCanvas(canvas); 
			 	fileMenu.exit();
		 	}
		 revalidate();	 
		 repaint();
		 

	}
	
	
	
	
}
	
	

