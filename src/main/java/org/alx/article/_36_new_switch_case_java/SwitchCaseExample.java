package org.alx.article._36_new_switch_case_java;

public class SwitchCaseExample {

    public static void simpleSwitchCase(String s) {
        switch (s) {
            case "stateA" -> System.out.println("State A started");
            case "stateB" -> System.out.println("State B started");
            case "stateC" -> System.out.println("State C started");
            default -> System.out.println("Parameter is unknown");
        }
    }

    public static void beforePatternMatching(Object o) {
        if (o instanceof String) {
            String str = (String) o;
            System.out.println("String: " + str);
        } else if (o instanceof Integer) {
            Integer i = (Integer) o;
            System.out.println("Integer: " + i);
        }
    }

    public static void printValueUsingPatternMatching(Object obj) {
        if (obj instanceof Integer i) {
            System.out.println("Integer: " + i);
        } else if (obj instanceof Long l) {
            System.out.println("Long: " + l);
        } else if (obj instanceof Double d) {
            System.out.println("Double: " + d);
        } else if (obj instanceof String s) {
            System.out.println("String: " + s);
        }
    }

    public static String getFormatStringUsingSwitchPatternMatching(Object obj) {
        return switch (obj) {
            case Integer i -> String.format("Integer: %d", i);
            case Long l -> String.format("Long: %d", l);
            case Double d -> String.format("Double: %f", d);
            case String s -> String.format("String: %s", s);
            default -> "Unknown";
        };
    }

    public static void patternMatchingWithRecords() {
        record Point(int x, int y) {
        }

        Point p = new Point(1, 1);

        if (p instanceof Point point) {
            System.out.println("X: " + point.x + ", Y: " + point.y);
        }
    }

    public static void nullHandlingOutsideSwitch(Object o) {
        if (o == null) {
            System.out.println("Null as input");
        } else {
            switch (o) {
                case String s -> System.out.println("String: " + s);
                case Integer i -> System.out.println("Integer: " + i);
                default -> System.out.println("Other data:" + o);
            }
        }
    }

    public static void nullHandlingWithSwitch(Object o) {
        switch (o) {
            case null -> System.out.println("Null as input");
            case String s -> System.out.println("String: " + s);
            case Integer i -> System.out.println("Integer: " + i);
            default -> System.out.println("Other data:" + o);
        }
    }

    public static String getStringOrEmptyWithIf(Object o) {
        return switch (o) {
            case String s -> {
                if (!s.isEmpty()) {
                    yield s;
                } else {
                    yield "";
                }
            }
            default -> "";
        };
    }

//    static int getIntegerUsingGuardPattern(Object o) {
//        return switch (o) {
//            case Integer i &&i > 1 ->i;
//                case null, default -> 0;
//        };
//    }

//    static int integerCheckUsingParenthesizedPatterns(Object o) {
//        return switch (o) {
//            case Integer i &&(0 < i && i < 100) ->i;
//                case null, default -> 0;
//        };
//    }
//
//    public static void coveringNotAllStates(Object obj) {
//        switch (obj) {
//            case String s -> System.out.println("String: " + s);
//        }
//    }

    public static void coveringAllStates(Object obj) {
        switch (obj) {
            case String s -> System.out.println("String: " + s);
            case Integer i -> System.out.println("Integer: " + i);
            case Long l -> System.out.println("Long: " + l);
            case Double d -> System.out.println("Double: " + d);
            default -> System.out.println("Unknown type");
        }
    }

//    public static void orderingSubclasses(Object obj) {
//        switch (obj) {
//            case CharSequence cs -> System.out.println("CharSequence: " + cs);
//            case String s -> System.out.println("String: " + s);
//            default -> System.out.println("Unknown type");
//        }
//    }

    public static void main(String[] args) {
        simpleSwitchCase("stateA");
        System.out.println();

        beforePatternMatching("hello");
        System.out.println();

        printValueUsingPatternMatching(1);
        System.out.println();

        printValueUsingPatternMatching(1);
        System.out.println();

        System.out.println(getFormatStringUsingSwitchPatternMatching(1));
        System.out.println();

        patternMatchingWithRecords();
        System.out.println();

        nullHandlingOutsideSwitch("New string");
        System.out.println();

        nullHandlingOutsideSwitch(null);
        System.out.println();

        nullHandlingWithSwitch("New string");
        System.out.println();

        nullHandlingWithSwitch(null);
        System.out.println();

        System.out.println(getStringOrEmptyWithIf("Some string"));
        System.out.println();
    }
}
