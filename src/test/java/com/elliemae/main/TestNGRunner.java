package com.elliemae.main;

import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;
import com.elliemae.core.Utils.CommonUtility;
import com.elliemae.core.Utils.CommonUtility.FileType;
import com.elliemae.core.Utils.MobileUtility;
import com.elliemae.alrg.consts.ALRGConsts;
import com.elliemae.alrg.utils.ALRGLogger;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.tools.ant.util.DateUtils;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import Exception.FilloException;
import Fillo.Recordset;

/**
 * <b>Name:</b> TestNGRunner 
 * <b>Description: </b>This class is used to create TestNG xml programmatically by reading excel data input and will be used to trigger test cases
 * 
 * @author <i>Supreet Singh</i>
 */

public class TestNGRunner {
	
    public static Logger _log=Logger.getLogger(TestNGRunner.class);
    
    private static String timeStamp = CommonUtility.currentTimeStamp;
    
    private static final String TESTING_VERSION = "testingVersion";
    private static final String RETRY_COUNT = "retryCount";
    private static final String UPDATE_TEST_STATUS_TO_JIRA = "updateTestStatusToJIRA";
    private static final String JIRA_USER_NAME = "jiraUserName";
    private static final String JIRA_PASSWORD = "jiraPassword";
    private static final String JIRA_PROJECT_NAME = "jiraProjectName";

    private static final String TEST_CYCLE_VERSION = "testCycleVersion";
    private static final String TEST_CYCLE_NAME = "testCycleName";
    private static final String YES = "yes";
    private static final String CHAR_EQUAL = "=";
    private static final String CHAR_UNDERSCORE = "_";
    private static final String CHAR_COMMA = ",";
    private static final String CHAR_DOUBLE_BAR = "||";
    private static final String DEFAULT_PLATFORM = "Web";
    private static final String COLUMN_PRIORITY = "Priority";
    private static final String COLUMN_COMPONENT = "Component";
    private static final String COLUMN_DEVICE_GROUP = "DeviceGroup";
    private static final String ALL = "ALL";
    private static final String TEST_METHOD_NAME = "TestMethodName";
    private static final String CLASS_NAME = "ClassName";
    private static final String CHAR_$$ = "$$";
    private static final String PLATFORM = "Platform";
    private static final String BROWSER_NAME = "BrowserName";
    private static final String DEVICE_USER_NAME = "DeviceUserName";
    private static final String DEVICE_TYPE = "DeviceType";
    //private static final String ENVIRONMENT_CLIENT_ID = "environmentClientID";
    private static final String ENVIRONMENT_PASSWORD = "environmentPassword";
    private static final String ENVIRONMENT_USER_NAME = "environmentUserName";
    private static final String ENVIRONMENT_NAME = "environmentName";
    private static final String JENKINS_JOB_NAME = "jenkinsJobName";
    private static final String TESTING_TYPE = "testingType";
    private static final String INPUT_EXCEL = "inputexcel";
    private static final String INPUT_DRIVER_INFO_EXCEL = "inputDriverInfoExcel";
    private static final String DEVICE_NAME = "DeviceName";
    private static final String VERSION = "Version";
    private static final String EMAILABLE_REPORT_HTML = "emailable-report.html";
    private static final String AUTOMATION_OUTPUT = "AutomationOutput";
    private static final String USER_DIR = "user.dir";
    private static final String APPIUM_PORT = "AppiumPort";
    private static final String XML_ALLOW_RETURN_VALUES = "XmlAllowReturnValues";
    private static final String XML_SUITE_THREAD_COUNT = "XmlSuiteThreadCount";
    private static final String XML_SUITE_PARALLEL_MODE = "XmlSuiteParallelMode";
    private static final String XML_SUITE_NAME = "XmlSuiteName";
    private static final String PRIORITY = "priority";
    private static final String TEAM_NAME = "teamName";
    private static final String LAYER = "layer";
    private static final String DEVICE_GROUP = "deviceGroup";
    private static final String COMPONENT = "component";
    private static final String INCLUDED_COMPONENT = "includedComponent";
    private static final String EXCLUDED_COMPONENT = "excludedComponent";
    private static final String THREAD_COUNT = "threadCount";
    private static final String PARALLEL_MODE = "parallel";
	
	 //Modified for TestObject
	private static final String TESTOBJECTACCESSCODE ="TestObjectAccessCode"; 
    private static final String SCUser = "SCUser";
    private static final String SCApiKey = "SCApiKey";
    private static final String SCLogFile = "SCLogFile";
    private static final String SCPort = "SCPort";
    private static ProcessBuilder scPb;
    private static Process scProc;
    //Added to satisfy new update for sauce Lab
    private static final String COL_ENVIRONMENT_NAME = "EnvironmentName";
    private static boolean isScStarted = false;
    private static boolean startSCConnect = false;
	
