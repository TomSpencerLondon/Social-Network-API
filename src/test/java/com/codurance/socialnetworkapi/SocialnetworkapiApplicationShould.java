package com.codurance.socialnetworkapi;

import com.codurance.social_network.domain.services.SocialNetworkAPI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.verify;

@WebMvcTest(SocialNetworkAPIController.class)
class SocialnetworkapiApplicationShould {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SocialNetworkAPI socialNetworkAPI;




  @Test
  void contextLoads() {
//    MvcResult httpResponse = mockMvc.perform(MockMvcRequestBuilders.get("/post"));
//
//    verify(socialNetworkAPI).createPost("Alice", "Hello");
  }

}
