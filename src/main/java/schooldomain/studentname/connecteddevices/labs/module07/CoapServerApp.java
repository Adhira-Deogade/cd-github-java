
/**
 * Application to start the Coap server
 */

package schooldomain.studentname.connecteddevices.labs.module07;
import java.util.logging.Logger;

/**
 * 
 * @author Adhira
 *
 */
public class CoapServerApp {
	
	//static
	private static final Logger _Logger = Logger.getLogger(CoapServerApp.class.getName());
	private static CoapServerApp _App;
	
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		System.out.println("Hello world");
		_App = new CoapServerApp();
		try {
			_Logger.info("ServerApp starts...");
			_App.start();
			
			
		}
		catch (Exception e) {
			_Logger.info("Server app start failed...");
			
			
		}
		
	}
	
	// private var's
	private CoapServerConnection _coapServer;
	
	// Constructor
	public CoapServerApp() {
		super();
	}
	
	// public methods
	public void start() {
		_coapServer = new CoapServerConnection();
		_coapServer.start();
	}
	
	

}
