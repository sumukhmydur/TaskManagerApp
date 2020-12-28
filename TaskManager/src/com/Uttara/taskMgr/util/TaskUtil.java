package com.Uttara.taskMgr.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Paragraph;

public class TaskUtil {
	
	public static boolean validateName(String str)
	{
		if ( str == null || str.trim().equals("") )
			return false;
		
		if ( ! Character.isLetter(str.charAt(0) ) )
			return false;
		
		if ( str.split(" ").length > 1)
			return false;
		
		for (int i = 1; i < str.length() ; i++) {
			char c = str.charAt(i);
			
			if ( ! (Character.isDigit(c) || Character.isLetter(c)) )
				return false;
		}
		return true;
	}
	
	public static boolean validateDate(String date)
	{
		if ( date == null || date.trim().equals(""))
			return false;
		
		
		String[] str = date.split("/");
		
		int month = Integer.parseInt(str[1]);
		if ( month < 1 || month > 12 )
			return false;
		
		int d = Integer.parseInt(str[0]);
		int year = Integer.parseInt(str[2]);
		if ( (month == 2 && year%4 == 0 && (d < 0 || d > 29)) || (month == 2 && year%4 != 0 && (d < 0 || d > 28)) )
			return false;
		else if ( month%2 == 0 && (d < 0 || d > 31) )
			return false;
		else if ( month%2 != 0 && (d < 0 || d > 30) )
			return false;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date d1 = null;
		try {
			d1 = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date d2 = new Date();
		if ( d1.compareTo(d2) < 0 ) 
			return false;
		
				
		return true;
	}
	
	public static boolean validatePriority(int priority)
	{
		if ( priority < 1 || priority > 10 )
			return false;
		return true;
	}
	
	public static boolean validateStatus(String status)
	{
		if ( status == null || status.trim().equals("") )
			return false;
		
		String newStatus = status.toLowerCase();
		if ( ! (newStatus.equals(Constants.STATUS_PENDING) || newStatus.equals(Constants.STATUS_COMPLETED) || newStatus.equals(Constants.STATUS_CANCELLED)) )
			return false;
		
		return true;
	}
	
	public static void addEmpltyLine(Paragraph paragraph, int number)
	{
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}
