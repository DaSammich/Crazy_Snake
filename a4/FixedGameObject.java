package a4;

import java.awt.Color;

public class FixedGameObject extends GameObject {

    private int age = 0;

    public FixedGameObject() {
        super();
    }

    public FixedGameObject(float x, float y, Color c) {
        super(x, y, c);
    }

    public FixedGameObject(float x, float y, Color c, int a) {
        super(x, y, c);
        age = a;
    }

    
    // These two methods are not allowed according to class Heiarchy.
    @Override
    public void setX(float x) {
        throw new UnsupportedOperationException("This method is not allowed");
    }

    @Override
    public void setY(float y) {
        throw new UnsupportedOperationException("This method is not allowed");
    }

    public float getAge() {
        return age;
    }

    public void age() {
        age++;
    }

    @Override
    public String toString() {
        String str = new String();
        str += super.toString();
        str += " age=" + age + " ";
        return str;
    }
}
