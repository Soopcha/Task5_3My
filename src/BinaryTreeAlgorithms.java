import java.util.Stack;

public class BinaryTreeAlgorithms {
    public interface Visitor<T> {
        void visit(T value);
    }

    public static <T> void preorderTraversal(BinaryTree<T> tree, Visitor<T> visitor) {
        BinaryTree.TreeNode<T> root = tree.getRoot();
        if (root == null) {
            return;
        }

        Stack<BinaryTree.TreeNode<T>> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            BinaryTree.TreeNode<T> node = stack.pop();
            visitor.visit(node.getValue());

            if (node.getRight() != null) {
                stack.push(node.getRight());
            }

            if (node.getLeft() != null) {
                stack.push(node.getLeft());
            }
        }
    }

    public static <T> void inorderTraversal(BinaryTree<T> tree, Visitor<T> visitor) {
        BinaryTree.TreeNode<T> root = tree.getRoot();
        if (root == null) {
            return;
        }

        Stack<BinaryTree.TreeNode<T>> stack = new Stack<>();
        BinaryTree.TreeNode<T> current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.getLeft();
            }

            current = stack.pop();
            visitor.visit(current.getValue());
            current = current.getRight();
        }
    }

    public static <T> void postorderTraversal(BinaryTree<T> tree, Visitor<T> visitor) {
        BinaryTree.TreeNode<T> root = tree.getRoot();
        if (root == null) {
            return;
        }

        Stack<BinaryTree.TreeNode<T>> stack1 = new Stack<>();
        Stack<BinaryTree.TreeNode<T>> stack2 = new Stack<>();
        stack1.push(root);

        while (!stack1.isEmpty()) {
            BinaryTree.TreeNode<T> node = stack1.pop();
            stack2.push(node);

            if (node.getLeft() != null) {
                stack1.push(node.getLeft());
            }

            if (node.getRight() != null) {
                stack1.push(node.getRight());
            }
        }

        while (!stack2.isEmpty()) {
            BinaryTree.TreeNode<T> node = stack2.pop();
            visitor.visit(node.getValue());
        }
    }

    /**
     * Представление дерева в виде строки в скобочной нотации
     *
     * @param treeNode Узел поддерева, которое требуется представить в виже скобочной нотации
     * @return дерево в виде строки
     */
    public static <T> String toBracketStr(BinaryTree.TreeNode<T> treeNode) {
        // данный класс нужен только для того, чтобы "спрятать" его метод (c 2-мя параметрами)
        // (не надо так кроче, можно и отдельно просто прайват сделать метод)
        class Inner {
            void printTo(BinaryTree.TreeNode<T> node, StringBuilder sb) {
                if (node == null) {
                    return;
                }
                sb.append(node.getValue());
                if (node.getLeft() != null || node.getRight() != null) {
                    sb.append(" (");
                    printTo(node.getLeft(), sb);
                    if (node.getRight() != null) {
                        sb.append(", ");
                        printTo(node.getRight(), sb);
                    }
                    sb.append(")");
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        // класс приходится создавать, т.к. статические методы в таких класс не поддерживаются
        new Inner().printTo(treeNode, sb);

        return sb.toString();
    }



}
