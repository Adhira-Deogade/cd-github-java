package schooldomain.studentname.connecteddevices.common;

/** File transfer between json file and SensorData class
 * @author: Adhira
 */



import java.io.*;

/*
 * Class to perform data trasfer between json and string
 */
public class FileTransfer {
	
	/*
	 * To read file
	 */
	public static String fileRead(String file)
	{
		String json = new String();
		try
		{
			FileReader fileread = new FileReader(file);
			int fp;
			while((fp=fileread.read())!=-1)
			{
				json = json + (char)fp;
			}
			fileread.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return json;
	}
	
	/*
	 * To write file
	 */
	public static void fileWrite(String fileWriteEnable, String file, String json)
	{
		File jsonFile = new File(file);
		
		try {
			jsonFile.createNewFile();
			FileWriter writer = new FileWriter(jsonFile);
			writer.write(json);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
}