	public static void main(String args[]) throws Exception {
		
		System.setProperty("logfolder", timeStamp);
		System.setProperty("logfilename", "AutomationLog_"+timeStamp);
		DOMConfigurator.configure("src"+File.separator+"main"+File.separator+"resources"+File.separator+"log4j.xml");
		
		ALRGLogger.log(_log,"Started Main Method");

		TestNGRunner mainObj = new TestNGRunner();

		Boolean emulatorStart= false;
		Map<String, String> parameters;	
		Map<String, String> argumentKeyValue = new HashMap<String,String>();
		Map<String, String> suiteInfo = new HashMap<>();
		Map<String, String> suiteParameter = new HashMap<>();
		// Map can hold TestNG Parameters.
		Map<String,Map<String,String>> testngParams = new HashMap<String, Map<String,String>>();
		Map<String,String> appiumPorts= new HashMap<>();
		List<XmlClass> testClasses= new ArrayList<>();
		ALRGLogger.log(_log,"############################################################################################");
		ALRGLogger.log(_log,"Started Main method to create TestNG.xml programmatically");
		
		/** Code to process command-line arguments. */
		processInputArguments(args, argumentKeyValue);
		
		if( argumentKeyValue.get(JIRA_USER_NAME) != null ) {
			suiteParameter.put(JIRA_USER_NAME, argumentKeyValue.get(JIRA_USER_NAME));
		}
		if( argumentKeyValue.get(JIRA_PASSWORD) != null ) {
			suiteParameter.put(JIRA_PASSWORD,  argumentKeyValue.get(JIRA_PASSWORD));
		}
        
		String additionalDataFilePath= CommonUtility.getRelativeFilePath("config", 
		        argumentKeyValue.get(INPUT_EXCEL)== null? "TestNG_XMLData_APP.xlsx":argumentKeyValue.get(INPUT_EXCEL).trim());
		
		if(StringUtils.isBlank(argumentKeyValue.get(UPDATE_TEST_STATUS_TO_JIRA)) || !YES.equalsIgnoreCase(argumentKeyValue.get(UPDATE_TEST_STATUS_TO_JIRA).trim())) {
			suiteParameter.put(UPDATE_TEST_STATUS_TO_JIRA, "no");
		}
		else {
			suiteParameter.put(UPDATE_TEST_STATUS_TO_JIRA, YES);
		}
		
		if(StringUtils.isNotBlank(argumentKeyValue.get(JIRA_PROJECT_NAME))) {
			suiteParameter.put(JIRA_PROJECT_NAME, argumentKeyValue.get(JIRA_PROJECT_NAME));
		}
		
		if(StringUtils.isNotBlank(argumentKeyValue.get(TEST_CYCLE_VERSION))) {
			suiteParameter.put(TEST_CYCLE_VERSION, argumentKeyValue.get(TEST_CYCLE_VERSION));
		}
		
	    if (StringUtils.isBlank(argumentKeyValue.get(TEST_CYCLE_NAME))) {

	        String testCycleName = DateUtils.format(System.currentTimeMillis(), "MMddyyyy_HHmmss");
            if( StringUtils.isNotEmpty(argumentKeyValue.get(TESTING_TYPE) )) {
                testCycleName = testCycleName + "_" + argumentKeyValue.get(TESTING_TYPE);
            }
            if( StringUtils.isNotEmpty(argumentKeyValue.get(INCLUDED_COMPONENT) ) && !"ALL".equalsIgnoreCase(argumentKeyValue.get(INCLUDED_COMPONENT)) ) {
                testCycleName = testCycleName + "_" + argumentKeyValue.get(INCLUDED_COMPONENT).replace(",", "-");
            }
            if( StringUtils.isNotEmpty(argumentKeyValue.get(EXCLUDED_COMPONENT) )) {
                testCycleName = testCycleName + "_" + "EXCL" + "_" + argumentKeyValue.get(EXCLUDED_COMPONENT).replace(",", "-");
            }	                

            testCycleName = testCycleName + "_" + argumentKeyValue.get(ENVIRONMENT_NAME);
            //testCycleName = testCycleName + "_" + argumentKeyValue.get(ENVIRONMENT_CLIENT_ID);
            suiteParameter.put(TEST_CYCLE_NAME, testCycleName);
        } else {
            suiteParameter.put(TEST_CYCLE_NAME, DateUtils.format(System.currentTimeMillis(), "MMddyyyy_HHmmss") + "_" + argumentKeyValue.get(TEST_CYCLE_NAME));
        }
		
		if(argumentKeyValue.containsKey(RETRY_COUNT)) {
			suiteParameter.put(RETRY_COUNT, argumentKeyValue.get(RETRY_COUNT));
		}
		
		/** Create TestNG XML Suite data collection. */
		ALRGLogger.log(_log, "Query SuiteInfo sheet to fetch suite details");
		String strSuiteInfoQuery="Select * from SuiteInfo";		
		Recordset recordSetSuiteInfo = CommonUtility.getRecordSetUsingFillo(additionalDataFilePath,strSuiteInfoQuery);
		ALRGLogger.log(_log, "SuiteInfo data retrieved");
		
		while(recordSetSuiteInfo.next()){	
			// Updated for TestObject
          if(StringUtils.isNotBlank(argumentKeyValue.get(ENVIRONMENT_NAME))) {
        	            
			suiteInfo.put(XML_SUITE_NAME, recordSetSuiteInfo.getField(XML_SUITE_NAME));
			
			//If Parallel Mode is available in the input arguments, Input argument (Parallel Mode) will be considered for generating the TestNG XML, 
			//else it will be picked up from the input file.
			if(argumentKeyValue.containsKey(PARALLEL_MODE))
				suiteInfo.put(XML_SUITE_PARALLEL_MODE, argumentKeyValue.get(PARALLEL_MODE).trim());
			else				
				suiteInfo.put(XML_SUITE_PARALLEL_MODE, null == recordSetSuiteInfo.getField(XML_SUITE_PARALLEL_MODE)? 
				        null: recordSetSuiteInfo.getField(XML_SUITE_PARALLEL_MODE).trim());
			//If Thread Count is available in the input arguments, Input argument (Thread Count) will be considered for generating the TestNG XML, 
			//else it will be picked up from the input file.
			if(argumentKeyValue.containsKey(THREAD_COUNT)) {
				suiteInfo.put(XML_SUITE_THREAD_COUNT, argumentKeyValue.get(THREAD_COUNT).trim());
			}
			else {			
				suiteInfo.put(XML_SUITE_THREAD_COUNT, null == recordSetSuiteInfo.getField(XML_SUITE_THREAD_COUNT)? 
				        null: recordSetSuiteInfo.getField(XML_SUITE_THREAD_COUNT).trim());
			}
			//If testing business rules, set threadCount to 1
			if( argumentKeyValue.get(INCLUDED_COMPONENT).contains("BR") ) {
			    suiteInfo.put(XML_SUITE_PARALLEL_MODE, null);
			    suiteInfo.put(XML_SUITE_THREAD_COUNT, "1");
			}
			
			suiteInfo.put(XML_ALLOW_RETURN_VALUES, recordSetSuiteInfo.getField(XML_ALLOW_RETURN_VALUES));
			suiteParameter.put(JENKINS_JOB_NAME, argumentKeyValue.get(JENKINS_JOB_NAME)==null? "":argumentKeyValue.get(JENKINS_JOB_NAME).trim());
			
			//Updated for TestObject. recordsetInfo different
			suiteParameter.put(ENVIRONMENT_NAME, argumentKeyValue.get(ENVIRONMENT_NAME).isEmpty() ? 
			        recordSetSuiteInfo.getField(COL_ENVIRONMENT_NAME):argumentKeyValue.get(ENVIRONMENT_NAME));
			suiteParameter.put(ENVIRONMENT_USER_NAME, argumentKeyValue.get(ENVIRONMENT_USER_NAME)==null? 
			        recordSetSuiteInfo.getField("EnvironmentUserName"):argumentKeyValue.get(ENVIRONMENT_USER_NAME).trim());
			suiteParameter.put(ENVIRONMENT_PASSWORD, argumentKeyValue.get(ENVIRONMENT_PASSWORD)==null? 
			        recordSetSuiteInfo.getField("EnvironmentPassword"):argumentKeyValue.get(ENVIRONMENT_PASSWORD).trim());
/*			suiteParameter.put(ENVIRONMENT_CLIENT_ID, argumentKeyValue.get(ENVIRONMENT_CLIENT_ID)==null? 
			        recordSetSuiteInfo.getField("EnvironmentClientID"):argumentKeyValue.get(ENVIRONMENT_CLIENT_ID).trim());*/
			//ENCW customized code
			suiteParameter.put(ALRGConsts.ENVIRONMENT_URL_PARAM, 
				argumentKeyValue.get(ALRGConsts.ENVIRONMENT_URL_PARAM)==null? "":argumentKeyValue.get(ALRGConsts.ENVIRONMENT_URL_PARAM).trim());
			suiteParameter.put(ALRGConsts.RUNNING_DEVICE_MODE_PARAM, 
					argumentKeyValue.get(ALRGConsts.RUNNING_DEVICE_MODE_PARAM)==null? "":argumentKeyValue.get(ALRGConsts.RUNNING_DEVICE_MODE_PARAM).trim());
			
          //Updated for TestObject
          }  else{
               Validate.notBlank(suiteParameter.get(ENVIRONMENT_NAME), "The supplied Environment Name i.e. '" + argumentKeyValue.get(ENVIRONMENT_NAME) 
                   + "' entry is missing in configuration file.");
          }
          suiteParameter.put("startLoggingFromMain", "true");
		}
		
        if(argumentKeyValue.containsKey(BROWSER_NAME))
        {
        	String[] browserArgs = argumentKeyValue.get(BROWSER_NAME).split(CHAR_COMMA);
        	for(int browserCount = 0; browserCount < browserArgs.length; browserCount++)
        	{
        		parameters = new HashMap<>();	
        		parameters.put("browserName", browserArgs[browserCount].trim());
        		parameters.put("platform", DEFAULT_PLATFORM);              
        		testngParams.put(browserArgs[browserCount], parameters);
        	}
        }
        
        String driverInfoDataFilePath= CommonUtility.getRelativeFilePath("config", 
                argumentKeyValue.get(INPUT_DRIVER_INFO_EXCEL)== null? "TestNG_DriverInfo.xlsx":argumentKeyValue.get(INPUT_DRIVER_INFO_EXCEL).trim());
        Recordset recordSetDriverInfo = getDriverInfoRecordSet(additionalDataFilePath, driverInfoDataFilePath);
        
		while(recordSetDriverInfo.next()) {			
			parameters = new HashMap<>();		
		    parameters.put("deviceUserName", recordSetDriverInfo.getField(DEVICE_USER_NAME).trim());
		    parameters.put("platform", recordSetDriverInfo.getField(PLATFORM).trim());
		    parameters.put("version", recordSetDriverInfo.getField(VERSION).trim());
		    parameters.put("deviceName", recordSetDriverInfo.getField(DEVICE_NAME).trim());
		    parameters.put("deviceOrientation", recordSetDriverInfo.getField("DeviceOrientation").trim());
		    //parameters.put("browserName", recordSetDriverInfo.getField(BROWSER_NAME).trim());
		    parameters.put("port", recordSetDriverInfo.getField(APPIUM_PORT).trim());
		    parameters.put("deviceType", recordSetDriverInfo.getField(DEVICE_TYPE).trim());
			parameters.put("loanFolder", recordSetDriverInfo.getField("LoanFolder").trim());
			
			//Updated for TestObject
			parameters.put("testObjectAccessCode", getTestObjectAccessCode(recordSetDriverInfo));
			
			if(recordSetDriverInfo.getField(PLATFORM).trim().equalsIgnoreCase("API"))				
				testngParams.put(recordSetDriverInfo.getField(PLATFORM).trim(), parameters);
			
			else if(recordSetDriverInfo.getField(PLATFORM).trim().equalsIgnoreCase("Web") 
					|| recordSetDriverInfo.getField(PLATFORM).trim().equalsIgnoreCase("APIWeb") 
					|| recordSetDriverInfo.getField(PLATFORM).trim().equalsIgnoreCase("Android") 
					|| recordSetDriverInfo.getField(PLATFORM).trim().equalsIgnoreCase("IOS") )
			{	
				if(!argumentKeyValue.containsKey(BROWSER_NAME))
				{
					  testngParams.put(recordSetDriverInfo.getField(BROWSER_NAME).trim(), parameters);
					  parameters.put("browserName",  recordSetDriverInfo.getField(BROWSER_NAME).trim());
				}
			}
			else
				testngParams.put(recordSetDriverInfo.getField(BROWSER_NAME).trim() + CHAR_UNDERSCORE+ recordSetDriverInfo.getField(DEVICE_USER_NAME) 
				    + CHAR_UNDERSCORE+ recordSetDriverInfo.getField(DEVICE_NAME) + CHAR_UNDERSCORE+ recordSetDriverInfo.getField(VERSION), parameters);
		    
		    if("Emulator".equalsIgnoreCase(recordSetDriverInfo.getField(DEVICE_TYPE))) {
		    	ALRGLogger.log(_log,"");
				ALRGLogger.log(_log,"-----------------------------------------------------------------");
				ALRGLogger.log(_log,"Starting new Android Device Emulator..");
				try{
					MobileUtility.startAndroidDeviceEmulator(recordSetDriverInfo.getField(DEVICE_USER_NAME).trim(),recordSetDriverInfo.getField(DEVICE_NAME).trim());
					Thread.sleep(60000);
					emulatorStart=true;
					ALRGLogger.log(_log,"Started new Android Device Emulator.");
				}
				catch(Exception e){
					ALRGLogger.log(_log,"Exception while starting new Android Device Emulator: "+e.getMessage());
				}
		    }
		    
		    if(!recordSetDriverInfo.getField(APPIUM_PORT).isEmpty())
		    	appiumPorts.put(recordSetDriverInfo.getField(APPIUM_PORT),recordSetDriverInfo.getField("BrowserDriverPort"));		    
	           
		    //Updated for testObject
/*		    if(StringUtils.isEmpty(getTestObjectAccessCode(recordSetDriverInfo))) {
		    	try {
		    		throw new Exception("Not provided Test Object Access code");
		    	} catch (Exception e) {
		    		ALRGLogger.log(_log,"Exception while accessing the TESTOBJECTACCESSCODE : "+e.getMessage());
		    	}                              
		    }*/
		}
					
		if(!(appiumPorts == null)) {
			for(Map.Entry<String,String> entry: appiumPorts.entrySet()) {
				//Updated for TestObject
				if(recordSetDriverInfo.getField(DEVICE_TYPE).equalsIgnoreCase("SauceLab") || 
						recordSetDriverInfo.getField(DEVICE_TYPE).equalsIgnoreCase("TestObject")) {
					//ALRGLogger.log(_log,"This SauceLab or TestObject Session");
					startSCConnect = true;
					ALRGLogger.log(_log,"This SauceLab or TestObject Session");
				} else {			    	
					ALRGLogger.log(_log,"");
			    	ALRGLogger.log(_log,"-----------------------------------------------------------------");
					ALRGLogger.log(_log,"Starting new Appium Session on port: "+entry.getKey());
					try{
						MobileUtility.startAppiumSession(entry.getKey(),entry.getValue(),recordSetDriverInfo.getField(PLATFORM));
						Thread.sleep(10000);
						ALRGLogger.log(_log,"Started new Appium Session.");
					}
					catch(Exception e){
						ALRGLogger.log(_log,"Exception while starting new Appium Session: "+e.getMessage());
					}
			    }
			}			
		}
		
		/** Gets testcases matching the input criteria.*/
		String strTestClassQuery = buildQuery("TestClass", argumentKeyValue);
		String strTestMethodQuery = buildQuery("TestMethod", argumentKeyValue);
		testClasses = mainObj.getTestCasesForCriteria(argumentKeyValue, additionalDataFilePath, strTestClassQuery, strTestMethodQuery);
				
		ALRGLogger.log(_log,"*******************************TESTCASE EXECUTION STARTED********************************");
		//Updated for TestObject
		if (startSCConnect){
			ALRGLogger.log(_log,"This starts SauceLab or TestObject Connection", EllieMaeLogLevel.reporter);
			beforeSCSuite(argumentKeyValue.get(SCUser),argumentKeyValue.get(SCApiKey),argumentKeyValue.get(SCLogFile),argumentKeyValue.get(SCPort));
		}
		
		mainObj.generateXMLfile(suiteInfo,suiteParameter, testngParams,testClasses);			
		
		ALRGLogger.log(_log,"*******************************TESTCASE EXECUTION FINISHED*******************************");
		
		if(emulatorStart==true){
			ALRGLogger.log(_log,"Killing Android AVD Device..");
			try{
				MobileUtility.killAndroidDeviceEmulator();
				ALRGLogger.log(_log,"Killed Android AVD Device.");
			}
			catch(Exception e){
				ALRGLogger.log(_log,"Exception while killing Android AVD Device: "+e.getMessage());
			}
			ALRGLogger.log(_log,"");
		}
		
		if(!(appiumPorts==null)){
			for(int i = 0; i<appiumPorts.size(); i++){
				ALRGLogger.log(_log,"Killing Appium Session..");
				try{
					MobileUtility.killAppiumSession();
					ALRGLogger.log(_log,"Killed Appium Session.");
				}
				catch(Exception e){
					ALRGLogger.log(_log,"Exception while killing Appium Session: "+e.getMessage());
				}
			}
		}
				
		ALRGLogger.log(_log,"############################################################################################");
		
		//Updated for TestObject
		afterSCSuite();
		
		/** Generate TestNG created result artifact archives. */ 
		mainObj.generateLogAndReportFiles();
		ALRGLogger.log(_log,"Completed Main Method.");
	}

