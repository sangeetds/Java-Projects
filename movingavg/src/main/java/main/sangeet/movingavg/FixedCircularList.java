package main.sangeet.movingavg;

import java.util.ArrayList;
import java.util.List;

// Naming might not be perfect
public class FixedCircularList<N extends Number> {
    public final List<N> inputList;
    private final int maxSize;
    private int head; // Head index to keep constant size.
    private int tail; // Tail index to keep constant size

    public FixedCircularList(int windowSize) {
        this.maxSize = windowSize;
        this.inputList = new ArrayList<>(windowSize);
        this.head = 0;
        this.tail = 0;
    }

    // To get data at the tail.
    public N getInitialData() {
        N data = this.inputList.get(this.tail);
        this.tail = (this.tail + 1) % this.maxSize;

        return data;
    }

    public int getSize() {
        return inputList.size();
    }

    // Could have went with resizing the input list
    // by constantly adding and removing data.
    // Went with a constant size as adding and removing
    // data might result in resizing list.
    public void addData(N num) {
        if (this.inputList.size() < this.maxSize) {
            this.inputList.add(num);
        }
        else {
            this.inputList.set(this.head, num);
        }

        this.head = (this.head + 1) % this.maxSize;
    }
}
