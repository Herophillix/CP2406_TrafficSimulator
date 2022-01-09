package TrafficSimulator;

public class MathUtility {
    public static int Clamp(int value, int min, int max)
    {
        return Math.max(min, Math.min(value, max));
    }

    public static float Clamp(float value, float min, float max)
    {
        return Math.max(min, Math.min(value, max));
    }

    public static double Clamp(double value, double min, double max)
    {
        return Math.max(min, Math.min(value, max));
    }
}
