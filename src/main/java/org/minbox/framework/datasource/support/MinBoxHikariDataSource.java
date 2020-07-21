package org.minbox.framework.datasource.support;

import com.zaxxer.hikari.HikariDataSource;
import org.minbox.framework.datasource.MinBoxDataSource;
import org.minbox.framework.datasource.config.DataSourceHikariConfig;
import org.minbox.framework.datasource.exception.DataSourceSwitchException;

import javax.sql.DataSource;

/**
 * The Hikari {@link DataSource} config
 *
 * @author 恒宇少年
 */
public class MinBoxHikariDataSource extends HikariDataSource implements MinBoxDataSource {

    public MinBoxHikariDataSource(DataSourceHikariConfig config) {
        try {
            this.setJdbcUrl(config.getUrl());
            this.setUsername(config.getUsername());
            this.setPassword(config.getPassword());
            config.getProperty().keySet().stream().forEach(param -> this.addDataSourceProperty(param, config.getProperty().get(param)));
        } catch (Exception e) {
            throw new DataSourceSwitchException("Create new Hikari dataSource fail.", e);
        }
    }

    /**
     * create new Hikari dataSource instance
     *
     * @return {@link DataSource} this class instance
     * @throws DataSourceSwitchException ApiBoot Exception
     */
    @Override
    public DataSource build() throws DataSourceSwitchException {
        return this;
    }
}
