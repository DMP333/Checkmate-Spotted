import javax.swing.*;

public class Knight extends Piece{
    Piece [][] gameBoard;

    public Knight(int currentX, int currentY, String color, String name) {
        super(currentX, currentY, 3, color, name, "Knight");
    }

    public boolean isMoveValid(int destinationX, int destinationY) {
        gameBoard = Board.getGameBoard();
        Piece [][] originalBoard = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                originalBoard[i][j] = gameBoard[i][j];
            }
        }

        int xChange = destinationX - super.getCurrentX();
        int yChange = destinationY - super.getCurrentY();
        boolean checkingStalemateOrCheckmate = Board.isCheckingStalemateOrCheckmate();


        if (destinationX < 0 || destinationX > 7 || destinationY < 0 || destinationY > 7) { // Piece moved out of board
            return false;
        }

        if (Math.abs(xChange) != Math.abs(yChange)) {
            if ((Math.abs(xChange) == 2 || Math.abs(yChange) == 2) && (Math.abs(xChange) == 1 || Math.abs(yChange) == 1)) {
                if (destinationX >= 0 && destinationX < 8 && destinationY >= 0 && destinationY < 8) {
                    if (gameBoard[destinationX][destinationY] != null) {
                      if (!gameBoard[destinationX][destinationY].getColor().equals(super.getColor())) {
                          if (!checkingStalemateOrCheckmate) {
                              super.getCaptured(destinationX, destinationY);
                          }
                      } else {
                          return false;
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
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
