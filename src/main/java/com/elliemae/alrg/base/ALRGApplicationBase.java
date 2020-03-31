package com.elliemae.alrg.base;

import com.elliemae.consts.FrameworkConsts;
import com.elliemae.core.Base.EllieMaeBase;
import com.elliemae.core.Logger.EllieMaeLog;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;
import com.elliemae.core.Utils.ApplitoolsUtils;
import com.elliemae.core.Utils.CommonUtility;
import com.elliemae.core.Utils.ExcelParser;
import com.elliemae.core.asserts.Assert;
import com.elliemae.core.asserts.Assert.STATUS;
import com.elliemae.core.asserts.SoftAssert;
import com.elliemae.alrg.consts.ALRGConsts;
import com.elliemae.alrg.utils.CommonUtilityApplication;
import com.elliemae.alrg.utils.DateHelper;
import com.elliemae.alrg.utils.ALRGLogger;
import com.elliemae.alrg.utils.LogHelper;
import com.elliemae.alrg.utils.StringHelper;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

/**
 * <b>Name:</b> EllieMaeApplicationBase <b>Description: </b>This class is
 * extending EllieMaeBase class and is used to create driver and read from data
 * provider.
 */

public class ALRGApplicationBase extends EllieMaeBase {

	public static Logger _log = Logger.getLogger(ALRGApplicationBase.class);
	public static final String LOG_START = "start";
	public static final String LOG_END = "end";

	protected static String RUNNING_DEVICE_MODE = "";
	
	protected String browserName;
	protected String platform;
	protected String videoRecording;
	
    private static final String TEST_CYCLE_VERSION = "testCycleVersion";
    private static final String UPDATE_TEST_STATUS_TO_JIRA = "updateTestStatusToJIRA";
    private static final String JIRA_PROJECT_NAME = "jiraProjectName";	
    private static boolean isUpdateTestStatusToJIRA;
    private static String jiraUserName = "";
    private static String jiraPassword = "";
    
    private String logFolderName = CommonUtility.currentTimeStamp;
    
