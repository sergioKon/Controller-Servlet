
package server.base.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MainControllerTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void noContentTest()  {

        ResponseEntity<String> response = restTemplate.getForEntity("/anyTypeClient", String.class);
        assertEquals(response.getBody(), "\""+ HttpStatus.NO_CONTENT.name()+ "\"");
    }

    @Test
    public void withContentTest()  {
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");
        ResponseEntity<String> response = restTemplate.getForEntity("/anyTypeClient", String.class,params);
        assertEquals(response.getBody(), "\""+ HttpStatus.NO_CONTENT.name()+ "\"");
    }
}
