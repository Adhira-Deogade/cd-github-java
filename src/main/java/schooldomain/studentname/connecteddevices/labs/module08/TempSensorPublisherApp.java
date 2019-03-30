
/**
 * @author Adhira
 * Created: 26th March, 2019
 */

package schooldomain.studentname.connecteddevices.labs.module08;

/**  Import MQTT client connector from module06 */
import schooldomain.studentname.connecteddevices.labs.module06.MqttClientConnector;
import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;
import com.labbenchstudios.edu.connecteddevices.common.ConfigUtil;
import java.util.Random;

/** Create publisher app */
public class TempSensorPublisherApp {
	
	// Create app and config instance
	private static TempSensorPublisherApp _App;
	private static ConfigUtil config = ConfigUtil.getInstance();
	
	// Create MQTT client
	private MqttClientConnector _mqttClient;
	
	// Obtain token
	private String _authToken = config.getProperty(ConfigConst.UBIDOTS_CLOUD_SECTION, ConfigConst.USER_AUTH_TOKEN_KEY);
	
	// Obtain certificate
	private String _pemFileName = ConfigConst.UBIDOTS_CERT_FILE;
	
	// Define host
	private String _host = config.getProperty(ConfigConst.UBIDOTS_CLOUD_SECTION, ConfigConst.HOST_KEY);
	
	// My device from ubidots web app, Temperature sensor
	public static final String topicName = "/v1.6/devices/MyDevice/CurrentTemp";
	
	/**
	 * Create constructor for publisher app
	 */
	public TempSensorPublisherApp() {
		super();
	}
	
	/**
	 * Generate temperature values using random function
	 */
	public String genval(float min, float max) {
		Random rand = new Random();
		float random = min + rand.nextFloat() * (max - min);
		return Float.toString(random);
	}
	
	/**
	 * Create MQTT client, connect and publish a test message to the given topic
	 * @param topicName: Publish messaging for given topic
	 * @summary: Generate random temperature values between 15 and 25 degree Farenheit.
	 * Keep the connection for 60 seconds
	 */
	public void start(String topicName) {
		try {
			_mqttClient = new MqttClientConnector(_host,_pemFileName, _authToken);
			_mqttClient.connect();
			while (true) {
				_mqttClient.publishMessage(topicName, ConfigConst.DEFAULT_QOS_LEVEL, genval(15f, 25f).getBytes());
				Thread.sleep(36000);
			}
		} catch (InterruptedException e) {
			_mqttClient.disconnect();
			e.printStackTrace();
		}
	}
	
	 /** Run the publisher application in main function for given topic */
	public static void main(String[] args) {
		_App = new TempSensorPublisherApp();
		try {
			_App.start(topicName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