	@Override
	@BeforeClass(alwaysRun = true)
	public void setUpClass(ITestContext context) throws Exception {
	  
        _log = Logger.getLogger(CommonUtility.getCurrentClassAndMethodNameForLogger());
        MDC.put("testClassName", this.getClass().getSimpleName());          
        MDC.put("threadIDForClass", "ClassThreadID_" + String.valueOf(Thread.currentThread().getId()));
        
        EllieMaeLog.log(_log, "------------------------------------------------------------");
        EllieMaeLog.log(_log, "*********************setUpClass()*********************");
        
        platform = (context.getCurrentXmlTest().getParameter("platform") == null ? ""
                : context.getCurrentXmlTest().getParameter("platform"));
        browserName = (context.getCurrentXmlTest().getParameter("browserName") == null ? ""
                : context.getCurrentXmlTest().getParameter("browserName"));
        videoRecording = (context.getCurrentXmlTest().getParameter("VideoRecording") == null ? ""
                : context.getCurrentXmlTest().getParameter("VideoRecording"));
        if (videoRecording == null) {
            videoRecording = "failcases";
        }      
        
/*        FrameworkConsts.LOANFOLDER = (context.getCurrentXmlTest().getParameter("loanFolder") == null ? ""
                : context.getCurrentXmlTest().getParameter("loanFolder"));*/
        FrameworkConsts.JENKINSJOBNAME = (context.getCurrentXmlTest().getParameter("jenkinsJobName") == null ? ""
                : context.getCurrentXmlTest().getParameter("jenkinsJobName"));
        FrameworkConsts.CURRENTBROWSERNAME = browserName;
        FrameworkConsts.ENVIRONMENTNAME = (context.getCurrentXmlTest().getParameter("environmentName") == null ? ""
                : context.getCurrentXmlTest().getParameter("environmentName"));
        FrameworkConsts.ENVIRONMENTUSERNAME = (context.getCurrentXmlTest().getParameter("environmentUserName") == null
                ? "" : context.getCurrentXmlTest().getParameter("environmentUserName"));
        FrameworkConsts.ENVIRONMENTPASSWORD = (context.getCurrentXmlTest().getParameter("environmentPassword") == null
                ? "" : context.getCurrentXmlTest().getParameter("environmentPassword"));
/*        FrameworkConsts.ENVIRONMENTCLIENTID = (context.getCurrentXmlTest().getParameter("environmentClientID") == null
                ? "" : context.getCurrentXmlTest().getParameter("environmentClientID"));*/
        FrameworkConsts.TESTOBJECTACCESSCODE = (context.getCurrentXmlTest().getParameter("testObjectAccessCode") == null
                ? "" : context.getCurrentXmlTest().getParameter("testObjectAccessCode"));
        
		//customization for dynamic url
		ALRGConsts.ENVIRONMENT_URL = (context.getCurrentXmlTest().getParameter(ALRGConsts.ENVIRONMENT_URL_PARAM) == null ? ""
				: context.getCurrentXmlTest().getParameter(ALRGConsts.ENVIRONMENT_URL_PARAM));
		
		//check mode parameter
		ALRGConsts.RUNNING_DEVICE_MODE = (context.getCurrentXmlTest().getParameter("runningDeviceMode") == null ? ""
            : context.getCurrentXmlTest().getParameter("runningDeviceMode"));
		
		ALRGConsts.RUNNING_DEVICE_NAME = (context.getCurrentXmlTest().getParameter("deviceName") == null ? ""
				 : context.getCurrentXmlTest().getParameter("deviceName"));
		
        // ######################################################################
        EllieMaeLog.log(_log, "*********************Before Class End*********************");		
	}
	
	
	public void createDriver(ITestContext context) throws Exception {


        String deviceUserName = context.getCurrentXmlTest().getParameter("deviceUserName");
        String version = context.getCurrentXmlTest().getParameter("version");
        String deviceName = context.getCurrentXmlTest().getParameter("deviceName");
        String deviceOrientation = context.getCurrentXmlTest().getParameter("deviceOrientation");
        String browserVersion = context.getCurrentXmlTest().getParameter("BrowserVersion");
        String osName = context.getCurrentXmlTest().getParameter("OSName");

        String port = context.getCurrentXmlTest().getParameter("port");
        String deviceType = context.getCurrentXmlTest().getParameter("deviceType");
        String appPath = context.getCurrentXmlTest().getParameter("appPath");
        
        if (platform.equalsIgnoreCase("Web") || platform.equalsIgnoreCase("APIWEB")) {
            EllieMaeLog.log(_log, "Creating driver for Web browser");
            driver = super.createDriver(browserName);

            EllieMaeLog.log(_log, "**************************Environment Details**************************");
            EllieMaeLog.log(_log, "TestNG TestName: " + context.getCurrentXmlTest().getName());
            EllieMaeLog.log(_log, "Platform: " + platform);
            EllieMaeLog.log(_log, "Browser Name: " + browserName);
            EllieMaeLog.log(_log, "*************************************************************************");
        } else if (platform.equalsIgnoreCase("API")) {
            EllieMaeLog.log(_log, "Running API test");

        } else if (platform.equalsIgnoreCase("Android") || platform.equalsIgnoreCase("ios")) {
            EllieMaeLog.log(_log, "Creating driver for Mobile");
            if (deviceType.equalsIgnoreCase("TestObjectAPP")) {
                driver = createDriver(platform, version, deviceName, deviceOrientation, port, deviceType, appPath);
            } else {
                driver = createDriver(deviceUserName, platform, version, deviceName, deviceOrientation, browserName, port, deviceType);
            }

            EllieMaeLog.log(_log, "**************************Environment Details**************************");
            EllieMaeLog.log(_log, "TestNG TestName: " + context.getCurrentXmlTest().getName());
            EllieMaeLog.log(_log, "Environment Name: " + deviceUserName);
            EllieMaeLog.log(_log, "Platform:" + platform);
            EllieMaeLog.log(_log, "Version:" + version);
            EllieMaeLog.log(_log, "Device Name:" + deviceName);
            EllieMaeLog.log(_log, "Device Orientation: " + deviceOrientation);
            EllieMaeLog.log(_log, "Browser Name: " + browserName);
            // EllieMaeLog.log(_log,"Appium port: "+port);
            EllieMaeLog.log(_log, "Device Type: " + deviceType);
            EllieMaeLog.log(_log, "*************************************************************************");
        } else if (platform.equalsIgnoreCase("VM")) {
            EllieMaeLog.log(_log, "Creating driver for VM Web browser");
            driver = createDriverForVM(browserName, browserVersion, osName, className);

            EllieMaeLog.log(_log, "**************************Environment Details**************************");
            EllieMaeLog.log(_log, "TestNG TestName: " + context.getCurrentXmlTest().getName());
            EllieMaeLog.log(_log, "Environment Name: " + deviceUserName);
            EllieMaeLog.log(_log, "Platform:" + platform);
            EllieMaeLog.log(_log, "Browser Name: " + browserName);
            EllieMaeLog.log(_log, "Browser Version:" + browserVersion);
            EllieMaeLog.log(_log, "OS Name:" + deviceName);
            EllieMaeLog.log(_log, "*************************************************************************");
        } else {
            skipTest = true;
            EllieMaeLog.log(_log, "Incorrect or incomplete value in platform column in TestNG_XMLData excel file",
                    EllieMaeLogLevel.reporter);
        }	    
	}
	
