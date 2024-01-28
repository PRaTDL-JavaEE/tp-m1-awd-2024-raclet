package doremi.domain;

public enum Genre {
    BLUES,
    ELECTRONIC,
    FOLK,
    INDIE,
    JAZZ,
    METAL,
    POP,
    RAP,
    REGGAE,
    ROCK;

    @Override
    public String toString() {
        return name();
    }
}
