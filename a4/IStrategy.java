package a4;

public interface IStrategy {
    abstract void apply(int time);
    abstract void connect(MovableGameObject o);
}
