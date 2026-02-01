import java.util.Scanner;

public class Score4_Game {

    // Symbols for players
    private static final char[] PLAYER_SYMBOLS = {'X', 'O', '#', '$'};
    private static final char EMPTY = '.';

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Score4!");
        System.out.println("Place 4 tokens in a row (horizontal, vertical, or diagonal) to win.");
        System.out.println();

        boolean playAgain = true;

        while (playAgain) {
            int numOfPlayers = readNumOfPlayers(sc);

            // Determine board size based on number of players
            int rows, cols;
            if (numOfPlayers == 2) {
                rows = 6;
                cols = 7;
            } else if (numOfPlayers == 3) {
                rows = 7;
                cols = 9;
            } else { 
                rows = 7;
                cols = 12;
            }

            // Read player names
            String[] playerNames = readPlayerNames(sc, numOfPlayers);

            char[][] board = initializeBoard(rows, cols);

            boolean hasWinner = false;
            int winnerIndex = -1; 
            int turn = 0;        

            while (!hasWinner && !isBoardFull(board)) {
                int currentPlayerIndex = turn % numOfPlayers;
                char symbol = PLAYER_SYMBOLS[currentPlayerIndex];

                printBoard(board);
                System.out.println(playerNames[currentPlayerIndex] + " (" + symbol + "), choose a column (1-" + cols + "):");

                int col = readValidColumn(sc, board);      
                int row = dropToken(board, col, symbol);  

                // Check win after placing the token
                if (checkWin(board, row, col, symbol)) {
                    hasWinner = true;
                    winnerIndex = currentPlayerIndex;
                }

                // Always advance move counter after a valid move
                turn++;

                System.out.println();
            }

            // Final state
            printBoard(board);

            // Print total moves and rounds info
            System.out.println("Total moves played: " + turn);
            int fullRounds = turn / numOfPlayers;
            int extraMoves = turn % numOfPlayers;
            System.out.println("Full rounds completed: " + fullRounds + " (plus " + extraMoves + " move(s) into the next round)");

            if (hasWinner) {
                System.out.println(playerNames[winnerIndex] + " wins! Congratulations!");
            } else {
                System.out.println("Game over! No winner (the board is full).");
            }

            // Restart option
            playAgain = askRestart(sc);
            System.out.println();
        }

        sc.close();
    }

    // Reads number of players (2-4) with validation
    private static int readNumOfPlayers(Scanner sc) {
        int n;

        while (true) {
            System.out.print("Enter number of players (2-4): ");

            if (!sc.hasNextInt()) {
                sc.next(); 
                System.out.println("Invalid input. Please enter a number (2-4).");
                continue;
            }

            n = sc.nextInt();

            if (n >= 2 && n <= 4) {
                break;
            } else {
                System.out.println("Invalid number of players. Please choose 2, 3, or 4.");
            }
        }

        System.out.println();
        return n;
    }

    // Reads names for each player
    private static String[] readPlayerNames(Scanner sc, int numOfPlayers) {
        // Clear leftover newline from previous nextInt()
        sc.nextLine();

        String[] names = new String[numOfPlayers];

        for (int i = 0; i < numOfPlayers; i++) {
            while (true) {
                System.out.print("Enter name for Player " + (i + 1) + " (" + PLAYER_SYMBOLS[i] + "): ");
                String name = sc.nextLine().trim();

                if (name.isEmpty()) {
                    System.out.println("Name cannot be empty. Try again.");
                    continue;
                }

                names[i] = name;
                break;
            }
        }

        System.out.println();
        return names;
    }

    // Asks user if they want to restart the game
    private static boolean askRestart(Scanner sc) {
        while (true) {
            System.out.print("Do you want to start a new game? (y/n): ");
            String ans = sc.next().trim().toLowerCase();

            if (ans.equals("y") || ans.equals("yes")) return true;
            if (ans.equals("n") || ans.equals("no")) return false;

            System.out.println("Please type y or n.");
        }
    }

    // Initializes the board with EMPTY character
    private static char[][] initializeBoard(int rows, int cols) {
        char[][] board = new char[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                board[r][c] = EMPTY;
            }
        }

        return board;
    }

    // Prints the board and the column numbering
    private static void printBoard(char[][] board) {
        int rows = board.length;
        int cols = board[0].length;

        System.out.println("Current board:");

        // Print board rows
        for (int r = 0; r < rows; r++) {
            System.out.print("|");
            for (int c = 0; c < cols; c++) {
                System.out.print(" " + board[r][c] + " ");
            }
            System.out.println("|");
        }

        // Print column numbers aligned under each column
        System.out.print(" "); // small left offset under the board
        for (int c = 1; c <= cols; c++) {
            System.out.printf("%2d ", c);
        }
        System.out.println();
    }

    // Reads a valid column and returns 0-based index
    private static int readValidColumn(Scanner sc, char[][] board) {
        int cols = board[0].length;

        while (true) {
            System.out.print("Column: ");

            if (!sc.hasNextInt()) {
                sc.next(); 
                System.out.println("Invalid input. Please enter a column number.");
                continue;
            }

            int input = sc.nextInt();
            int col = input - 1;

            // Check bounds
            if (col < 0 || col >= cols) {
                System.out.println("Out of range. Please choose a column between 1 and " + cols + ".");
                continue;
            }

            // Check if column is full
            if (isColumnFull(board, col)) {
                System.out.println("This column is full. Please choose another column.");
                continue;
            }

            return col;
        }
    }

    // Returns true if the top cell of the column is not EMPTY
    private static boolean isColumnFull(char[][] board, int col) {
        return board[0][col] != EMPTY;
    }

    // Drops a token into the given column; returns the row where it landed
    private static int dropToken(char[][] board, int col, char symbol) {
        for (int r = board.length - 1; r >= 0; r--) {
            if (board[r][col] == EMPTY) {
                board[r][col] = symbol;
                return r;
            }
        }
        // Should never happen if we validate column is not full
        return -1;
    }

    // Checks if board is full 
    private static boolean isBoardFull(char[][] board) {
        int cols = board[0].length;
        for (int c = 0; c < cols; c++) {
            if (board[0][c] == EMPTY) return false;
        }
        return true;
    }

    // Checks if the last move created a line of 4 in any direction
    private static boolean checkWin(char[][] board, int row, int col, char symbol) {
        // Directions: horizontal, vertical, diagonal down-right, diagonal up-right
        return hasFourInLine(board, row, col, symbol, 0, 1)    // horizontal
                || hasFourInLine(board, row, col, symbol, 1, 0)    // vertical
                || hasFourInLine(board, row, col, symbol, 1, 1)    // diagonal \
                || hasFourInLine(board, row, col, symbol, -1, 1);  // diagonal /
    }

    // Counts consecutive symbols in both directions for (dr, dc). If total >= 4 -> win
    private static boolean hasFourInLine(char[][] board, int row, int col, char symbol, int dr, int dc) {
        int count = 1; 

        count += countDirection(board, row, col, symbol, dr, dc);
        count += countDirection(board, row, col, symbol, -dr, -dc);

        return count >= 4;
    }

    // Counts consecutive symbols starting from (row, col) moving by (dr, dc)
    private static int countDirection(char[][] board, int row, int col, char symbol, int dr, int dc) {
        int r = row + dr;
        int c = col + dc;
        int count = 0;

        while (r >= 0 && r < board.length && c >= 0 && c < board[0].length) {
            if (board[r][c] == symbol) {
                count++;
                r += dr;
                c += dc;
            } else {
                break;
            }
        }

        return count;
    }
}
