import javax.swing.*;

public class Piece {
    private int currentX;
    private int currentY;
    private int point;
    private String color; // Black or White
    private String name;
    private String piece;
    private Piece [][] gameBoard;

    public Piece(int currentX, int currentY, int point, String color, String name, String piece) {
        this.currentX = currentX;
        this.currentY = currentY;
        this.point = point;
        this.color = color;
        this.name = name;
        this.piece = piece;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public int getPoint() {
        return point;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public void getCaptured(int destinationX, int destinationY) {
        gameBoard = Board.getGameBoard();
        gameBoard[destinationX][destinationY] = null;




        Board.setGameBoard(gameBoard);
    }

    public King getSameColorKing(String color) {
        gameBoard = Board.getGameBoard();
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

    public King getDifferentColorKing(String color) {
        gameBoard = Board.getGameBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gameBoard[i][j] != null) {
                    if ((gameBoard[i][j] instanceof King) && !gameBoard[i][j].getColor().equals(color)) {
                        return (King) gameBoard[i][j];
                    }
                }
            }
        }
        return null; // This statement should never be reached
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public String getPiece() {
        return piece;
    }
}
