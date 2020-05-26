package com.codurance.socialnetworkapi;

import com.codurance.social_network.domain.entities.FollowSubscription;
import com.codurance.social_network.domain.entities.User;
import com.codurance.social_network.domain.repositories.FollowSubscriptionRepository;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class MongoDBFollowRepository implements
    FollowSubscriptionRepository {

  public static final String USER = "followed";
  private static final String FOLLOWED = "follower";
  private final MongoClient mongoClient;

  public MongoDBFollowRepository() {
    mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
  }

  @Override
  public void save(FollowSubscription followSubscription) {
    Document followSubscriptionDocument = convertFollowSubscriptionToDocument(followSubscription);
    getFollowSubscriptionsCollection().insertOne(followSubscriptionDocument);

  }

  private MongoCollection<Document> getFollowSubscriptionsCollection() {
    return mongoClient.getDatabase("SocialNetwork").getCollection("followSubscription");
  }

  private Document convertFollowSubscriptionToDocument(FollowSubscription followSubscription) {
    Document subscriptionDocument = new Document();
    subscriptionDocument.append(USER, followSubscription.followed);
    subscriptionDocument.append(FOLLOWED, followSubscription.user);
    return subscriptionDocument;
  }

  @Override
  public List<FollowSubscription> fetch() {
    ArrayList<FollowSubscription> followSubscriptions = new ArrayList<>();
    for(Document d: getFollowSubscriptionsCollection().find()){
      followSubscriptions.add(convertDocumentToFollowSubscription(d));
    }
    return followSubscriptions;
  }

  private FollowSubscription convertDocumentToFollowSubscription(Document document) {
    return new FollowSubscription(
        new User(document.getString(USER)),
        new User(document.getString(FOLLOWED))
    );
  }

  @Override
  public List<FollowSubscription> getSubscriptionsFor(String userName) {
    Document query = new Document();
    query.append(USER, userName);
    ArrayList<FollowSubscription> followSubscriptions = new ArrayList<>();
    for(Document d: getFollowSubscriptionsCollection().find(query))
    {
      followSubscriptions.add(convertDocumentToFollowSubscription(d));
    }
    return followSubscriptions;
  }
}
