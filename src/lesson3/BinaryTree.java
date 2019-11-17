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


    /**
     * Удаление элемента в дереве
     * Средняя
     */
    //Лучший случай быстродействия O(log(n)),где n-кол-во элементов в дереве худший О(height()).
    //Память О(1)
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
        if (node.left == null)
            return node.value;
        return min(node.left);
    }

    private T max(Node<T> node) {
        if (node.right == null)
            return node.value;
        return min(node.right);
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

        private Deque<Node<T>> deque = new LinkedList<>();
        private Node<T> node;

        private BinaryTreeIterator() {
            if (root == null) return;
            treeIterator(root);
            node = root;
        }

        private void treeIterator(Node<T> currentNode) {
            if (currentNode.left != null)
                treeIterator(currentNode.left);
            deque.add(currentNode);
            if (currentNode.right != null)
                treeIterator(currentNode.right);
        }

        /**
         * Проверка наличия следующего элемента
         * Средняя
         */
        //Быстродействие O(1)
        // Память О(1).
        @Override
        public boolean hasNext() {
            return deque.peek() != null;
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        //Быстродействие O(1)
        // Память О(1).
        @Override
        public T next() {
            node = deque.remove();
            return node.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        //Лучший случай быстродействия O(log(n)),где n-кол-во элементов в дереве худший О(height()).
        //Память О(1)
        @Override
        public void remove() {
            BinaryTree.this.remove(node.value);
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
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
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тож  можно решить и их написать
     * Очень сложная
     */
    //Быстродействие O(n),где n - кол-во элементов в дереве.
    //Память О(1)
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return new SubTailHeadTree(this, fromElement, toElement);
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    //Быстродействие O(n),где n - кол-во элементов в дереве.
    //Память О(1)
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        return new SubTailHeadTree(this, null, toElement);
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    //Быстродействие O(n),где n - кол-во элементов в дереве.
    //Память О(1)
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return new SubTailHeadTree(this, fromElement, null);
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

    //Вспомогательный класс для подмножеств
    public class SubTailHeadTree extends BinaryTree<T> {
        private BinaryTree<T> binaryTree;
        private final T fromElement;
        private final T toElement;

        SubTailHeadTree(BinaryTree<T> binaryTree, T fromElement, T toElement) {
            this.binaryTree = binaryTree;
            this.fromElement = fromElement;
            this.toElement = toElement;
        }

        @Override
        public boolean add(T t) {
            if (!sub(t)) throw new IllegalArgumentException();
            return binaryTree.add(t);
        }

        @Override
        public boolean remove(Object o) {
            if (!sub(o)) return false;
            return binaryTree.remove(o);
        }

        @Override
        public int size() {
            return (int) binaryTree.stream().filter(this::sub).count();
        }

        @Override
        public boolean contains(Object o) {
            if (!sub(o)) return false;
            return binaryTree.contains(o);
        }

        public boolean sub(Object o) {
            T t = (T) o;
            int from = 0;
            if (fromElement != null) from = t.compareTo(fromElement);
            int to = -1;
            if (toElement != null) to = t.compareTo(toElement);
            return from >= 0 && to < 0;
        }
    }
}
