package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EshopApplicationTests {

    @Test
    void contextLoads() {
        // This method is intentionally empty to test if the Spring context loads successfully
    }

    @Test
    void testMain() {
        // This explicitly calls the main method to provide coverage
        EshopApplication.main(new String[]{});
    }
}