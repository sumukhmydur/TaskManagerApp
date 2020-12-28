package com.Uttara.taskMgr;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.Uttara.taskMgr.util.Constants;
import com.Uttara.taskMgr.util.Logger;
import com.Uttara.taskMgr.util.TaskBean;
import com.Uttara.taskMgr.util.TaskModel;
import com.Uttara.taskMgr.util.TaskUtil;

public class StartApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc1 = null;
		Scanner sc2 = null;
		try {
			sc1 = new Scanner(System.in);
			sc2 = new Scanner(System.in);
			
			int ch1 =0, ch2, ch3, ch4, ch5 ;
			String catName, taskName, desc, tags, splDt, scDt, status, result;
			int priority;
			List<TaskBean> tasks;
			TaskBean task;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date plDt, cDt;
			
			Logger.getInstance().log("Starting Task Manager...");		
			TaskModel model = new TaskModel();
			
			while ( ch1 != 7)
			{
				System.out.println();
				System.out.println("1) Create Catagory");
				System.out.println("2) Load a Catagory");
				System.out.println("3) Delete a Catagory");
				System.out.println("4) List");
				System.out.println("5) Search");
				System.out.println("6) Export");
				System.out.println("7) Exit");
				
				System.out.println();
				System.out.println("Enter your choice");
				ch1 = sc1.nextInt();
				
				switch(ch1)
				{
					case 1: 
							System.out.println();
							
							Logger.getInstance().log("Creating Category...");
							System.out.println("Enter Catagory Name");
							catName = sc2.nextLine();
							
							while ( ! TaskUtil.validateName(catName) )
							{
								System.out.println(" Catagory Name must be in 1 Word, should start with Letter or Alphanumberic, should not include any Special Character, Space");
								catName = sc2.nextLine();
							}
							
							if ( model.checkIfCategoryExists(catName))
								System.out.println("Category Name already exists");
							else
							{
								ch2 = 0;
								while ( ch2 != 6 )
								{
									System.out.println();
									System.out.println("1) Create Task");
									System.out.println("2) Update Task");
									System.out.println("3) Delete Task");
									System.out.println("4) List");
									System.out.println("5) Search");
									System.out.println("6) Go Back");
									
									System.out.println();
									System.out.println("Enter your choice");
									ch2 = sc1.nextInt();
									
									switch(ch2)
									{
										case 1:
												System.out.println();
												Logger.getInstance().log("Creating Task...");
												
												System.out.println("Enter Task Name");
												taskName = sc2.nextLine();
												while ( ! model.checkIfTaskExists(taskName, catName) )
												{
													System.out.println("Task Name already exists, Please enter a new Task");
													taskName = sc2.nextLine();
												}
												System.out.println("Enter Task Description");
												desc = sc2.nextLine();
												
												System.out.println("Enter Planned Date as (dd/mm/yyyy)");
												splDt = sc2.nextLine();
												while ( ! TaskUtil.validateDate(splDt)) {
													System.out.println("Date must be entered in dd/mm/yyyy, Please enter again");
													splDt = sc2.nextLine();
												}
												
												System.out.println("Enter tags (as comma separated words)");
												tags = sc2.nextLine();
												
												System.out.println("Enter priority ( 1 - Lowest and 10 - Highest");
												priority = sc1.nextInt();
												while ( ! TaskUtil.validatePriority(priority) )
												{
													System.out.println("Priority must be within the range of 1 to 10, Please Enter again");
													priority = sc1.nextInt();
												}
												
												plDt = sdf.parse(splDt);
												cDt = new Date();
												task = new TaskBean(taskName, desc, plDt, tags, priority, cDt, Constants.STATUS_PENDING);
												result = model.addTask(task, catName);
												
												if ( result.equals(Constants.SUCCESS) )
													System.out.println(" Task Added Succussfully :-)");
												else {
													System.out.println(" Task Addition Failed :-( ");
													System.out.println("Msg: " + result);
												}
												break;
												
										case 2:
												Logger.getInstance().log("Updating the Tasks...");
												System.out.println();
												
												System.out.println("Existing Tasks :");
												tasks = model.listTasks(catName);
												for (TaskBean taskBean : tasks) {
													System.out.println(taskBean.getTaskName());
												}
												System.out.println("Enter the Task Name for which you want to update");
												taskName = sc2.nextLine();
												while ( model.checkIfTaskExists(taskName, catName) )
												{
													System.out.println("Entered Task Name does not exist, Please enter a valid one from the above options");
													taskName = sc2.nextLine();
												}
												
												System.out.println();
												task = model.getTask(taskName, catName);
												splDt= sdf.format(task.getPlDt());
												scDt = sdf.format(task.getcDt());
												System.out.println("Name: " + task.getTaskName() + " Description: " + task.getDesc() + " Tags: " + task.getTags() + " Planned End Date: " + splDt + " Priority: " + task.getPriority() + " Created Date: " + scDt + " Status: " + task.getStatus() );
												System.out.println("1) Change Desctiption");
												System.out.println("2) Change Tags");
												System.out.println("3) Change Planned End Date");
												System.out.println("4) Change Priority");
												System.out.println("5) Change Status");
												
												
												System.out.println();
												System.out.println("Enter your choice");
												ch3 = sc1.nextInt();
												
												TaskBean newTask;
												switch(ch3)
												{
													case 1: 
															System.out.println();
															System.out.println("Enter the new Description");
															desc = sc2.nextLine();
															newTask = new TaskBean(task.getTaskName(), desc, task.getPlDt(), task.getTags(), task.getPriority(), task.getcDt(), task.getStatus());
															result = model.update(task, newTask, catName);
															
															if ( result.equals(Constants.SUCCESS))
																System.out.println(" Task Updated Succussfully :-)");
															else {
																System.out.println(" OOPS!! Something bad happened");
																System.out.println("Msg: " + result);
															}
															break;
															
													case 2:
															System.out.println();
															System.out.println("Enter the new Tags");
															tags = sc2.nextLine();
															newTask = new TaskBean(task.getTaskName(), task.getDesc(), task.getPlDt(), tags, task.getPriority(), task.getcDt(), task.getStatus());
															result = model.update(task, newTask, catName);
														
															if ( result.equals(Constants.SUCCESS))
																System.out.println(" Task Updated Succussfully :-)");
															else {
																System.out.println(" OOPS!! Something bad happened");
																System.out.println("Msg: " + result);
															}
															break;
															
													case 3:
															System.out.println();
															System.out.println("Enter the new Planned End Date");
															splDt = sc2.nextLine();
															while ( ! TaskUtil.validateDate(splDt)) {
																System.out.println("Date must be entered in dd/mm/yyyy, Please enter again");
																splDt = sc2.nextLine();
															}
															
															newTask = new TaskBean(task.getTaskName(), task.getDesc(), sdf.parse(splDt), task.getTags(), task.getPriority(), task.getcDt(), task.getStatus());
															result = model.update(task, newTask, catName);
													
															if ( result.equals(Constants.SUCCESS))
																System.out.println(" Task Updated Succussfully :-)");
															else {
																System.out.println(" OOPS!! Something bad happened");
																System.out.println("Msg: " + result);
															}
															break;
															
													case 4:
															System.out.println();
															System.out.println("Enter the new Priority");
															priority = sc2.nextInt();
															while ( ! TaskUtil.validatePriority(priority) )
															{
																System.out.println("Priority must be within the range of 1 to 10, Please Enter again");
																priority = sc1.nextInt();
															}
															
															newTask = new TaskBean(task.getTaskName(), task.getDesc(), task.getPlDt(), task.getTags(), priority, task.getcDt(), task.getStatus());
															result = model.update(task, newTask, catName);
													
															if ( result.equals(Constants.SUCCESS))
																System.out.println(" Task Updated Succussfully :-)");
															else {
																System.out.println(" OOPS!! Something bad happened");
																System.out.println("Msg: " + result);
															}
															break;
															
													case 5:
															System.out.println();
															System.out.println("Enter the new Status either as pending, completed or cancelled");
															status = sc2.next();
															while(  ! TaskUtil.validateStatus(status) )
															{
																System.out.println("Status must be as pending, completed or cancelled, Please Enter again");
																status = sc2.next();
															}
															
															newTask = new TaskBean(task.getTaskName(), task.getDesc(), task.getPlDt(), task.getTags(), task.getPriority(), task.getcDt(), status.trim().toLowerCase() );
															result = model.update(task, newTask, catName);
															
															if ( result.equals(Constants.SUCCESS) )
																System.out.println("Task Updated Succusfully");
															else
															{
																System.out.println(" OOPS!! Something bad happened");
																System.out.println("Msg: " + result);
															}
															break;
													
													default:
															System.out.println();
															System.out.println("Option not supported...");
												}
												break;
												
										case 3:
											Logger.getInstance().log("Deleting Tasks...");
											System.out.println();
											
											System.out.println("Existing Tasks :");
											tasks = model.listTasks(catName);
											for (TaskBean taskBean : tasks) {
												System.out.println(taskBean.getTaskName());
											}
											System.out.println("Enter the Task Name to delete");
											taskName = sc2.nextLine();
											while ( model.checkIfTaskExists(taskName, catName) )
											{
												System.out.println("Entered Task Name does not exist, Please enter a valid one from the above options");
												taskName = sc2.nextLine();
											}
											
											task = model.getTask(taskName, catName);
											result = model.delete(task, catName);
											
											if ( result.equals(Constants.SUCCESS))
												System.out.println(" Task Deleted Succussfully :-)");
											else
											{
												System.out.println(" OOPS!! Task could not be deleted");
												System.out.println("Msg: " + result);
											}
											break;
											
												
												
										case 4:
												Logger.getInstance().log("Listing the Tasks...");
												System.out.println();
												
												System.out.println("Press 1 to List Tasks by Alphabetical Listing by Name");
												System.out.println("Press 2 to List Tasks by Due Date");
												System.out.println("Press 3 to List Tasks by Created date");
												System.out.println("Press 4 to List Tasks by the Longest Time Pending");
												
												System.out.println("Enter your choice");
												ch4 = sc1.nextInt();
												
												switch(ch4)
												{
													case 1:
															System.out.println();
															
															tasks = model.listTasks(catName);
															
															for (TaskBean taskBean : tasks) {
																splDt = sdf.format(taskBean.getPlDt());
																scDt = sdf.format(taskBean.getcDt());
																System.out.println("Name: " + taskBean.getTaskName() + " Description: " + taskBean.getDesc() + " Tags: " + taskBean.getTags() + " Planned End Date: " + splDt + " Priority: " + taskBean.getPriority() + " Created Date: " + scDt + " Status:" + taskBean.getStatus());
															}
															break;
															
													case 2:
															System.out.println();
															
															for (TaskBean taskBean : model.getTasksBasedOnDueDate(catName)) {
																splDt = sdf.format(taskBean.getPlDt());
																scDt = sdf.format(taskBean.getcDt());
																System.out.println("Name: " + taskBean.getTaskName() + " Description: " + taskBean.getDesc() + " Tags: " + taskBean.getTags() + " Planned End Date: " + splDt + " Priority: " + taskBean.getPriority() + " Created Date: " + scDt + " Status:" + taskBean.getStatus());
															}
															break;
															
													case 3:
															System.out.println();
															
															for (TaskBean taskBean : model.getTasksBasedOnCreatedDate(catName)) {
																splDt = sdf.format(taskBean.getPlDt());
																scDt = sdf.format(taskBean.getcDt());
																System.out.println("Name: " + taskBean.getTaskName() + " Description: " + taskBean.getDesc() + " Tags: " + taskBean.getTags() + " Planned End Date: " + splDt + " Priority: " + taskBean.getPriority() + " Created Date: " + scDt + " Status:" + taskBean.getStatus());
															}
															break;
															
													case 4:
															System.out.println();
														
															for (TaskBean taskBean : model.getTasksBasedOnTimePending(catName)) {
																splDt = sdf.format(taskBean.getPlDt());
																scDt = sdf.format(taskBean.getcDt());
																System.out.println("Name: " + taskBean.getTaskName() + " Description: " + taskBean.getDesc() + " Tags: " + taskBean.getTags() + " Planned End Date: " + splDt + " Priority: " + taskBean.getPriority() + " Created Date: " + scDt + " Status:" + taskBean.getStatus());
															}
															break;
															
													default:
															System.out.println();
															System.out.println("Option not Supported");
												}
												
												break;	
												
										case 5:
												Logger.getInstance().log("Searching...");
												System.out.println();
										
												System.out.println("Enter a Word or Sentence to Search");
												String input = sc2.nextLine();
												
												tasks = model.search(input, catName);
												
												System.out.println("Total Number of Occurances: " + model.getTotalNoOfOccurances(input, tasks));
												
												System.out.println("No of Occurances Found in Description: " + model.getNoOfOccurancesInDescription(input, tasks));
												for ( TaskBean taskBean : tasks) {
													if ( taskBean.getDesc().contains(input) )
														System.out.println( taskBean.getTaskName() + " - " + taskBean.getDesc());
												}
												
												System.out.println("No of Occurances found in Name: " + model.getNoOfOccurancesInName(input, tasks));
												for (TaskBean taskBean : tasks) {
													if ( taskBean.getTaskName().contains(input) )
														System.out.println( "Task Name: " + taskBean.getTaskName());
												}
												
												System.out.println("No of Occurances found in Tags: " + model.getNoOfOccurancesInTags(input, tasks));
												for (TaskBean taskBean : tasks) {
													if ( taskBean.getTags().contains(input) )
														System.out.println(taskBean.getTaskName() + " - " + taskBean.getTags() );
												}
												break;
												
										
										case 6: 
												break;
												
										default:
												System.out.println();
												System.out.println("Option Not Supported yet...");
									}
								}
							}
			
							break;
							
					case 2:
							Logger.getInstance().log("Loading Catagory...");
							System.out.println();
							
							System.out.println("Enter the Catagory Name");
							catName = sc2.nextLine();
							
							while (! model.checkIfCategoryExists(catName) )
							{
								System.out.println("Hmm... Entered Catagory Name does not Exist, Please enter an existing one");
								catName = sc2.nextLine();
							}
							
							ch5 = 0;
							while ( ch5 != 6 )
							{
								System.out.println();
								System.out.println("1) Create Task");
								System.out.println("2) Update Task");
								System.out.println("3) Delete Task");
								System.out.println("4) List");
								System.out.println("5) Search");
								System.out.println("6) Go Back");
								
								System.out.println();
								System.out.println("Enter your choice");
								ch5 = sc1.nextInt();
								
								switch(ch5)
								{
									case 1:
											System.out.println();
											Logger.getInstance().log("Creating Task...");
											
											System.out.println("Enter Task Name");
											taskName = sc2.nextLine();
											while ( ! model.checkIfTaskExists(taskName, catName) )
											{
												System.out.println("Task Name already exists, Please enter a new Task");
												taskName = sc2.nextLine();
											}
											System.out.println("Enter Task Description");
											desc = sc2.nextLine();
											
											System.out.println("Enter Planned Date as (dd/mm/yyyy)");
											splDt = sc2.nextLine();
											while ( ! TaskUtil.validateDate(splDt)) {
												System.out.println("Date must be entered in dd/mm/yyyy, Please enter again");
												splDt = sc2.nextLine();
											}
											
											System.out.println("Enter tags (as comma separated words)");
											tags = sc2.nextLine();
											
											System.out.println("Enter priority ( 1 - Lowest and 10 - Highest");
											priority = sc1.nextInt();
											while ( ! TaskUtil.validatePriority(priority) )
											{
												System.out.println("Priority must be within the range of 1 to 10, Please Enter again");
												priority = sc1.nextInt();
											}
											
											plDt = sdf.parse(splDt);
											cDt = new Date();
											task = new TaskBean(taskName, desc, plDt, tags, priority, cDt, Constants.STATUS_PENDING );
											result = model.addTask(task, catName);
											
											if ( result.equals(Constants.SUCCESS) )
												System.out.println(" Task Added Succussfully :-)");
											else {
												System.out.println(" Task Addition Failed :-( ");
												System.out.println("Msg: " + result);
											}
											break;
											
									case 2:
											Logger.getInstance().log("Updating the Tasks...");
											System.out.println();
											
											System.out.println("Existing Tasks :");
											tasks = model.listTasks(catName);
											for (TaskBean taskBean : tasks) {
												System.out.println(taskBean.getTaskName());
											}
											System.out.println("Enter the Task Name for which you want to update");
											taskName = sc2.nextLine();
											while ( model.checkIfTaskExists(taskName, catName) )
											{
												System.out.println("Entered Task Name does not exist, Please enter a valid one from the above options");
												taskName = sc2.nextLine();
											}
											
											System.out.println();
											task = model.getTask(taskName, catName);
											splDt= sdf.format(task.getPlDt());
											scDt = sdf.format(task.getcDt());
											System.out.println("Name: " + task.getTaskName() + " Description: " + task.getDesc() + " Tags: " + task.getTags() + " Planned End Date: " + splDt + " Priority: " + task.getPriority() + " Created Date: " + scDt + " Status: " + task.getStatus() );
											System.out.println("1) Change Desctiption");
											System.out.println("2) Change Tags");
											System.out.println("3) Change Planned End Date");
											System.out.println("4) Change Priority");
											System.out.println("5) Change Status");
											
											
											System.out.println();
											System.out.println("Enter your choice");
											ch3 = sc1.nextInt();
											
											TaskBean newTask;
											switch(ch3)
											{
												case 1: 
														System.out.println();
														System.out.println("Enter the new Description");
														desc = sc2.nextLine();
														newTask = new TaskBean(task.getTaskName(), desc, task.getPlDt(), task.getTags(), task.getPriority(), task.getcDt(), task.getStatus() );
														result = model.update(task, newTask, catName);
														
														if ( result.equals(Constants.SUCCESS))
															System.out.println(" Task Updated Succussfully :-)");
														else {
															System.out.println(" OOPS!! Something bad happened");
															System.out.println("Msg: " + result);
														}
														break;
														
												case 2:
														System.out.println();
														System.out.println("Enter the new Tags");
														tags = sc2.nextLine();
														newTask = new TaskBean(task.getTaskName(), task.getDesc(), task.getPlDt(), tags, task.getPriority(), task.getcDt(), task.getStatus() );
														result = model.update(task, newTask, catName);
													
														if ( result.equals(Constants.SUCCESS))
															System.out.println(" Task Updated Succussfully :-)");
														else {
															System.out.println(" OOPS!! Something bad happened");
															System.out.println("Msg: " + result);
														}
														break;
														
												case 3:
														System.out.println();
														System.out.println("Enter the new Planned End Date");
														splDt = sc2.nextLine();
														while ( ! TaskUtil.validateDate(splDt)) {
															System.out.println("Date must be entered in dd/mm/yyyy, Please enter again");
															splDt = sc2.nextLine();
														}
														
														newTask = new TaskBean(task.getTaskName(), task.getDesc(), sdf.parse(splDt), task.getTags(), task.getPriority(), task.getcDt(), task.getStatus() );
														result = model.update(task, newTask, catName);
												
														if ( result.equals(Constants.SUCCESS))
															System.out.println(" Task Updated Succussfully :-)");
														else {
															System.out.println(" OOPS!! Something bad happened");
															System.out.println("Msg: " + result);
														}
														break;
														
												case 4:
														System.out.println();
														System.out.println("Enter the new Priority");
														priority = sc2.nextInt();
														while ( ! TaskUtil.validatePriority(priority) )
														{
															System.out.println("Priority must be within the range of 1 to 10, Please Enter again");
															priority = sc1.nextInt();
														}
														
														newTask = new TaskBean(task.getTaskName(), task.getDesc(), task.getPlDt(), task.getTags(), priority, task.getcDt(), task.getStatus() );
														result = model.update(task, newTask, catName);
												
														if ( result.equals(Constants.SUCCESS))
															System.out.println(" Task Updated Succussfully :-)");
														else {
															System.out.println(" OOPS!! Something bad happened");
															System.out.println("Msg: " + result);
														}
														break;
														
												case 5:
													System.out.println();
													System.out.println("Enter the new Status either as pending, completed or cancelled");
													status = sc2.next();
													while(  ! TaskUtil.validateStatus(status) )
													{
														System.out.println("Status must be as pending, completed or cancelled, Please Enter again");
														status = sc2.next();
													}
													
													newTask = new TaskBean(task.getTaskName(), task.getDesc(), task.getPlDt(), task.getTags(), task.getPriority(), task.getcDt(), status.trim().toLowerCase() );
													result = model.update(task, newTask, catName);
													
													if ( result.equals(Constants.SUCCESS) )
														System.out.println("Task Updated Succusfully");
													else
													{
														System.out.println(" OOPS!! Something bad happened");
														System.out.println("Msg: " + result);
													}
													break;
												
												default:
														System.out.println();
														System.out.println("Option not supported...");
											}
											break;
											
									case 3:
										Logger.getInstance().log("Deleting Tasks...");
										System.out.println();
										
										System.out.println("Existing Tasks :");
										tasks = model.listTasks(catName);
										for (TaskBean taskBean : tasks) {
											System.out.println(taskBean.getTaskName());
										}
										System.out.println("Enter the Task Name to delete");
										taskName = sc2.nextLine();
										while ( model.checkIfTaskExists(taskName, catName) )
										{
											System.out.println("Entered Task Name does not exist, Please enter a valid one from the above options");
											taskName = sc2.nextLine();
										}
										
										task = model.getTask(taskName, catName);
										result = model.delete(task, catName);
										
										if ( result.equals(Constants.SUCCESS))
											System.out.println(" Task Deleted Succussfully :-)");
										else
										{
											System.out.println(" OOPS!! Task could not be deleted");
											System.out.println("Msg: " + result);
										}
										break;
										
											
											
									case 4:
											Logger.getInstance().log("Listing the Tasks...");
											System.out.println();
											
											System.out.println("Press 1 to List Tasks by Alphabetical Listing by Name");
											System.out.println("Press 2 to List Tasks by Due Date");
											System.out.println("Press 3 to List Tasks by Created date");
											System.out.println("Press 4 to List Tasks by the Longest Time Pending");
											
											System.out.println("Enter your choice");
											ch4 = sc1.nextInt();
											
											switch(ch4)
											{
												case 1:
														System.out.println();
														
														tasks = model.listTasks(catName);
														
														for (TaskBean taskBean : tasks) {
															splDt = sdf.format(taskBean.getPlDt());
															scDt = sdf.format(taskBean.getcDt());
															System.out.println("Name: " + taskBean.getTaskName() + " Description: " + taskBean.getDesc() + " Tags: " + taskBean.getTags() + " Planned End Date: " + splDt + " Priority: " + taskBean.getPriority() + " Created Date: " + scDt + "Status: " + taskBean.getStatus());
														}
														break;
														
												case 2:
														System.out.println();
														
														for (TaskBean taskBean : model.getTasksBasedOnDueDate(catName)) {
															splDt = sdf.format(taskBean.getPlDt());
															scDt = sdf.format(taskBean.getcDt());
															System.out.println("Name: " + taskBean.getTaskName() + " Description: " + taskBean.getDesc() + " Tags: " + taskBean.getTags() + " Planned End Date: " + splDt + " Priority: " + taskBean.getPriority() + " Created Date: " + scDt+ "Status: " + taskBean.getStatus());
														}
														break;
														
												case 3:
														System.out.println();
														
														for (TaskBean taskBean : model.getTasksBasedOnCreatedDate(catName)) {
															splDt = sdf.format(taskBean.getPlDt());
															scDt = sdf.format(taskBean.getcDt());
															System.out.println("Name: " + taskBean.getTaskName() + " Description: " + taskBean.getDesc() + " Tags: " + taskBean.getTags() + " Planned End Date: " + splDt + " Priority: " + taskBean.getPriority() + " Created Date: " + scDt+ "Status: " + taskBean.getStatus());
														}
														break;
														
												case 4:
														System.out.println();
													
														for (TaskBean taskBean : model.getTasksBasedOnTimePending(catName)) {
															splDt = sdf.format(taskBean.getPlDt());
															scDt = sdf.format(taskBean.getcDt());
															System.out.println("Name: " + taskBean.getTaskName() + " Description: " + taskBean.getDesc() + " Tags: " + taskBean.getTags() + " Planned End Date: " + splDt + " Priority: " + taskBean.getPriority() + " Created Date: " + scDt+ "Status: " + taskBean.getStatus());
														}
														break;
														
												default:
														System.out.println();
														System.out.println("Option not Supported");
											}
											
											break;	
											
									case 5:
											Logger.getInstance().log("Searching...");
											System.out.println();
									
											System.out.println("Enter a Word or Sentence to Search");
											String input = sc2.nextLine();
											
											tasks = model.search(input, catName);
											
											System.out.println("Total Number of Occurances: " + model.getTotalNoOfOccurances(input, tasks));
											
											System.out.println("No of Occurances Found in Description: " + model.getNoOfOccurancesInDescription(input, tasks));
											for ( TaskBean taskBean : tasks) {
												if ( taskBean.getDesc().contains(input) )
													System.out.println( taskBean.getTaskName() + " - " + taskBean.getDesc());
											}
											
											System.out.println("No of Occurances found in Name: " + model.getNoOfOccurancesInName(input, tasks));
											for (TaskBean taskBean : tasks) {
												if ( taskBean.getTaskName().contains(input) )
													System.out.println( "Task Name: " + taskBean.getTaskName());
											}
											
											System.out.println("No of Occurances found in Tags: " + model.getNoOfOccurancesInTags(input, tasks));
											for (TaskBean taskBean : tasks) {
												if ( taskBean.getTags().contains(input) )
													System.out.println(taskBean.getTaskName() + " - " + taskBean.getTags() );
											}
											break;
											
									
									case 6: 
											break;
											
									default:
											System.out.println();
											System.out.println("Option Not Supported yet...");
								}
							}
		
							break;
							
					case 3:
							Logger.getInstance().log("Deleting a Catagory...");
							System.out.println();
							
							System.out.println("Enter the Catagory Name to delete");
							catName = sc2.nextLine();
							
							while ( ! model.checkIfCategoryExists(catName) )
							{
								System.out.println("Hmm...this Catagory does not exist, Please enter an existing one");
								catName = sc2.nextLine();
							}
							
							result = model.delete(catName);
							if ( result.equals(Constants.SUCCESS))
								System.out.println("Catagory Deleted Succussfully");
							else
								System.out.println("OOPS!! Could not delete the Catagory :-(");
							break;

					case 4:
							Logger.getInstance().log("Listing Catagory");
							System.out.println();
							
							System.out.println("Press 1 to List All Tasks by Alphabetical Listing by Name");
							System.out.println("Press 2 to List All Tasks by Due Date");
							System.out.println("Press 3 to List All Tasks by Created date");
							System.out.println("Press 4 to List All Tasks by the Longest Time Pending");
							
							System.out.println();
							System.out.println("Enter your choice");
							ch4 = sc1.nextInt();
							
							switch(ch4)
							{
								case 1:
										System.out.println();
										
										tasks = model.listTasks();
										for (TaskBean taskBean : tasks) {
											splDt = sdf.format(taskBean.getPlDt());
											scDt = sdf.format(taskBean.getcDt());
											System.out.println("Name: " + taskBean.getTaskName() + " Description: " + taskBean.getDesc() + " Tags: " + taskBean.getTags() + " Planned End Date: " + splDt + " Priority: " + taskBean.getPriority() + " Created Date: " + scDt+ "Status: " + taskBean.getStatus());
										}
										break;
										
								case 2:
										System.out.println();
										
										tasks = model.getTasksBasedOnDueDate();
										for (TaskBean taskBean : tasks) {
											splDt = sdf.format(taskBean.getPlDt());
											scDt = sdf.format(taskBean.getcDt());
											System.out.println("Name: " + taskBean.getTaskName() + " Description: " + taskBean.getDesc() + " Tags: " + taskBean.getTags() + " Planned End Date: " + splDt + " Priority: " + taskBean.getPriority() + " Created Date: " + scDt+ "Status: " + taskBean.getStatus());
										}
										break;
										
								case 3:
										System.out.println();
										
										tasks = model.getTasksBasedOnCreatedDate();
										for (TaskBean taskBean : tasks) {
											splDt = sdf.format(taskBean.getPlDt());
											scDt = sdf.format(taskBean.getcDt());
											System.out.println("Name: " + taskBean.getTaskName() + " Description: " + taskBean.getDesc() + " Tags: " + taskBean.getTags() + " Planned End Date: " + splDt + " Priority: " + taskBean.getPriority() + " Created Date: " + scDt+ "Status: " + taskBean.getStatus());
										}
										break;
										
								case 4:
										System.out.println();
										
										tasks = model.getTasksBasedOnTimePending();
										for (TaskBean taskBean : tasks) {
											splDt = sdf.format(taskBean.getPlDt());
											scDt = sdf.format(taskBean.getcDt());
											System.out.println("Name: " + taskBean.getTaskName() + " Description: " + taskBean.getDesc() + " Tags: " + taskBean.getTags() + " Planned End Date: " + splDt + " Priority: " + taskBean.getPriority() + " Created Date: " + scDt+ "Status: " + taskBean.getStatus());
										}
										break;
										
								default:
										System.out.println();
										System.out.println("Option not supported...");
							}
							break;
							
					case 5:
							Logger.getInstance().log("Searching in Catagories");
							System.out.println();
							
							System.out.println("Enter a word or sentence to search in all the Catagories");
							String input = sc2.nextLine();
							tasks = model.search(input);
							
							System.out.println("Total Number of Occurances: " + model.getTotalNoOfOccurances(input, tasks));
							
							System.out.println("No of Occurances Found in Description: " + model.getNoOfOccurancesInDescription(input, tasks));
							for ( TaskBean taskBean : tasks) {
								if ( taskBean.getDesc().contains(input) )
									System.out.println( taskBean.getTaskName() + " - " + taskBean.getDesc());
							}
							
							System.out.println("No of Occurances found in Name: " + model.getNoOfOccurancesInName(input, tasks));
							for (TaskBean taskBean : tasks) {
								if ( taskBean.getTaskName().contains(input) )
									System.out.println( "Task Name: " + taskBean.getTaskName());
							}
							
							System.out.println("No of Occurances found in Tags: " + model.getNoOfOccurancesInTags(input, tasks));
							for (TaskBean taskBean : tasks) {
								if ( taskBean.getTags().contains(input) )
									System.out.println(taskBean.getTaskName() + " - " + taskBean.getTags() );
							}
							break;
							
					case 6:
							Logger.getInstance().log("Exporting the Catagories to a PDF...");
							System.out.println();
							
							System.out.println("Enter the path to export the PDF");
							String path = sc2.nextLine();
							File f = new File(path);
							while ( ! (f.exists() || f.isDirectory()) )
							{
								System.out.println("Hmm...Entered path is not a directory, Please enter a directory path to Export");
								path = sc2.nextLine();
								f = new File(path);
							}
							
							result = model.export(path);
							if ( result.equals(Constants.SUCCESS))
								System.out.println("Export Succussfull :-)");
							else
							{
								System.out.println("OOPs !! Could not Export");
								System.out.println("Msg: " + result);
							}
							break;
							
						
					case 7:
							Logger.getInstance().log("Exiting...");
							System.out.println();
							
							System.out.println(" Thank You , See you later");
							break;
							
					default:
							System.out.println();
							System.out.println("Option not supported yet...");
				}
			}
		} catch(Throwable t) {
			// TODO: handle exception
			t.printStackTrace();
		}
		finally {
			sc1.close();
			sc2.close();
		}
	}

}
