package org.minbox.framework.datasource.routing.customizer;

import org.minbox.framework.datasource.annotation.DataSourceSwitch;

import java.util.Set;

/**
 * 自定义数据源选择器
 * <p>
 * 优先级高于{@link DataSourceSwitch}，数据源最终路由切换时调用
 *
 * @author 恒宇少年
 * @since 1.0.3
 */
public interface DataSourceSelectionCustomizer {
    /**
     * 自定义选择使用的数据源
     *
     * @param currentPoolName       当前所使用的数据源名称
     * @param dataSourcePoolNameSet 全部的数据源名称集合
     * @return 自定义选择后的数据源名称
     */
    String customize(String currentPoolName, Set<String> dataSourcePoolNameSet);
}
