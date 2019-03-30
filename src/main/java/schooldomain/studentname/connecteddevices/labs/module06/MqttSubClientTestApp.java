package schooldomain.studentname.connecteddevices.labs.module06;
/*
 * Jave script for MQTT Subscriber Client
 * @author: (modified by) Shyama Sastha Krishnamoorthy Srinivasan 
 */

import java.util.logging.Logger;

import schooldomain.studentname.connecteddevices.common.DataUtil;
import schooldomain.studentname.connecteddevices.common.SensorData;

public class MqttSubClientTestApp {
	
	private static final Logger logger = Logger.getLogger(MqttSubClientTestApp.class.getName());
	private static MqttSubClientTestApp app;
	private MqttClientConnector mqttClient;
	public MqttSubClientTestApp()
	 {
	 super();
	 }
	
	/*
	 * Function to initialize subscribe method
	 * @param topic: Topic for subscription
	 */
	public void start(String topicName)
	{
		mqttClient = new MqttClientConnector();
		mqttClient.connect();
		mqttClient.subscribeToTopic(topicName);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mqttClient.disconnect();
	}

	

	public static void main(String[] args) {
		
		app = new MqttSubClientTestApp();
		String topic = "Temperature Sensor";							

		try
		{
			app.start(topic);
			String message = MqttClientConnector.getMsg();			//Json Data Retrieval
			logger.info("\nJson Recieved:");
			System.out.println("\nJson Received: \n" + message);
			DataUtil data = new DataUtil();								
			SensorData sensor = data.JsonToSensorData(message,null);    //Json to SensorData Conversion
			logger.info("\nConverted SensorData:");
			System.out.println("\nAfter Json to SensorData Conversion: \n"+ sensor);
			String json = data.SensorDataToJson(sensor);				//SensorData to Json Conversion
			logger.info("\nConverted Json:");
			System.out.println("\nAfter conversion back to Json: \n"+ json);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}

}