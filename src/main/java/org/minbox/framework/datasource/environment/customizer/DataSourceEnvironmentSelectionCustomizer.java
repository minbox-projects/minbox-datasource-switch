package org.minbox.framework.datasource.environment.customizer;

import java.util.Set;

/**
 * 数据源环境自定义选择
 *
 * @author 恒宇少年
 * @since 1.0.4
 */
public interface DataSourceEnvironmentSelectionCustomizer {
    /**
     * 自定义选择数据源环境
     *
     * @param activeEnvironment  激活的数据源环境
     * @param environmentNameSet 全部的数据源环境集合
     * @return 自定义选择后的数据源环境
     */
    String customize(String activeEnvironment, Set<String> environmentNameSet);
}
