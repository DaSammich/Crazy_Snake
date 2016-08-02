package a4;

public interface IObservable {

    abstract void addObserver(IObserver o);

    abstract void notifyObservers();
}
