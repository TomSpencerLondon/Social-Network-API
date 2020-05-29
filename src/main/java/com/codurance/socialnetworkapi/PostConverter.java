package com.codurance.socialnetworkapi;

import com.codurance.social_network.domain.entities.Post;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.bson.Document;

public class PostConverter {
  public static final String MESSAGE_KEY = "message";
  public static final String NAME_KEY = "name";
  public static final String TIME_POSTED_KEY = "timePosted";

  public Post convertDocumentToPost(Document document) {
    return new Post(
        document.getString(MESSAGE_KEY),
        getLocalDateTime(document.getDate(TIME_POSTED_KEY)),
        document.getString(NAME_KEY));
  }

  public Document convertPostToDocument(Post post) {
    Document postDocument = new Document();
    postDocument.append(MESSAGE_KEY, post.getMessage());
    postDocument.append(NAME_KEY, post.getName());
    postDocument.append(TIME_POSTED_KEY, getDate(post.getTimePosted()));
    return postDocument;
  }

  private Date getDate(LocalDateTime localDateTime){
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }


  private LocalDateTime getLocalDateTime(Date date) {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }
}
