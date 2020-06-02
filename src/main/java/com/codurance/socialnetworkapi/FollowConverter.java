package com.codurance.socialnetworkapi;

import com.codurance.social_network.domain.entities.FollowSubscription;
import com.codurance.social_network.domain.entities.User;
import org.bson.Document;

public class FollowConverter {
  public static final String USER = "follower";
  private static final String FOLLOWED = "followed";

  public FollowSubscription convertDocumentToFollowSubscription(Document document) {
    return new FollowSubscription(
        new User(document.getString(USER)),
        new User(document.getString(FOLLOWED))
    );
  }

  public Document convertFollowSubscriptionToDocument(FollowSubscription followSubscription) {
    Document subscriptionDocument = new Document();
    subscriptionDocument.append(USER, followSubscription.user.name);
    subscriptionDocument.append(FOLLOWED, followSubscription.followed.name);
    return subscriptionDocument;
  }
}
