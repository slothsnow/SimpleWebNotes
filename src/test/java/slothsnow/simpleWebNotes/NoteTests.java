package slothsnow.simpleWebNotes;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Test
    void getExistingAndPrivateNote() throws IOException {
        ResponseEntity<String> response = this.restTemplate.getForEntity("/note/2", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void getNonExistingNote() {
        ResponseEntity<String> response = this.restTemplate.getForEntity("/note/14", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
