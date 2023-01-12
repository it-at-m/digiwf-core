package io.muenchendigital.digiwf.shared.configuration.hibernateSchema;

import org.hibernate.boot.model.relational.Namespace;
import org.hibernate.boot.model.relational.Sequence;
import org.hibernate.mapping.Table;
import org.hibernate.tool.schema.spi.SchemaFilter;

import java.util.List;

/**
 * Filter for excluding Camunda tables
 */
public class IgnoreTableDuplicatesFilter implements SchemaFilter {

    private final List<String> ignoredTables = List.of("ACT_RU_IDENTITYLINK", "ACT_RU_TASK");
    public static final IgnoreTableDuplicatesFilter INSTANCE = new IgnoreTableDuplicatesFilter();

    @Override
    public boolean includeNamespace(Namespace namespace) {
        return true;
    }

    @Override
    public boolean includeTable(Table table) {
        return !ignoredTables.contains(table.getName());
    }

    @Override
    public boolean includeSequence(Sequence sequence) {
        return true;
    }
}