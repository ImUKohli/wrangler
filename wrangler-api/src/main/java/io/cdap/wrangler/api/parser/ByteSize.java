package io.cdap.wrangler.api.parser;

import com.google.gson.JsonElement;
import io.cdap.wrangler.api.parser.TokenType;

public class ByteSize implements Token {
    private final String value;
    private final long bytes;

    public ByteSize(String value) {
        this.value = value;
        this.bytes = parseBytes(value);
    }

    @Override
    public TokenType type() {
        return TokenType.BYTE_SIZE;
    }

    @Override
    public JsonElement toJson() {
        return null;
    }

    @Override
    public String value() {
        return value;
    }

    public long getBytes() {
        return bytes;
    }

    private long parseBytes(String input) {
        String numStr = input.replaceAll("[^0-9.]", "");
        String unit = input.replaceAll("[0-9.]", "").toUpperCase();
        double number = Double.parseDouble(numStr);
        switch (unit) {
            case "KB": return (long) (number * 1024);
            case "MB": return (long) (number * 1024 * 1024);
            case "GB": return (long) (number * 1024 * 1024 * 1024);
            case "TB": return (long) (number * 1024 * 1024 * 1024 * 1024);
            case "PB": return (long) (number * 1024 * 1024 * 1024 * 1024 * 1024);
            case "B":
            default:   return (long) number;
        }
    }
}