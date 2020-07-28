package org.minbox.framework.datasource.config;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Hikari data source configuration class
 *
 * @author 恒宇少年
 */
@Data
public class DataSourceHikariConfig extends DataSourceConfig {
    /**
     * This property controls the default auto-commit behavior of connections returned from the pool.
     * It is a boolean value. Default: true
     */
    private boolean autoCommit = true;
    /**
     * This property controls the maximum number of milliseconds that a client (that's you) will wait for a connection from the pool.
     * If this time is exceeded without a connection becoming available, a SQLException will be thrown.
     * Lowest acceptable connection timeout is 250 ms. Default: 30000 (30 seconds)
     */
    private long connectionTimeout;
    /**
     * This property controls the maximum amount of time that a connection is allowed to sit idle in the pool.
     * This setting only applies when minimumIdle is defined to be less than maximumPoolSize.
     * Idle connections will not be retired once the pool reaches minimumIdle connections.
     * Whether a connection is retired as idle or not is subject to a maximum variation of +30 seconds, and average variation of +15 seconds.
     * A connection will never be retired as idle before this timeout.
     * A value of 0 means that idle connections are never removed from the pool.
     * The minimum allowed value is 10000ms (10 seconds). Default: 600000 (10 minutes)
     */
    private long idleTimeout = 600000;
    /**
     * This property controls the maximum size that the pool is allowed to reach, including both idle and in-use connections.
     * Basically this value will determine the maximum number of actual connections to the database backend.
     * A reasonable value for this is best determined by your execution environment.
     * When the pool reaches this size, and no idle connections are available, calls to getConnection() will block for up to connectionTimeout milliseconds before timing out.
     * Please read about pool sizing. Default: 10
     */
    private int maxPoolSize = 10;
    /**
     * This property controls the maximum lifetime of a connection in the pool.
     * An in-use connection will never be retired, only when it is closed will it then be removed.
     * On a connection-by-connection basis, minor negative attenuation is applied to avoid mass-extinction in the pool.
     * We strongly recommend setting this value, and it should be several seconds shorter than any database or infrastructure imposed connection time limit.
     * A value of 0 indicates no maximum lifetime (infinite lifetime), subject of course to the idleTimeout setting.
     * The minimum allowed value is 30000ms (30 seconds).
     * Default: 1800000 (30 minutes)
     */
    private long maxLifetime = 1800000;
    /**
     * This property controls the minimum number of idle connections that HikariCP tries to maintain in the pool.
     * If the idle connections dip below this value and total connections in the pool are less than maximumPoolSize, HikariCP will make a best effort to add additional connections quickly and efficiently.
     * However, for maximum performance and responsiveness to spike demands, we recommend not setting this value and instead allowing HikariCP to act as a fixed size connection pool.
     * Default: same as maximumPoolSize
     */
    private int minimumIdle;
    /**
     * If your driver supports JDBC4 we strongly recommend not setting this property.
     * This is for "legacy" drivers that do not support the JDBC4 Connection.isValid() API.
     * This is the query that will be executed just before a connection is given to you from the pool to validate that the connection to the database is still alive.
     * Again, try running the pool without this property, HikariCP will log an error if your driver is not JDBC4 compliant to let you know.
     * Default: none
     */
    private String connectionTestQuery;
    /**
     * Hikari dataSource Property Map
     * like：cachePrepStmts、prepStmtCacheSize、prepStmtCacheSqlLimit..
     */
    private Map<String, String> property = new HashMap<>();
}
