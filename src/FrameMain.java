import util.SwingUtils;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class FrameMain extends JFrame {
    private JPanel panelMain;
    private JTextField treeInfo;
    private JPanel paintPanel = null;
    private JPanel panelDrawTree;
    private JButton ChangeButton;
    private JTextArea areaPrintResult;
    private JSplitPane splitPanelMain;

    BinaryTree<Integer> tree = new SimpleBinaryTree<>();

    public FrameMain() {
        this.setTitle("Tree's Visits");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        splitPanelMain.setDividerLocation(0.5);
        splitPanelMain.setResizeWeight(1.0);
        splitPanelMain.setBorder(null);


        ChangeButton.addActionListener(actionEvent -> {
            SimpleBinaryTree<Integer> tree = new SimpleBinaryTree<>(Integer::parseInt);
            try {
                tree.fromBracketNotation(treeInfo.getText());  //происходит чтение информации о
                // бинарном дереве из текстового поля "treeInfo" и ее преобразование в бинарное дерево с помощью метода
                // "fromBracketNotation"
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            tree.myFunction();
            String answer = tree.toBracketStr();
            areaPrintResult.setText(answer);

            this.tree = tree;
        });

    }


    private void showSystemOut(Runnable action) {
        PrintStream oldOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(baos, true, "UTF-8"));

            action.run();

            areaPrintResult.setText(baos.toString("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            SwingUtils.showErrorMessageBox(e);
        }
        System.setOut(oldOut);
    }

}