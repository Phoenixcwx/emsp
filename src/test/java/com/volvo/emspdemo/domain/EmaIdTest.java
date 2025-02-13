package com.volvo.emspdemo.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmaIdTest {

    @Test
    void creatNewFor() {
        EmaId newID = EmaId.creatNewFor("cn", "tjs");
        assertNotNull(newID);
        String contractID = newID.toContractId();
        assertEquals("cntjs0000000001", contractID);

        EmaId newID2 = EmaId.creatNewFor("cn", "tjs");
        assertNotNull(newID2);
        String contractID2 = newID2.toContractId();
        assertEquals("cntjs0000000011", contractID2);

        EmaId newID3 = EmaId.creatNewFor("cn", "tjs");
        assertNotNull(newID3);
        String contractID3 = newID3.toContractId();
        assertEquals("cntjs0000000021", contractID3);
      }
}