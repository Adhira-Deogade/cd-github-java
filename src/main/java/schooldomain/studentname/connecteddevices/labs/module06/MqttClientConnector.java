package schooldomain.studentname.connecteddevices.labs.module06;


import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import schooldomain.studentname.connecteddevices.common.SensorData;
import schooldomain.studentname.connecteddevices.common.DataUtil;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;


public class MqttClientConnector implements MqttCallback{
	
	private static final Logger logger = Logger.getLogger(MqttClientConnector.class.getName());
	private String protocol = ConfigConst.SECURE_MQTT_PROTOCOL;
	private String host = "things.ubidots.com";
	private int port = ConfigConst.SECURE_MQTT_PORT;
	
	private String clientID;
	private String brokerAddr;
	private String authToken;
//	private String userName;
	private MqttClient mqttClient;
	private SensorData sensorData;
	private DataUtil dataUtil;
	private static String mssg;
	private String pemFileName;
	private boolean isSecureConn = false;
	
	public MqttClientConnector()
	{
		if(host!=null && host.trim().length()>0)
		{
			this.sensorData = new SensorData(30.0, 0.0, "Time","Temperature Sensor");
//			this.clientID = mqttClient.generateClientId();
			logger.info("ClientID for connection: " + clientID);
			this.brokerAddr = protocol + "://" + host + ":" + port;
			logger.info("URL for connection: " + brokerAddr);
		}
	}
	
	/**
	* Constructor.
	* @param host The name of the broker to connect.
	* @param userName The username for authorizing access to the broker.
	* @param pemFileName The name of the certificate file to use. If null / invalid, ignored.
	* @param authToken Token to authorize access to cloud
	*/
	public MqttClientConnector(String _host, String _pemFileName, String _authToken)
	{
		super();
		if (_host != null && _host.trim().length() > 0) {
			host = _host;
		}
		if (_pemFileName != null) {
			File file = new File(_pemFileName);
			
			if (file.exists()) {
				protocol = ConfigConst.SECURE_MQTT_PROTOCOL;
				port = ConfigConst.SECURE_MQTT_PORT;
				pemFileName = _pemFileName;
				isSecureConn = true;
				
				logger.info("PEM file valid. Using secure connection: " + _pemFileName);
				} else {
					logger.warning("PEM file invalid. Using insecure connection: " + pemFileName);
					}
			}
		if (_authToken != null && _authToken.trim().length() > 0) {
			authToken = _authToken;
		}
		
		clientID = MqttClient.generateClientId();
		logger.info("Using ClientID: " + clientID);
		brokerAddr = protocol + "://" + host + ":" + port;
		logger.info("Using URL for broker conn: " + brokerAddr);
		}
	
	
	/**
	 * To set the values of sensor data object
	 * @param sensorData: SensorData class object instance
	 */
	public void setsenseData(SensorData sensorData) {
		sensorData.setName("Temperature Sensor");
		sensorData.updateValue(20);
	}

	/**
	 * To get the sensor data object
	 * @return sensorData: SensorData class object instance
	 */
	public SensorData getSensorData() {
		Random rand = new Random();
		float min = 15;
		float max = 25;
		float random = min + rand.nextFloat() * (max - min);
		sensorData.updateValue(random);
		return sensorData;
	}
	
	/**
	 * To create the create JSON string of sensor data and return it
	 * @return SJobj: sensor data object in JSON string format
	 */
	public String createJSON() {
		setsenseData(sensorData);
		String SJobj = dataUtil.toJson(getSensorData());
		return SJobj;
	}
	
	/*
	 * To connect
	 */
	public void connect()
	{
		if (mqttClient == null) {
			MemoryPersistence persistence = new MemoryPersistence();
		try
		{
			System.out.println("Client ID: " + clientID);
			mqttClient = new MqttClient(brokerAddr, clientID, persistence);
			MqttConnectOptions ConnOp = new MqttConnectOptions();
			ConnOp.setCleanSession(true);
			if (authToken != null) {
				ConnOp.setUserName(authToken);
			}
			logger.info("Connecting to broker: " + brokerAddr);
			// Check to use secure connection
			if (isSecureConn)
				initSecureConnection(ConnOp);
			
			mqttClient.setCallback(this);
			mqttClient.connect(ConnOp);
			
			logger.info("Connected to broker: " + brokerAddr);
		}catch(MqttException ex)
		{
			logger.log(Level.SEVERE, "Failed to connect to broker " + brokerAddr, ex);
		}
		
		}
	}
	
