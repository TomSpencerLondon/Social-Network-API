package com.codurance.socialnetworkapi;

import com.codurance.social_network.domain.entities.Post;
import com.codurance.social_network.domain.repositories.PostRepository;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MongoDBPostRepository implements PostRepository {

  public static final String MESSAGE_KEY = "message";
  public static final String NAME_KEY = "name";
  public static final String TIME_POSTED_KEY = "timePosted";
  private final MongoClient mongoClient;
  private PostConverter postConverter = new PostConverter();

  public MongoDBPostRepository() {
    mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
  }

  @Override
  public void save(Post post) {
    Document postDocument = postConverter.convertPostToDocument(post);
    getPostsCollection().insertOne(postDocument);
  }

  private MongoCollection<Document> getPostsCollection() {
    return mongoClient.getDatabase("SocialNetwork").getCollection("posts");
  }

  @Override
  public List<Post> fetch() {
    ArrayList<Post> posts = new ArrayList<>();
    for(Document d: getPostsCollection().find())
    {
      posts.add(postConverter.convertDocumentToPost(d));
    }

    return posts;
  }

  @Override
  public List<Post> getPostsFor(String name) {
    Document query = new Document();
    query.append(NAME_KEY, name);
    ArrayList<Post> posts = new ArrayList<>();
    for(Document d: getPostsCollection().find(query))
    {
      posts.add(postConverter.convertDocumentToPost(d));
    }

    return posts;
  }

}
