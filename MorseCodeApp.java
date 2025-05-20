public class MorseCodeApp extends Application {
    @Override
    public void start(Stage stage) {
        MorseBST tree = new MorseBST();
        tree.buildTree();

    }

    public static void main(String[] args) {
        MorseBST tree = new MorseBST();
        tree.buildTree();
        MorseDecoder decoder = new MorseDecoder(tree);
        MorseEncoder encoder = new MorseEncoder(tree);
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite código Morse: ");
        System.out.println("Texto: " + decoder.decode(sc.nextLine()));

        System.out.print("Digite texto normal: ");
        System.out.println("Morse: " + encoder.encode(sc.nextLine()));

        launch();
    }
}