	private static String getTestObjectAccessCode(Recordset recordSetDriverInfo) {
		try {
			return recordSetDriverInfo.getField(TESTOBJECTACCESSCODE);
		} catch (FilloException e) {
			return "";
		}		
	}
	
	/**
	 * <b>Name:</b> processInputArguments
	 * <b>Description:</b> Method to process the input command-line arguments.
	 * 
	 * @param args The input argument array
	 * @param argumentKeyValue The argument key-value collection.
	 * @throws Exception The exception instance.
	 */
	protected static void processInputArguments(String[] args, Map<String, String> argumentKeyValue) throws Exception {
		
	    String value, key;
		
		for(String arg: args){
			value = null;
			
			if(StringUtils.isNotBlank(arg)) {
				arg = arg.trim();
				
				if(arg.contains("-")) {
					arg = arg.substring(arg.indexOf('-')+1);
				}
				else {
					ALRGLogger.log(_log, "Invalid argument specified:"+ arg);
					throw new Exception("Invalid argument specified:"+ arg);
				}
				
				if(arg.contains(CHAR_EQUAL)) {
					int idx = arg.indexOf(CHAR_EQUAL);			
					key = arg.substring(0, idx);
					value = arg.substring(idx+1, arg.length());
				}
				else {
					ALRGLogger.log(_log, "Value expected for specified argument '"+ arg + "'.");
					throw new Exception("Value expected for specified argument '"+ arg + "'.");					
				}
				
                if( StringUtils.isNotBlank(value) ) {
                    argumentKeyValue.put(key, value);
                }
                if(COMPONENT.equalsIgnoreCase(key)) {
			        List<String> argValues = Arrays.asList(value.split(CHAR_COMMA));
			        List<String> excludedGroups = new ArrayList<String>();
			        List<String> includedGroups = new ArrayList<String>();
			        for(String v : argValues) {
			          if( v.startsWith("^") ) {
			            excludedGroups.add(v.substring(1).trim());
			          } else {
			            includedGroups.add(v.trim());
			          }
			        } 	
			        argumentKeyValue.put(INCLUDED_COMPONENT, String.join(CHAR_COMMA, includedGroups));
			        argumentKeyValue.put(EXCLUDED_COMPONENT, String.join(CHAR_COMMA, excludedGroups));
				}
			}
		}		
		String[] DEFAULT_ARG_VALUES = new String[]{TESTING_TYPE, PRIORITY, TEAM_NAME, LAYER, DEVICE_GROUP, COMPONENT};
		for( String s : DEFAULT_ARG_VALUES ) {
	        if(!argumentKeyValue.containsKey(s)) 
	            argumentKeyValue.put(s, ALL);		    
		}		
		if(!argumentKeyValue.containsKey(INCLUDED_COMPONENT) && !argumentKeyValue.containsKey(EXCLUDED_COMPONENT))
		  argumentKeyValue.put(INCLUDED_COMPONENT, ALL);		
	}
	
