package com.bxnote.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SinaUser implements Serializable {
	public String name;
	public String gender;
	public long expiresIn;
	public String accessToken;
}
