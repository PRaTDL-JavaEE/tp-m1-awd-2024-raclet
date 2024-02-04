package doremi.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Band {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String name;

    private boolean active;

    public Band() { }

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

    public Long getId() {
        return id;
    }
}
