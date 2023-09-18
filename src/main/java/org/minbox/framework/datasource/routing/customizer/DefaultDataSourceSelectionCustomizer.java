package org.minbox.framework.datasource.routing.customizer;

import java.util.Set;

/**
 * 默认的自定义数据源选择器实现类
 *
 * @author 恒宇少年
 * @since 1.0.3
 */
public class DefaultDataSourceSelectionCustomizer implements DataSourceSelectionCustomizer {
    @Override
    public String customize(String currentPoolName, Set<String> dataSourcePoolNameSet) {
        return currentPoolName;
    }
}
