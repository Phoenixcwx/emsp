package com.volvo.emspdemo.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmaIdTest {

    @Test
    void creatNewTest() {
        EmaId newID = EmaId.creatNewFor("cn", "tjs");
        assertNotNull(newID);
        String contractID = newID.toContractId();
        assertEquals("cntjs0000000001", contractID);

        EmaId newID2 = EmaId.creatNewFor("cn", "tjs");
        assertNotNull(newID2);
        String contractID2 = newID2.toContractId();
        assertEquals("cntjs0000000011", contractID2);

        String lastContractId = "";
        for (int i = 0; i < 9; i++) {
            EmaId newID3 = EmaId.creatNewFor("cn", "tjs");
            assertNotNull(newID3);
            lastContractId = newID3.toContractId();
        }
        assertEquals("cntjs00000000a1", lastContractId);
      }
}