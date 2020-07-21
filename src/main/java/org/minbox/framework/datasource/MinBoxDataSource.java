package org.minbox.framework.datasource;

import org.minbox.framework.datasource.exception.DataSourceSwitchException;

import javax.sql.DataSource;

/**
 * ApiBoot Extends {@link DataSource}
 *
 * @author 恒宇少年
 */
public interface MinBoxDataSource extends DataSource {
    /**
     * Create new data source Instance
     *
     * @return {@link DataSource}
     * @throws DataSourceSwitchException datasource switch exception
     */
    DataSource build() throws DataSourceSwitchException;
}
