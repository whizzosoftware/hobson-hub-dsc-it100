package com.whizzosoftware.hobson.dsc.api.command;

import com.whizzosoftware.hobson.api.HobsonInvalidRequestException;
import org.junit.Test;
import static org.junit.Assert.*;

public class PartitionArmControlWithCodeTest {
    @Test
    public void testGetDataString() {
        // 6 character code
        PartitionArmControlWithCode pacwc = new PartitionArmControlWithCode(1, "123456");
        assertEquals("1123456", pacwc.getDataString());

        // 4 character code
        pacwc = new PartitionArmControlWithCode(1, "1234");
        assertEquals("1123400", pacwc.getDataString());

        // partition 8
        pacwc = new PartitionArmControlWithCode(8, "432101");
        assertEquals("8432101", pacwc.getDataString());

        // invalid partition 0
        try {
            new PartitionArmControlWithCode(0, "432101");
            fail("Should have thrown exception");
        } catch (HobsonInvalidRequestException e) {}

        // invalid partition 9
        try {
            new PartitionArmControlWithCode(9, "432101");
            fail("Should have thrown exception");
        } catch (HobsonInvalidRequestException e) {}
    }
}
