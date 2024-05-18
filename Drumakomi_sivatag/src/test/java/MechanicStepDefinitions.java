import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MechanicStepDefinitions {
    private Pipe pipe = null;

    private Cisterns cistern;

    private Mechanic mechanic;

    private Pump pump;

    @Given("a mechanic")
    public void aMechanic() {
        if(pipe == null) {
            pipe = new Pipe();
        }
        mechanic = new Mechanic(1, 0, pipe);
    }


    @Given("a pipe")
    public void aPipe() {
        pipe = new Pipe();
    }

    @And("the pipes is {string}")
    public void thePipesIs(String condition) {
        if(condition.equals("intact")) {
            pipe.setDamaged(false);
        }
        else if(condition.equals("damaged")) {
            pipe.setDamaged(true);
        }
        if (condition.equals("sticky")) {
            pipe.setStucky(10);
        }
    }

    @And("the mechanic is on the {string}")
    public void theMechanicIsOnThe(String element) {
        if(element.equals("pipe")) {
            if (pipe == null) {
                pipe = new Pipe();
            }
            mechanic.setElement(pipe);
        }
        if (element.equals("cistern")) {
            if (cistern == null) {
                cistern = new Cisterns();
            }
            mechanic.setElement(cistern);
        }
    }

    @When("The mechanic repairs the pipe")
    public void theMechanicRepairsThePipe() {
        mechanic.Repair();
    }

    @Then("the pipe should be {string}")
    public void thePipeShouldBe(String condition) {
        if(condition.equals("intact")) {
            assertEquals(false, pipe.getDamaged());
        }
        else if(condition.equals("damaged")) {
            assertEquals(true, pipe.getDamaged());
        }
    }

    @Given("a cistern")
    public void aCistern() {
        cistern = new Cisterns();
    }

    @And("the cistern has a pump")
    public void theCisternHasAPump() {
        cistern.CreatePump();
    }


    @When("The mechanic picks up the pump")
    public void theMechanicPicksUpThePump() {
        mechanic.Pickuppump();
    }

    @Then("the pump should be in the mechanic's hands")
    public void thePumpShouldBeInTheMechanicSHands() {
        assertEquals(mechanic.getPumpes(), 1);
    }
}
