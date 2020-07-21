package org.minbox.framework.datasource.support;

import com.alibaba.druid.pool.DruidDataSource;
import org.minbox.framework.datasource.MinBoxDataSource;
import org.minbox.framework.datasource.config.DataSourceDruidConfig;
import org.minbox.framework.datasource.exception.DataSourceSwitchException;

import javax.sql.DataSource;

/**
 * The Druid {@link DataSource} config
 *
 * @author 恒宇少年
 */
public class MinBoxDruidDataSource extends DruidDataSource implements MinBoxDataSource {

    public MinBoxDruidDataSource(DataSourceDruidConfig config) {
        try {
            this.setUrl(config.getUrl());
            this.setUsername(config.getUsername());
            this.setPassword(config.getPassword());
            this.setDriverClassName(config.getDriverClassName());
            this.setFilters(config.getFilters());
            this.setMaxActive(config.getMaxActive());
            this.setInitialSize(config.getInitialSize());
            this.setMaxWait(config.getMaxWait());
            this.setValidationQuery(config.getValidationQuery());
            this.setTestWhileIdle(config.isTestWhileIdle());
            this.setTestOnBorrow(config.isTestOnBorrow());
            this.setTestOnReturn(config.isTestOnReturn());
        } catch (Exception e) {
            throw new DataSourceSwitchException("Create new druid dataSource fail.", e);
        }
    }

    /**
     * create new druid dataSource instance
     *
     * @return {@link DataSource} this class instance
     * @throws DataSourceSwitchException ApiBoot Exception
     */
    @Override
    public DataSource build() throws DataSourceSwitchException {
        return this;
    }
}
