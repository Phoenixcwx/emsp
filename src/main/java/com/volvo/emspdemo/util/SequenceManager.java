package com.volvo.emspdemo.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SequenceManager {

    private static final String[] SEQUENCE_NAMES = new String[]{"contract_id_sequence_9dig", "contract_id_sequence_3dig"};

    private final JdbcTemplate jdbcTemplate;

    public SequenceManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 检查序列是否存在，如果不存在则创建
     */
    public void initializeSequence() {
        Arrays.stream(SEQUENCE_NAMES).forEach(sequenceName -> {
            String checkSequenceSql = "SELECT COUNT(*) FROM information_schema.sequences WHERE sequence_name = ?";
            Integer count = jdbcTemplate.queryForObject(checkSequenceSql, Integer.class, sequenceName.toUpperCase());

            if (count == null || count == 0) {
                String createSequenceSql = "CREATE SEQUENCE " + sequenceName + " START WITH 0 INCREMENT BY 1";
                jdbcTemplate.execute(createSequenceSql);
                System.out.println("Sequence created: " + sequenceName);
            } else {
                System.out.println("Sequence already exists: " + sequenceName);
            }
        });

    }

    /**
     * 获取下一个序列值
     */
    public long getNextSequenceValue(String sequenceName) {
        String nextValSql = "SELECT NEXTVAL('" + sequenceName + "')";
        return jdbcTemplate.queryForObject(nextValSql, Long.class);
    }

    public void resetSequence(String sequenceName) {
        String createSequenceSql = "SELECT SETVAL('" + sequenceName + "', 0, false)";
        jdbcTemplate.execute(createSequenceSql);
        System.out.println("Sequence created: " + sequenceName);
    }
}
