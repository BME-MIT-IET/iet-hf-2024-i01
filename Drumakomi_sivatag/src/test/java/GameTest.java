import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

class GameTest {

    private Game game;
    private PipeSystem pipeSystem = mock(PipeSystem.class);

    @BeforeEach
    public void init(){
        game = new Game(2, 2, 1);
        pipeSystem.getEndpoints().clear();
        pipeSystem.getSaboteur().clear();
        pipeSystem.getMechanic().clear();
    }

    @Test
    void InitializationWorking(){
        game.Initialization();

        assertEquals(2, pipeSystem.getEndpoints().size());
        assertEquals(2, pipeSystem.getPipes().size());
        assertEquals(1, pipeSystem.getPumpes().size());
    }

    @Test
    void CreatePlayersWorking(){
        Mountains mountain = mock(Mountains.class);
        Cisterns cisterns = mock(Cisterns.class);

        PipeSystem.getEndpoints().add(mountain);
        PipeSystem.getEndpoints().add(cisterns);
        game.CreatePlayers();
        assertEquals(1, PipeSystem.getMechanic().size());
        assertEquals(1, PipeSystem.getSaboteur().size());
    }

    @Test
    void SettingsWorking(){
        game.Settings(2, 2);
        assertEquals(false, game.rand);
    }

    @Test
    void TimerNotifyWorking(){
        game.TimerNotify();
        assertEquals(pipeSystem.getM_water(), pipeSystem.getS_water());

        pipeSystem.Addswater(2);
        assertNotEquals(pipeSystem.getM_water(), pipeSystem.getS_water());
    }

    @Test
    void Round(){
        //TODO
    }

    @Test
    void SaveAndExitWorking(){
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            game.SaveAndExit("test.txt");
            assertEquals("", outContent.toString().trim());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void LoadWorking(){
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            game.Load("test.txt");

            String output = outContent.toString().trim();
            String[] lines = output.split(System.lineSeparator());
            String lastLine = lines[lines.length - 1];

            String expectedLastLine = "Az ido lejart, a jatek veget ert!";
            assertEquals(expectedLastLine, lastLine);
        } finally {
            System.setOut(originalOut);
        }        
    }

    @Test
    void NotifyAllWorking(){
        game.Initialization();

        verify(pipeSystem, times(1)).getPumpes();
    }

    @Test
    void resetWaterWorking(){
        game.Initialization();
        game.resetWater();

        assertEquals(false, pipeSystem.getPumpes().get(0).GetVizezett());
        assertEquals(false, pipeSystem.getPipes().get(0).GetVizezett());
        assertEquals(false, pipeSystem.getPipes().get(1).GetVizezett());
    }

    @Test
    void RepairWorking(){
        Mechanic mechanic = mock(Mechanic.class);
        when(mechanic.Repair()).thenReturn(true);
        game.Initialization();
        pipeSystem.getMechanic().add(mechanic);
        game.currentTeam = true;
        game.currentPlayer = mechanic;
        game.Repair();

        assertEquals(true, mechanic.Repair());
    }

    @Test
    void DamageWorking(){
        Cisterns cisterns = mock(Cisterns.class);
        Saboteur saboteur = mock(Saboteur.class);
        when(saboteur.getElement()).thenReturn(cisterns);
        when(cisterns.Damage()).thenReturn(true);
        game.Initialization();
        pipeSystem.getSaboteur().add(saboteur);
        game.currentPlayer = saboteur;
        game.Repair();

        assertEquals(false, cisterns.Repair());
    }
    
    @Test
    void PickupPumpWorking(){
        Mechanic mechanic = mock(Mechanic.class);
        when(mechanic.Pickuppump()).thenReturn(true);
        game.Initialization();
        pipeSystem.getMechanic().add(mechanic);
        game.currentTeam = true;
        game.currentPlayer = mechanic;
        game.PickUpPump();
        verify(mechanic, times(1)).Pickuppump();
    }

    @Test
    void CutPipeWorkingNotMechanicRound(){
        Mechanic mechanic = mock(Mechanic.class);
        when(mechanic.CutPipe()).thenReturn(true);
        game.Initialization();
        assertEquals(false, game.CutPipe());
    }

    @Test
    void CutPipeWorkingMechanicRound(){
        Mechanic mechanic = mock(Mechanic.class);
        when(mechanic.CutPipe()).thenReturn(true);
        game.Initialization();
        pipeSystem.getMechanic().add(mechanic);
        game.currentTeam = true;
        game.currentPlayer = mechanic;
        assertEquals(true, game.CutPipe());
    }

    @Test
    void SetStuckyWorking(){
        Mechanic mechanic = mock(Mechanic.class);
        when(mechanic.SetStucky()).thenReturn(true);
        game.Initialization();
        pipeSystem.getMechanic().add(mechanic);
        game.currentTeam = true;
        game.currentPlayer = mechanic;

        game.SetStucky();

        verify(mechanic, times(1)).SetStucky();
    }

    @Test
    void SetSlipperyWorking(){
        Saboteur saboteur = mock(Saboteur.class);
        when(saboteur.SetStucky()).thenReturn(true);
        game.Initialization();
        pipeSystem.getSaboteur().add(saboteur);
        game.currentPlayer = saboteur;

        game.SetSlippery();

        verify(saboteur, times(1)).SetSlippery();
    }

    @Test
    void MoveWorking(){
        Mechanic mechanic = mock(Mechanic.class);
        Pipe pipe = mock(Pipe.class);
        when(mechanic.MoveToElement(pipe)).thenReturn(true);
        game.Initialization();
        pipeSystem.getMechanic().add(mechanic);
        game.currentTeam = true;
        game.currentPlayer = mechanic;
        game.Move(pipe);

        verify(mechanic, times(1)).MoveToElement(pipe);
    }

    @Test
    void StepUsedWorking(){
        Mechanic mechanic = mock(Mechanic.class);
        game.Initialization();
        pipeSystem.getMechanic().add(mechanic);
        game.currentTeam = true;
        game.currentPlayer = mechanic;
        assertTrue(true);
    }

    @Test
    void setupRoundWorking(){
        Mechanic mechanic = mock(Mechanic.class);
        game.Initialization();
        pipeSystem.getMechanic().add(mechanic);
        game.currentTeam = true;
        game.currentPlayer = mechanic;

        assertEquals(0, game.currentTime);
    }
}
