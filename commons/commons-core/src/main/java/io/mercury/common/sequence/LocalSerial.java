package io.mercury.common.sequence;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.concurrent.ThreadSafe;

import io.mercury.common.thread.Threads;

@ThreadSafe
public final class LocalSerial {

	private final AtomicLong serial;

	private LocalSerial(long initValue) {
		this.serial = new AtomicLong(initValue);
	}

	/**
	 * 
	 * @param initValue
	 * @return
	 */
	public static LocalSerial newInstance(long initValue) {
		return new LocalSerial(initValue);
	}

	/**
	 * 
	 * @return
	 */
	public static LocalSerial newInstance() {
		return new LocalSerial(0L);
	}

	/**
	 * 
	 * @return
	 */
	public long incrementAndGet() {
		return serial.incrementAndGet();
	}

	/**
	 * 
	 * @return
	 */
	public long getAndIncrement() {
		return serial.getAndIncrement();
	}

	public static void main(String[] args) {

		AtomicLong atomicLong = new AtomicLong(50);

		SecureRandom random = new SecureRandom();
		
		Threads.startNewThread(() -> {
			if (atomicLong.get() < 0) {
				return;
			} else if (atomicLong.get() > 100) {
				return;
			} else {
				if (random.nextLong() % 2 == 0) {
					atomicLong.getAndAdd(1);
				} else {
					atomicLong.getAndAdd(-1);
				}
			}
		});

	}

}
