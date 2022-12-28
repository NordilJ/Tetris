import figures.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;

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
        this.delay = 600;
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
        int figureNumber = 7; //rng.nextInt(7);
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

        if (!this.isManualMove){
            this.move(Direction.DOWN);
        }

        this.doDrawing(g);
        this.isManualMove = false;
    }

    private void move(Direction direction) {
        if (currentFigure.canMove(direction, this.pieces)) {
            currentFigure.move(direction);
        } else {
            if (direction == Direction.DOWN){
                this.pieces.addAll(currentFigure.pieces);
                // check for game end - later
                this.currentFigure = this.nextFigure;
                this.nextFigure = this.getRandomFigure();
            }
        }
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

            Toolkit.getDefaultToolkit().sync();
        } else {
            timer.stop();
//            this.gameOver(g);
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
            }else if (e.getKeyCode() == KeyEvent.VK_UP) {
                if(currentFigure.canRotate(panel.pieces)){
                    currentFigure.rotate();
                    panel.repaint();
                } else {
                    panel.isManualMove = false;
                }
            }
        }
    }
}