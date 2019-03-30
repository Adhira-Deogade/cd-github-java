
package schooldomain.studentname.connecteddevices.common;


/**  Actuator data class
 * @author: Adhira
 */

public class ActuatorData {

	/** Command, Status and Error case type definition */
	public static final int COMMAND_OFF = 0;
	public static final int COMMAND_ON = 1;
	public static final int COMMAND_SET = 2;
	public static final int COMMAND_RESET = 3;
	public static final int STATUS_IDLE = 0;
	public static final int STATUS_ACTIVE = 1;
	public static final int ERROR_OK = 0;
	public static final int ERROR_COMMAND_FAILED = 1;
	public static final int ERROR_NON_RESPONSIBLE = -1;

	private String name = "Actuator Data";
	private String timeStamp = null;
	private boolean hasError = false;
	private int command = 0;
	private int errCode = 0;
	private int statusCode = 0;
	private String stateData = null;
	private float val = 0.0f;

	/** Constructor */
	public ActuatorData(String timeStamp, String name) {
		this.setTime(timeStamp);
		this.name = name;
	}
	
	/** Get name */
	public String getName() {
		return name;
	}

	/** Set name */
	public void setName(String name) {
		this.name = name;
	}

	/** Get date and time */
	public String getTime() {
		return timeStamp;
	}

	/** Set the date and time */
	public void setTime(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	/** Check error */
	public boolean isHasError() {
		return hasError;
	}

	/** Validate error */
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	/** Get type of command */
	public int getCommand() {
		return command;
	}

	/** Set type of command */
	public void setCommand(int command) {
		this.command = command;
	}

	/** Get type of error code */
	public int getErrCode() {
		return errCode;
	}

	/** Set type of error code */
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	/** Get type of status */
	public int getStatusCode() {
		return statusCode;
	}

	/** Set type of status */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/** Get actuator state */
	public String getStateData() {
		return stateData;
	}

	/** Set actuator state */
	public void setStateData(String stateData) {
		this.stateData = stateData;
	}

	/** Get Actuator data value */
	public float getVal() {
		return val;
	}

	/** Set Actuator data values */
	public void setVal(float val) {
		this.val = val;
	}

	/** Update ActuatorData values */
	public void updateData(ActuatorData data) {
		this.command = data.getCommand();
		this.statusCode = data.getStatusCode();
		this.errCode = data.getErrCode();
		this.stateData = data.getStateData();
		this.val = data.getVal();
	}

	/** Print out the ActuatorData */
	public String fromJson() {
		return "Name: " + name + "\n"
				+ "time: " + timeStamp + "\n"
				+ "Command: " + command + "\n"
				+ "Status Code: " + statusCode + "\n"
				+ "Error Code: " + errCode + "\n"
				+ "State Data: " + stateData + "\n"
				+ "Value: " + val + "\n";
	}

}