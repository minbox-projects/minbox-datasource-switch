package org.minbox.framework.datasource;


import lombok.extern.slf4j.Slf4j;
import org.minbox.framework.datasource.config.DataSourceConfig;
import org.minbox.framework.datasource.config.DataSourceDruidConfig;
import org.minbox.framework.datasource.config.DataSourceHikariConfig;
import org.minbox.framework.datasource.support.MinBoxBasicDataSource;
import org.minbox.framework.datasource.support.MinBoxDruidDataSource;
import org.minbox.framework.datasource.support.MinBoxHikariDataSource;

import javax.sql.DataSource;

/**
 * {@link DataSource} Factory Class
 *
 * @author 恒宇少年
 */
@Slf4j
public class DataSourceFactoryBean {
    /**
     * create new dataSource instance
     *
     * @param config {@link DataSourceConfig}
     * @return {@link DataSource} the new instance
     */
    public DataSource newDataSource(DataSourceConfig config) {
        DataSource dataSource = null;
        // if not setting data source type class name
        if (config.getDataSourceType() == null) {
            // use druid data source
            if (checkUseAppointDataSource(DataSourceTypeNames.DRUID)) {
                dataSource = new MinBoxDruidDataSource((DataSourceDruidConfig) config);
                log.info("Initializing [{}] Druid Datasource Successfully.", config.getPoolName());
            }
            // use Hikari data source
            else if (checkUseAppointDataSource(DataSourceTypeNames.HIKARI)) {
                dataSource = new MinBoxHikariDataSource((DataSourceHikariConfig) config);
                log.info("Initialize [{}] Hikari Datasource Successfully.", config.getPoolName());
            }
        }
        // if setting data source type class name
        else {
            // druid data source
            if (DataSourceTypeNames.DRUID.equals(config.getDataSourceType().getName())) {
                dataSource = new MinBoxDruidDataSource((DataSourceDruidConfig) config);
                log.info("Initializing [{}] Druid Datasource Successfully.", config.getPoolName());
            }
            // Hikari data source
            else if (DataSourceTypeNames.HIKARI.equals(config.getDataSourceType().getName())) {
                dataSource = new MinBoxHikariDataSource((DataSourceHikariConfig) config);
                log.info("Initialize [{}] Hikari Datasource Successfully.", config.getPoolName());
            }
        }
        // use default basic data source
        if (dataSource == null) {
            dataSource = new MinBoxBasicDataSource(config);
            log.info("Unsupported data source type, use the default MinBoxBasicDataSource.");
        }
        return dataSource;
    }

    /**
     * check project is use appoint data source
     *
     * @return true/false
     */
    private boolean checkUseAppointDataSource(String dataSourceTypeName) {
        boolean isUseCheck = true;
        try {
            Class.forName(dataSourceTypeName);
        } catch (ClassNotFoundException e) {
            isUseCheck = false;
        }
        return isUseCheck;
    }
}
