package com.bxnote.utils;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.os.Looper;

public class Consumer implements Runnable {

	final Queue<Task> mQueue;
	public Task mRuningTask;
	private Thread thread;

	public Consumer() {
		super();
		this.mQueue = new ConcurrentLinkedQueue<Task>();
	}

	public synchronized void add(Task task) {

		mQueue.add(task);
		if (thread == null) {
			thread = new Thread(this);
			thread.setPriority(Thread.MIN_PRIORITY);
			thread.start();
		}

	}

	@Override
	public void run() {
		if (Looper.myLooper() == null) {
			Looper.prepare();
		}
		while (true) {
			while (!mQueue.isEmpty()) {
				Task task = mQueue.poll();
				synchronized (this) {
					mRuningTask = task;
				}
				task.runTask();
			}
			synchronized (this) {
				if (mQueue.isEmpty()) {
					thread = null;
					return;
				}
			}
		}
	}

	synchronized public void cancleAll() {
		if (mRuningTask != null) {
			mRuningTask.cacelTask();
		}
		Iterator<Task> iterator = mQueue.iterator();
		while (iterator.hasNext()) {
			Task deleteTask = iterator.next();
			deleteTask.cacelTask();
		}
	}

	public Queue<Task> getQueue() {
		return mQueue;
	}

}
