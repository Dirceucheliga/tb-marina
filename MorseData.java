import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MorseData {
    public static final Map<Character, String> ENCODE_MAP;
    public static final Map<String, Character> DECODE_MAP;

    static {
        Map<Character, String> encode = new HashMap<>();
        encode.put('A', ".-");    encode.put('B', "-..."); encode.put('C', "-.-.");
        encode.put('D', "-..");   encode.put('E', ".");     encode.put('F', "..-.");
        encode.put('G', "--.");   encode.put('H', "....");  encode.put('I', "..");
        encode.put('J', ".---");  encode.put('K', "-.-");   encode.put('L', ".-..");
        encode.put('M', "--");    encode.put('N', "-.");    encode.put('O', "---");
        encode.put('P', ".--.");  encode.put('Q', "--.-");  encode.put('R', ".-.");
        encode.put('S', "...");   encode.put('T', "-");     encode.put('U', "..-");
        encode.put('V', "...-");  encode.put('W', ".--");   encode.put('X', "-..-");
        encode.put('Y', "-.--");  encode.put('Z', "--..");
        encode.put('1', ".----"); encode.put('2', "..---"); encode.put('3', "...--");
        encode.put('4', "....-"); encode.put('5', "....."); encode.put('6', "-....");
        encode.put('7', "--..."); encode.put('8', "---.."); encode.put('9', "----.");
        encode.put('0', "-----");
        ENCODE_MAP = Collections.unmodifiableMap(encode);

        Map<String, Character> decode = new HashMap<>();
        for (Map.Entry<Character, String> e : encode.entrySet()) {
            decode.put(e.getValue(), e.getKey());
        }
        DECODE_MAP = Collections.unmodifiableMap(decode);
    }
}
