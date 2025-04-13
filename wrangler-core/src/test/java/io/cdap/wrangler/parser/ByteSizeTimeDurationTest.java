package io.cdap.wrangler.parser;

import io.cdap.wrangler.api.parser.ByteSize;
import io.cdap.wrangler.api.parser.TimeDuration;
import org.junit.Assert;
import org.testng.annotations.Test;

public class ByteSizeTimeDurationTest {
    @Test
    public void testByteSizeParsing() {
        ByteSize byteSize1 = new ByteSize("10KB");
        Assert.assertEquals(10 * 1024, byteSize1.getBytes());

        ByteSize byteSize2 = new ByteSize("1.5MB");
        Assert.assertEquals((long) (1.5 * 1024 * 1024), byteSize2.getBytes());
    }

    @Test
    public void testTimeDurationParsing() {
        TimeDuration timeDuration1 = new TimeDuration("150ms");
        Assert.assertEquals(150 * 1_000_000, timeDuration1.getNanoseconds());

        TimeDuration timeDuration2 = new TimeDuration("2.1s");
        Assert.assertEquals((long) (2.1 * 1_000_000_000), timeDuration2.getNanoseconds());
    }

}