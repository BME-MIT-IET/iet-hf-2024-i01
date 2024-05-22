import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PumpTest {

    private Pump pump;
    private Pipe inputPipe = mock(Pipe.class);
    private Pipe outputPipe = mock(Pipe.class);

    @BeforeEach
    public void init() {
        pump = new Pump();
        pump.setInput(inputPipe);
        pump.setOutput(outputPipe);
    }

    @Test
    void PumpIsNotBrokenInTheBeginning() {
        assertEquals(false, pump.getBroken());
    }

    // @Test
    // void PumpIsBrokenAfterRandomTime() {
    //     pump.TimerNotify();
    //     if (pump.getBroken() == true) {
    //         assertEquals(true, pump.getBroken());
    //         return;
    //     } else {
    //         PumpIsBrokenAfterRandomTime();
    //     }
    // }

    @Test
    void PumpIsNotBrokenAfterRepair() {
        pump.Damage();
        pump.Repair();
        assertEquals(false, pump.getBroken());
    }

    // @Test
    // void PumpIsBrokenAfterRepairAndRandomTime() {
    //     pump.Damage();
    //     pump.Repair();
    //     pump.TimerNotify();
    //     if (pump.getBroken() == true) {
    //         assertEquals(true, pump.getBroken());
    //         return;
    //     } else {
    //         PumpIsBrokenAfterRepairAndRandomTime();
    //     }
    // }

    @Test
    void SetPumpChangesInputPipe() {
        Pipe newInputPipe = mock(Pipe.class);
        pump.setInput(newInputPipe);
        assertEquals(newInputPipe, pump.getInput());
    }

    @Test
    void SetPumpChangesOutputPipe() {
        Pipe newOutputPipe = mock(Pipe.class);
        pump.setOutput(newOutputPipe);
        assertEquals(newOutputPipe, pump.getOutput());
    }

    @Test
    void TargetPipeHasCorrectWaterAmount() {
        when(inputPipe.GetStorage()).thenReturn(10);
        pump.Path(inputPipe);
        verify(inputPipe, times(1)).SetStorage(7);
    }

    @Test
    void PumpDoesNotPumpWaterIfInputPipeIsBroken() {
        when(inputPipe.GetStorage()).thenReturn(10);
        when(inputPipe.getDamaged()).thenReturn(true);
        pump.Path(inputPipe);
        verify(inputPipe, times(0)).SetStorage(0);
    }

    @Test
    void PumpIsBrokenSoPathIsNotCalled() {
        pump.Damage();
        pump.Path(inputPipe);
        verify(inputPipe, times(0)).SetStorage(1);
    }

    @Test
    void PumpHasPipeAsNeighbour() {
        Pipe newPipe = mock(Pipe.class);
        pump.SetNeighbour(newPipe);
        assertEquals(newPipe, pump.neighbours.get(0));
    }

    @Test
    void PumpHasMultiplePipesAsNeighbours() {
        Pipe newPipe1 = mock(Pipe.class);
        Pipe newPipe2 = mock(Pipe.class);
        Pipe newPipe3 = mock(Pipe.class);
        pump.SetNeighbour(newPipe1);
        pump.SetNeighbour(newPipe2);
        pump.SetNeighbour(newPipe3);
        assertEquals(newPipe1, pump.neighbours.get(0));
        assertEquals(newPipe2, pump.neighbours.get(1));
        assertEquals(newPipe3, pump.neighbours.get(2));
        assertNotEquals(newPipe3, pump.neighbours.get(0));
    }

    @Test
    void NewInputAndOutputPipesAreSet() {
        Pipe newInputPipe = mock(Pipe.class);
        Pipe newOutputPipe = mock(Pipe.class);
        pump.SetNeighbour(newInputPipe);
        pump.SetNeighbour(newOutputPipe);
        pump.SetPump(newInputPipe, newOutputPipe);
        assertEquals(newInputPipe, pump.getInput());
        assertEquals(newOutputPipe, pump.getOutput());
    }

    @Test
    void PumpNoLongerPumpsWaterAfter10Times() {
        when(inputPipe.GetStorage()).thenReturn(33);
        for (int i = 0; i < 10; i++) {
            pump.Path(inputPipe);
        }
        verify(inputPipe, times(1)).SetStorage(30);
        verify(inputPipe, times(8)).SetStorage(33);
        pump.Path(inputPipe);
        verify(inputPipe, times(0)).SetStorage(0);
    }

    @Test
    void PumpDoesNotPumpWaterIfInputPipeIsNotNeighbour() {
        Pipe newPipe = mock(Pipe.class);
        pump.Path(newPipe);
        verify(newPipe, times(0)).SetStorage(1);
    }

    @Test
    void PumpDoesNotPumpWaterIfOutputPipeIsNotNeighbour() {
        Pipe newPipe = mock(Pipe.class);
        pump.SetNeighbour(inputPipe);
        pump.Path(newPipe);
        verify(newPipe, times(0)).SetStorage(1);
    }

    @Test
    void PumpDoesNotPumpWaterIfInputPipeIsNotSet() {
        pump.setInput(null);
        pump.Path(inputPipe);
        verify(inputPipe, times(0)).SetStorage(1);
    }

    @Test
    void PumpDoesNotPumpWaterIfOutputPipeIsNotSet() {
        pump.setOutput(null);
        pump.Path(inputPipe);
        verify(outputPipe, times(0)).SetStorage(1);
    }
}
