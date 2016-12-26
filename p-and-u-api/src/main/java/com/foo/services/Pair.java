package com.foo.services;

public class Pair {

    private String programmer;
    private String unicorn;

    private Pair(Builder builder) {
        programmer = builder.programmer;
        unicorn = builder.unicorn;
    }

    public String getProgrammer() {
        return programmer;
    }

    public String getUnicorn() {
        return unicorn;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String programmer;
        private String unicorn;

        public Builder withProgrammer(String programmer) {
            this.programmer = programmer;
            return this;
        }

        public Builder withUnicorn(String unicorn) {
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
}
