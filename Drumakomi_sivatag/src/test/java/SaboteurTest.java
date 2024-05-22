import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

public class SaboteurTest {
    private Saboteur saboteur;
    private Cisterns cistern;
    private Pipe pipe;
    private Pipe pipe1;
    private Pipe pipe2;
    private Pump pump;
    private ArrayList<Element> neighbours;

    @BeforeEach
    public void setUp() {
        cistern = mock(Cisterns.class);
        pipe = mock(Pipe.class);
        pipe1 = mock(Pipe.class);
        pipe2 = mock(Pipe.class);
        pump = mock(Pump.class);
        saboteur = new Saboteur(1, 0, cistern);
       
        neighbours = new ArrayList<>(Arrays.asList(pipe, pump, pipe2));
        when(cistern.GetNeighbours()).thenReturn(neighbours);
    }

    @Test
    void testGetId() {
        assertEquals(1, saboteur.getId());
    }

    @Test
    void testGetCantMove() {
        assertEquals(0, saboteur.getcantMove());
    }

    @Test
    void testGetElement() {
        assertEquals(cistern, saboteur.getElement());
    }

    @Test
    void testSetElement() {
        Pipe newElement = mock(Pipe.class);
        saboteur.setElement(newElement); 
        assertEquals(newElement, saboteur.getElement()); 
    }

    @Test
    void testSetCantMove() {
        saboteur.setCantMove(2); 
        assertEquals(2, saboteur.getcantMove());
    }

    @Test
    public void testStuckySuccess() {
        saboteur.setElement(pipe1);
        pipe1.SetPlayer(saboteur);
        when(pipe1.SetStucky()).thenReturn(true);

        boolean result =  saboteur.SetStucky();

        assertTrue(result, "SetStucky should return true.");
        verify(pipe1,times(1)).SetStucky();
    }

    @Test
    public void testStuckyFailure() {
        saboteur.setElement(pipe1);
        pipe1.SetPlayer(saboteur);
        when(pipe1.SetStucky()).thenReturn(false);

        boolean result =  saboteur.SetStucky();

        assertFalse(result, "SetStucky should return false.");
        verify(pipe1,times(1)).SetStucky();
    }

    @Test
    public void testInfo() {
        saboteur.Info();
        verify(cistern, times(1)).Info();
    }

    @Test
    public void testInfoWithIndex() {
        int index = 1;
        saboteur.Info(index);
        verify(cistern, times(1)).Info(index);
    }


    @Test
    public void testMoveSuccess() {
        when(pipe.Move(saboteur)).thenReturn(true);

        boolean result = saboteur.Move(0);

        assertTrue(result, "Move should return true when movement is successful.");
        assertEquals(pipe, saboteur.getElement(), "The saboteur should have moved to the new element.");
        verify(cistern, times(1)).Remove(saboteur);
        verify(pipe, times(1)).SetPlayer(saboteur);
    }

    @Test
    public void testMoveFailure() {
        when(pipe.Move(saboteur)).thenReturn(false);

        boolean result = saboteur.Move(0);

        assertFalse(result, "Move should return false when movement is not successful.");
        assertEquals(cistern, saboteur.getElement(), "The saboteur should remain on the original element.");
        verify(cistern, never()).Remove(saboteur);
        verify(pipe, never()).SetPlayer(saboteur);
    }



    @Test
    public void testMoveInvalidIndex() {
        boolean result = saboteur.Move(-1);

        assertFalse(result, "Move should return false for an invalid index.");
        verify(cistern, never()).Remove(saboteur);
        verify(pipe, never()).SetPlayer(saboteur);

        result = saboteur.Move(neighbours.size());

        assertFalse(result, "Move should return false for an out-of-bounds index.");
        verify(cistern, never()).Remove(saboteur);
        verify(pipe, never()).SetPlayer(saboteur);
    }

    @Test
    public void testMoveWhenCantMove() {
        saboteur.setCantMove(2);

        boolean result = saboteur.Move(0);

        assertFalse(result, "Move should return false when saboteur cannot move.");
        assertEquals(1, saboteur.getcantMove(), "CantMove should decrement by 1.");
        assertEquals(cistern,saboteur.getElement(), "The saboteur should remain on the original element.");
        verify(cistern, never()).Remove(saboteur);
        verify(pipe, never()).SetPlayer(saboteur);
    }


    @Test
    public void testMoveToElementSuccess() {
        when(pipe.Move(saboteur)).thenReturn(true);

        boolean result = saboteur.MoveToElement(pipe);

        assertTrue(result, "MoveToElement should return true when movement is successful.");
        assertEquals(pipe, saboteur.getElement(), "The saboteur should have moved to the new element.");
        verify(cistern, times(1)).Remove(saboteur);
        verify(pipe, times(1)).SetPlayer(saboteur);
    }

    @Test
    public void testMoveToElementFailure() {
        when(pipe.Move(saboteur)).thenReturn(false);

        boolean result = saboteur.MoveToElement(pipe);

        assertFalse(result, "MoveToElement should return false when movement is not successful.");
        assertEquals(cistern, saboteur.getElement(), "The saboteur should remain on the original element.");
        verify(cistern, never()).Remove(saboteur);
        verify(pipe, never()).SetPlayer(saboteur);
    }


    @Test
    public void testMoveToElementNonNeighbour() {
        Pipe nonNeighbourPipe = mock(Pipe.class);

        boolean result = saboteur.MoveToElement(nonNeighbourPipe);

        assertFalse(result, "MoveToElement should return false when the target element is not a neighbour.");
        assertEquals(cistern, saboteur.getElement(), "The saboteur should remain on the original element.");
        verify(cistern, never()).Remove(saboteur);
        verify(nonNeighbourPipe, never()).SetPlayer(saboteur);
    }

    @Test
    public void testMoveToElementWhenCantMove() {
        saboteur.setCantMove(2);

        boolean result = saboteur.MoveToElement(pipe);

        assertFalse(result, "MoveToElement should return false when saboteur cannot move.");
        assertEquals(1, saboteur.getcantMove(), "CantMove should decrement by 1.");
        assertEquals(cistern, saboteur.getElement(), "The saboteur should remain on the original element.");
        verify(cistern, never()).Remove(saboteur);
        verify(pipe, never()).SetPlayer(saboteur);
    }


    @Test
    public void testSlipperySuccess() {
        saboteur.setElement(pipe1);
        pipe1.SetPlayer(saboteur);
        when(pipe1.SetSlippery()).thenReturn(true);

        boolean result =  saboteur.SetSlippery();

        assertTrue(result, "SetSlippery should return true.");
        verify(pipe1,times(1)).SetSlippery();
    }

    /*@Test
    public void testSlipperyFailure() {
        saboteur.setElement(pipe1);
        pipe1.SetPlayer(saboteur);
        when(pipe1.SetSlippery()).thenReturn(false);

        boolean result =  saboteur.SetSlippery();

        assertFalse(result, "SetSlippery should return false.");
        verify(pipe1,times(1)).SetSlippery();
    }*/
}
