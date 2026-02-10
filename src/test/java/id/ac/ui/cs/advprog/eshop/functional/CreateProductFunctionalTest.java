package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {
    @LocalServerPort
    private int serverPort;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("http://localhost:%d/product/create", serverPort);
    }

    @Test
    void createProduct_isSuccessful(ChromeDriver driver) {
        driver.get(baseUrl);

        // Fill out the form
        driver.findElement(By.id("nameInput")).sendKeys("Sampo Cap Bambang");
        driver.findElement(By.id("quantityInput")).sendKeys("100");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Verify we are redirected to the list and see the new product
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Sampo Cap Bambang"));
        assertTrue(pageSource.contains("100"));
    }
}