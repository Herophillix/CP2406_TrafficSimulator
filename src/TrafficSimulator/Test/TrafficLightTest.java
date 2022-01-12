package TrafficSimulator.Test;

import TrafficSimulator.Road;
import TrafficSimulator.TrafficLight;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrafficLightTest {
    TrafficLight trafficLight = new TrafficLight(0, "TrafficSimulator.TrafficLight", true, 0, Road.DIRECTION.EAST);

    @Test
    public void TestGreenLight() { assertTrue(trafficLight.GetIsGreen()); }

    @Test
    public void TestStateChange() {
        assertTrue(trafficLight.GetIsGreen());
        for(int i = 0; i < TrafficLight.SWITCH_THRESHOLD; ++i)
        {
            trafficLight.AddTick();
        }
        assertFalse(trafficLight.GetIsGreen());
    }
}
