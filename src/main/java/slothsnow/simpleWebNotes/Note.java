package slothsnow.simpleWebNotes;

import org.springframework.data.annotation.Id;

public class Note {
    private @Id Number id;
    private String text;
    private String owner;
    private boolean publicNote;

    public Note(String text, String owner, boolean publicNote) {
        this.text = text;
        this.owner = owner;
        this.publicNote = publicNote;
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
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isPublicNote() {
        return publicNote;
    }

    public void setPublicNote(boolean publicNote) {
        this.publicNote = publicNote;
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", text:'" + text + '\'' +
                ", owner:'" + owner + '\'' +
                ", publicNote:" + publicNote +
                '}';
    }
}
