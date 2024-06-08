package org.alx.article._43_thread_local_vs_scoped_value;

public class ScopedValueSimpleExample {
    private static final ScopedValue<String> scopedValue = ScopedValue.newInstance();

    public static void main(String[] args) {
        ScopedValue.where(scopedValue, "Scoped Value Example").run(() -> {
            String value = scopedValue.get(); // Retrieves "Scoped Value Example" within this scope
            System.out.println(value);
        });
    }
}
