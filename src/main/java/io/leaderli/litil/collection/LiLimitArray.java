package io.leaderli.litil.collection;

/**
 * a container is a array that have invariable length, which not contain null or duplicate element
 *
 * @param <T> the type of elements
 */
public class LiLimitArray<T> {

    public final int size;
    private final Object[] data;

    public LiLimitArray(int size) {
        this.size = size;
        data = new Object[size];
    }


    /**
     * move all element to the next index, the tail element will removed
     * and the new element append to head
     *
     * @param t the new element
     */
    public void add(T t) {
        if (t == null || contains(t)) {
            return;
        }
        System.arraycopy(data, 0, data, 1, size - 1);
        data[0] = t;
    }

    /**
     * removed the find element and  move all element after the find element to the prev index
     *
     * @param t the element will be removed
     * @return true if container have element ,it will removed otherwise false
     */
    public boolean remove(T t) {
        if (t == null) {
            return false;
        }
        for (int i = 0; i < size; i++) {

            if (t.equals(data[i])) {
                fastRemove(i);
                return true;
            }
        }
        return false;
    }

    private void fastRemove(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(data, index + 1, data, index,
                    numMoved);
        data[size - 1] = null;
    }

    /**
     * @param t a element
     * @return true if container have element otherwise false
     * <p>
     * if element is null ,it's always return false
     */
    public boolean contains(T t) {
        if (t == null) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (t.equals(data[i])) {
                return true;
            }
        }
        return false;
    }


}
