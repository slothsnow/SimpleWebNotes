package slothsnow.simpleWebNotes;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void getExistingAndPublicNote() throws IOException {
        ResponseEntity<String> response = this.restTemplate.getForEntity("/note/15", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext parsedResponse = JsonPath.parse(response.getBody());
        Number id = parsedResponse.read("$.id");
        assertThat(id).isEqualTo(15);
        String text = parsedResponse.read("$.text");
        assertThat(text).isEqualTo("This is just an example.");
        String owner = parsedResponse.read("$.owner");
        assertThat(owner).isEqualTo("exampleUser");
        boolean publicNote = parsedResponse.read("$.publicNote");
        assertThat(publicNote).isTrue();
    }

    /*
    @Test
    void getExistingAndPrivateNote() throws IOException {
        ResponseEntity<String> response = this.restTemplate.getForEntity("/note/2", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }*/

    @Test
    void getNonExistingNote() {
        ResponseEntity<String> response = this.restTemplate.getForEntity("/note/14", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void deleteAnExistingNote() {
        ResponseEntity<String> response = this.restTemplate.exchange("/note/15", HttpMethod.DELETE, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        ResponseEntity<String> getResponse = this.restTemplate.getForEntity("/note/15", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void deleteANonExistingNote() {
        ResponseEntity<String> response = this.restTemplate.exchange("/note/2", HttpMethod.DELETE, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void createANote() {
        ResponseEntity<String> response = this.restTemplate.postForEntity("/note", "{\"text\":\"This is a test note.\",\"owner\":\"testUser\",\"publicNote\":true}", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        ResponseEntity<String> getResponse = this.restTemplate.getForEntity("/note/" + response.getBody(), String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext parsedResponse = JsonPath.parse(getResponse.getBody());
        Number id = parsedResponse.read("$.id");
        Assertions.assertNotNull(response.getBody());
        assertThat(id).isEqualTo(Integer.parseInt(response.getBody()));
        String text = parsedResponse.read("$.text");
        assertThat(text).isEqualTo("This is a test note.");
        String owner = parsedResponse.read("$.owner");
        assertThat(owner).isEqualTo("testUser");
    }

    @Test
    @DirtiesContext
    void putAnExistingNote() {
        Note note = new Note("This is another test note.", "testUser", true);
        note.setId(15);
        HttpEntity<Note> request = new HttpEntity<>(note);
        ResponseEntity<String> response = this.restTemplate.exchange("/note/15", HttpMethod.PUT, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        ResponseEntity<String> getResponse = this.restTemplate.getForEntity("/note/15", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        DocumentContext parsedResponse = JsonPath.parse(getResponse.getBody());
        Number id = parsedResponse.read("$.id");
        assertThat(id).isEqualTo(15);
        String text = parsedResponse.read("$.text");
        assertThat(text).isEqualTo("This is another test note.");
    }
}