package Automation.winAppDriver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class TestJava {

	public static void main(String[] args) {
		//Test GitHub and
		// TODO Auto-generated method stub
		System.out.println("System date "+addingOrSubtractingDays("","","SystemDate","M/d/yyyy",7));
		System.out.println("System date "+addingOrSubtractingDays("","","SystemDate","M/d/yyyy",-8));
		System.out.println("System date "+addingOrSubtractingDays("","","SystemDate","d/MMMM/yyyy",7));
		System.out.println("System date "+addingOrSubtractingDays("","","SystemDate","d/MMMM/yyyy",-8));
		
		System.out.println("System date "+addingOrSubtractingDays("8/April/2020","d/MMMM/yyyy","","M/d/yyyy",7));
		System.out.println("System date "+addingOrSubtractingDays("8/April/2020","d/MMMM/yyyy","","M/d/yyyy",-8));
		System.out.println("System date "+addingOrSubtractingDays("8/April/2020","d/MMMM/yyyy","","d/MMMM/yyyy",7));
		System.out.println("System date "+addingOrSubtractingDays("8/April/2020","d/MMMM/yyyy","","d/MMMM/yyyy",-8));

	}
	
	public static String addingOrSubtractingDays(String inputDate,String inputDateFormat,String systemDate,String outputDateFormat,int i) 
	{
		String newDate = "";
		try {
			SimpleDateFormat sdf;
			Calendar c = null;
			
			//Calculates using system date
			if(!systemDate.isEmpty())
			{
			//Get system date
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy");
			LocalDateTime now = LocalDateTime.now();
			
			//Adding system date to Calendar instance
			sdf = new SimpleDateFormat("M/d/yyyy");
			c = Calendar.getInstance();
			try {
				c.setTime(sdf.parse(dtf.format(now)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			}
			
			//Calculates using given input date and format
			if(systemDate.isEmpty())
			{
				//Adding given input date to Calendar instance
				sdf = new SimpleDateFormat(inputDateFormat);
				c = Calendar.getInstance();
				try
				{
					c.setTime(sdf.parse(inputDate));
				}
				catch (ParseException e)
				{
					e.printStackTrace();
				}
			}

			//Add/subtract days
			c.add(Calendar.DAY_OF_MONTH, i);
			sdf = new SimpleDateFormat(outputDateFormat);
			newDate = sdf.format(c.getTime());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return newDate;

	}
	

}
