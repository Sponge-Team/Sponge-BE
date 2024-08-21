package com.petweb.sponge.oauth2.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

	private final LoginAuth loginAuth;

	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		return collection;
	}

	@Override
	public String getName() {
		return loginAuth.getName();
	}

	public String getLoginType() {
		return loginAuth.getLoginType();
	}

	public long getId() {
		return loginAuth.getId();
	}
}
