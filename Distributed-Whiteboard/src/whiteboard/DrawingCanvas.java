package whiteboard;

/**
 * Authors: Eric Nyachae
 * 
 * Project: Distributed Computing Assignment 2: Distributed Whiteboard 
 * 
 * 
 * 
 * 
 * 
 */


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import whiteboard.object.AWhiteboardObject;
import whiteboard.object.Circle;
import whiteboard.object.Elipse;
import whiteboard.object.FreeLine;
import whiteboard.object.Line;
import whiteboard.object.Rectangle;
import whiteboard.object.Square;
import whiteboard.object.Vector;

class DrawingCanvas extends JPanel implements MouseListener, MouseMotionListener {
    
	/**
	 * References:

	 * <http://download.oracle.com/javase/tutorial/>
	 * <http://www.roseindia.net/java/java-tips/45examples/50mouse/25paintdemo.shtml>
	 * <http://archives.java.sun.com/cgi-bin/wa?A2=ind0404&L=java2d-interest&D=0&P=2727>
	 * 
	 */
	
	//TODO: Tom, catch up sometime to discuss the direction forward.
	//As you will note i have not used the vector objects in the classes.
	//Class to transform the screen coordinates included in whiteboard package.
	//
	
	private static final long serialVersionUID = -5879085438448438938L;
	//--- Public constants used to specify shape being drawn. 
	public static final int NONE	  = -1;
    public static final int FREELINE  = 0;
    public static final int LINE      = 1;
    public static final int RECTANGLE = 2;
    public static final int SQUARE	  = 3;
    public static final int ELIPSE    = 4;
    public static final int CIRCLE	  = 5;
    public static final int TEXTBOX	  = 6;
    public static final int ERASE	  = 7;
    
    
	//--- Variables to store the current figure info
    private int _shape         = -1;
    private int _currentStartX = 0;  // where mouse first pressed
    private int _currentStartY = 0;
    private int _currentEndX   = 0;  // where dragged to or released
    private int _currentEndY   = 0;
    private ArrayList<Point> points = new ArrayList<Point>();//points for freehand drawing
    
    //A list of objects currently on the canvas.
    private ArrayList<AWhiteboardObject> objects;
    //A temporary object for manipulation until the object is saved
    //to the list.
    private AWhiteboardObject obj;

    
    //--- BufferedImage to store the underlying saved painting.
    //    Will be initialized first time paintComponent is called.
    private BufferedImage _bufImage = null;
	private BasicStroke _pen;
	private JTextField txtInput;
	private boolean _filledMode;
	private Color _penColor;
	private String fileName;
	private boolean _changesMade = false;
	private int _posWidth = 0;
	private int _posHeight = 0;    //values to check the start and end
	private int _posX = 0;		//co-ordinates always result in a positive value object
	private int _posY = 0;
	private static String _userText;
	
	
    
    //--- Private constant for size of paint area.
    private static final int SIZE = 600; // size of paint area
    
    
    
    //====================================================== constructor
    public DrawingCanvas() {
        setPreferredSize(new Dimension(SIZE, SIZE));
        setBackground(Color.white);
        txtInput = new JTextField();
        _pen = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        _penColor = Color.BLACK;
        _filledMode = false;
        //--- Add the mouse listeners.
        this.addMouseListener(this); 
        this.addMouseMotionListener(this);
    }//endconstructor
    
    
    
    //========================================================= setShape
    public void setShape(int shape) {
        //----Provided so users can set the shape.
        _shape = shape;
    }//end setShape
    
    
    //==========================================setMode
  	public void setMode(boolean fill) {
  		//----Alternate between filled shapes and empty modes
  		_filledMode  = fill;
  		
  	}//end setMode

  	
  	
  	//==========================================setColor
  	public void setColor(Color newColor) {
  		//--- Provided so that users can select the color for the pen
  		_penColor  = newColor;
  		
  	}//end setColor
      
  	
  	
    
    //=========================================================setPen
    public void setPen(float pWidth, int pCap, int pJoin) {
        //--- Provided so users can set the style of pen, specifically the width
        _pen = new BasicStroke(pWidth, pCap, pJoin);
        
    }//end setPen
    
    
    
