package io.mercury.persistence.chronicle.queue;

import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;

import org.slf4j.Logger;

import io.mercury.common.annotation.lang.AbstractFunction;
import io.mercury.persistence.chronicle.exception.ChronicleWriteException;
import io.mercury.persistence.chronicle.queue.AbstractChronicleQueue.CloseableChronicleAccessor;
import net.openhft.chronicle.queue.ExcerptAppender;

@Immutable
@NotThreadSafe
public abstract class AbstractChronicleAppender<T> extends CloseableChronicleAccessor implements Runnable {

	private final String appenderName;

	protected final Logger logger;
	protected final ExcerptAppender excerptAppender;

	private Supplier<T> dataSupplier;

	protected AbstractChronicleAppender(long allocateSeq, String appenderName, Logger logger,
			ExcerptAppender excerptAppender, Supplier<T> dataSupplier) {
		super(allocateSeq);
		this.appenderName = appenderName;
		this.logger = logger;
		this.excerptAppender = excerptAppender;
		this.dataSupplier = dataSupplier;
	}

	public ExcerptAppender excerptAppender() {
		return excerptAppender;
	}

	public int cycle() {
		return excerptAppender.cycle();
	}

	public int sourceId() {
		return excerptAppender.sourceId();
	}

	public String appenderName() {
		return appenderName;
	}

	@AbstractFunction
	protected abstract void append0(@Nonnull T t);

	/**
	 * 
	 * @param t
	 * @throws IllegalStateException
	 * @throws ChronicleWriteException
	 */
	public void append(@Nonnull T t) throws IllegalStateException, ChronicleWriteException {
		if (isClose) {
			throw new IllegalStateException("Unable to append data, Chronicle queue is closed");
		}
		try {
			if (t != null) {
				append0(t);
			} else {
				logger.warn("appenderName -> {} : received null object, Not written to the queue.", appenderName);
			}
		} catch (Exception e) {
			throw new ChronicleWriteException(e.getMessage(), e);
		}
	}

	@Override
	public void run() {
		if (dataSupplier != null) {
			for (;;) {
				if (isClose) {
					logger.info("Chronicle queue is closed, Thread exit");
					break;
				} else {
					T t = dataSupplier.get();
					append(t);
				}
			}
		} else {
			logger.warn("Supplier is null, Thread exit");
		}
	}

	protected void close0() {

	}

}
