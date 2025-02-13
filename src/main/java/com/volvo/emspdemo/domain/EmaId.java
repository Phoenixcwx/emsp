package com.volvo.emspdemo.domain;

import com.volvo.emspdemo.util.Base36Util;
import com.volvo.emspdemo.util.EmaIDGenerator;
import com.volvo.emspdemo.util.SpringContextHolder;
import lombok.Getter;

/**
 * <EMAID> = <Country Code> <S> <Provider ID> <S> <eMA Instance> <S> <Check Digit>
 *  format: /[a-z]{2}(-?)[\da-z]{3}\1[\da-z]{9}(\1[\da-z])?/i
 */
@Getter
public final class EmaId {
    /**
     * 是否
     */
    private final char[] country;
    private final char[] province;
    private final char[] emaInstance;
    private final char checkDigit;


    private EmaId(char[] country, char[] province, char[] emaInstance, char checkDigit) {
        this.country = country;
        this.province = province;
        this.emaInstance = emaInstance;
        this.checkDigit = checkDigit;
    }

    public static EmaId from(char[] country, char[] province, char[] emaInstance, char checkDigit) {
        return new EmaId(country, province, emaInstance, checkDigit);
    }

    public static EmaId from(String country, String province, String emaInstance, String checkDigit) {
        return new EmaId(country.toCharArray(), province.toCharArray(), emaInstance.toCharArray(), checkDigit.toCharArray()[0]);
    }

    public static EmaId creatNewFor(String country, String province) {
        EmaIDGenerator emaIDGenerator = SpringContextHolder.getBean(EmaIDGenerator.class);
        Long seqNumber = emaIDGenerator.generateNewSeqNumberFor(country, province);
        String emaInstance = Base36Util.formatString(Base36Util.toBase36(seqNumber), 9);
        return from(country, province, emaInstance, "1");
    }

    public String toContractId() {
        StringBuilder sb = new StringBuilder();
        sb.append(country);
        sb.append(province);
        sb.append(emaInstance);
        sb.append(checkDigit);
        return sb.toString();
    }
}
