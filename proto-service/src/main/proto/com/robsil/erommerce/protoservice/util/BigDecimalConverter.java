package com.robsil.erommerce.protoservice.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class BigDecimalConverter {

    private BigDecimalConverter() {}

    public static BigDecimal toJava(com.robsil.proto.BigDecimal value) {
        MathContext mc = new MathContext(value.getPrecision());
        return new BigDecimal(new BigInteger(value.getValue().toByteArray()), value.getScale(), mc);
    }

}
