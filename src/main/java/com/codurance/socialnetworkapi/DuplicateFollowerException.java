package com.codurance.socialnetworkapi;

public class DuplicateFollowerException extends RuntimeException {

  public DuplicateFollowerException() {
    super("Follower and followed must be different names");
  };
}
