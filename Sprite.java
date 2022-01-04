import java.awt.Color;
import java.awt.Rectangle;

public class Sprite {
	private int xPosition, yPosition, xVelocity, yVelocity, width, height, 
	initialXPosition, initialYPosition;
	private Color colour;
	
	//  Getter methods
	public int getXPosition() {return xPosition;}
	public int getYPosition() {return yPosition;}
	public int getXVelocity() {return xVelocity;}
	public int getYVelocity() {return yVelocity;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	public Color getColour() {return colour;}
	
	
	//  Setter methods
	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	 public void setXPosition(int newX, int panelWidth) {
	       xPosition = newX;
	       if(xPosition < 0) {
	           xPosition = 0;
	       } else if(xPosition + width > panelWidth) {
	           xPosition = panelWidth - width;
	       }
	 }
	 public void setYPosition(int newY, int panelHeight) {
	      yPosition = newY;
	      if(yPosition < 0) {
	          yPosition = 0;
	      } else if(yPosition + height > panelHeight) {
	          yPosition = panelHeight - height;
	      }
	 }
	public void setXVelocity(int xVelocity) {
		this.xVelocity = xVelocity;
	}
	public void setYVelocity(int yVelocity) {
		this.yVelocity = yVelocity;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public void setColour(Color colour) {
		this.colour = colour;
	}
	public void setInitialPosition(int initialX, int initialY) {
		this.initialXPosition = initialX;
		this.initialYPosition = initialY;
	}
	
	public void resetToInitialPosition() {
		this.xPosition = initialXPosition;
		this.yPosition = initialYPosition;
	}
	
	public Rectangle getRectangle() {

        return new Rectangle(getXPosition(), getYPosition(), getWidth(), getHeight());

    }
}
