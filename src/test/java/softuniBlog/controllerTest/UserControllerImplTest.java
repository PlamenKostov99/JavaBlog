package softuniBlog.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import softuniBlog.bindingModel.UserBindingModel;
import softuniBlog.entity.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerImplTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void registerProcess() throws Exception {

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/register")
                .content(
                    asJsonString(
                        new UserBindingModel("paqksrezachka@abv.bg", "Plamen Kostov", "1212", "1212")))
                .contentType("application/json"))
        .andExpect(status().isAccepted())
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists());
  }

  @Test
  void profilePage() throws Exception {

    mockMvc.perform(get("/profile/{id}", 4))
            .andDo(print()).andExpect(status().isAccepted())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.email").value("paqksrezachka@abv.bg"));

  }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
