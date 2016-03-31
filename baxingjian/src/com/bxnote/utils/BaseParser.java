package com.bxnote.utils;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseParser<T> {

	public abstract T parseJSON(String paramString) throws JSONException;

	/**
	 * 
	 * @param res
	 * @throws JSONException
	 */
	public String checkResponse(String paramString) throws JSONException {
		if (paramString == null) {
			return null;
		} else {
			JSONObject jsonObject;
			// String result = null;
			jsonObject = new JSONObject(paramString);
			String result = jsonObject.getString("result");
			if (result != null && !result.equals("")) {
				return result;
			} else {
				return null;
			}

		}
	}
}
