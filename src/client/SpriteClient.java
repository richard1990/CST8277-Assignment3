/**
 * client is the package for class placement.
 */
package client;
// import statements
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JFrame;
import javax.swing.JPanel;
import server.SpriteInterface;
/**
 * This class handles the client-side portion of the program. It creates the
 * GUI and gets the list of Sprites from the server.
 * @author		Richard Barney
 * @version		1.0.0 March 24, 2016
 */
public class SpriteClient extends JPanel{
	/** Eclipse-generated serialVersionUID. */
	private static final long serialVersionUID = -1064154829777931416L;
	/** Registry object. */
	private Registry registry;
	/** SpriteInterface object to make connection to server. */
	private SpriteInterface server;
	/** JFrame object. */
	private JFrame frame;
	/** Color object used to retrieve Sprite's colour from the server. */
	private Color color;
	/** Static String to specify host. */
	private static String hostName = "localhost";
	/** Static integer to specify port number. */
	private static final int PORT = 8081;
	/** Static String to specify the name for the remote reference to look up. */
	private static final String REMOTE_REF = "Assign3";

	/**
	 * Default constructor. Sets up everything on the client side, including the
	 * GUI.
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public SpriteClient() throws RemoteException, NotBoundException {
		registry = LocateRegistry.getRegistry(hostName, PORT);
		server = (SpriteInterface) registry.lookup(REMOTE_REF);
		color = server.getColor();
		addMouseListener(new Mouse());
		// setup the GUI
		frame = new JFrame("SpriteWorld");
		frame.setSize(server.getJPanelHeight(), server.getJPanelLength());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	/**
	 * Overridden paintComponent method to draw the Sprites.
	 * @param	g	Graphics object.
	 */
	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		try {
			// get the list of Sprites from the server and display them all
			if (server.getListOfSprites() != null) {
				for (int i = 0; i < server.getListOfSprites().size(); i++) {
					server.getListOfSprites().get(i).draw(g);
				}
			}
		} catch(RemoteException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Inner class Mouse to handle MouseEvent. Creates Sprite at location of
	 * click/press.
	 */
	private class Mouse extends MouseAdapter {
		@Override
	    public void mousePressed(final MouseEvent event){
	        try {
	        	// create new Sprite at location of click/press with the color
	        	// given by the server to this client
	        	server.createNewSprite(event.getX(), event.getY(), color);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	    }
	} // end class Mouse
	
	/**
	 * Void method that animates the program. Makes use of an infinite
	 * loop to keep drawing the Sprite objects.
	 */
	public void animate(){
	    while (true){	
	    	repaint();
	        try {
	            Thread.sleep(40); // wake up roughly 25 frames per second
	        }
	        catch(InterruptedException ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	
	/**
	 * Entry point "main()" as required by the JVM.
	 * @param	args	standard command line parameters (arguments) as a string array.
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public static void main(String args[]) throws RemoteException, NotBoundException {
		if (args.length > 0) {
			hostName = args[0];
		}
		SpriteClient client = new SpriteClient();
		client.animate();
	}
} // end class SpriteClient