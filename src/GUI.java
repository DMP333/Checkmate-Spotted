import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI extends JFrame {
    Container cp = getContentPane();
    BoardPanel panel = new BoardPanel();
    JButton buttonTryingToMove =  null;
    Piece pieceTryingToMove = null;
    String currentTurn = "White";
    int xCoordinate = 0;
    int yCoordinate = 0;

    public GUI () {
        cp.setLayout(null);

        for (int i = 8; i >= 1; i--) { // Setting ranks
            JLabel rank = new JLabel(Integer.toString(i));
            rank.setLocation(60, 85 + (8-i)*100);
            rank.setSize(50, 50);
            cp.add(rank);
        }

        for (int i = 0; i < 8; i++) { // Setting Letters
            JLabel letter = new JLabel(Character.toString((char)(i+97)));
            letter.setLocation(143 + i*100, 875);
            letter.setSize(50, 50);
            cp.add(letter);
        }

        panel.setLocation(100,65);
        panel.setSize(800, 800);
        cp.add(panel);
        panel.setLayout(null);

        cp.revalidate();
        cp.repaint();



        Timer white = new Timer("White");
        Timer black = new Timer("Black");
        white.start();
        black.start();

        cp.setName("Best Chess Game");
        setSize(1000, 1000);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public class BoardPanel extends JLayeredPane{
        public void paintComponent (Graphics g) {
            super.paintComponent(g);
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    if ((i%2) == 1 && (j%2) == 1) {
                        g.setColor(new Color(138,237,204));
                    } else if (i%2 == 1){
                        g.setColor(new Color(78,56,122));
                    } else if (j%2 == 0) {
                        g.setColor(new Color(138,237,204));
                    } else {
                        g.setColor(new Color(78,56,122));
                    }
                    g.fillRect( 100 * (j-1), 100 * (i-1), 100, 100);
                }
            }
            cp.revalidate();
            cp.repaint();
        }
    }

    public JButton createPiece(String piece, String color, int xCoordinate, int yCoordinate) {
        if (piece.equals("Pawn")) {
            JButton button = new JButton();
            button.setLocation(100 + 100 * (xCoordinate-1), 100 + 100 * (yCoordinate-1));
            button.setSize(100,100);

            ImageIcon pawnImage = null;

            if (color.equals("White")) {
                pawnImage = new ImageIcon("whitepawn.png");
            } else {
                pawnImage = new ImageIcon("blackpawn.png");
            }
            return getjButton(button, pawnImage);
        } else if (piece.equals("Rook")) {
            JButton button = new JButton();
            button.setLocation(100 + 100 * (xCoordinate-1), 100 + 100 * (yCoordinate-1));
            button.setSize(100,100);

            ImageIcon pawnImage = null;

            if (color.equals("White")) {
                pawnImage = new ImageIcon("whiteRook.png");
            } else {
                pawnImage = new ImageIcon("blackRook.png");
            }
            return getjButton(button, pawnImage);


        } else if (piece.equals("Knight")) {
            JButton button = new JButton();
            button.setLocation(100 + 100 * (xCoordinate-1), 100 + 100 * (yCoordinate-1));
            button.setSize(100,100);

            ImageIcon pawnImage = null;

            if (color.equals("White")) {
                pawnImage = new ImageIcon("whiteKnight.png");
            } else {
                pawnImage = new ImageIcon("blackKnight.png");
            }
            return getjButton(button, pawnImage);

        } else if (piece.equals("Bishop")) {
            JButton button = new JButton();
            button.setLocation(100 + 100 * (xCoordinate-1), 100 + 100 * (yCoordinate-1));
            button.setSize(100,100);

            ImageIcon pawnImage = null;

            if (color.equals("White")) {
                pawnImage = new ImageIcon("whiteBishop.png");
            } else {
                pawnImage = new ImageIcon("blackBishop.png");
            }
            return getjButton(button, pawnImage);

        } else if (piece.equals("Queen")) {
            JButton button = new JButton();
            button.setLocation(100 + 100 * (xCoordinate-1), 100 + 100 * (yCoordinate-1));
            button.setSize(100,100);

            ImageIcon pawnImage = null;

            if (color.equals("White")) {
                pawnImage = new ImageIcon("whiteQueen.png");
            } else {
                pawnImage = new ImageIcon("blackQueen.png");
            }
            return getjButton(button, pawnImage);

        } else if (piece.equals("King")) {
            JButton button = new JButton();
            button.setLocation(100 + 100 * (xCoordinate-1), 100 + 100 * (yCoordinate-1));
            button.setSize(100,100);

            ImageIcon pawnImage = null;

            if (color.equals("White")) {
                pawnImage = new ImageIcon("whiteKing.png");
            } else {
                pawnImage = new ImageIcon("blackKing.png");
            }
            return getjButton(button, pawnImage);

        } else {
            JButton button = new JButton();
            button.setLocation(100 + 100 * (xCoordinate-1), 100 + 100 * (yCoordinate-1));
            button.setSize(100,100);

            ImageIcon pawnImage = null;
            pawnImage = new ImageIcon("emptyTile.png");
            getjButton(button, pawnImage);
            return button;
        }
    }

    private JButton getjButton(JButton button, ImageIcon pawnImage) {
        Image img = pawnImage.getImage().getScaledInstance(90,90, Image.SCALE_DEFAULT);
        button.setIcon(new ImageIcon(img));

        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(true);

        panel.add(button);
        button.addMouseListener(new MyPanelListner());

        cp.revalidate();
        cp.repaint();
        return button;
    }

    class MyPanelListner implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("Square clicked");
            Piece [][] gameBoard = Board.getGameBoard();
            JButton currentlySelectedButton = (JButton) e.getSource();
            xCoordinate = (currentlySelectedButton.getX() - 100) / 100 + 1;
            yCoordinate = (currentlySelectedButton.getY() - 100) / 100 + 1;

            Piece currentlySelectedPiece = gameBoard[xCoordinate][yCoordinate];
            System.out.println("Current xCoordinate: " + xCoordinate + " Current yCoordinate: " + yCoordinate);

            if (pieceTryingToMove == null) { // If no piece was selected previously
                if (currentlySelectedPiece == null) {
                    System.out.println("No Piece recognized");
                    return;
                }else if (!currentlySelectedPiece.getColor().equals(currentTurn)) {
                    System.out.println("Wrong color selected");
                    return;
                }
                System.out.println("new piece is selected");
                buttonTryingToMove = currentlySelectedButton;
                pieceTryingToMove = currentlySelectedPiece;
                return;
            } else {
                if (buttonTryingToMove == currentlySelectedButton) { // if same piece is selected (취소하는거니까)
                    System.out.println("Selected the same piece, selection of piece willl be canceled");
                    buttonTryingToMove= null;
                    pieceTryingToMove = null;
                } else {
                    if (pieceTryingToMove instanceof Pawn) {
                        int moveReturnValue = ((Pawn)pieceTryingToMove).isMoveValid(xCoordinate, yCoordinate);
                        if (moveReturnValue == 0){
                            buttonListenerHelper(currentlySelectedButton, currentlySelectedPiece);
                        } else if (moveReturnValue == 1) {
                            System.out.println("enpassant");

                            // Dealing with original Pawn square
                            Board.emptyButton((buttonTryingToMove.getX() - 100) / 100 + 1, (buttonTryingToMove.getY() - 100) / 100 + 1);

                            // Dealing with square where pawn is captured
                            panel.remove(Board.getButtonBoard()[xCoordinate][(buttonTryingToMove.getY() - 100) / 100 + 1]);
                            Board.emptyButton(xCoordinate, (buttonTryingToMove.getY() - 100) / 100 + 1);

                            //Dealing with square where pawn is heading
                            panel.remove(currentlySelectedButton);
                            buttonTryingToMove.setLocation(currentlySelectedButton.getX(), currentlySelectedButton.getY());
                            (Board.getButtonBoard())[xCoordinate][yCoordinate] = buttonTryingToMove;

                            if (pieceTryingToMove.getColor().equals("White")) {
                                currentTurn = "Black";
                            } else {
                                currentTurn = "White";
                            }
                            buttonTryingToMove = null;
                            pieceTryingToMove = null;

                        } else {
                            buttonTryingToMove = null;
                            pieceTryingToMove = null;
                        }
                    } else if (pieceTryingToMove instanceof Rook) {
                        if (((Rook)pieceTryingToMove).isMoveValid(xCoordinate, yCoordinate)){
                            buttonListenerHelper(currentlySelectedButton, currentlySelectedPiece);
                        } else {
                            buttonTryingToMove = null;
                            pieceTryingToMove = null;
                        }
                    } else if (pieceTryingToMove instanceof Bishop) {
                        if (((Bishop)pieceTryingToMove).isMoveValid(xCoordinate, yCoordinate)){
                            buttonListenerHelper(currentlySelectedButton, currentlySelectedPiece);
                        } else {
                            buttonTryingToMove = null;
                            pieceTryingToMove = null;
                        }
                    } else if (pieceTryingToMove instanceof Knight) {
                        if (((Knight)pieceTryingToMove).isMoveValid(xCoordinate, yCoordinate)){
                            buttonListenerHelper(currentlySelectedButton, currentlySelectedPiece);
                        } else {
                            buttonTryingToMove = null;
                            pieceTryingToMove = null;
                        }
                    }else if (pieceTryingToMove instanceof Queen) {
                        if (((Queen)pieceTryingToMove).isMoveValid(xCoordinate, yCoordinate)){
                            buttonListenerHelper(currentlySelectedButton, currentlySelectedPiece);
                        } else {
                            buttonTryingToMove = null;
                            pieceTryingToMove = null;
                        }
                    } else if (pieceTryingToMove instanceof King) {
                        int moveReturnValue = ((King)pieceTryingToMove).isMoveValid(xCoordinate, yCoordinate);
                        if (moveReturnValue == 0){
                            buttonListenerHelper(currentlySelectedButton, currentlySelectedPiece);
                        } else if (moveReturnValue == 1) {
                            System.out.println("Castle");
                            if (xCoordinate == 2) { //Queen Side Castling
                                // Dealing with original King Square
                                Board.emptyButton(4, yCoordinate);

                                // Dealing with new King Square
                                panel.remove(currentlySelectedButton);
                                buttonTryingToMove.setLocation(currentlySelectedButton.getX(), currentlySelectedButton.getY());
                                (Board.getButtonBoard())[2][yCoordinate] = buttonTryingToMove;

                                panel.remove((Board.getButtonBoard())[3][yCoordinate]);
                                Board.getButtonBoard()[0][yCoordinate].setLocation(currentlySelectedButton.getX() + 100 , currentlySelectedButton.getY());
                                Board.getButtonBoard()[3][yCoordinate] = Board.getButtonBoard()[0][yCoordinate];

                                //Dealing with original Rook Square
                                Board.emptyButton(0, yCoordinate);
                            } else {
                                Board.emptyButton(4, yCoordinate);

                                // Dealing with new King Square
                                panel.remove(currentlySelectedButton);
                                buttonTryingToMove.setLocation(currentlySelectedButton.getX(), currentlySelectedButton.getY());
                                (Board.getButtonBoard())[6][yCoordinate] = buttonTryingToMove;

                                //Dealing with new rook square
                                panel.remove((Board.getButtonBoard())[5][yCoordinate]);
                                Board.getButtonBoard()[7][yCoordinate].setLocation(currentlySelectedButton.getX() - 100 , currentlySelectedButton.getY());
                                Board.getButtonBoard()[5][yCoordinate] = Board.getButtonBoard()[0][yCoordinate];

                                //Dealing with original Rook Square
                                Board.emptyButton(7, yCoordinate);
                            }

                            if (pieceTryingToMove.getColor().equals("White")) {
                                currentTurn = "Black";
                            } else {
                                currentTurn = "White";
                            }
                            buttonTryingToMove = null;
                            pieceTryingToMove = null;

                        } else {
                            buttonTryingToMove = null;
                            pieceTryingToMove = null;
                        }
                    }
                    if (Board.getSameColorKing(currentTurn).inInCheck()) {
                        if (Board.getSameColorKing(currentTurn).isInCheckmate()){
                            System.out.println("Is checkmate");
                            if (currentTurn.equals("Black")) {
                                checkMateScreen("White");
                            } else {
                                checkMateScreen("Black");
                            }

                        }
                    } else if (Board.getSameColorKing(currentTurn).isStalemate()) {
                        System.out.println("Is stalemate");
                        staleMateScreen();
                    }
                }
            }



            cp.revalidate();
            cp.repaint();
        }

        private void buttonListenerHelper(JButton currentlySelectedButton, Piece currentlySelectedPiece) {
            Board.emptyButton((buttonTryingToMove.getX() - 100) / 100 + 1, (buttonTryingToMove.getY() - 100) / 100 + 1);

            buttonTryingToMove.setLocation(currentlySelectedButton.getX(), currentlySelectedButton.getY());
            panel.remove(currentlySelectedButton);
            (Board.getButtonBoard())[xCoordinate][yCoordinate] = buttonTryingToMove;

            if (Pawn.getPieceToPromote() != null) {
                promotionScreen(pieceTryingToMove.getColor());
            }

            if (pieceTryingToMove.getColor().equals("White")) {
                currentTurn = "Black";
            } else {
                currentTurn = "White";
            }
            buttonTryingToMove = null;
            pieceTryingToMove = null;
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public void movePiece(JLabel label, int xCoordinate, int yCoordinate) {
        label.setLocation(107 + 100 * (xCoordinate-1), 100 + 100 * (yCoordinate-1));
        cp.revalidate();
        cp.repaint();
    }

    public void removePiece(JButton button) {
        cp.remove(button);
        cp.revalidate();
        cp.repaint();
    }

    public void checkMateScreen(String winner) {
        JLabel winningScreen = new JLabel("<html>" + "<B>" + winner + " won by checkmate" + "<B>" + "<html>");
        winningScreen.setBackground(Color.white);
        winningScreen.setLocation(250,300);
        winningScreen.setSize(800,100);
        winningScreen.setForeground(Color.WHITE);

        winningScreen.setFont(new Font("Serif", Font.PLAIN, 40));

        winningScreen.setOpaque(false);
        winningScreen.setVisible(true);

        panel.add(winningScreen);
        panel.moveToFront(winningScreen);

        cp.revalidate();
        cp.repaint();
    }

    public void timeoutScreen(String winner) {
        JLabel winningScreen = new JLabel("<html>" + "<B>" + winner + " won by timeout" + "<B>" + "<html>");
        winningScreen.setBackground(Color.white);
        winningScreen.setLocation(250,300);
        winningScreen.setSize(800,100);
        winningScreen.setForeground(Color.WHITE);

        winningScreen.setFont(new Font("Serif", Font.PLAIN, 40));

        winningScreen.setOpaque(false);
        winningScreen.setVisible(true);

        panel.add(winningScreen);
        panel.moveToFront(winningScreen);

        cp.revalidate();
        cp.repaint();
    }

    public void staleMateScreen() {
        JLabel winningScreen = new JLabel("<html>" + "<B>" + "Stalemate" + "<B>" + "<html>");
        winningScreen.setBackground(Color.white);
        winningScreen.setLocation(250,300);
        winningScreen.setSize(800,100);
        winningScreen.setForeground(Color.WHITE);

        winningScreen.setFont(new Font("Serif", Font.PLAIN, 40));

        winningScreen.setOpaque(false);
        winningScreen.setVisible(true);

        panel.add(winningScreen);
        panel.moveToFront(winningScreen);

        cp.revalidate();
        cp.repaint();
    }

    public void promotionScreen(String color) {
        if (color.equals("White")) {
            JButton whiteQueen = new JButton();
            ImageIcon wQ = new ImageIcon("whiteQueen.png");
            Image img = wQ.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
            whiteQueen.setIcon(new ImageIcon(img));
            whiteQueen.setOpaque(false);
            whiteQueen.setBorderPainted(false);
            whiteQueen.setContentAreaFilled(false);
            whiteQueen.setFocusPainted(true);
            whiteQueen.setLocation(100,15);
            whiteQueen.setSize(50,50);
            whiteQueen.addMouseListener(new promotionListener());
            cp.add(whiteQueen);

            JButton whiteRook = new JButton();
            ImageIcon wR = new ImageIcon("whiteRook.png");
            img = wR.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
            whiteRook.setIcon(new ImageIcon(img));
            whiteRook.setOpaque(false);
            whiteRook.setBorderPainted(false);
            whiteRook.setContentAreaFilled(false);
            whiteRook.setFocusPainted(true);
            whiteRook.setLocation(150,15);
            whiteRook.setSize(50,50);
            whiteRook.addMouseListener(new promotionListener());
            cp.add(whiteRook);

            JButton whiteBishop = new JButton();
            ImageIcon wB = new ImageIcon("whiteBishop.png");
            img = wB.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
            whiteBishop.setIcon(new ImageIcon(img));
            whiteBishop.setOpaque(false);
            whiteBishop.setBorderPainted(false);
            whiteBishop.setContentAreaFilled(false);
            whiteBishop.setFocusPainted(true);
            whiteBishop.setLocation(200,15);
            whiteBishop.setSize(50,50);
            whiteBishop.addMouseListener(new promotionListener());
            cp.add(whiteBishop);

            JButton whiteKnight = new JButton();
            ImageIcon wK = new ImageIcon("whiteKnight.png");
            img = wK.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
            whiteKnight.setIcon(new ImageIcon(img));
            whiteKnight.setOpaque(false);
            whiteKnight.setBorderPainted(false);
            whiteKnight.setContentAreaFilled(false);
            whiteKnight.setFocusPainted(true);
            whiteKnight.setLocation(250,15);
            whiteKnight.setSize(50,50);
            whiteKnight.addMouseListener(new promotionListener());
            cp.add(whiteKnight);
        } else {
            JButton blackQueen = new JButton();
            ImageIcon bQ = new ImageIcon("blackQueen.png");
            Image img = bQ.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
            blackQueen.setIcon(new ImageIcon(img));
            blackQueen.setOpaque(false);
            blackQueen.setBorderPainted(false);
            blackQueen.setContentAreaFilled(false);
            blackQueen.setFocusPainted(true);
            blackQueen.setLocation(700,15);
            blackQueen.setSize(50,50);
            blackQueen.addMouseListener(new promotionListener());
            cp.add(blackQueen);

            JButton blackRook = new JButton();
            ImageIcon bR = new ImageIcon("blackRook.png");
            img = bR.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
            blackRook.setIcon(new ImageIcon(img));
            blackRook.setOpaque(false);
            blackRook.setBorderPainted(false);
            blackRook.setContentAreaFilled(false);
            blackRook.setFocusPainted(true);
            blackRook.setLocation(750,15);
            blackRook.setSize(50,50);
            blackRook.addMouseListener(new promotionListener());
            cp.add(blackRook);

            JButton blackBishop = new JButton();
            ImageIcon bB = new ImageIcon("blackBishop.png");
            img = bB.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
            blackBishop.setIcon(new ImageIcon(img));
            blackBishop.setOpaque(false);
            blackBishop.setBorderPainted(false);
            blackBishop.setContentAreaFilled(false);
            blackBishop.setFocusPainted(true);
            blackBishop.setLocation(800,15);
            blackBishop.setSize(50,50);
            blackBishop.addMouseListener(new promotionListener());
            cp.add(blackBishop);

            JButton blackKnight = new JButton();
            ImageIcon bK = new ImageIcon("blackKnight.png");
            img = bK.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
            blackKnight.setIcon(new ImageIcon(img));
            blackKnight.setOpaque(false);
            blackKnight.setBorderPainted(false);
            blackKnight.setContentAreaFilled(false);
            blackKnight.setFocusPainted(true);
            blackKnight.setLocation(850,15);
            blackKnight.setSize(50,50);
            blackKnight.addMouseListener(new promotionListener());
            cp.add(blackKnight);
        }
    }

    class promotionListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            Piece [][] gameBoard = Board.getGameBoard();
            JButton currentlySelectedButton = (JButton) e.getSource();
            Piece pieceTryingToPromote = Pawn.getPieceToPromote();
            int currentX = pieceTryingToPromote.getCurrentX();
            int currentY = pieceTryingToPromote.getCurrentY();

            panel.remove(Board.getButtonBoard()[currentX][currentY]);

            if (currentlySelectedButton.getX() == 100) {
                Board.getButtonBoard()[currentX][currentY] = Board.sharingBoard.createPiece("Queen", "White", currentX, currentY);
                gameBoard[currentX][currentY] = new Queen(currentX, currentY, "White", "W" + Character.toString((char) 97) + "Queen");
            } else if (currentlySelectedButton.getX() == 150) {
                Board.getButtonBoard()[currentX][currentY] = Board.sharingBoard.createPiece("Rook", "White", currentX, currentY);
                gameBoard[currentX][currentY] = new Rook(currentX, currentY, "White", "W" + Character.toString((char) 97) + "Rook");
            } else if (currentlySelectedButton.getX() == 200) {
                Board.getButtonBoard()[currentX][currentY] = Board.sharingBoard.createPiece("Bishop", "White", currentX, currentY);
                gameBoard[currentX][currentY] = new Bishop(currentX, currentY, "White", "W" + Character.toString((char) 97) + "Bishop");
            } else if (currentlySelectedButton.getX() == 250) {
                Board.getButtonBoard()[currentX][currentY] = Board.sharingBoard.createPiece("Knight", "White", currentX, currentY);
                gameBoard[currentX][currentY] = new Knight(currentX, currentY, "White", "W" + Character.toString((char) 97) + "Knight");
            } else if (currentlySelectedButton.getX() == 700) {
                Board.getButtonBoard()[currentX][currentY] = Board.sharingBoard.createPiece("Queen", "Black", currentX, currentY);
                gameBoard[currentX][currentY] = new Queen(currentX, currentY, "Black", "B" + Character.toString((char) 97) + "Queen");
            } else if (currentlySelectedButton.getX() == 750) {
                Board.getButtonBoard()[currentX][currentY] = Board.sharingBoard.createPiece("Rook", "Black", currentX, currentY);
                gameBoard[currentX][currentY] = new Rook(currentX, currentY, "Black", "B" + Character.toString((char) 97) + "Rook");
            } else if (currentlySelectedButton.getX() == 800) {
                Board.getButtonBoard()[currentX][currentY] = Board.sharingBoard.createPiece("Bishop", "Black", currentX, currentY);
                gameBoard[currentX][currentY] = new Bishop(currentX, currentY, "Black", "B" + Character.toString((char) 97) + "Bishop");
            } else if (currentlySelectedButton.getX() == 180) {
                Board.getButtonBoard()[currentX][currentY] = Board.sharingBoard.createPiece("Knight", "Black", currentX, currentY);
                gameBoard[currentX][currentY] = new Knight(currentX, currentY, "Black", "B" + Character.toString((char) 97) + "Knight");
            }

            Board.setGameBoard(gameBoard);
            if (pieceTryingToPromote.getColor().equals("White")) {
                cp.remove(cp.getComponentAt(100, 15));
                cp.remove(cp.getComponentAt(150, 15));
                cp.remove(cp.getComponentAt(200, 15));
                cp.remove(cp.getComponentAt(250, 15));
            } else {
                cp.remove(cp.getComponentAt(700, 15));
                cp.remove(cp.getComponentAt(750, 15));
                cp.remove(cp.getComponentAt(800, 15));
                cp.remove(cp.getComponentAt(850, 15));
            }
            cp.revalidate();
            cp.repaint();
            Pawn.setPieceToPromote(null);
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    class Timer extends Thread implements Runnable {
        private String color;

        public Timer(String color) {
            this.color = color;
        }


        public void run() {
            int timeElapsed = 60;
            JLabel timer = new JLabel(Integer.toString(timeElapsed), SwingConstants.CENTER);
            timer.setOpaque(false);
            timer.setVisible(true);
            if (color.equals("White")) {
                timer.setLocation(350, 15);
                timer.setSize(100, 50);
                timer.setForeground(Color.BLACK);
                timer.setFont(new Font("Serif", Font.PLAIN, 40));
                timer.setBackground(Color.WHITE);
                timer.setOpaque(true);
            } else {
                timer.setLocation(550, 15);
                timer.setSize(100, 50);
                timer.setForeground(Color.WHITE);
                timer.setFont(new Font("Serif", Font.PLAIN, 40));
                timer.setBackground(Color.BLACK);
                timer.setOpaque(true);
            }

            cp.add(timer);
            cp.revalidate();
            cp.repaint();

            while (true) {
                if (color.equals(currentTurn)) {
                    if (timeElapsed == 0) {
                        if (currentTurn.equals("Black")) {
                            timeoutScreen("White");
                        } else {
                            timeoutScreen("Black");
                        }
                        break;
                    }
                    timeElapsed --;
                    timer.setText(Integer.toString(timeElapsed));
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                cp.revalidate();
                cp.repaint();
            }
        }
    }

    public void restart() {
        
    }


    public static void main(String[] args) {
        Board temp = new Board();
        Board.resetPiece();




    }
}