	protected static String buildQuery(String queryType, Map<String, String> argumentKeyValue) {
		
		StringBuffer strQueryBuilder = new StringBuffer();
		StringBuffer strTestFields = new StringBuffer(PRIORITY).append(",").append(DEVICE_GROUP).append(",").append(COMPONENT);
		StringBuffer strTestConditions = new StringBuffer(" where ExecuteClass= 'yes'");
		
		/** Code to populate respective queries with argument values. */
		if(argumentKeyValue.containsKey(TESTING_TYPE) && !"all".equalsIgnoreCase(argumentKeyValue.get(TESTING_TYPE))) {
			strTestConditions.append(" and ").append(argumentKeyValue.get(TESTING_TYPE)).append(" ='yes'");
		}			  		
		if(argumentKeyValue.containsKey(TESTING_VERSION) && !"all".equalsIgnoreCase(argumentKeyValue.get(TESTING_VERSION))) {
			strTestFields.append(",").append(argumentKeyValue.get(TESTING_VERSION).replaceAll("'", ""));
			strTestConditions.append(" and ").append(argumentKeyValue.get(TESTING_VERSION)).append(" != 'no'");
		}		
		if(argumentKeyValue.containsKey(DEVICE_GROUP) && !"all".equalsIgnoreCase(argumentKeyValue.get(DEVICE_GROUP))) {
			strTestConditions.append(" and ").append(COLUMN_DEVICE_GROUP).append(" = '").append(argumentKeyValue.get(DEVICE_GROUP)).append("'");
		}			
		
		if( "TestClass".equalsIgnoreCase(queryType) ) {
			strQueryBuilder.append("Select ").append(strTestFields).append(", ClassName").append(" From TestClasses ").append(strTestConditions);
		} else if( "TestMethod".equalsIgnoreCase(queryType) ) {
			strQueryBuilder.append("Select ").append(strTestFields).append(", TestMethodName").append(" From TestClasses ").append(strTestConditions)
				.append(" and ClassName ='" ).append( CHAR_$$ ).append( "'");
		}		
		return strQueryBuilder.toString();
	}
	
