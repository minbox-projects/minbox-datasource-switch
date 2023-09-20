package org.minbox.framework.datasource.environment;

import org.minbox.framework.datasource.config.DataSourceConfig;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 多环境数据源切换
 *
 * @author 恒宇少年
 * @since 1.0.4
 */
public class DataSourceSwitchEnvironment {
    private String environment;
    private String primaryPoolName;
    private List<DataSourceConfig> configs;

    private DataSourceSwitchEnvironment(String environment, String primaryPoolName, List<DataSourceConfig> configs) {
        this.environment = environment;
        this.primaryPoolName = primaryPoolName;
        this.configs = configs;
    }

    public static Builder initialization() {
        return new Builder();
    }

    public static class Builder {
        private String environment;
        private String primaryPoolName;
        private List<DataSourceConfig> configs = new ArrayList<>();

        private Builder() {
        }

        public Builder environment(String environment) {
            this.environment = environment;
            return this;
        }

        public Builder primaryPoolName(String primaryPoolName) {
            this.primaryPoolName = primaryPoolName;
            return this;
        }

        public Builder configs(List<DataSourceConfig> configs) {
            if (ObjectUtils.isEmpty(configs)) {
                this.configs = configs;
            } else {
                this.configs.addAll(configs);
            }
            return this;
        }

        public Builder config(DataSourceConfig config) {
            this.configs.add(config);
            return this;
        }

        public DataSourceSwitchEnvironment build() {
            return new DataSourceSwitchEnvironment(environment, primaryPoolName, configs);
        }
    }

    public String getEnvironment() {
        return environment;
    }

    public String getPrimaryPoolName() {
        return primaryPoolName;
    }

    public List<DataSourceConfig> getConfigs() {
        return configs;
    }
}
