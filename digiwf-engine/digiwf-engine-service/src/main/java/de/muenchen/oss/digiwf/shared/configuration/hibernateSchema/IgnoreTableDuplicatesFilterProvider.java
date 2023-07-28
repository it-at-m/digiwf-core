package de.muenchen.oss.digiwf.shared.configuration.hibernateSchema;


import org.hibernate.tool.schema.internal.DefaultSchemaFilter;
import org.hibernate.tool.schema.spi.SchemaFilter;
import org.hibernate.tool.schema.spi.SchemaFilterProvider;

/**
 * The class is required for managing the validation of the Camunda tables.
 * Camunda creates his own tables. When we are using entities for read out the saved tasks, hibernate wants to create the tables two times and an error is thrown.
 * So we have to exclude the Camunda table for the validation
 *
 * stack overflow link with another example: https://stackoverflow.com/questions/60420562/how-do-i-skip-certain-entity-classes-from-being-created-as-a-table-in-h2-in-me/60421054#60421054
 * docs of SchemaFilterProvider:  https://docs.jboss.org/hibernate/orm/6.0/javadocs/org/hibernate/tool/schema/spi/SchemaFilterProvider.html
 */
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
