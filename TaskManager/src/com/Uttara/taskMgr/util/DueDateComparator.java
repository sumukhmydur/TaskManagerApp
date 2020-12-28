package com.Uttara.taskMgr.util;

import java.util.Comparator;

public class DueDateComparator implements Comparator<TaskBean>{
	public int compare(TaskBean t1, TaskBean t2)
	{
		return t1.getPlDt().compareTo(t2.getPlDt());
	}
}
