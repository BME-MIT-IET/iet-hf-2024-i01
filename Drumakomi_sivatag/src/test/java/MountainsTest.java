import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;

class MountainsTest {
    private Mountains mountains;

    @BeforeEach
    public void init() {
        mountains = new Mountains();
    }

    @Test
    void PathCallsNeighboursPath() {
        Element element = mock(Element.class);
        Pipe pipe = mock(Pipe.class);
        Pump pump = mock(Pump.class);
        mountains.SetNeighbour(element);
        mountains.SetNeighbour(pipe);
        mountains.SetNeighbour(pump);
        assertEquals(element,mountains.GetNeighbours().get(0));
        mountains.TimerNotify();
        verify(element, times(1)).Path(mountains);
        verify(pipe, times(1)).Path(mountains);
        verify(pump, times(1)).Path(mountains);
    }

    @Test
    void PathCanRunOnlyOnce() {
        Element element = mock(Element.class);
        mountains.SetNeighbour(element);
        mountains.TimerNotify();
        mountains.Path(null);
        verify(element, times(1)).Path(mountains);
    }

    @Test
    void GetStorageReturnsMaxValue() {
        assertEquals(Integer.MAX_VALUE, mountains.GetStorage());
    }
}
