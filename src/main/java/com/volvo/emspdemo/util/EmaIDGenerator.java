package com.volvo.emspdemo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class EmaIDGenerator {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor = Throwable.class)
    public Long generateNewSeqNumberFor(String country, String province) {
        String sql = "SELECT seq_number from ema_id where country = '" + country + "' and province = '" + province + "'";
        List<Long> currentSeq = jdbcTemplate.query(sql, new RowMapper<Long>() {

            @Override
            public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getLong("seq_number");
            }
        });

        if(CollectionUtils.isEmpty(currentSeq) || 0 == currentSeq.size() || 0 == currentSeq.get(0)) {
            String insertSql = "insert into ema_id(country, province, seq_number) values(?, ?, ?)";
            jdbcTemplate.update(insertSql, country, province, 1L);
            return 0L;
        } else {
            Long currentSeqId = currentSeq.get(0);
            currentSeqId++;
            String updateSql = "update ema_id set seq_number = ? where country = ? and province = ?";
            int result = jdbcTemplate.update(updateSql, currentSeqId, country, province);
            if(0 == result) {
                throw new RuntimeException("new seq number set failed for " + country + " " + province );
            }
            return --currentSeqId;
        }
    }
}
