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

public class MechanicTest {

    private Mechanic mechanic;
    private Mountains mountain;
    private Pipe pipe;
    private Pipe pipe1;
    private Pipe pipe2;
    private Pump pump;
    private ArrayList<Element> neighbours;

    @BeforeEach
    public void setUp() {
        mountain = mock(Mountains.class);
        pipe = mock(Pipe.class);
        pipe1 = mock(Pipe.class);
        pipe2 = mock(Pipe.class);
        pump = mock(Pump.class);
        mechanic = new Mechanic(1, 0, mountain);
       
        neighbours = new ArrayList<>(Arrays.asList(pipe, pump, pipe2));
        when(mountain.GetNeighbours()).thenReturn(neighbours);
    }

    @Test
    void testGetId() {
        assertEquals(1, mechanic.getId());
    }

    @Test
    void testGetCantMove() {
        assertEquals(0, mechanic.getcantMove());
    }

    @Test
    void testGetElement() {
        assertEquals(mountain, mechanic.getElement());
    }

    @Test
    void testSetElement() {
        Pipe newElement = mock(Pipe.class);
        mechanic.setElement(newElement); 
        assertEquals(newElement, mechanic.getElement()); 
    }

    @Test
    void testSetCantMove() {
        mechanic.setCantMove(2); 
        assertEquals(2, mechanic.getcantMove());
    }

    @Test
    public void testStuckySuccess() {
        mechanic.setElement(pipe1);
        pipe1.SetPlayer(mechanic);
        when(pipe1.SetStucky()).thenReturn(true);

        boolean result =  mechanic.SetStucky();

        assertTrue(result, "SetStucky should return true.");
        verify(pipe1,times(1)).SetStucky();
    }

    @Test
    public void testStuckyFailure() {
        mechanic.setElement(pipe1);
        pipe1.SetPlayer(mechanic);
        when(pipe1.SetStucky()).thenReturn(false);

        boolean result =  mechanic.SetStucky();

        assertFalse(result, "SetStucky should return false.");
        verify(pipe1,times(1)).SetStucky();
    }

    @Test
    public void testInfo() {
        mechanic.Info();
        verify(mountain, times(1)).Info();
    }

    @Test
    public void testInfoWithIndex() {
        int index = 1;
        mechanic.Info(index);
        verify(mountain, times(1)).Info(index);
    }


    @Test
    public void testMoveSuccess() {
        when(pipe.Move(mechanic)).thenReturn(true);

        boolean result = mechanic.Move(0);

        assertTrue(result, "Move should return true when movement is successful.");
        assertEquals(pipe, mechanic.getElement(), "The mechanic should have moved to the new element.");
        verify(mountain, times(1)).Remove(mechanic);
        verify(pipe, times(1)).SetPlayer(mechanic);
    }

    @Test
    public void testMoveFailure() {
        when(pipe.Move(mechanic)).thenReturn(false);

        boolean result = mechanic.Move(0);

        assertFalse(result, "Move should return false when movement is not successful.");
        assertEquals(mountain, mechanic.getElement(), "The mechanic should remain on the original element.");
        verify(mountain, never()).Remove(mechanic);
        verify(pipe, never()).SetPlayer(mechanic);
    }



    @Test
    public void testMoveInvalidIndex() {
        boolean result = mechanic.Move(-1);

        assertFalse(result, "Move should return false for an invalid index.");
        verify(mountain, never()).Remove(mechanic);
        verify(pipe, never()).SetPlayer(mechanic);

        result = mechanic.Move(neighbours.size());

        assertFalse(result, "Move should return false for an out-of-bounds index.");
        verify(mountain, never()).Remove(mechanic);
        verify(pipe, never()).SetPlayer(mechanic);
    }

    @Test
    public void testMoveWhenCantMove() {
        mechanic.setCantMove(2);

        boolean result = mechanic.Move(0);

        assertFalse(result, "Move should return false when mechanic cannot move.");
        assertEquals(1, mechanic.getcantMove(), "CantMove should decrement by 1.");
        assertEquals(mountain, mechanic.getElement(), "The mechanic should remain on the original element.");
        verify(mountain, never()).Remove(mechanic);
        verify(pipe, never()).SetPlayer(mechanic);
    }


    @Test
    public void testMoveToElementSuccess() {
        when(pipe.Move(mechanic)).thenReturn(true);

        boolean result = mechanic.MoveToElement(pipe);

        assertTrue(result, "MoveToElement should return true when movement is successful.");
        assertEquals(pipe, mechanic.getElement(), "The mechanic should have moved to the new element.");
        verify(mountain, times(1)).Remove(mechanic);
        verify(pipe, times(1)).SetPlayer(mechanic);
    }

