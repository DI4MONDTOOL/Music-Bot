package com.DI4MONDTOOL.exceptions;

import com.DI4MONDTOOL.request.RequestParam;

public class ParamTypeException extends HypixelAPIException {
    public ParamTypeException(RequestParam requestParam, Object value) {
        super(requestParam.name() + " is not of correct type! " +
                "Expected " +
                "'" + requestParam.getValueClass().getSimpleName() + "'" +
                " but got " +
                "'" + (value == null ? "null" : value.getClass().getSimpleName()) + "'"
        );
    }
}
