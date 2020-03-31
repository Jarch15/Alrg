package com.elliemae.alrg.actions;

import com.elliemae.core.Actions.EllieMaeActions;
import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;
import com.elliemae.core.Utils.CommonUtility;
import com.elliemae.alrg.consts.AGENCYUPDATESConsts;
import com.elliemae.alrg.consts.ALRGConsts;
import com.elliemae.alrg.consts.SEARCHRESULTConsts;
import com.elliemae.alrg.utils.ALRGLogger;
import com.elliemae.alrg.utils.LogHelper;
import com.elliemae.consts.FrameworkConsts;
import com.google.common.base.Function;
import com.paulhammant.ngwebdriver.ByAngular;

import com.elliemae.alrg.actions.ALRGApplicationActions;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.elasticsearch.search.profile.SearchProfileShardResults;
import org.glassfish.grizzly.ThreadCache.ObjectCacheElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.instrument.classloading.tomcat.TomcatLoadTimeWeaver;
import org.testng.Assert;


/**
 * <b>Name:</b> EllieMaeApplicationActions  
 * <b>Description: </b>This class is extending EllieMaeActions class and is used to call Actions related to driver. 
 *
 * 
 */

public abstract class ALRGApplicationActions extends EllieMaeActions {

    public static Logger _log = Logger.getLogger(ALRGApplicationActions.class);

    protected WebDriver driver;
    protected JavascriptExecutor js;
    protected FluentWait<WebDriver> defaultFWait;

