package org.alx.article._24_solid.single_responsibiliy_principle;

public class Movie {
    private final SubscriptionType subscriptionType;

    public Movie(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }
}
