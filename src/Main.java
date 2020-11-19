import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        // Get the file name
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter file name: ");
        String fileName = scanner.nextLine();
        scanner.close();

        // This is the sudoku board representation
        // 0 represents an empty space
        int [][] board = initializeBoardFromFile("puzzles/" + fileName);
        printArr(board);
        board = solve(board);
        printArr(board);
    }

    public static int[][] solve(int[][] board) {
        int[][] newBoard = new int[9][9];
        for(int i = 0; i < newBoard.length; i++) {
            newBoard[i] = Arrays.copyOf(board[i], 9);
        }

        //printArr(newBoard);
        // Iterate until we find a zero
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                if(newBoard[i][j] == 0) {
                    boolean oneValid = false;
                    int[][] finalBoard;
                    // Simulate putting each number in the spot
                    for(int k = 1; k <= 9; k++) {
                        if(isValid(newBoard, k, i, j)) {
                            oneValid = true;
                            newBoard[i][j] = k;
                            //System.out.println("i: " + i + "\tj: " + j + "\tk: " + k);
                            finalBoard = solve(newBoard);
                            if(finalBoard != null) {
                                //System.out.println("Returning a board");
                                return finalBoard;
                            }
                        }
                    }
                    if(!oneValid) {
                        //System.out.println("No valid values");
                        //printArr(board);
                        return null;
                    }

                }
            }
        }
        System.out.println("Solved");
        return newBoard;
    }

    public static boolean isValid(int[][] board, int val, int i, int j) {
        // Check the row
        for(int col = 0; col < 9; col++) {
            if(board[i][col] == val) {
                return false;
            }
        }

        // Check the column
        for(int row = 0; row < 9; row++) {
            if(board[row][j] == val) {
                return false;
            }
        }

        // Check the square

        // Check which square it is in
        int squareRow = i / 3;
        int squareCol = j / 3;
        for(int row = squareRow * 3; row < squareRow * 3 + 3; row++) {
            for(int col = squareCol * 3; col < squareCol * 3 + 3; col++) {
                if(board[row][col] == val)
                    return false;
            }
        }

        return true;
    }

    public static int[][] initializeBoardFromFile(String fileName) throws IOException {
        FileReader fileReader = new FileReader(fileName);
        BufferedReader reader = new BufferedReader(fileReader);
        int[][] board = new int[9][9];
        for(int i = 0; i < board.length; i++) {
            String line = reader.readLine();
            String[] arr = line.split(" ");
            for(int j = 0; j < arr.length; j++) {
                board[i][j] = Integer.parseInt(arr[j]);
            }
        }

        reader.close();
        return board;
    }

    public static void printArr(int[][] arr) {
        if(arr == null) {
            System.out.println("Invalid board");
            return;
        }
        System.out.println("Board State: ");
        for(int[] row : arr) {
            for(int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
}
