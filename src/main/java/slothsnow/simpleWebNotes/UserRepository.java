package slothsnow.simpleWebNotes;

import org.springframework.data.repository.CrudRepository;

interface UserRepository extends CrudRepository<User, String> {
}
