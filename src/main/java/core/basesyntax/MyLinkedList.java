package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        validateIndex(index, true);
        Node<T> newNode = new Node<>(value);
        if (index == size) {
            add(value);
        } else if (index == 0) {
            newNode.setNext(head);
            if (head != null) {
                head.setPrev(newNode);
            }
            head = newNode;
            if (tail == null) {
                tail = newNode;
            }
            size++;
        } else {
            Node<T> current = findNodeByIndex(index);
            Node<T> prev = current.getPrev();
            newNode.setNext(current);
            newNode.setPrev(prev);
            if (prev != null) {
                prev.setNext(newNode);
            } else {
                head = newNode;
            }
            current.setPrev(newNode);
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index, false);
        return findNodeByIndex(index).getValue();
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index, false);
        Node<T> node = findNodeByIndex(index);
        T oldValue = node.getValue();
        node.setValue(value);
        return oldValue;
    }

    @Override
    public T remove(int index) {
        validateIndex(index, false);
        Node<T> target = findNodeByIndex(index);
        T value = target.getValue();
        unlink(target);
        size--;
        return value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (current.getValue() == null ? object == null
                    : current.getValue().equals(object)) {
                unlink(current);
                size--;
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void validateIndex(int index, boolean isAddOperation) {
        if (index < 0 || (!isAddOperation && index >= size)
                || (isAddOperation && index > size)) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    private Node<T> findNodeByIndex(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.getPrev();
            }
        }
        return current;
    }

    private void unlink(Node<T> node) {
        Node<T> next = node.getNext();
        Node<T> prev = node.getPrev();

        if (prev != null) {
            prev.setNext(next);
        } else {
            head = next;
        }

        if (next != null) {
            next.setPrev(prev);
        } else {
            tail = prev;
        }

        node.setValue(null);
        node.setPrev(null);
        node.setNext(null);
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;
        private Node<T> tail;
        private int size;

        public Node(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }
}
