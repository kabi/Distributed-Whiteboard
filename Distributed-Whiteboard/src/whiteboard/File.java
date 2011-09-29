package whiteboard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * File System operations
 * @author Kabi
 *
 */
public class File {
//TODO PUT SHIT IN HERE
	
	//=============================================================open
		public void open()
		{
			//TODO: Scale the opened images to fit on canvas
			//T
			//---Provided to enable users to open image files
			if(_changesMade)
			{
				int confirm = JOptionPane.showConfirmDialog(this, "Save changes to file?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION);
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
			            JOptionPane.showMessageDialog(this, "Only (*.jpg) and (*.png) files allowed.", "Error saving file", JOptionPane.ERROR_MESSAGE);
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
					int confirm = JOptionPane.showConfirmDialog(this, "Save changes to file?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION);
					if(confirm == JOptionPane.CANCEL_OPTION);
					else if (confirm == JOptionPane.YES_OPTION)
					{
						save();
						if(_changesMade);
						else
						{
							_bufImage = null;
							//paintComponent(this.getGraphics());
							
							Graphics g = this.getGraphics();
						    g.setColor(Color.white);
						    g.fillRect(0,0,this.getBounds().width,this.getBounds().height);
						}
					}
					else
					{
						//_bufImage = (BufferedImage)this.createImage(this.getWidth(), this.getHeight());
						_bufImage = null;
						paintComponent(this.getGraphics());
					}
					
				}else
				{
					//_bufImage = (BufferedImage)this.createImage(this.getWidth(), this.getHeight());
					_bufImage = null;
					paintComponent(this.getGraphics());
					
				}
				repaint();
				_changesMade = false;
				
			}//end newCanvas()
			
			//================================================================exit
			public void exit() {
				//---Provided so users can exit the application. 
				//Checks for any pending changes to be saved
				if(_changesMade)
				{
					int confirm = JOptionPane.showConfirmDialog(this, "Save changes to file?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION);
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
}
