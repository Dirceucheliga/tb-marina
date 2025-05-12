import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MorseCodeApp extends Application {
    // Nó da árvore de busca binária
    static class Node {
        char letter;
        Node left, right;
        Node(char letter) { this.letter = letter; }
    }

    static class MorseBST {
        private Node root;
        private Map<String, Character> decodeMap = new HashMap<>();
        private Map<Character, String> encodeMap = new HashMap<>();

        // Insere letra na árvore e registra nos mapas de encode/decode
        public void insert(char letter, String morseCode) {
            decodeMap.put(morseCode, letter);
            encodeMap.put(letter, morseCode);
            if (root == null) root = new Node(' ');
            Node current = root;
            for (char symbol : morseCode.toCharArray()) {
                if (symbol == '.') {
                    if (current.left == null) current.left = new Node(' ');
                    current = current.left;
                } else { // traço '-'
                    if (current.right == null) current.right = new Node(' ');
                    current = current.right;
                }
            }
            current.letter = letter;
        }

        // Decodifica Morse para texto
        public String decode(String input) {
            StringBuilder result = new StringBuilder();
            for (String token : input.trim().split(" ")) {
                Character ch = decodeMap.get(token);
                result.append(ch != null ? ch : '?');
            }
            return result.toString();
        }

        // Codifica texto para Morse
        public String encode(String input) {
            StringBuilder result = new StringBuilder();
            for (char c : input.toUpperCase().toCharArray()) {
                if (c == ' ') {
                    result.append("  "); // duas quebras para separar palavras
                } else {
                    String code = encodeMap.get(c);
                    result.append(code != null ? code : "?");
                    result.append(' ');
                }
            }
            return result.toString().trim();
        }

        // Altura da árvore para dimensionar o canvas
        public int getHeight() { return getHeight(root); }
        private int getHeight(Node node) {
            if (node == null) return 0;
            return 1 + Math.max(getHeight(node.left), getHeight(node.right));
        }

        // Renderiza a árvore JavaFX
        public void drawTree(Canvas canvas) {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            drawNode(gc, root, canvas.getWidth()/2, 40, canvas.getWidth()/4);
        }

        private void drawNode(GraphicsContext gc, Node node, double x, double y, double offset) {
            if (node == null) return;
            gc.strokeOval(x-15, y-15, 30, 30);
            gc.strokeText(node.letter==' '?"":String.valueOf(node.letter), x-5, y+5);
            if (node.left != null) {
                double nx = x - offset, ny = y + 100;
                gc.strokeLine(x, y+15, nx, ny-15);
                drawNode(gc, node.left, nx, ny, offset/2);
            }
            if (node.right != null) {
                double nx = x + offset, ny = y + 100;
                gc.strokeLine(x, y+15, nx, ny-15);
                drawNode(gc, node.right, nx, ny, offset/2);
            }
        }
    }

    private static MorseBST morseTree = new MorseBST();

    // Popula mapas e árvore
    private static void populateTree() {
        Map<Character,String> table = Map.ofEntries(
                Map.entry('A', ".-"), Map.entry('B', "-..."), Map.entry('C', "-.-."),
                Map.entry('D', "-.."), Map.entry('E', "."), Map.entry('F', "..-."),
                Map.entry('G', "--."), Map.entry('H', "...."), Map.entry('I', ".."),
                Map.entry('J', ".---"), Map.entry('K', "-.-"), Map.entry('L', ".-.."),
                Map.entry('M', "--"), Map.entry('N', "-."), Map.entry('O', "---"),
                Map.entry('P', ".--."), Map.entry('Q', "--.-"), Map.entry('R', ".-."),
                Map.entry('S', "..."), Map.entry('T', "-"), Map.entry('U', "..-"),
                Map.entry('V', "...-"), Map.entry('W', ".--"), Map.entry('X', "-..-"),
                Map.entry('Y', "-.--"), Map.entry('Z', "--.."),
                Map.entry('1', ".----"), Map.entry('2', "..---"), Map.entry('3', "...--"),
                Map.entry('4', "....-"), Map.entry('5', "....."), Map.entry('6', "-...."),
                Map.entry('7', "--..."), Map.entry('8', "---.."), Map.entry('9', "----."),
                Map.entry('0', "-----")
        );
        table.forEach((ch,code)-> morseTree.insert(ch, code));
    }

    public static void main(String[] args) {
        populateTree();
        Scanner scanner = new Scanner(System.in);

        // Decodificação
        System.out.print("Digite código Morse (símbolos separados por espaço): ");
        String morseInput = scanner.nextLine();
        System.out.println("Texto decodificado: " + morseTree.decode(morseInput));

        // Codificação
        System.out.print("Digite texto para codificar em Morse: ");
        String textInput = scanner.nextLine();
        System.out.println("Código Morse: " + morseTree.encode(textInput));

        launch(args);
    }


    @Override
    public void start(Stage stage) {
        stage.setTitle("Visualizador de Árvore BST - Código Morse");
        int h = morseTree.getHeight();
        double w = Math.pow(2, h)*20;
        double ch = 100 + h*100;

        Canvas canvas = new Canvas(w, ch);
        morseTree.drawTree(canvas);
        stage.setScene(new Scene(new Group(canvas), w, ch));
        stage.show();
    }
}