    public ALRGApplicationActions(WebDriver driver) {

        super(driver);
        this.driver = driver;
        wait = new WebDriverWait(driver, ALRGConsts.DEFAULT_WAIT_TIMEOUT_SECONDS);
        js = (JavascriptExecutor) driver;
        defaultFWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(ALRGConsts.DEFAULT_WAIT_TIMEOUT_SECONDS))
                .pollingEvery(Duration.ofMillis(100)).ignoring(NoSuchElementException.class, NullPointerException.class);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }


    public void takeScreenShot(String desc) {

        try {
            String fileName = MDC.get("threadID") + "_" + desc;
            String timestamp = System.getProperty("logfolder");
            CommonUtility.takeScreenShot(this.driver, fileName, timestamp);
        } catch (Exception e) {
            ALRGLogger.log(_log, "Failed to take Screenshot: " + e.getMessage());
            StringWriter stackTrace = new StringWriter();
            ALRGLogger.log(_log, "Exception StackTrace: " + stackTrace.toString());
        }
    }

    public void writePageSourceToFile(String desc) {
        try {
            // Create file
            String fileName = MDC.get("threadID") + "_" + desc;
            String timestamp = System.getProperty("logfolder");    
            String filePath = new File("").getAbsolutePath();
            filePath = filePath + File.separator + "AutomationOutput" + File.separator + timestamp + File.separator
                    + "ScreenShots" + File.separator + fileName + ".html";            
            FileWriter fstream = new FileWriter(filePath);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(driver.getPageSource());
            // Close the output stream
            out.close();
        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * <b>Name:</b> alrg_swipeLeftRight<br>
     * <b>Description:</b> This method is used to swipe to the left or to the
     * right
     * 
     * @param element
     * @param iOffset
     *        [negative to Left, positive to Right]
     * 
     */
    public void alrg_swipeLeftRight(WebElement element, int iOffset) {
        Actions actions = new Actions(driver);
        actions.dragAndDropBy(element, iOffset, 0).perform();
    }

    /**
     * <b>Name: </b>alrg_goToThePage<br>
     * <b>Description: </b>Using URL go to the desired page
     * 
     * @param URL
     *
     */
    public void alrg_goToThePage(String URL) {
        try {
            driver.get(URL);
            waitForPageToLoad();
            ALRGLogger.log(_log, "Navigate to: '" + URL + "' page loaded with the title: " + driver.getTitle(), EllieMaeLogLevel.reporter);
        } catch (Exception e) {
            LogHelper.logTestError(_log, "Exception in alrg_goToThePage: " + e.getMessage());
        }
    }

    public void alrg_type(By locator, String elementValue, String elementDescription) {
        alrg_type(alrg_findElement(locator), elementValue, elementDescription);
    }

    /**
     * <b>Name:</b> alrg_type<br>
     * <b>Description:</b> Fills a TextBox on the web page according to the
     * given value.
     * 
     * @param element
     * @param elementValue
     * @param elementDescription
     * 
     */
    public void alrg_type(WebElement element, String elementValue, String elementDescription) {

        if (elementValue != null) {

            alrg_waitForElementEnabled(element);
            alrg_scrollIntoViewElement(element, elementDescription);
            try {
                if (!wait.until(ExpectedConditions.visibilityOf(element)).isEnabled()) {
                    throw new Exception(elementDescription + " does not exist.");
                }
                if (StringUtils.isNotEmpty(alrg_getTextBoxValue(element, elementDescription))) {
                    element.clear();
                    waitForPageToLoad();
                }
                if (StringUtils.isNotEmpty(alrg_getTextBoxValue(element, elementDescription))) {
                    clearTextField(element);
                    waitForPageToLoad();
                }
                element.sendKeys(elementValue);
                alrg_waitForSpinnerToBeGone();
                // windowFocus();
                ALRGLogger.log(_log, "Entering Text in: " + "'" + elementDescription + "'" + " = " + elementValue, EllieMaeLogLevel.reporter);
            } catch (Exception e) {
                ALRGLogger.log(_log, "Fail to enter Text: " + elementValue + " for " + elementDescription, EllieMaeLogLevel.reporter);
                ALRGLogger.log(_log, "Exception in alrg_type: " + e.getMessage());
            }
        } else {
            ALRGLogger.log(_log, "Element: " + elementDescription + " does not exist.");
        }
    }


    public void alrg_clearTextField(WebElement element, String elementDescription) {
        
        try {
            String v = element.getAttribute("value");
            int i = 0;
            while( i < v.length() && !StringUtils.isEmpty(v) ) {
                element.sendKeys(Keys.CONTROL, Keys.HOME);
                element.sendKeys(Keys.DELETE);
                i++;
            }
        } catch (Exception e) {
            ALRGLogger.log(_log, "Fail to clear text for " + elementDescription, EllieMaeLogLevel.reporter);
        }
    }
    
    public void alrg_typeAndTab(By locator, String elementValue, String elementDescription) {
        alrg_typeAndTab(alrg_findElement(locator), elementValue, elementDescription);
    }

    public void alrg_typeAndTab(WebElement element, String elementValue, String elementDescription) {
        alrg_type(element, elementValue, elementDescription);
        alrg_sendTab(element, elementDescription);
    }

    /**
     * <b>Name:</b> alrg_type_WithoutClearData<br>
     * <b>Description:</b> Fills a TextBox on the web page according to the
     * given value with out clearing the text box.
     * 
     * @param element
     * @param elementValue
     * @param elementDescription
     * 
     */
    public void alrg_typeNoClearData(WebElement element, String elementValue, String elementDescription) {
        if (elementValue != null) {
            waitForPageToLoad();
            alrg_scrollIntoViewElement(element, elementDescription);
            try {
                if (element == null) {
                    ALRGLogger.log(_log, "Element: " + elementDescription + " does not exist.");
                }
                if (!wait.until(ExpectedConditions.visibilityOf(element)).isEnabled()) {
                    throw new Exception(elementDescription + " does not exist.");
                }
                element.sendKeys(elementValue);
                // windowFocus();
                ALRGLogger.log(_log, "Entering Text in: " + "'" + elementDescription + "'" + " = " + elementValue, EllieMaeLogLevel.reporter);
            } catch (Exception e) {
                ALRGLogger.log(_log, "Fail to enter Text: " + elementValue + " for " + elementDescription);
                ALRGLogger.log(_log, "Exception in alrg_type: " + e.getMessage());
            }
        }
    }

    /**
     * <b>Name:</b> alrg_type_noScroll<br>
     * <b>Description:</b> Fills a TextBox on the web page according to the
     * given value.
     * 
     * @param element
     * @param elementValue
     * @param elementDescription
     * 
     */
    public void alrg_type_noScroll(WebElement element, String elementValue, String elementDescription) {

        if (elementValue != null) {
            waitForPageToLoad();
            try {
                if (element == null) {
                    ALRGLogger.log(_log, "Element: " + elementDescription + " does not exist.");
                }
                if (!wait.until(ExpectedConditions.visibilityOf(element)).isEnabled()) {
                    throw new Exception(elementDescription + " does not exist.");
                }
                if (StringUtils.isNotEmpty(alrg_getTextBoxValue(element, elementDescription))) {
                    element.clear();
                    waitForPageToLoad();
                }
                if (StringUtils.isNotEmpty(alrg_getTextBoxValue(element, elementDescription))) {
                    clearTextField(element);
                    waitForPageToLoad();
                }
                element.sendKeys(elementValue);
                waitForPageToLoad();
                windowFocus();
                ALRGLogger.log(_log, "Entering Text in: " + "'" + elementDescription + "'" + " = " + elementValue, EllieMaeLogLevel.reporter);
            } catch (Exception e) {
                ALRGLogger.log(_log, "Fail to enter Text: " + elementValue + " for " + elementDescription);
                ALRGLogger.log(_log, "Exception in alrg_type: " + e.getMessage());
            }
        }
    }

    /**
     * Clicking on an element using JS
     * 
     * @param element
     * @return
     */
    public boolean alrg_clickElementUsingJS(WebElement element, String elementDescription) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            ALRGLogger.log(_log, "alrg_clickElementUsingJS() Clicked on: " + "'" + elementDescription + "'", EllieMaeLogLevel.reporter);
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "alrg_clickElementUsingJS(), Exception to click on element:: " + e.getMessage());
            return false;
        }
    }

    public boolean alrg_clickElement(By locator, String elementDescription) {
        return alrg_clickElement(alrg_findElement(locator), elementDescription);
    }

    /**
     * <b>Name:</b> alrg_clickElement<br>
     * <b>Description:</b> Click on a link/button/radio button.
     * 
     * @param element
     * @param elementDescription
     * 
     */
    public boolean alrg_clickElement(WebElement element, String elementDescription) {

        boolean isClicked = false;
        if (element != null) {
            try {
                if (!element.isDisplayed()) {
                    alrg_scrollIntoViewElement(element, elementDescription);
                }
                alrg_waitForElementToBeClickable(element, elementDescription);
                element.click();
                ALRGLogger.log(_log, "alrg_clickElement() Clicked on: " + "'" + elementDescription + "'", EllieMaeLogLevel.reporter);
                isClicked = true;
            } catch (Exception e) {
                ALRGLogger.log(_log, "alrg_clickElement() Fail to click: " + elementDescription, EllieMaeLogLevel.reporter);
                ALRGLogger.log(_log, "alrg_clickElement() Fail to click: " + elementDescription + " because of error: " + e.getMessage());
                // try a workaround instead
                isClicked = alrg_clickElementUsingJS(element, elementDescription);
            } finally {
                alrg_waitForSpinnerToBeGone();
            }
        } else {
            ALRGLogger.log(_log, "alrg_clickElement() element is null!", EllieMaeLogLevel.reporter);
        }
        return isClicked;
    }

    public void alrg_scrollIntoViewElement(By locator, String elementDescription) {
        alrg_scrollIntoViewElement(alrg_findElement(locator), elementDescription);
    }

    /**
     * <b>Name:</b> alrg_scrollIntoViewElement<br>
     * <b>Description:</b> Scrolling into view an element.
     * 
     * @param element
     * @param elementDescription
     * 
     */
    public void alrg_scrollIntoViewElement(WebElement element, String elementDescription) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            ALRGLogger.log(_log, "Failed to scroll into view element: " + elementDescription);
            ALRGLogger.log(_log, "Exception in alrg_scrollIntoViewElement: " + e.getMessage());
        }
    }

    /**
     * <b>Name:</b> alrg_scrollUpOrDownToViewElement<br>
     * <b>Description:</b> Scrolling up or down into view an element<br>
     * 
     * @param element
     * @param upDown
     *        - up/down
     * @param elementDescription
     *  
     */
    public void alrg_scrollUpOrDownToViewElement(WebElement element, String upDown, String elementDescription) {
        String dir = "";
        try {
            switch (upDown.toLowerCase()) {
                case "up":
                    dir = "false";
                    break;
                case "down":
                    dir = "true";
                    break;
                default:
                    break;
            }
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(" + dir + ");", element);
            ALRGLogger.log(_log, "Scrolled " + upDown + " into view element: " + elementDescription);
        } catch (Exception e) {
            ALRGLogger.log(_log, "Failed to scroll " + upDown + " into view element: " + elementDescription);
            ALRGLogger.log(_log, "Exception in alrg_scrollUpOrDownToViewElement: " + e.getMessage());
        }
    }

    public String alrg_getAttributeValue(By locator, String attributeName, String elementDescription) {
        return alrg_getAttributeValue(alrg_findElement(locator), attributeName, elementDescription);
    }


    /**
     * <b>Name:</b> alrg_getTextBoxText<br>
     * <b>Description:</b> Return text from the Text box
     * 
     * @param element
     * @param attributeName
     * @param elementDescription
     *  
     */
    public String alrg_getAttributeValue(WebElement element, String attributeName, String elementDescription) {
        String returnText = "";
        try {
            // hoverOverAnElement(element);
            returnText = element.getAttribute(attributeName);
            ALRGLogger.log(_log, "Attribute from " + "'" + elementDescription + "'" + " = " + returnText, EllieMaeLogLevel.reporter);
        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception to get attribute value: " + e.getMessage());
        }
        return returnText;
    }

    /**
     * <b>Name:</b> alrg_isTextPresent<br>
     * <b>Description:</b> Check whether text present or not.<br>
     * 
     * @param element
     * @param text
     * @return alrg_isTextPresent (true/false)
     *  
     */
    public boolean alrg_isTextPresent(WebElement element, String text) {
        boolean textPresent = false;
        try {
            hoverOverAnElement(element);
            String textSearch = element.getText().toLowerCase().trim();
            if (text.length()>0)
            	text=text.toLowerCase().trim();
            
            if (textSearch.contains(text)|| textSearch.equals(text)){
            	
                ALRGLogger.log(_log, "The: " + "'" + text + "'" + " text present", EllieMaeLogLevel.reporter);
                textPresent = true;
            } else {
                ALRGLogger.log(_log, "The: " + "'" + text + "'" + " text not present", EllieMaeLogLevel.reporter);
            }
        } catch (NoSuchElementException e) {

        } catch (Exception e) {
            ALRGLogger.log(_log, "The: " + "'" + text + "'" + " - " + textPresent);
            ALRGLogger.log(_log, "Exception in alrg_isTextPresent: " + e.getMessage());
        }
        return textPresent;
    }

    /**
     * <b>Name:</b> alrg_explicitWait<br>
     * <b>Description: </b>Method waiting till an element is found and displays
     * duration of waiting.
     * 
     * @param locator
     * @param timeOutInSeconds
     *  
     */
    public void alrg_explicitWait(final By locator, int timeOutInSeconds) {

        ALRGLogger.log(_log, "********Explicit Wait started**********");
        long startTime = System.currentTimeMillis();

        try {
            alrg_waitForElementToBeVisible(locator, timeOutInSeconds, locator.toString());
            Actions act = new Actions(driver);
            WebElement e = driver.findElement(locator);
            act.moveToElement(e).perform();
            ALRGLogger.log(_log, "Passed to explicit wait for element (" + locator + ") within - "
                    + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()));

        } catch (Exception e) {
            ALRGLogger.log(_log, "Fail to alrg_explicitWait() for element {" + locator + " }" + " within - "
                    + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()));
            ALRGLogger.log(_log, "Exception in alrg_explicitWait:" + e.getMessage());
        }
    }

    
    /**
     * <b>Name:</b> alrg_waitImplicitly<br>
     * <b>Description: </b> Method for waiting a certain amount of time.
     * 
     * @param time[second]
     *  
     * 
     * Avoid using hard-coded wait, use other explict wait instead
     */
    @Deprecated
    public void alrg_waitForSeconds(int time) {
        long startTime = 0;
        startTime = System.currentTimeMillis();
        for (int i = 0; i < time; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
        ALRGLogger.log(_log, "The alrg_waitImplicitly lasted for - " + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()));
    }

    /**
     * <b>Name:</b> alrg_getCheckBoxStatus<br>
     * <b>Description:</b> This method is boolean to get status of the check
     * box.
     * 
     * @param element
     *  
     */
    public boolean alrg_getCheckBoxStatus(WebElement element) {
        if (element.isSelected())
            return true;
        else
            return false;
    }

    /**
     * <b>Name:</b> alrg_getOptionButtonStatus<br>
     * <b>Description:</b> This method is boolean to get status of the radio button/option button
     *      * 
     * @param element
     *  
     */
    public boolean alrg_getOptionButtonStatus(WebElement element,String elementDescription) {
        if (element.isSelected())
            return true;
        else
            return false;
    }

    
    
    public void alrg_setCheckBox(By locator, String setOnOff, String elementDesc) {
        alrg_setCheckBox(alrg_findElement(locator), setOnOff, elementDesc);
    }

    /**
     * <b>Name:</b> alrg_setCheckBox<br>
     * <b>Description:</b> This method is used to check a check box
     * 
     * @param element
     * @param setOnOff
     *        - string ON or OFF
     * @param elementDesc
     *  
     */
    public void alrg_setCheckBox(WebElement element, String setOnOff, String elementDesc) {

        alrg_scrollIntoViewElement(element, elementDesc);
        if (!alrg_getCheckBoxStatus(element) && setOnOff.equalsIgnoreCase("on")) {
            try {
                element.click();
            } catch (Exception e) { // workaround for ElementNotVisibleException
                alrg_clickElementUsingJS(element, elementDesc);
            }
            ALRGLogger.log(_log, "'" + elementDesc + "'" + " checkbox checked", EllieMaeLogLevel.reporter);
        } else if (alrg_getCheckBoxStatus(element) && setOnOff.equalsIgnoreCase("off")) {
            try {
                element.click();
            } catch (Exception e) { // workaround for ElementNotVisibleException, NotClickableException
                alrg_clickElementUsingJS(element, elementDesc);
            }
            ALRGLogger.log(_log, "'" + elementDesc + "'" + " checkbox unchecked", EllieMaeLogLevel.reporter);
        } else {
            ALRGLogger.log(_log, "Check box: '" + elementDesc + "'" + " already set to: " + setOnOff, EllieMaeLogLevel.reporter);
        }
    }

    /**
     * <b>Name:</b> alrg_verifyAllListItems<br>
     * <b>Description:</b> This method is used to verify all items on the list
     * 
     * @param element
     * @param expected
     *        - the expected list
     * @param elementDesc
     *  
     */
    public void alrg_verifyAllListItems(WebElement element, String[] expected, String elementDesc) {
        Select ls = new Select(element);
        List<WebElement> options = ls.getOptions();
        for (WebElement we : options) {
            boolean match = false;
            for (int i = 0; i < expected.length; i++) {
                if (we.getText().equals(expected[i].trim())) {
                    match = true;
                }
            }
            Assert.assertTrue(match, "'" + elementDesc + "'" + " list is not the same - ");
        }
    }

    /**
     * <b>Name:</b> alrg_matchAllListItems<br>
     * <b>Description:</b> This method is used to verify all items on the list
     * 
     * @param element
     * @param expected
     *        - the expected list
     * @param elementDesc
     * @return alrg_matchAllListItems(true/false)
     *  
     */
    public boolean alrg_matchAllListItems(WebElement element, String[] expected, String elementDesc) {
        Select ls = new Select(element);
        List<WebElement> options = ls.getOptions();
        for (WebElement we : options) {
            for (int i = 0; i < expected.length; i++) {
                if (we.getText().equals(expected[i].trim()) == false) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * <b>Name:</b> alrg_isOptionPresentInDrpdwn<br>
     * <b>Description:</b> This method is used to verify if expected value is
     * present in the dropdown
     * 
     * @param element
     * @param expected
     * @param elementDesc
     * @return
     */
    public boolean alrg_isOptionPresentInDrpdwn(WebElement element, String expected, String elementDesc) {
        Select ls = new Select(element);
        List<WebElement> options = ls.getOptions();
        for (WebElement we : options) {
            if (we.getText().equalsIgnoreCase(expected.trim())) {
                return true;
            }
        }
        return false;
    }

    public boolean alrg_clickUsingActions(By locator, String elementDescription) {
        return alrg_clickUsingActions(alrg_findElement(locator), elementDescription);
    }

    /**
     * <b>Name:</b> alrg_clickUsingActions<br>
     * <b>Description: </b>Method clicks on a specific element using actions.
     * 
     * @param element
     * @param elementDescription
     *  
     */
    public boolean alrg_clickUsingActions(WebElement element, String elementDescription) {

        boolean isClicked = false;
        try {
            waitForPageToLoad();
            alrg_scrollIntoViewElement(element, elementDescription);
            Actions builder = new Actions(driver);
            builder.moveToElement(element).click().build().perform();
            waitForPageToLoad();
            ALRGLogger.log(_log, "Clicking on the element: " + elementDescription, EllieMaeLogLevel.reporter);
            isClicked = true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "Fail to click on the element: " + elementDescription + ". Exception in clickUsingActions: " + e.getMessage(),
                    EllieMaeLogLevel.debug);
        } finally {
        }
        return isClicked;
    }

    /**
     * <b>Name:</b> alrg_clickElementNoWait<br>
     * <b>Description:</b> Click on a link/button/radio button without
     * waitForAngularRequestsToFinish.
     * 
     * @param element
     * @param elementDescription
     *  
     */
    public boolean alrg_clickElementNoWait(WebElement element, String elementDescription) {

        boolean isClicked = false;
        if (element != null) {
            try {
                if (!alrg_isElementClickable(element, elementDescription)) {
                    alrg_scrollIntoViewElement(element, elementDescription);
                    element.click();
                }
                ALRGLogger.log(_log, "alrg_clickElementNoWait() Clicked on: " + "'" + elementDescription + "'", EllieMaeLogLevel.reporter);
                isClicked = true;
            } catch (Exception e) {
                ALRGLogger.log(_log, "alrg_clickElementNoWait() Fail to click: " + elementDescription, EllieMaeLogLevel.reporter);            	
                ALRGLogger.log(_log, "alrg_clickElementNoWait() Fail to click: " + elementDescription + " because of error: " + e.getMessage());
                // try a workaround instead
                isClicked = alrg_clickElementUsingJS(element, elementDescription);
            } finally {
                try {
                    alrg_waitForSpinnerToBeGone();
                } catch (Exception ex) {
                }
            }
        } else {
            ALRGLogger.log(_log, "alrg_clickElement() element is null!", EllieMaeLogLevel.reporter);
        }
        return isClicked;
    }

    /**
     * <b>Name:</b> alrg_typeNoWait<br>
     * <b>Description:</b> Fills a TextBox on the web page according to the
     * given value without waitForAngularRequestsToFinish.
     * 
     * @param element
     * @param elementValue
     * @param elementDescription
     * 
     */
    public void alrg_typeNoWait(WebElement element, String elementValue, String elementDescription) {

        if (elementValue != null) {

            alrg_scrollIntoViewElement(element, elementDescription);
            try {
                if (element == null) {
                    ALRGLogger.log(_log, "Element: " + elementDescription + " does not exist.");
                }
                if (!wait.until(ExpectedConditions.visibilityOf(element)).isEnabled()) {
                    throw new Exception(elementDescription + " does not exist.");
                }
                if (StringUtils.isNotEmpty(alrg_getTextBoxValue(element, elementDescription))) {
                    element.clear();
                    alrg_waitForSpinnerToBeGone(1);
                }
                element.sendKeys(elementValue);
                windowFocus();
                ALRGLogger.log(_log, "Entering Text in: " + "'" + elementDescription + "'" + " = " + elementValue, EllieMaeLogLevel.reporter);
            } catch (Exception e) {
                ALRGLogger.log(_log, "Fail to enter Text: " + elementValue + " for " + elementDescription, EllieMaeLogLevel.reporter);
                ALRGLogger.log(_log, "Exception in alrg_type: " + e.getMessage(), EllieMaeLogLevel.reporter);
            }
        } else {
            ALRGLogger.log(_log, "alrg_typeNoWait() element is null!", EllieMaeLogLevel.reporter);
        }
    }

    public void alrg_typeNoWaitAndTab(WebElement element, String elementValue, String elementDescription) {
        alrg_typeNoWait(element, elementValue, elementDescription);
        alrg_sendTab(element, elementDescription);
    }

    /**
     * <b>Name:</b> alrg_selectListUsingAValue<br>
     * <b>Description:</b> This method is used to select from a list using link
     * value in css selector<br>
     * <b>Example:</b>
     * <code>alrg_selectListUsingAValue(lockStatLst,"Locked","Lock Status list");</code>
     * 
     * @param element
     * @param lstItem
     * @param elementDescription
     *  
     */
    public void alrg_selectListUsingAValue(WebElement element, String lstItem, String elementDescription) {

        alrg_waitForElementToBeVisible(element, 5, elementDescription);
        alrg_clickElement(element, elementDescription);
        alrg_clickElement(element.findElement(By.cssSelector(ALRGConsts.LST_A_VALUE_START + lstItem + ALRGConsts.LST_A_VALUE_END)),
                "item " + "-" + lstItem + "-" + " in " + elementDescription);
    }

    /**
     * <b>Name:</b> alrg_countElements<br>
     * <b>Description:</b> This method is used to count elements
     * 
     * @param locator
     *  
     */
    public int alrg_countElements(By locator) {

        alrg_explicitWait(locator, 20);
        List<WebElement> elements = driver.findElements(locator);
        return elements.size();
    }

    /**
     * <b>Name:</b> alrg_countElements<br>
     * <b>Description:</b> This method is used to count elements
     * 
     * @param locator
     *  
     */
    public int alrg_countElementsNoWait(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        return elements.size();
    }

    /**
     * 
     */
    public void alrg_selectListByIndexAndTab(WebElement element, int index, String elementDescription) {
        try {
            if (element == null) {
                ALRGLogger.log(_log, "Element for : " + elementDescription + " does not exist.", EllieMaeLogLevel.reporter);
            } else {
                // DO NOT CHANGE - scroll only when needed. otherwise, new button gets hidden, no way to scroll back
                if (!element.isDisplayed()) {
                    alrg_scrollIntoViewElement(element, elementDescription);
                }
                driver.switchTo().activeElement();
                Select select = new Select(element);
                select.selectByIndex(index);
                alrg_sendTab(element, elementDescription);
                ALRGLogger.log(_log, "Selected: " + (elementDescription == null ? element : elementDescription) + " with index: " + index,
                        EllieMaeLogLevel.reporter);
            }
        } catch (Exception e) {
            ALRGLogger.log(_log, "Fail to select: " + (elementDescription == null ? element : elementDescription) + " with text: " + index,
                    EllieMaeLogLevel.reporter);
            ALRGLogger.log(_log, "Exception in alrg_selectListByVisibleText: " + e.getMessage(), EllieMaeLogLevel.reporter);
        }
    }


    public void alrg_selectListByVisibleTextIgnoreCase(WebElement element, String elementValue, String elementDescription) {
        try {
            if (element == null) {
                ALRGLogger.log(_log, "Element for : " + elementDescription + " does not exist.", EllieMaeLogLevel.reporter);
            } else {
                // DO NOT CHANGE - scroll only when needed. otherwise, new button gets hidden, no way to scroll back
                if (!element.isDisplayed()) {
                    alrg_scrollIntoViewElement(element, elementDescription);
                }
                driver.switchTo().activeElement();
                Select select = new Select(element);
                int index = 0;
                try {
                    for (WebElement option : select.getOptions()) {
                        if (option.getText().equalsIgnoreCase(elementValue))
                            break;
                        index++;
                    }
                    select.selectByIndex(index);
                } catch (NoSuchElementException notFoundEx) {
                    ALRGLogger.log(_log, "Cannot select by Visible text. Try select by value: " + notFoundEx.getMessage());
                    select.selectByValue(elementValue);
                }
                ALRGLogger.log(_log, "Selected: " + (elementDescription == null ? element : elementDescription) + " with text: " + elementValue,
                        EllieMaeLogLevel.reporter);
            }
        } catch (Exception e) {
            ALRGLogger.log(_log, "Fail to select: " + (elementDescription == null ? element : elementDescription) + " with text: " + elementValue,
                    EllieMaeLogLevel.reporter);
            ALRGLogger.log(_log, "Exception in alrg_selectListByVisibleTextIgnoreCase: " + e.getMessage(), EllieMaeLogLevel.reporter);
        }
    }

    public void alrg_selectListByVisibleTextIgnoreCaseAndTab(WebElement element, String elementValue, String elementDescription) {
        alrg_selectListByVisibleTextIgnoreCase(element, elementValue, elementDescription);
        alrg_sendTab(element, elementDescription);
    }

    /**
     * <b>Name:</b> alrg_selectListByVisibleText<br>
     * <b>Description:</b> Select a Drop Down/field on the web page according to
     * the given value.
     * 
     * @param element
     * @param elementValue
     * @param elementDescription
     * 
     *  
     */
    public void alrg_selectListByVisibleText(WebElement element, String elementValue, String elementDescription) {
        try {
            if (element == null) {
                ALRGLogger.log(_log, "Element for: " + elementDescription + " does not exist.", EllieMaeLogLevel.reporter);
            } else {
                // DO NOT CHANGE - scroll only when needed. otherwise, new button gets hidden, no way to scroll back
                if (!element.isDisplayed()) {
                    alrg_scrollIntoViewElement(element, elementDescription);
                }
                driver.switchTo().activeElement();
                Select select = new Select(element);
                try {
                    select.selectByVisibleText(elementValue);
                } catch (NoSuchElementException notFoundEx) {
                    ALRGLogger.log(_log, "Cannot select by Visible text. Try select by value: " + notFoundEx.getMessage());
                    select.selectByValue(elementValue);
                }
                ALRGLogger.log(_log, "Selected: " + (elementDescription == null ? element : elementDescription) + " with text: " + elementValue,
                        EllieMaeLogLevel.reporter);
            }
        } catch (Exception e) {
            ALRGLogger.log(_log, "Fail to select: " + (elementDescription == null ? element : elementDescription) + " with text: " + elementValue,
                    EllieMaeLogLevel.reporter);
            ALRGLogger.log(_log, "Exception in alrg_selectListByVisibleText: " + e.getMessage(), EllieMaeLogLevel.reporter);
        }
    }


    public void alrg_selectListByVisibleTextAndTab(By locator, String elementValue, String elementDescription) {
        alrg_selectListByVisibleTextAndTab(alrg_findElement(locator), elementValue, elementDescription);
    }


    public void alrg_selectListByVisibleTextAndTab(WebElement element, String elementValue, String elementDescription) {
        alrg_selectListByVisibleText(element, elementValue, elementDescription);
        alrg_sendTab(element, elementDescription);
    }

    /**
     * <b>Name:</b> alrg_selectListByVisibleTextNoScroll<br>
     * <b>Description:</b> Select a Drop Down/field on the web page according to
     * the given value without scrolling to the element
     * 
     * @param element
     * @param elementValue
     * @param elementDescription
     * 
     *  
     */
    public void alrg_selectListByVisibleTextNoScroll(WebElement element, String elementValue, String elementDescription) {
        try {
            waitForPageToLoad();
            if (element == null) {
                ALRGLogger.log(_log, "Element for : " + elementDescription + " does not exist.");
            } else {
                driver.switchTo().activeElement();
                Select select = new Select(element);
                try {
                    select.selectByVisibleText(elementValue);
                } catch (NoSuchElementException notFoundEx) {
                    ALRGLogger.log(_log, "Cannot select by Visible text. Try select by value: " + notFoundEx.getMessage());
                    select.selectByValue(elementValue);
                }
                ALRGLogger.log(_log, "Selected: " + (elementDescription == null ? element : elementDescription) + " with text: " + elementValue);
                alrg_waitForSpinnerToBeGone();
            }
        } catch (Exception e) {
            ALRGLogger.log(_log, "Fail to select: " + (elementDescription == null ? element : elementDescription) + " with text: " + elementValue);
            ALRGLogger.log(_log, "Exception in alrg_selectListByVisibleText: " + e.getMessage());
        }
    }

    /**
     * <b>Name:</b> alrg_simplyClicksElement<br>
     * <b>Description: </b>Method clicks on a specific element without waiting
     * and scrolling to the element
     * 
     * @param element
     * @param elementDescription
     *  
     */
    public boolean alrg_simplyClicksElement(WebElement element, String elementDescription) {

        boolean isClicked = false;
        if (element != null) {
            try {
                element.click();
                ALRGLogger.log(_log, "alrg_simplyClicksElement() Clicked on: " + "'" + elementDescription + "'", EllieMaeLogLevel.reporter);
                isClicked = true;
            } catch (Exception e) {
                ALRGLogger.log(_log, "alrg_simplyClicksElement() Fail to click: " + elementDescription + " because of error: " + e.getMessage(),
                        EllieMaeLogLevel.debug);
                // try a workaround instead
                isClicked = alrg_clickElementUsingJS(element, elementDescription);
            } finally {
                alrg_waitForSpinnerToBeGone();
            }
        } else {
            ALRGLogger.log(_log, "alrg_simplyClicksElement() element is null!", EllieMaeLogLevel.reporter);
        }
        return isClicked;
    }

    public void alrg_simlyType(By locator, String elementValue, String elementDescription) {
        alrg_simlyType(alrg_findElement(locator), elementValue, elementDescription);
    }

    /**
     * <b>Name:</b> alrg_simlyType<br>
     * <b>Description:</b> Fills a TextBox on the web page according to the
     * given value without waiting and scrolling to the element
     * 
     * @param element
     * @param elementValue
     * @param elementDescription
     *  
     */
    public void alrg_simlyType(WebElement element, String elementValue, String elementDescription) {
        if (elementValue != null) {
            try {
                if (element == null) {
                    ALRGLogger.log(_log, "Element: " + elementDescription + " does not exist.");
                }
                if (!wait.until(ExpectedConditions.visibilityOf(element)).isEnabled()) {
                    throw new Exception(elementDescription + " does not exist.");
                }
                if (StringUtils.isNotEmpty(alrg_getTextBoxValue(element, elementDescription))) {
                    clearTextField(element);
                    waitForPageToLoad();
                }
                // added selenium clear as in few case text was not getting cleared
                try {
                    if (StringUtils.isNotEmpty(alrg_getTextBoxValue(element, elementDescription))) {
                        element.clear();
                        waitForPageToLoad();
                    }
                } catch (Exception e1) {
                }
                element.sendKeys(elementValue);
                windowFocus();
                ALRGLogger.log(_log, "Entering Text in: " + "'" + elementDescription + "'" + " = " + elementValue, EllieMaeLogLevel.reporter);
            } catch (Exception e) {
                ALRGLogger.log(_log, "Fail to enter Text: " + elementValue + " for " + elementDescription);
                ALRGLogger.log(_log, "Exception in alrg_simlyType: " + e.getMessage());
            }
        }
    }

    public void alrg_simlyTypeAndTab(WebElement element, String elementValue, String elementDescription) {
        alrg_simlyType(element, elementValue, elementDescription);
        alrg_sendTab(element, elementDescription);
    }


    public void alrg_simplySelectListByVisibleText(By locator, String elementValue, String elementDescription) {
        alrg_simplySelectListByVisibleText(alrg_findElement(locator), elementValue, elementDescription);
    }


    /**
     * <b>Name:</b> alrg_selectListByVisibleText<br>
     * <b>Description:</b> Select a Drop Down/field on the web page according to
     * the given value without waiting and scrolling to the element
     * 
     * @param element
     * @param elementValue
     * @param elementDescription
     * 
     *  
     */
    public void alrg_simplySelectListByVisibleText(WebElement element, String elementValue, String elementDescription) {
        try {
            if (element == null) {
                ALRGLogger.log(_log, "Element for : " + elementDescription + " does not exist.");
            } else {
                driver.switchTo().activeElement();
                Select select = new Select(element);
                try {
                    select.selectByVisibleText(elementValue);
                } catch (NoSuchElementException notFoundEx) {
                    ALRGLogger.log(_log, "Cannot select by Visible text. Try select by value: " + notFoundEx.getMessage());
                    select.selectByValue(elementValue);
                }
                ALRGLogger.log(_log, "Selected: " + (elementDescription == null ? element : elementDescription) + " with text: " + elementValue);
            }
        } catch (Exception e) {
            ALRGLogger.log(_log, "Fail to select: " + (elementDescription == null ? element : elementDescription) + " with text: " + elementValue);
            ALRGLogger.log(_log, "Exception in alrg_simplySelectListByVisibleText: " + e.getMessage());
        }
    }

    /**
     * <b>Name:</b> alrg_getWebElement <b></br>
     * Description: </b>Method returns WebElement using identifier of an element
     * and it locator
     * 
     * @param identifier
     *        - className, cssSelector and so on
     * @param locator
     * @return we
     *  
     * @deprecated
     * @see alrg_findElement
     */
    public WebElement alrg_getWebElement(String identifier, String locator) {
        WebElement we = null;
        try {
            switch (identifier.toLowerCase()) {
                case "classname":
                    we = driver.findElement(By.className(locator));
                    break;
                case "cssselector":
                    we = driver.findElement(By.cssSelector(locator));
                    break;
                case "id":
                    we = driver.findElement(By.id(locator));
                    break;
                case "linktext":
                    we = driver.findElement(By.linkText(locator));
                    break;
                case "name":
                    we = driver.findElement(By.name(locator));
                    break;
                case "partiallinktext":
                    we = driver.findElement(By.partialLinkText(locator));
                    break;
                case "tagname":
                    we = driver.findElement(By.tagName(locator));
                    break;
                case "xpath":
                    we = driver.findElement(By.xpath(locator));
                    break;
                case "binding":
                    we = driver.findElement(ByAngular.binding(locator));
                    break;
                case "buttontext":
                    we = driver.findElement(ByAngular.buttonText(locator));
                    break;
                case "exactbinding":
                    we = driver.findElement(ByAngular.exactBinding(locator));
                    break;
                case "exactrepeater":
                    we = driver.findElement(ByAngular.exactRepeater(locator));
                    break;
                case "model":
                    we = driver.findElement(ByAngular.model(locator));
                    break;
                case "options":
                    we = driver.findElement(ByAngular.options(locator));
                    break;
                case "partialbuttontext":
                    we = driver.findElement(ByAngular.partialButtonText(locator));
                    break;
                case "repeater":
                    we = driver.findElement(ByAngular.repeater(locator));
                    break;
                default:
                    break;
            }
        } catch (NoSuchElementException elEx) {
            ALRGLogger.log(_log, "Element: '" + locator + "' (" + identifier + ")  does not exist.", EllieMaeLogLevel.warn);
        }
        return we;
    }

    /**
     * Tries to locator a list of elements. The provided locator are tried for different types of locators in
     * the order of "css", "xpath".
     * 
     * @return the web element identified by the locator. If not found, returns null
     * 
     */
    public List<WebElement> alrg_findElements(String sLocator) {

        List<WebElement> elements = null;
        WebElement web = null;
        try {
            // try css selector first, if not found, then try xpath
            elements = driver.findElements(By.cssSelector(sLocator));
        } catch (InvalidSelectorException e) {
            // try xpath
            elements = driver.findElements(By.xpath(sLocator));
        }
        return elements;
    }

    public WebElement alrg_findElement(By locator) {

        WebElement web = null;
        try {
            web = driver.findElement(locator);
        } catch (Exception e) {
            ALRGLogger.log(_log, "Element: '" + locator + " does not exist.", EllieMaeLogLevel.reporter, EllieMaeLogLevel.warn);
        }
        return web;
    }

    /**
     * Tries to locator an element. The provided locator are tried for different types of locators in
     * the order of "css", "xpath".
     * 
     * @return the web element identified by the locator. If not found, returns null
     * 
     */
    public WebElement alrg_findElement(String sLocator) {

        List<WebElement> elements = null;
        WebElement web = null;
        try {
            if (sLocator.startsWith("/")) {
                elements = driver.findElements(By.xpath(sLocator));
            } else {
                // try css selector first, if not found, then try xpath
                elements = driver.findElements(By.cssSelector(sLocator));
            }
        } catch (InvalidSelectorException e) {
            // try xpath
            elements = driver.findElements(By.xpath(sLocator));
        }

        if (elements != null && elements.size() > 0) {
            web = elements.get(0);
        }
        return web;
    }
    
    
    public String alrg_getTextBoxValue(By locator, String description) {
    	return alrg_getTextBoxValue(alrg_findElement(locator), description);
    }

    public String alrg_getTextBoxValue(By locator) {
        return alrg_getTextBoxValue(alrg_findElement(locator), "");
    }

    /**
     * <b>Name:</b> alrg_getTextBoxValue<br>
     * <b>Description:</b> This is method is used to fetch the value within a
     * textbox element, which does not have value attribute populated.
     * 
     *
     */
    public String alrg_getTextBoxValue(WebElement element) {
        return alrg_getTextBoxValue(element, "");
    }

    public String alrg_getTextBoxValue(WebElement element, String elementDescription) {
        String attValue = "";
        alrg_scrollIntoViewElement(element, elementDescription);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object o = js.executeScript("return arguments[0].value;", element);
        attValue = (o == null) ? "" : o.toString();
        // ALRGLogger.log(_log, "Textbox " + elementDescription + " has Value: " + attValue);
        return attValue;
    }


    public String alrg_getSelectedOptionText(By locator, String elementDescription) {
        return alrg_getSelectedOptionText(alrg_findElement(locator), elementDescription);
    }

    /**
     * <b>Name:</b> getSelectedOptionText<br>
     * <b>Description:</b> Return selected value from drop down
     * 
     * @param element
     * @param elementDescription
     *  
     */
    public String alrg_getSelectedOptionText(WebElement element, String elementDescription) {
        String returnText = "";
        waitForPageToLoad();
        alrg_scrollIntoViewElement(element, elementDescription);
        try {
            if (element == null) {
                ALRGLogger.log(_log, "Element for: " + elementDescription + " does not exist");
            }
            driver.switchTo().activeElement();
            Select select = new Select(element);
            returnText = select.getFirstSelectedOption().getText();
            ALRGLogger.log(_log, "Selected: " + returnText);
        } catch (Exception e) {
            ALRGLogger.log(_log, "Failed to get selected text from" + elementDescription + ".Exception: " + e.getMessage());
        }
        return returnText;
    }

    public String alrg_getSelectedOptionTextNoScroll(WebElement element, String elementDescription) {
        String returnText = "";
        waitForPageToLoad();
        try {
            if (element == null) {
                ALRGLogger.log(_log, "Element for: " + elementDescription + " does not exist");
            }
            driver.switchTo().activeElement();
            Select select = new Select(element);
            returnText = select.getFirstSelectedOption().getText();
            ALRGLogger.log(_log, "Selected: " + returnText);
        } catch (Exception e) {
            ALRGLogger.log(_log, "Failed to get selected text from" + elementDescription + ".Exception: " + e.getMessage());
        }
        return returnText;
    }

    /**
     * <b>Name:</b> alrg_clickElementNoWait<br>
     * <b>Description:</b> Click on a link/button/radio button without
     * waitForAngularRequestsToFinish and also without scrolling the element
     * into view.
     * 
     * @param element
     * @param elementDescription
     * 
     */
    public boolean alrg_clickElementNoWaitNoScroll(WebElement element, String elementDescription) {
        boolean isClicked = false;
        if (element != null) {
            try {
                element.click();
                ALRGLogger.log(_log, "alrg_clickElementNoWaitNoScroll() Clicked on: " + "'" + elementDescription + "'", EllieMaeLogLevel.reporter);
                isClicked = true;
            } catch (Exception e) {
                ALRGLogger.log(_log, "alrg_clickElementNoWaitNoScroll() Fail to click: " + elementDescription,
                        EllieMaeLogLevel.reporter);            	
                ALRGLogger.log(_log, "alrg_clickElementNoWaitNoScroll() Fail to click: " + elementDescription + " because of error: " + e.getMessage());
                // try a workaround instead
                isClicked = alrg_clickElementUsingJS(element, elementDescription);
            } finally {
                waitForPageToLoad();
            }
        } else {
            ALRGLogger.log(_log, "alrg_clickElement() element is null!", EllieMaeLogLevel.reporter);
        }
        return isClicked;
    }

    /**
     * <b>Name:</b> alrg_selectListUsingAValueNoWait<br>
     * <b>Description:</b> This method is used to select from a list using link
     * at Tablet and waits for Tablet Spinner value in css selector<br>
     * <b>Example:</b>
     * <code>alrg_selectListUsingAValue(lockStatLst,"Locked","Lock Status list");</code>
     * 
     * @param element
     * @param lstItem
     * @param elementDescription
     * 
     */
    public void alrg_selectListUsingAValueNoWait(WebElement element, String lstItem, String elementDescription) {

        alrg_waitForElementToBeVisible(element, 5, elementDescription);
        alrg_clickElementNoWait(element, elementDescription);
        alrg_clickElementNoWait(driver.findElement(By.cssSelector(ALRGConsts.LST_A_VALUE_START + lstItem + ALRGConsts.LST_A_VALUE_END)),
                "item " + "-" + lstItem + "-" + " in " + elementDescription);
    }



    /**
     * <b>Name:</b> alrg_getCheckBoxStatusUsingAttribute<br>
     * <b>Description:</b> This method is boolean to get status of the check
     * box, using <code>getAttribute("checked")</code> value
     * 
     * @param element
     *  
     */
    public boolean alrg_getCheckBoxStatusUsingAttribute(WebElement element) {
        if (element.getAttribute("checked") != null)
            return true;
        else
            return false;
    }


    public void alrg_setCheckBoxUsingAttribute(By locator, String setOnOff, String elementDesc) {
        alrg_setCheckBoxUsingAttribute(alrg_findElement(locator), setOnOff, elementDesc);
    }

    /**
     * <b>Name:</b> alrg_setCheckBoxUsingAttribute<br>
     * <b>Description:</b> This method is used to select a check box, using
     * <code>alrg_getCheckBoxStatusUsingAttribute</code> method
     * 
     * @param element
     * @param setOnOff
     *        - string ON or OFF
     * @param elementDesc
     *  
     */
    public void alrg_setCheckBoxUsingAttribute(WebElement element, String setOnOff, String elementDesc) {

        if (!alrg_getCheckBoxStatusUsingAttribute(element) && setOnOff.equalsIgnoreCase("on")) {
            element.click();
            alrg_waitForElementToBeSelected(element, setOnOff);
            ALRGLogger.log(_log, "'" + elementDesc + "'" + " checkbox checked", EllieMaeLogLevel.reporter);
        } else if (alrg_getCheckBoxStatusUsingAttribute(element) && setOnOff.equalsIgnoreCase("off")) {
            element.click();
            alrg_waitForElementToBeSelected(element, setOnOff);
            ALRGLogger.log(_log, "'" + elementDesc + "'" + " checkbox unchecked", EllieMaeLogLevel.reporter);
        } else {
            ALRGLogger.log(_log, "Check box: '" + elementDesc + "'" + " already set to: " + setOnOff, EllieMaeLogLevel.reporter);
        }
    }

    /**
     * <b>Name:</b> alrg_getCheckBoxStatusUsingAttribute<br>
     * <b>Description:</b> This method is boolean to get status of the check
     * box, using {@code getAttribute(attrNm)} attribute name and
     * {@code returnVal}
     * 
     * @param element
     * @param attrNm
     * @param returnVal
     *        - value returned by getAttribute
     *  
     */
    public boolean alrg_getCheckBoxStatusUsingAttribute(WebElement element, String attrNm, String returnVal) {
        if (element.getAttribute(attrNm).matches(returnVal))
            return true;
        else
            return false;
    }

    /**
     * <b>Name:</b> alrg_setCheckBoxUsingAttribute<br>
     * <b>Description:</b> This method is used to select a check box, using
     * {@code alrg_getCheckBoxStatusUsingAttribute} method
     * 
     * @param element
     * @param attrNm
     * @param returnVal
     * @param setOnOff
     *        - string ON or OFF
     * @param elementDesc
     *  
     */
    public void alrg_setCheckBoxUsingAttribute(WebElement element, String attrNm, String returnVal, String setOnOff, String elementDesc) {
        if (!alrg_getCheckBoxStatusUsingAttribute(element, attrNm, returnVal) && setOnOff.equalsIgnoreCase("on")) {
            element.click();
            ALRGLogger.log(_log, "'" + elementDesc + "'" + " checkbox checked", EllieMaeLogLevel.reporter);
        } else if (alrg_getCheckBoxStatusUsingAttribute(element, attrNm, returnVal) && setOnOff.equalsIgnoreCase("off")) {
            element.click();
            ALRGLogger.log(_log, "'" + elementDesc + "'" + " checkbox unchecked", EllieMaeLogLevel.reporter);
        } else {
            ALRGLogger.log(_log, "Check box: '" + elementDesc + "'" + " already set to: " + setOnOff, EllieMaeLogLevel.reporter);
        }
    }

    /**
     * <b>Name:</b> alrg_isElementClickable<br>
     * <b>Description:</b> This method is boolean to checks if an element is
     * clickable
     * 
     * @param element
     *  
     */
    public boolean alrg_isElementClickable(WebElement element, String elementDescription) {
        try {
            element.click();
            ALRGLogger.log(_log, "Element: " + "'" + elementDescription + "' - clickable", EllieMaeLogLevel.reporter);
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "Element: " + "'" + elementDescription + "' - is not clickable", EllieMaeLogLevel.reporter);
            return false;
        }
    }

    /**
     * <b>Name:</b> alrg_isElementEnable<br>
     * <b>Description:</b> This method is boolean to checks if an element is
     * Enabled
     * 
     * @param element
     * @param elementDescription
     * @return
     */
    public boolean alrg_isElementEnable(WebElement element, String elementDescription) {
        try {
            ALRGLogger.log(_log, "Verifying if " + "'" + elementDescription + "'" + " is Enabled", EllieMaeLogLevel.reporter);
            alrg_scrollIntoViewElement(element, elementDescription);
            return element.isEnabled();
        } catch (Exception e) {
            return false;
        }

    }

    public boolean alrg_isElementEnable(By by, String elementDescription) {
        try {
            WebElement element = driver.findElement(by);
            return alrg_isElementEnable(element, elementDescription);
        } catch (Exception e) {
            return false;
        }
    }


    public boolean alrg_isElementDisabled(By by, String elementDescription) {
        WebElement element = driver.findElement(by);
        return alrg_isElementDisabled(element, elementDescription);
    }

    /**
     * Returns true is element is disabled
     * 
     * @param element
     * @param elementDescription
     * @return
     */
    public boolean alrg_isElementDisabled(WebElement element, String elementDescription) {

        ALRGLogger.log(_log, "Verifying if " + "'" + elementDescription + "'" + " is disalbed", EllieMaeLogLevel.reporter);
        alrg_scrollIntoViewElement(element, elementDescription);
        boolean isDisabled = "disabled".equals(element.getAttribute("disabled"));
        boolean isReadOnly = "true".equals(element.getAttribute("readonly"));
        return isDisabled || isReadOnly || !element.isEnabled();
    }


    /**
     * <b>Name:</b> alrg_isElementDisplayed<br>
     * <b>Description:</b> This method is boolean to checks if an element is
     * Displayed
     * 
     * @param element
     * @return
     */
    public boolean alrg_isElementDisplayed(WebElement element) {
    	
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean alrg_isElementDisplayed(WebElement element, int nSecs) {
        alrg_waitUntilElementDisplayed(element, nSecs);
        return alrg_isElementDisplayed(element);
    }

    /**
     * <b>Name:</b> alrg_isElementDisplayed<br>
     * <b>Description:</b> This method is boolean to checks if an element is
     * Displayed
     * 
     * @param element
     * @return
     */
    public boolean alrg_isElementDisplayed(By by) {
        try {
            WebElement element = driver.findElement(by);
            return alrg_isElementDisplayed(element);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean alrg_isElementDisplayed(By by, int nSecs) {
        alrg_waitUntilElementDisplayed(by, nSecs);
        return alrg_isElementDisplayed(by);
    }

    // Wait until element is displayed for particular number of seconds
    public boolean alrg_waitUntilElementDisplayed(WebElement ele, int sTime) {

        try {
            FluentWait<WebDriver> eWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(sTime)).pollingEvery(Duration.ofMillis(200))
                    .ignoring(NoSuchElementException.class, NullPointerException.class);
            eWait.until(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return alrg_isElementDisplayed(ele);
                }
            });
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "alrg_waitUntilElementDisplayed(), exception " + e.getMessage());
            return false;
        }
    }

    /**
     * <b>Name:</b> waitForElementToBeInvisible <b></br>
     * Description: </b>The method waits until the element becomes invisible
     * during the waiting time
     * 
     * @param locator
     * @param timeOutInSeconds
     * @param elementDesc
     *  
     */
    public boolean alrg_waitForElementToBeInvisible(final By locator, int timeOutInSeconds, String elementDesc) {
        long startTime = System.currentTimeMillis();
        try {
            new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.invisibilityOfElementLocated(locator));
            ALRGLogger.log(_log,
                    "In " + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()) + " The " + elementDesc + " disappeared",
                    EllieMaeLogLevel.debug);
            return true;
        } catch (NoSuchElementException e) {
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "Time out exception for wait invisibility (" + CommonUtility.countPerformTime(startTime, System.currentTimeMillis())
           		+ ") " + elementDesc + ":" + e);
            return false;
        }
    }

    public boolean alrg_waitUntilNumberOfElementsToBeMoreThan0(By locator, int sTime) {
    	return alrg_waitUntilNumberOfElementsToBeMoreThan(locator, 0, sTime);
    }
    
    public boolean alrg_waitUntilNumberOfElementsToBeMoreThan(By locator, int expectedNum, int sTime) {


    	long startTime = System.currentTimeMillis();
        try {
            FluentWait<WebDriver> eWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(sTime)).pollingEvery(Duration.ofMillis(200))
                    .ignoring(NoSuchElementException.class);
            eWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, expectedNum));
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "alrg_waitUntilListOfElementsSizeMoreThan0 (" + CommonUtility.countPerformTime(startTime, System.currentTimeMillis())
            	+ ") , exception " + e.getMessage());
            return false;
        }
    }

    public boolean alrg_waitUntilNumberOfElementsToBeMoreThan0(List<WebElement> elements, int sTime) {

    	long startTime = System.currentTimeMillis();
        try {
            FluentWait<WebDriver> eWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(sTime)).pollingEvery(Duration.ofMillis(200))
                    .ignoring(NoSuchElementException.class);
            eWait.until(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return elements.size() > 0;
                }
            });
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "alrg_waitUntilListOfElementsSizeMoreThan0 (" + CommonUtility.countPerformTime(startTime, System.currentTimeMillis())
            	+ ") , exception " + e.getMessage());
            return false;
        }
    }
    
    public boolean alrg_waitUntilNumberOfElementsToBe(By locator, int number, int sTime) {

        try {
            FluentWait<WebDriver> eWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(sTime)).pollingEvery(Duration.ofMillis(200))
                    .ignoring(NoSuchElementException.class);
            eWait.until(ExpectedConditions.numberOfElementsToBe(locator, number));
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "Error in alrg_waitUntilNumberOfElementsToBe() locator=" + locator + ", message=" + e.getMessage());
            return false;
        }
    }

    public boolean alrg_waitUntilListOfElementsPresent(By locator, int sTime) {

        try {
            FluentWait<WebDriver> eWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(sTime)).pollingEvery(Duration.ofMillis(200))
                    .ignoring(NoSuchElementException.class, NullPointerException.class);
            eWait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "alrg_waitUntilListOfElementsPresent(), exception " + e.getMessage());
            return false;
        }
    }

    public boolean alrg_waitUntilListOfElementsPresent(By locator) {

        try {
            defaultFWait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "alrg_waitUntilListOfElementsPresent(), exception " + e.getMessage());
            return false;
        }
    }

    public boolean alrg_waitUntilElementDisplayed(By locator, int sTime) {
        try {
            FluentWait<WebDriver> eWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(sTime)).pollingEvery(Duration.ofMillis(100))
                    .ignoring(NoSuchElementException.class, NullPointerException.class);
            eWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            
            alrg_waitUntilElementDisplayed(driver.findElement(locator), sTime);
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "alrg_waitUntilElementDisplayed(), exception " + e.getMessage());
            return false;
        }
    }

    public boolean alrg_waitUntilElementDisplayed(By locator) {
        return alrg_waitUntilElementDisplayed(locator, ALRGConsts.DEFAULT_WAIT_TIMEOUT_SECONDS);
    }

    /**
     * 
     * @param ele
     * @param sTime
     */
    public boolean alrg_waitUntilElementDisappears(WebElement ele, int sTime) {

        try {
            FluentWait<WebDriver> eWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(sTime)).pollingEvery(Duration.ofMillis(200));
            eWait.until(ExpectedConditions.invisibilityOf(ele));
            return true;
        } catch (NoSuchElementException e) {
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "alrg_waitUntilElementDisappears(), exception " + e.getMessage());
            return false;
        }
    }

    /**
     * <b>Name:</b> alrg_waitForElementExists<br>
     * <b>Description: </b>Method waiting till an element is exists
     * 
     * @param by
     * @param sTime
     *        - the number of repetitions expectations
     *  
     */
    public boolean alrg_waitForElementExists(By locator, int sTime) {

        long startTime = System.currentTimeMillis();
        try {
            FluentWait<WebDriver> eWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(sTime)).pollingEvery(Duration.ofMillis(200))
                    .ignoring(NoSuchElementException.class);
            eWait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ALRGLogger.log(_log, "In " + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()) + ", " + locator + " exists",
                    EllieMaeLogLevel.debug);
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "In " + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()) + ", " + locator + " not exists");
            return false;
        }
    }

    public boolean alrg_waitForElementEnabled(WebElement ele) {

        return alrg_waitForElementEnabled(ele, ALRGConsts.DEFAULT_WAIT_TIMEOUT_SECONDS);
    }

    // Wait until element is enabled for particular number of seconds
    public boolean alrg_waitForElementEnabled(WebElement ele, int sTime) {

        try {
            FluentWait<WebDriver> eWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(sTime)).pollingEvery(Duration.ofMillis(200))
                    .ignoring(NoSuchElementException.class);
            eWait.until(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return ele.isEnabled();
                }
            });
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "alrg_waitForElementEnabled(), exception " + e.getMessage());
            return false;
        }
    }

    // Wait until element is disabled for particular number of seconds
    public boolean alrg_waitForElementDisabled(WebElement ele, int sTime) {

        try {
            FluentWait<WebDriver> eWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(sTime)).pollingEvery(Duration.ofMillis(200))
                    .ignoring(NoSuchElementException.class);
            eWait.until(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver driver) {
     
                    return ele.isEnabled();
                }
            });
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "alrg_waitForElementdisabled(), exception " + e.getMessage());
            return false;
        }
    }

    
    
    public boolean alrg_waitForElementEnabled(By by, int sTime) {

        try {
            FluentWait<WebDriver> eWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(sTime)).pollingEvery(Duration.ofMillis(200))
                    .ignoring(NoSuchElementException.class);
            eWait.until(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return alrg_findElement(by).isEnabled();
                }
            });
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "alrg_waitForElementEnabled(), exception " + e.getMessage());
            return false;
        }
    }


    public boolean alrg_waitForElementToBeSelected(WebElement element, String setOnOff) {

        try {
            if ("on".equalsIgnoreCase(setOnOff)) {
                new WebDriverWait(driver, 1).until(ExpectedConditions.elementSelectionStateToBe(element, true));

            } else if ("off".equalsIgnoreCase(setOnOff)) {
                new WebDriverWait(driver, 1).until(ExpectedConditions.elementSelectionStateToBe(element, false));
            }
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "Check box: '" + element + "'" + " can't be set to: " + setOnOff + ", " + e.getMessage(), EllieMaeLogLevel.reporter);
            return false;
        }
    }

    
    public boolean alrg_waitUntilTextNotEmpty(By locator, int sTime) {

        try {
            FluentWait<WebDriver> eWait = 
                    new FluentWait<>(driver).withTimeout(Duration.ofSeconds(sTime)).pollingEvery(Duration.ofMillis(5000));
            eWait.until(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return StringUtils.isNotBlank(alrg_findElement(locator).getText());
                }
            });
                    
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "Error in alrg_waitUntilNumberOfElementsToBe() locator=" + locator + ", message=" + e.getMessage());
            return false;
        }
    }
    
    /**
     * Send tab key to an element
     */
    public boolean alrg_sendTab(WebElement ele, String description) {

        try {
            ele.sendKeys(Keys.TAB);
            //ALRGLogger.log(_log, "Sent tab key to element " + description, EllieMaeLogLevel.reporter);
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "Send tab key to element " + description + " failed, exception " + e.getMessage(), EllieMaeLogLevel.warn);
        } finally {
            waitForPageToLoad();
        }
        return false;
    }

    /**
     * <b>Name:</b>clickOnButton<br>
     * <b>Description:</b> This method to click on button which is unique on the
     * page
     * 
     * @param buttonName
     */
    public boolean alrg_clickOnButton(String buttonName) {

        ALRGLogger.log(_log, "Clicking on button " + buttonName);
        try {
            WebElement button = driver.findElement(ByAngular.buttonText(buttonName));
            alrg_scrollIntoViewElement(button, buttonName);
            alrg_waitForElementToBeClickable(button, buttonName);
            alrg_clickElementNoWait(button, buttonName);
            waitForPageToLoad();
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "Error clicking button '" + buttonName + "', exception " + e.getMessage(), EllieMaeLogLevel.reporter);
        }
        return false;
    }

    /**
     * <b>Name:</b> doubleClickElement<br>
     * <b>Description:</b> This method is used to double click on a webelement
     * 
     * @throws InterruptedException
     * 
     * 
     */
    public void alrg_doubleClickElement(WebElement element, String elementDescription) {

        if (element != null) {
            try {
                waitForPageToLoad();
                alrg_scrollIntoViewElement(element, elementDescription);
                Actions action = new Actions(driver);
                // Double click
                action.doubleClick(element).perform();
                ALRGLogger.log(_log, "doubleClicked on: " + "'" + elementDescription + "'", EllieMaeLogLevel.reporter);
            } catch (Exception e) {
                ALRGLogger.log(_log, "Fail to double click: " + elementDescription + " for " + elementDescription);
                ALRGLogger.log(_log, "Exception in doubleclickElementNG: " + e.getMessage());
            } finally {
                waitForPageToLoad();
            }
        } else {
            ALRGLogger.log(_log, "alrg_doubleClickElement() element is null!", EllieMaeLogLevel.reporter);
        }
    }

    public void alrg_moveToElement(WebElement element, String description) {
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.build().perform();
    }

    public String getBrowserWebdriverName() {
        Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
        String browserName = cap.getBrowserName().toLowerCase();
        return browserName;
    }

    public void alrg_waitForTextToAppearInTextBox(String textToAppear, WebElement element, String elementDescription) {

        if (element != null) {
            try {
                waitForPageToLoad();
                alrg_scrollIntoViewElement(element, elementDescription);
                WebDriverWait wait = new WebDriverWait(driver, 30);
                wait.ignoring(NoSuchElementException.class, NullPointerException.class).until(ExpectedConditions.textToBePresentInElement(element, textToAppear));
                ALRGLogger.log(_log, "Text is present in Text Field " + "'" + elementDescription + "'", EllieMaeLogLevel.reporter);

            } catch (Exception e) {
                ALRGLogger.log(_log, "Fail check if text is present in text field" + elementDescription + " for " + elementDescription);
                ALRGLogger.log(_log, "Exception in while waiting for text to be displayed in text field: " + e.getMessage());
            }
        } else {
            ALRGLogger.log(_log, "waitForTextToAppearInTextBox() element is null!", EllieMaeLogLevel.reporter);
        }
    }

    /**
     * <b>Name:</b> selectFrame<br>
     * <b>Description:</b> Selects a particular frame on the basis of the
     * parameters passed.<br>
     * 
     * @param frameId
     *  
     */
    public void alrg_selectFrame(String frameId) {
        try {
            driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
            try {
                driver.switchTo().frame(frameId);
                ALRGLogger.log(_log, "Frame has switched to: " + frameId);
            } catch (NoSuchFrameException e) {
                throw new Exception(e.getMessage(), e);
            }
        } catch (Exception e) {
            ALRGLogger.log(_log, "Fail to select Frame: " + e.getMessage());
        }
        // TODO - switch to this later
        /*
         * try {
         * defaultFWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameId));
         * } catch (Exception e) {
         * ALRGLogger.log(_log, "alrg_selectFrame(), exception " + e.getMessage());
         * }
         */
    }


    /**
     * <b>Name:</b> alrg_switchFromFrameToDefaultContent<br>
     * <b>Description:</b> This method is used to switch for a frame to default
     * content<br>
     * 
     *  
     */
    public void alrg_switchFromFrameToDefaultContent() {
        try {
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in switchFromFrameToDefaultContent : " + e.getMessage());
        }
    }


    /**
     * <b>Name:</b> switchToServiceLandingFrame<br>
     * <b>Description:</b> This method is used to switch to the service landing page frame
     */
    public void alrg_switchToServiceLandingFrame() {
        try {
            wait.until( ExpectedConditions.frameToBeAvailableAndSwitchToIt("slpIFrame"));
            driver.switchTo().frame("slpIFrame");
        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in alrg_switchToServiceLandingFrame : " + e.getMessage());
        } finally {
            alrg_waitForSpinnerToBeGone();
        }
    }

    /**
     * This function return element text using JavaScript.
     * This defers from alrg_getText() that the text is returned even if the element is hidden
     * 
     * @param element
     * @return
     */
    public String alrg_getTextJS(WebElement element, String desc) {
        try {
            String s = js.executeScript("return arguments[0].innerText;", element).toString();
            ALRGLogger.log(_log, "alrg_getTextJS() of element '" + desc + "' returns: " + "'" + s + "'", EllieMaeLogLevel.debug);
            return s;
        } catch (Exception e) {
            ALRGLogger.log(_log, "alrg_getTextJS(), Exception getting text of element '" + desc + "':" + e.getMessage());
          //TODO - change to return null
            return "NoValue";
        }
    }

    public String alrg_getText(By locator) {
        String value = null;
        try {
            WebElement element = alrg_findElement(locator);
            if (element != null) {
                alrg_scrollIntoViewElement(element, "Getting Text");
                value = element.getText();
            }
            return value;
        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in finding element : " + e.getMessage());
            //TODO - change to return null
            return "NoValue";
        }
    }

    /**
     * <b>Name:</b> alrg_getText<br>
     * <b>Description:</b> This method is used to get text from element
     */
    public String alrg_getText(WebElement element) {
        try {
            alrg_scrollIntoViewElement(element, "Getting Text");
            String value = element.getText();
            return value;
        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in finding element : " + e.getMessage());
            //TODO - change to return null
            return "NoValue";
        }
    }

    /**
     * <b>Name:</b> alrg_getBackgroundColor<br>
     * <b>Description:</b> This is method is used to get background color from
     * an element<br>
     * 
     * @param element
     * @param elementDescription
     *  
     */
    public String alrg_getBackgroundColor(WebElement element, String elementDescription) {
        String color = "";
        try {
            color = element.getCssValue("background-color").trim();
            ALRGLogger.log(_log, "Color of the " + elementDescription + " is " + "'" + color + "'", EllieMaeLogLevel.reporter);
        } catch (Exception e) {
            ALRGLogger.log(_log,
                    "Failed to get background color from" + elementDescription + ".Exception from alrg_getBackgroundColor: " + e.getMessage());
        }
        return color;
    }

    /**
     * <b>Name:</b> alrg_isBackgroundColorWhite<br>
     * <b>Description:</b> Check whether Background Color white or not<br>
     * 
     * @param element
     * @param elementDescription
     * @return alrg_isBackgroundColorWhite (true/false)
     *  
     */
    public boolean alrg_isBackgroundColorWhite(WebElement element, String elementDescription) {
        boolean color = false;
        try {
            if (alrg_getBackgroundColor(element, elementDescription).contentEquals("rgba(255, 255, 255, 1)")) {
                color = true;
            }
        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in alrg_isBackgroundColorWhite: " + e.getMessage());
        }
        return color;
    }

    /**
     * <b>Name:</b> uploadFileUsingAutoIt<br>
     * <b>Description:</b> This method can be used to upload a file using AutoIt exe file<br>
     * 
     * <ul style="list-style-type:none">
     * <b><i>Use the following steps for creating and running AutoIt exe file:</i></b>
     * <li>- Downloaded and install AutoIt @see
     * <a href="http://www.autoitscript.com/site/autoit/downloads/">AutoIt
     * downloads</a>
     * <li>- Write the code as below in notepad, change the file name that you
     * want to upload and saved it as Test1txt.au3 or any other name<br>
     * <blockquote>
     * 
     * <pre>
     * {@code WinWaitActive("Open")
     * Send("\\corp.elmae\root\QA\Public\EDM_TestDocs\SupportedFileTypes\Test1.txt")
     * Send("{ENTER}")}
     * </pre>
     * 
     * </blockquote>
     * <li>- Right click on this .au3 file and compile it
     * <li>- Set the path of the AutoIt executable with the file name as a parameter to the method (see example below):
     * </ul>
     * 
     * <b>Example:</b>
     * {@code emActions.uploadFileUsingAutoIt("/src/test/resources/com/elliemae/EncwMobile/baseline/Test1txt.exe");}
     * <p>
     * 
     * @param pathWithFile
     *        -
     *        {@code "/src/test/resources/com/elliemae/EncwMobile/baseline/Test1txt.exe"}
     *  
     */
    public void uploadFileUsingAutoIt(String pathWithFile) {
        String[] commands = new String[] {};
        try {
            commands = new String[] {System.getProperty("user.dir") + pathWithFile};
            Runtime.getRuntime().exec(commands);
            ALRGLogger.log(_log, "File path for AutoIt file: " + pathWithFile);
        } catch (IOException e) {
            ALRGLogger.log(_log, "Exception in uploadFileUsingAutoIt: " + e.getMessage());
        }
    }

    /**
     * <b>Name:</b> alrg_refreshWin<br>
     * <b>Description:</b> This method is used to refresh a window<br>
     * 
     *  
     */
    public void alrg_refreshWin() {
        try {
            driver.navigate().refresh();
            waitForPageToLoad();
        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in alrg_refreshWin: " + e.getMessage());
        }
        ALRGLogger.log(_log, "The window: " + driver.getTitle() + " has been refreshed");
    }

    /**
     * <b>Name:</b> alrg_dropFile<br>
     * <b>Description:</b> This method is used to simulate the
     * {@code dragenter, dragover} and {@code drop} events on the targeted
     * element with the file set in the {@code dataTransfer} object<br>
     * <p>
     * <b>Example:</b><br>
     * {@code emActions.alrg_dropFile(new File("/src/test/resources/com/elliemae/EncwMobile/baseline/OrderCredProvLst.txt"), unsgdFileDropArea, 0, 0);}
     * 
     * @param filePath
     * @param target
     *        - webelement
     * @param offsetX
     *        - 0
     * @param offsetY
     *        - 0
     */
    public void alrg_dropFile(File filePath, WebElement target, int offsetX, int offsetY) {

        if (!filePath.exists())
            throw new WebDriverException("File not found: " + filePath.toString());

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, 30);

        String JS_DROP_FILE = "var target = arguments[0]," + "    offsetX = arguments[1]," + "    offsetY = arguments[2],"
                + "    document = target.ownerDocument || document," + "    window = document.defaultView || window;" + ""
                + "var input = document.createElement('INPUT');" + "input.type = 'file';" + "input.style.display = 'none';"
                + "input.onchange = function () {" + "  var rect = target.getBoundingClientRect(),"
                + "      x = rect.left + (offsetX || (rect.width >> 1))," + "      y = rect.top + (offsetY || (rect.height >> 1)),"
                + "      dataTransfer = { files: this.files };" + "" + "  ['dragenter', 'dragover', 'drop'].forEach(function (name) {"
                + "    var evt = document.createEvent('MouseEvent');"
                + "    evt.initMouseEvent(name, !0, !0, window, 0, 0, 0, x, y, !1, !1, !1, !1, 0, null);" + "    evt.dataTransfer = dataTransfer;"
                + "    target.dispatchEvent(evt);" + "  });" + "" + "  setTimeout(function () { document.body.removeChild(input); }, 25);" + "};"
                + "document.body.appendChild(input);" + "return input;";

        WebElement input = (WebElement) jse.executeScript(JS_DROP_FILE, target, offsetX, offsetY);
        input.sendKeys(filePath.getAbsoluteFile().toString());
        wait.until(ExpectedConditions.stalenessOf(input));
    }

    /**
     * <b>Name:</b> alrg_getCheckBoxStatusUsingJS<br>
     * <b>Description:</b> This method is boolean to get status of the check box
     * using java script.
     * 
     * @param element
     *  
     */
    public boolean alrg_getCheckBoxStatusUsingJS(WebElement element) {
        if (((JavascriptExecutor) driver).executeScript("return document.querySelector('input[type=\"checkbox\"]:valid').checked;", element)
                .toString().matches("true"))
            return true;
        else
            return false;
    }

    /**
     * <b>Name:</b> alrg_getCheckBoxElementStatusUsingJS<br>
     * <b>Description:</b> This method is boolean to get status of the check box for the given element only
     * using java script.
     * 
     * @param element
     * 
     */
    public boolean alrg_getCheckBoxElementStatusUsingJS(WebElement element) {
        if (((JavascriptExecutor) driver).executeScript("return arguments[0].checked;", element).toString().matches("true"))
            return true;
        else
            return false;
    }

    /**
     * <b>Name:</b> alrg_setCheckBoxWithJS<br>
     * <b>Description:</b> This method is used to check a check box with method
     * {@code alrg_getCheckBoxStatusUsingJS}
     * 
     * @param element
     * @param setOnOff
     *        - string ON or OFF
     * @param elementDesc
     *  
     */
    public void alrg_setCheckBoxWithJS(WebElement element, String setOnOff, String elementDesc) {

        if (!alrg_getCheckBoxStatusUsingJS(element) && setOnOff.equalsIgnoreCase("on")) {
            element.click();
            alrg_waitForElementToBeSelected(element, setOnOff);
            ALRGLogger.log(_log, "'" + elementDesc + "'" + " checkbox checked", EllieMaeLogLevel.reporter);
        } else if (alrg_getCheckBoxStatusUsingJS(element) && setOnOff.equalsIgnoreCase("off")) {
            element.click();
            alrg_waitForElementToBeSelected(element, setOnOff);
            ALRGLogger.log(_log, "'" + elementDesc + "'" + " checkbox unchecked", EllieMaeLogLevel.reporter);
        } else {
            ALRGLogger.log(_log, "Check box: '" + elementDesc + "'" + " already set to: " + setOnOff, EllieMaeLogLevel.reporter);
        }
    }

    /**
     * <b>Name:</b> closeActiveBrowserTab <br>
     * <b>Description:</b> This method is used to close currently active browser
     * tab or window
     * 
     *  
     */
    public void alrg_closeActiveBrowserTab() {
        switchToCurrentWindow();
        try {
            ((JavascriptExecutor) driver).executeScript("window.close();", driver.findElement(By.cssSelector("body")));
        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in closeActiveTab: " + e.getMessage());
        }
    }

    /**
     * <b>Name:</b> alrg_setCheckBoxWithJS<br>
     * <b>Description:</b> This method is used to check a check box with method
     * {@code alrg_getCheckBoxStatusUsingJSWithId} and click on a check box using
     * Actions<br>
     * 
     * @param element
     * @param id
     *        - string id of an element
     * @param setOnOff
     *        - string ON or OFF
     * @param elementDesc
     *  
     */
    public void alrg_setCheckBoxWithJS(WebElement element, String id, String setOnOff, String elementDesc) {

        Actions builder = new Actions(driver);
        if (!alrg_getCheckBoxStatusUsingJSWithId(element, id) && setOnOff.equalsIgnoreCase("on")) {
            builder.moveToElement(element).click().build().perform();
            alrg_waitForElementToBeSelected(element, setOnOff);
            ALRGLogger.log(_log, "'" + elementDesc + "'" + " checkbox checked", EllieMaeLogLevel.reporter);
        } else if (alrg_getCheckBoxStatusUsingJSWithId(element, id) && setOnOff.equalsIgnoreCase("off")) {
            builder.moveToElement(element).click().build().perform();
            alrg_waitForElementToBeSelected(element, setOnOff);
            ALRGLogger.log(_log, "'" + elementDesc + "'" + " checkbox unchecked", EllieMaeLogLevel.reporter);
        } else {
            ALRGLogger.log(_log, "Check box: '" + elementDesc + "'" + " already set to: " + setOnOff, EllieMaeLogLevel.reporter);
        }
    }

    /**
     * <b>Name:</b> alrg_getCheckBoxStatusUsingJSWithId<br>
     * <b>Description:</b> This method is boolean to get status of the check box
     * using java script with element id.
     * 
     * @param element
     * @param id
     *        - string of an element
     *  
     */
    public boolean alrg_getCheckBoxStatusUsingJSWithId(WebElement element, String id) {
        if (((JavascriptExecutor) driver).executeScript("return document.getElementById('" + id + "').checked;", element).toString().matches("true"))
            return true;
        else
            return false;
    }

    /**
     * <b>Name:</b> alrg_getIdFromAttributeInnerHTML<br>
     * <b>Description:</b> This method is used to get a id of an element from
     * attribute innerHTML
     * 
     * @param element
     *  
     */
    public String alrg_getIdFromAttributeInnerHTML(WebElement element) {
        String id = "";
        id = element.getAttribute("innerHTML").split("id=\"")[1].split("\" ")[0];
        ALRGLogger.log(_log, "ID from the innerHTML: " + id, EllieMaeLogLevel.reporter);
        return id;
    }

    /**
     * <b>Name:</b> alrg_getFileNameFromPath<br>
     * <b>Description:</b> This method is used to get a file name from the path
     * 
     * @param pathWithFile
     *  
     */
    public String alrg_getFileNameFromPath(String pathWithFile) {
        String fileNm = "";
        Path path = Paths.get(pathWithFile);
        fileNm = path.getFileName().toString();
        ALRGLogger.log(_log, "File name from the path: " + fileNm, EllieMaeLogLevel.reporter);
        return fileNm;
    }

    /**
     * <b>Name:</b> isAttributeClassContainsWord<br>
     * <b>Description:</b> This method checks whether attribute class contain
     * search word
     * 
     * @param element
     * @param srchWord
     * @return isAttributeClassContainsWord (true/false)
     *  
     * 
     */
    public boolean alrg_isAttributeClassContainsWord(WebElement element, String srchWord) {
        if (element.getAttribute("class").contains(srchWord))
            return true;
        else
            return false;
    }

    /**
     * <b>Name:</b> isElementExists<br>
     * <b>Description:</b> This method checks whether an element exists
     * 
     * @param By
     * @return isElementExists (true/false)
     *  
     * 
     */
    public boolean alrg_isElementExists(By by) {
        boolean isExists = true;
        try {
            driver.findElement(by);
        } catch (NoSuchElementException e) {
            isExists = false;
        }
        return isExists;
    }

    /**
     * <b>Name:</b> alrg_simplyClicksUsingJS<br>
     * <b>Description: </b>Method clicks on a specific element using java
     * script.
     * 
     * @param element
     * @param elementDescription
     *  
     */
    public void alrg_simplyClicksUsingJS(WebElement element, String elementDescription) {
        if (element != null) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                ALRGLogger.log(_log, "Clicked on: " + "'" + elementDescription + "'", EllieMaeLogLevel.reporter);
                try {
                    alrg_waitForSpinnerToBeGone();
                } catch (Exception ex) {
                    // ALRGLogger.log(_log, "Exception from waitForPageToLoad: " + ex.toString());
                }
            } catch (Exception e) {
                ALRGLogger.log(_log, "Fail to click: " + elementDescription + " for " + elementDescription);
                ALRGLogger.log(_log, "Exception in alrg_clickElementNoWait: " + e.getMessage());
            } finally {
            }
        }
    }

    /**
     * <b>Name:</b> alrg_isListContainsAll<br>
     * <b>Description:</b> This method checks whether an all items contains a
     * list
     * 
     * <p>
     * <b>Example:</b><br>
     * {@code String[] lstBorNm = {"All",testNm +" and "+"Co_"+testNm+" "+
     * loanNm,testNm +" "+ bor2Nm};
     * {@code emActions.alrg_isListContainsAll(webelement,lstBorNm,"Borrower Pair");}
     * 
     * @param element
     * @param expected
     *        - list
     * @param elementDesc
     *        - list descripton
     * @return alrg_isListContainsAll (true/false)
     *  
     * 
     */
    public boolean alrg_isListContainsAll(WebElement element, String[] expected, String elementDesc) {
        Select ls = new Select(element);
        List<WebElement> options = ls.getOptions();
        List<String> arLst = Arrays.asList(expected);
        List<String> expectedElements = new ArrayList<>(arLst);
        List<String> stringElements = new ArrayList<>(options.size());

        for (WebElement we : options) {
            stringElements.add(we.getAttribute("innerText").trim());
        }
        ALRGLogger.log(_log, "Options in dropdown are':  " + stringElements, EllieMaeLogLevel.reporter);
        return stringElements.containsAll(expectedElements);
    }

    public boolean alrg_isListContainsAll(WebElement element, List<String> expected, String elementDesc) {
        return alrg_isListContainsAll(element, expected.toArray(new String[0]), elementDesc);
    }

    public boolean alrg_isListContainsAll(String[] actual, String[] expected, String elementDesc) {

        List<String> expectedElements = Arrays.asList(expected);
        List<String> actualElements = Arrays.asList(actual);
        ALRGLogger.log(_log, "Options in dropdown are':  " + actualElements, EllieMaeLogLevel.reporter);
        return actualElements.containsAll(expectedElements);
    }

    /**
     * <b>Name:</b> alrg_getLastListOption<br>
     * <b>Description: </b>Method get last option from a drop down list
     * 
     * @param element
     *  
     */
    public String alrg_getLastListOption(WebElement element) {
        Select select = new Select(element);
        List<WebElement> ls = select.getOptions();
        int numElements = ls.size();
        select.selectByIndex(numElements - 1);
        WebElement lastNm = select.getFirstSelectedOption();
        return lastNm.getAttribute("innerText");
    }

    /**
     * <b>Name:</b> alrg_getTextFromBrowserAlert<br>
     * <b>Description: </b>This method is used to get text from a Browser alert
     * 
     *  
     */
    public String alrg_getTextFromBrowserAlert() {
        return driver.switchTo().alert().getText();
    }

    /**
     * <b>Name:</b> alrg_isBrowserAlertPresent<br>
     * <b>Description:</b> This method checks whether an Browser alert exists
     * 
     *  
     * 
     */
    public boolean alrg_isBrowserAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException Ex) {
            return false;
        }
    }

    /**
     * <b>Name:</b> alrg_getIdFromAttributeInnerHTML<br>
     * <b>Description:</b> This method is used to get a id of an element from
     * attribute innerHTML
     * 
     * @param element
     * @param firstRegex
     * @param secondRegex
     *  
     */
    public String alrg_getIdFromAttributeInnerHTML(WebElement element, String firstRegex, String secondRegex) {
        String id = "";
        id = element.getAttribute("innerHTML").split(firstRegex)[1].split(secondRegex)[0];
        ALRGLogger.log(_log, "ID from the innerHTML: " + id, EllieMaeLogLevel.reporter);
        return id;
    }

    /**
     * This function converts an element if necessary.
     * For date picker element, it returns the input tag that's contained within em-datepicker tag
     * 
     * @param e
     * @return
     */
    public WebElement alrg_elementConverter(WebElement e) {

        // add code to accommodate app datapicker change. It was an input field before, now changed to em-datepicker
        if ("em-datepicker".equalsIgnoreCase(e.getTagName())) {
            // find its descendant that's an input field
            return e.findElement(By.cssSelector(ALRGConsts.EM_DATE_PICKER_INPUT));
        }
        return e;
    }

    /**
     * <b>Name:</b> alrg_isListContainsAllNoSelect<br>
     * <b>Description:</b> This method checks whether an all items contains a
     * list without select attribute<br>
     * 
     * <p>
     * <b>Example:</b><br>
     * {@code String[] lstBorNm = {"All",testNm +" and "+"Co_"+testNm+" "+
     * loanNm,testNm +" "+ bor2Nm};
     * {@code emActions.alrg_isListContainsAll(listWebElement,lstBorNm,"Borrower Pair");}
     * 
     * @param listWebElement
     * @param expected
     *        - list
     * @param elementDesc
     *        - list descripton
     * @return alrg_isListContainsAll (true/false)
     *  
     * 
     */
    public boolean alrg_isListContainsAllNoSelect(List<WebElement> listWebElement, String[] expected, String elementDesc) {
        List<String> arLst = Arrays.asList(expected);
        List<String> expectedElements = new ArrayList<>(arLst);
        List<String> stringElements = new ArrayList<>(listWebElement.size());

        for (WebElement we : listWebElement) {
            stringElements.add(we.getAttribute("innerText").trim());
        }
        return stringElements.containsAll(expectedElements);
    }

    public void alrg_waitForSpinnerToBeGone() {
        alrg_waitForSpinnerToBeGone(ALRGConsts.MAX_WAIT_TIMEOUT_SECONDS);
    }

    public abstract void alrg_waitForSpinnerToBeGone(int nSecs);

    public abstract boolean alrg_waitForCalcSpinnerToBeGone();

    public boolean alrg_waitForPipelineSpinnerToGo(int waitSeconds) {
        // Fetching Loan...
        return alrg_waitForTextSpinnerToGo("Fetching", waitSeconds);
    }

    public boolean alrg_waitForLoadingSpinnerToGo(int waitSeconds) {
        // Fetching Loan...
        return alrg_waitForTextSpinnerToGo("Loading", waitSeconds);
    }

    public boolean alrg_waitForValidatingMilestoneSpinnerToGo(int waitSeconds) {
        // Validating Milestone. Please Standby...
        return alrg_waitForTextSpinnerToGo("Validating Milestone", waitSeconds);
    }


    public boolean alrg_waitForBusinessRulesSpinnerToGo(int waitSeconds) {
        // Saving Loan Data and Evaluating Business Rules...
        return alrg_waitForTextSpinnerToGo("Saving Loan Data and Evaluating Business Rules", waitSeconds);
    }

    /**
     *
     * @param text
     * @param waitSeconds
     */
    // TODO: This is only tested in Desktop so far. Need to see if mobile needs a different implementation
    public boolean alrg_waitForTextSpinnerToGo(String text, int waitSeconds) {

        try {
            long startTime = System.currentTimeMillis();
            final List<WebElement> loadingElementList = driver.findElements(By.cssSelector(ALRGConsts.SPINNER_BLOCK_UI_MSG));
            new WebDriverWait(driver, waitSeconds).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    for (WebElement ele : loadingElementList) {
                        if (alrg_getTextJS(ele, "spinner block message").toLowerCase().contains(text.toLowerCase()) && ele.isDisplayed())
                            return false;
                    }
                    return true;
                }
            });
            ALRGLogger.log(_log, "In " + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()) + " The spinner that contains " + text
                    + " disappeared", EllieMaeLogLevel.debug);
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "wait for spinner that contains " + text + " to disappear:" + e.getMessage(), EllieMaeLogLevel.error);
            return false;
        }
    }

    /**
     * Clicking on an element using JS
     * 
     * @param element
     * @return
     */
    public boolean alrg_clickElementUsingAngularJS(WebElement element, String elementDescription) {
        try {
            // ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            ((JavascriptExecutor) driver).executeScript("angular.element(arguments[0]).triggerHandler('click');", element);

            ALRGLogger.log(_log, "alrg_clickElementUsingAngularJS() Clicked on: " + "'" + elementDescription + "'", EllieMaeLogLevel.reporter);
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "alrg_clickElementUsingAngularJS(), Exception to click on element:: " + e.getMessage());
            return false;
        }
    }

    /**
     * <b>Name:</b> alrg_getIdFromAttributeInnerHTML<br>
     * <b>Description:</b> This method is used to get a parent node of an
     * element<br>
     * 
     * @param element
     *  
     */
    public WebElement alrg_getParentNode(WebElement element) {
        return (WebElement) ((JavascriptExecutor) driver).executeScript("return arguments[0].parentNode;", element);
    }

    /**
     * Return the URL of the tab of provided index
     */
    public String getURLfromTab(int idx) {

        String url = "";
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        if (tabs != null && tabs.size() > idx) {
            driver.switchTo().window(tabs.get(idx));
            url = driver.getCurrentUrl();
            driver.close();
        }
        if (tabs != null && tabs.size() > 0) {
            driver.switchTo().window(tabs.get(0));
        }

        return url;
    }

    /**
     * <b>Name:</b> alrg_getClientHeight<br>
     * <b>Description:</b> This method is used to get a height of an element<br>
     * 
     * @param element
     * @param elementDescription
     *  
     */
    public long alrg_getClientHeight(WebElement element, String elementDescription) {
        long height = (long) ((JavascriptExecutor) driver).executeScript("return arguments[0].clientHeight;", element);
        ALRGLogger.log(_log, "Height of: " + "'" + elementDescription + "'" + " = " + height, EllieMaeLogLevel.reporter);
        return height;
    }


    /*
     * @Deprecated
     * public void waitForCalculatingSpinnerToGo() {
     * 
     * waitForTextSpinnerToGo("Calculating", 120);
     * }
     * 
     * @Deprecated
     * public void waitForTextSpinnerToGo(String text, int waitSeconds) {
     * 
     * try {
     * long startTime = System.currentTimeMillis();
     * 
     * //TODO - temporary testing code for spinner change in xp environment
     * if( FrameworkConsts.ENVIRONMENTNAME.equalsIgnoreCase("xp")) {
     * if (Exists(By.cssSelector(ALRGConsts.SPINNER_BLOCK_UI_CONTAINER), "Loading Container")) {
     * FluentWait<WebDriver> fluentWait = new FluentWait<>(driver).withTimeout(ALRGConsts.MAX_WAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
     * .pollingEvery(200, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);
     * 
     * fluentWait.until(new Function<WebDriver, Boolean> () {
     * public Boolean apply(WebDriver driver) {
     * WebElement e = driver.findElement(By.cssSelector(ALRGConsts.SPINNER_BLOCK_UI_CONTAINER));
     * return (e == null) ? false : "opacity: 0;".equalsIgnoreCase(e.getAttribute("style"));
     * }
     * });
     * }
     * ALRGLogger.log(_log, "In " + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()) + " The XP spinner that contains " + text +
     * " disappeared",
     * EllieMaeLogLevel.info);
     * } else {
     * final List<WebElement> loadingElementList = driver.findElements(By.cssSelector(ALRGConsts.SPINNER_BLOCK_UI_MSG));
     * new WebDriverWait(driver, waitSeconds).until(new ExpectedCondition<Boolean>() {
     * public Boolean apply(WebDriver d) {
     * for (WebElement ele : loadingElementList) {
     * if (ele.getText().toLowerCase().contains(text.toLowerCase()) && ele.isDisplayed())
     * return false;
     * }
     * return true;
     * }
     * });
     * ALRGLogger.log(_log, "In " + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()) + " The spinner that contains " + text +
     * " disappeared",
     * EllieMaeLogLevel.debug);
     * // }
     * 
     * } catch (Exception e) {
     * ALRGLogger.log(_log, "wait for spinner that contains " + text + " to disappear:" + e.getMessage(), EllieMaeLogLevel.error);
     * }
     * }
     */

    /**
     * <b>Name:</b> alrg_simplyClicksElementNoWaitForSpinner<br>
     * <b>Description: </b>Method clicks on a specific element without waiting
     * and scrolling to the element and waiting for spinner to be gone<br>
     * 
     * @param element
     * @param elementDescription
     *  
     */
    public boolean alrg_simplyClicksElementNoWaitForSpinner(WebElement element, String elementDescription) {
        boolean isClicked = false;
        if (element != null) {
            try {
                element.click();
                ALRGLogger.log(_log, "alrg_simplyClicksElementNoWaitForSpinner() Clicked on: " + "'" + elementDescription + "'",
                        EllieMaeLogLevel.reporter);
                isClicked = true;
            } catch (Exception e) {
                ALRGLogger.log(_log,
                        "alrg_simplyClicksElementNoWaitForSpinner() Fail to click: " + elementDescription + " because of error: " + e.getMessage(),
                        EllieMaeLogLevel.debug);
                // try a workaround instead
                isClicked = alrg_clickElementUsingJS(element, elementDescription);
            }
        }
        return isClicked;
    }

    public String[] getDropdownValues(WebElement ele) {

        alrg_scrollIntoViewElement(ele, ele.toString());
        Select ls = new Select(ele);
        List<WebElement> options = ls.getOptions();
        List<String> values = new ArrayList<>(options.size());

        for (WebElement we : options) {
            values.add(we.getAttribute("innerText").trim());
        }
        String[] valuesArr = new String[values.size()];
        return values.toArray(valuesArr);
    }

    /**
     * <b>Name:</b> alrg_getSelectedOptionTextUsingJs<br>
     * <b>Description:</b> This is method is used to fetch the value within a
     * drop down element, which does not have value attribute populated.
     * 
     *
     */
    public String alrg_getSelectedOptionTextUsingJs(WebElement element) {
        String attValue = "";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object o = js.executeScript("return arguments[0].value;", element);
        attValue = (o == null) ? "" : o.toString();
        ALRGLogger.log(_log, "Text Box has Value: " + attValue, EllieMaeLogLevel.reporter);
        return attValue;
    }

    
    public boolean alrg_waitForTextToClear(WebElement element, String elementDesc) {

    	long startTime = System.currentTimeMillis();
        try {
            defaultFWait.until(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return StringUtils.isEmpty(element.getAttribute("value"));
                }
            });
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "Time out (" + CommonUtility.countPerformTime(startTime, System.currentTimeMillis())
            	+ ") exception for wait visibility " + elementDesc + ":" + e);
            return false;
        }             
    }
    
    public boolean alrg_waitForTextToBe(WebElement element, String elementDesc, String text, int timeOutInSeconds) {
        long startTime = System.currentTimeMillis();
        try {
            new WebDriverWait(driver, timeOutInSeconds).ignoring(NoSuchElementException.class, NullPointerException.class)
                .until(ExpectedConditions.textToBePresentInElement(element, text));
            ALRGLogger.log(_log, "In " + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()) + " The " + elementDesc + " appeared",
                    EllieMaeLogLevel.debug);
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "Time out (" + CommonUtility.countPerformTime(startTime, System.currentTimeMillis())
            	+ ") exception for wait visibility " + elementDesc + ":" + e);
            return false;
        }
    }
    
    /**
     * <b>Name:</b> alrg_waitForElementToBeVisible <b></br>
     * Description: </b>The method waits until the element becomes visible
     * during the waiting time
     * 
     * @param element
     * @param timeOutInSeconds
     * @param elementDesc
     *  
     */
    public boolean alrg_waitForElementToBeVisible(WebElement element, int timeOutInSeconds, String elementDesc) {
        long startTime = System.currentTimeMillis();
        try {
            new WebDriverWait(driver, timeOutInSeconds)
                .ignoring(NoSuchElementException.class, NullPointerException.class)
                .until(ExpectedConditions.visibilityOf(element));
            ALRGLogger.log(_log, "In " + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()) + " The " + elementDesc + " appeared",
                    EllieMaeLogLevel.debug);
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "Time out (" + CommonUtility.countPerformTime(startTime, System.currentTimeMillis())
            	+ ") exception for wait visibility " + elementDesc + ":" + e);
            return false;
        }
    }

    public boolean alrg_waitForElementToBeVisible(By locator, int timeOutInSeconds, String elementDesc) {
        long startTime = System.currentTimeMillis();
        try {
            new WebDriverWait(driver, timeOutInSeconds).ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));  
            ALRGLogger.log(_log, "In " + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()) + " The " + elementDesc + " appeared",
                    EllieMaeLogLevel.debug);
            return true;
        } catch (Exception e) {
/*        	WebElement ele = alrg_findElement(locator);
        	ALRGLogger.log(_log, ele == null ? (elementDesc + " not found") : (elementDesc + " displayed? " + ele.isDisplayed()));*/
        	ALRGLogger.log(_log, "Time out (" + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()) + 
        			") exception for wait visibility " + elementDesc + ":" + e);
        	//takeScreenShot("alrg_waitForElementToBeVisible");
            return false;
        }
    }

    /**
     * <b>Name:</b> alrg_waitForElementToBeClickable <b></br>
     * Description: </b>The method waits until the element becomes clickable
     * 
     * @param locator
     * @param elementDesc
     *  
     */
    public boolean alrg_waitForElementToBeClickable(WebElement element, String elementDesc) {
        long startTime = System.currentTimeMillis();
        try {
            defaultFWait.until(ExpectedConditions.elementToBeClickable(element));
            ALRGLogger.log(_log,
                    "In " + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()) + " the " + elementDesc + " become clickable",
                    EllieMaeLogLevel.debug);
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "alrg_waitForElementToBeClickable() Time out (" + CommonUtility.countPerformTime(startTime, System.currentTimeMillis())
            	+ ") exception waiting for '" + elementDesc + "' to become clickable: " + e);
            return false;
        }
    }

    public boolean alrg_waitForElementClassToBeActive(WebElement element, String elementDesc, int secs) {

        long startTime = System.currentTimeMillis();
        try {
            FluentWait<WebDriver> eWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(secs)).pollingEvery(Duration.ofMillis(200))
                    .ignoring(NoSuchElementException.class);
            eWait.until(ExpectedConditions.attributeContains(element, "class", "active"));
            ALRGLogger.log(_log,
                    "In " + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()) + " the " + elementDesc + " became active",
                    EllieMaeLogLevel.debug);
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log,
                    "In " + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()) + " the " + elementDesc + " did NOT become active");
            return false;
        }
    }

    /**
     * <b>Name:</b> waitForNumberOfWindows<br>
     * <b>Description:</b> waiting for number of the window.
     * 
     * @param numberOfWindows
     */
    public boolean alrg_waitForNumberOfWindows(final int numberOfWindows) {

        long startTime = 0;
        try {
            startTime = System.currentTimeMillis();
            defaultFWait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindows));
            ALRGLogger.log(_log,
                    "Passed to wait for the new window within - " + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()),
                    EllieMaeLogLevel.debug);
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "Fail to wait for the new window within - " + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()));
            ALRGLogger.log(_log, "Exception in waitForNumberOfWindows: " + e.getMessage());
            return false;
        }
    }


    /**
     * <b>Name:</b>pressKey<br>
     * <b>Description:</b> Press a Key
     * e.g objEllieMaeActions.pressKey(driver.findElement(By.xpath("//input[@placeholder='Password']")),Keys.ENTER);
     * 
     * @param element
     */
    public void pressKey(WebElement element, Keys key) {
        element.sendKeys(key);

    }



    /**
     * <b>Name:</b> checkPageIsReady() <b></br>
     * Description: </b>The method waits for JQ, Angular and JS to complete if any exists during the
     * waiting time
     * 
     * 
     */
    public void waitForPageToLoad() {

        try {
            long startTime = System.currentTimeMillis();
            // waitUntilJQueryReady();
            waitUntilAngularReady();
            waitUntilDocumentReady();
            alrg_waitForSpinnerToBeGone();

            ALRGLogger.log(_log, "The waitForPageToLoad took - " + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()),
                    perfLogLevel(startTime, System.currentTimeMillis()));
        } catch (Exception e) {
            ALRGLogger.log(_log, "waitForPageToLoad():" + e.getMessage(), EllieMaeLogLevel.error);
        }
    }


    public void waitForJQueryLoad() {

        // Wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) js.executeScript("return jQuery.active") == 0);
        // Get JQuery is Ready
        boolean jqueryReady = (Boolean) js.executeScript("return jQuery.active==0");
        // Wait JQuery until it is Ready!
        if (!jqueryReady) {
            // Wait for jQuery to load
            defaultFWait.until(jQueryLoad);
        }
    }


    // Wait for Angular Load
    public void waitForAngularLoad() {

        String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";
        // Wait for ANGULAR to load
        ExpectedCondition<Boolean> angularLoad =
                driver -> Boolean.valueOf(((JavascriptExecutor) driver).executeScript(angularReadyScript).toString());
        // Get Angular is Ready
        boolean angularUnDefined = (Boolean) js.executeScript("return window.angular === undefined");
        if (angularUnDefined) {
            try {
                boolean angularReady = Boolean.valueOf(js.executeScript(angularReadyScript).toString());

                // Wait ANGULAR until it is Ready!
                if (!angularReady) {
                    // Wait for Angular to load
                    defaultFWait.until(angularLoad);
                }
            } catch (WebDriverException e) {
                // ALRGLogger.log(_log, "waitForAngularLoad() exception:"+e.getMessage());
            }
        }
    }

    public void waitUntilDocumentReady() {

        long startTime = System.currentTimeMillis();

        // Wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = driver -> js.executeScript("return document.readyState").toString().equals("complete");
        // Get JS is Ready
        boolean jsReady = (Boolean) js.executeScript("return document.readyState").toString().equals("complete");
        // Wait Javascript until it is Ready!
        if (!jsReady) {
            defaultFWait.until(jsLoad);
            ALRGLogger.log(_log, "The waitUntilDocumentReady() took - " + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()),
                    EllieMaeLogLevel.info);
        } else {
            // ALRGLogger.log(_log, "JS is loaded...");
        }
    }

    // Wait Until JQuery and JS Ready
    public void waitUntilJQueryReady() {

        long startTime = System.currentTimeMillis();
        // First check that JQuery is defined on the page. If it is, then wait AJAX
        Boolean jQueryDefined = (Boolean) js.executeScript("return typeof jQuery != 'undefined'");
        if (jQueryDefined) {
            // Pre Wait for stability (Optional)
            // alrg_waitImplicitly(1);

            // Wait JQuery Load
            waitForJQueryLoad();

            // Post Wait for stability (Optional)
            // alrg_waitImplicitly(1);
            /*
             * ALRGLogger.log(_log, "The waitUntilAngularReady() took - "
             * + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()));
             */
        } else {
            // ALRGLogger.log(_log, "jQuery is not defined on this site");
        }
    }

    // Wait Until Angular and JS Ready
    protected void waitUntilAngularReady() {

        long startTime = System.currentTimeMillis();
        // First check that ANGULAR is defined on the page. If it is, then wait ANGULAR
        Boolean angularUnDefined = (Boolean) js.executeScript("return window.angular === undefined");
        if (!angularUnDefined) {
            Boolean angularInjectorUnDefined = (Boolean) js.executeScript("return angular.element(document).injector() === undefined");
            if (!angularInjectorUnDefined) {
                // Pre Wait for stability (Optional)
                // alrg_waitImplicitly(1);

                // Wait Angular Load
                waitForAngularLoad();

                // Post Wait for stability (Optional)
                // alrg_waitImplicitly(1);
                ALRGLogger.log(_log, "The waitUntilAngularReady() took " + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()),
                        EllieMaeLogLevel.debug);
            }
        }
    }


    /**
     * To test different branches and avoid creating/supporting multiple automation code branches,
     * sometimes code needs to determine which release it is testing.
     * 
     * Returns true if a component is turned on for the provided release
     */
    public boolean isFeatureEnabled(String featureName, String build) {

        if (build.contains("19.2") || build.contains("19.1") || build.contains("18.4") || build.contains("18.3") 
                || build.contains("18.2") || (build.contains("18.1"))) {
            if ("LE".equalsIgnoreCase(featureName) || "CD".equalsIgnoreCase(featureName) || "REGZLE".equalsIgnoreCase(featureName)
                    || "FNMA".equalsIgnoreCase(featureName) || "VO".equalsIgnoreCase(featureName)) {
                return false;
            }
        }
        return true;
    }


    /**
     * <b>Name:</b> alrg_isFrameExists<br>
     * <b>Description:</b> This method checks whether is the any frame exist on
     * a page
     * 
     * @return alrg_isFrameExists (true/false)
     *  
     * 
     */
    public boolean alrg_isFrameExists() {
        try {
            for (int i = 0; i < 42; i++) {
                driver.switchTo().frame(i);
            }
            ALRGLogger.log(_log, "Frame exists on " + "'" + driver.getTitle() + "'" + " page", EllieMaeLogLevel.reporter);
            return true;
        } catch (NoSuchFrameException e) {
            ALRGLogger.log(_log, "Frame does not exist on " + "'" + driver.getTitle() + "'" + " page", EllieMaeLogLevel.reporter);
            return false;
        }
    }


    /**
     * <b>Name:</b> selectAutocompleteOptionWithIndex(WebElement autoOptions, int indexToSelect)<br>
     * <b>Description:</b> This method is used to select option by index
     * in autoselect appeared in text box<br>
     * 
     * Use ex:
     * public boolean selectCityInZipcodeLookupAutoselect(int index) {
     * WebElement autoOptions = driver.findElement(By.cssSelector("[class*=\"autocomplete-list\"]"));
     * return objEllieMaeActions.selectAutoCompleteOptionWithText(autoOptions, index);
     * }
     * 
     * 
     */
    public boolean selectAutoCompleteOptionWithIndex(WebElement autoOptions, int indexToSelect) {
        boolean isSelected = false;
        try {
            wait.until(ExpectedConditions.visibilityOf(autoOptions));

            List<WebElement> optionsToSelect = autoOptions.findElements(By.tagName("li"));
            if (indexToSelect <= optionsToSelect.size()) {
                ALRGLogger.log(_log, "Trying to select opion: " + optionsToSelect.get(indexToSelect).getText(), EllieMaeLogLevel.reporter);
                optionsToSelect.get(indexToSelect).click();
                isSelected = true;
            }
        } catch (Exception e) {
            isSelected = false;
            ALRGLogger.log(_log, "Error:" + e.getMessage(), EllieMaeLogLevel.reporter);
        }
        return isSelected;
    }


    /**
     * <b>Name:</b> selectAutoCompleteOptionWithText(WebElement autoOptions, String textToSelect)<br>
     * <b>Description:</b> This method is used to select option by text
     * in autoselect appeared in text box<br>
     * 
     * Use ex:
     * public boolean selectCityInZipcodeLookupAutoselect(String city) {
     * WebElement autoOptions = driver.findElement(By.cssSelector("[class*=\"autocomplete-list\"]"));
     * return objEllieMaeActions.selectAutoCompleteOptionWithText(autoOptions, city);
     * }
     * 
     * 
     */
    public boolean selectAutoCompleteOptionWithText(String textToSelect) {

        boolean isSelected = false;
        try {
           // WebElement autoComplete = alrg_findElement(By.cssSelector(ALRGConsts.AUTO_COMPLETE_LIST));
            WebElement autoComplete = alrg_findElement(By.xpath("//*[@id='Search_Text_ID']/span/div/div"));  
          //  List<WebElement> optionsToSelect = autoComplete.findElements(By.tagName("li"));
            List<WebElement> optionsToSelect = driver.findElements(By
                    .xpath("//div[@class='tt-suggestion tt-selectable']"));
         
            for (WebElement option : optionsToSelect) {
                String tempOptionText = option.findElement(By.tagName("a")).getText();
            	//String tempOptionText = option.findElement(By.xpath("//div[@class='tt-suggestion tt-selectable']")).getText();
          
                if (tempOptionText.contains(textToSelect)) {
                    ALRGLogger.log(_log, "Trying to select: " + textToSelect, EllieMaeLogLevel.reporter);
                    // option.click();
                    option.findElement(By.tagName("a")).click();
                    alrg_waitForSeconds(2);
                    isSelected = true;
                    break;
                }
            }

        } catch (NoSuchElementException e) {
            ALRGLogger.log(_log, "Error:" + e.getMessage(), EllieMaeLogLevel.error);
            isSelected = false;
        } catch (Exception e) {
            ALRGLogger.log(_log, "Error:" + e.getStackTrace(), EllieMaeLogLevel.error);
            isSelected = false;
        }
        return isSelected;
    }

	/**
	 * <b>Name:</b> alrg_selectAutoCompleteOptionWithText(String textToSelect)
	 * <br>
	 * <b>Description:</b> This method is used to select option by text in
	 * autoselect options appeared in text box<br>
	 * <b>@author:Archana Joshi</b>
	 * 
	 * @param textToSelect
	 */

    public boolean alrg_selectAutoCompleteOptionWithText(String locator, String textToSelect) {

        boolean isSelected = false;
        try {

			 List<WebElement> optionsToSelect = driver.findElements(By.xpath(locator));
			//List<WebElement> optionsToSelect = driver.findElements(By.xpath("//div[@class='tt-suggestion tt-selectable']"));
			
			for (WebElement option : optionsToSelect) {
				String tempOptionText = option.getText();
				System.out.println(tempOptionText);
				if (tempOptionText.equals(textToSelect)) {
					ALRGLogger.log(_log, "Trying to select: " + textToSelect, EllieMaeLogLevel.reporter);
					option.click();
					isSelected = true;
					break;
				}
			}

        } catch (NoSuchElementException e) {
            ALRGLogger.log(_log, "Error:" + e.getMessage(), EllieMaeLogLevel.error);
            isSelected = false;
        } catch (Exception e) {
            ALRGLogger.log(_log, "Error:" + e.getStackTrace(), EllieMaeLogLevel.error);
            isSelected = false;
        }
        return isSelected;
    }

    
 
    
    
    public boolean alrg_selectAutoCompleteOptionWithIndex(String locator, int indexToSelect) {
        boolean isSelected = false;
        try {
            // List<WebElement> optionsToSelect = driver.findElements(By.xpath("//div[@class='tt-suggestion tt-selectable']"));
			List<WebElement> optionsToSelect = driver.findElements(By.xpath(locator));

            if (indexToSelect <= optionsToSelect.size()) {
                ALRGLogger.log(_log, "Trying to select opion: " + optionsToSelect.get(indexToSelect).getText(), EllieMaeLogLevel.reporter);
                optionsToSelect.get(indexToSelect).click();
                isSelected = true;
            }
        } catch (Exception e) {
            isSelected = false;
            ALRGLogger.log(_log, "Error:" + e.getMessage(), EllieMaeLogLevel.reporter);
        }
        return isSelected;
    }
    
    /**
	 * <b>Name:</b> alrg_isTypeAheadDisplayed
	 * <br>
	 * <b>Description:</b> This method is used to check if auto type options are displayed or not.
	 *<br>
	 * <b>@author:Archana Joshi</b>
	 * @param locator
	 */
	public boolean alrg_isTypeAheadDisplayed(String locator) {
		boolean status = false;
		try {
			List<WebElement> optionsToSelect = driver.findElements(By.xpath(locator));
			if (optionsToSelect.size() > 0)
				status = true;
			else
				status = false;

		} catch (Exception e) {
			status = false;
			ALRGLogger.log(_log, "Error:" + e.getMessage(), EllieMaeLogLevel.reporter);
		}
		return status;
	}

    /**
     * <b>Name:</b> selectByVisibleTextIfNotFirstIndex<br>
     * <b>Description:</b> Select a Drop Down/field on the web page according to the
     * visible text, if the text is not exist method select the first item from the
     * list using index</br>
     * 
     * @param element
     * @param elementValue
     * @param elementDescription
     * 
     *  
     */
    public void alrg_selectByVisibleTextIfNotFirstIndex(WebElement element, String elementValue, String elementDescription) {
        try {
            if (element == null) {
                ALRGLogger.log(_log, "Element for : " + elementDescription + " does not exist.", EllieMaeLogLevel.reporter);
            } else {
                // DO NOT CHANGE - scroll only when needed. otherwise, new button gets hidden,
                // no way to scroll back
                if (!element.isDisplayed()) {
                    alrg_scrollIntoViewElement(element, elementDescription);
                }
                driver.switchTo().activeElement();
                Select select = new Select(element);
                try {
                    select.selectByVisibleText(elementValue);
                    ALRGLogger.log(_log, "Selected item by Visible text: " + elementValue + " in " + elementDescription, EllieMaeLogLevel.reporter);
                } catch (NoSuchElementException notFoundEx) {
                    ALRGLogger.log(_log, "Cannot select by Visible text. Try select by first index", EllieMaeLogLevel.reporter);
                    // ****Get all items from the list
                    int size = select.getOptions().size();
                    int k = 0;
                    String text = null;
                    for (k = 0; k < size; k++) {
                        text = select.getOptions().get(k).getText();
                        if (StringUtils.isNotEmpty(text) && !"Select".equalsIgnoreCase(text)) {
                            break;
                        }
                    }
                    select.selectByIndex(k);
                    ALRGLogger.log(_log, "Selected first index with text: " + alrg_getSelectedOptionTextNoScroll(element, " in " + elementDescription),
                            EllieMaeLogLevel.reporter);
                }
            }
        } catch (Exception e) {
            ALRGLogger.log(_log, "Fail to select: " + (elementDescription == null ? element : elementDescription) + " with text: " + elementValue,
                    EllieMaeLogLevel.reporter);
            ALRGLogger.log(_log, "Exception in selectByVisibleTextOrFirstIndex: " + e.getMessage(), EllieMaeLogLevel.reporter);
        }
    }

    public void alrg_selectFirstNonEmptyValue(WebElement element, String elementDescription) {

        int size = 0;
        try {
            if (element == null) {
                ALRGLogger.log(_log, "Element for : " + elementDescription + " does not exist.", EllieMaeLogLevel.reporter);
            } else {
                // DO NOT CHANGE - scroll only when needed. otherwise, new button gets hidden,
                // no way to scroll back
                if (!element.isDisplayed()) {
                    alrg_scrollIntoViewElement(element, elementDescription);
                }
                driver.switchTo().activeElement();
                Select select = new Select(element);
                // ****Get all items from the list
                size = select.getOptions().size();
                int k = 0;
                String text = null;
                for (k = 0; k < size; k++) {
                    text = select.getOptions().get(k).getText();
                    if (StringUtils.isNotEmpty(text) && !"Select".equalsIgnoreCase(text)) {
                        break;
                    }
                }
                // add wait to ensure the list loads complete
                alrg_waitForDropdownToLoad(select, k + 1, 3);
                select.selectByIndex(k);
                ALRGLogger.log(_log, "Selected first index with text: " + alrg_getSelectedOptionTextNoScroll(element, " in " + elementDescription),
                        EllieMaeLogLevel.reporter);
            }
        } catch (Exception e) {
            ALRGLogger.log(_log, "Select size:" + size + ", Fail to select: " + (elementDescription == null ? element : elementDescription),
                    EllieMaeLogLevel.reporter);
            ALRGLogger.log(_log, "Exception in selectByVisibleTextOrFirstIndex: " + e.getMessage(), EllieMaeLogLevel.reporter);
        }
    }

    protected boolean alrg_waitForDropdownToLoad(Select select, int miniumSize, int nSecs) {

        try {
            FluentWait<WebDriver> eWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(nSecs)).pollingEvery(Duration.ofMillis(200))
                    .ignoring(NoSuchElementException.class);
            eWait.until(new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return select.getOptions().size() >= miniumSize;
                }
            });
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "alrg_waitUntilListOfElementsSizeMoreThan0(), exception " + e.getMessage());
            return false;
        }
    }

    protected EllieMaeLogLevel perfLogLevel(long startTime, long endTime) {

        if ((int) CommonUtility.calculatePerformTime(startTime, endTime) > 3) {
            return EllieMaeLogLevel.info;
        } else {
            return EllieMaeLogLevel.debug;
        }
    }


    public void openNewTab() {

        // open new tab
        // driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t"); - no
        // js.executeScript("window.open()");

        Robot rob;
        try {
            rob = new Robot();
            rob.keyPress(KeyEvent.VK_CONTROL);
            rob.keyPress(KeyEvent.VK_T);
            rob.keyRelease(KeyEvent.VK_CONTROL);
            rob.keyRelease(KeyEvent.VK_T);

        } catch (AWTException e) {
            ALRGLogger.log(_log, "Exception in openNewTab: " + e.getMessage(), EllieMaeLogLevel.reporter);
        }

    }

    /**
     * 
     */
    public void closeAppWhileKeepBrowserOpen() {

        try {
            openNewTab();

            Set<String> windows = driver.getWindowHandles();
            Iterator<String> iter = windows.iterator();
            String[] winNames = new String[windows.size()];
            int i = 0;
            while (iter.hasNext()) {
                winNames[i] = iter.next();
                i++;
            }

            if (winNames.length > 1) {
                for (i = winNames.length; i > 1; i--) {
                    driver.switchTo().window(winNames[i - 1]);
                    driver.close();
                }
            }
            driver.switchTo().window(winNames[0]);
        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in closeAppWhileKeepBrowserOpen: " + e.getMessage(), EllieMaeLogLevel.reporter);
        }
    }


    public boolean alrg_waitForElementToBeClickable(WebElement element, int sTimeout, String elementDesc) {
        long startTime = System.currentTimeMillis();
        try {
            FluentWait<WebDriver> fWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(sTimeout)).pollingEvery(Duration.ofMillis(1000))
                    .ignoring(NoSuchElementException.class, NullPointerException.class);            
            fWait.until(ExpectedConditions.elementToBeClickable(element));
            ALRGLogger.log(_log,
                    "In " + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()) + " the " + elementDesc + " become clickable",
                    EllieMaeLogLevel.debug);
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in 'alrg_waitForElementToBeClickable' while waiting for '" + elementDesc + "' to become clickable: " + e,
                    EllieMaeLogLevel.reporter);
            return false;
        }
    }

    public boolean alrg_waitForElementToBeClickable(String sLocator, int sTimeout, String elementDesc) {
        
        long startTime = System.currentTimeMillis();
        try {

            FluentWait<WebDriver> fWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(sTimeout)).pollingEvery(Duration.ofMillis(100))
                    .ignoring(NoSuchElementException.class, NullPointerException.class);
            fWait.until( ExpectedConditions.elementToBeClickable(alrg_findElement(sLocator)) );

            ALRGLogger.log(_log,
                    "In " + CommonUtility.countPerformTime(startTime, System.currentTimeMillis()) + " the " + elementDesc + " become clickable",
                    EllieMaeLogLevel.debug);
            return true;
        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception in 'alrg_waitForElementToBeClickable' while waiting for '" + elementDesc + "' to become clickable: " + e,
                    EllieMaeLogLevel.reporter);
            return false;
        }
    }

    public void alrg_setTextBoxValueUsingJS(WebElement element, String elementValue, String elementDescription) {

        if (elementValue != null) {

            alrg_waitForElementEnabled(element);
            alrg_scrollIntoViewElement(element, elementDescription);
            try {
                if (!wait.until(ExpectedConditions.visibilityOf(element)).isEnabled()) {
                    throw new Exception(elementDescription + " does not exist.");
                }
                if (StringUtils.isNotEmpty(alrg_getTextBoxValue(element, elementDescription))) {
                    element.clear();
                    waitForPageToLoad();
                }
                if (StringUtils.isNotEmpty(alrg_getTextBoxValue(element, elementDescription))) {
                    clearTextField(element);
                    waitForPageToLoad();
                }

                JavascriptExecutor js = (JavascriptExecutor) driver;
                alrg_scrollIntoViewElement(element, elementDescription);

                alrg_clickUsingActions(element, elementDescription);

                js.executeScript("arguments[0].value = '" + elementValue + "';", element);

                js.executeScript("var evt = document.createEvent('HTMLEvents'); evt.initEvent('change',true,true); arguments[0].dispatchEvent(evt);",
                        element);

                alrg_waitForSpinnerToBeGone();
                // windowFocus();
                ALRGLogger.log(_log, "Entering Text in: " + "'" + elementDescription + "'" + " = " + elementValue, EllieMaeLogLevel.reporter);
            } catch (Exception e) {
                ALRGLogger.log(_log, "Fail to enter Text: " + elementValue + " for " + elementDescription, EllieMaeLogLevel.reporter);
                ALRGLogger.log(_log, "Exception in alrg_type: " + e.getMessage());
            }
        } else {
            ALRGLogger.log(_log, "Element: " + elementDescription + " does not exist.");
        }
    }

