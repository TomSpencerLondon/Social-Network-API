package com.codurance.socialnetworkapi;

import com.codurance.social_network.domain.entities.FollowSubscription;
import com.codurance.social_network.domain.entities.Post;
import com.codurance.social_network.domain.repositories.FollowSubscriptionRepository;
import com.codurance.social_network.domain.repositories.PostRepository;
import java.util.List;

public class MongoDBFollowRepository implements
     FollowSubscriptionRepository {

  @Override
  public void save(FollowSubscription followSubscription) {

  }

  @Override
  public List<FollowSubscription> fetch() {
    return null;
  }

  @Override
  public List<FollowSubscription> getSubscriptionsFor(String s) {
    return null;
  }
}
