package slothsnow.simpleWebNotes;

import org.springframework.data.annotation.Id;

public class Note {
    private @Id Number id;
    private String text;
    private String owner;

    public Note(String text, String owner) {
        this.text = text;
        this.owner = owner;
    }

    public String getText() {
        return text;
    }

    public String getOwner() {
        return owner;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        throw new UnsupportedOperationException("Cannot set ID of note");
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
