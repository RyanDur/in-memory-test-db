package com.foo.doamins;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = Unicorn.Builder.class)
public class Unicorn {
    private final String name;

    private Unicorn(Builder builder) {
        name = builder.name;
    }

    public String getName() {
        return name;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder()
    public static class Builder {
        private String name;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Unicorn build() {
            return new Unicorn(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Unicorn unicorn = (Unicorn) o;

        return name != null ? name.equals(unicorn.name) : unicorn.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Unicorn{" +
                "name='" + name + '\'' +
                '}';
    }
}
