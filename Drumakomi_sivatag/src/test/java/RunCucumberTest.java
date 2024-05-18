import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;



@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "summary"})
public class RunCucumberTest {

}
