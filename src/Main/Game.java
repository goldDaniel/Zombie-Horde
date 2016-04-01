
package Main;

import javax.swing.JFrame;

/**
 *
 * @author vangradomor
 */
public class Game {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GamePanel panel;
        JFrame frame = new JFrame();

        /*sets frame defaults************************************/
        frame.setVisible(true);
        frame.setSize(1024, 768);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(panel = new GamePanel());
        /*******************************************************/
        
        /*allows keylisteners in panel*/
        panel.setFocusable(true);
        panel.requestFocus();
        /*****************************/
    }
}
