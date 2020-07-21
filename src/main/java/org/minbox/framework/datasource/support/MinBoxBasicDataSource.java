package org.minbox.framework.datasource.support;

import com.zaxxer.hikari.HikariDataSource;
import org.minbox.framework.datasource.MinBoxDataSource;
import org.minbox.framework.datasource.config.DataSourceConfig;
import org.minbox.framework.datasource.exception.DataSourceSwitchException;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.DelegatingDataSource;

import javax.sql.DataSource;

/**
 * The Basic {@link DataSource} config
 *
 * @author 恒宇少年
 */
public class MinBoxBasicDataSource extends DelegatingDataSource implements MinBoxDataSource {
    private DataSourceConfig config;

    public MinBoxBasicDataSource(DataSourceConfig config) {
        this.config = config;
        this.setTargetDataSource(build());
    }

    /**
     * create default basic data source
     *
     * @return {@link DataSource} instance
     * @throws DataSourceSwitchException ApiBoot Exception
     */
    @Override
    public DataSource build() throws DataSourceSwitchException {
        try {
            DataSource dataSource = DataSourceBuilder.create().url(config.getUrl()).username(config.getUsername()).password(config.getPassword()).driverClassName(config.getDriverClassName())
                // springboot 2.x default is HikariDataSource
                .type(HikariDataSource.class)
                .build();
            return dataSource;
        } catch (Exception e) {
            throw new DataSourceSwitchException("Create a default data source exception", e);
        }

    }
}
