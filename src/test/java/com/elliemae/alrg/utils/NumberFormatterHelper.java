package com.elliemae.alrg.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

public class NumberFormatterHelper {

	public static final Locale EN_US = new Locale("en", "us");
	public static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(EN_US);
	
	
	public static String formatToUSCurrency(String val) {
		return formatToUSCurrency(Double.parseDouble(val));
	}	
	
	public static String formatToUSCurrency(Double val) {
		return currencyFormatter.format(val);
	}

	public static double currencyToDouble(String val) {
		if( StringUtils.isNotEmpty(val)) {
			return stringToDouble(val.replace("$", "")); 
		}
		return 0;
	}
	
	public static double currencyToInteger(String val) throws ParseException {
		if( StringUtils.isNotEmpty(val)) {
			return stringToInteger(val.replace("$", "")); 
		}
		return 0;
	}
	
	public static double stringToDouble(String val) {
		if( StringUtils.isNotEmpty(val)) {
			return Double.parseDouble(val.replace(",", ""));
		}
		return 0;
	}
	
	public static int stringToInteger(String val) throws ParseException {	
		if( StringUtils.isNotEmpty(val)) {
			return Integer.parseInt(val.replace(",", ""));
		}
		return 0;
	}	
	
	/**
	 * <b>Name:</b> clickloanSummaryChangeLoanType_ConfWin_OK<br>
	 * <b>Description:</b> This method is used to click on Ok button on confirmation Window for changing the loan type
	 */
	public static Double roundOff(Double value) {
		
		DecimalFormat f = new DecimalFormat("##.00");
		String formattedValue = f.format(value);
		return(Double.parseDouble(formattedValue));		
	}	
	
	/**
	 * Add "$" in front of a string
	 * @param value
	 * @return
	 */
	public static String formatStringToCurrency(String value) {
		Double value1 = Double.parseDouble(value);
		String temp = currencyFormatter.format(value1).replace("$", "");
		return temp;
	}	
}
