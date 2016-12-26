package com.foo.endpoint;

public class Programmer {
    private String name;

    public Programmer() {
    }

    private Programmer(Builder builder) {
        name = builder.name;
    }

    public String getName() {
        return name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Programmer build() {
            return new Programmer(this);
        }
    }

}
