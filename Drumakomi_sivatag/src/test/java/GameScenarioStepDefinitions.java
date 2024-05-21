import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class GameScenarioStepDefinitions {
    private Pump pump;
    private Pipe input;
    private Pipe output;

    private Saboteur saboteur;

    private Mechanic mechanic;

    private Pump extraPump;


   @Given("A pump with input and output pipes")
    public void aPumpWithInputAndOutputPipes() {
       pump = new Pump();
       input = new Pipe();
       output = new Pipe();
       pump.setInput(input);
       pump.setOutput(output);
       input.neighbours.add(pump);
       output.neighbours.add(pump);
       pump.neighbours.add(input);
       pump.neighbours.add(output);
   }

   @When("Some the time passes")
    public void someTheTimePasses() {
       pump.setBroken(true);
   }

   @Then("The pump is broken")
    public void thePumpIsBroken() {
       assertEquals(true, pump.getBroken());
   }

   @Given("A mechanic on the output pipe")
    public void aMechanicOnTheOutputPipe() {
       mechanic = new Mechanic(0, 0, output);
   }

   @Given("A saboteur on the input pipe")
    public void aSaboteur() {
       saboteur = new Saboteur(0, 0, input);
   }

   @And("The {string} steps to the {string}")
    public void theSaboteurStepsToThePump(String who, String where) {
       Player player;
       Element element;
       if(who.equals("saboteur")) {
           player = saboteur;
       } else {
           player = mechanic;
       }
       if(where.equals("pump")) {
              element = pump;
       } else if(where.equals("input")) {
              element = input;
       } else if (where.equals("extra pump")) {
              element = extraPump;
       } else {
              element = output;
       }
       player.MoveToElement(element);
   }

   @When("The mechanic repairs the pump")
    public void theMechanicRepairsThePump() {
       mechanic.Repair();
   }

    @Then("The pump is working")
     public void thePumpIsFixed() {
         assertEquals(false, pump.getBroken());
    }

    @Then("The {string} is on the {string}")
    public void thePlayerIsOnTheElement(String who, String where) {
        Player player;
        Element element;
        if(who.equals("saboteur")) {
            player = saboteur;
        } else {
            player = mechanic;
        }
        if(where.equals("pump")) {
            element = pump;
        } else if(where.equals("input")) {
            element = input;
        } else if (where.equals("extra pump")) {
            element = extraPump;
        } else {
            element = output;

        }
        assertEquals(element, player.getElement());
    }

    @Given("An addition pump connected to the output pipe")
    public void anAdditionPumpConnectedToTheOutputPipe() {
        extraPump = new Pump();
        extraPump.setOutput(output);
        output.neighbours.add(extraPump);
        extraPump.neighbours.add(output);
    }

    @When("The {string} detaches the output pipe from the pump")
    public void theDetachesThe(String who) {
       pump.detachPipe(output);
    }
}
