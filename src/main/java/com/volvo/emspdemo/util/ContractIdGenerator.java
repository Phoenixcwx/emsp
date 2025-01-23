package com.volvo.emspdemo.util;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.stereotype.Component;

@Component
public class ContractIdGenerator {
    private static final String LOCK_NAME = "contractIdGeneratorLock";
    private static final String ID_FORMAT = "%s-%03d-%09s"; // 使用 %s 代替 %d 以支持 36 进制
     private static final String NINE_DIG_SEQ_NAME =  "contract_id_sequence_9dig";
    private static final String  THREE_DIG_SEQ_NAME = "contract_id_sequence_3dig";

    private final SequenceManager sequenceManager;

    public ContractIdGenerator(SequenceManager sequenceManager) {
        this.sequenceManager = sequenceManager;
    }

    @SchedulerLock(name = LOCK_NAME, lockAtLeastFor = "1s", lockAtMostFor = "10s")
    public String generateContractIdWithPlaceHolder() {
        // 获取三位序列号
        long threeDig = sequenceManager.getNextSequenceValue(THREE_DIG_SEQ_NAME);

        // 获取序列号
        long nineDig = sequenceManager.getNextSequenceValue(NINE_DIG_SEQ_NAME);

        // 如果序列号超过 36^9，重置序9位序列号并增加三位序列号
        if (nineDig >= Math.pow(36, 9)) {
            sequenceManager.resetSequence(NINE_DIG_SEQ_NAME);
            threeDig++;
        }

        // 将序列号转换为 36 进制的字符串
        String base36Seq3 = Base36Util.toBase36(nineDig);
        String base36Seq9 = Base36Util.toBase36(nineDig);

        // 生成 Contract ID
        String prefix = "aa";
        String contractId = String.format(ID_FORMAT, prefix, base36Seq3, base36Seq9);

        return contractId;
    }

    @SchedulerLock(name = LOCK_NAME, lockAtLeastFor = "1s", lockAtMostFor = "10s")
    public String generateContractIdWithoutPlaceHolder() {
        String result = generateContractIdWithPlaceHolder();
        return result.replace("-","");
    }
}
