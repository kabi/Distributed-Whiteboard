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
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * File System operations
 * @author Kabi
 *
 */
public class FileMenu {
//TODO PUT SHIT IN HERE

	DrawingCanvas canvas;
	private String fileName = new String();
	private JFileChooser fileChooser = new JFileChooser();
	
	
	
	public FileMenu(DrawingCanvas canvas) {
		// TODO Auto-generated constructor stub
		this.canvas = canvas;
		fileChooser .resetChoosableFileFilters();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image (*.jpg)", "jpg", "jpeg"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image (*.png)", "png"));
        //fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image (*.gif)", "gif"));
        
        
	}

		//=============================================================open
		public void open()
		{
			//TODO: Scale the opened images to fit on canvas
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
						canvas.setBufImage(null);
						openFile();
						canvas.repaint();
						canvas.setChangesMade(false);
					}
				}
				else
				{
					//_bufImage = (BufferedImage)this.createImage(this.getWidth(), this.getHeight());
					canvas.setBufImage(null);
					openFile();
					canvas.repaint();
					canvas.setChangesMade(false);
				}
				
			}else openFile();
		}//end open
			
			//======================================================openFile
			public void openFile(){
				
				//final JFileChooser sFile = new JFileChooser();
				int of = fileChooser.showOpenDialog(canvas);
				//---Helper method for opening files
				try 
				{
						if (of == JFileChooser.APPROVE_OPTION) {
							try 
							{
								Image img = ImageIO.read(fileChooser.getSelectedFile());
								canvas.setBufImage((BufferedImage)img);
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
					 //final JFileChooser sFile = new JFileChooser();
				 
					 int response = fileChooser.showSaveDialog(canvas);
					 if(response == JFileChooser.APPROVE_OPTION)
					 {
						 fileName = fileChooser.getSelectedFile().getAbsolutePath();
						 //checkFileExtension(fileName);
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
			            canvas.setChangesMade(false);
			        }
			
			        else 
			        {
			        	WritableRaster raster = canvas._bufImage.getRaster();
			            WritableRaster newRaster;
			            newRaster = raster.createWritableChild(0, 0, canvas.getWidth(), canvas.getHeight(), 0, 0, null);
			            DirectColorModel cm = (DirectColorModel) canvas._bufImage.getColorModel();
			            DirectColorModel newCM = new DirectColorModel(cm.getPixelSize(),
			                                                          cm.getRedMask(),
			                                                          cm.getGreenMask(),
			                                                          cm.getBlueMask());
			            BufferedImage rgbBuffer = new BufferedImage(newCM, newRaster, false,  null);
			            try 
			            { 
			            	file = new File(fileName.concat(".jpg"));
			            	ImageIO.write(rgbBuffer, "jpg", file);
			            	
			            	
			            }
			            catch (IOException e) { e.printStackTrace(); }
			            //JOptionPane.showMessageDialog(canvas, "Only (*.jpg) and (*.png) files allowed.", "Error saving file", JOptionPane.ERROR_MESSAGE);
			            //fileName = null;
			            canvas.setChangesMade(false);
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
							canvas.setBufImage(null);
							
							Graphics g = canvas.getGraphics();
						    g.setColor(Color.white);
						    g.fillRect(0, 0, canvas.getBounds().width, canvas.getBounds().height);
						}
					}
					else
					{
						canvas.setBufImage(null);
						canvas.paintComponent(canvas.getGraphics());
					}
					
				}else
				{
					canvas.setBufImage(null);
					canvas.paintComponent(canvas.getGraphics());
					
				}
				canvas.repaint();
				canvas.setChangesMade(false);
				
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
