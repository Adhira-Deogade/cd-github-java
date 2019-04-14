
/**
 * Resource handler to convert sensor data to json.
 * Handle 4 types of Coap requests using Californium Coap package
 */


package schooldomain.studentname.connecteddevices.labs.module07;

import java.util.logging.Logger;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

import schooldomain.studentname.connecteddevices.common.DataUtil;
import schooldomain.studentname.connecteddevices.common.SensorData;

/**
 * 
 * @author Adhira
 *
 */
public class TempResourceHandler extends CoapResource {
	
	// static
	private static final Logger _Logger = Logger.getLogger(TempResourceHandler.class.getName());
	public static String ServerName = "Test"; 
	
	private SensorData sensorData = new SensorData(0.0,30.0,"time","Test");
	private DataUtil dataUtil = new DataUtil();
	private String payloader = dataUtil.toJson(sensorData);
//	private SensorData senPayloader = dataUtil.fromJson(payloader, null);
	
	
	
	// Constructor, set name to "Test"
	public TempResourceHandler() {
		super(ServerName);
		// TODO Auto-generated constructor stub
	}
	
	// Constructor with different name defined in app
	/**
	 * 
	 * @param name: Name of topic
	 */
	public TempResourceHandler(String name) {
		super(name);
	}
	
	// Constructor with boolean value
	/**
	 * 
	 * @param name: Name of topic
	 * @param visible
	 */
	public TempResourceHandler(String name, boolean visible) {
		super(name, visible);
		
	}
	
	// Public methods
	
	@Override
	/**
	 * Handling GET request
	 */
	public void handleGET(CoapExchange ce) {
//		String ResponseMessage = "Here's the response to GET 'Test' request::" + super.getName();
		
		String ResponseMessage = "Response for: " + super.getName() + ":\n json data " + payloader;
		ce.accept();
		ce.respond(ResponseCode.CONTENT, payloader, MediaTypeRegistry.APPLICATION_JSON);
//		System.out.println(ce.getRequestText());
//		ce.respond(ResponseCode.VALID, ResponseMessage);
		
		_Logger.info("Handling GET" + ResponseMessage);
		_Logger.info(ce.getRequestCode().toString() + ":" + ce.getRequestText());
		
	}
	/**
	 * Handling POST request
	 */
	public void handlePOST(CoapExchange ce) {
//		String ResponseMessage = "Here's the response to POST 'Test' request::" + super.getName();
		
		String jsonData = new String(ce.getRequestPayload());
//		sensorData = new SensorData(0.0, 30.0, "time", "Temperature");
//		SensorData senPayloader = dataUtil.fromJson(jsonData, null);
		
		String ResponseMessage = "Object created for:" + super.getName() + ":\nSensor data:\n" + jsonData;
		
//		System.out.println(ce.getRequestText());
		ce.respond(ResponseCode.CREATED, ResponseMessage);
		
		_Logger.info("Handling POST" + ResponseMessage);
		_Logger.info(ce.getRequestCode().toString() + ":" + ce.getRequestText());
		
	}
	
	/**
	 * Handling PUT request
	 */
	public void handlePUT(CoapExchange ce) {
//		String ResponseMessage = "Here's the response to PUT 'Test' request::" + super.getName();
		
		String jsonData = new String(ce.getRequestPayload());
//		senPayloader = dataUtil.fromJson(jsonData, null);
//		SensorData senPayloader = dataUtil.fromJson(jsonData, null);
		String ResponseMessage = "Object updated for: " +super.getName() + ":\nSensor data\n" + jsonData;
		
//		System.out.println(ce.getRequestText());
		ce.respond(ResponseCode.CHANGED, ResponseMessage);
		
		_Logger.info("Handling PUT" + ResponseMessage);
		_Logger.info(ce.getRequestCode().toString() + ":" + ce.getRequestText());
		
	}
	
	/**
	 * Handling DELETE request
	 */
	public void handleDELETE(CoapExchange ce) {
//		String ResponseMessage = "Here's the response to DELETE 'Test' request::" + super.getName();
		
		sensorData = null;
		String ResponseMessage = "Object deleted for:" + super.getName() + ".";
		
 		
//		System.out.println(ce.getRequestText());
		ce.respond(ResponseCode.DELETED, ResponseMessage);
		
		_Logger.info("Handling DELETE" + ResponseMessage);
		_Logger.info(ce.getRequestCode().toString() + ":" + ce.getRequestText());
		
	}
	
	

}
