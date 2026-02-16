import javax.swing.*;

public class Bishop extends Piece{
    Piece [][] gameBoard;

    public Bishop(int currentX, int currentY, String color, String name) {
        super(currentX, currentY, 3, color, name, "Bishop");
        gameBoard = Board.getGameBoard();
    }

    @Override
    public MoveResult isMoveValid(int destinationX, int destinationY) {
        gameBoard = Board.getGameBoard();
        int xChange = super.getCurrentX() - destinationX;
        int yChange = super.getCurrentY() - destinationY;
        int absoluteChange = Math.abs(xChange);
        boolean checkingStalemateOrCheckmate = Board.isCheckingStalemateOrCheckmate();

        // Checking if destination can be reached when no other piece exists
        if (destinationX < 0 || destinationX > 7 || destinationY < 0 || destinationY > 7 ) {
            return MoveResult.INVALID;
        }

        if (destinationX + destinationY != super.getCurrentX() + super.getCurrentY() &&
            destinationX - destinationY != super.getCurrentX() - super.getCurrentY()) {
            return MoveResult.INVALID;
        }

        if (destinationX == super.getCurrentX() || destinationY == super.getCurrentY()) {
            return MoveResult.INVALID;
        }

        if (xChange > 0 && yChange > 0 ) { //moving left-up
            for (int i = 1; i < absoluteChange; i++) {
                if (gameBoard[super.getCurrentX() - i][super.getCurrentY() - i] != null) {
                    return MoveResult.INVALID;
                }
            }
        } else if (xChange < 0 && yChange < 0) { //Moving down right
            for (int i = 1; i < absoluteChange; i++) {
                if (gameBoard[super.getCurrentX() + i][super.getCurrentY() + i] != null) {
                    return MoveResult.INVALID;
                }
            }
        } else if (xChange > 0 && yChange < 0) { //Moving down left
            for (int i = 1; i < absoluteChange; i++) {
                if (gameBoard[super.getCurrentX() - i][super.getCurrentY() + i] != null) {
                    return MoveResult.INVALID;
                }
            }
        } else { //Moving right up
            for (int i = 1; i < absoluteChange; i++) {
                if (gameBoard[super.getCurrentX() + i][super.getCurrentY() - i] != null) {
                    return MoveResult.INVALID;
                }
            }
        }

        //TODO: Check if King gets in check before this

        Piece [][] originalBoard = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                originalBoard[i][j] = gameBoard[i][j];
            }
        }

        if (gameBoard[destinationX][destinationY] != null) { // Capturing piece
            if (gameBoard[destinationX][destinationY].getColor().equals(super.getColor())) { //Trying to capture same team
                return MoveResult.INVALID;
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
            return MoveResult.INVALID;
        }
        if (!checkingStalemateOrCheckmate) {
            super.setCurrentX(destinationX);
            super.setCurrentY(destinationY);
        } else {
            Board.setGameBoard(originalBoard);
        }
        return MoveResult.NORMAL;
    }
}
