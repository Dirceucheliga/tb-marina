public class MorseEncoder {
    private final MorseBST tree;
    public MorseEncoder(MorseBST tree) { this.tree = tree; }

    public String encode(String text) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toUpperCase().toCharArray()) {
            if (c == ' ') {
                sb.append("  ");
            } else {
                sb.append(tree.encodeChar(c)).append(' ');
            }
        }
        return sb.toString().trim();
    }
}
