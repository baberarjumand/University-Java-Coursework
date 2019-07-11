//public static String[] getBookings() {
//        return new String[]{"38",
// 2       "2", "Gurganus, Erik", "Gurganus, Fernando",
// 3       "1", "Cahn, Lance",
// 4       "1", "Burrough, Jamie",
// 7       "3", "Riney, Christian", "Marceau, Hillary", "Marceau, Julio",
// 10       "3", "Gariepy, Noemi", "Gariepy, Louisa", "Gariepy, Nelson",
// 12       "2", "Mazzoni, Max", "Fiorita, Tyrone",
// 15       "3", "Ehle, Clinton", "Minnifield, Clinton", "Blinn, Jamie",
// 17       "2", "Sokolowski, Kurt", "Sokolowski, Sofia",
// 19       "2", "Secord, Hugh", "McVeigh, Karina",
// 20       "1", "McMonagle, Christian",
// 21       "1", "Canchola, Clayton",
// 23       "2", "Duer, Julio", "Danos, Ted",
// 26       "3", "Regal, Christian", "Mun, Allan", "Mun, Lakisha",
// 28       "2", "Noblitt, Karina", "Tussey, Clayton",
// 29       "1", "Seckman, Jamie",
// 31       "2", "Folmar, Edwina", "Lokey, Clayton",
// 33       "2", "Pippen, Javier", "Saba, Earlene",
// 37       "4", "Tippit, Lorrie", "Tippit, Harriett", "Tippit, Clare", "Tippit, Lance",
// 40       "3", "Mazurek, Mallory", "Mazurek, Stefan", "Mazurek, Ihor",
// 42       "2", "Saini, Amie", "Peavler, Darcy"
//        };
//        }

import java.util.ArrayList;
import java.util.Random;

public class A1Q1 {

    public static void main(String[] args) {

        // import passengers information, first element tells number of total passengers to book
        String[] bookings1 = {
                "8",
                "2", "Nobbly, Greg", "Nobbly, Jo-Anne",
                "1", "Lee, Sook",
                "3", "Lukas, Stephie", "Lukas, Cambridge", "Lukas, Ogden"
        };
        System.out.println("Seating Plan for Flight 1:");
        processAndPrintCurrentBooking(bookings1);

        String[] bookings2 = {
                "12",
                "2", "Man, Super", "Man, Bat",
                "1", "Widow, Black",
                "3", "Man, Iron", "Man, Spider", "Man, Hulk",
                "4", "Man, Ant", "Man, Aqua", "Man, Shakti", "Boy, Spider",
                "2", "Woman, Cat", "Woman, Wonder"
        };
        System.out.println("\nSeating Plan for Flight 2:");
        processAndPrintCurrentBooking(bookings2);

        String[] bookings3 = Bookings.getBookings();
        System.out.println("\nSeating Plan for Flight 3:");
        processAndPrintCurrentBooking(bookings3);

    }

    private static void processAndPrintCurrentBooking(String[] bookings) {
        // get number of passengers to be booked from first element
        int numberOfSeats = Integer.parseInt(bookings[0]);

        // initialize and set all seats elements to empty
        String[] seats = initializeSeats(numberOfSeats);

        // linearly book passengers from bookings[] to seats[] (in sequence)
        //LinearlyBookPassengers(bookings, seats);

        // randomly book passengers from bookings[] to seats[] (random seat assignment)
        RandomlyBookPassengers(bookings, seats);

        // print out seats[] array
        printSeatsAfterBooking(seats);
    }

    public static void RandomlyBookPassengers(String[] bookings, String[] seats) {

        int currentBookingsIndex = 1;
        int sizeOfCurrentGroup = Integer.parseInt(bookings[currentBookingsIndex]);
        currentBookingsIndex++;

        while( (sizeOfCurrentGroup <= getNumberOfEmptySeats(seats)) && (currentBookingsIndex < bookings.length) ){

            ArrayList<Integer> positionsOfBlocksAvailable = blocksAvailable(seats, sizeOfCurrentGroup);
            if(positionsOfBlocksAvailable.size() > 0){
                Random randomGenerator = new Random();
                int randomPickFromPositionsList = randomGenerator.nextInt(positionsOfBlocksAvailable.size());
                int randomPosition = positionsOfBlocksAvailable.get(randomPickFromPositionsList);
                for( int i=randomPosition; i<(randomPosition+sizeOfCurrentGroup); i++){
                    seats[i] = bookings[currentBookingsIndex];
                    currentBookingsIndex++;
                }
            }
            else {
                // TO-DO Split group and randomly assign to individual seats
                for(int i=0; i<sizeOfCurrentGroup; i++){
                    ArrayList<Integer> positionsOfEmptySeats = getPositionsOfEmptySeats(seats);
                    Random randomGenerator2 = new Random();
                    int randomPickFromEmptySeats = randomGenerator2.nextInt(positionsOfEmptySeats.size());
                    int randomPosition = positionsOfEmptySeats.get(randomPickFromEmptySeats);
                    seats[randomPosition] = bookings[currentBookingsIndex];
                    currentBookingsIndex++;
                }
            }
            if( currentBookingsIndex < bookings.length){
                sizeOfCurrentGroup = Integer.parseInt(bookings[currentBookingsIndex]);
                currentBookingsIndex++;
                if( sizeOfCurrentGroup > getNumberOfEmptySeats(seats)){
                    System.out.println("Not all passengers were seated successfully.");
                }
            }
        }
    }

