/**
 * server is the package for class placement.
 */
package server;
// import statements
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
/**
 * This class launches the server side of the program. Creates a registry
 * at port 8081 and binds the remote reference to the specified name.
 * User can also provide a hostname on the command line (default is localhost).
 * @author		Richard Barney
 * @version		1.0.0 March 24, 2016
 */
public class SpriteWorld { 
	/**
	 * Entry point "main()" as required by the JVM. Runs the server side of the program.
	 * @param	args	standard command line parameters (arguments) as a string array.
	 * @throws java.rmi.RemoteException
	 * @throws java.rmi.AlreadyBoundException
	 */
	public static void main(String args[]) throws java.rmi.RemoteException, java.rmi.AlreadyBoundException {
		String remoteName = "Assign3";
		String hostName = "localhost";
		int portNum = 8081;
		// if an IP is specified in the command line, use it as hostname
		if (args.length > 0) {
			hostName = args[0];
		}
		try {
			System.setProperty("java.server.rmi.hostname", hostName);
			SpriteImpl remoteRef = new SpriteImpl();
			// create registry at port and bind the remote reference to the specified name
			Registry registry = LocateRegistry.createRegistry(portNum);
			registry.bind(remoteName, remoteRef);
			System.out.print("Server is now running at " + hostName +".");
		} catch (Exception ex){
			ex.printStackTrace();
		}
	} // end method main
} // end class SpriteWorld