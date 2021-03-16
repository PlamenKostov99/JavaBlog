package softuniBlog.controllerTest;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TagControllerImplTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void articlesWithTag() throws Exception {

    mockMvc.perform(get("/tag/{name}", "tenis"))
            .andDo(print()).andExpect(status().isAccepted())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.name").value("tenis"));


  }
}
