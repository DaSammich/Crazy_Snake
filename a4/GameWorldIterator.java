package a4;

import java.util.*;

public class GameWorldIterator {

    private ArrayList<GameObject> list;
    private int index;
    private boolean canRemove;

    public GameWorldIterator(ArrayList<GameObject> w) {
        list = w;
        index = -1;
        canRemove = false;
    }

    // returns if there is something in the next index
    public boolean hasNext() {
        // prev + 1 is what next will return
        return (index + 1 < list.size());
    }

    // returns the next object with some checking
    public GameObject next() {
        // if there's nothing there throw an exception
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        index++;
        canRemove = true;
        return list.get(index);
    }

    // returns the index of what next() will return
    public int nextIndex() {
        return index + 1;
    }
    
    public void remove() {
        // this shouldn't happen, but if it did java app was in 
        // illegal state
        if (!canRemove) {
            throw new IllegalStateException();
        }
        
        // remove the elemenet
        list.remove(index);
        
        // adjust to make sure index  points to correct element
        index--;
        // not sure if there is anything in there, so set to false
        canRemove = false;
    }
}
