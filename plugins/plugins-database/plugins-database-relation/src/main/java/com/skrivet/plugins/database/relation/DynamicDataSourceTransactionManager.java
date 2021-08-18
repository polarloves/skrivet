package com.skrivet.plugins.database.relation;

import org.springframework.beans.BeansException;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

public class DynamicDataSourceTransactionManager extends DataSourceTransactionManager implements ApplicationListener<RefreshEvent>, ApplicationContextAware {
    private ApplicationContext applicationContext;
    private String dataSourceName;
    private boolean dynamic;

    @Override
    public void onApplicationEvent(RefreshEvent event) {
        if (dynamic) {
            DataSource dataSource = applicationContext.getBean(dataSourceName, DataSource.class);
            super.setDataSource(dataSource);
        }
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }
}