package com.hitales.national.migrate.enums;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-03-07
 * @time:14:12
 */
public enum GlobalHttpStatus {

    GLOBAL_BIZ_STATUS(452,"业务异常");

    private final int value;

    private final String reasonPhrase;


    GlobalHttpStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }


    /**
     * Return the integer value of this status code.
     */
    public int value() {
        return this.value;
    }

    /**
     * Return the reason phrase of this status code.
     */
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }
}
