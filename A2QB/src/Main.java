import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String inputFileName = "a2b.txt";

        ArrayList<Sentence> allSentences = readTextFileAndCreateArrayListOfSentences(inputFileName);

        // checking if allSentences array created as intended
//        int currentSentenceNumber = 1;
//        for (Sentence sentence : allSentences) {
//            System.out.println( "(" + currentSentenceNumber + ") " + sentence );
//            currentSentenceNumber++;
//        }

        // DONE print first five sentences
        printFirstFiveSentencesInAllSentences(allSentences);

        // DONE print last five sentences
        printLastFiveSentencesInAllSentences(allSentences);

        // DONE print summary statistics
        printSummaryStatisticsForAllSentences(allSentences);

    }

    private static void printSummaryStatisticsForAllSentences(ArrayList<Sentence> allSentences) {

        int numberOfWords = 0;
        int numberOfLetters = 0;
        int numberOfSentences = allSentences.size();
        for ( Sentence sentence : allSentences ) {
            numberOfWords += sentence.getWordCount();
            numberOfLetters += sentence.getLetterCount();
        }

        System.out.println("Summary Statistics:");

        // DONE print number of letters
        System.out.println("Number of letters: " + numberOfLetters);

        // DONE print number of words
        System.out.println("Number of words: " + numberOfWords);

        // DONE print number of sentences
        System.out.println("Number of sentences: " + numberOfSentences);

        // DONE print readability
        double automatedReadabilityIndex = (4.71*(numberOfLetters/numberOfWords)) + (0.5*(numberOfWords/numberOfSentences)) - 21.43;
        System.out.println("Readability: " + String.format("%.1f", automatedReadabilityIndex));

    }

    private static void printLastFiveSentencesInAllSentences(ArrayList<Sentence> allSentences) {

        System.out.println("Last five sentences in input file:");
        for ( int i = allSentences.size()-5; i < allSentences.size(); i++) {
            System.out.println( "(" + (i+1) + ") " + allSentences.get(i));
        }
        System.out.println();

    }

    private static void printFirstFiveSentencesInAllSentences(ArrayList<Sentence> allSentences) {

        System.out.println("First five sentences in input file:");
        for ( int i=0; i<5; i++) {
            System.out.println( "(" + (i+1) + ") " + allSentences.get(i));
        }
        System.out.println();

    }

    private static ArrayList<Sentence> readTextFileAndCreateArrayListOfSentences(String inputFileName) {

        ArrayList<Sentence> allSentences = new ArrayList<>();
        int currentSentenceNumber = 0;
        allSentences.add(new Sentence());

        try {

            Scanner scanner = new Scanner(new File(inputFileName));

            while ( scanner.hasNext() ) {

                String currentWord = scanner.next();

                if ( !(currentWord.charAt(currentWord.length()-1)=='.' || currentWord.charAt(currentWord.length()-1)=='?' || currentWord.charAt(currentWord.length()-1)=='!' ) ) {

                    allSentences.get(currentSentenceNumber).addWordToSentence(currentWord);

                } else {

                    allSentences.get(currentSentenceNumber).addWordToSentence(currentWord);
                    allSentences.add(new Sentence());
                    currentSentenceNumber++;

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // this line removes the last empty element of allSentences
        allSentences.remove(allSentences.get(allSentences.size()-1));

        return allSentences;

    }

    public static class Sentence {

        private ArrayList<String> words = new ArrayList<>();

        public void addWordToSentence (String word) {
            this.words.add(word);
        }

        public int getWordCount () {
            return this.words.size();
        }

        public int getLetterCount() {
            int numberOfCharacters = 0;
            for ( String word : this.words) {
                //numberOfCharacters += word.length();
                if ( word.charAt(word.length()-1)=='.' || word.charAt(word.length()-1)=='?' || word.charAt(word.length()-1)=='!' || word.charAt(word.length()-1)==',' ) {
                    numberOfCharacters += word.length();
                    numberOfCharacters--;
                } else {
                    numberOfCharacters += word.length();
                }

            }
            return numberOfCharacters;
        }

        @Override
        public String toString() {
            String s = "";
            for (int i=0; i<this.words.size(); i++) {
                s += this.words.get(i) + " ";
            }
            return s;
        }
    }

}
