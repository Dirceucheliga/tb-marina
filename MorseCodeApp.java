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
    // Árvore de busca binária para código Morse
    static class Node {
        char letter;
        Node left, right;
        Node(char letter) {
            this.letter = letter;
        }
    }

    static class MorseBST {
        private Node root;

        // Mapeamento direto para acelerar decodificação
        private Map<String, Character> decodeMap = new HashMap<>();

        // Insere uma letra seguindo pontos (.) à esquerda e traços (-) à direita
        public void insert(char letter, String morseCode) {
            // Prepara mapa de decodificação
            decodeMap.put(morseCode, letter);

            if (root == null) {
                root = new Node(' ');
            }
            Node current = root;
            for (int i = 0; i < morseCode.length(); i++) {
                char symbol = morseCode.charAt(i);
                if (symbol == '.') {
                    if (current.left == null) current.left = new Node(' ');
                    current = current.left;
                } else if (symbol == '-') {
                    if (current.right == null) current.right = new Node(' ');
                    current = current.right;
                }
            }
            current.letter = letter;
        }

        // Decodifica uma sequência de código Morse (letras separadas por espaço)
        public String decode(String input) {
            StringBuilder result = new StringBuilder();
            for (String token : input.trim().split(" ")) {
                Character ch = decodeMap.get(token);
                result.append(ch != null ? ch : '?');
            }
            return result.toString();
        }

        // Altura da árvore (níveis)
        public int getHeight() {
            return getHeight(root);
        }
        private int getHeight(Node node) {
            if (node == null) return 0;
            return 1 + Math.max(getHeight(node.left), getHeight(node.right));
        }

        // Desenha a árvore em um Canvas JavaFX
        public void drawTree(Canvas canvas) {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            drawNode(gc, root, canvas.getWidth() / 2, 40, canvas.getWidth() / 4);
        }

        private void drawNode(GraphicsContext gc, Node node,
                              double x, double y, double xOffset) {
            if (node == null) return;
            // Nó
            gc.strokeOval(x - 15, y - 15, 30, 30);
            gc.strokeText(node.letter == ' ' ? "" : String.valueOf(node.letter), x - 5, y + 5);
            // Filho esquerdo
            if (node.left != null) {
                double nx = x - xOffset, ny = y + 100;
                gc.strokeLine(x, y + 15, nx, ny - 15);
                drawNode(gc, node.left, nx, ny, xOffset / 2);
            }
            // Filho direito
            if (node.right != null) {
                double nx = x + xOffset, ny = y + 100;
                gc.strokeLine(x, y + 15, nx, ny - 15);
                drawNode(gc, node.right, nx, ny, xOffset / 2);
            }
        }
    }

    // Instância compartilhada da árvore
    private static MorseBST morseTree = new MorseBST();

    // Mapeia alfabeto e números básicos
    private static void populateTree() {
        Map<Character, String> table = Map.ofEntries(
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
        table.forEach(morseTree::insert);
    }

    public static void main(String[] args) {
        populateTree();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Morse (letters separated by spaces): ");
        String input = scanner.nextLine();
        System.out.println("Decoded word: " + morseTree.decode(input));
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Morse Code BST Visualizer");
        int height = morseTree.getHeight();
        double width = Math.pow(2, height) * 20;
        double canvasHeight = 100 + height * 100;

        Canvas canvas = new Canvas(width, canvasHeight);
        morseTree.drawTree(canvas);

        Group root = new Group(canvas);
        primaryStage.setScene(new Scene(root, width, canvasHeight));
        primaryStage.show();
    }
}