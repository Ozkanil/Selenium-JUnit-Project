package selenium_tests;

import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import testBase.TestBase;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class ShoppingWebsiteTest extends TestBase {

    @Test
    public void newTest() throws IOException, InterruptedException {

        driver.get("https://www.trendyol.com/");
        String trendyolHandle = driver.getWindowHandle();
        //driver.findElement(By.xpath("//span[@class='homepage-popup-gender']")).click();
        driver.findElement(By.xpath("//input[@class='search-box']")).sendKeys("iPhone 13" + Keys.ENTER);
        String price = driver.findElement(By.xpath("(//div[@class='prc-box-dscntd'])[1]")).getText();

        int priceAsInt = Integer.parseInt(price.substring(0, price.indexOf(",")).replaceAll("[^0-9]", ""));

        WebDriver newTab = driver.switchTo().newWindow(WindowType.TAB);
        newTab.get("https://www.amazon.com.tr/");
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("iPhone 13" + Keys.ENTER);
        String price2 = driver.findElement(By.xpath("(//span[@class='a-price-whole'])[2]")).getText();
        int price2AsInt = Integer.parseInt(price2.replaceAll("[^0-9]", ""));

        if (priceAsInt < price2AsInt) {

            driver.switchTo().window(trendyolHandle);
            Thread.sleep(5000);
            takeScreenShot();

        } else {

            System.out.println("Amazon is cheaper!");
        }

        driver.navigate().refresh();
        Thread.sleep(3000);

        JavascriptExecutor js=(JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");

        driver.findElement(By.linkText("Contact us")).click();

        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));

        String windowHandle= driver.getWindowHandle();
        WebElement formButton= wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("contactUsCol2Button")));
        formButton.click();

        Thread.sleep(2000);
        driver.navigate().refresh();

        driver.get("https://www.jotform.com/Trendyol/contact-form");

        Thread.sleep(2000);

        WebElement dropdown= driver.findElement(By.id("input_28"));
        new Select(dropdown).selectByIndex(2);
        Thread.sleep(4000);
        driver.findElement(By.id("input_16")).sendKeys("Prices");
        Thread.sleep(3000);
        driver.findElement(By.id("form-pagebreak-next_7")).click();
        Faker faker=new Faker();
        driver.findElement(By.id("input_30")).sendKeys(faker.name().firstName());
        driver.findElement(By.id("input_12")).sendKeys(faker.name().lastName());
        driver.findElement(By.id("input_13")).sendKeys(faker.internet().emailAddress());
        driver.findElement(By.id("input_14")).sendKeys("5478561942");
        WebElement uploadButton= driver.findElement(By.id("input_19"));

        String pathOfImage = System.getProperty("user.home")+"/Desktop/2022.01.21.12.14.54.png";
        uploadButton.sendKeys(pathOfImage);

    }

    public void takeScreenShot () {

        TakesScreenshot ts = (TakesScreenshot) driver;
        File image = ts.getScreenshotAs(OutputType.FILE);

        String currentTime = new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss").format(new Date());

        String path = System.getProperty("user.home") + "/Desktop/" + currentTime + ".png";
        File finalPath = new File(path);
        try {
            FileUtils.copyFile(image, finalPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
