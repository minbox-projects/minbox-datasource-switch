package org.minbox.framework.datasource.routing;

import lombok.extern.slf4j.Slf4j;
import org.minbox.framework.datasource.DataSourceFactoryBean;
import org.minbox.framework.datasource.config.DataSourceConfig;
import org.minbox.framework.datasource.environment.DataSourceSwitchEnvironment;
import org.minbox.framework.datasource.environment.customizer.DataSourceEnvironmentSelectionCustomizer;
import org.minbox.framework.datasource.environment.customizer.DefaultDataSourceEnvironmentSelectionCustomizer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * MinBox Switch DataSource Routing
 *
 * @author 恒宇少年
 */
@Slf4j
public class MinBoxSwitchRoutingDataSource extends AbstractRoutingDataSource implements InitializingBean, DisposableBean {
    /**
     * environment@poolName
     */
    private static final String POOL_NAME_FORMAT = "%s@%s";
    private DataSourceFactoryBean factoryBean;
    private String activeEnvironment;
    private DataSourceEnvironmentSelectionCustomizer environmentSelectionCustomizer;
    private List<DataSourceSwitchEnvironment> dataSourceSwitchEnvironments;
    private Map<String, DataSource> dataSourceMap = new LinkedHashMap<>();
    private Set<String> environmentNameSet;
    private String primaryPoolName;
    private String formattedPrimaryPoolName;

    public MinBoxSwitchRoutingDataSource(DataSourceFactoryBean factoryBean, String activeEnvironment, List<DataSourceSwitchEnvironment> environments) {
        this(factoryBean, new DefaultDataSourceEnvironmentSelectionCustomizer(), activeEnvironment, environments);
    }

    public MinBoxSwitchRoutingDataSource(DataSourceFactoryBean factoryBean, DataSourceEnvironmentSelectionCustomizer environmentSelectionCustomizer,
                                         String activeEnvironment, List<DataSourceSwitchEnvironment> environments) {
        Assert.hasLength(activeEnvironment, "Active environment cannot be empty.");
        Assert.notEmpty(environments, "DataSource switch environment list cannot be null.");
        this.factoryBean = factoryBean;
        this.environmentSelectionCustomizer = environmentSelectionCustomizer == null ? new DefaultDataSourceEnvironmentSelectionCustomizer() : environmentSelectionCustomizer;
        this.activeEnvironment = activeEnvironment;
        this.dataSourceSwitchEnvironments = environments;
        // @formatter:off
        DataSourceSwitchEnvironment activeSwitchProfile = dataSourceSwitchEnvironments.stream()
                .filter(switchProfile -> activeEnvironment.equals(switchProfile.getEnvironment()))
                .findFirst()
                .orElse(null);
        // @formatter:on
        Assert.notNull(activeSwitchProfile, "Active environment not find in datasource switch environment list.");
        this.primaryPoolName = activeSwitchProfile.getPrimaryPoolName();
        this.formattedPrimaryPoolName = String.format(POOL_NAME_FORMAT, this.activeEnvironment, this.primaryPoolName);
        this.environmentNameSet = dataSourceSwitchEnvironments.stream().map(DataSourceSwitchEnvironment::getEnvironment).collect(Collectors.toSet());
    }

    /**
     * get current datasource instance
     *
     * @return DataSource
     */
    @Override
    protected Object determineCurrentLookupKey() {
        String currentPoolName = DataSourceContextHolder.get();
        if (ObjectUtils.isEmpty(currentPoolName)) {
            // use active environment default primary pool name
            return this.formattedPrimaryPoolName;
        }
        String currentProfile = this.environmentSelectionCustomizer.customize(this.activeEnvironment, this.environmentNameSet);
        if (ObjectUtils.isEmpty(currentProfile) || !this.environmentNameSet.contains(currentProfile)) {
            currentProfile = this.activeEnvironment;
            log.error("Selection environment [{}] not in set, use default active environment.", currentProfile);
        }
        // return after format pool name
        return String.format(POOL_NAME_FORMAT, currentProfile, currentPoolName);
    }

    /**
     * init all datasource by DataSourceConfig
     *
     * @see DataSourceConfig
     * @see org.minbox.framework.datasource.config.DataSourceDruidConfig
     * @see org.minbox.framework.datasource.config.DataSourceHikariConfig
     */
    @Override
    public void afterPropertiesSet() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        dataSourceSwitchEnvironments.forEach(switchProfile -> switchProfile.getConfigs().forEach(dataSourceConfig -> {
            DataSource dataSource = factoryBean.newDataSource(dataSourceConfig);
            // format datasource pool name with environment
            String poolName = String.format(POOL_NAME_FORMAT, switchProfile.getEnvironment(), dataSourceConfig.getPoolName());
            dataSourceMap.put(poolName, dataSource);
            targetDataSources.put(poolName, dataSource);
        }));
        // set target datasource's
        this.setTargetDataSources(targetDataSources);
        // set active environment default data source
        this.setDefaultTargetDataSource(this.dataSourceMap.get(this.formattedPrimaryPoolName));
        log.info("DataSourceSwitch initialization successful, default pool name is [{}]", this.formattedPrimaryPoolName);
        // call parent afterProperties method
        super.afterPropertiesSet();
    }

    /**
     * service shutdown
     * all datasource execute destroy
     *
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        dataSourceMap.keySet().forEach(poolName -> {
            try {
                DataSource dataSource = dataSourceMap.get(poolName);
                Class<? extends DataSource> clazz = dataSource.getClass();
                // get close method
                // druid or Hikari dataSource have "close" method
                Method closeMethod = clazz.getDeclaredMethod("close");
                if (closeMethod != null) {
                    closeMethod.invoke(dataSource);
                    log.info("Execute closing datasource [{}]", poolName);
                }
            } catch (Exception e) {
                // ignore
            }
        });
    }
}
