package com.codurance.socialnetworkapi;

import com.codurance.social_network.domain.entities.Post;
import com.codurance.social_network.domain.repositories.FollowSubscriptionRepository;
import com.codurance.social_network.domain.repositories.PostRepository;
import com.codurance.social_network.domain.services.Clock;
import com.codurance.social_network.domain.services.FollowSubscriptionService;
import com.codurance.social_network.domain.services.PostService;
import com.codurance.social_network.domain.services.SocialNetworkAPI;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
  public void post(String name, String message) {
    socialNetworkAPI.createPost(name, message);
  }

  @RequestMapping(path = "/read", produces = "application/json; charset=UTF-8")
  @ResponseBody
  public List<Post> read(String name) {
    return socialNetworkAPI.getPostsFor(name);
  }

  @RequestMapping(path = "/wall", produces = "application/json; charset=UTF-8")
  public List<Post> wall(String userName) {

    return socialNetworkAPI.getWallPostsFor(userName);
  }

  @RequestMapping(path = "/follow", produces = "application/json; charset=UTF-8")
  public void follow(String follower, String followed){
    socialNetworkAPI.save(follower, followed);
  }
}
