import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class PumpStepDefinitions {
    private Pump pump;

    private Pipe input;
    private Pipe output;

    private int inputWater;

    private int outputWater;

    @Given("A pump with 2 connected pipes")
    public void a_pump_with_2_connected_pipes() {
        pump = new Pump();
        input = new Pipe();
        output = new Pipe();
        pump.setInput(input);
        pump.setOutput(output);
    }

    @When("The pump {string}")
    public void thePump(String condition) {
        if (condition.equals("working")) {
            pump.setBroken(false);
            pump.Path(input);
        }
        if (condition.equals("broken")) {
            pump.setBroken(true);
            pump.Path(input);
        }
    }


    @And("There's {int} unit of water in the source")
    public void thereSUnitOfWaterInTheSource(int water) {
        input.SetStorage(water);
        inputWater = water;
    }

    @And("There's {int} unit of water in the destination")
    public void thereSUnitOfWaterInTheDestination(int water) {
        output.SetStorage(water);
        outputWater = water;
    }

    @Then("The pump should move the water from the source to the destination")
    public void thePumpShouldMoveTheWaterFromTheSourceToTheDestination() {
        assertEquals(0, input.GetStorage());
        assertEquals(inputWater + outputWater, output.GetStorage());
    }

    @Then("The pump should not move the water from the source to the destination")
    public void thePumpShouldNotMoveTheWaterFromTheSourceToTheDestination() {
        assertEquals(inputWater, input.GetStorage());
        assertEquals(outputWater, output.GetStorage());
    }
}
