import java.util.Iterator;

public class FibonacciSequence {
    //fields
    int NumberOfElements;
    //consructor
    public FibonacciSequence(int n) {NumberOfElements = n;}
    //methods
    public Iterator<?> iterator(){
        return null;
    }
    //internal class
    private class FibonacciIterator{
        //this is where most of the work happens
        // fields

        int numEmitted;
        //constructor
        public FibonacciIterator(){
            numEmitted = 0;
        }
        // methods
        public boolean hasNext(){
            if(numEmitted >= NumberOfElements) return false;

        }

    }
    
}
