/**
 * server is the package for class placement
 */
package server;
//import statements
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * This class sets up a Sprite that will be displayed on screen and moves
 * around in multiple directions, bouncing off boundaries (JFrame edges).
 * @author		Todd Kelley, Richard Barney
 * @version		1.0.0 March 24, 2016
 */
@Entity
@Table(name="Sprites")
public class Sprite implements Serializable {
	/** Eclipse-generated serialVersionUID. */
	private static final long serialVersionUID = -5732987524052909849L;
	/** Random object. */
	private final static Random random = new Random();
	/** Static integer to determine the Sprite's size. */
	private final static int SIZE = 10;
	/** Static integer to determine the Sprite's speed. */
	private final static int MAX_SPEED = 5;
	/** Static integer to specify the window limit. */
	private final static int WINDOWSIZE = 400;
	/** Integer used to determine the Sprite's x-axis position. */
	private int x;
	/** Integer used to determine the Sprite's y-axis position. */
	private int y;
	/** Integer for directional x-axis. */
	private int dx;
	/** Integer for directional y-axis. */
	private int dy;
	/** Color object to specify the Sprite's colour. */
	private Color color;
	/** Integer used to specify the Sprite's ID. */
	private int id;
	
	/**
	 * Default constructor.
	 */
	public Sprite() {}
	
	/**
	 * Parameterized constructor.
	 * @param	x		integer containing the Sprite's x-axis position
	 * @param	y		integer containing the Sprite's y-axis position
	 * @param	color	Color object specifying the Sprite's colour
	 */
    public Sprite(int x, int y, Color color) {
    	this.x = x;
    	this.y = y;
    	this.color = color;
    	dx = random.nextInt(2*MAX_SPEED) - MAX_SPEED;
    	dy = random.nextInt(2*MAX_SPEED) - MAX_SPEED;
    }
    
    /**
     * Get method that returns the Sprite's x-axis position.
     * @return the Sprite's x-axis position as an integer.
     */
	public int getX() { return x; }
	/**
     * Get method that returns the Sprite's y-axis position.
     * @return the Sprite's y-axis position as an integer.
     */
	public int getY() { return y; }
	/**
     * Get method that returns the Sprite's directional x-axis.
     * @return the Sprite's directional x-axis as an integer.
     */
	public int getDX() { return dx;	}
	/**
     * Get method that returns the Sprite's directional y-axis.
     * @return the Sprite's directional y-axis as an integer.
     */
	public int getDY() { return dy;	}
	/**
     * Get method that returns the Sprite's color.
     * @return the Sprite's colour as a Color object.
     */
	public Color getColor() { return color; }
	/**
	 * Get method that returns the Sprite's ID. Used by Hibernate to give each
	 * Sprite a generated ID. 
	 * @return the Sprite's ID as an integer.
	 */
	@Id
	@GeneratedValue
	public int getSpriteID() { return id; }
	
	/**
	 * Set method that sets the Sprite's x-axis position.
	 * @param	x	integer containing the Sprite's x-axis position.
	 */
	public void setX(int x) { this.x = x; }
	/**
	 * Set method that sets the Sprite's y-axis position.
	 * @param	y	integer containing the Sprite's y-axis position.
	 */
	public void setY(int y) { this.y = y; }
	/**
	 * Set method that sets the Sprite's directional x-axis.
	 * @param	dx	integer containing the Sprite's directional x-axis.
	 */
	public void setDX(int dx) { this.dx = dx; }
	/**
	 * Set method that sets the Sprite's directional y-axis.
	 * @param	dy	integer containing the Sprite's directional y-axis.
	 */
	public void setDY(int dy) {	this.dy = dy; }
	/**
	 * Set method that sets the Sprite's directional x-axis.
	 * @param	dx	integer containing the Sprite's directional x-axis.
	 */
	public void setColor(Color color) { this.color = color;	}
	/**
	 * Set method that sets the Sprite's id. Used by Hibernate to give each
	 * Sprite a generated ID.
	 * @param	id	integer containing the Sprite's ID.
	 */
	public void setSpriteID(int id) { this.id = id; }
    
    /**
     * Void method that draws the Sprite.
     * @param	g	Graphics object.
     */
    public void draw(Graphics g) {
        g.setColor(color);
	    g.fillOval(x, y, SIZE, SIZE);
    }
    
    /**
     * Void method that determines how the Sprite moves and how it
     * will bounce off the edges of the window.
     */
    public void move() throws java.rmi.RemoteException {       
    	// if the sprite is less than 0 on x-axis and directionally is 
    	// moving to the left, then make it look like its bouncing 
    	// off the left wall by inverting the direction on x-axis
        if (x < 0 && dx < 0) {
            x = 0;
            dx = -dx;
        }
        // if the sprite is less than 0 on y-axis and directionally is 
    	// moving to the top, then make it look like its bouncing 
    	// off the top wall by inverting the direction on y-axis
        if (y < 0 && dy < 0) {
            y = 0;
            dy = -dy;
        }
        // if the sprite on the x-axis is greater than the panel width
        // and directionally is moving to the right, then make it look like
        // its bouncing off the right wall by inverting the direction
        // on x-axis
        if (x > WINDOWSIZE - SIZE && dx > 0) {
        	x = WINDOWSIZE - SIZE;
        	dx = - dx;
        }
        // if the sprite on the x-axis is greater than the panel height 
        // and directionally is moving to the bottom, then make it look like
        // its bouncing off the bottom wall by inverting the direction
        // on y-axis
        if (y > WINDOWSIZE - SIZE && dy > 0) {
        	y = WINDOWSIZE - SIZE;
        	dy = -dy;
        }
        // make the sprite move by incrementing the sprite's position on the
        // x- and y-axis with the sprite's direction
        x += dx;
        y += dy;
    } // end method move
} // end class Sprite