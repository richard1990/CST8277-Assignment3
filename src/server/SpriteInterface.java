/**
 * business is the package for class placement.
 */
package server;
//import statements
import java.awt.Color;
import java.util.List;
/**
 * This interface retrieves the list of Sprites from the server, creates a
 * new Sprite, retrieves the JFrame's length and height, moves the Sprite, and
 * retrieves the Sprite's colour. The class SpriteImpl.java implements this
 * interface.
 * @author		Richard Barney
 * @version		1.0.0 March 24, 2015
 */
public interface SpriteInterface extends java.rmi.Remote {
	/**
	 * Void method that creates a new Sprite.
	 * @param	x		x-value of new Sprite.
	 * @param	y		y-value of new Sprite.
	 * @param	color	Color object of the new Sprite.
	 * @throws	java.rmi.RemoteException
	 */
	void createNewSprite(int x, int y, Color color) throws java.rmi.RemoteException;
	/**
	 * Get method that returns a list of the Sprites currently active.
	 * @return	a list of active Sprites.
	 * @throws	java.rmi.RemoteException
	 */
	List<Sprite> getListOfSprites() throws java.rmi.RemoteException;
	/**
	 * Get method that returns the length of the JPanel as an integer.
	 * @return an integer to be used for the JPanel length.
	 * @throws java.rmi.RemoteException
	 */
	int getJPanelLength() throws java.rmi.RemoteException;
	/**
	 * Get method that returns the height of the JPanel as an integer.
	 * @return an integer to be used for the JPanel height.
	 * @throws java.rmi.RemoteException
	 */
	int getJPanelHeight() throws java.rmi.RemoteException;
	/**
	 * Get method that returns the Sprite's color as a Color object.
	 * @return a Color object for the Sprite's color.
	 * @throws java.rmi.RemoteException
	 */
	Color getColor() throws java.rmi.RemoteException;
	/**
	 * Void method that allows a Sprite to move around the window.
	 * @throws java.rmi.RemoteException
	 */
	void moveSprite() throws java.rmi.RemoteException;
} // end interface SpriteInterface