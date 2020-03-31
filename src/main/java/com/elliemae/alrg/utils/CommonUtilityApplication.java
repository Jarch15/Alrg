package com.elliemae.alrg.utils;


import com.elliemae.core.Logger.EllieMaeLog;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;
import com.elliemae.core.Utils.CommonUtility;
import com.elliemae.alrg.config.ALRGSQLDBHelper;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 * <b>Name:</b> CommonUtilityApplication
 * <b>Description: </b>This class is extending CommonUtility class in EllieMaeATF and is used in various classes as a utility
 * 
 * 
 */

public class CommonUtilityApplication extends CommonUtility {

    public static Logger _log = Logger.getLogger(CommonUtilityApplication.class);

    /**
     * <b>Name: </b>isNull<br>
     * <b>Description: </b>This method is used to hide the null-check
     * 
     * @param argument
     * @author <i>Andrei Arefiev</i>
     */
    public <T> boolean isNull(T argument) {
        return (argument == null);
    }

    /**
     * <b>Name: </b>getArrayFromFile<br>
     * <b>Description: </b>This method is used to get string array from a file
     * 
     * @param path - Path to a file
     * @author <i>Andrei Arefiev</i>
     */
    public static String[] getArrayFromFile(String path) {
        List<String> slst = new ArrayList<String>();
        try {
            Scanner sc = new Scanner(new File(path));
            while (sc.hasNext()) {
                slst.add(sc.nextLine());
            }
            sc.close();
        } catch (FileNotFoundException e) {
            EllieMaeLog.log(_log, "Exception in getArrayFromFile is: " + e.getMessage());
            e.printStackTrace();
        }
        int num = slst.size();
        int i = 0;
        String[] sarr = new String[num];
        for (String s : slst) {
            sarr[i] = s;
            i++;
        }
        return sarr;
    }

    /**
     * <b>Name: </b>extractTestDataToMap<br>
     * <b>Description: </b>This method is used to get string hash map from a
     * cell of xlsx file</br>
     * 
     * <b>Example:</b> <code>extractTestDataToMap(testData.get("borDob"))</br>
     * In a sell of the xlsx file the data should be placed as follows - {swipe:"3",dobYear:"1987",dobMth:OCT,dobDay:"15"}</code>
     * 
     * @param validationContent
     *        - content of the cell
     */
    public static HashMap<String, String> extractTestDataToMap(String validationContent) {
        HashMap<String, String> validationMap = new HashMap<String, String>();
        EllieMaeLog.log(_log, "TestData from a cell of Excel:" + validationContent);

        JSONObject json = new JSONObject(validationContent);

        if (null != json) {
            Set<String> keys = json.keySet();
            for (String key : keys) {
                try {
                    validationMap.put(key, json.getString(key));
                } catch (Exception e) {
                    validationMap.put(key, json.getJSONObject(key).toString());
                }
            }
        }
        return validationMap;
    }

    /**
     * <b>Name:</b>extractTestDataToMap<br>
     * <b>Description:</b>Parse the elements of the input or the baseline
     * content<br>
     * Pre-req: Data will be expected in the format : [key][value],[key2][value2
     * 
     * @param validationContent
     * 
     */
    public static HashMap<String, String> extractTestDataToMap_SquareBrackets(String content) {

        HashMap<String, String> map = new HashMap<String, String>();
        EllieMaeLog.log(_log, "***** Start Extract the Values from TestData in to a Map *****", EllieMaeLogLevel.reporter);
        EllieMaeLog.log(_log, "TestData from Excel:" + content);

        if (content.startsWith("file;"))
            content = CommonUtility.getStringFromFile(content, "baseline");

        Pattern p1 = Pattern.compile("\\[(.*?)\\]\\[(.*?)\\](?s)");
        Matcher m1 = p1.matcher(content);

        while (m1.find()) {
            Matcher m2 = p1.matcher(m1.group(0));
            while (m2.find()) {
                map.put(m2.group(1).trim(), m2.group(2).trim());
            }
        }
        EllieMaeLog.log(_log, "Test data Map details :" + map);
        EllieMaeLog.log(_log, "***** END Extract the Values from TestData in to a Map *****", EllieMaeLogLevel.reporter);
        return map;
    }

    /**
     * <b>Name: </b>getClipboardContents<br>
     * <b>Description: </b>This method is used to get the String residing on the
     * clipboard
     * 
     * @return any text found on the Clipboard; if none found, return an empty
     *         String
     * @author <i>Andrei Arefiev</i>
     */
    public static String getClipboardContents() {
        String result = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if (hasTransferableText) {
            try {
                result = (String) contents.getTransferData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException | IOException ex) {
                System.out.println(ex);
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * <b>Name:</b> compareList<br>
     * <b>Description:</b> This method is to compare if two lists contain the same strings
     * grid <br>
     * 
     * @param List
     *        of String 1
     * @param List
     *        of String 2
     * @return boolean
     * 
     */
    public static boolean compareListIgnoreOrder(List<String> list1, List<String> list2) {

        boolean isMatchFound = false;
        if (list1 == null && list2 == null) {
            return true;
        }

        if ((list1 == null && list2 != null) || list1 != null && list2 == null || list1.size() != list2.size()) {
            return false;
        }

        // to avoid messing the order of the lists we will use a copy
        // as noted in comments by A. R. S.
        List sortedList1 = new ArrayList<String>(list1);
        List sortedList2 = new ArrayList<String>(list2);

        Collections.sort(sortedList1);
        Collections.sort(sortedList2);
        isMatchFound = sortedList1.equals(sortedList2);

        return isMatchFound;
    }

    public static boolean compareList(List<String> list1, List<String> list2) {

        boolean isMatchFound = false;
        if (list1 == null && list2 == null) {
            return true;
        }

        if ((list1 == null && list2 != null) || list1 != null && list2 == null || list1.size() != list2.size()) {
            return false;
        }
        isMatchFound = list1.equals(list2);

        return isMatchFound;
    }




    public static void executeUpdateQuery(String query) throws Exception {
        ALRGSQLDBHelper sqlDbHelper = ALRGSQLDBHelper.getInstance();
        Connection conn = sqlDbHelper.getDBConnection();
        sqlDbHelper.executeUpdate(conn, query);
    }

    
    /**
     * <b>Name: </b>verifySortingOrder<br>
     * <b>Description: </b>This method is used to verify alphabetical sorting order
     * Provide --> List<String> cityNames1 = new ArrayList<String>(Arrays.asList("London", "New York", "Tokyo"));
     * 
     * @param List<String>
     * @author <i>Aditya Shrivastava</i>
     */
    public static boolean verifySortingOrder(List<String> list){
    	
        int n;        
        n = list.size();        
        boolean isSorted = true;            
        for (int i = 0; i < n -1; i++) 
        {
            for (int j = i + 1; j < n; j++) 
            {
                if (list.get(i).toString().compareTo(list.get(j).toString())>0)  //to verify sorting order
                {
                	isSorted = false;
                	
                }
            }
        }
        return isSorted;        
    }
}