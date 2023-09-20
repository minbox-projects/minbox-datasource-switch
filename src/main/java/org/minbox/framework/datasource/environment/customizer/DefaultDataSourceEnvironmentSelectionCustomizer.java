package org.minbox.framework.datasource.environment.customizer;

import java.util.Set;

/**
 * 默认的数据源自定义选择实现
 *
 * @author 恒宇少年
 * @since 1.0.4
 */
public class DefaultDataSourceEnvironmentSelectionCustomizer implements DataSourceEnvironmentSelectionCustomizer {
    @Override
    public String customize(String activeEnvironment, Set<String> environmentNameSet) {
        return activeEnvironment;
    }
}
