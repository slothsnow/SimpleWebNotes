package slothsnow.simpleWebNotes;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getExistingUser() throws IOException {
        ResponseEntity<String> response = this.restTemplate.getForEntity("/users/testUser", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualToIgnoringWhitespace(
                new String(getClass().getResourceAsStream("/expectedResponseGetExistingUser.json").readAllBytes())
        );
    }

    @Test
    void getNonExistingUser() {
        ResponseEntity<String> response = this.restTemplate.getForEntity("/users/nonExistingUser", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
