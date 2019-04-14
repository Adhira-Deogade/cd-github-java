 

/**
 * Coap server connector to run the application and include resource handler 
 */

package schooldomain.studentname.connecteddevices.labs.module07;
import java.util.logging.Logger;
import org.eclipse.californium.core.CoapServer;



/**
 * 
 * @author Adhira
 *
 */
public class CoapServerConnection {
	
	// static
	private static final Logger _Logger = Logger.getLogger(CoapServerConnection.class.getName());
	
	// private vars
	private CoapServer _coapServer;
	
	//constructors
	public CoapServerConnection() {
		super();
	}
	
	
	public void start () {
		if(_coapServer == null) {
			_Logger.info("Creating Coap server instance and 'Test' handler");
			_coapServer = new CoapServer();
			
			// Create a new TempResourceHandler and add it to CoapServer
			TempResourceHandler tempRH = new TempResourceHandler();
			_coapServer.add(tempRH);
			
		}
		
		_Logger.info("Starting Coap server...");
		_coapServer.start();
		
	}
	
	public void stop() {
		_Logger.info("Stopping coap server...");
		_coapServer.stop();
	}
	

}
