package com.DI4MONDTOOL.util;

import com.DI4MONDTOOL.reply.AbstractReply;

@FunctionalInterface
public interface Callback<T extends AbstractReply> {

    public void callback(Throwable failCause, T result);
}
