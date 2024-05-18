
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SaboteurStepDefinitions {

    private Saboteur saboteur;

    private Pipe pipe = null;

    private Pump pump = null;

    private Mechanic mechanic;

    @Given("A pipe")
    public void a_pipe() {
        pipe = new Pipe();
    }

    @Given("A pump")
    public void a_pump() {
        pump = new Pump();
    }


    @And("The pipe is {string}")
    public void the_pipe_is(String condition) {
        if(condition.equals("intact")) {
            pipe.setDamaged(true);
        }
        if(condition.equals("damaged")) {
            pipe.setDamaged(false);
        }
        if (condition.equals("sticky")) {
            pipe.setStucky(10);
        }
        if (condition.equals("not slippery")) {
            pipe.setSlippery(0);
        }
        if (condition.equals("not sticky")) {
            pipe.setSlippery(0);
        }
    }

    @Given("A saboteur")
    public void a_saboteur(){
        if(pipe == null) {
            pipe = new Pipe();
        }
        saboteur = new Saboteur(1, 0, pipe);
    }

    @And("The saboteur is on a {string}")
    public void the_saboteur_is_on_a(String type) {
        if(type.equals("pipe")) {
            if (pipe == null) {
                pipe = new Pipe();
            }
            saboteur = new Saboteur(1, 0, pipe);
        }
        else if (type.equals("pump")) {
            if (pump == null) {
                pump = new Pump();
            }
            saboteur = new Saboteur(1, 0, pump);
        }
    }



    @And("The pump is {string}")
    public void the_pump_is(String condition) {
        if(condition.equals("intact")) {
            pump.setBroken(false);
        }
        else if(condition.equals("broken")) {
            pump.setBroken(true);
        }
    }

    @And("The pump and the pipe are connected")
    public void the_pipe_and_the_pump_are_connected() {
        if(pipe == null) {
            pipe = new Pipe();
        }
        if(pump == null) {
            pump = new Pump();
        }
        pump.setInput(pipe);
    }


    @When("The saboteur {string} the element")
    public void saboteur_damages_the_pipe(String action) {
        if(action.equals("damages")) {
            saboteur.getElement().Damage();
        }
    }

    @When ("The saboteur steps to a pipe")
    public void saboteur_steps_to_a_pipe() {
        saboteur.setElement(pipe);
    }

    @Then ("The saboteur should be on a {string}")
    public void the_saboteur_should_be_on_a_pipe(String element) {
        if(element.equals("pipe")) {
            assertEquals(saboteur.getElement(), pipe);
        } else if (element.equals("pump")) {
            assertEquals(saboteur.getElement(), pump);
        }
    }



    @Then("The pipe should be {string}")
    public void the_pipe_should_be(String condition) {
        if(condition.equals("damaged")) {
            assertEquals(pipe.getDamaged(), true);
        }
        if(condition.equals("sticky")) {
            assertEquals(pipe.isstucky(), true);
        }
        if(condition.equals("slippery")) {
            assertEquals(pipe.isSlippery(), true);
        }
    }

    @Then("The pump should be {string}")
    public void the_pump_should_be(String condition) {
        if(condition.equals("damaged")) {
            assertEquals(pump.getBroken(), false);
        }
    }


    @When("The saboteur steps to a pump")
    public void theSaboteurStepsToAPump() {
        saboteur.MoveToElement(pump);
    }

    @When("The saboteur make the element to {string}")
    public void theSaboteurMakeTheElementTo(String condition) {
        if(condition.equals("slippery")) {
            saboteur.SetSlippery();
        }
        if (condition.equals("sticky")) {
            saboteur.SetStucky();
        }
    }
}
