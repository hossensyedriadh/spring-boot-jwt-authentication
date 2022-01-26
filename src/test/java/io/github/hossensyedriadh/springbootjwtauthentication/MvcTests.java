package io.github.hossensyedriadh.springbootjwtauthentication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class MvcTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_return_400_bad_request() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/authentication/authenticate")
                        .content("""
                                {
                                    "username": "mock_mvc_user",
                                    "password": "mock_mvc_password"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @WithMockUser(username = "test", password = "test", authorities = {"ROLE_ADMINISTRATOR"})
    @Test
    void should_respond_with_200_admin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/test/admin"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @WithMockUser(username = "test", password = "test", authorities = {"ROLE_MODERATOR"})
    @Test
    void should_respond_with_200_mod() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/test/mod"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @WithMockUser(username = "test", password = "test", authorities = {"ROLE_MEMBER"})
    @Test
    void should_respond_with_200_member() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/test/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}
