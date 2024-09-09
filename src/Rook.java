import javax.swing.*;

public class Rook extends Piece{
    boolean movedFromOriginalPosition;
    Piece [][] gameBoard;

    public Rook(int currentX, int currentY, String color, String name) {
        super(currentX, currentY, 5, color, name, "Rook");
        this.movedFromOriginalPosition = false;
        this.gameBoard = Board.getGameBoard();
    }

    public boolean isMoveValid(int destinationX, int destinationY) { //right now, this also moves the pieces
       gameBoard = Board.getGameBoard();
        int xChange = super.getCurrentX() - destinationX;
        int yChange = super.getCurrentY() - destinationY;
        boolean checkingStalemateOrCheckmate = Board.isCheckingStalemateOrCheckmate();

        if (xChange != 0 && yChange != 0) { // Piece moved diagonally
            return false;
        } else if (xChange == 0 && yChange == 0) { // Piece didn't move
            return false;
        } else if (destinationX < 0 || destinationX > 7 || destinationY < 0 || destinationY > 7) { // Piece moved out of board
            return false;
        }

        if (xChange != 0) { //X Change
            if (xChange > 0) { //Piece move right
                for (int i = super.getCurrentX(); i < destinationX; i++) {
                    if (gameBoard[i][super.getCurrentY()] != null) {
                        return false;
                    }
                }
            } else { //Piece move left
                for (int i = super.getCurrentX(); i > destinationX; i--) {
                    if (gameBoard[i][super.getCurrentY()] != null) {
                        return false;
                    }
                }
            }
        } else { //Y Change
            if (yChange > 0) { //Piece move up
                for (int i = super.getCurrentY(); i < destinationY; i++) {
                    if (gameBoard[i][super.getCurrentY()] != null) {
                        return false;
                    }
                }
            } else { //Piece move down
                for (int i = super.getCurrentY(); i > destinationY; i--) {
                    if (gameBoard[i][super.getCurrentY()] != null) {
                        return false;
                    }
                }
            }
        }

        //TODO: Check if this move makes its color's king in check

        Piece [][] originalBoard = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                originalBoard[i][j] = gameBoard[i][j];
            }
        }

        if (gameBoard[destinationX][destinationY] != null) {
            if (gameBoard[destinationX][destinationY].getColor().equals(super.getColor())) { //Trying to capture same team
                return false;
            } else {
                if (!checkingStalemateOrCheckmate) {
                    super.getCaptured(destinationX, destinationY); // Capture the piece
                }
            }
        }

        gameBoard[getCurrentX()][getCurrentY()] = null;
        gameBoard[destinationX][destinationY] = this;

        Board.setGameBoard(gameBoard);

        if (super.getSameColorKing(super.getColor()).inInCheck()) {
            Board.setGameBoard(originalBoard);
            return false;
        }

        if (!checkingStalemateOrCheckmate) {
            super.setCurrentX(destinationX);
            super.setCurrentY(destinationY);
            movedFromOriginalPosition = true;
        } else {
            Board.setGameBoard(originalBoard);
        }

        return true;
    }

    public boolean isMovedFromOriginalPosition() {
        return movedFromOriginalPosition;
    }

    public void setMovedFromOriginalPosition(boolean movedFromOriginalPosition) {
        this.movedFromOriginalPosition = movedFromOriginalPosition;
    }
}
