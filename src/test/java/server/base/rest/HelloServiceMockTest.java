package server.base.rest;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.utils.common.Converter.addApostrophe;
import static com.utils.common.Converter.removeApostrophe;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.class)
public class HelloServiceMockTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;
    MvcResult mvcResult = null;


    @Test
    public void multipartRequestTestWithOtherKeys() throws Exception {

        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        MockMultipartFile secondFile = new MockMultipartFile("data", "other-file-name.data", "text/plain", "some other type".getBytes());
        MockMultipartFile jsonFile = new MockMultipartFile("json", "", "application/json", "{\"json\": \"someValue\"}".getBytes());
        MockMultipartFile file = new MockMultipartFile("dataXML", "orig", MediaType.APPLICATION_XML_VALUE, "bar".getBytes());

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/anyTypeClient")
                        .file(firstFile)
                        .file(secondFile)
                        .file(file)
                        .file(jsonFile)
                        .param("some-random", "4"))
                .andExpect(status().is(200))
                .andExpect(content().string(addApostrophe("OK")));
    }

    @Test
    public void multipartRequestTestWithSingleKey() throws Exception {
        final String FILE_KEY = "fileKey";
        MockMultipartFile firstFile = new MockMultipartFile(FILE_KEY, "filename.txt", "text/plain", "some xml".getBytes());
        MockMultipartFile secondFile = new MockMultipartFile(FILE_KEY, "other-file-name.data", "text/plain", "some other type".getBytes());
        MockMultipartFile jsonFile = new MockMultipartFile(FILE_KEY, "", "application/json", "{\"json\": \"someValue\"}".getBytes());
        MockMultipartFile originFile = new MockMultipartFile(FILE_KEY, "orig", MediaType.APPLICATION_XML_VALUE, "bar".getBytes());

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.multipart("/anyTypeClient")
                        .file(firstFile)
                        .file(secondFile)
                        .file(jsonFile)
                        .file(originFile)
                        .param("some-random", "4"))
                .andExpect(status().is(HttpStatus.OK.value()));
        //   .andExpect(content().string(Converter.removeApostrophe("OK")));
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        assertEquals(removeApostrophe(contentAsString), "OK");
    }

    @Test
    public void octetStreamRequestTest() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(post("/anyTypeClient")
                        //   .accept(MediaType.APPLICATION_OCTET_STREAM)
                        .content("bar".getBytes())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(content().string(addApostrophe("OK")));
    }

    @Test()
    public void jsonRequestValidTest() throws Exception {
        String jsonInputString = "{\"name\":\" Upendra\", \"job\": \"Programmer\"}";
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mvcResult = mockMvc.perform(post("/anyTypeClient")
                            .content(jsonInputString.getBytes())
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                     .andExpect(status().is(HttpStatus.OK.value()))
                    .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(response.getStatus(), HttpStatus.OK.value());
        assertEquals(response.getContentAsString(StandardCharsets.UTF_8),  addApostrophe("OK"));
    }


    @Test()
    public void jsonRequestNotValid() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
         mockMvc.perform(
                        post("/anyTypeClient")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("bar".getBytes()))
                .andDo(print())
                .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IOException))
               .andReturn();

        Exception returnException = mvcResult.getResolvedException();
        if (returnException != null) {
            assertTrue(returnException instanceof IllegalArgumentException);
            assertEquals(returnException.getMessage(), "the contents is not valid json ");
        }
    }

    @Test()
    public void XmlRequestValid() throws Exception {
        String xml ="<company> <firstname>yong</firstname> <lastname>Fain</lastname>" +
                      "<nickname>Boris</nickname> <salary currency=\"USD\">100000</salary> </company>";
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(post("/anyTypeClient")
                        .contentType(MediaType.APPLICATION_XML_VALUE)
                        .content(xml.getBytes()))
               // .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(result -> assertEquals(result.getResponse().getContentAsString(), addApostrophe("OK")))
                .andReturn();
        }
  }
