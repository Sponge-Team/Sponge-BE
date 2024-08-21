package com.petweb.sponge.utils;

import com.petweb.sponge.oauth2.dto.CustomOAuth2User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationUtil {
	public Long getLoginId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomOAuth2User userDetails = (CustomOAuth2User) authentication.getPrincipal();

		return userDetails.getId();
	}

	public String getLoginType() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomOAuth2User userDetails = (CustomOAuth2User) authentication.getPrincipal();

		return userDetails.getLoginType();
	}

}
