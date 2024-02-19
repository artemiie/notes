package com.notes.jwt;

import java.util.HashMap;

public interface JwtService {

  String generate(TokenParameters params);

  String generateAccessToken(String username);

  String generateRefreshToken(String username);

  String generateRestoreToken(String username);

  String generateActivationToken(String username);

  boolean isValid(String token, TokenType type);

  HashMap<String, Object> fields(String token);

  String field(String token, String by);
}
