package org.minbox.framework.datasource.exception;

import lombok.NoArgsConstructor;

/**
 * The datasource switch exception
 *
 * @author 恒宇少年
 */
@NoArgsConstructor
public class DataSourceSwitchException extends RuntimeException {
    public DataSourceSwitchException(String message) {
        super(message);
    }

    public DataSourceSwitchException(String message, Throwable cause) {
        super(message, cause);
    }
}
