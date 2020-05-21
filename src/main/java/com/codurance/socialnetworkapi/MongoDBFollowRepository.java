package com.codurance.socialnetworkapi;

import com.codurance.social_network.domain.entities.FollowSubscription;
import com.codurance.social_network.domain.entities.Post;
import com.codurance.social_network.domain.repositories.FollowSubscriptionRepository;
import com.codurance.social_network.domain.repositories.PostRepository;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.bson.Document;

import java.util.List;

public class MongoDBFollowRepository implements
     FollowSubscriptionRepository {
  private final MongoClient mongoClient;

  public MongoDBFollowRepository() {
    mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
  }

  @Override
  public void save(FollowSubscription followSubscription) {

  }

  private Document convertFollowSubscriptionToDocument(FollowSubscription followSubscription){
    Document subscriptionDocument = new Document();
    subscriptionDocument.append("", followSubscription);
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
