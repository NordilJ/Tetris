import figures.*;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.*;

public class Board extends JPanel implements ActionListener {
    public static final int BOARD_WIDTH = 440;
    public static final int BOARD_HEIGHT = 700;

    public static final int IN_GAME_SCORE = 10;
    public List<Piece> pieces;
    public int points;
    public int delay;
    public boolean isManualMove = false;

    private Timer timer;

    private boolean inGame = true;

    Figure currentFigure;
    Figure nextFigure;

    public Board() {
        this.init();
    }

    private void init() {
        this.addKeyListener(new TAdapter(this));
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        this.pieces = new ArrayList<>();
        this.points = 0;
        this.delay = 500;
        this.initGame();
    }

    private void initGame() {
        this.currentFigure = this.getRandomFigure();
        this.nextFigure = this.getRandomFigure();
        timer = new Timer(delay, this);
        timer.start();
    }

    private Figure getRandomFigure() {
        Random rng = new Random();
        int figureNumber = rng.nextInt(7);
        switch (figureNumber) {
            case 1:
                return new ReverseL();
            case 2:
                return new L();
            case 3:
                return new S();
            case 4:
                return new ReverseS();
            case 5:
                return new Square();
            case 6:
                return new T();
            default:
                return new Stick();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!this.isManualMove) {
            this.move(Direction.DOWN);
        }

        this.doDrawing(g);
        this.isManualMove = false;
    }

    private void move(Direction direction) {
        if (currentFigure.canMove(direction, this.pieces)) {
            currentFigure.move(direction);
        } else {
            if (direction == Direction.DOWN) {
                this.pieces.addAll(currentFigure.pieces);
                clearFilledRows();
                this.currentFigure = this.nextFigure;
                this.nextFigure = this.getRandomFigure();
                if (checkGameOver()) {
                    this.inGame = false;
                }
            }
        }
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 24);

        g.setColor(Color.BLACK);
        g.setFont(small);
        g.drawString(msg, BOARD_WIDTH / 2, BOARD_HEIGHT / 2);

        String scoreMsg = "Score: " + this.points;
        g.drawString(scoreMsg, BOARD_WIDTH / 2, (BOARD_HEIGHT / 2) + 20);
    }

    private boolean checkGameOver() {
        return this.currentFigure.pieces.stream().anyMatch(piece -> this.pieces.stream()
                .anyMatch(boardPiece -> boardPiece.position.y == piece.position.y
                        && boardPiece.position.x == piece.position.x));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
    }

    private void doDrawing(Graphics g) {
        if (this.inGame) {
            this.currentFigure.draw(g);
            this.nextFigure.drawNext(g);

            for (Piece piece : this.pieces) {
                g.setColor(Color.WHITE);
                g.fillRoundRect(piece.position.x, piece.position.y, Piece.PEACE_SIZE, Piece.PEACE_SIZE, Piece.ARC, Piece.ARC);
                g.setColor(Color.BLACK);
                g.drawRoundRect(piece.position.x, piece.position.y, Piece.PEACE_SIZE, Piece.PEACE_SIZE, Piece.ARC, Piece.ARC);
            }

            g.setColor(Color.BLACK);
            g.drawRect(10, 10, 190, 70);
            g.drawRect(220, 10, 190, 70);
            g.drawRect(10, 90, 400, 560);


            g.setFont(g.getFont().deriveFont(36f));
            g.drawString(String.valueOf(this.points), 300, 60);

            Toolkit.getDefaultToolkit().sync();
        } else {
            timer.stop();
            this.gameOver(g);
        }
    }

    private class TAdapter extends KeyAdapter {
        private Board panel;

        public TAdapter(Board panel) {
            this.panel = panel;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            panel.isManualMove = true;
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                move(Direction.LEFT);
                panel.repaint();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                move(Direction.RIGHT);
                panel.repaint();
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                move(Direction.DOWN);
                panel.repaint();
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (currentFigure.canRotate(panel.pieces)) {
                    currentFigure.rotate();
                    panel.repaint();
                } else {
                    panel.isManualMove = false;
                }
            }
        }
    }

    private void clearFilledRows() {
        Map<Integer, List<Piece>> filledRows = new TreeMap<>();
        for (Piece piece : this.pieces) {
            if (!filledRows.containsKey(piece.position.y)) {
                List<Piece> newPieces = new ArrayList<>();
                newPieces.add(piece);
                filledRows.put(piece.position.y, newPieces);
            } else {
                List<Piece> newPieces = filledRows.get(piece.position.y);
                newPieces.add(piece);
                filledRows.put(piece.position.y, newPieces);
            }
        }

        if (filledRows.values().stream().anyMatch(row -> row.size() == 13)) {
            List<Piece> newPieces = new ArrayList<>();
            int rowFallCount = 0;
            filledRows = ((TreeMap<Integer, List<Piece>>) filledRows).descendingMap();
            for (Integer key : filledRows.keySet()) {
                List<Piece> rowPieces = filledRows.get(key);
                if (rowPieces.size() == 13) {
                    points += IN_GAME_SCORE;
                    rowFallCount++;
                } else {
                    for (Piece rowPiece : rowPieces) {
                        rowPiece.position.y += rowFallCount * Piece.PEACE_SIZE;
                    }

                    newPieces.addAll(rowPieces);
                }
            }

            this.pieces = newPieces;
        }
    }

}