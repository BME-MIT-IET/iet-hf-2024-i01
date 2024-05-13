import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTest {
    @Test
    public void Test(){
        Pump p = new Pump();
        p.Repair();
        assertEquals(p.getBroken(), false);
    }
}
