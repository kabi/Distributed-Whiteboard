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
public class FileMenu {
//TODO PUT SHIT IN HERE

	DrawingCanvas canvas;
	private String fileName;
	
	
	
	public FileMenu(DrawingCanvas canvas) {
		// TODO Auto-generated constructor stub
		this.canvas = canvas;
	}

		//=============================================================open
		public void open()
		{
			//TODO: Scale the opened images to fit on canvas
			//T
			//---Provided to enable users to open image files
			if(canvas._changesMade)
			{
				int confirm = JOptionPane.showConfirmDialog(canvas, "Save changes to file?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION);
				if(confirm == JOptionPane.CANCEL_OPTION);
				else if (confirm == JOptionPane.YES_OPTION)
				{
					save();
					if(canvas._changesMade);
					else
					{
						canvas._bufImage = null;
						openFile();
						canvas.repaint();
						canvas._changesMade = false;
					}
				}
				else
				{
					//_bufImage = (BufferedImage)this.createImage(this.getWidth(), this.getHeight());
					canvas._bufImage = null;
					openFile();
					canvas.repaint();
					canvas._changesMade = false;
				}
				
			}else openFile();
		}//end open
			
			//======================================================openFile
			public void openFile(){
				
				final JFileChooser sFile = new JFileChooser();
				int of = sFile.showOpenDialog(canvas);
				//---Helper method for opening files
				try 
				{
						if (of == JFileChooser.APPROVE_OPTION) {
							try 
							{
								Image img = ImageIO.read(sFile.getSelectedFile());
								canvas._bufImage = (BufferedImage)img;
								canvas.paintComponents(canvas.getGraphics());
								canvas.repaint();
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
		  		if(canvas._changesMade)
		  		fileName = null;
		  		save();
		  	
		  	}//end saveAs
		  
		  	
		    //===================================================================save
			public void save() {
				
				//---Enables the users to save the canvas image buffer to file
				 if(canvas._changesMade)
				 if(fileName == null || fileName.isEmpty())
				 {
					 final JFileChooser sFile = new JFileChooser();
				 
					 int response = sFile.showSaveDialog(canvas);
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
			            	ImageIO.write(canvas._bufImage, suffix, file); 
			            	
			            }
			            catch (IOException e) 
			            { 
			            	e.printStackTrace(); 
			            }
			        }
			
			        else if (suffix.toLowerCase().equals("jpg")) {
			            WritableRaster raster = canvas._bufImage.getRaster();
			            WritableRaster newRaster;
			            newRaster = raster.createWritableChild(0, 0, canvas.getWidth(), canvas.getHeight(), 0, 0, null);
			            DirectColorModel cm = (DirectColorModel) canvas._bufImage.getColorModel();
			            DirectColorModel newCM = new DirectColorModel(cm.getPixelSize(),
			                                                          cm.getRedMask(),
			                                                          cm.getGreenMask(),
			                                                          cm.getBlueMask());
			            BufferedImage rgbBuffer = new BufferedImage(newCM, newRaster, false,  null);
			            try { ImageIO.write(rgbBuffer, suffix, file); }
			            catch (IOException e) { e.printStackTrace(); }
			            canvas._changesMade = false;
			        }
			
			        else 
			        {
			            JOptionPane.showMessageDialog(canvas, "Only (*.jpg) and (*.png) files allowed.", "Error saving file", JOptionPane.ERROR_MESSAGE);
			            fileName = null;
			            canvas._changesMade = true;
			        }
				 
		    }//end save
			
			
			//=============================================================newCanvas
			public void newCanvas()
			{
				//---Provided to enable users initiate a new canvas for drawing. 
				//Checks if changes have been made to the
				//current canvas and prompts for saving if so.
				if(canvas._changesMade)
				{
					int confirm = JOptionPane.showConfirmDialog(canvas, "Save changes to file?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION);
					if(confirm == JOptionPane.CANCEL_OPTION);
					else if (confirm == JOptionPane.YES_OPTION)
					{
						save();
						if(canvas._changesMade);
						else
						{
							canvas._bufImage = null;
							//paintComponent(this.getGraphics());
							
							Graphics g = canvas.getGraphics();
						    g.setColor(Color.white);
						    g.fillRect(0, 0, canvas.getBounds().width, canvas.getBounds().height);
						}
					}
					else
					{
						//_bufImage = (BufferedImage)this.createImage(this.getWidth(), this.getHeight());
						canvas._bufImage = null;
						canvas.paintComponent(canvas.getGraphics());
					}
					
				}else
				{
					//_bufImage = (BufferedImage)this.createImage(this.getWidth(), this.getHeight());
					canvas._bufImage = null;
					canvas.paintComponent(canvas.getGraphics());
					
				}
				canvas.repaint();
				canvas._changesMade = false;
				
			}//end newCanvas()
			
			//================================================================exit
			public void exit() {
				//---Provided so users can exit the application. 
				//Checks for any pending changes to be saved
				if(canvas._changesMade)
				{
					int confirm = JOptionPane.showConfirmDialog(canvas, "Save changes to file?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION);
					if(confirm == JOptionPane.CANCEL_OPTION);
					else if (confirm == JOptionPane.YES_OPTION)
					{
						save();
						if(canvas._changesMade);
						else
						{System.exit(0);
						}
					}
					else System.exit(0);
					
				} else System.exit(0);
				
				
			}//end exit()

			public void setCanvas(DrawingCanvas canvas) {
				// TODO Auto-generated method stub
				this.canvas = canvas;
			}
}
