import javax.swing.*;

public class Board {
    static Piece [][] gameBoard;
    static JButton [][] buttonBoard;
    public static GUI sharingBoard;
    public static int turnCount = 0;
    public static boolean checkingStalemateOrCheckmate = false;


    public Board() {
        gameBoard = new Piece [8][8];
        sharingBoard = new GUI();
        buttonBoard = new JButton[8][8];
    }

    public static Piece[][] getGameBoard() {
        return gameBoard;
    }

    public static void setGameBoard(Piece[][] gameBoard) {
        Board.gameBoard = gameBoard;
    }

    public static GUI getSharingBoard() {
        return sharingBoard;
    }

    public static void setSharingBoard(GUI sharingBoard) {
        Board.sharingBoard = sharingBoard;
    }

    public static void resetPiece() {
        for (int i = 0; i < 8; i++) {
            buttonBoard[i][1] = sharingBoard.createPiece("Pawn", "Black", i, 1);
            gameBoard[i][1] = new Pawn(i, 1, "Black", "B" + Character.toString((char)i + 97) + "Pawn");
        }

        for (int i = 0; i < 8; i++) {
            buttonBoard[i][6] = sharingBoard.createPiece("Pawn", "White", i, 6);
            gameBoard[i][6] = new Pawn(i, 6, "White", "W" + Character.toString((char)i + 97) + "Pawn");
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 2; j++) {
                if (i == 0 || i == 7) {
                    if (j == 0) {
                        buttonBoard[i][0] = sharingBoard.createPiece("Rook", "Black", i, 0);
                        gameBoard[i][0] = new Rook(i, 0, "Black", "B" + Character.toString((char)i + 97) + " Rook");
                    } else {
                        buttonBoard[i][7] = sharingBoard.createPiece("Rook", "White", i, 7);
                        gameBoard[i][7] = new Rook(i, 7, "White", "W" + Character.toString((char)i + 97) + "Rook");
                    }
                } else if (i == 1 || i == 6) {
                    if (j == 0) {
                        buttonBoard[i][0] = sharingBoard.createPiece("Knight", "Black", i, 0);
                        gameBoard[i][0] = new Knight(i, 0, "Black", "B" + Character.toString((char)i + 97) + "Knight");
                    } else {
                        buttonBoard[i][7] = sharingBoard.createPiece("Knight", "White", i, 7);
                        gameBoard[i][7] = new Knight(i, 7, "White", "W" + Character.toString((char)i + 97) + "Knight");
                    }
                } else if (i == 2 || i == 5) {
                    if (j == 0) {
                        buttonBoard[i][0] = sharingBoard.createPiece("Bishop", "Black", i, 0);
                        gameBoard[i][0] = new Bishop(i, 0, "Black", "B" + Character.toString((char)i + 97) + "Bishop");
                    } else {
                        buttonBoard[i][7] = sharingBoard.createPiece("Bishop", "White", i, 7);
                        gameBoard[i][7] = new Bishop(i, 7, "White", "W" + Character.toString((char)i + 97) + "Bishop");
                    }
                } else if (i == 3) {
                    if (j == 0) {
                        buttonBoard[i][0] = sharingBoard.createPiece("Queen", "Black", i, 0);
                        gameBoard[i][0] = new Queen(i, 0, "Black", "B" + Character.toString((char)i + 97) + "Queen");
                    } else {
                        buttonBoard[i][7] = sharingBoard.createPiece("Queen", "White", i, 7);
                        gameBoard[i][7] = new Queen(i, 7, "White", "W" + Character.toString((char)i + 97) + "Queen");
                    }
                } else {
                    if (j == 0) {
                        buttonBoard[i][0] = sharingBoard.createPiece("King", "Black", i, 0);
                        gameBoard[i][0] = new King(i, 0, "Black", "B" + Character.toString((char)i + 97) + "King");
                    } else {
                        buttonBoard[i][7] = sharingBoard.createPiece("King", "White", i, 7);
                        gameBoard[i][7] = new King(i, 7, "White", "W" + Character.toString((char)i + 97) + "King");
                    }
                }

            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 2; j <= 5; j++) {
                buttonBoard[i][j] = sharingBoard.createPiece("", "", i, j);
            }
        }
    }

    public static void emptyButton(int xCoordinate, int yCoordinate) {
        buttonBoard[xCoordinate][yCoordinate] = sharingBoard.createPiece("", "", xCoordinate, yCoordinate);
    }

    public static void removeButton(int xCoordinate, int yCoordinate) {
        sharingBoard.removePiece(buttonBoard[xCoordinate][yCoordinate]);
        buttonBoard[xCoordinate][yCoordinate] = null;
    }



    public static JButton[][] getButtonBoard() {
        return buttonBoard;
    }

    public static void setButtonBoard(JButton[][] buttonBoard) {
        Board.buttonBoard = buttonBoard;
    }

    public static King getSameColorKing(String color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gameBoard[i][j] != null) {
                    if ((gameBoard[i][j] instanceof King) && gameBoard[i][j].getColor().equals(color)) {
                        return (King) gameBoard[i][j];
                    }
                }
            }
        }
        return null; // This statement should never be reached
    }

    public static boolean isCheckingStalemateOrCheckmate() {
        return checkingStalemateOrCheckmate;
    }

    public static void setCheckingStalemateOrCheckmate(boolean checkingStalemateOrCheckmate) {
        Board.checkingStalemateOrCheckmate = checkingStalemateOrCheckmate;
    }
}
