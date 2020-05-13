package com.codurance.socialnetworkapi;

import com.codurance.social_network.domain.entities.Post;
import com.codurance.social_network.domain.repositories.InMemoryFollowSubscriptionRepository;
import com.codurance.social_network.domain.repositories.InMemoryPostRepository;
import com.codurance.social_network.domain.repositories.PostRepository;
import com.codurance.social_network.domain.services.Clock;
import com.codurance.social_network.domain.services.FollowSubscriptionService;
import com.codurance.social_network.domain.services.PostService;
import com.codurance.social_network.domain.services.SocialNetworkAPI;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {

  private SocialNetworkAPI socialNetworkAPI;

  public BlogController() {

    InMemoryFollowSubscriptionRepository inMemoryFollowRepository = new InMemoryFollowSubscriptionRepository();
    PostRepository postRepository = new InMemoryPostRepository();
    Clock clock = new Clock();
    PostService postService = new PostService(inMemoryFollowRepository, postRepository, clock);
    FollowSubscriptionService followSubscriptionService = new FollowSubscriptionService(inMemoryFollowRepository);
    socialNetworkAPI = new SocialNetworkAPI(postService, followSubscriptionService);
  }

  @RequestMapping("/")
  public String index() {
    return "Connected to the BlogController!";
  }

  @RequestMapping("/sayhello")
  public String sayHello(String name) {

    if(name == null || name.isEmpty()) {
      return "Sorry, I don't know your name";
    }
    return String.format("Hello %s!", name);
  }

  @RequestMapping("/post")
  public void Post(String name, String message) {
    socialNetworkAPI.createPost(name, message);
  }

  @RequestMapping("wall")
  public List<Post> Wall(String userName) {

    return socialNetworkAPI.getWallPostsFor(userName);
  }
}
