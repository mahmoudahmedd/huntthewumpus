import view.WumpusViewImpl;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingUtilities.invokeLater;

public class PlayHuntTheWumusGame {
    public static void main(String[] args) {
        // Main function
        invokeLater(() -> {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("Hunt The view.Wumpus");
            f.setResizable(false);
            f.add(new WumpusViewImpl(), BorderLayout.CENTER);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
