package com.Uttara.taskMgr.util;

import java.util.Comparator;

public class TimePendingComparator implements Comparator<TaskBean>{
	public int compare(TaskBean t1, TaskBean t2)
	{
		int timePending1 = t1.getPlDt().compareTo(t1.getcDt());
		int timePending2 = t2.getPlDt().compareTo(t2.getcDt());
		
		return timePending2 - timePending1;
	}
}
