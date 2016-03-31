package com.bxnote.bean;

import java.io.Serializable;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;


public class AccessToken implements Serializable {

	private static final long serialVersionUID = 1L;
	public Oauth2AccessToken accessToken;
	public String uid;

}
