package io.cdap.wrangler.api.directives;

import io.cdap.wrangler.api.*;
import io.cdap.wrangler.api.parser.*;

import java.util.List;

public class AggregateStats implements Directive {
    private String sizeColumn;
    private String timeColumn;
    private String totalSizeColumn;
    private String totalTimeColumn;
    private long totalBytes = 0;
    private long totalNanoseconds = 0;

    @Override
    public UsageDefinition define() {
        return UsageDefinition.builder("aggregate-stats")
                .add("size-column", TokenType.COLUMN_NAME, "Source column with byte sizes")
                .add("time-column", TokenType.COLUMN_NAME, "Source column with time durations")
                .add("total-size-column", TokenType.COLUMN_NAME, "Target column for total size (MB)")
                .add("total-time-column", TokenType.COLUMN_NAME, "Target column for total time (seconds)")
                .build();
    }

    @Override
    public void initialize(Arguments args, ExecutorContext context) throws RecipeException {
        this.sizeColumn = ((ColumnName) args.value("size-column")).value();
        this.timeColumn = ((ColumnName) args.value("time-column")).value();
        this.totalSizeColumn = ((ColumnName) args.value("total-size-column")).value();
        this.totalTimeColumn = ((ColumnName) args.value("total-time-column")).value();
    }

    @Override
    public void initialize(Arguments args) throws DirectiveParseException {

    }

    @Override
    public List<Row> execute(List<Row> rows, ExecutorContext context) throws RecipeException {
        // Aggregate phase: accumulate totals
        for (Row row : rows) {
            Object sizeValue = row.getValue(sizeColumn);
            Object timeValue = row.getValue(timeColumn);

            if (sizeValue instanceof String) {
                ByteSize byteSize = new ByteSize((String) sizeValue);
                totalBytes += byteSize.getBytes();
            }

            if (timeValue instanceof String) {
                TimeDuration timeDuration = new TimeDuration((String) timeValue);
                totalNanoseconds += timeDuration.getNanoseconds();
            }
        }

        // Finalization phase: create a single row with the results
        Row result = new Row();
        double totalSizeMB = totalBytes / (1024.0 * 1024.0); // Convert bytes to MB
        double totalTimeSeconds = totalNanoseconds / 1_000_000_000.0; // Convert nanoseconds to seconds
        result.add(totalSizeColumn, totalSizeMB);
        result.add(totalTimeColumn, totalTimeSeconds);
        return List.of(result);
    }

    @Override
    public void destroy() {
        // No cleanup needed
    }
}