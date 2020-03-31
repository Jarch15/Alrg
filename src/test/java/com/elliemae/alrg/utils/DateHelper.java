package com.elliemae.alrg.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateHelper {

	private static final SimpleDateFormat SDF_MMddyyyy = new SimpleDateFormat("MM/dd/yyyy");
	private static final SimpleDateFormat SDF_yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat SDF_DDMMMMYYYY = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);

	public static String currentTimeStampAsString() {
		return CommonUtilityApplication.getCurrentDateTimeString("HHmmssMMddyy");
	}

	public static String today_MMddyyyy() {
		return deltaDaysFromToday_MMddyyyy(0);
	}

	public static String daysFromToday_MMddyyyy(int numOfDays) {
		return deltaDaysFromToday_MMddyyyy(numOfDays);
	}

	/**
	 * <b>Description:</b> This method is used to get previous and next months from system time
	 */
	public static String getCurrentMonth_DDMMMMYYYY(int numberOfMmonths) {

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, numberOfMmonths);
		return SDF_DDMMMMYYYY.format(c.getTime()).split(" ")[1];
	}
	
	/**
	 * <b>Description:</b> This method is used to get previous and next months from system time
	 */
	public static String getCurrentMonthAsNumber() {

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return String.valueOf(c.get(Calendar.MONTH)+1);
	}

    public static String deltaDaysFromToday_MMddyyyy(int numOfYears, int numOfMonths, int numOfDays) {

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, numOfYears);
        c.add(Calendar.MONTH, numOfMonths);
        c.add(Calendar.DATE, numOfDays);
        return SDF_MMddyyyy.format(c.getTime());
    }	
	
	public static String deltaDaysFromToday_MMddyyyy(int numOfDays) {

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, numOfDays);
		return SDF_MMddyyyy.format(c.getTime());
	}

	/**
	 * <b>Description:</b> This method is used to get date format from system time
	 */
	public static String today_DDMMMMYYYY() {
		Date now = new Date();
		return SDF_DDMMMMYYYY.format(now);
	}

	public static Date toDate_yyyyMMdd(String dateStr) throws ParseException {
		Date d = (Date) SDF_yyyyMMdd.parse(dateStr);
		return d;
	}

	public static String fromyyyyMMdd_toMMddyyyy(String dateStr) throws ParseException {
		Date d = (Date) SDF_yyyyMMdd.parse(dateStr);
		return SDF_MMddyyyy.format(d);
	}

	public static String fromMMddyyyy_toyyyyMMdd(String dateStr) throws ParseException {
		Date d = (Date) SDF_MMddyyyy.parse(dateStr);
		return SDF_yyyyMMdd.format(d);
	}

	/**
	 * <b>Description:</b> This method is used to get current year from system time
	 */
	public static int getCurrentYear() {
		return Integer.parseInt(DateHelper.today_DDMMMMYYYY().split(" ")[2]);
	}

	/**
	 * <b>Description:</b> This method is used to get current month as text from system time
	 */
	public static String getCurrentMonthAsText() {
		return DateHelper.today_DDMMMMYYYY().split(" ")[1];
	}

	/**
	 * <b>Description:</b> This method is used to get current day from system time
	 */
	public static int getCurrentDay() {
		return Integer.parseInt(DateHelper.today_DDMMMMYYYY().split(" ")[0]);
	}

	public static String systemDate() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy ");
		//get current date 
		Date date = new Date();
		// Now format the date
		String date1 = dateFormat.format(date).trim();
		return date1;
	}
}
