import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String inputFile = "a3a.txt";

        ArrayList<Item> shoppingList = new ArrayList<>();
        ArrayList<Item> purchaseList = new ArrayList<>();

        processInputTextFile(inputFile, shoppingList, purchaseList);

    }

    private static void processInputTextFile(String inputFile, ArrayList<Item> shoppingList, ArrayList<Item> purchaseList) {

        try (Scanner s = new Scanner(new FileReader("a3a.txt"))) {
            while (s.hasNext()) {

                // write current line of text from input text file into input[] array using ',' as delimiter
                String input[] = s.nextLine().split(",");

                // if input[] length is 1, then list command is received and lists are to be printed
                if (input.length == 1 && input[0].equals("list")) {
                    printLists(shoppingList, purchaseList);
                }
                // if input[] length is greater than 1, then relevant processing needs to be done to the lists
                else {
                    // if input[0] is 'add', then add item to shopping list
                    if (input[0].equals("add")) {
                        addItemToShoppingList(input, shoppingList);
                    }

                    // if input[0] is 'buy', then remove it from shopping list (if it exists there) and add to purchase list
                    else if (input[0].equals("buy")) {
                        addItemToPurchaseListAndRemoveFromShoppingList(input, shoppingList, purchaseList);
                    }

                    // this case is executed when invalid input is found
                    else {
                        System.out.println("Invalid input");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void addItemToPurchaseListAndRemoveFromShoppingList(String[] input, ArrayList<Item> shoppingList, ArrayList<Item> purchaseList) {

        String itemName = input[2];
        int itemQuantity = Integer.parseInt(input[1]);
        boolean itemExistsInShoppingList = false;
        int itemExistsInShoppingListIndex = -1;
        boolean itemExistsInPurchaseList = false;
        int itemExistsInPurchaseListIndex = -1;

        // if item exists in shopping list, adjust it's quantity accordingly
        for (int i=0; i<shoppingList.size(); i++) {
            if (shoppingList.get(i).getName().equals(itemName)){
                itemExistsInShoppingList = true;
                itemExistsInShoppingListIndex = i;
            }
        }
        if (itemExistsInShoppingList) {
            shoppingList.get(itemExistsInShoppingListIndex).setQuantity(shoppingList.get(itemExistsInShoppingListIndex).getQuantity() - itemQuantity);
            // if new quantity of current item is zero, then remove it from shopping list
            if(shoppingList.get(itemExistsInShoppingListIndex).getQuantity() == 0) {
                shoppingList.remove(itemExistsInShoppingListIndex);
            }
        }

        // if purchase list is empty, add new item to it
        if (purchaseList.size() == 0) {
            purchaseList.add(new Item(itemName, itemQuantity));
        }

        // else check if purchase list has current item. If so, add to it's quantity
        else {
            for(int i=0; i<purchaseList.size(); i++){
                if(purchaseList.get(i).getName().equals(itemName)){
                    itemExistsInPurchaseList = true;
                    itemExistsInPurchaseListIndex = i;
                }
            }
            if (itemExistsInPurchaseList) {
                purchaseList.get(itemExistsInPurchaseListIndex).setQuantity(purchaseList.get(itemExistsInPurchaseListIndex).getQuantity() + itemQuantity);
            } else {
                purchaseList.add(new Item(itemName, itemQuantity));
            }
        }
    }

    private static void addItemToShoppingList(String[] input, ArrayList<Item> shoppingList) {

        String itemName = input[2];
        int itemQuantity = Integer.parseInt(input[1]);
        boolean itemExists = false;
        int itemExistsIndex = 0;

        // if shopping list is empty, add new item to list
        if (shoppingList.size() == 0) {
            shoppingList.add(new Item(itemName, itemQuantity));
        }

        // check if item exists already in list. If not, then add new item to list
        else {
            for(int i=0; i<shoppingList.size(); i++) {
                if (shoppingList.get(i).getName().equals(itemName)){
                    itemExists = true;
                    itemExistsIndex = i;
                }
            }
            if ( itemExists ) {
                shoppingList.get(itemExistsIndex).setQuantity(shoppingList.get(itemExistsIndex).getQuantity() + itemQuantity);
            } else {
                shoppingList.add(new Item(itemName, itemQuantity));
            }
        }

    }

    private static void printLists(ArrayList<Item> shoppingList, ArrayList<Item> purchaseList) {

        System.out.println("==============");
        // print out shopping list
        System.out.println("Shopping List:");
        if (shoppingList.size() == 0) {
            System.out.println("No items currently on shopping list");
        } else {
            for(int i=0; i<shoppingList.size(); i++){
                if(shoppingList.get(i).getQuantity()>0)
                    System.out.println(shoppingList.get(i).getQuantity() + " - " + shoppingList.get(i).getName());
            }
        }
        System.out.println("==============");
        // print out purchase list
        System.out.println("Purchase List:");
        if (purchaseList.size() == 0) {
            System.out.println("No items currently on purchase list");
        } else {
            for(int i=0; i<purchaseList.size(); i++){
                System.out.println(purchaseList.get(i).getQuantity() + " - " + purchaseList.get(i).getName());
            }
        }
        System.out.println();
    }
}

class Item {
    private String name;
    private int quantity;

    public Item(String name, int quantity) {
        this.setName(name);
        this.setQuantity(quantity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if ( quantity<0 )
            this.quantity = 0;
        else
            this.quantity = quantity;
    }
}

