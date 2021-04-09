package com.bank.transaction.security;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.bank.transaction.api.CustomException;
import com.bank.transaction.dao.entity.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

  /**
   * THIS IS NOT A SECURE PRACTICE! For simplicity, we are storing a static key here. Ideally, in a
   * microservices environment, this key would be kept on a config-server.
   */
  @Value("${security.jwt.token.secret-key:secret-key}")
  private String secretKey;

  @Value("${security.jwt.token.expire-length:3600000}")
  private long validityInMilliseconds = 3600000; // 1h

  @Autowired
  private SpringSecurityUserDetails springSecurityUserDetails;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken(String username, List<Role> roles) {

    Claims claims = Jwts.claims().setSubject(username);
    //List<SimpleGrantedAuthority>
    //List<SimpleGrantedAuthority>
    claims.put("auth", roles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority())).filter(Objects::nonNull).collect(Collectors.toList()));

    Date now = new Date();
    Date validity = new Date(now.getTime() + validityInMilliseconds);

    return Jwts.builder()//
        .setClaims(claims)//
        .setIssuedAt(now)//
        .setExpiration(validity)//
        .signWith(SignatureAlgorithm.HS512, secretKey)//
        .compact();
  }
  
  public String createRefreshToken(String username) {
      LocalDateTime currentTime = LocalDateTime.now();
      Claims claims = Jwts.claims().setSubject(username);
      String token = Jwts.builder()
              .setClaims(claims)
              .setIssuer("Bank Tech")
              .setId(UUID.randomUUID().toString())
              .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
              .setExpiration(Date.from(currentTime
                      .plusMinutes(60)
                      .atZone(ZoneId.systemDefault()).toInstant()))
              .signWith(SignatureAlgorithm.HS512, secretKey)
              .compact();

      return token;

	   
	  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = springSecurityUserDetails.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
