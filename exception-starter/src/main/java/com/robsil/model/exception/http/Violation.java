package com.robsil.model.exception.http;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Violation {

    private String fieldName;
    private String message;

}
