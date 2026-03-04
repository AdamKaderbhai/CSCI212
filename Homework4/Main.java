public class Main{
    public static void main(String[] args) {
    LinkedList<Integer> Stack = new LinkedList<>();
    Stack.push(1); Stack.push(2); Stack.push(3);
    System.out.println(Stack.pop()); 
    System.out.println(Stack.top()); 

    LinkedList<Integer> queue = new LinkedList<>();
    queue.enqueue(1); queue.enqueue(2); queue.enqueue(3);
    System.out.println(queue.dequeue()); 
    }

}
