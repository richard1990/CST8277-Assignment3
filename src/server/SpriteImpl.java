/**
 * server is the package for class placement.
 */
package server;
// import statements
import java.awt.Color;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.tool.hbm2ddl.SchemaExport;
/**
 * This class handles the implementation of SpriteInterface.java. It allows
 * a client to create a Sprite, tell the client the window dimensions,
 * return a list containing the Sprites, gives the client a specific Color for Sprites,
 * and moves Sprites around the window.
 * @author		Richard Barney
 * @version		1.0.0 March 24, 2016
 */
public class SpriteImpl extends UnicastRemoteObject implements SpriteInterface, Runnable {
	/** Eclipse-generated serialVersionUID. */
	private static final long serialVersionUID = -4980321111401088894L;
	/** List to hold all Sprites generated. */
	private List<Sprite> spritesArr;
	/** SessionFactory object. */
	private SessionFactory factory;
	/** Static integer to specify JFrame window size. */
	private final static int WINDOWSIZE = 400;
	
	/**
	 * Default constructor. Builds everything necessary for Hibernate.
	 * @throws RemoteException
	 */
	public SpriteImpl() throws RemoteException {
		spritesArr = new ArrayList<Sprite>();
	    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
	    try {
	        MetadataImplementor meta = (MetadataImplementor) new MetadataSources(registry)
	        .addAnnotatedClass(Sprite.class).buildMetadata();
	        new SchemaExport(meta).create(true, true);
	        factory = meta.buildSessionFactory();
	    } catch (Exception ex) {
	    	StandardServiceRegistryBuilder.destroy(registry);
	    }
	}
	
	/**
	 * Overridden void method that creates a new Sprite. Makes use of Session
	 * objects to keep the Sprites as persistent objects.
	 * @param	x		x-value of new Sprite.
	 * @param	y		y-value of new Sprite.
	 * @param	color	Color object of the new Sprite.
	 * @throws	RemoteException
	 */
	@SuppressWarnings("unchecked")
	@Override 
	public void createNewSprite(int x, int y, Color color) throws RemoteException {
		// setup the session and create the new Sprite
		Session session = factory.getCurrentSession();
		Sprite sprite = new Sprite(x, y, color);
		try {
			// save the Sprite to a session and add it to the ArrayiList
			session.beginTransaction();
			session.save(sprite);
			spritesArr = (ArrayList<Sprite>) session.createCriteria(Sprite.class).list();
			session.getTransaction().commit();
		} catch(Exception ex) {
			// rollback if there was a problem
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		new Thread(this).start();
	}
	
	/**
	 * Overridden get method that returns a list of the Sprites currently active.
	 * @return	a list of active Sprites.
	 * @throws	RemoteException
	 */
	@Override 
	public List<Sprite> getListOfSprites() throws RemoteException {
		return spritesArr;		
	}
	/**
	 * Overridden get method that returns the length of the JPanel as an integer.
	 * @return an integer to be used for the JPanel length.
	 * @throws RemoteException
	 */
	@Override
	public int getJPanelLength() throws RemoteException {
		return WINDOWSIZE;
	}	
	/**
	 * Overridden get method that returns the height of the JPanel as an integer.
	 * @return an integer to be used for the JPanel length.
	 * @throws RemoteException
	 */
	@Override
	public int getJPanelHeight() throws RemoteException {
		return WINDOWSIZE;
	}	
	/**
	 * Overridden get method that returns a random Color object for Sprite colour.
	 * @return a random Color object.
	 */
	@Override
	public Color getColor(){
		Color[] colorArr = { Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN,
				Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK,Color.RED, Color.WHITE, Color.YELLOW };
		Random rand = new Random();
		return colorArr[rand.nextInt(colorArr.length)];		
	}
	
	/**
	 * Void method that allows a Sprite to move around the window.
	 * @throws RemoteException
	 */
	@Override 
	public void moveSprite() throws RemoteException {
		// get the current session
		Session session = factory.getCurrentSession();
		try {
			// move the sprite and update the persistent instance
			session.beginTransaction();
			for(Sprite sprite : spritesArr){
				sprite.move();
				session.update(sprite);
			}
			session.getTransaction().commit();
		} catch(Exception ex) {
			// rollback if there was a problem
			ex.printStackTrace();
			session.getTransaction().rollback();
		}
		
		try {
			Thread.sleep(40); // wake up roughly 25 frames per second
		} catch(InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
     * Overridden run method necessary for Runnable interface. Keeps
     * the Sprites moving.
     */
	@Override
	public void run() {
		while(true) {
			try {
				moveSprite();
			} catch (RemoteException ex) {
				ex.printStackTrace();
			}
		}
	}
} // end class SpriteImpl