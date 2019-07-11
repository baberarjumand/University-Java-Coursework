import java.util.ArrayList;
import java.util.Random;

public class A1Q2 {

    public static void main(String[] args) {

        Event[] September = new Event[30];
        Event[] October = new Event[31];

        // assign 10 random events with names, start times and random priorities
        assignTenRandomEvents(September);
        assignTenRandomEvents(October);

        // print out calendar for Sept and Oct
        printCalendar(September, October);

        // print out events for Sept and Oct
        System.out.println("Events in September:");
        printEventList(September);
        System.out.println("Events in October:");
        printEventList(October);

        // print out high-priority events for Sept and Oct
        System.out.println("High-priority events in September:");
        printHighPriorityEvents(September);
        System.out.println("High-priority events in October:");
        printHighPriorityEvents(October);

    }

    private static void printCalendar(Event[] september, Event[] october) {

        int currentIndex = 0;

        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(6);

        System.out.println("\nSeptember:\nSun\tMon\tTue\tWed\tThu\tFri\tSat");
        for(int j=0;j<randomInt;j++){
            System.out.print(" \t");
        }
        for(int i=0; i<30; i++){
            if( randomInt>0 ){
                if((september[i]!=null)&&(september[i].getPriority()==3)) {
                    System.out.print((currentIndex+1) + "*\t");
                }
                else {
                    System.out.print((currentIndex+1) + "\t");
                }
                currentIndex++;
                if((currentIndex%7)==(7-randomInt))
                    System.out.println();
            } else if ( randomInt==0 ) {
                if((september[i]!=null)&&(september[i].getPriority()==3)) {
                    System.out.print((currentIndex+1) + "*\t");
                }
                else {
                    System.out.print((currentIndex+1) + "\t");
                }
                currentIndex++;
                if((currentIndex%7)==0)
                    System.out.println();
            }
        }
        System.out.println();

        currentIndex = 0;
        int randomInt2 = ((randomInt)+30)%7;
        //System.out.println("\nRandomInt2 = " + randomInt2);

        System.out.println("\nOctober:\nSun\tMon\tTue\tWed\tThu\tFri\tSat");

        for(int j=0;j<randomInt2;j++){
            System.out.print(" \t");
        }

        for(int i=0; i<31; i++){
            if( randomInt2>0 ){
                if((october[i]!=null)&&(october[i].getPriority()==3)) {
                    System.out.print((currentIndex+1) + "*\t");
                }
                else {
                    System.out.print((currentIndex+1) + "\t");
                }
                currentIndex++;
                if((currentIndex%7)==(7-randomInt2))
                    System.out.println();
            } else if ( randomInt2==0 ) {
                if((october[i]!=null)&&(october[i].getPriority()==3)) {
                    System.out.print((currentIndex+1) + "*\t");
                }
                else {
                    System.out.print((currentIndex+1) + "\t");
                }
                currentIndex++;
                if((currentIndex%7)==0)
                    System.out.println();
            }
        }
        System.out.println("\n");
    }

    private static void printHighPriorityEvents(Event[] month) {
        for ( int i=0; i<month.length; i++) {
            if( (month[i] != null) ){
                if(month[i].getPriority() == 3){
                    System.out.print((i+1) + ": ");
                    System.out.println(month[i].printHighPriorityOnly());
                }

            }
        }
        System.out.println();
    }

    private static void printEventList(Event[] month) {
        for ( int i=0; i<month.length; i++) {
            if( month[i] != null){
                System.out.print((i+1) + ": ");
                System.out.println(month[i]);
            }
        }
        System.out.println();
    }

    private static void assignTenRandomEvents(Event[] month) {

        // Check if month elements are null initially
//        if(month[0] == null){
//            System.out.println("First element is null.");
//        }

        String[] eventNames = { "Office Party",
                                "Neighborhood Parade",
                                "Park Picnic",
                                "Charades",
                                "Soiree",
                                "Eid Party",
                                "Forest Run",
                                "Code Bash",
                                "Engineering Conference",
                                "Performance Review" };

        String[] eventStartTimes = {    "3:00PM",
                                        "12:00PM",
                                        "1:00PM",
                                        "8:00PM",
                                        "5:00PM",
                                        "8:00AM",
                                        "7:00AM",
                                        "12:00AM",
                                        "9:00AM",
                                        "11:00AM" };

        ArrayList<Integer> tenRandomUniqueDates = getTenRandomUniqueDates(month);

        // Check if ten random unique dates are correctly generated
//        for (Integer num : tenRandomUniqueDates){
//            System.out.println(num);
//        }

        for ( int i=0; i<10; i++) {
            // Cannot access set methods for Event object without instantiating new Event object first
//            month[tenRandomUniqueDates.get(i)].setName(eventNames[i]);
//            month[tenRandomUniqueDates.get(i)].setStartTime(eventStartTimes[i]);
//            month[tenRandomUniqueDates.get(i)].setPriority(getRandomPriority());
            month[tenRandomUniqueDates.get(i)] = new Event(eventNames[i], eventStartTimes[i], getRandomPriority());
        }

        // Check if ten random unique events are added to month[] correctly
//        for ( int i=0; i<month.length; i++){
//            if( month[i] != null){
//                System.out.print((i+1) + ": ");
//                System.out.println(month[i]);
//            }
//        }

    }

    private static int getRandomPriority() {
        Random randomGenerator = new Random();
        int randomPriority = randomGenerator.nextInt(3) + 1;
        return randomPriority;
    }

    private static ArrayList<Integer> getTenRandomUniqueDates(Event[] month) {

        ArrayList<Integer> tenRandomUniqueDates = new ArrayList<>();

        do {

            Random randomGenerator = new Random();
            int randomDate = randomGenerator.nextInt(month.length-1);
            if ( tenRandomUniqueDates.contains(randomDate) == false ) {
                tenRandomUniqueDates.add(randomDate);
            }
        } while ( tenRandomUniqueDates.size() < 10 );

        return tenRandomUniqueDates;

    }
}
