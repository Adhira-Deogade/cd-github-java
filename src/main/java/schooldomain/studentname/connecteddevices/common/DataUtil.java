
package schooldomain.studentname.connecteddevices.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import schooldomain.studentname.connecteddevices.common.FileTransfer;


/**
 * Data Util class
 * @author: Adhira
 */


/*
 * Class that helps in parsing data from Json to object and vice-versa
 */
public class DataUtil {
	
	/*
	 * To get data from object to Json
	 */
	public String toJson(SensorData sensordata)
	{
		String jsonsD;
		Gson gson = new Gson();
		jsonsD = gson.toJson(sensordata);
		return jsonsD;
	}
	
	/*
	 * To get object from Json to object
	 */
	public SensorData fomJson(String jsondata,String filename)
	{
		SensorData sensorData=null;
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		if(filename==null)
		{	
			sensorData = gson.fromJson(jsondata, SensorData.class);
			return sensorData;
		}
		else
		{
			String data = FileTransfer.fileRead(filename);
			sensorData = gson.fromJson(data, SensorData.class);
			return sensorData;	
		}
	}

}