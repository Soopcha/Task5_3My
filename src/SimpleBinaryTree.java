import java.util.Iterator;
import java.util.function.Function;
/*
3. Реализовать функцию, которая изменить двоичное дерево следующим образом:
Присоединит к самому правому листу в дереве в качестве правого потомка левое
поддерево корня дерева (левый потомок у корня дерева при этом надо убрать).

 */

/**
 * Реализация простейшего бинарного дерева (самое первое)
 */
public class SimpleBinaryTree<T> implements BinaryTree<T> {

    protected class SimpleTreeNode implements BinaryTree.TreeNode<T> {
        public T value;
        public SimpleTreeNode left;
        public SimpleTreeNode right;

        public SimpleTreeNode(T value, SimpleTreeNode left, SimpleTreeNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public SimpleTreeNode(T value) {
            this(value, null, null);
        }

        //переопределение методов, которые мы и хотели переопределить
        @Override
        public T getValue() {
            return value;
        }

        @Override
        public TreeNode<T> getLeft() {
            return left;
        }

        @Override
        public TreeNode<T> getRight() {
            return right;
        }
    }

    protected SimpleTreeNode root = null; //корень - вершина данного двоичного дерева

    protected Function<String, T> fromStrFunc;
    protected Function<T, String> toStrFunc;

    public SimpleBinaryTree(Function<String, T> fromStrFunc, Function<T, String> toStrFunc) {
        this.fromStrFunc = fromStrFunc;
        this.toStrFunc = toStrFunc;
    }/* принимает два аргумента: функцию fromStrFunc и функцию toStrFunc. (но реально это объекы, реализующие нужный нам метод
    функциональный интерфейс это, с одним методом)
Функция fromStrFunc принимает на вход строковое значение и возвращает объект типа T.
Эта функция используется для преобразования строковых значений в объекты типа T.
Функция toStrFunc принимает на вход объект типа T и возвращает его строковое представление.
Эта функция используется для преобразования объектов типа T в строковое представление.
Конструктор сохраняет эти две функции в полях класса, чтобы они могли быть использованы в дальнейшем при
работе с объектами типа T. Конструктор может быть использован для создания экземпляра класса SimpleBinaryTree с
заданными функциями преобразования значений.
    */

    public SimpleBinaryTree(Function<String, T> fromStrFunc) {
        this(fromStrFunc, Object::toString);
    }

    public SimpleBinaryTree() {
        this(null);
    }

    @Override
    public TreeNode<T> getRoot() {
        return root;
    }

    public void clear() {
        root = null;
    }

    public void myFunction(){ //Основная функция - ответ на таск
        // Находим самый правый лист в дереве
        SimpleTreeNode rightmostLeaf = findRightmostLeaf(root);

        // Находим корень дерева и его левого потомка
        SimpleTreeNode parent = root;
        SimpleTreeNode leftChild = root.left;

        // Присоединяем левое поддерево корня дерева к самому правому листу
        rightmostLeaf.right = leftChild;
        parent.left = null;

        // Возвращаем измененное дерево  мб это не нужно?
        //return root;
    }

    // Рекурсивная функция для поиска самого правого листа в дереве:
    private SimpleTreeNode findRightmostLeaf(SimpleTreeNode node) {
        if (node.getRight() != null) {
            return findRightmostLeaf(node.right);
        }
        return node;
    }

    private T fromStr(String s) throws Exception {
        s = s.trim();
        if (s.length() > 0 && s.charAt(0) == '"') {
            s = s.substring(1);
        }
        if (s.length() > 0 && s.charAt(s.length() - 1) == '"') {
            s = s.substring(0, s.length() - 1);
        }
        if (fromStrFunc == null) {
            throw new Exception("Не определена функция конвертации строки в T");
        }
        return fromStrFunc.apply(s);
    }

    private static class IndexWrapper {
        public int index = 0;
    }

    private void skipSpaces(String bracketStr, IndexWrapper iw) {
        while (iw.index < bracketStr.length() && Character.isWhitespace(bracketStr.charAt(iw.index))) {
            iw.index++;
        }
    }

    private T readValue(String bracketStr, IndexWrapper iw) throws Exception {
        // пропуcкаем возможные пробелы
        skipSpaces(bracketStr, iw);
        if (iw.index >= bracketStr.length()) {
            return null;
        }
        int from = iw.index;
        boolean quote = bracketStr.charAt(iw.index) == '"';
        if (quote) {
            iw.index++;
        }
        while (iw.index < bracketStr.length() && (
                quote && bracketStr.charAt(iw.index) != '"' ||
                        !quote && !Character.isWhitespace(bracketStr.charAt(iw.index)) && "(),".indexOf(bracketStr.charAt(iw.index)) < 0
        )) {
            iw.index++;
        }
        if (quote && bracketStr.charAt(iw.index) == '"') {
            iw.index++;
        }
        String valueStr = bracketStr.substring(from, iw.index);
        T value = fromStr(valueStr);
        skipSpaces(bracketStr, iw);
        return value;
    }

    private SimpleTreeNode fromBracketStr(String bracketStr, IndexWrapper iw) throws Exception {
        //чтение скобочного представления
        T parentValue = readValue(bracketStr, iw);
        SimpleTreeNode parentNode = new SimpleTreeNode(parentValue);
        if (bracketStr.charAt(iw.index) == '(') {
            iw.index++;
            skipSpaces(bracketStr, iw);
            if (bracketStr.charAt(iw.index) != ',') {
                parentNode.left = fromBracketStr(bracketStr, iw);
                skipSpaces(bracketStr, iw);
            }
            if (bracketStr.charAt(iw.index) == ',') {
                iw.index++;
                skipSpaces(bracketStr, iw);
            }
            if (bracketStr.charAt(iw.index) != ')') {
                parentNode.right = fromBracketStr(bracketStr, iw);
                skipSpaces(bracketStr, iw);
            }
            if (bracketStr.charAt(iw.index) != ')') {
                throw new Exception(String.format("Ожидалось ')' [%d]", iw.index));
            }
            iw.index++;
        }

        return parentNode;
    }

    public void fromBracketNotation(String bracketStr) throws Exception {
        IndexWrapper iw = new IndexWrapper();
        SimpleTreeNode root = fromBracketStr(bracketStr, iw);
        if (iw.index < bracketStr.length()) {
            throw new Exception(String.format("Ожидался конец строки [%d]", iw.index));
        }
        this.root = root;
    }
}