  //=========================================================getPen
    public BasicStroke getPen() {
        //--- Provided so we can access the style of pen currently set
        return _pen;
        
    }//end setPen
    
    
    
    //=================================================== paintComponent
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;  // downcast to Graphics2D
        if (_bufImage == null) {
            //--- This is the first time, initialize _bufImage
            int w = this.getWidth();
            int h = this.getHeight();
            _bufImage = (BufferedImage)this.createImage(w, h);
            Graphics2D gc = _bufImage.createGraphics();
            gc.setColor(Color.WHITE);
            gc.fillRect(0, 0, w, h); // fill in background
        }
        g2.drawImage(_bufImage, null, 0, 0);  // draw previous shapes
        
        drawCurrentShape(g2);
    }//end paintComponent
    
    
    
    
    //================================================= drawCurrentShape
    private void drawCurrentShape(Graphics2D g2) {
        //--- Draws current shape on a graphics context, either
        //    on the context passed to paintComponent, or the
        //    context for the BufferedImage.
    	
    	g2.setColor(_penColor);
        g2.setStroke(_pen);
        checkZeroSizeObject();
        switch (_shape) {
        
            case NONE  :
                     break;
             
            case FREELINE:
            	for (int i = 0; i < points.size() - 2; i++)
                {
                    Point p1 = points.get(i);
                    Point p2 = points.get(i + 1);

                    g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            	_changesMade = true; //flag changes made to canvas
                     break;  
            case LINE: 
                g2.drawLine(_currentStartX, _currentStartY, //no check needed as line is a 1 dimensional object
                            _currentEndX , _currentEndY);
                break;  
            case RECTANGLE:
            	if(_filledMode) 
            		g2.fillRect(_posX, _posY,
                            _posWidth, 
                            _posHeight);
            	else
                    g2.drawRect(_posX, _posY,
                            _posWidth, 
                            _posHeight);
            	_changesMade = true; //flag changes made to canvas
                break;  
            case SQUARE:
            	if(_filledMode)
            		g2.fillRect(_posX, _posY,
                            _posHeight, 
                            _posHeight);
            	else
            		g2.drawRect(_posX, _posY,
                            _posHeight, 
                            _posHeight);
            	_changesMade = true; //flag changes made to canvas
                break;  
            case ELIPSE:
            	if(_filledMode)
            		g2.fillOval(_posX, _posY,
                            _posWidth, 
                            _posHeight);
            	else
            		g2.drawOval(_posX, _posY,
                            _posWidth, 
                            _posHeight);
            	_changesMade = true; //flag changes made to canvas
                break;     
            case CIRCLE:
            	if(_filledMode)
            		g2.fillOval(_posX, _posY,
                            _posHeight, 
                            _posHeight);
            	else
                     g2.drawOval(_posX, _posY,
                             _posHeight, 
                             _posHeight);
            		_changesMade = true; //flag changes made to canvas
                     break;
                     
            case TEXTBOX:
            	
            	//TODO: (Eric) Preformatted text 
                    g2.drawString(_userText, _currentStartX, _currentStartY);
                   
                    _changesMade = true; //flag changes made to canvas
                break;
            case ERASE:
            	for (int i = 0; i < points.size() - 2; i++)
                {
                    Point p1 = points.get(i);
                    Point p2 = points.get(i + 1);
                    g2.setColor(this.getBackground());
                    g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            		g2.setColor(_penColor);
            		_changesMade = true; //flag changes made to canvas
                     break;   
            default:  // should never happen
                     g2.drawString("Error!", 10, 20);
                     _changesMade = true; //flag changes made to canvas
                     break;
                     
        }
        
        
        
    }//end paintComponent

    
    
    //===================================================== mousePressed
    public void mousePressed(MouseEvent e) {
        _currentStartX = e.getX(); // save x coordinate of the click
        _currentStartY = e.getY(); // save y
        _currentEndX   = _currentStartX;   // set end to same pixel
        _currentEndY   = _currentStartY;
        
        //If freedrawing, then instantiate the object here
        //because the object needs to be updated while the mouse is being
        //dragged.
        if(this._shape == 0 || this._shape==7){
        	//TODO Eric -- alter these variables so that vector is 
        	//instantiated with a pair of doubles, between 0 and 1.
        	//(0.0,0.0) should be the bottom left corner of the whiteboard.
        	//(1.0,1.0) should be the top right corner of the whiteboard.
        	//Just write a small conversion function. It shouldn't be hard.
        	
        	
        	//TODO: (Eric) Transfer conversion helper functions to new class
        	
        	points.add(new Point(_currentStartX, _currentStartY));
        	
        	Vector vec = new Vector(TransformCoords.userX(_currentStartX, this.getWidth()), 
        			TransformCoords.userY(_currentStartY, this.getHeight()), true);
        	this.obj = new FreeLine(vec);
        	
        }
		 	 
    }//end mousePressed

    //===================================================== mouseDragged
    public void mouseDragged(MouseEvent e) {
    		_currentEndX = e.getX();   // save new x and y coordinates
    		_currentEndY = e.getY();
    		//System.out.println(startvec.toString() + ": " + endvec.toString());
            System.out.println(_currentStartX + ": " + _currentStartY);
            System.out.println(_currentEndX + ": " + _currentEndY);
    	//If freedrawing, then update the object after each MouseEvent
    	if(this._shape == 0 || this._shape==7) {
    		
    		Vector vec = new Vector(TransformCoords.userX(_currentEndX, this.getWidth()), 
    				TransformCoords.userY(_currentEndY, this.getHeight()), true);
    		//TODO ERIC -- again make these doubles correctly scaled and oriented!!
    		//((FreeLine) this.obj).add(vec);
    		System.out.println(TransformCoords.userX(_currentEndX, this.getWidth()) +  ": " + TransformCoords.userY(_currentEndY, this.getHeight()));
    		//Add points for free drawing and erasing
    		points.add(new Point(_currentEndX, _currentEndY));
    		Graphics2D grafarea = _bufImage.createGraphics();
            drawCurrentShape(grafarea);
            
    	}
    	repaint(); 
    }//end mouseDragged
    
    
    //==================================================== mouseReleased
    public void mouseReleased(MouseEvent e) {
        // This will save the shape that has been dragged by
        // drawing it onto the bufferedImage where all shapes
        // are written.
        _currentEndX = e.getX(); // save ending coordinates
        _currentEndY = e.getY();
        
        try
        {
	        //Save the current object.
	        Vector startvec = new Vector(TransformCoords.userX(_currentStartX, this.getWidth()), 
	        		TransformCoords.userY(_currentStartY, this.getWidth()), true);
	        Vector endvec = new Vector(TransformCoords.userX(_currentEndX, this.getWidth()), 
	        		TransformCoords.userY(_currentEndY, this.getWidth()), true);
	        objects = new ArrayList<AWhiteboardObject>();
	        //TODO ERIC -- sorry!!
	        switch(this._shape) {
	        	case -1:
	        		//do nothing
	        		break;
	        	case 0:
	        		((FreeLine) this.obj).add(endvec);
	        		this.objects.add(obj);
	        		points.clear();
	        		break;
	        	case 1:
	        		this.objects.add(new Line(startvec, endvec));
	        		break;
	        	case 2:
	        		this.objects.add(new Rectangle(startvec,endvec));
	        		break;
	        	case 3:
	        		this.objects.add(new Square(startvec, endvec));
	        		break;
	        	case 4:
	        		this.objects.add(new Elipse(startvec, endvec));
	        		break;
	        	case 5:
	        		this.objects.add(new Circle(startvec, endvec));
	        		break;
	        	case 6://TODO: preformatted text e.t.c.
	        		System.out.println(_userText);
	        		_userText = showTextArea(_currentStartX, _currentStartY);
	        		//commented out for debugging, throwing null errors
	        		//this.objects.add(new TextBox(startvec,endvec));
	        		break;
	        	case 7:
	        		((FreeLine) this.obj).add(endvec);
	        		this.objects.add(obj);
	        		points.clear();
	        		break;
	        	default:
	        		//Not goint to happen
	        		System.err.println("Something fucked up royally.");
	        }
	        //Add the object to the list.
        }catch(Exception e1)
        {
        	e1.printStackTrace();
        }
        
        //--- Draw the current shape onto the buffered image.
        Graphics2D grafarea = _bufImage.createGraphics();
        drawCurrentShape(grafarea);
        this.repaint();
    }//end mouseReleased
    
    

	//========================================== ignored mouse listeners
    public void mouseMoved   (MouseEvent e) {}
    public void mouseEntered (MouseEvent e) {}
    public void mouseExited  (MouseEvent e) {}
    public void mouseClicked (MouseEvent e) {}
    
    
 
    //====================================================================showTextArea
    private String showTextArea(int _currentStartX2, int _currentStartY2) 
    {
		// TODO: (Eric) Pre-formatted persistent text area
    	
    	JPopupMenu popup = new JPopupMenu();
        popup.setLayout(new BorderLayout());
        popup.add(new JPanel());
        popup.setPopupSize(200, 20);
        txtInput = new JTextField();
        popup.add(txtInput);
        popup.show(this, _currentStartX2, _currentStartY2);
        repaint();
        return txtInput.getText();
    
	}

    
  //===================================================================saveAs
  	public void saveAs() 
  	{
  		if(_changesMade)
  		fileName = null;
  		save();
  	
  	}//end saveAs
  
  	
    //===================================================================save
	public void save() {
		
		//---Enables the users to save the canvas image buffer to file
		 if(_changesMade)
		 if(fileName == null || fileName.isEmpty())
		 {
			 final JFileChooser sFile = new JFileChooser();
		 
			 int response = sFile.showSaveDialog(this);
			 if(response == JFileChooser.APPROVE_OPTION)
			 {
				 fileName = sFile.getSelectedFile().getAbsolutePath();
			 }
		 }
	        File file = new File(fileName);
	        String suffix = fileName.substring(fileName.lastIndexOf('.') + 1);
	
	        // png files
	        if (suffix.toLowerCase().equals("png")) {
	            try 
	            { 
	            	ImageIO.write(_bufImage, suffix, file); 
	            	
	            }
	            catch (IOException e) 
	            { 
	            	e.printStackTrace(); 
	            }
	        }
	
	        else if (suffix.toLowerCase().equals("jpg")) {
	            WritableRaster raster = _bufImage.getRaster();
	            WritableRaster newRaster;
	            newRaster = raster.createWritableChild(0, 0, this.getWidth(), this.getHeight(), 0, 0, new int[] {0, 1, 2});
	            DirectColorModel cm = (DirectColorModel) _bufImage.getColorModel();
	            DirectColorModel newCM = new DirectColorModel(cm.getPixelSize(),
	                                                          cm.getRedMask(),
	                                                          cm.getGreenMask(),
	                                                          cm.getBlueMask());
	            BufferedImage rgbBuffer = new BufferedImage(newCM, newRaster, false,  null);
	            try { ImageIO.write(rgbBuffer, suffix, file); }
	            catch (IOException e) { e.printStackTrace(); }
	            _changesMade = false;
	        }
	
	        else 
	        {
	            JOptionPane.showMessageDialog(null, "Only (*.jpg) and (*.png) files allowed.", "Error saving file", JOptionPane.ERROR_MESSAGE);
	            fileName = null;
	            _changesMade = true;
	        }
		 
    }//end save
	
	
	//=============================================================newCanvas
	public void newCanvas()
	{
		//---Provided to enable users initiate a new canvas for drawing. 
		//Checks if changes have been made to the
		//current canvas and prompts for saving if so.
		if(_changesMade)
		{
			int confirm = JOptionPane.showConfirmDialog(null, "Save changes to file?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION);
			if(confirm == JOptionPane.CANCEL_OPTION);
			else if (confirm == JOptionPane.YES_OPTION)
			{
				save();
				if(_changesMade);
				else
				{
					_bufImage = null;
					paintComponent(this.getGraphics());
					revalidate();
					_changesMade = false;
				}
			}
			else
			{
				//_bufImage = (BufferedImage)this.createImage(this.getWidth(), this.getHeight());
				_bufImage = null;
				paintComponent(this.getGraphics());
				revalidate();
				_changesMade = false;
			}
			
		}else
		{
			//_bufImage = (BufferedImage)this.createImage(this.getWidth(), this.getHeight());
			_bufImage = null;
			paintComponent(this.getGraphics());
			revalidate();
			_changesMade = false;
		}
		
		
	}//end newCanvas()
	
	
	//================================================================exit
	public void exit() {
		//---Provided so users can exit the application. 
		//Checks for any pending changes to be saved
		if(_changesMade)
		{
			int confirm = JOptionPane.showConfirmDialog(null, "Save changes to file?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION);
			if(confirm == JOptionPane.CANCEL_OPTION);
			else if (confirm == JOptionPane.YES_OPTION)
			{
				save();
				if(_changesMade);
				else
				{System.exit(0);
				}
			}
			else System.exit(0);
			
		} else System.exit(0);
		
		
	}//end exit()
	
	
	//=============================================================open
	public void open()
	{
		//TODO: Scale the opened images to fit on canvas
		//T
		//---Provided to enable users to open image files
		if(_changesMade)
		{
			int confirm = JOptionPane.showConfirmDialog(null, "Save changes to file?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION);
			if(confirm == JOptionPane.CANCEL_OPTION);
			else if (confirm == JOptionPane.YES_OPTION)
			{
				save();
				if(_changesMade);
				else
				{
					_bufImage = null;
					openFile();
					repaint();
					_changesMade = false;
				}
			}
			else
			{
				//_bufImage = (BufferedImage)this.createImage(this.getWidth(), this.getHeight());
				_bufImage = null;
				openFile();
				repaint();
				_changesMade = false;
			}
			
		}else openFile();
	}//end open
		
		//======================================================openFile
		public void openFile(){
			
			final JFileChooser sFile = new JFileChooser();
			int of = sFile.showOpenDialog(this);
			//---Helper method for opening files
			try 
			{
					if (of == JFileChooser.APPROVE_OPTION) {
						try 
						{
							Image img = ImageIO.read(sFile.getSelectedFile());
							_bufImage = (BufferedImage)img;
							paintComponents(this.getGraphics());
							repaint();
						} catch (final Exception e) {
							e.printStackTrace();
						}
					}
				} catch (final Exception e) 
				{
					e.printStackTrace();
				}
		
		}//end openFile()
	
		
	//==================================================================checkZeroSizeObject
	public void checkZeroSizeObject()
	{
			//---Provided to check that the result from starting click operation and the 
			//end click operation always results in positive values for drawing objects
			//e.g. drawing from right to left or bottom to top.
	       if (_currentEndX < _currentStartX || _currentEndY < _currentStartY)
	       {
	          if (_currentEndX < _currentStartX)
	          {
	             _posWidth = _currentStartX - _currentEndX;
	             _posX   = _currentStartX - _posWidth;
	          }
	          else
	          {
	             _posX    = _currentStartX;
	             _posWidth  = _currentEndX - _currentStartX;
	
	          }
	          
	          if (_currentEndY < _currentStartY)
	          {
	             _posHeight = _currentStartY - _currentEndY;
	             _posY    = _currentStartY - _posHeight;
	          }
	          else
	          {
	             _posY    = _currentStartY;
	             _posHeight = _currentEndY - _currentStartY;
	          }
	       }
	       else
	       {
	          _posX    = _currentStartX;
	          _posY    = _currentStartY;
	          _posWidth  = _currentEndX - _currentStartX;
	          _posHeight = _currentEndY - _currentStartY;
	       }
	 }//end checkZeroSizeObject


}