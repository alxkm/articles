package org.alx.article._65_visitor_pattern_in_java;

// Visitor interface
interface Visitor {
    void visit(Book book);

    void visit(Fruit fruit);
}

// Concrete Visitor
class ShoppingCartVisitor implements Visitor {
    @Override
    public void visit(Book book) {
        System.out.println("Book: " + book.getTitle() + ", Price: " + book.getPrice());
    }

    @Override
    public void visit(Fruit fruit) {
        System.out.println("Fruit: " + fruit.getName() + ", Weight: " + fruit.getWeight() + ", Price: " + fruit.getPrice());
    }
}

// Element interface
interface ItemElement {
    void accept(Visitor visitor);
}

// Concrete Element - Book
class Book implements ItemElement {
    private String title;
    private double price;

    public Book(String title, double price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

// Concrete Element - Fruit
class Fruit implements ItemElement {
    private String name;
    private double weight;
    private double price;

    public Fruit(String name, double weight, double price) {
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

// Client
public class VisitorPatternDemo {
    public static void main(String[] args) {
        ItemElement[] items = new ItemElement[]{
                new Book("Design Patterns", 30.00),
                new Fruit("Apple", 2.0, 3.00)
        };

        Visitor visitor = new ShoppingCartVisitor();
        for (ItemElement item : items) {
            item.accept(visitor);
        }
    }
}