import javax.swing.*;

public class Pawn extends Piece{
   private boolean canMoveTwice;
   private boolean moveTwiceLastTurn;
   private static Piece pieceToPromote = null;
   Piece [][] gameBoard;

   public Pawn(int currentX, int currentY, String color, String name) {
       super(currentX, currentY, 1, color, name, "Pawn");
       this.canMoveTwice = true;
       this.moveTwiceLastTurn = false;
       gameBoard = Board.getGameBoard();
   }


    public int move_pawn_once () {
        return -1;
    }

    public String  getName() {
       return super.getName();
    }

    public int isMoveValid(int destinationX, int destinationY) {
        gameBoard = Board.getGameBoard();

        Piece [][] originalBoard = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                originalBoard[i][j] = gameBoard[i][j];
            }
        }

        int xChange = super.getCurrentX() - destinationX;
        int yChange = super.getCurrentY() - destinationY;
        boolean tryingToEnPassant = false;
        boolean checkingStalemateOrCheckmate = Board.isCheckingStalemateOrCheckmate();

        if (destinationX < 0 || destinationX > 8 || destinationY < 0 || destinationY > 8) { // Piece moved out of board
            return -1;
        }

        if (super.getColor().equals("Black")) {
            if (yChange > 0) {
                return -1;
            }
        } else {
            if (yChange < 0) {
                return -1;
            }
        }

        // Check for Check first

        if (xChange != 0) { //Trying to capture the piece
            if (Math.abs(xChange) != 1 || Math.abs(yChange) != 1) {
                return -1;
            }
            if (gameBoard[destinationX][destinationY] == null) { // This is enpassant case
                if (gameBoard[destinationX][super.getCurrentY()] instanceof Pawn &&
                    !gameBoard[destinationX][super.getCurrentY()].getColor().equals(super.getColor())) { // if the piece to enpassant is pawn
                    if (!((Pawn)(gameBoard[destinationX][super.getCurrentY()])).isMoveTwiceLastTurn()) {
                       return -1;
                    }
                    tryingToEnPassant = true;
                } else {
                    return -1;
                }
            }
        } else if (Math.abs(yChange) > 2) {
            return -1;
        } else if (Math.abs(yChange) == 2) {
            if (!canMoveTwice) {
                return -1;
            }
            if (super.getColor().equals("White")){
                if (gameBoard[super.getCurrentX()][super.getCurrentY() - 1] != null) {
                    return -1;
                }
            } else {
                if (gameBoard[super.getCurrentX()][super.getCurrentY() + 1] != null) {
                    return -1;
                }
            }


            if (super.getColor().equals("White")) {
                if (getCurrentY() != 6) {
                    return -1;
                }
            } else {
                if (getCurrentY() != 1) {
                    return -1;
                }
            }

        }


        if (xChange != 0) {
            if (tryingToEnPassant) {
                if (!checkingStalemateOrCheckmate) {
                    gameBoard[destinationX][super.getCurrentY()].getCaptured(destinationX, super.getCurrentY());
                }
            } else { // Capturing other piece
                if (!checkingStalemateOrCheckmate) {
                    gameBoard[destinationX][destinationY].getCaptured(destinationX, destinationY);
                }
            }
        } else {
            if (gameBoard[destinationX][destinationY] != null) {
                return -1;
            }
        }


        gameBoard[getCurrentX()][getCurrentY()] = null;
        gameBoard[destinationX][destinationY] = this;
        Board.setGameBoard(gameBoard);
        if (super.getSameColorKing(super.getColor()).inInCheck()) {
            Board.setGameBoard(originalBoard);
            return -1;
        }

        if (Math.abs(yChange) == 2 && !checkingStalemateOrCheckmate) {
            canMoveTwice = false;
            moveTwiceLastTurn = true;
        }



        if (!checkingStalemateOrCheckmate) {
            super.setCurrentX(destinationX);
            super.setCurrentY(destinationY);
        } else {
            Board.setGameBoard(originalBoard);
        }

        if ((super.getCurrentY() == 0 || super.getCurrentY() == 7) && !checkingStalemateOrCheckmate) {
            pieceToPromote = this;
        }

        if (tryingToEnPassant && !checkingStalemateOrCheckmate) {
            return 1;
        }

        return 0;
    }

    public boolean isMoveTwiceLastTurn() {
        return moveTwiceLastTurn;
    }

    public static Piece getPieceToPromote() {
        return pieceToPromote;
    }

    public static void setPieceToPromote(Piece pieceToPromote) {
        Pawn.pieceToPromote = pieceToPromote;
    }

    // move method Calls on game control System to check if move is valid
    // check available Square method
    // Capture method: Called by move method
}
