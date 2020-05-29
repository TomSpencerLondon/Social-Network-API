package com.codurance.socialnetworkapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.codurance.social_network.domain.entities.Post;
import java.time.LocalDateTime;
import org.bson.Document;
import org.junit.jupiter.api.Test;

public class PostConverterShould {

  private PostConverter postConverter = new PostConverter();
  private Document postDocument = new Document();

  @Test
  void convert_document_to_post() {
    postDocument.append("message", "hello");
    postDocument.append("name", "John Doe");
    postDocument.append("timePosted", new java.util.Date(120, 4, 29, 8, 42, 30));
    Post post = postConverter.convertDocumentToPost(postDocument);
    assertEquals("hello", post.getMessage());
    assertEquals("John Doe", post.getName());
    assertEquals("2020-05-29T08:42:30", post.getTimePosted().toString());
  }

  @Test
  void convert_post_to_document() {
    Post post = new Post("hello",
        LocalDateTime.of(2020, 5, 29, 8, 42, 30),
        "John Doe");

    Document document = postConverter.convertPostToDocument(post);

    assertEquals("hello", document.getString("message"));
    assertEquals(new java.util.Date(120, 4, 29, 8, 42, 30), document.getDate("timePosted"));
    assertEquals("John Doe", document.getString("name"));
  }
}
