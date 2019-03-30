package schooldomain.studentname.connecteddevices.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Sensor data class
 * @author: Adhira
 */

public class SensorData {
	/** Defining all variables */
	private Double curVal = ((double) 0);
	private Double maxVal;
	private Double minVal;
	private Double totVal = ((double) 0);
	private Double diffVal;
	private Double avgVal = ((double) 0);
	private String timeStamp;
	private Integer sampleCount = 0;
	private String name;
	
	/** Constructor */
	public SensorData(Double minVal, Double maxVal, String timeStamp, String name) {
		super();
		this.minVal = minVal;
		this.maxVal = maxVal;
		this.timeStamp = timeStamp;
		this.updateTime();
		this.name = name;
	}
	
	/** Obtain average value */
	public Double getAvgValue() {
		return avgVal;
	}

	/** Set average value */
	public void setAvgValue(Double avgVal) {
		this.avgVal = avgVal;
	}
	
	/** Get name */
	public String getName() {
		return name;
	}

	/** Set name */
	public void setName(String name) {
		this.name = name;
	}

	/** Get current value */
	public Double getCurValue() {
		return curVal;
	}
	
	/** Set current value */
	public void setCurValue(Double curVal) {
		this.curVal = curVal;
	}
	
	/** Get max value */
	public Double getMaxValue() {
		return maxVal;
	}
	
	/** Set max value */
	public void setMaxValue(Double maxVal) {
		this.maxVal = maxVal;
	}
	
	/** Get minimum value */
	public Double getMinValue() {
		return minVal;
	}
	
	/** Set minimum value */
	public void setMinValue(Double minVal) {
		this.minVal = minVal;
	}
	
	/** Get total value */
	public Double getTotValue() {
		return totVal;
	}
	
	/** Set total value */
	public void setTotValue(Double totVal) {
		this.totVal = totVal;
	}
	
	/** Get sample count */
	public int getsampleCount() {
		return sampleCount;
	}
	
	/** Set total sample count */
	public void setsampleCount(int sampleCount) {
		this.sampleCount = sampleCount;
	}
	
	/** Get difference in temperature value */
	public Double getDiffValue() {
		return diffVal;
	}
	
	/** Set difference in temperature value */
	public void setDiffValue(Double diffVal) {
		this.diffVal = diffVal;
	}

	/** Get time stamp */
	public String getTime() {
		return timeStamp;
	}

	/** Set and update time */
	public void updateTime() {
		timeStamp = new SimpleDateFormat("yyyy.MM.dd HH:mm.ss").format(new Date());
	}
	
	/** Update values */
	public void updateValue(float val) {
		updateTime();
		++sampleCount;
		curVal = (double) val;
		totVal += (double) val;
		if (curVal < minVal) {
			minVal = curVal;
		}
		if (curVal > maxVal) {
			maxVal = curVal;
		}
		if (totVal != 0 && sampleCount > 0) {
			avgVal = totVal / ((double) sampleCount);
		}
	}
	
	/** Print sensor data values obtained from JSon file */
	public String fromJson() {
		return "Name: " + name + "\n"
				+ "Time: " + timeStamp + "\n"
				+ "Current: " + curVal + "\n"
				+ "Average: " + avgVal + "\n"
				+ "Samples: " + sampleCount + "\n"
				+ "Min: " + minVal + "\n" 
				+ "Max: " + maxVal + "\n";
	}
	
}