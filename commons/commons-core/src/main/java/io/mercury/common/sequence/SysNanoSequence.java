package io.mercury.common.sequence;

import org.slf4j.Logger;

import io.mercury.common.log.CommonLoggerFactory;
import io.mercury.common.log.LogConfigurator;

public final class SysNanoSequence {

	public static long milli() {
		return System.nanoTime() / 1000_000;
	}

	public static long micro() {
		return System.nanoTime() / 1000;
	}

	public static long nano() {
		return System.nanoTime();
	}

	public static void main(String[] args) {

		LogConfigurator.logFileName("test-log");
		Logger log = CommonLoggerFactory.getLogger(SysNanoSequence.class);

		for (int i = 0; i < 20; i++) {
			log.debug(String.valueOf(SysNanoSequence.micro()));
		}

	}

}