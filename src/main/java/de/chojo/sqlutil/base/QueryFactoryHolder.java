/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) 2022 RainbowDashLabs and Contributor
 */

package de.chojo.sqlutil.base;

import de.chojo.sqlutil.wrapper.QueryBuilderConfig;
import de.chojo.sqlutil.wrapper.QueryBuilderFactory;
import de.chojo.sqlutil.wrapper.stage.QueryStage;

import javax.sql.DataSource;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Base class which provides a factory for easy usage.
 * <p>
 * Can be used instead of a {@link DataHolder}
 */
public abstract class QueryFactoryHolder extends DataHolder {
    private final QueryBuilderFactory factory;

    /**
     * Create a new QueryFactoryholder
     *
     * @param dataSource datasource
     * @param config     factory config
     */
    public QueryFactoryHolder(DataSource dataSource, QueryBuilderConfig config) {
        super(dataSource);
        factory = new QueryBuilderFactory(new AtomicReference<>(config), dataSource);
    }
    public QueryFactoryHolder(DataSource dataSource) {
        super(dataSource);
        factory = new QueryBuilderFactory(QueryBuilderConfig.defaultConfig(), dataSource);
    }

    /**
     * Create a new query builder with a defined return type. Use it for selects.
     *
     * @param clazz class of required return type. Doesn't matter if you want a list or single result.
     * @param <T>   type if result as class
     * @return a new query builder in a {@link QueryStage}
     */
    public <T> QueryStage<T> builder(Class<T> clazz) {
        return factory.builder(clazz);
    }

    /**
     * Create a new Query builder without a defined return type. Use it for updates.
     *
     * @return a new query builder in a {@link QueryStage}
     */
    public QueryStage<Void> builder() {
        return factory.builder();
    }

    /**
     * Get the underlying factory
     * @return query factory
     */
    public QueryBuilderFactory factory() {
        return factory;
    }

    /**
     * Get the underlying data source
     * @return datasource
     */
    public DataSource source() {
        return factory.source();
    }
}
