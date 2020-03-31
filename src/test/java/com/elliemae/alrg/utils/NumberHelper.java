package com.elliemae.alrg.utils;

import java.math.BigDecimal;

public class NumberHelper {

	public static int getNumberOfDecimalPlaces(BigDecimal val) {		
		return getNumberOfDecimalPlaces(val.toString());
	}
	
	public static int getNumberOfDecimalPlaces(String val) {
		String[] temp = val.trim().split("\\.");
		int decimalLenght = temp[1].length();
		return decimalLenght;
	}	
	
	public static Integer extractNumberFromString(String scenarioText) {   //Extracting number from a String
        int count=0;
       if(scenarioText!=null && !scenarioText.equals("")) {
            scenarioText =scenarioText.replaceAll("[^0-9?!\\.]","");
            if(!scenarioText.equals("")) count=Integer.parseInt(scenarioText);
       }
        return count;
    }
}
