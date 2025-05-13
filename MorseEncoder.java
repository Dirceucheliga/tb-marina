public class MorseEncoder {
    public String encode(String text) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toUpperCase().toCharArray()) {
            if (c == ' ') {
                sb.append("  "); // separar palavras
            } else {
                String code = MorseData.ENCODE_MAP.getOrDefault(c, "?");
                sb.append(code).append(' ');
            }
        }
        return sb.toString().trim();
    }
}
