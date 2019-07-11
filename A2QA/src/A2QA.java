import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class A2QA {

    public static void main(String[] args) {

        String inputFileName = "a2a.txt";
        String outputFileName = "a2q1out.txt";

        ArrayList<Event> allEvents = readTextFileAndCreateArrayOfEvents(inputFileName);

        // checking if all events are successfully imported into allEvents array
//        for (Event event : allEvents) {
//            System.out.println(event);
//        }

        try {
            PrintWriter pw = new PrintWriter(new FileWriter(outputFileName));

            // count number of countries in allEvents
            countAndPrintDifferentCountriesInAllEvents(allEvents, pw);

            // count number of each event type in allEvents
            countAndPrintDifferentEventTypesInAllEvents(allEvents, pw);

            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void countAndPrintDifferentEventTypesInAllEvents(ArrayList<Event> allEvents, PrintWriter pw) {

        ArrayList<String> eventTypes = new ArrayList<>();
        ArrayList<Integer> numbersOfEvents = new ArrayList<>();

        for ( int i=0; i<allEvents.size(); i++) {
            if (!eventTypes.contains(allEvents.get(i).getEventType())) {
                eventTypes.add(allEvents.get(i).getEventType());
                numbersOfEvents.add(1);
            } else {
                int index = eventTypes.indexOf(allEvents.get(i).getEventType());
                numbersOfEvents.set(index, numbersOfEvents.get(index)+1);
            }
        }

        System.out.println("Count of gold medallists by event type:");
        pw.println("Count of gold medallists by event type:");
        for ( int j=0; j<eventTypes.size(); j++) {
            System.out.println(eventTypes.get(j) + " - " + numbersOfEvents.get(j));
            pw.println(eventTypes.get(j) + " - " + numbersOfEvents.get(j));
        }

    }

    private static void countAndPrintDifferentCountriesInAllEvents(ArrayList<Event> allEvents, PrintWriter pw) {

        ArrayList<String> countries = new ArrayList<>();
        ArrayList<Integer> numberOfMedals = new ArrayList<>();

        for ( int i=0; i<allEvents.size(); i++) {
            if (!countries.contains(allEvents.get(i).getCountryCode())) {
                countries.add(allEvents.get(i).getCountryCode());
                numberOfMedals.add(1);
            } else {
                int index = countries.indexOf(allEvents.get(i).getCountryCode());
                numberOfMedals.set(index, numberOfMedals.get(index)+1);
            }
        }

        System.out.println("Count of gold medallists by country:");
        pw.println("Count of gold medallists by country:");
        for ( int j=0; j<countries.size(); j++ ) {
            System.out.println(countries.get(j) + " - " + numberOfMedals.get(j));
            pw.println(countries.get(j) + " - " + numberOfMedals.get(j));
        }
        System.out.println();
        pw.println();

    }

    private static ArrayList<Event> readTextFileAndCreateArrayOfEvents(String fileName) {

        ArrayList<Event> allEvents = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while(scanner.hasNextLine()) {
                String s = scanner.nextLine();
                if( s.length() == 3 ) {
                    String countryCode = s;
                    String eventType = scanner.nextLine();
                    String eventName = scanner.nextLine();
                    //System.out.println(countryCode);
                    //System.out.println(eventName);
                    //System.out.println(eventType);
                    allEvents.add(new Event(countryCode, eventType,eventName));
                }

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return allEvents;
    }

    public static class Event {
        private String countryCode;
        private String eventType;
        private String eventDescription;

        public Event (String countryCode, String eventType, String eventDescription) {
            this.countryCode = countryCode;
            this.eventType = eventType;
            this.eventDescription = eventDescription;
        }

        @Override
        public String toString() {
            String s =  "Country Code: " + countryCode +
                        "\nEvent Type\t: " + eventType +
                        "\nEvent Desc\t: " + eventDescription;
            return s;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getEventType() {
            return eventType;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
        }

        public String getEventDescription() {
            return eventDescription;
        }

        public void setEventDescription(String eventDescription) {
            this.eventDescription = eventDescription;
        }
    }
}


