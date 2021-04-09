package com.bank.transaction.dao.entity;

import org.springframework.security.core.GrantedAuthority;
//Eumn
public enum Role implements GrantedAuthority {
  ROLE_ADMIN, ROLE_CLIENT;

  public String getAuthority() {
    return name();
  }

}