    @Test
    public void testMoveToElementFailure() {
        when(pipe.Move(mechanic)).thenReturn(false);

        boolean result = mechanic.MoveToElement(pipe);

        assertFalse(result, "MoveToElement should return false when movement is not successful.");
        assertEquals(mountain, mechanic.getElement(), "The mechanic should remain on the original element.");
        verify(mountain, never()).Remove(mechanic);
        verify(pipe, never()).SetPlayer(mechanic);
    }


    @Test
    public void testMoveToElementNonNeighbour() {
        Pipe nonNeighbourPipe = mock(Pipe.class);

        boolean result = mechanic.MoveToElement(nonNeighbourPipe);

        assertFalse(result, "MoveToElement should return false when the target element is not a neighbour.");
        assertEquals(mountain, mechanic.getElement(), "The mechanic should remain on the original element.");
        verify(mountain, never()).Remove(mechanic);
        verify(nonNeighbourPipe, never()).SetPlayer(mechanic);
    }

    @Test
    public void testMoveToElementWhenCantMove() {
        mechanic.setCantMove(2);

        boolean result = mechanic.MoveToElement(pipe);

        assertFalse(result, "MoveToElement should return false when mechanic cannot move.");
        assertEquals(1, mechanic.getcantMove(), "CantMove should decrement by 1.");
        assertEquals(mountain, mechanic.getElement(), "The mechanic should remain on the original element.");
        verify(mountain, never()).Remove(mechanic);
        verify(pipe, never()).SetPlayer(mechanic);
    }

    @Test
    void testGetPumpes() {
        assertEquals(0, mechanic.getPumpes());
    }

    @Test
    void testSetPumpes() {
        mechanic.setPumpes(5); 
        assertEquals(5, mechanic.getPumpes());
    }

    @Test
    public void testSetPump() {
        Pump pumpa = mock(Pump.class);
        //pipe2 = mock(Pipe.class);
        mechanic.setElement(pumpa);
        pumpa.SetPlayer(mechanic);
        when(pumpa.SetPump(pipe, pipe2)).thenReturn(true);

        boolean result = mechanic.setPump(pipe, pipe2);

        assertTrue(result, "Setting pumpa should return true.");
        verify(pumpa,times(1)).SetPump(pipe, pipe2);
    }

    @Test
    public void testSetPumpFailure() {
        Pump pumpa = mock(Pump.class);
        //pipe2 = mock(Pipe.class);
        mechanic.setElement(pumpa);
        pumpa.SetPlayer(mechanic);
        when(pumpa.SetPump(pipe, pipe2)).thenReturn(false);

        boolean result = mechanic.setPump(pipe, pipe2);

        assertFalse(result, "Setting pumpa should return true.");
        verify(pumpa,times(1)).SetPump(pipe, pipe2);
    }

    @Test
    public void testRepairSuccess() {
        mechanic.setElement(pipe1);
        pipe1.SetPlayer(mechanic);
        when(pipe1.Repair()).thenReturn(true);

        boolean result =  mechanic.Repair();

        assertTrue(result, "Repair should return true.");
        verify(pipe1,times(1)).Repair();
    }

    @Test
    public void testRepairFailure() {
        mechanic.setElement(pipe1);
        pipe1.SetPlayer(mechanic);
        when(pipe1.Repair()).thenReturn(false);

        boolean result =  mechanic.Repair();

        assertFalse(result, "Repair should return false.");
        verify(pipe1,times(1)).Repair();
    }

    @Test
    public void testPickuppumpSuccess() {
        mechanic.setElement(pipe);
        pipe.SetPlayer(mechanic);
        when(pipe.PickUpPump()).thenReturn(true);

        boolean result = mechanic.Pickuppump();

        assertTrue(result, "Pick up pump should return true.");
        assertEquals(1, mechanic.getPumpes(), "Pump count should be 1 after successful pickup.");
        verify(pipe,times(1)).PickUpPump();
    }

    @Test
    public void testPickuppumpFailure() {
        mechanic.setElement(pipe);
        pipe.SetPlayer(mechanic);
        when(pipe.PickUpPump()).thenReturn(false);

        boolean result = mechanic.Pickuppump();

        assertFalse(result, "Pick up pump should return false.");
        assertEquals(0, mechanic.getPumpes(), "Pump count should remain 0 after failed pickup.");
        verify(pipe,times(1)).PickUpPump();
    }

    @Test
    public void testCutPipe() {
        mechanic.setElement(pipe);
        pipe.SetPlayer(mechanic);
        when(pipe.CutPipe()).thenReturn(true);

        boolean result = mechanic.CutPipe();

        assertTrue(result, "Cut pipe should return true.");
        verify(pipe,times(1)).CutPipe();
    }

    @Test
    public void testCutPipeFailure() {
        mechanic.setElement(pipe);
        pipe.SetPlayer(mechanic);
        when(pipe.CutPipe()).thenReturn(false);

        boolean result = mechanic.CutPipe();

        assertFalse(result, "Cut pipe should return false.");
        verify(pipe,times(1)).CutPipe();
    }



    

}
