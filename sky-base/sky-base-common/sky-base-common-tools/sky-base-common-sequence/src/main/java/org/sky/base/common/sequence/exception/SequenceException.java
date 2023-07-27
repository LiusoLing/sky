package org.sky.base.common.sequence.exception;

/**
 * <p>
 * 序列号生成异常
 * </p>
 *
 * @author liusongling
 * @since 2023-07-27 09:29:04
 */
public class SequenceException extends RuntimeException {
    private static final long serialVersionUID = 2523903096709542362L;

    public SequenceException(String message) {
        super(message);
    }

    public SequenceException(Throwable cause) {
        super(cause);
    }
}
