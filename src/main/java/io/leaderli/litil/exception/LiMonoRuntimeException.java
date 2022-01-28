package io.leaderli.litil.exception;

public class LiMonoRuntimeException extends RuntimeException {
    public LiMonoRuntimeException(String msg) {
        super(msg);
    }

    public LiMonoRuntimeException(Throwable throwable) {
        super(throwable);
    }
}
