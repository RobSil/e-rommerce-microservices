package com.robsil.erommerce.protoservice.util.util;

import com.robsil.proto.Id;

public class IdUtil {

    private IdUtil() {}

    public static Id of(Long id) {
        return Id.newBuilder().setId(id).build();
    }

}
