package com.vytrack.apachePOI.utilities;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BrowserUtils {

    /*
In this class only general utility methods that are not related to some specific page
 */

    public static void sleep(int second){
        second *=1000;
        try{
            Thread.sleep(second);
        }catch (InterruptedException e){

        }
    }

    /*
    This method accepts 3 arguments.
    Arg1: webdriver
    Arg2: expectedInURL: for verify if the URL contains given argument
        - If condition matches, will break loop
     Arg3: expectedInTitle to be compared against actualTitle
     */
    public static void switchWindowAndVerify(String expectedInURL, String expectedInTitle){

        Set<String> allWindowHandles=Driver.getDriver().getWindowHandles();

        for (String each : allWindowHandles) {
            Driver.getDriver().switchTo().window(each);
            System.out.println("Current URL: "+Driver.getDriver().getCurrentUrl());
            if (Driver.getDriver().getCurrentUrl().contains(expectedInURL)){
                break;
            }
        }
        //Assert: Title contains "Etsy"
        String actualTitle=Driver.getDriver().getTitle();
        Assert.assertTrue(actualTitle.contains(expectedInTitle));
    }

    /*
    This method accepts a String "expectedTitle" nad Asserts if it is true
     */
    public static void verifyTitle(String expectedTitle){
        Assert.assertEquals(Driver.getDriver().getTitle(),expectedTitle);
    }

    /*
    Creating a utility method for ExplicitWait, so we don't have to repeat the lines
    */
    public static void waitForInvisibilityOf(WebElement webElement){
        Driver.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait=new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOf(webElement));
    }

    /**
     * This method will accept a String "expectedTitle" and Asserts if it is true
     * @param expectedInURL
     */
    public static void verifyURLContains(String expectedInURL){
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains(expectedInURL));
    }

    /**
     * This method will accept a dropdown as a WebElement
     * @param dropdownElement
     * @return
     */
    public static List<String> dropdownOptionsAsString(WebElement dropdownElement){
        Select select=new Select(dropdownElement );
        //List of all ACTUAL month <options> as a web element
        List<WebElement> actualOptionsAsWebElement = select.getOptions();
        //create a List of string and pass all the actual web element options' string into that new list

        //List of all ACTUAL month options as a string
        List<String>actualOptionsAsString= new ArrayList<>();

        for (WebElement each : actualOptionsAsWebElement) {
            actualOptionsAsString.add(each.getText());
        }
        return actualOptionsAsString;
    }

    /**
     * This method will accept a group radio buttons as a List of WebElement
     * @param radioButtons
     * @param attributeValue
     */
    public static void clickRadioButton(List<WebElement> radioButtons, String attributeValue){

        for (WebElement each : radioButtons) {
            if (each.getAttribute("value").equalsIgnoreCase(attributeValue)){
                each.click();
            }
        }
    }

    /**
     * return a list of string from a list of web elements
     * @param list
     * @return
     */
    public static List<String>getElementsText(List<WebElement>list){
        List<String>elementTexts = new ArrayList<>();
        for (WebElement element:list) {
            elementTexts.add(element.getText());
        }
        return elementTexts;
    }

    public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec){
        WebDriverWait wait=new WebDriverWait(Driver.getDriver(),Duration.ofSeconds(timeToWaitInSec));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForVisibility(By locator, int timeout){
        WebDriverWait wait=new WebDriverWait(Driver.getDriver(),Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickability(WebElement element, int timeout){
        WebDriverWait wait=new WebDriverWait(Driver.getDriver(),Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

}
