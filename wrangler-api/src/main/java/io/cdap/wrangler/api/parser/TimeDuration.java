package io.cdap.wrangler.api.parser;

import com.google.gson.JsonElement;
import io.cdap.wrangler.api.parser.TokenType;

public class TimeDuration implements Token {
    private final String value;
    private final long nanoseconds;

    public TimeDuration(String value) {
        this.value = value;
        this.nanoseconds = parseNanoseconds(value);
    }

    @Override
    public TokenType type() {
        return TokenType.TIME_DURATION;
    }

    @Override
    public JsonElement toJson() {
        return null;
    }

    @Override
    public String value() {
        return value;
    }

    public long getNanoseconds() {
        return nanoseconds;
    }

    private long parseNanoseconds(String input) {
        String numStr = input.replaceAll("[^0-9.]", "");
        String unit = input.replaceAll("[0-9.]", "").toLowerCase();
        double number = Double.parseDouble(numStr);
        switch (unit) {
            case "ms": return (long) (number * 1_000_000);
            case "s":  return (long) (number * 1_000_000_000);
            case "m":  return (long) (number * 60 * 1_000_000_000);
            case "h":  return (long) (number * 3600 * 1_000_000_000);
            case "d":  return (long) (number * 86400 * 1_000_000_000);
            case "ns":
            default:   return (long) number;
        }
    }
}