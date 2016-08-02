package a4;

import java.awt.Color;
import java.text.DecimalFormat;

public abstract class GameObject {

    private float x;
    private float y;
    private Color c;
    DecimalFormat df = new DecimalFormat("###.##");

    public GameObject() {
        x = 0;
        y = 0;
    }

    public GameObject(float x, float y, Color c) {
        this.x = x;
        this.y = y;
        this.c = c;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    
    public void setX(float x) {
        this.x = x;
    }
    
    public void setY(float y) {
        this.y = y;
    }

    public Color getColor() {
        return c;
    }

    @Override
    public String toString() {
        String str = new String();
        str += "loc=" + "(" + df.format(this.x) + "," + df.format(this.y) + 
                ")" + " " + this.c;
        return str;
    }

}
