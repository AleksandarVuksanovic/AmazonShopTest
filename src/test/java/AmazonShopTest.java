import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import static org.openqa.selenium.Keys.ENTER;

public class AmazonShopTest {

    public static void main(String[] args) {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.navigate().to("https://www.amazon.com/");

        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys("Selenium Framework Design in Data-Driven Testing");
        searchBox.sendKeys(ENTER);

        WebElement book = driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[3]/div[2]/div[2]/div/div/div/div/div/div[1]/div/div[2]/div/span/a/div/img"));
        book.click();

        WebElement cartBeforeAdding = driver.findElement(By.id("nav-cart-count"));
        Assert.assertEquals(cartBeforeAdding.getText(), "0");

        WebElement addToCart = driver.findElement(By.id("add-to-cart-button"));
        addToCart.click();

        WebElement cartAfterAdding = driver.findElement(By.id("nav-cart-count"));
        Assert.assertEquals(cartAfterAdding.getText(), "1");

        WebElement notification = driver.findElement(By.cssSelector(".a-size-medium-plus.a-color-base.sw-atc-text.a-text-bold"));
        String expectedNotification = "Added to Cart";
        String actualNotification = notification.getText();
        Assert.assertEquals(actualNotification, expectedNotification);

        driver.manage().deleteCookieNamed("session-id");
        driver.navigate().refresh();

        WebElement emptyCartNotification = driver.findElement(By.cssSelector(".a-row.sc-your-amazon-cart-is-empty"));
        Assert.assertEquals(emptyCartNotification.getText(),"Your Amazon Cart is empty");

        WebElement cartAfterRemovingItem = driver.findElement(By.id("nav-cart-count"));
        Assert.assertEquals(cartAfterRemovingItem.getText(), "0");

    }
}
