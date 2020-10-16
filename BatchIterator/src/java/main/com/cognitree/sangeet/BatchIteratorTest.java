package main.com.cognitree.sangeet;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class BatchIteratorTest {
    public static void main(String[] args) {
        DataStore<Student> d = new DataStore<>();
        d.storeData(new Student(1, "A"));
        d.storeData(new Student(2, "B"));
        Student s = new Student(3, "C");
        Student s4 = new Student(4, "H");
        Student s5 = new Student(5, "I");
        Student s6 = new Student(6, "J");
        d.storeData(s);

        Iterator<Batch<Student>> dataIterator = d.getIterator(3);
        d.storeData(s4);
        d.storeData(s5);
        d.storeData(s6);

        while (dataIterator.hasNext()) {
            System.out.println(dataIterator.next());
        }

//        System.out.println(dataIterator.next());

        List<Student> arr = new ArrayList<>();

        Student s1 = new Student(1, "E");
        Student s2 = new Student(2, "F");
        Student s3 = new Student(3, "G");

        arr.add(s1);
        arr.add(s2);
        arr.add(s3);

        Iterator<Student> i = arr.iterator();
        arr.remove(s3);
        arr.add(s3);

        while (i.hasNext()) {
            System.out.println(i.next());
        }

        System.out.println(i.next());
    }
}
