package com.Uttara.taskMgr.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class TaskModel {
	public List<TaskBean> listTasks(String catName)
	{
		BufferedReader br = null;
		TaskBean task;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		List<TaskBean> tasks = new ArrayList<TaskBean>();
		
		try {
			br = new BufferedReader( new FileReader ( catName + ".todo") );
			String line;
			while ( (line = br.readLine()) != null)
			{
				String[] str = line.split(":");
				task = new TaskBean(str[0], str[1], sdf.parse(str[2]), str[3], Integer.parseInt(str[4]), sdf.parse(str[5]), str[6] );
				tasks.add(task);
			}
			return tasks;
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		finally {
			if ( br != null )
			{
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public List<TaskBean> listTasks()
	{
		TaskBean task;
		List<TaskBean> tasks = new ArrayList<TaskBean>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		File f = new File (Constants.CATEGORIES_PATH);
		
		File[] fl = f.listFiles();
		for (File file : fl) {
			if ( file.isFile() && file.getName().contains(".todo") )
			{
				BufferedReader br = null;
				
				try {
					br = new BufferedReader( new FileReader(file) );
					
					String line;
					if ( (line = br.readLine()) != null )
					{
						String[] str = line.split(":");
						task = new TaskBean( str[0], str[1], sdf.parse(str[2]), str[3], Integer.parseInt(str[4]), sdf.parse(str[5]), str[6]);
						tasks.add(task);
					}
					
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally
				{
					if ( br != null )
					{
						try {
							br.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		return tasks;
	}
	
	public TaskBean getTask(String taskName, String catName)
	{
		List<TaskBean> task = listTasks(catName);
		
		for (TaskBean taskBean : task) {
			if ( taskBean.getTaskName().equals(taskName) )
				return taskBean;
		}
		return null;
	}
	
	public String addTask(TaskBean task, String catName)
	{
		BufferedWriter bw = null;
		
		try {
			bw = new BufferedWriter ( new FileWriter( catName + ".todo", true) );
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String splDt = sdf.format(task.getPlDt());
			String scDt = sdf.format(task.getcDt());
			bw.write( task.getTaskName() + ":" + task.getDesc() + ":" + splDt + ":" + task.getTags() + ":" + task.getPriority() + ":" + scDt + ":" + task.getStatus() );
			bw.newLine();
			
			return Constants.SUCCESS;
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return "OOPS!! Could not add Task "+ e.getMessage();
		}
		finally {
			if ( bw != null)
			{
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public String update( TaskBean oldTask, TaskBean newTask, String catName)
	{
		List<TaskBean> tasks = new ArrayList<TaskBean>();
		TaskBean task;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		try {
			br = new BufferedReader( new FileReader(catName + ".todo") );
			String line;
			
			while ( (line = br.readLine()) != null)
			{
				String[] str = line.split(":");
				if ( oldTask.getTaskName().equals(str[0]) ) {
					task = new TaskBean(str[0], newTask.getDesc(), newTask.getPlDt(), newTask.getTags(), newTask.getPriority(), sdf.parse(str[5]), newTask.getStatus() );
					tasks.add(task);
				}
				else
				{
					task = new TaskBean(str[0], str[1], sdf.parse(str[2]), str[3], Integer.parseInt(str[4]), sdf.parse(str[5]), str[6]);
					tasks.add(task);
				}
			}
			
			bw = new BufferedWriter ( new FileWriter( catName + ".todo") );
			
			for (TaskBean taskBean : tasks) {
				String splDt = sdf.format(taskBean.getPlDt());
				String scDt = sdf.format(taskBean.getcDt());
				bw.write( taskBean.getTaskName() + ":" + taskBean.getDesc() + ":" + splDt + ":" + taskBean.getTags() + ":" + taskBean.getPriority() + ":" + scDt + ":" + taskBean.getStatus() );
				bw.newLine();
			}
			
			return Constants.SUCCESS;
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return "OOPS!! Could not update Task "+ e.getMessage();

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "OOPS!! Could not update Task "+ e.getMessage();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "OOPS!! Could not update Task "+ e.getMessage();
		}
		finally
		{
			if ( br != null )
			{
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if ( bw != null )
			{
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public String delete(String catName)
	{
		new File(catName + ".todo").delete();
		return Constants.SUCCESS;
	}
	
	public String delete(TaskBean task, String catName)
	{
		TaskBean newTask;
		List<TaskBean> tasks = new ArrayList<TaskBean>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		try {
			br = new BufferedReader( new FileReader(catName + ".todo") );
			String line;
			
			while ( (line = br.readLine()) != null)
			{
				String[] str = line.split(":");
				
				if (  ! task.getTaskName().equals(str[0]) )
				{
					newTask = new TaskBean(str[0], str[1], sdf.parse(str[2]), str[3], Integer.parseInt(str[4]), sdf.parse(str[5]), str[6] );
					tasks.add(newTask);
				}
						
			}
			
			bw = new BufferedWriter ( new FileWriter( catName + ".todo") );
			
			for (TaskBean taskBean : tasks) {
				String splDt = sdf.format(taskBean.getPlDt());
				String scDt = sdf.format(taskBean.getcDt());
				bw.write( taskBean.getTaskName() + ":" + taskBean.getDesc() + ":" + splDt + ":" + taskBean.getTags() + ":" + taskBean.getPriority() + ":" + scDt + ":" + taskBean.getStatus() );
				bw.newLine();
			}
			
			return Constants.SUCCESS;
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return "OOPS!! Could not delete Task "+ e.getMessage();

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "OOPS!! Could not delete Task "+ e.getMessage();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "OOPS!! Could not delete Task "+ e.getMessage();
		}
		finally
		{
			if ( br != null )
			{
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if ( bw != null )
			{
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public List<TaskBean> search(String input, String catName)
	{
		List<TaskBean> tasks = new ArrayList<TaskBean>();
		TaskBean task;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		BufferedReader br = null;
		
		try {
			br = new BufferedReader( new FileReader( catName + ".todo") );
			
			String line;
			while ( (line = br.readLine()) != null )
			{
				if ( line.contains(input) )
				{
					String[] str = line.split(":");
					task = new TaskBean(str[0], str[1], sdf.parse(str[2]), str[3], Integer.parseInt(str[4]), sdf.parse(str[5]), str[6] );
					tasks.add(task);
				}
			}
			return tasks;
		} catch (IOException e) {
			// TODO: handle exception
				e.printStackTrace();
				return null;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		finally
		{
			if ( br != null)
			{
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public List<TaskBean> search(String input)
	{
		List<TaskBean> tasks = new ArrayList<TaskBean>();
		TaskBean task;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		File f  = new File(Constants.CATEGORIES_PATH);
		File[] fl = f.listFiles();
		
		for (File file : fl) {
			if ( file.isFile() && file.getName().contains(".todo") )
			{
				BufferedReader br = null;
				
				try {
					br = new BufferedReader ( new FileReader ( file ) );
					
					String line;
					while( (line = br.readLine()) != null )
					{
						if ( line.contains(input) )
						{
							String[] str = line.split(":");
							task = new TaskBean( str[0], str[1], sdf.parse(str[2]), str[3], Integer.parseInt(str[4]), sdf.parse(str[5]), str[6] );
							tasks.add(task);
						}
					}
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally
				{
					if ( br != null)
					{
						try {
							br.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		return tasks;
	}
	
	public String export(String path)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Font titleFont = new Font( Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
		Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, BaseColor.RED);
		Font taskFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL);
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(path+"\\todoList.pdf"));
			document.open();
			document.addTitle("todoList");
			
			Paragraph preface = new Paragraph("Your TODO List of Tasks", titleFont);
			preface.setAlignment(Element.ALIGN_CENTER);
			TaskUtil.addEmpltyLine(preface, 3);
			document.add(preface);
			
			File f = new File(Constants.CATEGORIES_PATH);
			File[] fl = f.listFiles();
			for (File file : fl) {
				if ( file.isFile() && file.getName().contains(".todo"))
				{
					String catName = file.getName().substring(0, file.getName().lastIndexOf('.'));
					Paragraph content = new Paragraph();
					content.add( new Paragraph(catName, catFont) );
					TaskUtil.addEmpltyLine(content, 1);
					List<TaskBean> tasks = listTasks(catName);
					
					for (TaskBean task : tasks) {
						content.add( new Paragraph("Task Name: " + task.getTaskName(), taskFont ) );
						content.add( new Paragraph("Description: " + task.getDesc(), taskFont) );
						content.add( new Paragraph("Planned End Date: " + sdf.format(task.getPlDt()), taskFont) );
						content.add( new Paragraph("Tags: "+ task.getTags(), taskFont) );
						content.add( new Paragraph("Created Date: " + sdf.format(task.getcDt()), taskFont) );
						content.add( new Paragraph("Status: " + task.getStatus(), taskFont) );
						TaskUtil.addEmpltyLine(content, 3);
					}
					document.add(content);
				}
			}
			document.close();
			return Constants.SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "OOPS!! Could not export your Tasks" + e.getMessage();
		}
	}
	
	public List<TaskBean> getTasksBasedOnDueDate()
	{
		TaskBean task;
		List<TaskBean> tasks = new ArrayList<TaskBean>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		File f = new File (Constants.CATEGORIES_PATH);
		
		File[] fl = f.listFiles();
		for (File file : fl) {
			if ( file.isFile() && file.getName().contains(".todo") )
			{
				BufferedReader br = null;
				
				try {
					br = new BufferedReader( new FileReader(file) );
					
					String line;
					if ( (line = br.readLine()) != null )
					{
						String[] str = line.split(":");
						task = new TaskBean( str[0], str[1], sdf.parse(str[2]), str[3], Integer.parseInt(str[4]), sdf.parse(str[5]), str[6]);
						tasks.add(task);
					}
					Collections.sort(tasks, new DueDateComparator() );
					
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally
				{
					if ( br != null )
					{
						try {
							br.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		return tasks;
	}
	
	public List<TaskBean> getTasksBasedOnDueDate(String catName)
	{
		List<TaskBean> tasks = listTasks(catName);
		Collections.sort(tasks, new DueDateComparator());
		
		return tasks;
	}
	
	public List<TaskBean> getTasksBasedOnCreatedDate(String catName)
	{
		List<TaskBean> tasks = listTasks(catName);
		Collections.sort(tasks, new CreatedDateComparator());
		
		return tasks;
	}
	
	public List<TaskBean> getTasksBasedOnCreatedDate()
	{
		TaskBean task;
		List<TaskBean> tasks = new ArrayList<TaskBean>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		File f = new File (Constants.CATEGORIES_PATH);
		
		File[] fl = f.listFiles();
		for (File file : fl) {
			if ( file.isFile() && file.getName().contains(".todo") )
			{
				BufferedReader br = null;
				
				try {
					br = new BufferedReader( new FileReader(file) );
					
					String line;
					if ( (line = br.readLine()) != null )
					{
						String[] str = line.split(":");
						task = new TaskBean( str[0], str[1], sdf.parse(str[2]), str[3], Integer.parseInt(str[4]), sdf.parse(str[5]), str[6]);
						tasks.add(task);
					}
					Collections.sort(tasks, new CreatedDateComparator() );
					
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally
				{
					if ( br != null )
					{
						try {
							br.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		return tasks;
	}
	
	public List<TaskBean> getTasksBasedOnTimePending(String catName)
	{
		List<TaskBean> tasks = listTasks(catName);
		Collections.sort(tasks, new TimePendingComparator());
		
		return tasks;
	}
	
	public List<TaskBean> getTasksBasedOnTimePending()
	{
		TaskBean task;
		List<TaskBean> tasks = new ArrayList<TaskBean>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		File f = new File (Constants.CATEGORIES_PATH);
		
		File[] fl = f.listFiles();
		for (File file : fl) {
			if ( file.isFile() && file.getName().contains(".todo") )
			{
				BufferedReader br = null;
				
				try {
					br = new BufferedReader( new FileReader(file) );
					
					String line;
					if ( (line = br.readLine()) != null )
					{
						String[] str = line.split(":");
						task = new TaskBean( str[0], str[1], sdf.parse(str[2]), str[3], Integer.parseInt(str[4]), sdf.parse(str[5]), str[6]);
						tasks.add(task);
					}
					Collections.sort(tasks, new TimePendingComparator() );
					
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally
				{
					if ( br != null )
					{
						try {
							br.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		return tasks;
	}
	
	public boolean checkIfCategoryExists(String catName)
	{
		return new File(catName+".todo").exists();
	}
	
	public boolean checkIfTaskExists(String taskName, String catName)
	{
		if ( ! checkIfCategoryExists(catName))
			return true;
		
		List<TaskBean> tasks = listTasks(catName);
		
		for (TaskBean task : tasks) {
			if ( task.getTaskName().equals(taskName))
				return false;
		}
		return true;
	}
	
	public int getTotalNoOfOccurances(String input, List<TaskBean> tasks)
	{
		int totalCount = 0;
		
		for (TaskBean taskBean : tasks) {
			if ( taskBean.getDesc().contains(input))
				totalCount++;
			
			if ( taskBean.getTaskName().contains(input) )
				totalCount++;
				
			if ( taskBean.getTags().contains(input))
				totalCount++;
		}
		return totalCount;
	}
	
	public int getNoOfOccurancesInName(String input, List<TaskBean> tasks )
	{
		int nameCount = 0;
		
		for (TaskBean taskBean : tasks) {
			
			if ( taskBean.getTaskName().contains(input) )
				nameCount++;
		}
		return nameCount;
	}
	
	public int getNoOfOccurancesInDescription(String input, List<TaskBean> tasks)
	{
		int desCount = 0;
		
		for (TaskBean taskBean : tasks) {
			if ( taskBean.getDesc().contains(input))
				desCount++;
		}
		return desCount;
	}
	
	public int getNoOfOccurancesInTags(String input, List<TaskBean> tasks)
	{
		int tagsCount = 0;
		
		for (TaskBean taskBean : tasks) {
			
			if ( taskBean.getTags().contains(input))
				tagsCount++;
		}
		return tagsCount;
	}
}
