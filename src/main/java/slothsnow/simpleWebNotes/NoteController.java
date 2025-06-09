package slothsnow.simpleWebNotes;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/note")
public class NoteController {
    private NoteRepository noteRepository;

    private NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNote(@PathVariable Number id) {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isPresent()) {
            return new ResponseEntity<>(note.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable Number id) {
        if (noteRepository.existsById(id)) {
            noteRepository.deleteById(id);
        }
        else {
            return new ResponseEntity<>("Note not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("{}", HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<Number> createNote(@RequestBody String noteS) {
        DocumentContext parsedNote = JsonPath.parse(noteS);
        Note note = new Note(parsedNote.read("$.text"), 
                        parsedNote.read("$.owner"), 
                        parsedNote.read("$.publicNote"));
        Note savedNote = noteRepository.save(note);
        return new ResponseEntity<>(savedNote.getId(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateNote(@PathVariable Number id, @RequestBody String noteS) {
        DocumentContext parsedNote = JsonPath.parse(noteS);
        if (noteRepository.existsById(id)) {
            Note note = new Note(parsedNote.read("$.text"),
                parsedNote.read("$.owner"),
                parsedNote.read("$.publicNote"));
        note.setId(id);
        noteRepository.save(note);
    }
    else {
        return new ResponseEntity<>("Note not found", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>("{}", HttpStatus.OK);
}
}