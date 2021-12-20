package server.base.rest;

import http.Handlers.custom.HTTPAbstractHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
public class HelloServiceMockTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    Environment env;

    @Test
    void  readConfigLocationTest ()  {
        String path = env.getProperty("http.baseHandler.root");
        if (path==null) {
            Class<?> baseClassPath = HTTPAbstractHandler.class;
            URL rootLocation = baseClassPath.getProtectionDomain().getCodeSource().getLocation();
            String relativeLocation = baseClassPath.getPackageName();
            path = rootLocation.getPath() + relativeLocation;
        }
        assertNotNull(path);
    }


    @Test
    void  readDefaultHandlersLocationTest ()  {
            Class<?> baseClassPath = HTTPAbstractHandler.class;
            URL rootLocation = baseClassPath.getProtectionDomain().getCodeSource().getLocation();
            String relativeLocation = baseClassPath.getPackageName();
            String path = rootLocation.getPath() + relativeLocation.replace(".", File.separator);

            assertNotNull(path);
    }

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
        mockMvc.perform(MockMvcRequestBuilders.multipart("/clientData")
                         .file(firstFile)
                         .file(secondFile)
                         .file(jsonFile)
                        .file(originFile)
                        .param("some-random", "4"))
                .andExpect(status().is(200))
                .andExpect(content().string("\"" + HttpStatus.OK.name()+"\""));

    }
    @Test
    public void octetStreamRequestTest() throws Exception {
        MockMultipartFile originFile = new MockMultipartFile("binary", "orig", MediaType.APPLICATION_OCTET_STREAM_VALUE, "bar".getBytes());

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/clientData")
                   //     .accept(MediaType.APPLICATION_OCTET_STREAM)
                        .content("bar".getBytes())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().string("\"" + HttpStatus.OK.name()+"\""));
    }
 }

