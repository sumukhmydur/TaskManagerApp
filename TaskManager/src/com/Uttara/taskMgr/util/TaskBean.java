package com.Uttara.taskMgr.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskBean implements Comparable<TaskBean>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String taskName;
	private String desc;
	private Date cDt;
	private Date plDt;
	private String tags;
	private int priority;
	private String status;
	
	public TaskBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public TaskBean(String taskName, String desc, Date plDt, String tags, int priority, Date cDt, String status) {
		super();
		this.taskName = taskName;
		this.desc = desc;
		this.cDt = cDt;
		this.plDt = plDt;
		this.tags = tags;
		this.priority = priority;
		this.status = status;
	}


	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		if ( taskName == null || taskName.trim().equals(""))
			throw new IllegalArgumentException("Task Name is Null");
		else
			this.taskName = taskName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getcDt() {
		return cDt;
	}
	public void setCDt(Date cDt) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		String scDt = sdf.format(cDt);

		if ( scDt == null || scDt.trim().equals(""))
			throw new IllegalArgumentException("Date Format is incorrect");
		else
			this.cDt = cDt;
	}
	public Date getPlDt() {
		return plDt;
	}
	public void setPlDt(Date plDt) {
		
		SimpleDateFormat sdf = new SimpleDateFormat();
		String splDt = sdf.format(plDt);

		if ( splDt == null || splDt.trim().equals(""))
			throw new IllegalArgumentException("Date Format is incorrect");
		else
			this.plDt = plDt;
	}
	public String getTags() {
		
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}

	@Override
	public int hashCode() {
		return (taskName+desc+cDt+plDt+tags+priority+status).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if ( obj instanceof TaskBean)
		{
			TaskBean t = (TaskBean)obj;
			
			if ( this.taskName.equals(t.taskName) && this.desc.equals(t.desc) && this.cDt.equals(t.cDt) && this.plDt.equals(t.plDt) && this.tags.equals(t.tags) && this.priority == t.priority && this.status.equals(status) )
				return true;
			else
				return false;
		}
		else
			return false;
	}

	@Override
	public String toString() {
		return "TaskBean [taskName=" + taskName + ", desc=" + desc + ", cDt=" + cDt + ", plDt=" + plDt + ", tags=" + tags + ", priority="
				+ priority + ", Status=" + status + "]";
	}
	
	
	public int compareTo(TaskBean t)
	{
		return this.taskName.compareTo(t.taskName); 
	}
	
}
