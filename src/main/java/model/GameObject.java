package model;

import java.util.Objects;

public abstract class GameObject {
    String id;

    public void setId(String id) {
        this.id=id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameObject gameObject = (GameObject) o;
        return id.equals(gameObject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