	/**
	 * To initiate the secure connection for mqtt connection
	 * @param connOpts: The mqtt connection option which need to be set to a secure ssl context
	 */
	private void initSecureConnection(MqttConnectOptions connOpts)
	{
		try {
			logger.info("Configuring TLS...");
			
			SSLContext sslContext = SSLContext.getInstance("SSL");
			KeyStore keyStore = readCertificate();
			
			TrustManagerFactory trustManagerFactory =
					TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManagerFactory.init(keyStore);
			sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
			
			connOpts.setSocketFactory(sslContext.getSocketFactory());
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Failed to initialize secure MQTT connection.", e);
				}
		}
	
	/**
	 * To read the certificate from pemFile
	 * @return 
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	private KeyStore readCertificate()
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException
	{
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		FileInputStream fis = new FileInputStream(pemFileName);
		BufferedInputStream bis = new BufferedInputStream(fis);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		
		ks.load(null);
		
		while (bis.available() > 0) {
			Certificate cert = cf.generateCertificate(bis);
			ks.setCertificateEntry("adk_store" + bis.available(), cert);
			}
		return ks;
		}
	
	/*
	 * To disconnect
	 */
	public void disconnect()
	{
		try {
			mqttClient.disconnect();
			logger.info("Disconnected from broker: " + brokerAddr);
		}catch(Exception ex)
		{
			logger.log(Level.SEVERE, "Failed to disconnect from broker: " + brokerAddr , ex);
		}
	}
	
	/*
	 * To publish a message
	 * @param topic: Topic for the message
	 * @param qosLevel: 0: at most once, 1: at least once, 2: exactly once
	 * @param payload
	 * @return true if publish successful, false if publish failed
	 */
	public boolean publishMessage(String topic, int qosLevel, byte[] payload) {
		boolean msgSent = false;
		try
		{
			logger.info("Publishing message to " + topic);
			MqttMessage msg = new MqttMessage(payload);
			msg.setQos(qosLevel);
			mqttClient.publish(topic, msg);
			logger.info("Message Published " + msg.getId());
			msgSent = true;
		}catch(Exception ex)
		{
			logger.log(Level.SEVERE, "Failed to publish Mqtt message " + ex.getMessage());
		}
		return msgSent;
	}
	
	/**
	 * To subscribe to all topics
	 * @return true if subscribe to all topics successfully, false if failed
	 */
	public boolean subscribeToAll() {
		try {
			mqttClient.subscribe("$SYS/#");
			logger.log(Level.INFO, "Subscribed to all topics successfully.");
			return true;
		} catch (MqttException e) {
			logger.log(Level.WARNING, "Failed to subscribe to all topics.", e);
		}
		return false;
	}

	/*
	 * To subscribe to topics
	 * @return true if subscribe the topic successfully, false if failed
	 */
	public boolean subscribeToTopic(String topic)
	{
		boolean success = false;
		try {
			logger.info("Subscribing to Topic: " + topic);
			mqttClient.subscribe(topic);
			success = true;
		} catch (MqttException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	/**
	 * To unsubscribe from a topic
	 * @param topic: Topic to unsubscribe from
	 * @return true if unsubscribed successfully, false if failed
	 */
	public boolean unSubscribeFromTopic(String topic) {
		try {	
			mqttClient.unsubscribe(topic);
			logger.log(Level.INFO, "Unsubscribed from topic " + topic + " successfully.");
			return true;
		} catch (MqttException e) {
			logger.log(Level.WARNING, "Failed to unsubscribe from topic " + topic, e);
			e.printStackTrace();
		}
		return false;
	}
	
	/*
	 * (non-Javadoc) To check for loss of connection
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.Throwable)
	 */
	public void connectionLost(Throwable cause) {
		
		logger.log(Level.WARNING, "Connection to broker lost. Retrying...", cause);
	}

	/*
	 * (non-Javadoc) To check if message has arrived
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#messageArrived(java.lang.String, org.eclipse.paho.client.mqttv3.MqttMessage)
	 */
	
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		
		MqttClientConnector.setMsg(message);
		logger.info("Message arrived: " + topic + "," + message.getId());
		System.out.print("Message recieved: " + topic + "," + message.getId() + ": "+ message + "\n");
	
	}
	
	/*
	 * (non-Javadoc) To check for completetion of delivery
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(org.eclipse.paho.client.mqttv3.IMqttDeliveryToken)
	 */
	
	public void deliveryComplete(IMqttDeliveryToken token) {
		
		logger.info("Message delivered: " + token.getMessageId() + "-" + token.getResponse());
		
	}
	
	/*
	 * To parse message as string
	 */
	public static String getMsg() {
		return mssg;
	}
	
	/*
	 * To parse the message from MQTT Broker 
	 */
	public static void setMsg(MqttMessage message) {
		MqttClientConnector.mssg = message.toString();
	}
}