package softuniBlog.controllerTest;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StringUtils;
import softuniBlog.exceptions.AccessDeniedEx;

import static javax.management.Query.value;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
class HomeControllerImplTest {

  @Autowired private MockMvc mockMvc;


  @Test
  void index() throws Exception {

    MvcResult mvcResult = this.mockMvc.perform(get("/"))
            .andDo(print()).andExpect(status().isAccepted())
            .andExpect(jsonPath("$[0].name").value("Cool category !!"))
            .andReturn();

    Assert.assertEquals("application/json",
            mvcResult.getResponse().getContentType());
  }

  @Test
  void articles() throws Exception {

    MvcResult mvcResult = this.mockMvc.perform(get("/category/1"))
            .andDo(print()).andExpect(status().isAccepted())
            .andExpect(jsonPath("$[0].title").value("FOOTBALL"))
            .andReturn();

    Assert.assertEquals("application/json",
            mvcResult.getResponse().getContentType());
  }

  @Test
  void accessDenied() throws Exception {

  }
}
