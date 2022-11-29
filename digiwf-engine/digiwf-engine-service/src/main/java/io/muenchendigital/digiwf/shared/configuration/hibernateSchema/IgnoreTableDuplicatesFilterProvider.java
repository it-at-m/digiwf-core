package io.muenchendigital.digiwf.shared.configuration.hibernateSchema;


import org.hibernate.tool.schema.internal.DefaultSchemaFilter;
import org.hibernate.tool.schema.spi.SchemaFilter;
import org.hibernate.tool.schema.spi.SchemaFilterProvider;

public class IgnoreTableDuplicatesFilterProvider implements SchemaFilterProvider {

    @Override
    public SchemaFilter getCreateFilter() {
        return IgnoreTableDuplicatesFilter.INSTANCE;
    }

    @Override
    public SchemaFilter getDropFilter() {
        return DefaultSchemaFilter.INSTANCE;
    }

    @Override
    public SchemaFilter getMigrateFilter() {
        return IgnoreTableDuplicatesFilter.INSTANCE;
    }

    @Override
    public SchemaFilter getValidateFilter() {
        return IgnoreTableDuplicatesFilter.INSTANCE;
    }
}