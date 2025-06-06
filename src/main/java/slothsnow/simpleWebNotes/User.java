package slothsnow.simpleWebNotes;

import org.springframework.data.annotation.Id;
import java.util.ArrayList;

public class User {
    private @Id String name;
    private ArrayList<Number> notes;

    public User(String name) {
        this.name = name;
        this.notes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Number> getNotes() {
        return notes;
    }

    public void addNote(Number note) {
        this.notes.add(note);
    }

    public void removeNote(Number note) {
        this.notes.remove(note);
    }
}
