import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

class PipeTest {
    Pipe pipe;

    @BeforeEach
    public void init(){
        pipe = new Pipe();
    }

    @Test
    void StorageGetSetIsGood(){
        pipe.SetStorage(10);
        assertEquals(10, pipe.GetStorage());
    }

    @Test
    void PipeNotDamagedAfterInicialization(){
        assertEquals(false, pipe.getDamaged());
    }

    @Test
    void PipeNotSlipperyAfterInicialization(){
        assertEquals(false, pipe.isSlippery());
    }

    @Test
    void PipeNotDamageableAfterInicialization(){
        assertEquals(false, pipe.isnotDamageable());
    }

    @Test
    void PipeNotStuckyAfterInicialization(){
        assertEquals(false, pipe.isstucky());
    }

    @Test
    void PipeRepairWhenThePipeIsNotDamaged(){
        assertEquals(false, pipe.Repair());
    }

    @Test
    void PipeRepairWhenThePipeIsDamaged(){
        pipe.Damage();
        assertEquals(true, pipe.Repair());
    }

    @Test
    void PipeDamaged(){
        pipe.Damage();
        assertEquals(true, pipe.getDamaged());
    }

    @Test
    void PipeNotDamageWhenItsBroken(){
        pipe.Damage();
        assertEquals(false, pipe.Damage());
    }

    @Test
    void PlayerCanMoveToThePipe(){
        Mechanic player = mock(Mechanic.class);
        assertEquals(true, pipe.Move(player));
    }

    @Test
    void PlayerCantMoveToThePipe(){
        Mechanic player1 = mock(Mechanic.class);
        Mechanic player2 = mock(Mechanic.class);
        pipe.AddPlayer(player1);
        assertEquals(false, pipe.Move(player2));
    }

    @Test
    void PipeSetStucky(){
        assertEquals(true, pipe.SetStucky());
    }

    @Test
    void PipeDontBeStuckyIfAlreadyIs(){
        pipe.SetStuckyValue(2);
        assertEquals(false, pipe.SetStucky());
    }

    @Test
    void PipeSetSlippery(){
        assertEquals(true, pipe.SetSlippery());
    }

    @Test
    void PipeDontBeSlipperyIfAlreadyIs(){
        pipe.SetSlipperyValue(2);
        assertEquals(false, pipe.SetSlippery());
    }

    @Test
    void PipeStuckyWhenPlayerMove(){
        Mechanic player = mock(Mechanic.class);
        pipe.SetStuckyValue(2);
        assertEquals(true, pipe.Move(player));
        assertEquals(false, pipe.isstucky());
        verify(player, times(1)).setCantMove(2);
    }

    @Test
    void PipeSlipperyWhenPlayerMove(){
        Mechanic player = mock(Mechanic.class);
        Pump pump1 = mock(Pump.class);
        Pump pump2 = mock(Pump.class);
        Pump pump3 = mock(Pump.class);
        Pump pump4 = mock(Pump.class);
        Pump pump5 = mock(Pump.class);
        when(player.getElement()).thenReturn(pipe);
        pipe.SetNeighbour(pump1);
        pipe.SetNeighbour(pump2);
        pipe.SetNeighbour(pump3);
        pipe.SetNeighbour(pump4);
        pipe.SetNeighbour(pump5);
        pipe.SetSlipperyValue(2);
        assertEquals(false, pipe.Move(player));
    }

    @Test
    void PipePathWhenHaveEnoughSpaceForWater(){
        Pump pump = mock(Pump.class);
        when(pump.GetStorage()).thenReturn(10);
        PipeSystem pipeSystem = mock(PipeSystem.class);

        pipe.Path(pump);

        assertEquals(3, pipe.GetStorage());
        assertEquals(0, pipeSystem.getS_water());

        verify(pump, times(1)).SetStorage(7);
    }

