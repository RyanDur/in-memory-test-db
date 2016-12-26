package com.foo.doamins;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = Programmer.Builder.class)
public class Programmer {
    private final String name;

    private Programmer(Builder builder) {
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

        public Programmer build() {
            return new Programmer(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Programmer that = (Programmer) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
