package com.codurance.socialnetworkapi;

import com.codurance.social_network.domain.entities.Post;
import com.codurance.social_network.domain.repositories.PostRepository;
import com.mongodb.Function;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MongoDBPostRepository implements PostRepository {

  public static final String MESSAGE_KEY = "message";
  public static final String NAME_KEY = "name";
  public static final String TIME_POSTED_KEY = "timePosted";
  private final MongoClient mongoClient;

  public MongoDBPostRepository() {
    mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
  }

  @Override
  public void save(Post post) {
    Document postDocument = convertPostToDocument(post);
    getPostsCollection().insertOne(postDocument);
  }

  private Document convertPostToDocument(Post post) {
    Document postDocument = new Document();
    postDocument.append(MESSAGE_KEY, post.getMessage());
    postDocument.append(NAME_KEY, post.getName());
    postDocument.append(TIME_POSTED_KEY, post.getTimePosted());
    return postDocument;
  }

  private MongoCollection<Document> getPostsCollection() {
    return mongoClient.getDatabase("SocialNetwork").getCollection("posts");
  }

  @Override
  public List<Post> fetch() {
    ArrayList<Post> posts = new ArrayList<>();
    for(Document d: getPostsCollection().find())
    {
      posts.add(convertDocumentToPost(d));
    }

    return posts;
  }

  private Post convertDocumentToPost(Document document) {
    return new Post(
        document.getString(MESSAGE_KEY),
        getLocalDateTime(document.getDate(TIME_POSTED_KEY)),
        document.getString(NAME_KEY));
  }

  private LocalDateTime getLocalDateTime(Date date) {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

  @Override
  public List<Post> getPostsFor(String name) {
    Document query = new Document();
    query.append(NAME_KEY, name);
    ArrayList<Post> posts = new ArrayList<>();
    for(Document d: getPostsCollection().find(query))
    {
      posts.add(convertDocumentToPost(d));
    }

    return posts;
  }
}