/*    public void expandCollapseLeftToggleBar(String expand_collapse) {

        String expectedClassAttribute = "icon-menu-collapse";
        waitForPageToLoad();
        driver.switchTo().defaultContent();

        try {
            WebElement leftNavToggle = alrg_findElement(ALRGConsts.LEFT_TOGGLE_SIDE_BAR);

            if (expand_collapse.equalsIgnoreCase("expand")) {
                if (alrg_getAttributeValue(leftNavToggle, "Class", "").equals("icon-menu-expand")) {
                    ALRGLogger.log(_log, "Left Toggle did not expand on first time.", EllieMaeLogLevel.reporter);
                    alrg_clickElement(leftNavToggle, "Expand Left Toggle Bar");
                    waitForPageToLoad();
                    if (!alrg_getAttributeValue(leftNavToggle, "Class", "").equals("icon-menu-collapse")) {
                        ALRGLogger.log(_log, "Left Toggle did not collapse on first time.", EllieMaeLogLevel.reporter);
                        alrg_clickElement(leftNavToggle, "Expand Left Toggle Bar");
                        waitForPageToLoad();
                    }                    
                }                

            } else if (expand_collapse.equalsIgnoreCase("collapse")) {
                if (alrg_getAttributeValue(leftNavToggle, "Class", "").equals("icon-menu-collapse")) {
                    ALRGLogger.log(_log, "Left Toggle did not collapse on first time.", EllieMaeLogLevel.reporter);
                    alrg_clickElement(leftNavToggle, "Expand Left Toggle Bar");
                    waitForPageToLoad();
                    if (!alrg_getAttributeValue(leftNavToggle, "Class", "").equals("icon-menu-expand")) {
                        ALRGLogger.log(_log, "Left Toggle did not expand on first time.", EllieMaeLogLevel.reporter);
                        alrg_clickElement(leftNavToggle, "Expand Left Toggle Bar");
                        waitForPageToLoad();
                    }                    
                }                  
            }
        } catch (Exception e) {
            ALRGLogger.log(_log, "Exception while expanding/collapsing Left toggle bar. Exception - " + e.getMessage(), EllieMaeLogLevel.reporter);
        }
    }*/

    /**
     * <b>Name:</b>clickButtonOnRentalIncomeModal<br>
     * <b>Description:</b> This method to click buttons on rental income modal
     * 
     * @param buttonName
     *            Cancel, Done
     */
