public class MorseDecoder {
    private final MorseBST tree;
    public MorseDecoder(MorseBST tree) { this.tree = tree; }

    public String decode(String morse) {
        StringBuilder sb = new StringBuilder();
        for (String token : morse.trim().split(" ")) {
            if (token.isEmpty()) continue;      
            sb.append(tree.decodeToken(token));
        }
        return sb.toString();
    }
}