	public void closeDriver(ITestContext context) {
	    
        if (browserName.equalsIgnoreCase("IE") || browserName.equalsIgnoreCase("FIREFOX")) {
            try {
                EllieMaeLog.log(_log, "Closing IE/FIREFOX browser");
                CommonUtility.closeBrowser(browserName); // Special case
            } catch (Exception e) {
                EllieMaeLog.log(_log, "Failed to close IE browser. Exception: " + e.getMessage());
                StringWriter stackTrace = new StringWriter();
                e.printStackTrace(new PrintWriter(stackTrace));
                EllieMaeLog.log(_log, "Exception StackTrace: " + stackTrace.toString());
            }
        } else if (driver != null) {
            try {
                EllieMaeLog.log(_log, "Closing Webdriver");
                this.driver.close();
            } catch (Exception e) {
                EllieMaeLog.log(_log, "Failed to close webDriver. Exception: " + e.getMessage());
                StringWriter stackTrace = new StringWriter();
                e.printStackTrace(new PrintWriter(stackTrace));
                EllieMaeLog.log(_log, "Exception StackTrace: " + stackTrace.toString());
            } finally {
                try {
                    EllieMaeLog.log(_log, "Quit Driver" + driver.toString());
                    this.driver.quit(); // It is used to shut down the web
                                        // driver instance or destroy the
                                        // web driver instance(Close all the
                                        // windows)
                } catch (Exception ex) {
                    EllieMaeLog.log(_log, "Warning: Browser closed but having issue with Driver quit.");
                }

            }
        }	    
	}
	
    @Override
    public void setUpMethod() {
        ALRGLogger.log(_log, "Override super class setupMethod()", EllieMaeLogLevel.reporter);
    }
    
	@BeforeMethod (alwaysRun = true)
	public void setUpMethod(ITestContext context) throws Exception {    

        _log = Logger.getLogger(CommonUtility.getCurrentClassAndMethodNameForLogger());
        
        MDC.put("testClassName", this.getClass().getSimpleName());          
        MDC.put("testMethodName", testMethodName);
        FrameworkConsts.TESTCASENAME.set(testMethodName);
        
        EllieMaeLog.log(_log, "#############################################################");
        EllieMaeLog.log(_log, "*********************setUpMethod()*********************");
        
        // Advanced Report**********************
        try {
            System.out.println("Class Name --> " + className + "_" + testMethodName);
            FrameworkConsts.test.putIfAbsent(className + "_" + testMethodName,
                    FrameworkConsts.extent.createTest(className + "_" + testMethodName));
            EllieMaeLog.setLogger(FrameworkConsts.test.get(className + "_" + testMethodName));
            FrameworkConsts.test.get(className + "_" + testMethodName).assignAuthor("ATF------->");
            // *********************************************************************************************************

        } catch (Exception e) {
            e.printStackTrace();
        }

        testStatus = new ConcurrentHashMap<>();
        sAssert = new SoftAssert(testStatus);
        Assert.setTestStatus(testStatus);

        EllieMaeLog.log(_log, "*********************Before Method End*********************");
        FrameworkConsts.isEnvDetailsLogged.set(false);
        if (skipTest) {
            throw new SkipException("Incorrect or incomplete value in platform column in TestNG_XMLData excel file");
        }
		
		createDriver(context);	
		
		if (isRunningOnRealDevice()) {
			ALRGLogger.log(_log, "Device Name: " + ALRGConsts.RUNNING_DEVICE_NAME, EllieMaeLogLevel.reporter);
		}
		if( isRunningInWebMode() ) {			
            driver.manage().window().maximize();		
            ALRGLogger.log(_log, "Running in web mode, maximize window: " + MDC.get("testClassName") + "." + testMethodName, EllieMaeLogLevel.reporter);
            //ALRGLogger.log(_log, "Running in mobile mode, set tablet size for test: " + MDC.get("testClassName") + "." + testMethodName, EllieMaeLogLevel.reporter);
		} else {
 /*           Dimension dimension = new Dimension(1366, 1024);
            driver.manage().window().setSize(dimension);*/
            ALRGLogger.log(_log, "Running in mobile mode, set screen size for test: " + MDC.get("testClassName") + "." + testMethodName,
                    EllieMaeLogLevel.reporter);	    		    
		}
		//driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);		
	}