	/**
	 * <b>Name:</b> generateLogAndReportFiles
	 * <b>Description:</b> Method to generate the log and report files.
	 * 
	 */
	protected void generateLogAndReportFiles() {
		String sourceFilePath;
		String destinationFilePath, tempDestinationFilePath = "";
		File file = null;
		FileWriter fileWriter = null;
		
		try{
			sourceFilePath = System.getProperty(USER_DIR) + File.separator + AUTOMATION_OUTPUT+ File.separator + timeStamp;
				
			destinationFilePath= FrameworkConsts.OUTPUTPATH + "/" + FrameworkConsts.ENVIRONMENTNAME + "/" + FrameworkConsts.JENKINSJOBNAME + "/" +timeStamp;
			tempDestinationFilePath = destinationFilePath.replace("/", "\\");
			
			ALRGLogger.log(_log, "Shared folder path:" + tempDestinationFilePath);
			
			String linkFolder = System.getProperty(USER_DIR) + File.separator + AUTOMATION_OUTPUT ; 
			file = new File(linkFolder);
			file.mkdirs();
			ALRGLogger.log(_log, "Automation Output Link folder path:" + linkFolder);
			
			file = new File(linkFolder + File.separator + "OutputLink.txt");
			file.createNewFile();
			
			fileWriter = new FileWriter(file);
			fileWriter.write(tempDestinationFilePath);
			fileWriter.flush();

			CommonUtility.copyFilesOrFolder(sourceFilePath + File.separator + "TestNG_Reports" + File.separator + ALRGConsts.REPORT_FILE_NAME, 
			        System.getProperty(USER_DIR) + File.separator + AUTOMATION_OUTPUT+ File.separator +ALRGConsts.REPORT_FILE_NAME, FileType.FILE);
			
			//also copy emailable-report to log folder, so it can be zipped with the log files
			CommonUtility.copyFilesOrFolder(sourceFilePath + File.separator + "TestNG_Reports" + File.separator + ALRGConsts.REPORT_FILE_NAME, 
					sourceFilePath + File.separator +ALRGConsts.REPORT_FILE_NAME, FileType.FILE);
			
            
			String inputFileName = System.getProperty(USER_DIR) + File.separator + AUTOMATION_OUTPUT+ File.separator + ALRGConsts.REPORT_FILE_NAME;
			String outputReportZipFilePath = System.getProperty(USER_DIR) + File.separator + AUTOMATION_OUTPUT+ File.separator +"emailableReport.zip";
			String outputLogZipFilePath = sourceFilePath+"_logs.zip";
			String outputScreenshotZipFilePath = sourceFilePath+ "_screenShots.zip";
						
			ALRGLogger.log(_log, "Zip the custom-report.html.");
			CommonUtility.zipFile(new File(inputFileName), outputReportZipFilePath);
			
			ALRGLogger.log(_log, "Zip the AutomationOutput Folder for logs.");
			CommonUtility.zipFolder(new File(sourceFilePath), "", outputLogZipFilePath);
			
			File screenShotPath = new File(sourceFilePath + File.separator + "Screenshots");
			if(screenShotPath.exists()){
				ALRGLogger.log(_log, "Zip the AutomationOutput Folder for Screenshots.");
				CommonUtility.zipFolder(new File(sourceFilePath + File.separator + "Screenshots"), "", outputScreenshotZipFilePath);				
			}
			
			ALRGLogger.log(_log, "Copy the Zip files at shared folder.");

			CommonUtility.copyFileToNetworkLocation(FrameworkConsts.EMUSERDOMAIN, FrameworkConsts.EMNETWORKUSERNAME, 
			        CommonUtility.decryptData(FrameworkConsts.EMNETWORKUSERPASSWORD), outputReportZipFilePath, destinationFilePath, "emailableReport.zip");
			CommonUtility.copyFileToNetworkLocation(FrameworkConsts.EMUSERDOMAIN, FrameworkConsts.EMNETWORKUSERNAME, 
			        CommonUtility.decryptData(FrameworkConsts.EMNETWORKUSERPASSWORD), outputLogZipFilePath, destinationFilePath, timeStamp + "_logs.zip");
			if(screenShotPath.exists()){
				CommonUtility.copyFileToNetworkLocation(FrameworkConsts.EMUSERDOMAIN, FrameworkConsts.EMNETWORKUSERNAME, 
				        CommonUtility.decryptData(FrameworkConsts.EMNETWORKUSERPASSWORD), outputScreenshotZipFilePath, destinationFilePath, timeStamp + "_screenShots.zip");
			}
		}
		catch(Exception e){
			ALRGLogger.log(_log, "Failed to copy AutomationOutput Folder at specified Path. Exception: "+e.getMessage());
		}
		finally {
			IOUtils.closeQuietly(fileWriter);
		}
	}
	/**
	 * <b>Name:</b> getTestCasesForCriteria
	 * <b>Description:</b> Method to get test cases matching the given criteria.
	 * 
	 * @param argumentKeyValue The input argument Map collection.
	 * @param additionalDataFilePath The Data file path.
	 * @param strTestClassInfoQuery The Test class information retrieving query.
	 * @param strTestMethodInfoQuery The Test method information retrieving query.
	 * @return testClasses The List of XmlClass instances , that is, test cases to be run.
	 */
	protected List<XmlClass> getTestCasesForCriteria(Map<String, String> argumentKeyValue,
			String additionalDataFilePath, String strTestClassInfoQuery, String strTestMethodInfoQuery) {
		
		List<XmlClass> testClasses = new ArrayList<>();
		List<String> testClassesName = new ArrayList<String>();
		Recordset recordSetTestMethodInfo=null;
		XmlClass testClass;
		List<XmlInclude> includes;
		Fillo.Connection conn = null;
		Recordset recordSetTestClassInfo= null;

		try {
			ALRGLogger.log(_log, "Query to fetch ClassName(s): "+strTestClassInfoQuery, EllieMaeLogLevel.reporter);
			/** Gets the Fillo connection instance. */
			conn = CommonUtility.getConnectionUsingFillo(additionalDataFilePath);
			
			/** Fetches records matching the criteria. */
			recordSetTestClassInfo = CommonUtility.getRecordSetUsingFilloConnection(conn, strTestClassInfoQuery);
			ALRGLogger.log(_log, "Number of records for TestClassInfo: "+(recordSetTestClassInfo==null?0:recordSetTestClassInfo.getCount()), EllieMaeLogLevel.reporter);
			
			ALRGLogger.log(_log,"---------------------------------------------------------------");
			ALRGLogger.log(_log, "Priority requested			: "+argumentKeyValue.get(PRIORITY), EllieMaeLogLevel.reporter);
			ALRGLogger.log(_log, "Layer requested				: "+argumentKeyValue.get(LAYER), EllieMaeLogLevel.reporter);
			ALRGLogger.log(_log, "Component requested			: "+argumentKeyValue.get(COMPONENT), EllieMaeLogLevel.reporter);
			ALRGLogger.log(_log, "DeviceGroup requested		: "+argumentKeyValue.get(DEVICE_GROUP), EllieMaeLogLevel.reporter);
			ALRGLogger.log(_log, "TestingType requested		: "+argumentKeyValue.get(TESTING_TYPE), EllieMaeLogLevel.reporter);
			ALRGLogger.log(_log, "TestingVersion requested     : "+argumentKeyValue.get(TESTING_VERSION), EllieMaeLogLevel.reporter);
			ALRGLogger.log(_log,"---------------------------------------------------------------");
			
			while(recordSetTestClassInfo.next()) {
				/** Filter the recordsets not matching the criteria. */
				if(StringUtils.isNotBlank(recordSetTestClassInfo.getField(CLASS_NAME)) 
				      && metExecutionCriteria(argumentKeyValue, recordSetTestClassInfo) ) {     
             
					testClassesName.add(recordSetTestClassInfo.getField(CLASS_NAME));
				}
			}
			
			testClassesName = new ArrayList<String>(new LinkedHashSet<String>(testClassesName));			
			Collections.sort(testClassesName);
			
			int totalTestMethodsinExcel=0;
			int countTestMethods=0;
			// Loop through testClassesName.
			for(String testClassName: testClassesName) {
			    testClass = new XmlClass();
			    testClass.setName(testClassName.trim());
			    
			    String q = strTestMethodInfoQuery.replace(CHAR_$$, testClassName.trim());
			    //ALRGLogger.log(_log, "Query to fetch TestMethodName(s): "+q, EllieMaeLogLevel.debug);
			   
			    /** Fetches records matching the criteria. */
			    recordSetTestMethodInfo = CommonUtility.getRecordSetUsingFilloConnection(conn, q);
			    includes = new ArrayList<XmlInclude>();
			    
			    if( recordSetTestMethodInfo != null ) {
    			    while(recordSetTestMethodInfo.next()) {
    			    	/** Filter the recordsets not matching the criteria. */
    			    	  if(StringUtils.isNotBlank(recordSetTestMethodInfo.getField(TEST_METHOD_NAME)) 
    			    			 && metExecutionCriteria(argumentKeyValue, recordSetTestMethodInfo) ) {	
    
    			    		includes.add (new XmlInclude (recordSetTestMethodInfo.getField(TEST_METHOD_NAME).trim()));
    						//ALRGLogger.log(_log, "TestMethodName Included: "+recordSetTestMethodInfo.getField(TEST_METHOD_NAME).trim(), EllieMaeLogLevel.debug);
    						totalTestMethodsinExcel++;
    					}
    			    }
			    } else {
			        ALRGLogger.log(_log, "No methods found for Query: "+q, EllieMaeLogLevel.reporter);
			    }
			    
			    includes = new ArrayList<XmlInclude>(new LinkedHashSet<XmlInclude>(includes));
			    
			    testClass.setIncludedMethods(includes);
			    testClasses.add(testClass);
			    
			    countTestMethods+= includes.size();
			}

			ALRGLogger.log(_log, "Number of test methods (as per filter criteria) in Excel: "+totalTestMethodsinExcel);		    
		    ALRGLogger.log(_log, "Number of duplicate test methods (as per filter criteria) in Excel: "+(totalTestMethodsinExcel - countTestMethods));
		    ALRGLogger.log(_log, "Number of test methods (as per filter criteria) in TestNG xml (after removing duplicates): "+countTestMethods);		    
		}
		catch(Exception ex) {
			ALRGLogger.log(_log,"Exception while reading data from TestNG_XMLData.xlsx : (Check data in excel with respect to filter criteria requested.)"+ex.getMessage());
			ex.printStackTrace();
		}
		finally {
			if(null != conn) {
				conn.close();
			}
		}
		
		return testClasses;
	}		
	
