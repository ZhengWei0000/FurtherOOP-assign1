package lists;

import java.util.Iterator;


// a IntNode for each element in LinkedList
class GenericNode<T> {
    T value;
    GenericNode<T> next;

    public GenericNode(T value) {
        this.value = value;
        this.next = null;
    }
}


public class GenericLinkedList<T> implements GenericList<T> {
    GenericNode<T> head;
    private GenericNode<T> tail;
    int len;

    public GenericLinkedList() {
        head = null;
        tail = null;
        len = 0;
    }

    public boolean contains(T value) {
        // todo: implement this properly
        GenericNode<T> current = head;
        while (current != null) {
            if (current.value.equals(value)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void append(T value) {
        // todo: implement an efficient append method
        GenericNode<T> newNode = new GenericNode<>(value);
        if (tail == null) {  // 链表为空时
            head = newNode;
            tail = newNode;
        } else {  // 链表不为空时
            tail.next = newNode;
            tail = newNode;
        }
        len++;
    }

    public int length() {
        return len;
    }

    @Override
    public Iterator<T> iterator() {
        return new GenericLinkedListIterator<T>(this);
    }

    private static class GenericLinkedListIterator<T> implements Iterator<T> {
        private GenericNode<T> current;

        public GenericLinkedListIterator(GenericLinkedList<T> list) {
            current = list.head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T value = current.value;
            current = current.next;
            return value;
        }
    }

    public static void main(String[] args) {
        GenericLinkedList<Integer> list = new GenericLinkedList<>();
        list.append(1);
        list.append(2);
        list.append(3);
        for (Integer i : list) {
            System.out.println(i);
        }
    }
}
