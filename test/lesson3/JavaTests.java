package lesson3;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JavaTests {
    private void testAdd() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        assertEquals(0, tree.size());
        assertFalse(tree.contains(5));
        tree.add(10);
        tree.add(5);
        tree.add(7);
        tree.add(10);
        assertEquals(3, tree.size());
        assertTrue(tree.contains(5));
        tree.add(3);
        tree.add(1);
        tree.add(3);
        tree.add(4);
        assertEquals(6, tree.size());
        assertFalse(tree.contains(8));
        tree.add(8);
        tree.add(15);
        tree.add(15);
        tree.add(20);
        assertEquals(9, tree.size());
        assertTrue(tree.contains(8));
        assertTrue(tree.checkInvariant());
        assertTrue(tree.contains(8));
    }

    @Test
    @Tag("Example")
    void testAddJava() {
        testAdd();
    }

    private void testRemove() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        assertFalse(tree.remove(10));
        for (int i = 1; i < 11; i++) {
            tree.add(i);
        }
        assertEquals(10, tree.size());
        tree.remove(10);
        assertFalse(tree.remove(45));
        assertEquals(9, tree.size());
        assertFalse(tree.contains(10));
    }

    @Test
    @Tag("Normal")
    void testRemoveJava() {
        testRemove();
    }

    private void testIterator() {
        BinaryTree<Integer> binarySet = new BinaryTree<>();
        TreeSet<Integer> treeSet = new TreeSet<>();
        assertFalse(binarySet.iterator().hasNext());

        for (int i = 0; i < 20; i++) {
            int num = new Random().nextInt(100);
            binarySet.add(num);
            treeSet.add(num);
        }

        Iterator treeIterator = treeSet.iterator();
        Iterator binaryIterator = binarySet.iterator();
        while (treeIterator.hasNext()) {
            assertEquals(treeIterator.next(), binaryIterator.next());
        }
        Object removeElement = new Object();
        Iterator treeIterator2 = treeSet.iterator();
        Iterator binaryIterator2 = binarySet.iterator();
        for (int i = 0; i < new Random().nextInt(binarySet.size()); i++) {
            removeElement = treeIterator2.next();
        }

        treeSet.remove(removeElement);

        while (binaryIterator2.hasNext()) {
            Object element = binaryIterator2.next();
            if (element == removeElement) {
                System.out.println(element);
                binaryIterator2.remove();
            }
        }
        assertEquals(treeSet, binarySet);
        assertEquals(treeSet.size(), binarySet.size());
    }

    @Test
    @Tag("Normal")
    void testIteratorJava() {
        testIterator();
    }
}
