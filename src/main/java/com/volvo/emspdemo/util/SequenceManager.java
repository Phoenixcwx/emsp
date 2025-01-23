package com.volvo.emspdemo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
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

    /**
     * 获取当前序列值
     */
    public long getCurrentSequenceValue(String sequenceName) {
        String sql = "SELECT last_value FROM pg_sequences WHERE schemaname = ? AND sequencename = ?";
        String schemaName = "public";
        try {
            // 执行查询并返回结果
            return jdbcTemplate.queryForObject(sql, Long.class, schemaName, sequenceName);
        } catch (EmptyResultDataAccessException e) {
            // 如果序列不存在，记录日志并抛出异常
            log.error("序列不存在: schemaName={}, sequenceName={}", schemaName, sequenceName, e);
            throw new IllegalArgumentException("序列不存在: " + sequenceName, e);
        } catch (Exception e) {
            // 处理其他异常
            log.error("查询序列当前值失败: schemaName={}, sequenceName={}", schemaName, sequenceName, e);
            throw new RuntimeException("查询序列当前值失败", e);
        }
    }

    public void resetSequence(String sequenceName) {
        String createSequenceSql = "SELECT SETVAL('" + sequenceName + "', 0, false)";
        jdbcTemplate.execute(createSequenceSql);
        System.out.println("Sequence created: " + sequenceName);
    }
}
