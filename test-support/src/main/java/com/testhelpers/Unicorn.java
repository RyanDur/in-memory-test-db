package com.testhelpers;

public class Unicorn {
    private String name;
    private Programmer programmer;

    private Unicorn(Builder builder) {
        name = builder.name;
        programmer = builder.programmer;
    }

    public String getName() {
        return name;
    }

    public Programmer getProgrammer() {
        return programmer;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private Programmer programmer;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withProgrammer(Programmer programmer) {
            this.programmer = programmer;
            return this;
        }

        public Unicorn build() {
            return new Unicorn(this);
        }
    }

    @Override
    public String toString() {
        return "Unicorn {\n" +
                "\tname = '" + name + "\'" +
                ",\n\tprogrammer = " + programmer.getName() +
                "\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Unicorn unicorn = (Unicorn) o;

        if (name != null ? !name.equals(unicorn.name) : unicorn.name != null) return false;
        return programmer != null ? programmer.equals(unicorn.programmer) : unicorn.programmer == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (programmer != null ? programmer.hashCode() : 0);
        return result;
    }
}
