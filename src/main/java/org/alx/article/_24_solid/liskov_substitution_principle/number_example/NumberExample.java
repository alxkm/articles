package org.alx.article._24_solid.liskov_substitution_principle.number_example;

class NumberAdder {
    public int add(int a, int b) {
        return a + b;
    }
}

class ExtendedNumberAdder extends NumberAdder {
    @Override
    public int add(int a, int b) {
        throw new UnsupportedOperationException("This operation is not supported");
    }
}

public class NumberExample {
}
