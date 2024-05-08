package org.alx.article._24_solid.single_responsibiliy_principle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AccessManager {
    public static boolean hasUnlimitedContentAccess(User user) {
        Date now = new Date();
        return user.getSubscriptionType() == SubscriptionType.VIP
                && user.getSubscriptionExpirationDate().after(now);
    }

    public static List<Movie> getBasicContent(List<Movie> movies) {
        return movies.stream()
                .filter(movie -> SubscriptionType.STANDARD.equals(movie.getSubscriptionType()))
                .collect(Collectors.toList());
    }

    public static List<Movie> getPremiumContent(List<Movie> movies) {
        return movies
                .stream()
                .filter(movie -> SubscriptionType.VIP.equals(movie.getSubscriptionType()))
                .collect(Collectors.toList());
    }

    public static List<Movie> getContentForUserWithBasicAccess(List<Movie> movies) {
        return getBasicContent(movies);
    }

    public static List<Movie> getContentForUserWithUnlimitedAccess(List<Movie> movies, User user) {
        if (SubscriptionType.VIP.equals(user.getSubscriptionType())) {
            return getPremiumContent(movies);
        }
        return new ArrayList<>();
    }
}