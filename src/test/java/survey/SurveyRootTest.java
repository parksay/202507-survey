package survey;


import org.junit.jupiter.api.Test;
import org.parksay.Main;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Main.class)
public class SurveyRootTest {

    @Test
    public void contextLoads() {
        System.out.println("hello world!");
    }
}
