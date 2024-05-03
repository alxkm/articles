package org.alx.article._10_introduction_to_stream_api;

import java.util.List;

public class Person {
    private String name;
    List<Integer> transactions;
    int score;

    public Person(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public Person(String name, List<Integer> transactions) {
        this.name = name;
        this.transactions = transactions;
    }

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Integer> transactions) {
        this.transactions = transactions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
