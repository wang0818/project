package com.bxnote.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class QQUser implements Serializable {
	public String qqNickName;
	public String gender;
	public long expiresIn;
	public String accessToken;
}