	@AfterMethod (alwaysRun = true)
	@Override
	public void tearDownMethod(ITestResult testResult, ITestContext context) { 
		
	    _log = Logger.getLogger(CommonUtility.getCurrentClassAndMethodNameForLogger());
	    EllieMaeLog.log(_log, "#############################################################");
	    EllieMaeLog.log(_log, "*********************tearDownMethod() Before Method Start *********************");
	    
		String currentValue;
		Set<Map.Entry<String, String>> testStatusEntries = testStatus.entrySet();
		for(Map.Entry<String, String> currTestStatusEntry: testStatusEntries) {
			if(null != currTestStatusEntry && StringUtils.isNotBlank(currTestStatusEntry.getKey())) {
				currentValue = StringUtils.isBlank(currTestStatusEntry.getValue())? STATUS.Skipped.toString(): currTestStatusEntry.getValue();
				ALRGLogger.log(_log, "************************************* Test Status: JIRA=" + currTestStatusEntry.getKey() 
				  + "\t Status=" + currentValue + "*********************************************", EllieMaeLogLevel.reporter);
				
				if( currentValue.equals(STATUS.Skipped.toString()) ) {
					if( testResult.isSuccess() ) {
					  ALRGLogger.log(_log, "************************************* Change Status of JIRA=" + currTestStatusEntry.getKey() 
					    + " from 'Skipped' to 'Success'*********************************************", EllieMaeLogLevel.reporter);
						testStatus.put(currTestStatusEntry.getKey(), STATUS.Success.toString());
					} /*else if( testResult.getStatus() == ITestResult.FAILURE ) {
						testStatus.put(currTestStatusEntry.getKey(), STATUS.Failure.toString());
					}*/
				}
			}
		}	
		
        try {
            if (null != testStatus && !testStatus.isEmpty()) {
                testStatusEntries = testStatus.entrySet();
            } else {
                if (null == testStatus) {
                    testStatus = new ConcurrentHashMap<>();
                }

                if (null != FrameworkConsts.JIRANUMBERTOUPDATE) {
                    if (testResult.getStatus() == ITestResult.FAILURE) {
                        testStatus.put(FrameworkConsts.JIRANUMBERTOUPDATE, STATUS.Failure.toString());
                    } else if (testResult.getStatus() == ITestResult.SUCCESS) {
                        testStatus.put(FrameworkConsts.JIRANUMBERTOUPDATE, STATUS.Success.toString());
                    } else {
                        testStatus.put(FrameworkConsts.JIRANUMBERTOUPDATE, STATUS.Skipped.toString());
                    }
                }
                testStatusEntries = testStatus.entrySet();
            }

            EllieMaeLog.log(_log, "isUpdateTestStatusToJIRA:" + isUpdateTestStatusToJIRA);

            for (Map.Entry<String, String> currTestStatusEntry : testStatusEntries) {
                if (null != currTestStatusEntry && StringUtils.isNotBlank(currTestStatusEntry.getKey())) {
                    currentValue = StringUtils.isBlank(currTestStatusEntry.getValue()) ? STATUS.Skipped.toString()
                            : currTestStatusEntry.getValue();
                    EllieMaeLog.log(_log,
                            "************************************* Test Status: JIRA=" + currTestStatusEntry.getKey()
                                    + "\t Status=" + currentValue + "*********************************************",
                            EllieMaeLogLevel.reporter);

                    if (isUpdateTestStatusToJIRA) {
                        if (null == allTestStatus) {
                            EllieMaeLog.log(_log,
                                    "************************ Following status not logged as 'allTestStatus' instance was null ************************************* Test Status: JIRA="
                                            + currTestStatusEntry.getKey() + "\t Status=" + currentValue
                                            + "*********************************************",
                                    EllieMaeLogLevel.reporter);
                        } else {
                            allTestStatus.put(currTestStatusEntry.getKey(), currentValue);
                        }
                    }
                }
            }
        } finally {
            if (null != testStatus) {
                testStatus.clear();
                testStatus = null;
            }
        }

        if (!platform.equalsIgnoreCase("API")) {
            try {
                if (testResult.getStatus() == ITestResult.FAILURE) {
                    EllieMaeLog.log(_log, "Taking screenshots for failure");
                    CommonUtility.takeScreenShot(this.driver,
                            className + "_" + testMethodName + "_" + context.getCurrentXmlTest().getName(),
                            logFolderName);
                } else {
                    EllieMaeLog.log(_log, "No test assertion failed, therefore, no screenshots");
                }
            } catch (Exception e) {
                EllieMaeLog.log(_log, "Failed to take Screenshot: " + e.getMessage());
                StringWriter stackTrace = new StringWriter();
                e.printStackTrace(new PrintWriter(stackTrace));
                EllieMaeLog.log(_log, "Exception StackTrace: " + stackTrace.toString());
            }
        }
        
        // ######################## Applitool SetUp #############################
        if (ApplitoolsUtils.eyes != null) {
            EllieMaeLog.log(_log, "Closing Applietool eyes", EllieMaeLogLevel.reporter);
            ApplitoolsUtils.eyes.close();
        }
        // #######################################################################
        FrameworkConsts.isEnvDetailsLogged.set(true);

        closeDriver(context);
        
        EllieMaeLog.log(_log, "***********************tearDownMethod() After Method End**********************");
        EllieMaeLog.log(_log, "#############################################################");
        MDC.remove("testMethodName");        
	}
	