    public static ArrayList<Integer> getPositionsOfEmptySeats(String[] seats) {

        ArrayList<Integer> positionsOfEmptySeats = new ArrayList<>();
        for (int i=0; i<seats.length; i++){
            if(seats[i].equals("-empty-")){
                positionsOfEmptySeats.add(i);
            }
        }
        return positionsOfEmptySeats;
    }

    public static ArrayList<Integer> blocksAvailable(String[] seats, int sizeOfCurrentGroup) {

        int currentBlockOfEmptySeats = 0;
        ArrayList<Integer> positionsOfBlocksAvailable = new ArrayList<>();

        for(int i=0; i<seats.length; i++){
            for(int j=i; (j<(i+sizeOfCurrentGroup))&&(j<seats.length); j++){
                if(seats[j].equals("-empty-")){
                    currentBlockOfEmptySeats++;
                    if( currentBlockOfEmptySeats == sizeOfCurrentGroup){
                        positionsOfBlocksAvailable.add(j-(sizeOfCurrentGroup-1));
                    }
                }
                else {
                    currentBlockOfEmptySeats = 0;
                }
            }
            currentBlockOfEmptySeats = 0;
        }

        return positionsOfBlocksAvailable;

    }

    public static String[] initializeSeats(int numberOfSeats){
        String[] seats = new String[numberOfSeats];
        for(int i=0; i<numberOfSeats; i++){
            seats[i] = "-empty-";
        }
        return seats;
    }

    public static int getNumberOfEmptySeats(String[] seats){
        int numberOfEmptySeats=0;
        for(int i=0; i<seats.length; i++){
            if(seats[i].equals("-empty-"))
                numberOfEmptySeats++;
        }
        return numberOfEmptySeats;
    }

    public static void printSeatsAfterBooking(String[] seats){
        for(int i=0; i<seats.length; i++){
            System.out.println("Seat " + (i+1) + ": " + seats[i]);
        }
    }

    public static void LinearlyBookPassengers(String[] bookings, String[] seats){
        int currentBookingsIndex = 1;
        //int numberOfSeats = Integer.parseInt(bookings[0]);
        int sizeOfCurrentGroup = Integer.parseInt(bookings[currentBookingsIndex]);
        currentBookingsIndex++;

        // proceed with booking into seats if enough empty seats are available for current group
        while ( (sizeOfCurrentGroup <= getNumberOfEmptySeats(seats)) && (currentBookingsIndex<bookings.length) ){
            int positionOfBlockAvailable = blockAvailable(seats, sizeOfCurrentGroup);
            if(positionOfBlockAvailable >=0){
                for(int i=positionOfBlockAvailable; i<(positionOfBlockAvailable+sizeOfCurrentGroup);i++){
                    seats[i] = bookings[currentBookingsIndex];
                    currentBookingsIndex++;
                }
            }
            else {
                //System.out.println("No more seats left for this group");
            }
            if(currentBookingsIndex<bookings.length){
                sizeOfCurrentGroup = Integer.parseInt(bookings[currentBookingsIndex]);
                currentBookingsIndex++;
                if( sizeOfCurrentGroup > getNumberOfEmptySeats(seats)){
                    System.out.println("No more seats available.");
                }
            }
        }
    }

    public static int blockAvailable (String[] seats, int sizeOfCurrentGroup){

        int currentBlockOfEmptySeats=0;

        for (int i=0; i<seats.length; i++){
            if(seats[i].equals("-empty-")){
                currentBlockOfEmptySeats++;
                if( currentBlockOfEmptySeats == sizeOfCurrentGroup){
                    return (i-(sizeOfCurrentGroup-1));
                }
            }
            else{
                currentBlockOfEmptySeats=0;
            }
        }
        return -1;
    }


}
