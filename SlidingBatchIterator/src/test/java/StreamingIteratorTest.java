import java.util.Iterator;

public class StreamingIteratorTest {
    public static void main(String[] args) {
        StreamingStore<Student> str = new StreamingStore<>();
        str.storeData(new Student(1, "A"));
        str.storeData(new Student(2, "B"));
        str.storeData(new Student(3, "C"));
        str.storeData(new Student(4, "D"));
        Student s1 = new Student(5, "E");

        Iterator<Batch<Student>> itr = str.getIterator(2);
//        str.storeData(s1);

        while (itr.hasNext()) {
            System.out.println(itr.next());
        }

        str.storeData(s1);

        Iterator<Batch<Student>> itr1 = str.getIterator(2);

        while (itr1.hasNext()) {
            System.out.println(itr1.next());
            System.out.println(itr1.next());
        }

        itr1.next();
    }
}
