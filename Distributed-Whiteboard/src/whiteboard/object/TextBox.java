package whiteboard.object;

import java.awt.Font;
import java.io.Serializable;

import whiteboard.object.Rectangle;

/**
 * <b>Text Whiteboard Object</b> <br />
 * A rectangular text area containing formatted text.
 * 
 * 
 * @author ovangle
 * @version 0.1
 */

public class TextBox extends ABoxed implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5314011502733915623L;
	private String contents;
	private Font font;
	private Rectangle textarea;
	
	public TextBox(Vector startbox, Vector endbox) throws ZeroSizeObjectException {
		super(startbox,endbox);
		this.textarea = new Rectangle(startbox,endbox);
		this.font = new Font(null, Font.PLAIN, 12);
		this.contents = null;
	}
	
	public String getContents() { return contents; }
	public Font getFont() { return font; }
	
	public void setContents(String contents) {
		this.contents = contents;
	}
	public void appendContents(String str) {
		this.contents = contents + str;
	}
	
	public void insertCharacter(char c, int index) {
		String substr1, substr2;
		try {
			substr1 = this.contents.substring(0,index);
			substr2 = this.contents.substring(index);
		} catch(IndexOutOfBoundsException e) {
			return;
		}
		this.contents = substr1 + c + substr2;
	}
	
	public void removeCharacter(int index) {
		String substr1,substr2;
		try {
			substr1 = this.contents.substring(0,index);
			substr2 = this.contents.substring(index+1);
		} catch(IndexOutOfBoundsException e) {
			return;
		}
		this.contents = substr1 + substr2;
	}
	
	public void setFont(String name, int style, int size) {
		this.font = new Font(name,style,size);
	}
	
	/**
	 * Scale object, preserving the aspect ratio and centre of the object.
	 * Contained text is also scaled.
	 */
	@Override
	public void scale(double percentage) throws ZeroSizeObjectException {
		textarea.scale(percentage);
		font = new Font(font.getName(),font.getStyle(), (int) (percentage * font.getSize()));
	}
	/**
	 * resize Rectangle containing object.
	 * Text is not affected.
	 */
	@Override
	public boolean resize(Vector resize_to, String resize_point) throws ZeroSizeObjectException {
		//TODO Resize Text?!?
		return textarea.resize(resize_to,resize_point);
	}
	
	@Override
	public Rectangle getBox() {
		return textarea;
	}
}