/*    public void clickButtonOnModal(String buttonName) {

        waitForPageToLoad();
        try {
            ALRGLogger.log(_log, "Attempt to click on '" + buttonName + "' button on MI modal",
                    EllieMaeLogLevel.reporter);
            if (buttonName.equalsIgnoreCase("ok")) {
                alrg_clickElement(By.xpath(ALRGConsts.MODAL_OK_BTN), buttonName);
            } else if (buttonName.equalsIgnoreCase("cancel")) {
                alrg_clickElement(By.xpath(ALRGConsts.MODAL_CANCEL_BTN), buttonName);
            } else if (buttonName.equalsIgnoreCase("save")) {
                alrg_clickElement(By.xpath(ALRGConsts.MODAL_SAVE_BTN), buttonName);
            } else if (buttonName.equalsIgnoreCase("savecontinue")) {
                alrg_clickElement(By.xpath(ALRGConsts.MODAL_FOOOTER_SAVE_CONTINUE_BTN), buttonName);
            } else if (buttonName.equalsIgnoreCase("continue")) {
                alrg_clickElement(By.xpath(ALRGConsts.MODAL_CONTINUE_BTN), buttonName);
            }
            waitForPageToLoad();
        } catch (Exception e) {
            ALRGLogger.log(_log, "Error while Clicking" + buttonName + " button");
        }
    }*/
    
	/**
	 * <b>Name:</b> selectElementUsingActions<br>
	 * <b>Description: </b>Method select a specific element using actions.<br>
	 * <b><i>When using the method, an informational warning occurs. This is a known
	 * 
	 * <a href="https://github.com/mozilla/geckodriver/issues/1123#unicode">defect
	 * about 'Actions moveToElement'</a></i></b>
	 * 
	 * @param element
	 * @param elementDescription
	 *  
	 */
	public boolean selectElementUsingActions(WebElement element, String elementDescription) {

		boolean isSelected = false;
		try {
			waitForPageToLoad();
			Actions builder = new Actions(driver);
			builder.moveToElement(element, 3, 3).click().keyDown(Keys.SHIFT).moveToElement(element, 200, 0).click()
					.keyUp(Keys.SHIFT).build().perform();
			waitForPageToLoad();
			ALRGLogger.log(_log, "Selecting the element: " + elementDescription, EllieMaeLogLevel.reporter);
			isSelected = true;
		} catch (Exception e) {
			ALRGLogger.log(_log, "Fail to select the element: " + elementDescription
					+ ". Exception in selectElementUsingActions: " + e.getMessage(), EllieMaeLogLevel.debug);
		} 
		return isSelected;
	}
	
	
	/**
	 * <b>Name:</b> alrg_selectElementUsingActions<br>
	 * <b>Description: </b>Method select a specific element using mouse click actions.<br>
	 *  <b>@author: Archana Joshi</b> 
	 *  
	 * @param element
	 * @param elementDescription
	 *  
	 */
	public boolean alrg_selectElementUsingActions(WebElement element, String elementDescription) {

		boolean isSelected=false;
		List<WebElement> childs = element.findElements(By.xpath(".//*"));
		Actions actions = new Actions(driver);
		try {
			for (WebElement e : childs) {
				String eletagName = e.getTagName();
				String eleText = e.getText();
				if (eletagName.equals("div") && eleText.equals(SEARCHRESULTConsts.ACTIONS_ELEMENT_TO_SELECT_TXT)) {
					// System.out.println("Tag: " + eletagName + " Text: " +
					// eleText );
					actions.moveToElement(e).perform();
					ALRGLogger.log(_log, "Selecting the element: " + eleText, EllieMaeLogLevel.reporter);
					e.click();
					isSelected = true;
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ALRGLogger.log(_log,"Fail to select the element: " + elementDescription
									+ ". Exception in alrg_selectElementUsingActions: " + e.getMessage(),
							EllieMaeLogLevel.debug);

		}
		return isSelected;
	}
	
	/**
	* <b>Name:</b> clickKeywordSearchCheckBox<br>
	 * <b>Description:</b> This method selects one of the check box from available list.
	 * <b>@author:Archana Joshi</b>
	 * 
	 */
	public boolean alrg_selectCheckBoxFromList(WebElement element, String elementDescription) {

		boolean isSelected=false;
		List<WebElement> childs = element.findElements(By.xpath("*//span[@class='checkbox']"));	
		try {
			  for (WebElement e  : childs)
	            {
	                String eletagName = e.getTagName();
	                String eleText = e.getText();
	                //System.out.println("Tag: " + eletagName + " Text: " + eleText);
				  	ALRGLogger.log(_log, "Selecting the element: " + eleText, EllieMaeLogLevel.reporter);
	                e.click();
	                break;                
	            }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ALRGLogger.log(_log,"Fail to select the element: " + elementDescription
									+ ". Exception in alrg_selectCheckBoxFromList: " + e.getMessage(),
							EllieMaeLogLevel.debug);

		}
		return isSelected;
	}

	public int alrg_getSearchResultDivCount(WebElement element) {
		int resultCount=0;
		List<WebElement> childs = element.findElements(By.xpath("*//div"));
		try {
			resultCount=childs.size();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ALRGLogger.log(_log,"" + e.getMessage(),EllieMaeLogLevel.debug);
		}
		return resultCount;
		}
	
	public int alrg_getDocumentIconCount(WebElement element) {

		int resultCount=0;
		List<WebElement> childs = element.findElements(By.xpath(SEARCHRESULTConsts.DOCUMENT_ICON));
		try {
			resultCount=childs.size();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ALRGLogger.log(_log,"" + e.getMessage(),EllieMaeLogLevel.debug);
		}
		return resultCount;
		
	}
	
/**
	*<b>Name:</b> alrg_validateResultDisplay<br>
	 * <b>Description: </b>This method Validates search result,checks for documentIcon, breadCrumb and two lines of text.
	 * <br>
	 * <b>@author: Archana Joshi</b>
	 * 
	 * @param parentElement
	 * 
	 */
	public boolean alrg_validateResultDisplay(WebElement element) {

		boolean iconStatus = false;
		boolean breadCrumbStatus = false;

		List<WebElement> childDivs = element.findElements(By.xpath("*//div"));
		try {
			for (WebElement div : childDivs) {
				if (div.findElements(By.xpath(SEARCHRESULTConsts.DOCUMENT_ICON)).size() > 0) {
					ALRGLogger.log(_log, "Document icon found for first search result.", EllieMaeLogLevel.reporter);
					iconStatus = true;
					breadCrumbStatus = alrg_checkForBreadCrumb(div);				
					break; // checking only for one result.
				} // end if
				else {
					iconStatus = false;
					ALRGLogger.log(_log, "Document icon not displayed for search result.", EllieMaeLogLevel.debug);
				}
			} // end for

		} catch (Exception e) {
			ALRGLogger.log(_log, "" + e.getMessage(), EllieMaeLogLevel.debug);
		}
		if (iconStatus && breadCrumbStatus)
			return true;
		else
			return false;

	}
	
/**
	*<b>Name:</b> checkAllPageResults<br>
	 * <b>Description: </b>This method clicks on next button and verifies first result till next page button is present.
	 * <br>
	 * <b>@author: Archana Joshi</b>
	 * 
	 * @param element
	 * 
	 */
	public boolean checkAllPageResults(WebElement element)
	{
		boolean status=false;
		String currentPageResultText="",currentBCText="";
		int pageNo=3;
		try
		{
			//while (alrg_isElementExists(By.xpath((SEARCHRESULTConsts.PAGECONTROL_SEARCH_PREV_NEXT)))) {
			//As discussed, checking results on first 3 pages only. 10/01/19. AJ
			boolean nextBtnPresent =alrg_isElementExists(By.xpath((SEARCHRESULTConsts.PAGECONTROL_SEARCH_PREV_NEXT)));
			while (pageNo>0 && nextBtnPresent ) {
				// Fetch first result text from current page
				currentPageResultText = alrg_getFirstResultText(element);
				currentBCText=getFirstBreadCrumbText(element);
				List<WebElement> childBtn = driver
						.findElements(By.xpath(SEARCHRESULTConsts.PAGECONTROL_SEARCH_PREV_NEXT));
				for (WebElement nextBtn : childBtn) {
					if (nextBtn.getText().equals("Next >")) {
					
						nextBtn.click();
						pageNo--; //decrement page counter
						ALRGLogger.log(_log,"Checking results on Next page.",EllieMaeLogLevel.reporter);
						Thread.sleep(500);
						status=compareResultOnNextPage(element, currentPageResultText,currentBCText);
					}//end if
					
				}//end for 
				if (status==false)
				{
					ALRGLogger.log(_log,"same results found on multiple pages",EllieMaeLogLevel.reporter);
					break;// same results found on multiple pages
				}
			}//end while
		}catch(Exception e)
		{
			ALRGLogger.log(_log,"" + e.getMessage(),EllieMaeLogLevel.debug);
		}
		return status;
	}

/**

     *<b>Name:</b> alrg_checkForBreadCrumb<br>
	 * <b>Description: </b>This method checks if label Text contains ">" or not.
	 * <br>
	 * <b>@author: Archana Joshi</b>
	 * 
	 * @param parentElement
	 * 
	 */
	public boolean alrg_checkForBreadCrumb(WebElement parentElement) {
		boolean status = false;
		List<WebElement> childs = parentElement.findElements(By.xpath(".//label"));
		try {
		
			for (WebElement e : childs) {
				if (e.getText().contains(">")) {
					ALRGLogger.log(_log, "" + "BreadCrumb found  " + e.getText(), EllieMaeLogLevel.reporter);
					status = true;
					break;
				} else {
					ALRGLogger.log(_log, "" + "BreadCrumb not found for " + e.getText(), EllieMaeLogLevel.debug);
				}
			}
		} catch (Exception e) {
			ALRGLogger.log(_log, "" + e.getMessage(), EllieMaeLogLevel.debug);
		}
		return status;
	}
	
	 
/**
    * <b>Name:</b> getFirstResultText<br>
	 * <b>Description: </b>Fetch first result text from page.
	 * <br>
	 * <b>@author: Archana Joshi</b>
	 * 
	 * @param parentElement
	 * 
	 */
	public String alrg_getFirstResultText(WebElement parentElement) {
		
		String currentPageResultText="";
		List<WebElement> childs = parentElement.findElements(By.tagName("a"));
		try {
			for (WebElement e : childs) {
				// System.out.println("tag name"+ e.getTagName()+" "+e.getText());
				//ALRGLogger.log(_log, "" + " Result text  " + e.getText(), EllieMaeLogLevel.reporter);
				currentPageResultText = e.getText();
				break;
			}
		} catch (Exception e) {
			ALRGLogger.log(_log, "" + e.getMessage(), EllieMaeLogLevel.debug);
		}
		return currentPageResultText;
	}
	
	
/**	
 	* <b>Name:</b> getFirstBreadCrumbText<br>
	 * <b>Description: </b>This method checks if label Text contains ">" or not.
	 * <br>
	 * <b>@author: Archana Joshi</b>
	 * 
	 * @param parentElement
	 * 
	 */
	public String getFirstBreadCrumbText(WebElement parentElement) {
		String currentBCText = "";
		List<WebElement> childs = parentElement.findElements(By.xpath(".//label"));
		try {
			//driver.manage().timeouts().implicitlyWait(90,TimeUnit.SECONDS) ;
			//Thread.sleep(500);
			for (WebElement e : childs) {
				if (e.getText().contains(">")) {
				
					currentBCText = e.getText();
					System.out.println("BC Text "+currentBCText);
					break;
				} else {
					ALRGLogger.log(_log, "" + "BreadCrumb not found for " + e.getText(), EllieMaeLogLevel.debug);
				}
			}
		} catch (Exception e) {
			ALRGLogger.log(_log, "" + e.getMessage(), EllieMaeLogLevel.debug);
		}
		return currentBCText;
	}
		
/**
	* <b>Name:</b> compareResultOnNextPage<br>
		 * <b>Description: </b>This method compares result text on next page. returns true if results are different.
		 * <br>
		 * <b>@author: Archana Joshi</b>
		 * 
		 * @param element
		 * @param prevPageResultText
		 * @param prevText
		 */
	public boolean compareResultOnNextPage(WebElement element, String prevPageResultText,String prevBCText) {
		
		
		String currentPageResultText = alrg_getFirstResultText(element);
		String currentBCText=getFirstBreadCrumbText(element);
		//String s1=currentBCText.substring(currentBCText.indexOf(">"));
	
		
		ALRGLogger.log(_log, currentPageResultText+ "** Comparing with**  "+ prevPageResultText,EllieMaeLogLevel.reporter);
		ALRGLogger.log(_log, currentBCText+ " **Comparing with ** "+ prevBCText,EllieMaeLogLevel.reporter);
		if (!currentPageResultText.equals(prevPageResultText) && !currentBCText.equals(prevBCText))
			return true;
		else
		{
			
			ALRGLogger.log(_log, " same result found on preveous page" ,EllieMaeLogLevel.reporter);
			return false;
		}
	}
	
/**
 	* <b>Name:</b> alrg_isTextHighlighted<br>
	 * <b>Description: </b>This method checks if given text is highlighted.
	 * <b>@author: Archana Joshi</b>
	 * 
	 * @param element
	 * @param searchText
	 */
	public boolean alrg_isTextHighlighted(WebElement element,String searchText)
	{
		boolean status = true;
		List<WebElement> childs = element.findElements(By.xpath(SEARCHRESULTConsts.HIGHLIGHTED_TEXT));
		try {
		
			for (WebElement e : childs) {
				if (e.getText().toLowerCase().equals(searchText)|| e.getText().toLowerCase().contains(searchText)) {
					status = true;
					//ALRGLogger.log(_log, "" + "Text highlighted  " + e.getText(), EllieMaeLogLevel.reporter);
						
				} else {
					status = false;
					ALRGLogger.log(_log, "" + "Text not highlighted " + e.getText(), EllieMaeLogLevel.reporter);
					break;
				}
			}
		} catch (Exception e) {
			ALRGLogger.log(_log, "" + e.getMessage(), EllieMaeLogLevel.debug);
		}
		return status;
	}
	
/**
 	* <b>Name:</b> alrg_clickOnFirstResult<br>
	 * <b>Description: </b>This method clicks on first search result.
	 * <b>@author: Archana Joshi</b>
	 * 
	 * @param parentElement
	 * 
	 */
	public void alrg_clickOnFirstResult(WebElement parentElement) {
		
		List<WebElement> childs = parentElement.findElements(By.tagName("a"));
		try {
			for (WebElement e : childs) {
				//ALRGLogger.log(_log, "" + " Result text  " + e.getText(), EllieMaeLogLevel.reporter);
				alrg_clickElement(e, e.getText());
				//e.click();
				break;
			}
		} catch (Exception e) {
			ALRGLogger.log(_log, "" + e.getMessage(), EllieMaeLogLevel.debug);
		}

	}

	/**
 	* <b>Name:</b> alrg_isDocumentReady<br>
	 * <b>Description: </b>This method checks for document.readyState
	 * <b>@author: Archana Joshi</b>
	 * 
	 * 
	 */
	public boolean alrg_isDocumentReady()
	{
		 Boolean readyStateComplete = false;
	        while (!readyStateComplete)
	        {
	            JavascriptExecutor executor = (JavascriptExecutor) driver;
	            executor.executeScript("window.scrollTo(0, document.body.offsetHeight)");
	           
	            readyStateComplete =  executor.executeScript("return document.readyState").equals("complete");
	           // System.out.println("executing");
	        }
			return readyStateComplete;
	}

	/**
 	* <b>Name:</b> alrg_isChkBoxClickable<br>
	 * <b>Description: </b>This method checks if checkbox is clickable not?
	 * <b>@author: Archana Joshi</b>
	 * 
	 * @param element
	 * 
	 */
	
	public boolean alrg_isChkBoxClickable(WebElement element,String elementDescription) {
		boolean status=false;
		List<WebElement> childs = element.findElements(By.name("SelectedEAlerts"));	
		try {
			  for (WebElement e  : childs)
	            {   
				  status=alrg_isElementClickable(e, elementDescription);
			  
	            }
		} catch (Exception e) {
			
			ALRGLogger.log(_log, e.getMessage(),EllieMaeLogLevel.debug);

		}
	return status;
	}

	/**
 	* <b>Name:</b> alrg_getCheckBoxStatus<br>
	 * <b>Description: </b>This method checks all chekbox/es found under web element provided.
	 * <b>@author: Archana Joshi</b>
	 * 
	 * @param element
	 * 
	 */
	public boolean  alrg_getCheckBoxStatus(WebElement element,String elementDescription) {
		boolean status=false;
		List<WebElement> childs = element.findElements(By.name("SelectedEAlerts"));	
		try {
			  for (WebElement e  : childs)
	            {     
				  if (! alrg_getCheckBoxStatus(e))
				  {
					  ALRGLogger.log(_log, "found e-Alert checkbox for  '"+elementDescription + "'  unchecked." ,EllieMaeLogLevel.reporter);
					  status=false;
				  }
					else
						status=true;
					
	             }
		} catch (Exception e) {
			ALRGLogger.log(_log, e.getMessage(),EllieMaeLogLevel.debug);

		}
	return status;
	}

	/**
	 * <b>Name:</b> alrg_deSelectCheckBox<br>
	 * <b>Description: </b>This method unchecks all chekbox/es found under web element provided.
	 * <b>@author: Archana Joshi</b>
	 * 
	 * @param element
	 * 
	 */
	public boolean alrg_deSelectCheckBox(WebElement element) {
		boolean status=false;
		List<WebElement> childs = element.findElements(By.name("SelectedEAlerts"));	
		try {
			
			  for (WebElement e  : childs)
	            {  
				  if(alrg_getCheckBoxStatus(e))
				  {
					  e.click();
					  status=true;
				  }
	            }
		} catch (Exception e) {
			status=false;
			ALRGLogger.log(_log, e.getMessage(),EllieMaeLogLevel.debug);

		}
	return status;
	}

	/**
 	* <b>Name:</b> alrg_selectCheckBox<br>
	 * <b>Description: </b>This method selects all chekbox/es found under web element provided, if it is unchecked.
	 * <b>@author: Archana Joshi</b>
	 * 
	 * @param element
	 * 
	 */
	public boolean alrg_selectCheckBox(WebElement element) {
		boolean status=false;
		List<WebElement> childs = element.findElements(By.name("SelectedEAlerts"));	
		try {
			  for (WebElement e  : childs)
	            {     
				  //ALRGLogger.log(_log, "Selecting the element: " + eleText, EllieMaeLogLevel.reporter);
				  if(!alrg_getCheckBoxStatus(e))
				  {
					  e.click();
					  status=true;
				  }
				  
	            }
		} catch (Exception e) {
			status=false;
			ALRGLogger.log(_log, e.getMessage(),EllieMaeLogLevel.debug);

		}
	return status;
	}
	
	/**
 	* <b>Name:</b> alrg_selectCheckBox<br>
	 * <b>Description: </b>This method toggles chekbox/es selection found under web element provided.
	 * <b>@author: Archana Joshi</b>
	 * 
	 * @param element
	 * @param elementDesc
	 */
	public boolean alrg_toggleChkBoxSelection(WebElement element,String elementDesc) {
		boolean status=false;
		List<WebElement> childs = element.findElements(By.name("SelectedEAlerts"));	
		try {
			  for (WebElement e  : childs)
	            {   				  		
				  e.click(); 
				  status=true;  
	            }
		} catch (Exception e) {
			status=false;
			ALRGLogger.log(_log,"Error in checking check box  "+elementDesc+ e.getMessage(),EllieMaeLogLevel.debug);

		}
	return status;
	}
	
	/**
 	* <b>Name:</b> waitForElementDisabled<br>
	 * <b>Description: </b>This method waits till element is disabled again.
	 * <b>@author: Archana Joshi</b>
	 * 
	 * @param element
	 * 
	 */
	public boolean waitForElementDisabled(WebElement element,String elementDescription) {
		// TODO Auto-generated method stub
		while (alrg_isElementEnable(element, elementDescription))
		{
			System.out.println("waiting for element to be disabled agaian");
		}
		return true;
	}

	/**
 	* <b>Name:</b> alrg_pauseFor<br>
	 * <b>Description: </b>This method waits for few milliseconds.
	 * <b>@author: Archana Joshi</b>
	 * 
	 * @param milliseconds
	 * 
	 */
	public void alrg_pauseFor(long milliseconds) {
		try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
        }
	}	
	
	/**
 	* <b>Name:</b> alrg_validateUpdateDisplay<br>
	 * <b>Description: </b>This method validates display for each update.Checks for Icon /Title/Date.
	 * verifies for all updates found , returns false if one of the element is missing for any of the update.
	 * <b>@author: Archana Joshi</b>
	 * 
	 * @param element
	 * 
	 */
	public boolean alrg_validateUpdateDisplay(WebElement element) {
		boolean result=false;
		boolean iconStatus = false;
		boolean dateStatus = false;
		boolean titleStatus=false;
		String updateTitle="";
		String updateDate="";

		List<WebElement> childDivs = element.findElements(By.xpath(AGENCYUPDATESConsts.AU_UPDATE_ROW));
		try {
			for (WebElement div : childDivs) {
				
				if (div.findElements(By.xpath(AGENCYUPDATESConsts.AU_UPDATE_DOCUMENT_ICON)).size() > 0) 
					iconStatus = true;	
				else 
					iconStatus = false;		
				
				if (div.findElements(By.xpath(AGENCYUPDATESConsts.AU_UPDATE_TITLE)).size() > 0) {
					updateTitle=div.findElement(By.xpath(AGENCYUPDATESConsts.AU_UPDATE_TITLE)).getText();
					if(updateTitle.equals(""))
						titleStatus = false;
					else
						titleStatus=true;
												
				}
				if (div.findElements(By.xpath(AGENCYUPDATESConsts.AU_UPDATE_DATE)).size() > 0) {
					updateDate=div.findElement(By.xpath(AGENCYUPDATESConsts.AU_UPDATE_DATE)).getText();
					if(updateDate.equals(""))
						dateStatus = false;
					else
						dateStatus=true;
				}
				
				if (iconStatus && titleStatus && dateStatus )
					result= true;
				else
				{
					ALRGLogger.log(_log, "Document icon/title/Date not found for update."+ updateTitle, EllieMaeLogLevel.reporter);
					return false;// return if one of the element is missing for any result.
				}
			} // end for

		} catch (Exception e) {
			ALRGLogger.log(_log, "" + e.getMessage(), EllieMaeLogLevel.debug);
		}
		return result;
		
	}
	
	public int alrg_getUpdatesCount(WebElement element) {
		int resultCount=0;
		List<WebElement> childs = element.findElements(By.xpath(AGENCYUPDATESConsts.AU_UPDATE_ROW));
		try {
			resultCount=childs.size();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ALRGLogger.log(_log,"" + e.getMessage(),EllieMaeLogLevel.debug);
		}
		return resultCount;
		}
	
	}
