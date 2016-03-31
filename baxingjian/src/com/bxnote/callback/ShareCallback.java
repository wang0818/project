package com.bxnote.callback;

public interface ShareCallback<T> {
	void shareCallback(T t);
	void deleteCallback(T t);
	void writeCallback(T t);
	void showLargerPhoto(T t);
}
