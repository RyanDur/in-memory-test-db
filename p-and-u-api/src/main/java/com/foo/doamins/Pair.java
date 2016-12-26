package com.foo.doamins;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = Pair.Builder.class)
public class Pair {

    private final Programmer programmer;
    private final Unicorn unicorn;

    private Pair(Builder builder) {
        programmer = builder.programmer;
        unicorn = builder.unicorn;
    }

    public Programmer getProgrammer() {
        return programmer;
    }

    public Unicorn getUnicorn() {
        return unicorn;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder()
    public static class Builder {
        private Programmer programmer;
        private Unicorn unicorn;

        public Builder withProgrammer(Programmer programmer) {
            this.programmer = programmer;
            return this;
        }

        public Builder withUnicorn(Unicorn unicorn) {
            this.unicorn = unicorn;
            return this;
        }

        public Pair build() {
            return new Pair(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        if (programmer != null ? !programmer.equals(pair.programmer) : pair.programmer != null) return false;
        return unicorn != null ? unicorn.equals(pair.unicorn) : pair.unicorn == null;
    }

    @Override
    public int hashCode() {
        int result = programmer != null ? programmer.hashCode() : 0;
        result = 31 * result + (unicorn != null ? unicorn.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "programmer=" + programmer +
                ", unicorn=" + unicorn +
                '}';
    }
}
