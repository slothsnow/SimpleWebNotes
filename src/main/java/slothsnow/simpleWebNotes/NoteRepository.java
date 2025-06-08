package slothsnow.simpleWebNotes;

import org.springframework.data.repository.CrudRepository;

interface NoteRepository extends CrudRepository<Note, Number> {
}
