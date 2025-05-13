public class MorseDecoder {
    public String decode(String morse) {
        StringBuilder sb = new StringBuilder();
        for (String token : morse.trim().split(" ")) {
            sb.append(MorseData.DECODE_MAP.getOrDefault(token, '?'));
        }
        return sb.toString();
    }
}
