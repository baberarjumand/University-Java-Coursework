import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String inputFile = "a3plot1.txt";
//        String inputFile = "a3plot2.txt";

        ArrayList<Point> points = processInputFileAndExtractPointsData(inputFile);

        int numberOfRows = 20;      // Range of y is from 1 to 20 (inclusive)
        int numberOfCols = 40;      // Range of x is from 0 to 40 (inclusive)

        String[][] grid = initializeGrid(numberOfRows+1, numberOfCols+1);

        // place points on plot
        grid = placePointsOnGrid(grid, points);

//        printGrid(grid);

        ArrayList<Point> regressionLineCoordinates = getRegressionPointsCoordinates(points);

        // draw line on plot
        grid = placeRegressionPointsOnGrid(grid, regressionLineCoordinates);

        printGrid(grid);

    }

    private static String[][] placeRegressionPointsOnGrid(String[][] grid, ArrayList<Point> regressionLineCoordinates) {

        int maxRowIndex = grid.length-1; // max row index going down is 20

        for(Point p: regressionLineCoordinates) {
            if (grid[maxRowIndex-p.getyCoordinate()][p.getxCoordinate()].equals("X")) {
                grid[maxRowIndex-p.getyCoordinate()][p.getxCoordinate()] = "*";
            } else {
                grid[maxRowIndex-p.getyCoordinate()][p.getxCoordinate()] = "-";
            }
        }

        return grid;
    }

    private static ArrayList<Point> getRegressionPointsCoordinates(ArrayList<Point> points) {

        // to calculate the points for the regression line, I used the formula from the following site
        // https://www.mathsisfun.com/data/least-squares-regression.html

        ArrayList<Point> regressionPointsList = new ArrayList<>();

        // get number of points
        double numberOfPoints = points.size();

        // get sigmaXY
        double sigmaXY = 0;

        // get sigma X
        double sigmaX = 0;

        // get sigmaY
        double sigmaY = 0;

        // get sigmaOfXSquared
        double sigmaOfXSquared = 0;

        for(Point p: points){
            sigmaXY += (p.getxCoordinate() * p.getyCoordinate());
            sigmaX += p.getxCoordinate();
            sigmaY += p.getyCoordinate();
            sigmaOfXSquared += (p.getxCoordinate() * p.getxCoordinate());
        }

        // get square of sigmaX
        double squareOfSigmaX = (sigmaX * sigmaX);

//        System.out.println("SigmaXY: " + sigmaXY);
//        System.out.println("SigmaX: " + sigmaX);
//        System.out.println("SigmaY: " + sigmaY);
//        System.out.println("SigmaOfXsq: " + sigmaOfXSquared);
//        System.out.println("Sq of SigmaX: " + squareOfSigmaX);

        double gradient = ((numberOfPoints*sigmaXY)-(sigmaX*sigmaY))/((numberOfPoints*sigmaOfXSquared)-(squareOfSigmaX));
        double yIntercept = (sigmaY-(gradient*sigmaX))/numberOfPoints;

//        System.out.println("gradient: " + gradient);
//        System.out.println("yIntercept: " + yIntercept);

        // use y=mx+b to calculate points for regression line and add to list
        for(int i=0; i<=40; i++) {
            regressionPointsList.add(new Point(i, ((gradient*i)+yIntercept)));
        }

        return regressionPointsList;

    }

    private static String[][] placePointsOnGrid(String[][] grid, ArrayList<Point> points) {

//        System.out.println(grid.length);                  // prints 21
//        System.out.println(grid[grid.length-1].length);   // prints 41
        int maxRowIndex = grid.length-1;                    // max row index going down is 20
        int maxColIndex = grid[grid.length-1].length-1;     // max col index going right is 40

//        System.out.println(maxRowIndex + " " + maxColIndex);

        // testing logic by placing individual points
//        // place 2,2
//        grid[maxRowIndex-2][2] = "X";
//        // place 3,3
//        grid[maxRowIndex-3][3] = "X";
//        // place 2,1
//        grid[maxRowIndex-1][2] = "X";
//        // place 1,5
//        grid[maxRowIndex-5][1] = "X";
//        // place 5,1
//        grid[maxRowIndex-1][5] = "X";

        // plotting points from points list onto grid
        for (Point p: points){
            grid[maxRowIndex-p.getyCoordinate()][p.getxCoordinate()] = "X";
        }

        return grid;
    }

    private static void printGrid(String[][] grid) {
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[i].length; j++){
                System.out.print(grid[i][j] + " ");
                //System.out.print("(" + i + "," + j + ")\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static String[][] initializeGrid(int numberOfRows, int numberOfColoumns) {

        String[][] grid = new String[numberOfRows][numberOfColoumns];

        // set all items in 2D array to o
        for (int i=0; i<numberOfRows; i++) {
            for(int j=0; j<numberOfColoumns; j++) {
                //grid[i][j] = "o";
                if( i==(numberOfRows-1) && j==0){       // set origin
                    grid[i][j] = "+";
                } else if(j==0){                        // set y-axis
                    grid[i][j] = "|";
                } else if (i == (numberOfRows-1)) {     // set x-axis
                    grid[i][j] = "-";
                } else {
                    grid[i][j] = " ";
                }
            }
        }

        return grid;

    }


    private static ArrayList<Point> processInputFileAndExtractPointsData(String inputFile) {

        ArrayList<Point> points = new ArrayList<>();

        try(Scanner s = new Scanner(new FileReader(inputFile))) {
            while(s.hasNext()) {
                String input[] = s.nextLine().split(" ");
                if(input.length == 2) {
//                    System.out.println("xc: " + input[0] + " yc: " + input[1]);
                    try {
                        int xCoordinate = Integer.parseInt(input[0]);
                        int yCoordinate = Integer.parseInt(input[1]);
                        if ( xCoordinate>=0 && xCoordinate <=40 && yCoordinate>=1 && yCoordinate<=20){
                            points.add(new Point(xCoordinate,yCoordinate));
                        }
                    } catch (NumberFormatException e) {
                        //System.out.println("Invalid input detected: " + input[0] + " " + input[1]);
                    }
                }
                else {
//                    System.out.println("Invalid input detected and ignored.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return points;

    }


}

class Point {
    private int xCoordinate;
    private int yCoordinate;

    public Point(int xCoordinate, int yCoordinate) {
        this.setxCoordinate(xCoordinate);
        this.setyCoordinate(yCoordinate);
    }

    public Point(int xCoordinate, double yCoordinate) {
        this.setxCoordinate(xCoordinate);
        this.setyCoordinate(yCoordinate);
    }

    @Override
    public String toString() {
        return "xc: " + xCoordinate + " yc: " + yCoordinate;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = (int)Math.round(xCoordinate);
    }

    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = (int)Math.round(yCoordinate);
    }
}