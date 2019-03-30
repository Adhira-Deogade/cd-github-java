package schooldomain.studentname.connecteddevices.labs.module06;
/*
 * Jave script for MQTT Subscriber Client
 * @author: (modified by) Shyama Sastha Krishnamoorthy Srinivasan 
 */

import java.util.logging.Logger;

import schooldomain.studentname.connecteddevices.common.DataUtil;
import schooldomain.studentname.connecteddevices.common.SensorData;

public class MqttPubClientTestApp {
	
	private static final Logger logger = Logger.getLogger(MqttPubClientTestApp.class.getName());
	private static MqttPubClientTestApp SubApp;
	private MqttClientConnector mqttClient;
	public SensorData sensorData;
	public DataUtil data;

	public MqttPubClientTestApp() {
		super();
	}
	
	/*Function to create Json data
	 * @param sensorData: SensorData instance
	 * @return: Json object
	 */
	public String createJSON(SensorData sensorData) {
		data = new DataUtil();
		SensorData sd = setSensorData(sensorData);
		logger.info("Before conversion to Json: \n");
		System.out.println(data + "\n");
		String Jobj = data.SensorDataToJson(sd);
		return Jobj;
	}
	
	/*
	 * Function to intialize or update the SensorData instance
	 * @param sensorData: variable of SensorData
	 * @return: Update to SensorData
	 */
	public SensorData setSensorData(SensorData sensorData) {
		sensorData.setName("Temperature Sensor");
		sensorData.updateTime();
		sensorData.setAvgValue((double) 10);
		sensorData.setCurValue((double) 20);
		sensorData.setMinValue((double) 5);
		sensorData.setMaxValue((double) 30);
		return sensorData;
	}
	
	/*
	 * Function to initialize publish method
	 * @param topic: Topic of the message
	 */
	public void start(String topicName)
	{
		mqttClient = new MqttClientConnector();
		sensorData = new SensorData(30.0,0.0,"Time","Temperature Sensor");
		mqttClient.connect();
		String sensor = createJSON(sensorData);
		logger.info("In Json format: \n");
		System.out.println(sensor + "\n");
		mqttClient.publishMessage(topicName, 2 , sensor.getBytes());
	}
	
	public static void main(String[] args) {

		SubApp = new MqttPubClientTestApp();
		String topic = "Temperature Sensor";
		try {
			SubApp.start(topic);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}