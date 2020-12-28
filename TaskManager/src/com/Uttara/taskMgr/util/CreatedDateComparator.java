package com.Uttara.taskMgr.util;

import java.util.Comparator;

public class CreatedDateComparator implements Comparator<TaskBean>{
	public int compare(TaskBean t1, TaskBean t2)
	{
		return t1.getcDt().compareTo(t2.getcDt());
	}
}
