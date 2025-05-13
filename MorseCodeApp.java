import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import java.util.Scanner;

public class MorseCodeApp extends Application {
    @Override
    public void start(Stage stage) {
        MorseBST tree = new MorseBST();
        tree.buildTree();
        int h = tree.getHeight();
        double w = Math.pow(2, h) * 20;
        double ch = 100 + h * 100;
        Canvas canvas = new Canvas(w, ch);
        tree.draw(canvas);

        stage.setTitle("Visualizador de Árvore BST - Código Morse");
        stage.setScene(new Scene(new Group(canvas), w, ch));
        stage.show();
    }

    public static void main(String[] args) {
        MorseDecoder decoder = new MorseDecoder();
        MorseEncoder encoder = new MorseEncoder();
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite código Morse: ");
        String morseInput = sc.nextLine();
        System.out.println("Texto decodificado: " + decoder.decode(morseInput));

        System.out.print("Digite texto para codificar: ");
        String textInput = sc.nextLine();
        System.out.println("Código Morse: " + encoder.encode(textInput));

        launch();
    }
}
