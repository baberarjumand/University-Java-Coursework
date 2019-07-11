import java.util.*;

public class Activity7C {
    public static void main(String[] args) {
        StudentList students = new StudentList();

        students.add(new Student(8032, "Casper", 2.78));
        students.add(new Student(3044, "Sheena", 3.92));
        students.add(new Student(6170, "Yolanda", 4.26));
        students.add(new Student(1755, "Geordi", 3.58));

        students.printByNumber();

        students.printByName();

        System.out.println("\nEnd of processing.");
    }
}

class Student {
    private int number;
    private String name;
    private double gpa;

    public Student(int number, String name, double gpa) {
        this.number = number;
        this.name = name;
        this.gpa = gpa;
    }

    public int getNumber() {
        return number;
    }

    public double getGPA() {
        return gpa;
    }

    public boolean nameComesBefore(Student other) {
        return name.compareToIgnoreCase(other.name) < 0;
    }

    public String toString() {
        return number + " " + name + " (" + gpa + ")";
    }
}

class StudentList {
    private ArrayList<Student> list;

    public StudentList() {
        list = new ArrayList<Student>();
    }

    public void add(Student student) {
        boolean done = false;
        int pos;

        // find the insertion point (this is just a linear search)
        pos = list.size() - 1;
        while (pos >= 0 && !done) {
            if (student.getNumber() > list.get(pos).getNumber()) {
                done = true;
            } else {
                pos--;
            }
        }

        list.add(pos + 1, student);
    }

    public void printByNumber() {
        System.out.println("\nStudents ordered by number:");
        printStudents(list);
    }

    private void printStudents(ArrayList<Student> list) {
        System.out.println("\nList of all students:\n");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + 1 + ": " + list.get(i));
        }
    }

    public void printByName() {

        ArrayList<Student> listSortedByName = sortByName(list);
        printStudents(listSortedByName);

    }

    private ArrayList<Student> sortByName(ArrayList<Student> list) {

        ArrayList<Student> sortedList = (ArrayList<Student>) list.clone();

        // selection sort (https://www.geeksforgeeks.org/selection-sort/)
        int minIndex;

        for (int i=0; i < sortedList.size()-1; i++){
            minIndex = i;
            for(int j=i+1; j<sortedList.size(); j++){
                if(sortedList.get(j).nameComesBefore(sortedList.get(i)))
                    minIndex=j;
            }

            Student temp = sortedList.get(minIndex);
            sortedList.set(minIndex, sortedList.get(i));
            sortedList.set(i, temp);
        }

        return sortedList;

    }
}
