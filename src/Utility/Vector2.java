package Utility;

public class Vector2 {
    public int x;
    public int y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 Add(Vector2 other)
    {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    public Vector2 Minus(Vector2 other)
    {
        return new Vector2(this.x - other.x, this.y - other.y);
    }

    public Vector2 Multiply(int scalar)
    {
        return new Vector2(this.x * scalar, this.y * scalar);
    }

    public Vector2 Divide(int scalar) {
        if(scalar == 0)
            scalar = 1;
        return new Vector2(this.x / scalar, this.y / scalar);
    }
}
