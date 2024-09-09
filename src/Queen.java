import javax.swing.*;

public class Queen extends  Piece{
    Piece [][] gameBoard;

    public Queen (int currentX, int currentY, String color, String name) {
        super(currentX, currentY, 5, color, name, "Queen");
    }

    public boolean isMoveValid(int destinationX, int destinationY) {
        gameBoard = Board.getGameBoard();

        Piece [][] originalBoard = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                originalBoard[i][j] = gameBoard[i][j];
            }
        }

        int xChange = super.getCurrentX() - destinationX;
        int yChange = super.getCurrentY() - destinationY;
        int absoluteChange = Math.abs(xChange);

        boolean moveDiagonal = false;
        boolean moveStraight = false;
        boolean checkingStalemateOrCheckmate = Board.isCheckingStalemateOrCheckmate();

        if (destinationX < 0 || destinationX >= 8 || destinationY < 0 || destinationY >= 8 ||
            (destinationX == getCurrentX() && destinationY == getCurrentY())) {
            return false;
        }

        if (destinationX == getCurrentX() || destinationY == getCurrentY()) {
            moveStraight = true;
        } else if (destinationX + destinationY == super.getCurrentX() + super.getCurrentY() ||
            destinationX - destinationY == super.getCurrentX() - super.getCurrentY()) {
            moveDiagonal = true;
        } else {
            return false;
        }

        if (moveStraight) {
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
        }

        if (moveDiagonal) {
            if (xChange > 0 && yChange > 0 ) { //moving left-up
                for (int i = 1; i < absoluteChange; i++) {
                    if (gameBoard[super.getCurrentX() - i][super.getCurrentY() - i] != null) {
                        return false;
                    }
                }
            } else if (xChange < 0 && yChange < 0) { //Moving down right
                for (int i = 1; i < absoluteChange; i++) {
                    if (gameBoard[super.getCurrentX() + i][super.getCurrentY() + i] != null) {
                        return false;
                    }
                }
            } else if (xChange > 0 && yChange < 0) { //Moving down left
                for (int i = 1; i < absoluteChange; i++) {
                    if (gameBoard[super.getCurrentX() - i][super.getCurrentY() + i] != null) {
                        return false;
                    }
                }
            } else { //Moving right up
                for (int i = 1; i < absoluteChange; i++) {
                    if (gameBoard[super.getCurrentX() + i][super.getCurrentY() - i] != null) {
                        return false;
                    }
                }
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
        } else {
            Board.setGameBoard(originalBoard);
        }

        return true;
    }

}
