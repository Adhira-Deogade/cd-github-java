
/**
 * @author Adhira
 * Created: 26th March, 2019
 */


package schooldomain.studentname.connecteddevices.labs.module08;

/** Import MQTT client connector from module 6 */
import schooldomain.studentname.connecteddevices.labs.module06.MqttClientConnector;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;
import com.labbenchstudios.edu.connecteddevices.common.ConfigUtil;

/** Create subscriber applciation */
public class TempActuatorSubscriberApp extends Thread{
	
	// Create app and config instance
	private static TempActuatorSubscriberApp _App;
	private static ConfigUtil config = ConfigUtil.getInstance();
	
	// Create MQTT client
	private MqttClientConnector _mqttClient;
	
	// Obtain authentication token
	private String _authToken = config.getProperty(ConfigConst.UBIDOTS_CLOUD_SECTION, ConfigConst.USER_AUTH_TOKEN_KEY);
	
	// Obtain ubidots certificate
	private String _pemFileName = ConfigConst.UBIDOTS_CERT_FILE;
	
	// Define host 
	private String _host = config.getProperty(ConfigConst.UBIDOTS_CLOUD_SECTION, ConfigConst.BASE_URL_KEY);
	
	// Define the actuator from Ubidots cloud
	public static final String topicName = "/v1.6/devices/MyDevice/Thermostat";
	
	/** Create subscriber app constructor */
	public TempActuatorSubscriberApp() {
		super();
	}
		
	/**
	 * Create MQTT client, connect and subscribe to the given topic
	 * @param topicName: Publish messaging for given topic
	 * Keep the connection for 
	 * Unsubscribe to the topic after 60s
	 * Disconnect the MQTT client connection
	 */
	public void start(String topicName) {
		try {
			_mqttClient = new MqttClientConnector(_host, _pemFileName, _authToken);
			_mqttClient.connect();
			while(true) {
				_mqttClient.subscribeToTopic(topicName);
				Thread.sleep(30000);
			}
		} catch (InterruptedException e) {
			_mqttClient.disconnect();
			e.printStackTrace();
		}
	}
		
	/** Run the subscriber application in main function for given topic */
	public static void main(String[] args) {
		_App = new TempActuatorSubscriberApp();
		try {
			_App.start(topicName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}