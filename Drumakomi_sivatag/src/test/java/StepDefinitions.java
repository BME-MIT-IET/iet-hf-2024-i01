import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class StepDefinitions {

    private Saboteur saboteur;

    private Pipe pipe = null;

    private Pump pump = null;

    @Given("A pipe")
    public void elements(String type) {
        pipe = new Pipe();
    }

    @And("The pipe is {string}")
    public void the_pipe_is(String condition) {
        if(condition.equals("intact")) {
            pipe.setDamaged(true);
        }
        else if(condition.equals("damaged")) {
            pipe.setDamaged(false);
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


    @When("The saboteur {string} the element")
    public void saboteur_damages_the_pipe(String action) {
        if(action.equals("damages")) {
            saboteur.getElement().Damage();
        }
    }



    @Then("The pipe should be {string}")
    public void the_pipe_should_be(String condition) {
        if(condition.equals("damaged")) {
            assertEquals(pipe.getDamaged(), true);
        }
    }

    @Then("The pump should be {string}")
    public void the_pump_should_be(String condition) {
        if(condition.equals("damaged")) {
            assertEquals(pump.getBroken(), false);
        }
    }



}
