package org.alx.article._46_java_recursive_generics_example;

public class QueryBuilderExample {
    public static void main(String[] args) {
        String query = QueryBuilder.select("id", "name", "age")
                .from("users")
                .where("age > 18")
                .orderBy("name", "age")
                .build();

        System.out.println(query);
    }
}
