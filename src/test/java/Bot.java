import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class Bot {
    public ChromeDriver driver;

    String login = "";
    String password = "";
    String snkTitle = "";
    String snkSize = "";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void authorization() throws InterruptedException {
        driver.get("https://brandshop.ru/");

        driver.findElement(By.className("icon-profile")).click();
        driver.findElement(By.name("email")).sendKeys(login);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.className("btn-fluid")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("hassubs")));

        WebElement men = driver.findElement(By.className("hassubs")).findElement(By.tagName("a"));
        if (men.isDisplayed()) {
            men.click();
        } else {
            // mobile | pl
            driver.findElement(By.className("tss-label")).click();
            WebDriverWait waitCatalog = new WebDriverWait(driver, 5);
            waitCatalog.until(ExpectedConditions.visibilityOfElementLocated(By.className("accordion-mobile")));
            driver.findElement(By.className("accordion-mobile")).click();
        }


        WebElement snickers = driver.findElement(By.cssSelector("a[title*='" + snkTitle + "']"));
        driver.get(snickers.getAttribute("href"));


        WebElement size = driver.findElement(By.xpath(".//*[text()='" + snkSize + "']"));
        size.click();

        // add to bucket
        driver.findElement(By.className("btn-cart")).click();

        // go to bucket
        driver.findElement(By.className("icon-cart")).click();

        driver.findElement(By.className("btn-fluid")).click();

        // fill order data
        List<WebElement> elements = driver.findElements(By.className("bs-select-current"));
        elements.get(1).click();
        driver.findElement(By.xpath(".//*[contains(text(),'Почта России')]")).click();

        // payment method
        List<WebElement> elements1 = driver.findElements(By.className("bs-select-current"));
        elements1.get(2).click();
        driver.findElement(By.xpath(".//*[contains(text(),'Банковская карта')]")).click();

        // confirm order
        WebDriverWait confirmWait = new WebDriverWait(driver, 10);
        confirmWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("payture-confirm")));
        driver.findElement(By.id("payture-confirm")).click();


        WebDriverWait paymentWait = new WebDriverWait(driver, 20);
        paymentWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("bank")));

        // next you fill card number and others

    }

    @After
    public void closeDriver() {
        driver.quit();
    }
}