	/**
	 * <b>Name:</b> generateXMLfile
	 * <b>Description:</b> Method to generate the testNG.xml.
	 * 
	 * @param suiteInfo The SuiteInfo collection.
	 * @param suiteParameter The suite parameter-value collection.
	 * @param testNGParams The testNG parameter-value collection.
	 * @param testClasses The List of XMLClasses i.e. test classes.
	 */
	public void generateXMLfile(Map<String, String> suiteInfo, Map<String, String> suiteParameter, Map<String,Map<String,String>> testNGParams,List<XmlClass> testClasses) {
	    
		Map<String, String> parameters;
		XmlTest test;
		//Create an instance on TestNG
		TestNG myTestNG = new TestNG();
		XmlSuite suite = new XmlSuite ();		
		
		suite.setName(StringUtils.isBlank(suiteInfo.get(XML_SUITE_NAME)) ? "Suite": suiteInfo.get(XML_SUITE_NAME));
	    suite.setAllowReturnValues(StringUtils.isBlank(suiteInfo.get(XML_ALLOW_RETURN_VALUES)) ? false: Boolean.parseBoolean(suiteInfo.get(XML_ALLOW_RETURN_VALUES)));
	    suite.setParallel(StringUtils.isBlank(suiteInfo.get(XML_SUITE_PARALLEL_MODE)) 
	            ? XmlSuite.ParallelMode.FALSE : XmlSuite.ParallelMode.getValidParallel(suiteInfo.get(XML_SUITE_PARALLEL_MODE)));

	    if(suite.getParallel() != XmlSuite.ParallelMode.FALSE && suite.getParallel() != XmlSuite.ParallelMode.NONE)
    		suite.setThreadCount(StringUtils.isBlank(suiteInfo.get(XML_SUITE_THREAD_COUNT)) ? 1 : Integer.parseInt(suiteInfo.get(XML_SUITE_THREAD_COUNT)));
	    
	    List<String> listeners = new LinkedList<>();
	    listeners.add("com.elliemae.core.listener.RetryListener");
	    listeners.add("com.elliemae.core.listener.PostTestListener");
	    listeners.add("com.elliemae.alrg.listener.TestNGCustomReportListener");
	    
	    suite.setListeners(listeners);	    
	    suite.setParameters(suiteParameter);
	    
	    for(Map.Entry<String, Map<String,String>> entry : testNGParams.entrySet()){
	    	test = new XmlTest (suite);
	    	//test.setVerbose(10); //[Uncomment this flag only in case of debugging because this setting generates a lot of logging resulting in slowing the jobs]
		    test.setName (entry.getKey());
		    test.setPreserveOrder ("true");
		    		    
		    parameters = new HashMap<>();
	    	parameters= entry.getValue();
			test.setParameters(parameters);
	    
			test.setXmlClasses (testClasses);
	    }
	    	    
	    ALRGLogger.log(_log,"Printing TestNG Suite Xml", EllieMaeLogLevel.reporter);
	    ALRGLogger.log(_log, "\n"+suite.toXml(), EllieMaeLogLevel.reporter);
	    
	    //Add the suite to the list of suites.
	    List<XmlSuite> mySuites = new ArrayList<XmlSuite>();
	    mySuites.add(suite);
	    
	    //Set the list of Suites to the testNG object you created earlier.
	    myTestNG.setXmlSuites(mySuites);
	    
	    //Set OutputFolder Path for TestNG test-output files
	    String finalPath = System.getProperty(USER_DIR) + File.separator + AUTOMATION_OUTPUT + File.separator + timeStamp + File.separator + "TestNG_Reports";
	    myTestNG.setOutputDirectory(finalPath);
	    
	    //invoke run() - this will run your class.
	    ALRGLogger.log(_log,"Run TestNG xml created");
	    myTestNG.run();
	}	
	
	
	private boolean metExecutionCriteria(Map<String, String> argumentKeyValue, Recordset recordSet) {
       
		//component 
        if ( executeForTestVersion(argumentKeyValue.get(TESTING_VERSION), recordSet) 
        		&& executeForComponent(argumentKeyValue.get(INCLUDED_COMPONENT), 
        								argumentKeyValue.get(EXCLUDED_COMPONENT), recordSet) ) {         
            return true;
        }
	    return false;
	}
	

