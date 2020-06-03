package com.codurance.socialnetworkapi;

import com.codurance.social_network.domain.entities.FollowSubscription;
import com.codurance.social_network.domain.repositories.FollowSubscriptionRepository;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class MongoDBFollowRepository implements
    FollowSubscriptionRepository {

  public static final String USER = "follower";
  private static final String FOLLOWED = "followed";
  private final MongoClient mongoClient;
  private FollowConverter followSubscriptionConverter = new FollowConverter();

  public MongoDBFollowRepository() {
    mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
  }

  @Override
  public void save(FollowSubscription followSubscription) {
    Document followSubscriptionDocument = followSubscriptionConverter.convertFollowSubscriptionToDocument(followSubscription);

    getFollowSubscriptionsCollection().insertOne(followSubscriptionDocument);
  }

  private MongoCollection<Document> getFollowSubscriptionsCollection() {
    return mongoClient.getDatabase("SocialNetwork").getCollection("followSubscription");
  }

  @Override
  public List<FollowSubscription> fetch() {
    ArrayList<FollowSubscription> followSubscriptions = new ArrayList<>();
    for(Document d: getFollowSubscriptionsCollection().find()){
      followSubscriptions.add(followSubscriptionConverter.convertDocumentToFollowSubscription(d));
    }
    return followSubscriptions;
  }

  @Override
  public List<FollowSubscription> getSubscriptionsFor(String userName) {
    Document query = new Document();
    query.append(USER, userName);
    ArrayList<FollowSubscription> followSubscriptions = new ArrayList<>();
    for(Document d: getFollowSubscriptionsCollection().find(query))
    {
      followSubscriptions.add(followSubscriptionConverter.convertDocumentToFollowSubscription(d));
    }
    return followSubscriptions;
  }
}
