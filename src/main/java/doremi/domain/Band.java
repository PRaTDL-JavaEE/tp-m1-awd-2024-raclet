package doremi.domain;

public class Band {

    private String name;

    private boolean active;

    public Band(String name, boolean active) {
        this.setName(name);
        this.setActive(active);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
