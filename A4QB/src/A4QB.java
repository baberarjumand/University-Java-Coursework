import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class A4QB {

    public static void main(String[] args) {

        String inputFile = "a4b.txt";

        //ArrayList<Item> ordersList = processInputFile(inputFile);
        //printOrdersList(ordersList);

        OrdersCollection allOrders = new OrdersCollection(inputFile);
        ArrayList<Item> sortedOrders = allOrders.sort();
        //printOrdersList(allOrders.getOrdersList());
        printOrdersList(sortedOrders);

    }

    private static void printOrdersList(ArrayList<Item> ordersList) {

        int numberOfDonuts = 0;
        int numberOfCoffees = 0;
        int numberOfSandwiches = 0;
        int numberOfPops = 0;
        double totalPriceOfAllOrders = 0.0;

        for(Item e: ordersList) {
            System.out.println(e);
            if(e instanceof Coffee)
                numberOfCoffees += e.getQuantity();
            else if (e instanceof Donut)
                numberOfDonuts += e.getQuantity();
            else if (e instanceof Sandwich)
                numberOfSandwiches += e.getQuantity();
            else if (e instanceof  Pop)
                numberOfPops += e.getQuantity();
            totalPriceOfAllOrders += e.totalPrice();
            System.out.println();
        }

        System.out.println("Summary of All Orders");
        System.out.println("Number of Coffees: " + numberOfCoffees);
        System.out.println("Number of Donuts : " + numberOfDonuts);
        System.out.println("Number of Sandwiches: " + numberOfSandwiches);
        System.out.println("Number of Pops: " + numberOfPops);
        System.out.println("Price of all orders: $" + (Math.round(totalPriceOfAllOrders*100.0)/100.0));

    }

    private static ArrayList<Item> processInputFile(String inputFile) {

        ArrayList<Item> ordersList = new ArrayList<>();

        try(Scanner s = new Scanner(new FileReader(inputFile))){
            while (s.hasNext()){
                String inputLine[] = s.nextLine().split(",");
                if(inputLine.length==3 && inputLine[0].equals("Coffee")){
                    ordersList.add(new Coffee(Integer.parseInt(inputLine[1]), inputLine[2]));
                } else if (inputLine.length==4 && inputLine[0].equals("Donut")){
                    ordersList.add(new Donut(Integer.parseInt(inputLine[1]), Double.parseDouble(inputLine[2]), inputLine[3]));
                } else if (inputLine.length==5 && inputLine[0].equals("Sandwich")) {
                    ordersList.add(new Sandwich(Integer.parseInt(inputLine[1]), Double.parseDouble(inputLine[2]), inputLine[3], inputLine[4]));
                } else if (inputLine.length==4 && inputLine[0].equals("Pop")){
                    ordersList.add(new Pop(Integer.parseInt(inputLine[1]), inputLine[2], inputLine[3]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ordersList;

    }

}

class OrdersCollection {

    private ArrayList<Item> ordersList = new ArrayList<>();

    // constructor populates orderList by reading from inputFile
    public OrdersCollection (String inputFile) {
        try(Scanner s = new Scanner(new FileReader(inputFile))){
            while (s.hasNext()){
                String inputLine[] = s.nextLine().split(",");
                if(inputLine.length==3 && inputLine[0].equals("Coffee")){
                    ordersList.add(new Coffee(Integer.parseInt(inputLine[1]), inputLine[2]));
                } else if (inputLine.length==4 && inputLine[0].equals("Donut")){
                    ordersList.add(new Donut(Integer.parseInt(inputLine[1]), Double.parseDouble(inputLine[2]), inputLine[3]));
                } else if (inputLine.length==5 && inputLine[0].equals("Sandwich")) {
                    ordersList.add(new Sandwich(Integer.parseInt(inputLine[1]), Double.parseDouble(inputLine[2]), inputLine[3], inputLine[4]));
                } else if (inputLine.length==4 && inputLine[0].equals("Pop")){
                    ordersList.add(new Pop(Integer.parseInt(inputLine[1]), inputLine[2], inputLine[3]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Item> getOrdersList() {
        return ordersList;
    }

    public ArrayList<Item> sort() {

        ArrayList<Item> clonedList = (ArrayList<Item>) ordersList.clone();

        // using selection sort to sort in ascending order by total price of each order
        // ref: https://www.geeksforgeeks.org/selection-sort/
        for (int i=0; i<clonedList.size()-1; i++) {

            int minIndex = i;

            for (int j=i+1; j<clonedList.size(); j++) {
                if(clonedList.get(j).totalPrice() < clonedList.get(minIndex).totalPrice())
                    minIndex = j;
            }

            Item temp = clonedList.get(minIndex);
            clonedList.set(minIndex, clonedList.get(i));
            clonedList.set(i, temp);

        }

        return clonedList;

    }
}

abstract class Item {

    private int quantity;
    private double unitPrice;

    public Item(int quantity, double unitPrice){
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public double totalPrice() {
        return (quantity * unitPrice);
    }

    public int getQuantity() {
        return quantity;
    }

    public double roundToTwoDecimals (double number) {
        return Math.round(number*100.0)/100.0;
    }
}

abstract class Drink extends Item {

    private String size;

    public Drink(int quantity, double unitPrice){
        super(quantity, unitPrice);
    }

    protected void setSize(String size) {
        this.size = size;
    }

    protected String getSize() {
        return size;
    }
}

class Coffee extends Drink {


    public Coffee(int quantity, String size) {
        super(quantity, setUnitPrice(size));
        this.setSize(size);
    }

    private static double setUnitPrice(String size) {
        if(size.equals("small"))
            return 1.39;
        else if (size.equals("medium"))
            return 1.69;
        else if (size.equals("large"))
            return 1.99;
        else
            return 0.00;
    }

    @Override
    public String toString() {
        return "Item Type: Coffee\n" + "Quantity: " + this.getQuantity() + "\nSize: " + this.getSize() + "\nTotal Price: $" + roundToTwoDecimals(totalPrice());
    }
}

class Pop extends Drink {

    private String brand;


    public Pop(int quantity, String size, String brand) {
        super(quantity, setUnitPrice(size));
        this.setSize(size);
        this.brand = brand;
    }

    private static double setUnitPrice(String size) {
        if(size.equals("small"))
            return 1.79;
        else if (size.equals("medium"))
            return 2.09;
        else if (size.equals("large"))
            return 2.49;
        else
            return 0.00;
    }

    @Override
    public String toString() {
        return "Item Type: Pop\n" + "Quantity: " + this.getQuantity() + "\nSize: " + this.getSize() + "\nBrand: " + brand + "\nTotal Price: $" + roundToTwoDecimals(totalPrice());
    }
}

class Donut extends Item {

    private String flavor;

    public Donut(int quantity, double unitPrice, String flavor) {
        super(quantity, unitPrice);
        this.flavor = flavor;
    }

    @Override
    public double totalPrice() {
        if(getQuantity() < 6) {
            return (1.07 * super.totalPrice());
        }
        return super.totalPrice();
    }

    @Override
    public String toString() {
        return "Item Type: Donut\n" + "Quantity: " + this.getQuantity() + "\nFlavor: " + flavor + "\nTotal Price: $" + roundToTwoDecimals(totalPrice());
    }
}

class Sandwich extends Item {

    private String filling;
    private String bread;

    public Sandwich(int quantity, double unitPrice, String filling, String bread) {
        super(quantity, unitPrice);
        this.filling = filling;
        this.bread = bread;
    }

    @Override
    public double totalPrice() {
        return (1.07 * super.totalPrice());
    }

    @Override
    public String toString() {
        return "Item Type: Sandwich\n" + "Quantity: " + this.getQuantity() + "\nFilling: " + filling + "\nBread: " + bread + "\nTotal Price: $" + roundToTwoDecimals(totalPrice());
    }
}
