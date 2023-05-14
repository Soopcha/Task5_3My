import util.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.Stack;

import static java.awt.Frame.MAXIMIZED_BOTH;

public class Main {
    public static void main(String[] args) throws Exception {
//        Locale.setDefault(Locale.ROOT);
//        //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        SwingUtils.setDefaultFont("Times New Roman", 18);
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FrameMain().setVisible(true);
//            }
//        });

        Locale.setDefault(Locale.ROOT);

        //SwingUtils.setLookAndFeelByName("Windows");
        //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //SwingUtils.setDefaultFont(null, 20);
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        SwingUtils.setDefaultFont("Arial", 20);

        EventQueue.invokeLater(() -> {
            try {
                JFrame frameMain = new FrameMain();
                frameMain.setVisible(true);
                frameMain.setExtendedState(MAXIMIZED_BOTH);
            } catch (Exception ex) {
                SwingUtils.showErrorMessageBox(ex);
            }
        });


    }
}