package com.robsil.erommerce.protoservice.util;

import com.google.protobuf.ByteString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class BigDecimalConverter {

    private BigDecimalConverter() {}

    public static BigDecimal toJava(com.robsil.proto.BigDecimal value) {
        MathContext mc = new MathContext(value.getPrecision());
        return new BigDecimal(new BigInteger(value.getValue().toByteArray()), value.getScale(), mc);
    }

    public static com.robsil.proto.BigDecimal toProto(BigDecimal value) {
        return com.robsil.proto.BigDecimal.newBuilder()
                .setScale(value.scale())
                .setPrecision(value.precision())
                .setValue(ByteString.copyFrom(value.unscaledValue().toByteArray()))
                .build();
    }

}
