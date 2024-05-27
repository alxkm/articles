package org.alx.article._38_builder.user_builder;

public class User {
    private String userId;
    private String name;

    private User() {
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public static Builder newBuilder() {
        return new User().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setUserId(String userId) {
            User.this.userId = userId;

            return this;
        }

        public Builder setName(String name) {
            User.this.name = name;

            return this;
        }

        public User build() {
            return User.this;
        }

    }

    public static void main(String[] args) {
        User user = User.newBuilder().setUserId("123").setName("John").build();
    }
}