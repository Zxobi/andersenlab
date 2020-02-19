import com.andersenlab.linkedList.LinkedList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LinkedListTest {

    private LinkedList<Integer> linkedList;

    @Before
    public void setupList() {
        linkedList = new LinkedList<>();
    }

    @Test
    public void testAddSingle() {
        linkedList.add(1);

        Assert.assertEquals((int)linkedList.get(0), 1);
        Assert.assertEquals(linkedList.size(), 1);
    }

    @Test
    public void testAddMultiple() {
        linkedList.add(1);
        linkedList.add(2);

        Assert.assertEquals((int)linkedList.get(1), 2);
        Assert.assertEquals(linkedList.size(), 2);
    }

    @Test
    public void testRemoveEmpty() {
        Assert.assertFalse(linkedList.remove(1));
        Assert.assertEquals(linkedList.size(), 0);
    }

    @Test
    public void testRemoveSingle() {
        fillList(1);
        Assert.assertTrue(linkedList.remove(0));
        Assert.assertEquals(linkedList.size(), 0);
    }

    @Test
    public void testRemoveMultiple() {
        fillList(2);
        Assert.assertTrue(linkedList.remove(1));
        Assert.assertEquals(linkedList.size(), 1);
    }

    @Test
    public void testGetFirst() {
        fillList(5);
        Assert.assertEquals((int)linkedList.get(0), 0);
    }

    @Test
    public void testGetMiddle() {
        fillList(5);
        Assert.assertEquals((int)linkedList.get(2), 2);
    }

    @Test
    public void testGetLast() {
        fillList(5);
        Assert.assertEquals((int)linkedList.get(4), 4);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetIndexOutOfBound() {
        fillList(5);
        linkedList.get(5);
    }

    @Test()
    public void testClearSingle() {
        fillList(1);
        linkedList.clear();
        Assert.assertEquals(linkedList.size(), 0);
    }

    @Test
    public void testClearMultiple() {
        fillList(5);
        linkedList.clear();
        Assert.assertEquals(linkedList.size(), 0);
    }

    @Test
    public void testReverseSingle() {
        linkedList.add(1);
        linkedList.reverse();
        Assert.assertEquals((int)linkedList.get(0), 1);
    }

    @Test
    public void testReverseMultiple() {
        fillList(10);
        linkedList.reverse();
        Assert.assertArrayEquals(
                new int[] {9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
                toArray(linkedList)
        );
    }

    private void fillList(int count) {
        for (int i = 0; i < count; i++) {
            linkedList.add(i);
        }
    }

    private int[] toArray(LinkedList<Integer> linkedList) {
        int[] arr = new int[linkedList.size()];
        for (int i = 0; i < linkedList.size(); i++) {
            arr[i] = linkedList.get(i);
        }

        return arr;
    }
}
