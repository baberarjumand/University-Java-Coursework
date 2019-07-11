public class Activity4CAlt {
    public static void main(String[] args) {
        Portfolio jim, helen;
        Property property;

        jim = new Portfolio("Jim");
        jim.add(new Property(16, 8, 160));
        jim.add(new Property(24, 17, 130));
        jim.add(new Property(129, 180, 35));

        helen = new Portfolio("Helen");
        helen.add(new Property(9, 13, 120));
        helen.add(new Property(15, 15, 210));
        helen.add(new Property(9, 13, 120));

        jim.print();
        System.out.println();
        helen.print();
        System.out.println();

//        jim.subdivide(0, true);
//        jim.subdivide(2, false);
//        helen.subdivide(0, false);
//
//        property = jim.transfer(1, helen);
//        System.out.println(property + " transferred from Jim to Helen for $" + property.value());
//        property = helen.transfer(3, jim);
//        System.out.println(property + " transferred from Helen to Jim for $" + property.value());
//        System.out.println();
//
//        jim.print();
//        System.out.println();
//        helen.print();
//        System.out.println();

        swap(jim, 0, helen, 2);

        jim.print();
        System.out.println();
        helen.print();
        System.out.println();

        swap(helen, 1, jim, 1);

        jim.print();
        System.out.println();
        helen.print();
        System.out.println();

        System.out.println("\nEnd of processing.");
    }

    private static void swap(Portfolio personA, int a, Portfolio personB, int b) {

        // get property lists for both persons
        Property[] propertyListA = personA.getList();
        Property[] propertyListB = personB.getList();

        System.out.println("Swapping\n" + propertyListA[a] + " Value: " + propertyListA[a].value() + "\nwith\n" + propertyListB[b] + " Value: " + propertyListB[b].value() );
        System.out.println("Swapping properties' value difference: " + (propertyListA[a].value()-propertyListB[b].value()) + "\n");

        // swap properties based on provided integer values
        Property tempProperty = propertyListA[a];
        propertyListA[a] = propertyListB[b];
        propertyListB[b] = tempProperty;

        // override property lists with swapped values
        personA.setList(propertyListA);
        personB.setList(propertyListB);

    }


}

class Property {
    private int length, width; // both are in metres
    private int valuePerSqM;   // value in $ per square metre

    public Property(int length, int width, int valuePerSqM) {
        this.length = length;
        this.width = width;
        this.valuePerSqM = valuePerSqM;
    }

    public int value() {
        return length * width * valuePerSqM;
    }

    public Property subdivide(boolean lengthwise) {
        Property subdivision;

        if (lengthwise) {
            subdivision = new Property(length / 2, width, valuePerSqM);
            length = (length + 1) / 2;
        } else {
            subdivision = new Property(length, width / 2, valuePerSqM);
            width = (width + 1) / 2;
        }

        return subdivision;
    }

    public String toString() {
        return "Property: " + length + "m long by " + width + "m wide ($" +
                valuePerSqM + " per square metre)";
    }
}

class Portfolio {
    private Property[] list;
    int size;
    private String owner;

    public Portfolio(String owner) {
        // assume that a typical portfolio has at most 3 properties
        setList(new Property[3]);
        // but it has none initially
        size = 0;
        this.owner = owner;
    }

    public void add(Property p) {
        if (size >= getList().length) {
            // make the new list twice as big as the old
            Property[] newList = new Property[getList().length * 2];
            // copy the old contents to the new list
            System.arraycopy(getList(), 0, newList, 0, getList().length);
            // replace the old list with the new one
            setList(newList);
        }
        getList()[size] = p;
        size++;
    }

    public void print() {
        int totalValue;

        System.out.println(owner + "'s portfolio:");

        totalValue = 0;
        for (int i = 0; i < size; i++) {
            System.out.println(" " + getList()[i]);
            totalValue += getList()[i].value();
        }

        System.out.println("Total value: $" + totalValue);
    }

    public void subdivide(int position, boolean lengthwise) {
        Property toSubdivide;
        Property subdivision;

        toSubdivide = getList()[position];
        subdivision = toSubdivide.subdivide(lengthwise);

        add(subdivision);
    }

    public Property transfer(int position, Portfolio buyer) {
        Property toTransfer;

        toTransfer = getList()[position];
        remove(position);
        buyer.add(toTransfer);

        return toTransfer;
    }

    private void remove(int position) {
        for (int i = position + 1; i < size; i++) {
            getList()[i - 1] = getList()[i];
        }
        size--;
    }

    public Property[] getList() {
        return list;
    }

    public void setList(Property[] list) {
        this.list = list;
    }
}
