import javax.swing.*;
import java.awt.*;

/**

 A basic GUI with components such as
 minimizing, resizing, exiting, etc.
 *
 @version JDK 21.0
 @author Christian Pineda
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

        // Test Statement
        System.out.println("Hello World!");
        System.out.println("dog");
        System.out.println("Is this thing working??");
    }
}