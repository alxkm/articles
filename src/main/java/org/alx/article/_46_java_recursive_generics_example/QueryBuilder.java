package org.alx.article._46_java_recursive_generics_example;

public class QueryBuilder {

    public static SelectBuilder select(String... columns) {
        return new SelectBuilder(columns);
    }

    public static class SelectBuilder {
        private final String[] columns;

        private SelectBuilder(String[] columns) {
            this.columns = columns;
        }

        public FromBuilder from(String table) {
            return new FromBuilder(this, table);
        }

        public String[] getColumns() {
            return columns;
        }
    }

    public static class FromBuilder {
        private final SelectBuilder selectBuilder;
        private final String table;

        private FromBuilder(SelectBuilder selectBuilder, String table) {
            this.selectBuilder = selectBuilder;
            this.table = table;
        }

        public WhereBuilder where(String condition) {
            return new WhereBuilder(this, condition);
        }

        public OrderByBuilder orderBy(String... columns) {
            return new OrderByBuilder(this, columns);
        }

        public String getTable() {
            return table;
        }

        public SelectBuilder getSelectBuilder() {
            return selectBuilder;
        }
    }

    public static class WhereBuilder {
        private final FromBuilder fromBuilder;
        private final String condition;

        private WhereBuilder(FromBuilder fromBuilder, String condition) {
            this.fromBuilder = fromBuilder;
            this.condition = condition;
        }

        public OrderByBuilder orderBy(String... columns) {
            return new OrderByBuilder(fromBuilder, columns);
        }

        public String getCondition() {
            return condition;
        }

        public FromBuilder getFromBuilder() {
            return fromBuilder;
        }
    }

    public static class OrderByBuilder {
        private final FromBuilder fromBuilder;
        private final WhereBuilder whereBuilder;
        private final String[] columns;

        private OrderByBuilder(FromBuilder fromBuilder, String[] columns) {
            this.fromBuilder = fromBuilder;
            this.whereBuilder = null;
            this.columns = columns;
        }

        private OrderByBuilder(WhereBuilder whereBuilder, String[] columns) {
            this.fromBuilder = whereBuilder.getFromBuilder();
            this.whereBuilder = whereBuilder;
            this.columns = columns;
        }

        public String build() {
            StringBuilder query = new StringBuilder("SELECT ");

            String[] selectColumns = fromBuilder.getSelectBuilder().getColumns();
            query.append(String.join(", ", selectColumns));
            query.append(" FROM ").append(fromBuilder.getTable());

            if (whereBuilder != null) {
                query.append(" WHERE ").append(whereBuilder.getCondition());
            }

            if (columns.length > 0) {
                query.append(" ORDER BY ").append(String.join(", ", columns));
            }

            return query.toString();
        }
    }
}
