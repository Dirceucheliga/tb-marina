import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Map;

class MorseBST {
    private static class Node { char letter; Node left, right; Node(char l){ letter=l; } }
    private Node root;

    public void buildTree() {
        root = new Node(' ');
        for (Map.Entry<Character, String> e : MorseData.ENCODE_MAP.entrySet()) {
            insert(e.getKey(), e.getValue());
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

    public int getHeight() { return height(root); }
    private int height(Node n) { return n == null ? 0 : 1 + Math.max(height(n.left), height(n.right)); }

    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        drawNode(gc, root, canvas.getWidth()/2, 40, canvas.getWidth()/4);
    }

    private void drawNode(GraphicsContext gc, Node n, double x, double y, double off) {
        if (n == null) return;
        gc.strokeOval(x-15, y-15, 30, 30);
        if (n.letter != ' ') gc.strokeText(String.valueOf(n.letter), x-5, y+5);
        if (n.left != null) {
            double nx = x-off, ny = y+100;
            gc.strokeLine(x, y+15, nx, ny-15);
            drawNode(gc, n.left, nx, ny, off/2);
        }
        if (n.right != null) {
            double nx = x+off, ny = y+100;
            gc.strokeLine(x, y+15, nx, ny-15);
            drawNode(gc, n.right, nx, ny, off/2);
        }
    }
}