    @Override
	public void tearDownClass() {
        ALRGLogger.log(_log, "Override super class tearDownClass()", EllieMaeLogLevel.reporter);
	}
	
	@AfterClass (alwaysRun = true)
	public void tearDownClass(ITestContext context) {
		
        _log = Logger.getLogger(CommonUtility.getCurrentClassAndMethodNameForLogger());
        EllieMaeLog.log(_log, "*********************tearDownClass() After Class Start*********************");
        if (skipTest) {
            EllieMaeLog.log(_log, "Incorrect or incomplete value in platform column in TestNG_XMLData excel file",
                    EllieMaeLogLevel.reporter);
        } 
        if (platform.equalsIgnoreCase("API")) {
            EllieMaeLog.log(_log, "End Running API Test");
        }

        EllieMaeLog.log(_log, "*********************tearDownClass() After Class End************************");
        EllieMaeLog.log(_log, "------------------------------------------------------------");        
		MDC.remove("testClassName");
	}
	
	
    /**
     * <b>Name:</b> tearDownTest <b>Description:</b> Method to perform tasks
     * prior to tear down of Test level. 
     * 
     */
    @AfterTest(alwaysRun = true)
    @Override
    public void tearDownTest() {
        try {
            _log = Logger.getLogger(CommonUtility.getCurrentClassAndMethodNameForLogger());
            ALRGLogger.log(_log, "***********************After Test*************************");

            if (isUpdateTestStatusToJIRA) {
            	if( StringUtils.isEmpty(jiraUserName) || StringUtils.isEmpty(jiraPassword) ) {
            		ALRGLogger.log(_log, "ERROR: jiraUserName and jiraPassword not provided - JIRA test status NOT updated", EllieMaeLogLevel.reporter);
            	} else {
	                FrameworkConsts.TESTCASENAME.set("JIRA API CALL");
	                /**
	                 * Update the Jira(s) with the test status for the test automation run.
	                 */
	                updateJiraWithTestStatus();
            	}
            }
        } finally {
            skipTest = false;
            MDC.remove("threadIDForClass");
            MDC.remove("threadID");
            ALRGLogger.log(_log, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
    }
    
    @Override
    @BeforeTest(alwaysRun = true)
    public void setUpTest(ITestContext context) {
        
        super.setUpTest(context);
        
        //this is only needed because those variables are private in super class, and they are used in updateJiraWithTestStatus()
        //which we override
        if (StringUtils.isNotBlank(context.getCurrentXmlTest().getParameter(JIRA_PROJECT_NAME))) {
            jiraProjectName = context.getCurrentXmlTest().getParameter(JIRA_PROJECT_NAME).trim();
        }

        if (StringUtils.isNotBlank(context.getCurrentXmlTest().getParameter(TEST_CYCLE_VERSION))) {
            testCycleVersionName = context.getCurrentXmlTest().getParameter(TEST_CYCLE_VERSION).trim();
        }
        if (StringUtils.isBlank(context.getCurrentXmlTest().getParameter(UPDATE_TEST_STATUS_TO_JIRA)) || !"yes"
                .equalsIgnoreCase(context.getCurrentXmlTest().getParameter(UPDATE_TEST_STATUS_TO_JIRA).trim())) {
            isUpdateTestStatusToJIRA = false;
        } else {
            isUpdateTestStatusToJIRA = true;
        }      
        jiraUserName = context.getCurrentXmlTest().getParameter("jiraUserName");
        jiraPassword = context.getCurrentXmlTest().getParameter("jiraPassword");
    }
    
    
 /*   *//**
     * <b>Name:</b> updateJiraWithTestStatus <b>Description:</b> Method to
     * update the Jira(s) with the test status for the automation test run.
     *//*
    @Override
    protected void updateJiraWithTestStatus() {
           
        try {
            
            //List<String> listOfJiraProjectName = Arrays.asList(jiraProjectName.split(","));
            List<String> testCasesJiraProjectNames = uniqueJiraProjectNames();
            
            for (String singleJiraProjectName : testCasesJiraProjectNames) {

                if( !testCasesJiraProjectNames.contains(singleJiraProjectName) ) {
                    
                    ALRGLogger.log(_log, "########################## No Jira test cases for " + singleJiraProjectName
                            + "##############################", EllieMaeLogLevel.reporter, EllieMaeLogLevel.debug);                    
                    continue;
                }
                ALRGLogger.log(_log, "########################## Updating the Jira for " + singleJiraProjectName
                        + " ##############################", EllieMaeLogLevel.reporter, EllieMaeLogLevel.debug);

                long versionId = 0, testCycleId = -1;

                *//** Update Jira with test run statuses. *//*
                Set<String> jiraIds = allTestStatus.keySet();

                if (null == jiraIds || jiraIds.isEmpty()) {
                    ALRGLogger.log(_log,
                            "No Jira identifiers foound in the request hence skipping Jira status update activity.",
                            EllieMaeLogLevel.reporter);
                } else {

                    Validate.notNull(singleJiraProjectName,
                            "Jira Project Name can not be Null, Check the value of JiraProjectName provided in the arguments");
                    Validate.notEmpty(singleJiraProjectName,
                            "Jira Project Name can not be Empty, Check the value of JiraProjectName provided in the arguments");

                    *//** Flag to identify Jira work flow calls. *//*
                    FrameworkConsts.isJiraWorkflowCall = true;

                    FrameworkConsts.APIMETHODNAME.set("GET JIRA PROJECTID");
                    long projectId = getProjectId(singleJiraProjectName);

                    ALRGLogger.log(_log, singleJiraProjectName+ " has project id:" + projectId, EllieMaeLogLevel.reporter);                    
                    Validate.isTrue(0 < projectId,
                            "Invalid project identifier found in the request. Check the value of JiraProjectName provided in the arguments");

                    FrameworkConsts.APIMETHODNAME.set("GET JIRA ISSUEIDs");
                    Map<String, Long> issuesIds = getIssueIdsForJiraKeys(jiraIds);

                    Validate.notNull(issuesIds, "IssueIds object is null");
                    Validate.notEmpty(issuesIds, "IssueIds object is empty");

                    *//** Deduce the version identifier. *//*
                    if (StringUtils.isBlank(testCycleVersionName)
                            || "unscheduled".equalsIgnoreCase(testCycleVersionName)) {
                        versionId = -1;
                    } else {
                        FrameworkConsts.APIMETHODNAME.set("GET JIRA PROJECT VERSIONID");
                        versionId = getProjectVersionID(projectId, testCycleVersionName);
                    }

                    ALRGLogger.log(_log, testCycleVersionName+ " has version id:" + versionId, EllieMaeLogLevel.reporter);   
                    Validate.isTrue(((-1 == versionId) || (0 < versionId)),
                            "Invalid version identifier found in the request.");

                    *//** Create the test cycle for this automation run. *//*
                    FrameworkConsts.APIMETHODNAME.set("CREATE JIRA TESTCYCLE");
                    testCycleId = createTestCycle(singleJiraProjectName, projectId, versionId);
                    ALRGLogger.log(_log, "Testcycle id:" + testCycleId, EllieMaeLogLevel.reporter);   
                    Validate.isTrue((0 < testCycleId), "Invalid test cycle identifier found in the request.");

                    *//**
                     * Link the test Jira(s) with the test cycle for this
                     * automation run.
                     *//*
                    FrameworkConsts.APIMETHODNAME.set("ADD TESTS TO JIRA CYCLE");
                    ALRGLogger.log(_log, "Add JIRAs to test cycle.  JiraId=" + jiraIds.toString() 
                        + ", project="+singleJiraProjectName + ", version="+testCycleVersionName + ", testCycleId=" + testCycleId, EllieMaeLogLevel.reporter); 
                    linkTestsToCycle(jiraIds, projectId, versionId, testCycleId);

                    Thread.sleep(240000);

                    *//** Add execution entry for the tests in test cycle. **//*
                    FrameworkConsts.APIMETHODNAME.set("ADD JIRA ISSUE EXECUTIONIDS");
                    Map<String, Long> jiraIssueExecutionIds = addExecutionEntryInTestCycle(issuesIds, versionId,
                            testCycleId, projectId);
                    if( jiraIssueExecutionIds != null && !jiraIssueExecutionIds.isEmpty() ) {
                        *//**
                         * Update execution entry status for tests with their run
                         * status.
                         *//*
                        FrameworkConsts.APIMETHODNAME.set("UPDATE JIRA TEST STATUS");
                        updateExecutionStatus(allTestStatus, jiraIssueExecutionIds);
                    } else {
                        ALRGLogger.log(_log, "JiraIssueExecutionIds is null or empty.  issuesIds=" + issuesIds.toString() 
                        + ", project="+singleJiraProjectName + ", version="+testCycleVersionName + ", testCycleId=" + testCycleId, EllieMaeLogLevel.reporter);                         
                    }
                }
            }

        } catch (Exception ex) {
            ALRGLogger.log(_log, "Exception occurred in updateJiraWithTestStatus(). Exception: " + ex.getMessage(),
                    EllieMaeLogLevel.error, EllieMaeLogLevel.reporter);
            ex.printStackTrace();
        } finally {
            if (null != allTestStatus) {
                allTestStatus.clear();
                allTestStatus = null;
            }

            FrameworkConsts.isJiraWorkflowCall = false;
        }
    }*/
    
    public List<String> uniqueJiraProjectNames() {
        
        List<String> pNames = new ArrayList<String>();
        Set<String> jiraIds = allTestStatus.keySet();
        String prefix;
        for(String jId : jiraIds) {
            if( jId.indexOf("-") >0 ) {
                prefix = jId.substring(0, jId.indexOf("-"));
                if( !pNames.contains(prefix) ) {
                    pNames.add(prefix);
                }
            } else {
                ALRGLogger.log(_log, "Invalid JIRA ID="+jId, EllieMaeLogLevel.reporter, EllieMaeLogLevel.error);
            }
        }
        return pNames;
    }
    
	/**
	 * <b>Name: </b>LogToReporterStartEnd<br>
	 * <b>Description: </b>This method is used to set output start/end time and
	 * message to the report</br>
	 * 
	 * <b>Example:</b> <code>LogToReporterStartEnd("start","Start: JIRA_# Description of the test",EllieMaeLogLevel.reporter);</code>
	 * 
	 * @param startEnd
	 * @param message
	 * @param logLevel 
	 */
	public static void LogToReporterStartEnd(String startEnd, String message, EllieMaeLogLevel logLevel) {
		switch (startEnd.toLowerCase()) {
		case LOG_START:
			ALRGLogger.log(_log, "**************************************************", logLevel);
			
			//ALRGLogger.log(_log,
					//"Start DateTime: " + CommonUtilityApplication.getCurrentDateTimeString("yyyy-MM-dd HH:mm:ss"),
				//	logLevel);
			ALRGLogger.log(_log, "Start: " + message, logLevel);
			
			break;
		case LOG_END:
			ALRGLogger.log(_log,
					"End DateTime: " + CommonUtilityApplication.getCurrentDateTimeString("yyyy-MM-dd HH:mm:ss"),
					logLevel);
			ALRGLogger.log(_log, "**************************************************");
			ALRGLogger.log(_log, "End: " + message, logLevel);
			break;
		default:
			break;
		}
	}
	
	/**
	 *  <b>Name: </b>getMethodName</br>
	 *  <b>Description: </b>Get the method name for a depth in call stack</br>
	 *
	 * @param depth - in the call stack (0 means current method, 1 means call method, ...)
	 * @return method name
	 * 
	 * 
	 */
	public String getMethodName(final int depth) {
		  final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		  return ste[2+ depth].getMethodName();
	}
	
	public void takeScreenShot(String desc) {
      try { 
        String fileName = className + "_" + testMethodName+"_"+ MDC.get("threadID")+"_"+desc;
        String timestamp = System.getProperty("logfolder");
        CommonUtility.takeScreenShot(this.driver, fileName,timestamp);             
      }
      catch (Exception e) {
          ALRGLogger.log(_log, "Failed to take Screenshot: " + e.getMessage());  
          StringWriter stackTrace = new StringWriter();
          e.printStackTrace(new PrintWriter(stackTrace));
          ALRGLogger.log(_log, "Exception StackTrace: "+stackTrace.toString());
      }
	}
	
	
	/**
	 * handle test exception including taking a screenshot, logging the error, and fail the test
	 * @param _log
	 * @param testData
	 * @param e
	 */
	public void handleException(Logger _log, HashMap<String, String> testData, Throwable e) {
	  
	  takeScreenShot(getClassName(e.getClass()));
      LogHelper.logTestError(_log, e);      
      Assert.fail(testData.get(ALRGConsts.TESTDATA_TEST_CASE_DESCRIPTION) + " failed:" + e.getMessage(), e);	  
	}
	
	public boolean isRunningInTabletMode() {
		return ( "tablet".equalsIgnoreCase(ALRGConsts.RUNNING_DEVICE_MODE));	
	}
	
    public boolean isRunningInWebMode() {
        return ( className.startsWith("Desktop") );    
    }
    
	public boolean isRunningOnRealDevice() {
	    return StringUtils.isNotBlank(ALRGConsts.RUNNING_DEVICE_NAME);
	}
	

    /**
     * <b>Name:</b> getAllTestData <b>Description:</b> Data provider method to
     * get all test data for the Test method.
     * 
     * @param testMethod
     *            The method instance.
     */
	@Override
    protected void prepareForGetDataProvider(Method testMethod) {
        EllieMaeLog.log(_log, "Test Method to be executed: " + testMethod.getName());
        testMethodName = testMethod.getName();

        resourcesFolder = resourceFolder();
        if (resourcesFolder.equalsIgnoreCase("testcases")) {
            EllieMaeLog.log(_log, "Resources Folder: " + resourcesFolder);
            EllieMaeLog.log(_log,
                    "Make sure test class follows correct package naming convention : com.elliemae.testcases.Component.SampleTest.java");
            resourcesFolder = "";
            FrameworkConsts.tlResourceFolder.set(resourcesFolder);
        } else {
            EllieMaeLog.log(_log, "Resources Folder: " + resourcesFolder);
            //EMATF's CommonUtility getRelativeFilePath() is used by EBS API, 
            //and it uses FrameworkConsts.tlResourceFolder and prefix it with hard coded com/elliemae 
            int idx = resourcesFolder.lastIndexOf("elliemae")+9;
            FrameworkConsts.tlResourceFolder.set(resourcesFolder.substring(idx));
        }
    }

    
	protected String resourceFolder() {
		
		String fullClassName = this.getClass().getName();
		String rFolder =  fullClassName.substring(0, fullClassName.lastIndexOf("."));
		//remove package name "testcases" if exists
		rFolder = rFolder.replace(".testcases", "");		
		rFolder = rFolder.replace(".", File.separator);	
		
		return rFolder;
	}
	
	protected String dataFolder() {
				
		File f = new File("");
		String dataFolder = f.getAbsolutePath().replace("\\", File.separator);
		dataFolder = dataFolder + File.separator + "src" + File.separator + "test" + File.separator + "resources"
				+ File.separator + resourceFolder();
		
		return dataFolder;
	}
	
	public String getDataFilePath(String dataFile) {
		return dataFolder() + File.separator + dataFile;
	}
	
	public String getDataFilePath() {
		String dataFile = this.getClass().getSimpleName() + "_data.xlsx";
		return getDataFilePath(dataFile);
	}
	
	/**
	 * <b>Name:</b> getDataProvider <b>Description:</b> Gets data from the excel
	 * sheet in form of hash map and converts it into 2 dimensional array.
	 * 
	 * @param dataFileName
	 *            The data file name.
	 * @param className
	 *            The test class name.
	 * @param isGetAllTestData
	 *            The flag to indicate whether only testData or all of the test
	 *            related data (e.g. both testData and testCaseData) is to be
	 *            returned for the requester.
	 * @return Object[][] two dimension array of data
	 * @throws Exception
	 *             The Exception instance.
	 */
	@Override
	public Object[][] getDataProvider(String dataFileName, String className, boolean isGetAllTestData)
			throws Exception {
		
		String strTestDataQuery;
		ArrayList<HashMap<String, String>> testDataSheet = null;
		Map<String, String> testData = null;
		HashMap<String, HashMap<String, String>> testCaseData;

		String dataFilePath =  dataFolder() + File.separator + dataFileName	+ ".xlsx";
		ALRGLogger.log(_log, "Entering Test data from file: " + dataFileName);
		ExcelParser ep = new ExcelParser(dataFilePath, className);
		testDataSheet = ep.readTestData();

		int numberTestCases = testDataSheet.size();
		Object[][] result = new Object[numberTestCases][];

		for (int i = 0; i < numberTestCases; i++) {
			dataFilePath = null;
			strTestDataQuery = null;
			testCaseData = null;

			try {
				testData = testDataSheet.get(i);

				if (isGetAllTestData) {
					dataFilePath = getDataFilePath(dataFileName + ".xlsx");
					strTestDataQuery = "Select * from " + testData.get("TestDataSheet") + " where Test_Case_Name = '"
							+ testData.get("Test_Case_Name") + "' order by SequenceID";

					ALRGLogger.log(_log, "Entering Test data from file: " + dataFilePath);
					testCaseData = CommonUtility.getAdditionalDataInMap(dataFilePath, strTestDataQuery);
				}
			} catch (Exception e) {
				ALRGLogger.log(_log, "Exception occurred while fetching data from file. Exception:" + e.getMessage(),
						EllieMaeLogLevel.warn);
				e.printStackTrace();
			} finally {
				if (isGetAllTestData) {
					result[i] = new Object[] { testData, testCaseData };
				} else {
					result[i] = new Object[] { testData };
				}
			}
		}

		ALRGLogger.log(_log, "Exiting Test data");
		return result;
	}	
	
	/**
	 * Returns borrower name in a unique format.  The timestamp at the end 
	 * is replaced by ascii characters as mobile can only search by borr name 
	 * if it doesn't contain numbers, otherwise it considers the search string a loan number 
	 */
	public String autoBorrName_alpha(String borrName) {
	    
	    return "Auto-" + borrName + "-" + StringHelper.alphabet_HHmmss();
	}
	
	
    /**
     * Returns borrower name in a unique format by appending a timestamp at the end 
     */	
    public String autoBorrName(String borrName) {
        
        return "Auto-" + borrName + "-" + DateHelper.currentTimeStampAsString();
    }	
}