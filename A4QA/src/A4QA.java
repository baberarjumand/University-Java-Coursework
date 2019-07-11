import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class A4QA {

    public static void main(String[] args) {

        String inputFile = "a4a.txt";
        ArrayList<Item> ordersList = processInputFile(inputFile);

        printOrdersList(ordersList);

    }

    private static void printOrdersList(ArrayList<Item> ordersList) {

        int numberOfDonuts = 0;
        int numberOfCoffees = 0;
        double totalPriceOfAllOrders = 0.0;

        for(Item e: ordersList) {
            System.out.println(e);
            if(e instanceof Coffee)
                numberOfCoffees += e.getQuantity();
            else if (e instanceof Donut)
                numberOfDonuts += e.getQuantity();
            totalPriceOfAllOrders += e.totalPrice();
        }

        System.out.println("\nSummary of All Orders");
        System.out.println("Number of Coffees: " + numberOfCoffees);
        System.out.println("Number of Donuts : " + numberOfDonuts);
        System.out.println("Price of all orders: $" + totalPriceOfAllOrders);

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
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ordersList;

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
}

class Coffee extends Item {

    private String size;

    public Coffee(int quantity, String size) {
        super(quantity, setUnitPrice(size));
        this.size = size;
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
        return "Item Type:Coffee " + "Quantity:" + this.getQuantity() + " Size:" + this.size + " Total Price: $" + totalPrice();
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
        return "Item Type:Donut " + "Quantity:" + this.getQuantity() + " Flavor:" + flavor + " Total Price: $" + totalPrice();
    }
}