	private boolean executeForTestVersion(String argTestingVersion, Recordset recordSet) {
	    
	    boolean toExecute = true;
	    String flag = "";
	    if( "all".equalsIgnoreCase(argTestingVersion) ) {
	        return toExecute; 
	    }
	    if( argTestingVersion != null && StringUtils.isNotEmpty(argTestingVersion)) {
	        try {
                flag = recordSet.getField(argTestingVersion);
            } catch (FilloException e) {
                flag = "no";
            }
	        if( "no".equalsIgnoreCase(flag)) {	    
	            toExecute = false; 
	        }
    	}
	    return toExecute;
	}
	
	private boolean executeForComponent(String includedComponent, String excludedComponent, Recordset recordSet) {
			
	     String componentName = "";  
	      try {
	          componentName = recordSet.getField(COLUMN_COMPONENT);
	      } catch(FilloException e) {}
	      
    	  boolean isMatch = false;
    	  componentName = componentName.toLowerCase();
    	  if(ALL.equalsIgnoreCase(includedComponent)) {
    	    return true;
    	  }
    	  if( StringUtils.isBlank(componentName) || 
    		  ( StringUtils.isBlank(includedComponent) && StringUtils.isNotEmpty(excludedComponent) && !excludedComponent.toLowerCase().contains(componentName))  ) {
    		  isMatch = true;
    	  }		    
          if(StringUtils.isNotBlank(componentName)) { 
      	    if ( StringUtils.isNotEmpty(includedComponent) ) {
      	      //for included component, check for regular expression 
      	      String[] includedArr = includedComponent.split(CHAR_COMMA);
      	      for(String v : includedArr ) {
      	        if(v.toLowerCase().matches(componentName) ) {
      	          isMatch = true;
      	          break;
      	        }
      	      }
            }
    	  }
    	  return isMatch;
	}

	
	private static Recordset getDriverInfoRecordSet(String additionalDataFilePath, String driverInfoDataFilePath) {
	    
	    Recordset recordSetDriverInfo;
	    File f = new File(driverInfoDataFilePath);
        String strDriverInfoQuery="Select * from DriverInfo where ExecuteDevice= 'yes'";   
	    if( f.exists() ) {  
	        ALRGLogger.log(_log, "Query DriverInfo sheet to fetch driver details:" + driverInfoDataFilePath);
	        recordSetDriverInfo = CommonUtility.getRecordSetUsingFillo(driverInfoDataFilePath,strDriverInfoQuery);
	    } else {
	        /** Create TestNG parameters collection. */
	        ALRGLogger.log(_log, "Query DriverInfo sheet to fetch driver details:" + additionalDataFilePath);
	        recordSetDriverInfo = CommonUtility.getRecordSetUsingFillo(additionalDataFilePath,strDriverInfoQuery); 	        
	    }   
        ALRGLogger.log(_log, "DriverInfo data retrieved");    	    
        return recordSetDriverInfo;
	}
	
	//Updated for TestObject
	// Sauce Connect : Create VPN Connection
	static void beforeSCSuite(String userName,String apiKey, String logFile, String port) {
		if(StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(apiKey)) {
		    ALRGLogger.log(_log, "Starting Sauce Connect");
			try{
				String temp = FrameworkConsts.BROWSEREXE_PATH + "/sc.exe";
				scPb = new ProcessBuilder(FrameworkConsts.BROWSEREXE_PATH + "/sc.exe","-u " + userName,"-k "+ apiKey,"-N no-proxy-caching","-l " + logFile,"-X " + port);
				scProc = scPb.start();
				ALRGLogger.log(_log, "Started Sauce Connect EXE");
				isScStarted = true;
			}
			catch (Exception ex){
				ex.printStackTrace();
			}
			ALRGLogger.log(_log, "Sauce Connect Running");
		}
	}

	// Sauce Connect : Terminate VPN Connection
	static void afterSCSuite(){
	    if (isScStarted)	{
	        System.out.println("Stopping Sauce Connect");
	        scProc.destroy();
	        System.out.println("Sauce Connect Stopped");
	    }
	}

}