    @Test
    void PipePathWhenHaveEnoughSpaceButPipeIsDamaged(){
        Pump pump = mock(Pump.class);
        when(pump.GetStorage()).thenReturn(10);
        PipeSystem pipeSystem = mock(PipeSystem.class);

        pipe.Damage();
        pipe.Path(pump);

        assertEquals(0, pipe.GetStorage());
        assertEquals(3, pipeSystem.getS_water());

        verify(pump, times(1)).SetStorage(7);
        verify(pipeSystem, times(1)).Addswater(3);    
    }

    @Test
    void PipePathWhenHaveNeighbor(){
        Pump pump1 = mock(Pump.class);
        when(pump1.GetStorage()).thenReturn(10);
        Pump pump2 = mock(Pump.class);
        when(pump2.GetStorage()).thenReturn(10);
        pipe.SetNeighbour(pump1);
        pipe.SetNeighbour(pump2);

        pipe.Path(pump1);

        assertEquals(2, pipe.GetNeighbours().size());
        assertEquals(3, pipe.GetStorage());
    }

    @Test
    void ChangePipeWrong(){
        Pump pump1 = mock(Pump.class);
        Pump pump2 = mock(Pump.class);
        Pump pump3 = mock(Pump.class);
        Pump pump4 = mock(Pump.class);

        pipe.SetNeighbour(pump1);
        pipe.SetNeighbour(pump2);

        assertEquals(false, pipe.ChangePipe(pump3, pump4));
    }

    @Test
    void ChangePipeDontHaveThatPump(){
        Pump pump1 = mock(Pump.class);
        Pump pump2 = mock(Pump.class);
        Pump pump3 = mock(Pump.class);

        pipe.SetNeighbour(pump1);
        pipe.SetNeighbour(pump2);

        assertEquals(false, pipe.ChangePipe(pump1, pump3));
    }

    @Test
    void ChangePipeInput(){
        Pump pump1 = mock(Pump.class);
        Pump pump2 = mock(Pump.class);
        Pump pump3 = mock(Pump.class);
        PipeSystem pipeSystem = mock(PipeSystem.class);
        
        ArrayList<Pump> pumps = new ArrayList<Pump>();
        pumps.add(pump1);
        pumps.add(pump2);
        pumps.add(pump3);
        pipeSystem.setPumpes(pumps);

        pipe.SetNeighbour(pump1);
        pipe.SetNeighbour(pump2);

        //assertEquals(false, pipe.ChangePipe(pump1, pump3));
    }

    @Test
    void TimerNotifyWorking(){
        pipe.SetSlipperyValue(1);
        pipe.SetNotDamageable(1);
        pipe.SetStuckyValue(1);
        pipe.TimerNotify();
        assertEquals(false, pipe.isSlippery());
        assertEquals(false, pipe.isnotDamageable());
        assertEquals(false, pipe.isstucky());
    }

    @Test
    void InfoWorking(){
        pipe.Info();
        assertTrue(true);
    }

    @Test
    void CutPipeWhenMechanicDontHavePump(){
        Mechanic mechanic = mock(Mechanic.class);
        when(mechanic.getPumpes()).thenReturn(0);
        pipe.AddPlayer(mechanic);
        assertEquals(false, pipe.CutPipe());
    }

    @Test
    void CutPipeWhenHavePump(){
        Mechanic mechanic = mock(Mechanic.class);
        Pump pump1 = mock(Pump.class);
        Pump pump2 = mock(Pump.class);
        Pipe pipe2 = mock(Pipe.class);

        when(mechanic.getPumpes()).thenReturn(1);
        ArrayList<Element> pump2Neighbours = new ArrayList<Element>();
        pump2Neighbours.add(pipe);
        pump2Neighbours.add(pipe2);
        when(pump2.GetNeighbours()).thenReturn(pump2Neighbours);

        pipe.AddPlayer(mechanic);
        pipe.SetNeighbour(pump1);
        pipe.SetNeighbour(pump2);
        assertEquals(true, pipe.CutPipe());
    }
}
