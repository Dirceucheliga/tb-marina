import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MorseBST {
    static class Node {
        char letter;
        Node left, right;
        Node(char letter) { this.letter = letter; }
    }

    private Node root;

    public void buildTree() {
        root = new Node(' ');
        char[] letters = {
                'A','B','C','D','E','F','G','H','I','J','K','L','M',
                'N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
        };
        String[] codes = {
                ".-","-...","-.-.","-..",".","..-.","--.","....","..",
                ".---","-.-",".-..","--","-.","---",".--.","--.-",".-.",
                "...","-","..-","...-",".--","-..-","-.--","--.."
        };
        for (int i = 0; i < letters.length; i++) {
            insert(letters[i], codes[i]);
        }
        char[] digits = { '0','1','2','3','4','5','6','7','8','9' };
        String[] dCodes = {
                "-----",".----","..---","...--","....-",".....",
                "-....","--...","---..","----."
        };
        for (int i = 0; i < digits.length; i++) {
            insert(digits[i], dCodes[i]);
        }
    }

    private void insert(char letter, String code) {
        Node cur = root;
        for (char c : code.toCharArray()) {
            if (c == '.') {
                if (cur.left == null) cur.left = new Node(' ');
                cur = cur.left;
            } else {
                if (cur.right == null) cur.right = new Node(' ');
                cur = cur.right;
            }
        }
        cur.letter = letter;
    }

    public char decodeToken(String code) {
        Node cur = root;
        for (char c : code.toCharArray()) {
            if (cur == null) return '?';
            cur = (c == '.') ? cur.left : cur.right;
        }
        return (cur != null) ? cur.letter : '?';
    }

    public String encodeChar(char letter) {
        StringBuilder path = new StringBuilder();
        return findPath(root, letter, path) ? path.toString() : "?";
    }

    private boolean findPath(Node node, char target, StringBuilder path) {
        if (node == null) return false;
        if (node.letter == target) return true;
        path.append('.');
        if (findPath(node.left, target, path)) return true;
        path.deleteCharAt(path.length() - 1);
        path.append('-');
        if (findPath(node.right, target, path)) return true;
        path.deleteCharAt(path.length() - 1);
        return false;
    }

    public int getHeight() {
        return height(root);
    }

    private int height(Node n) {
        return (n == null) ? 0 : 1 + Math.max(height(n.left), height(n.right));
    }

    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        drawNode(gc, root, canvas.getWidth() / 2, 40, canvas.getWidth() / 4);
    }

    private void drawNode(GraphicsContext gc, Node node,
                          double x, double y, double offset) {
        if (node == null) return;
        gc.strokeOval(x - 15, y - 15, 30, 30);
        if (node.letter != ' ') gc.strokeText(String.valueOf(node.letter), x - 5, y + 5);
        if (node.left != null) {
            double nx = x - offset, ny = y + 100;
            gc.strokeLine(x, y + 15, nx, ny - 15);
            drawNode(gc, node.left, nx, ny, offset / 2);
        }
        if (node.right != null) {
            double nx = x + offset, ny = y + 100;
            gc.strokeLine(x, y + 15, nx, ny - 15);
            drawNode(gc, node.right, nx, ny, offset / 2);
        }
    }
}
