package org.alx.article._24_solid.single_responsibiliy_principle;

import java.util.Date;

public class User {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final SubscriptionType subscriptionType;
    private final Date subscriptionExpirationDate;

    public User(String firstName, String lastName, String email, SubscriptionType subscriptionType, Date subscriptionExpirationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.subscriptionType = subscriptionType;
        this.subscriptionExpirationDate = subscriptionExpirationDate;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public boolean hasExtraAccess() {
        Date now = new Date();
        return subscriptionType == SubscriptionType.VIP && subscriptionExpirationDate.after(now);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public Date getSubscriptionExpirationDate() {
        return subscriptionExpirationDate;
    }
}