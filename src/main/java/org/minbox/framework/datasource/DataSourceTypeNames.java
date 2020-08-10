package org.minbox.framework.datasource;

/**
 * {@link javax.sql.DataSource} Types name definition
 *
 * @author 恒宇少年
 */
public interface DataSourceTypeNames {
    /**
     * The Druid Class Name
     *
     * @see com.alibaba.druid.pool.DruidDataSource
     */
    String DRUID = "org.minbox.framework.datasource.support.MinBoxDruidDataSource";
    /**
     * The Hikari Class Name
     *
     * @see com.zaxxer.hikari.HikariDataSource
     */
    String HIKARI = "org.minbox.framework.datasource.support.MinBoxHikariDataSource";
}
