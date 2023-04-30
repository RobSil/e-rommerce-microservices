package com.robsil.model.exception.http;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class GrpcHttpException extends HttpException {
}
