import javax.swing.*;
import java.awt.*;

/**
 * A basic GUI with an About screen and a screen to
 * enter in your first name and email address.
 *
 * @version JDK 21.0
 * @author Jordan Festin, Bill Lactaoen, Christian Pineda, Ian Salsich
 */

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Basic GUI v0.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.getContentPane().setBackground(new Color(64, 224, 208));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(true);

        frame.add(createMainPanel());
    }

    public static JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        JButton setupButton = new JButton("Setup Account");
        setupButton.setPreferredSize(new Dimension(120, 50));
        JButton aboutButton = new JButton("About");
        aboutButton.setPreferredSize(new Dimension(120, 50));

        mainPanel.add(setupButton);
        mainPanel.add(aboutButton);

        return mainPanel;
    }
}