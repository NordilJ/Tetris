import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {
    public Game() {
        this.init();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame ex = new Game();
            ex.setVisible(true);
        });
    }

    private void init() {
        this.add(new Board());

        this.setResizable(false);
        this.pack();

        this.setSize(440, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Tetris !!!");
        this.setLocationRelativeTo(null);
    }
}