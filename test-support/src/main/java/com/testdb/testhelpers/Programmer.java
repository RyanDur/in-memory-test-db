package com.testdb.testhelpers;

public class Programmer {
    private String name;
    private Unicorn unicorn;

    public Programmer(Builder builder) {
        name = builder.name;
        unicorn = builder.unicorn;
    }

    public String getName() {
        return name;
    }

    public Unicorn getUnicorn() {
        return unicorn;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private Unicorn unicorn;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withUnicorn(Unicorn unicorn) {
            this.unicorn = unicorn;
            return this;
        }

        public Programmer build() {
            return new Programmer(this);
        }
    }

    @Override
    public String toString() {
        return "Programmer{" +
                "name='" + name + '\'' +
                ", unicorn=" + unicorn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Programmer that = (Programmer) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return unicorn != null ? unicorn.equals(that.unicorn) : that.unicorn == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (unicorn != null ? unicorn.hashCode() : 0);
        return result;
    }
}
