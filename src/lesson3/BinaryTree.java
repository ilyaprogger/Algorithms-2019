package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        //  Node<T> parent = null;

        Node(T value) {
            this.value = value;
        }

    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    public int height() {
        return height(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    //@Override
    //public boolean equals(Object obj) {
    //    if (this == obj)
    //        return true;
    //    if (obj == null)
    //        return false;
    //    return getClass() == obj.getClass();
    //}

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    //Лучший случай быстродействия O(log(n)) худший О(height()).
    @Override
    public boolean remove(Object o) {
        T t = (T) o;
        if (find(t) == null) return false;
        size--;
        root = remove(root, new Node<>(t));
        return true;
    }

    private Node<T> remove(Node<T> start, Node<T> delete) {
        if (start == null) return null;
        int comparator = delete.value.compareTo(start.value);
        if (comparator < 0)
            start.left = remove(start.left, delete);
        else if (comparator > 0)
            start.right = remove(start.right, delete);
        else if (start.left != null && start.right != null) {
            Node<T> node = new Node<>(min(start.right));
            node.left = start.left;
            node.right = start.right;
            start = node;
            start.right = remove(start.right, start);
        } else {
            if (start.left != null) return start.left;
            else return start.right;
        }
        return start;
    }

    private T min(Node<T> node) {
        Node<T> current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    private T max(Node<T> node) {
        Node<T> current = node;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Stack<Node> stack = new Stack<>();
        private Node<T> node;

        private BinaryTreeIterator(Node<T> argRoot) {
            node = argRoot;
        }

        /**
         * Проверка наличия следующего элемента
         * Средняя
         */
        @Override
        public boolean hasNext() {
            return (!stack.isEmpty() || node != null);
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        @Override
        public T next() {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            node = stack.pop();
            Node node = this.node;
            this.node = this.node.right;

            return (T)node.value;
        }
        /**
         * Удаление следующего элемента
         * Сложная
         */
        @Override
        public void remove() {

        }

    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator(root);
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        // TODO
        throw new NotImplementedError();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
