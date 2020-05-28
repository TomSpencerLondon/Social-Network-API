package com.codurance.socialnetworkapi;

import com.codurance.social_network.domain.entities.Post;
import com.codurance.social_network.domain.repositories.FollowSubscriptionRepository;
import com.codurance.social_network.domain.repositories.InMemoryFollowSubscriptionRepository;
import com.codurance.social_network.domain.repositories.PostRepository;
import com.codurance.social_network.domain.services.Clock;
import com.codurance.social_network.domain.services.FollowSubscriptionService;
import com.codurance.social_network.domain.services.PostService;
import com.codurance.social_network.domain.services.SocialNetworkAPI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class SocialNetworkAPIController {

  private SocialNetworkAPI socialNetworkAPI;

  public SocialNetworkAPIController() {

    FollowSubscriptionRepository followRepository = new MongoDBFollowRepository();
    PostRepository postRepository = new MongoDBPostRepository();
    Clock clock = new Clock();
    PostService postService = new PostService(followRepository, postRepository, clock);
    FollowSubscriptionService followSubscriptionService = new FollowSubscriptionService(followRepository);
    socialNetworkAPI = new SocialNetworkAPI(postService, followSubscriptionService);
  }

  @RequestMapping("/post")
  public void post(@RequestParam("name") @NotBlank String name, @RequestParam("message") @NotBlank String message) {
    socialNetworkAPI.createPost(name, message);
  }

  @RequestMapping(path = "/read", produces = "application/json; charset=UTF-8")
  public List<Post> read(@RequestParam("name") @NotBlank String name) {
    return socialNetworkAPI.getPostsFor(name);
  }

  @RequestMapping(path = "/wall", produces = "application/json; charset=UTF-8")
  public List<Post> wall(@RequestParam("name") @NotBlank String name) {
    return socialNetworkAPI.getWallPostsFor(name);
  }

  @RequestMapping(path = "/follow", produces = "application/json; charset=UTF-8")
  public void follow(@RequestParam("follower") @NotBlank String follower, @RequestParam("followed") @NotBlank String followed){
    socialNetworkAPI.save(follower, followed);
  }
}
