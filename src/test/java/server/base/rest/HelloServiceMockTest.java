package server.base.rest;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.class)
public class HelloServiceMockTest {

    @Autowired
     WebApplicationContext webApplicationContext;

    @Test
    public void multipartRequestTestWithOtherKeys() throws Exception {

        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        MockMultipartFile secondFile = new MockMultipartFile("data", "other-file-name.data", "text/plain", "some other type".getBytes());
        MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json", "{\"json\": \"someValue\"}".getBytes());


        MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload")
                   //     .file(firstFile)
                  //      .file(secondFile)
                        .file(file)
                        .param("some-random", "4"))
                .andExpect(status().is(200))
                .andExpect(content().string("success"));
    }
    @Test
    public void multipartRequestTestWithSingleKey() throws Exception {
        final String FILE_KEY= "fileKey";
        MockMultipartFile firstFile = new MockMultipartFile(FILE_KEY, "filename.txt", "text/plain", "some xml".getBytes());
        MockMultipartFile secondFile = new MockMultipartFile(FILE_KEY, "other-file-name.data", "text/plain", "some other type".getBytes());
        MockMultipartFile jsonFile = new MockMultipartFile(FILE_KEY, "", "application/json", "{\"json\": \"someValue\"}".getBytes());
        MockMultipartFile originFile = new MockMultipartFile(FILE_KEY, "orig", MediaType.APPLICATION_XML_VALUE, "bar".getBytes());

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/anyTypeClient")
                         .file(firstFile)
                         .file(secondFile)
                         .file(jsonFile)
                        .file(originFile)
                        .param("some-random", "4"))
                .andExpect(status().is(200))
                .andExpect(content().string("success"));

    }
    @Test
    public void octetStreamRequestTest() throws Exception {
        MockMultipartFile originFile = new MockMultipartFile("binary", "orig", MediaType.APPLICATION_OCTET_STREAM_VALUE, "bar".getBytes());

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/anyTypeClient")
                        .accept(MediaType.APPLICATION_OCTET_STREAM)
                        .content("bar".getBytes())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andExpect(status().is(200))
               .andExpect(content().string("success"));
    }
 }

