import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CisternsTest {
    private Cisterns cisterns;
    private PipeSystem pipeSystem;
    private Game game;

    @BeforeEach
    public void init() {
        cisterns = new Cisterns();
        pipeSystem = mock(PipeSystem.class);
        game = mock(Game.class);
    }

    @Test
    void PickUpPump() {
        cisterns.CreatePump();
        cisterns.PickUpPump();
        assertEquals(0, cisterns.getAvailablePumps());
    }

    @Test
    void CreatePumpCreatesNewPump() {
        cisterns.CreatePump();
        assertEquals(1, cisterns.getAvailablePumps());
    }

    @Test
    void CreatePipeCreatesNewPipe() {
        cisterns.CreatePipe();
        assertEquals(1, cisterns.neighbours.size());
    }

    @Test
    void CreatePipeCreatesNewPipeWithNeighbour() {
        cisterns.CreatePipe();
        assertEquals(cisterns, cisterns.neighbours.get(0).neighbours.get(0));
    }

    @Test
    void CantPickUpPumpIfNoPumpsAvailable() {
        cisterns.PickUpPump();
        assertEquals(0, cisterns.getAvailablePumps());
    }

    @Test
    void CisternsAddsWaterForMechnaics() {
        cisterns.AddMWater(5);
        verify(pipeSystem,times(1)).incMWater(5);
    }  

    @Test
    void AfterRandomTimeAPipeIsCreated() {
        int size = cisterns.neighbours.size();
        cisterns.TimerNotify();
        if(size == cisterns.neighbours.size()) {
            AfterRandomTimeAPipeIsCreated();
        }
        assertEquals(1, cisterns.neighbours.size());
    }

    @Test
    void AfterRandomTimeAPumpIsCreated() {
        int size = cisterns.getAvailablePumps();
        cisterns.TimerNotify();
        if(size == cisterns.getAvailablePumps()) {
            AfterRandomTimeAPumpIsCreated();
        }
        assertEquals(1, cisterns.getAvailablePumps());
    }

    @Test
    void WaterAddedToCisterns() {
        Pipe pipe = mock(Pipe.class);
        when(pipe.GetStorage()).thenReturn(10);
        cisterns.Path(pipe);
        verify(pipe,times(1)).SetStorage(0);
    }
}
