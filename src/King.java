import javax.swing.*;
import java.util.Queue;

public class King extends Piece{
    Piece [][] gameBoard;
    boolean kingMoved;
    Piece pieceAttacking = null;
    int pieceAttackingCount = 0;

    public King(int currentX, int currentY, String color, String name) {
        super(currentX, currentY, 1, color, name, "King");
        gameBoard = Board.getGameBoard();
        kingMoved = false;
    }

    public int isMoveValid(int destinationX, int destinationY) {
        gameBoard = Board.getGameBoard();
        Piece [][] originalBoard = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                   originalBoard[i][j] = gameBoard[i][j];
            }
        }

        int xChange = destinationX - super.getCurrentX();
        int yChange = destinationY - super.getCurrentY();

        int originalX = super.getCurrentX();
        int originalY = super.getCurrentY();

        boolean tryingToCastle = false;
        boolean checkingStalemateOrCheckmate = Board.isCheckingStalemateOrCheckmate();

        if (destinationX < 0 || destinationX > 7 || destinationY < 0 || destinationY > 7) { // Piece moved out of board
            return -1;
        }

        for (int i = destinationX-1; i < destinationX +1; i++) {
            for (int j = destinationY-1; j < destinationY + 1; j++) {
                if (i >= 0 && i < 8 && j >= 0 && j < 8) {
                    if (gameBoard[i][j] != null) {
                        if (gameBoard [i][j] instanceof King && !gameBoard [i][j].getColor().equals(super.getColor())) {
                            return -1;
                        }
                    }
                }
            }
        }

        if (destinationX == super.getCurrentX() && destinationY == super.getCurrentY()) {
            return -1;
        }

        if (!kingMoved) {
            if ((getCurrentY() == 0 || getCurrentY() == 7) && (xChange == 2 || xChange == -2)) {
                tryingToCastle = true;
                if (xChange > 0) { // Castling kingSide
                    for (int i = super.getCurrentX()+1; i < 7; i++) {
                        if (gameBoard[i][getCurrentY()] != null) {
                            return -1;
                        } else {
                            gameBoard[getCurrentX()][getCurrentY()] = null;
                            gameBoard[i][getCurrentY()] = this;
                            Board.setGameBoard(gameBoard);
                            super.setCurrentX(destinationX);
                            super.setCurrentY(destinationY);
                            if (this.inInCheck()) {
                                Board.setGameBoard(originalBoard);
                                super.setCurrentX(originalX);
                                super.setCurrentY(originalY);
                                return -1;
                            } else if (checkingStalemateOrCheckmate) {
                                super.setCurrentX(originalX);
                                super.setCurrentY(originalY);
                            }
                            Board.setGameBoard(originalBoard);
                        }
                    }
                    if (gameBoard[7][getCurrentY()] != null && gameBoard[7][getCurrentY()] instanceof Rook) {
                        Rook tempRook = (Rook) gameBoard[7][getCurrentY()];
                        if (tempRook.isMovedFromOriginalPosition()) {
                            return -1;
                        } else {
                            gameBoard[6][getCurrentY()] = this;
                            gameBoard[5][getCurrentY()] = gameBoard[7][getCurrentY()];
                            gameBoard[7][getCurrentY()] = null;
                            gameBoard[4][getCurrentY()] = null;
                            ((Rook) (gameBoard[5][getCurrentY()])).movedFromOriginalPosition = true;
                            kingMoved = true;
                            return 1;
                        }
                    } else {
                        return  -1;
                    }

                } else { // Castling Queen side
                    for (int i = super.getCurrentX()-1; i > 1; i--) {
                        if (gameBoard[i][getCurrentY()] != null) {
                            return -1;
                        } else {
                            gameBoard[getCurrentX()][getCurrentY()] = null;
                            gameBoard[i][getCurrentY()] = this;
                            Board.setGameBoard(gameBoard);
                            super.setCurrentX(destinationX);
                            super.setCurrentY(destinationY);
                            if (this.inInCheck()) {
                                Board.setGameBoard(originalBoard);
                                super.setCurrentX(originalX);
                                super.setCurrentY(originalY);
                                return -1;
                            } else if (checkingStalemateOrCheckmate) {
                                super.setCurrentX(originalX);
                                super.setCurrentY(originalY);
                            }
                            Board.setGameBoard(originalBoard);
                        }
                    }
                    if (gameBoard[0][getCurrentY()] != null && gameBoard[0][getCurrentY()] instanceof Rook) {
                        Rook tempRook = (Rook) gameBoard[0][getCurrentY()];
                        if (tempRook.isMovedFromOriginalPosition()) {
                            return -1;
                        } else {
                            gameBoard[2][getCurrentY()] = this;
                            gameBoard[3][getCurrentY()] = gameBoard[7][getCurrentY()];
                            gameBoard[0][getCurrentY()] = null;
                            gameBoard[4][getCurrentY()] = null;
                            ((Rook) (gameBoard[3][getCurrentY()])).movedFromOriginalPosition = true;
                            kingMoved = true;
                            return 1;
                        }
                    } else {
                        return -1;
                    }
                }

            }
        }

        if (Math.abs(xChange) > 1 || Math.abs(yChange) > 1) {
            return -1;
        }

        if (gameBoard[destinationX][destinationY] != null) {
            if (gameBoard[destinationX][destinationY].getColor().equals(super.getColor())) {
                return -1;
            }
            if (!checkingStalemateOrCheckmate) {
                super.getCaptured(destinationX, destinationY); // Capture the piece
            }
        }
        gameBoard[getCurrentX()][getCurrentY()] = null;
        gameBoard[destinationX][destinationY] = this;
        Board.setGameBoard(gameBoard);

        super.setCurrentX(destinationX);
        super.setCurrentY(destinationY);
        if (inInCheck()) {
            Board.setGameBoard(originalBoard);
            super.setCurrentX(originalX);
            super.setCurrentY(originalY);
            return -1;
        } else if (checkingStalemateOrCheckmate) {
            super.setCurrentX(originalX);
            super.setCurrentY(originalY);
        }

        if (!checkingStalemateOrCheckmate) {
            super.setCurrentX(destinationX);
            super.setCurrentY(destinationY);
            kingMoved = true;
        } else {
            Board.setGameBoard(originalBoard);
        }
        return 0;
    }


    public boolean inInCheck() { //returns true if they are in the check
        gameBoard = Board.getGameBoard();
        pieceAttackingCount = 0;
        pieceAttacking = null;

        //Checking knight's presence, 걍 루프없이 체크하는것이 정신겅강에 좋겠다
        int [][] knightMove = {{-1,2}, {-2, -1}, {1,2}, {2,1}, {-1, -2}, {-2, 1}, {1, -2}, {2,-1}};
        for (int i = 0; i < 8; i++) {
            if (super.getCurrentX() + knightMove[i][0] < 8 && super.getCurrentX() + knightMove[i][0] >=0
                && super.getCurrentY() + knightMove[i][1] < 8 &&  super.getCurrentY() + knightMove[i][1] >=0
                && gameBoard[super.getCurrentX() + knightMove[i][0]][super.getCurrentY() + knightMove[i][1]]
                instanceof Knight &&
                !gameBoard[super.getCurrentX() + knightMove[i][0]][super.getCurrentY() +
                    knightMove[i][1]].getColor().equals(super.getColor())) {
                pieceAttackingCount++;
                pieceAttacking =  gameBoard[super.getCurrentX() + knightMove[i][0]][super.getCurrentY() + knightMove[i][1]];
            }
        }

        for (int i = getCurrentX() + 1; i < 8; i++) { // Checking right of the king
            if (gameBoard[i][super.getCurrentY()] != null) {
                if (!gameBoard[i][super.getCurrentY()].getColor().equals(super.getColor())) {
                    if (gameBoard[i][super.getCurrentY()] instanceof Rook || gameBoard[i][super.getCurrentY()] instanceof Queen) {
                        pieceAttackingCount ++;
                        pieceAttacking = gameBoard[i][super.getCurrentY()];
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        for (int i = super.getCurrentX()-1; i >=0; i--) { // Checking left of the king
            if (gameBoard[i][super.getCurrentY()] != null) {
                if (!gameBoard[i][super.getCurrentY()].getColor().equals(super.getColor())) {
                    if (gameBoard[i][super.getCurrentY()] instanceof Rook || gameBoard[i][super.getCurrentY()] instanceof Queen) {
                        pieceAttackingCount ++;
                        pieceAttacking = gameBoard[i][super.getCurrentY()];
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        for (int i = getCurrentY() + 1; i < 8; i++) { // Checking above the king
            if (gameBoard[super.getCurrentX()][i] != null) {
                if (!gameBoard[super.getCurrentX()][i].getColor().equals(super.getColor())) {
                    if (gameBoard[super.getCurrentX()][i] instanceof Rook || gameBoard[super.getCurrentX()][i] instanceof Queen) {
                        pieceAttackingCount ++;
                        pieceAttacking = gameBoard[super.getCurrentX()][i];
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        for (int i = getCurrentY() -1; i >= 0; i--) { // Checking below the king
            if (gameBoard[super.getCurrentX()][i] != null) {
                if (!gameBoard[super.getCurrentX()][i].getColor().equals(super.getColor())) {
                    if (gameBoard[super.getCurrentX()][i] instanceof Rook || gameBoard[super.getCurrentX()][i] instanceof Queen) {
                        pieceAttackingCount ++;
                        pieceAttacking = gameBoard[super.getCurrentX()][i];
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        int [][] diagonalChecker = {{1,1}, {1,-1}, {-1,1}, {-1,-1}};
        for (int i = 0; i < 4; i++) {
            int currentX = super.getCurrentX() + diagonalChecker[i][0];
            int currentY = super.getCurrentY() + diagonalChecker[i][1];
            int whileLoopCounter = 0;
            while(currentX < 8 && currentX >= 0 && currentY < 8 && currentY >= 0) {
                if (gameBoard[currentX][currentY] != null) {
                    if (gameBoard[currentX][currentY].getColor().equals(super.getColor())) {
                        break;
                    }
                    if (gameBoard[currentX][currentY] instanceof Bishop || gameBoard[currentX][currentY] instanceof Queen) {
                        pieceAttackingCount++;
                        pieceAttacking = gameBoard[currentX][currentY];
                    } else if (gameBoard[currentX][currentY] instanceof Pawn) {
                        if (whileLoopCounter == 0) {
                            if (gameBoard[currentX][currentY].getColor().equals("Black")) {
                                if (i == 0 || i == 2) {
                                    pieceAttackingCount ++;
                                    pieceAttacking = gameBoard[super.getCurrentX()][i];
                                } else {
                                    break;
                                }
                            } else {
                                if (i == 1 || i == 3) {
                                    pieceAttackingCount ++;
                                    pieceAttacking = gameBoard[super.getCurrentX()][i];
                                } else {
                                    break;
                                }
                            }
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                }

                currentX += diagonalChecker[i][0];
                currentY += diagonalChecker[i][1];
                whileLoopCounter ++;
            }
        }
        if (pieceAttackingCount == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isInCheckmate() { // return true if it is in checkmate// only call if piece is in check
        gameBoard = Board.getGameBoard();

        Piece [][] originalBoard = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                originalBoard[i][j] = gameBoard[i][j];
            }
        }

        int currentPieceAttackingCount = pieceAttackingCount;
        Board.setCheckingStalemateOrCheckmate(true);


        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <=1; j++) {
                if (isMoveValid(getCurrentX() + i, getCurrentY() + j) == 0) {
                    Board.setGameBoard(originalBoard);
                    Board.setCheckingStalemateOrCheckmate(false);
                    return false;
                }
            }
        }

        if (currentPieceAttackingCount > 1) {
            Board.setGameBoard(originalBoard);
            Board.setCheckingStalemateOrCheckmate(false);
            return true;
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gameBoard[i][j] != null && gameBoard[i][j].getColor().equals(super.getColor()) && !(gameBoard[i][j] instanceof King)) {
                    if (gameBoard[i][j] instanceof Pawn) {
                        if (((Pawn)(gameBoard[i][j])).isMoveValid(pieceAttacking.getCurrentX(), pieceAttacking.getCurrentY()) == 0){
                            Board.setGameBoard(originalBoard);
                            Board.setCheckingStalemateOrCheckmate(false);
                            return false;
                        }
                    } else if (gameBoard[i][j] instanceof Rook) {
                        if (((Rook)(gameBoard[i][j])).isMoveValid(pieceAttacking.getCurrentX(), pieceAttacking.getCurrentY())){
                            Board.setGameBoard(originalBoard);
                            Board.setCheckingStalemateOrCheckmate(false);
                            return false;
                        }
                    } else if (gameBoard[i][j] instanceof Knight) {
                        if (((Knight)(gameBoard[i][j])).isMoveValid(pieceAttacking.getCurrentX(), pieceAttacking.getCurrentY())){
                            Board.setGameBoard(originalBoard);
                            Board.setCheckingStalemateOrCheckmate(false);
                            return false;
                        }
                    } else if (gameBoard[i][j] instanceof Bishop) {
                        if (((Bishop) (gameBoard[i][j])).isMoveValid(pieceAttacking.getCurrentX(), pieceAttacking.getCurrentY())) {
                            Board.setGameBoard(originalBoard);
                            Board.setCheckingStalemateOrCheckmate(false);
                            return false;
                        }
                    } else if (gameBoard[i][j] instanceof Queen) {
                        if (((Queen)(gameBoard[i][j])).isMoveValid(pieceAttacking.getCurrentX(), pieceAttacking.getCurrentY())) {
                            Board.setGameBoard(originalBoard);
                            Board.setCheckingStalemateOrCheckmate(false);
                            return false;
                        }
                    }
                }
            }
        }
        Board.setGameBoard(originalBoard);
        Board.setCheckingStalemateOrCheckmate(false);
        return true;
    }

    public boolean isStalemate() { // If white King calls this class, it determines stalemate from white's perspective
        Board.setCheckingStalemateOrCheckmate(true);
        gameBoard = Board.getGameBoard();

        Piece [][] originalBoard = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                originalBoard[i][j] = gameBoard[i][j];
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gameBoard[i][j] != null && gameBoard[i][j].getColor().equals(super.getColor())) {
                    if (gameBoard[i][j] instanceof Pawn) {
                       if (super.getColor().equals("Black")) {
                           if (((Pawn)(gameBoard[i][j])).isMoveValid(i + 1, j) != -1 ||
                               ((Pawn)(gameBoard[i][j])).isMoveValid(i + 2, j) != -1 ||
                               ((Pawn)(gameBoard[i][j])).isMoveValid(i + 1, j + 1) != -1 ||
                               ((Pawn)(gameBoard[i][j])).isMoveValid(i + 1, j - 1) != -1) {
                               Board.setGameBoard(originalBoard);
                               Board.setCheckingStalemateOrCheckmate(false);
                               return false;
                           }
                       } else {
                           if (((Pawn)(gameBoard[i][j])).isMoveValid(i - 1, j) != -1 ||
                               ((Pawn)(gameBoard[i][j])).isMoveValid(i - 2, j) != -1 ||
                               ((Pawn)(gameBoard[i][j])).isMoveValid(i - 1, j + 1) != -1 ||
                               ((Pawn)(gameBoard[i][j])).isMoveValid(i - 1, j - 1) != -1) {
                               Board.setGameBoard(originalBoard);
                               Board.setCheckingStalemateOrCheckmate(false);
                               return false;
                           }
                       }
                    } else if (gameBoard[i][j] instanceof Rook) {
                        for (int k = 1; k <= 7; k++) {
                            if (((Rook)(gameBoard[i][j])).isMoveValid(i - k, j) ||
                                ((Rook)(gameBoard[i][j])).isMoveValid(i + k, j) ||
                                ((Rook)(gameBoard[i][j])).isMoveValid(i, j + k) ||
                                ((Rook)(gameBoard[i][j])).isMoveValid(i, j - k)) {
                                Board.setGameBoard(originalBoard);
                                Board.setCheckingStalemateOrCheckmate(false);
                                return false;
                            }
                        }
                    } else if (gameBoard[i][j] instanceof Knight) {
                        int [][] knightMove = {{-1,2}, {-2, -1}, {1,2}, {2,1}, {-1, -2}, {-2, -1}, {1, -2}, {2,-1}};
                        for (int k = 0; k < 8; k++) {
                            if (((Knight)(gameBoard[i][j])).isMoveValid(i + knightMove[k][0], j + knightMove[k][1])) {
                                Board.setGameBoard(originalBoard);
                                Board.setCheckingStalemateOrCheckmate(false);
                                return false;
                            }
                        }
                    } else if (gameBoard[i][j] instanceof Bishop) {
                        int [][] diagonalChecker = {{1,1}, {1,-1}, {-1,1}, {-1,-1}};
                        for (int k = 1; k <= 4; k++) {
                            for (int l = 0; l < 4; l++) {
                                if (((Bishop)(gameBoard[i][j])).isMoveValid(i + k * diagonalChecker[l][0], j + k * diagonalChecker[l][1])) {
                                    Board.setGameBoard(originalBoard);
                                    Board.setCheckingStalemateOrCheckmate(false);
                                    return false;
                                }
                            }
                        }
                    } else if (gameBoard[i][j] instanceof Queen) {
                        for (int k = 1; k <= 7; k++) {
                            if (((Queen)(gameBoard[i][j])).isMoveValid(i - k, j) ||
                                ((Queen)(gameBoard[i][j])).isMoveValid(i + k, j) ||
                                ((Queen)(gameBoard[i][j])).isMoveValid(i, j + k) ||
                                ((Queen)(gameBoard[i][j])).isMoveValid(i, j - k)) {
                                Board.setGameBoard(originalBoard);
                                Board.setCheckingStalemateOrCheckmate(false);
                                return false;
                            }
                        }

                        int [][] diagonalChecker = {{1,1}, {1,-1}, {-1,1}, {-1,-1}};
                        for (int k = 1; k <= 4; k++) {
                            for (int l = 0; l < 4; l++) {
                                if (((Queen)(gameBoard[i][j])).isMoveValid(i + k * diagonalChecker[l][0], j + k * diagonalChecker[l][1])) {
                                    Board.setGameBoard(originalBoard);
                                    Board.setCheckingStalemateOrCheckmate(false);
                                    return false;
                                }
                            }
                        }
                    } else if (gameBoard[i][j] instanceof King) {
                        for (int k = -1; k <= 1; k++) {
                            for (int l = -1; l <= 1; l++) {
                                if (isMoveValid(getCurrentX() + k, getCurrentY() + l) == 0) {
                                    Board.setGameBoard(originalBoard);
                                    Board.setCheckingStalemateOrCheckmate(false);
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        Board.setGameBoard(originalBoard);
        Board.setCheckingStalemateOrCheckmate(false);
        return true;
    }



    //Checkmate
    //Stalemate



}
