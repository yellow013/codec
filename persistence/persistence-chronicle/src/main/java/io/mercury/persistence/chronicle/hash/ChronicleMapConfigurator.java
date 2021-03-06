package io.mercury.persistence.chronicle.hash;

import static io.mercury.common.util.StringUtil.fixPath;

import java.io.File;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import io.mercury.common.collections.Capacity;
import io.mercury.common.config.Configurator;
import io.mercury.common.datetime.DateTimeUtil;
import io.mercury.common.sys.SysProperties;
import io.mercury.common.util.Assertor;
import lombok.Getter;

@Immutable
public final class ChronicleMapConfigurator<K, V> implements Configurator {

	@Getter
	private final Class<K> keyClass;
	@Getter
	private final Class<V> valueClass;

	@Getter
	private final K averageKey;
	@Getter
	private final V averageValue;

	@Getter
	private final boolean putReturnsNull;
	@Getter
	private final boolean removeReturnsNull;
	@Getter
	private final boolean recover;
	@Getter
	private final boolean persistent;

	@Getter
	private final long entries;
	@Getter
	private final int actualChunkSize;

	@Getter
	private final String rootPath;
	@Getter
	private final String folder;

	// final save path
	@Getter
	private final File savePath;

	private final String cfgInfo;

	// extended use
	private Builder<K, V> builder;

	private ChronicleMapConfigurator(Builder<K, V> builder) {
		this.builder = builder;
		this.keyClass = builder.keyClass;
		this.valueClass = builder.valueClass;
		this.averageKey = builder.averageKey;
		this.averageValue = builder.averageValue;
		this.putReturnsNull = builder.putReturnsNull;
		this.removeReturnsNull = builder.removeReturnsNull;
		this.recover = builder.recover;
		this.persistent = builder.persistent;
		this.entries = builder.entries;
		this.actualChunkSize = builder.actualChunkSize;
		this.rootPath = builder.rootPath;
		this.folder = builder.folder;
		this.savePath = new File(rootPath + FixedFolder + folder);
		this.cfgInfo = "[SaveTo->" + savePath.getAbsolutePath() + "]:[KeyType==" + keyClass.getSimpleName()
				+ ",ValueType==" + valueClass.getSimpleName() + "]";
	}

	private static final String FixedFolder = "chronicle-map/";

	/**
	 * 
	 * @param <K>
	 * @param <V>
	 * @param keyClass
	 * @param valueClass
	 * @return
	 * @throws NullPointerException
	 */
	public static <K, V> Builder<K, V> newBuilder(@Nonnull Class<K> keyClass, @Nonnull Class<V> valueClass)
			throws NullPointerException {
		Assertor.nonNull(keyClass, "keyClass");
		Assertor.nonNull(valueClass, "valueClass");
		return new Builder<>(keyClass, valueClass, SysProperties.JAVA_IO_TMPDIR,
				"auto-create-" + DateTimeUtil.datetimeOfSecond());
	}

	/**
	 * 
	 * @param <K>
	 * @param <V>
	 * @param keyClass
	 * @param valueClass
	 * @param folder
	 * @return
	 * @throws NullPointerException
	 */
	public static <K, V> Builder<K, V> newBuilder(@Nonnull Class<K> keyClass, @Nonnull Class<V> valueClass,
			@Nonnull String folder) throws NullPointerException {
		Assertor.nonNull(keyClass, "keyClass");
		Assertor.nonNull(valueClass, "valueClass");
		Assertor.nonNull(folder, "folder");
		return new Builder<>(keyClass, valueClass, SysProperties.JAVA_IO_TMPDIR, folder);
	}

	/**
	 * 
	 * @param <K>
	 * @param <V>
	 * @param keyClass
	 * @param valueClass
	 * @param rootPath
	 * @param folder
	 * @return
	 * @throws NullPointerException
	 */
	public static <K, V> Builder<K, V> newBuilder(@Nonnull Class<K> keyClass, @Nonnull Class<V> valueClass,
			@Nonnull String rootPath, @Nonnull String folder) throws NullPointerException {
		Assertor.nonNull(keyClass, "keyClass");
		Assertor.nonNull(valueClass, "valueClass");
		Assertor.nonNull(rootPath, "rootPath");
		Assertor.nonNull(folder, "folder");
		return new Builder<>(keyClass, valueClass, rootPath, folder);
	}

	/**
	 * 
	 * @param <K>
	 * @param <V>
	 * @param original
	 * @return
	 * @throws NullPointerException
	 */
	public static <K, V> Builder<K, V> reset(@Nonnull ChronicleMapConfigurator<K, V> original)
			throws NullPointerException {
		Assertor.nonNull(original, "original");
		return original.builder;
	}

	@Override
	public String getCfgInfo() {
		return cfgInfo;
	}

	/**
	 * 
	 * @author yellow013
	 *
	 * @param <K>
	 * @param <V>
	 */
	public static class Builder<K, V> {

		private Class<K> keyClass;
		private Class<V> valueClass;
		private String rootPath;
		private String folder;

		private K averageKey;
		private V averageValue;

		private boolean putReturnsNull = false;
		private boolean removeReturnsNull = false;
		private boolean recover = false;
		private boolean persistent = true;

		private long entries = 65536;
		private int actualChunkSize;

		private Builder(@Nonnull Class<K> keyClass, @Nonnull Class<V> valueClass, @Nonnull String rootPath,
				@Nonnull String folder) {
			this.keyClass = keyClass;
			this.valueClass = valueClass;
			this.rootPath = fixPath(rootPath);
			this.folder = fixPath(folder);
		}

		/**
		 * 
		 * @param averageKey
		 * @return
		 */
		public Builder<K, V> averageKey(K averageKey) {
			this.averageKey = averageKey;
			return this;
		}

		/**
		 * 
		 * @param averageValue
		 * @return
		 */
		public Builder<K, V> averageValue(V averageValue) {
			this.averageValue = averageValue;
			return this;
		}

		/**
		 * 
		 * @return
		 */
		public Builder<K, V> enablePutReturnsNull() {
			this.putReturnsNull = true;
			return this;
		}

		/**
		 * 
		 * @return
		 */
		public Builder<K, V> enableRemoveReturnsNull() {
			this.removeReturnsNull = true;
			return this;
		}

		/**
		 * 
		 * @return
		 */
		public Builder<K, V> enableRecover() {
			this.recover = true;
			return this;
		}

		/**
		 * 
		 * @param persistent
		 * @return
		 */
		public Builder<K, V> persistent(boolean persistent) {
			this.persistent = persistent;
			return this;
		}

		/**
		 * 
		 * @param actualChunkSize
		 * @return
		 */
		public Builder<K, V> actualChunkSize(int actualChunkSize) {
			this.actualChunkSize = actualChunkSize;
			return this;
		}

		/**
		 * 
		 * @param entries
		 * @return
		 */
		public Builder<K, V> entries(long entries) {
			this.entries = entries;
			return this;
		}

		/**
		 * 
		 * @param capacity
		 * @return
		 */
		public Builder<K, V> entries(Capacity capacity) {
			this.entries = capacity.value();
			return this;
		}

		/**
		 * 
		 * @return
		 */
		public ChronicleMapConfigurator<K, V> build() {
			return new ChronicleMapConfigurator<>(this);
		}
	}

	public static void main(String[] args) {

		ChronicleMapConfigurator<String, Long> configurator = ChronicleMapConfigurator
				.newBuilder(String.class, Long.class, SysProperties.USER_HOME, "/user").build();

		System.out.println(configurator.getCfgInfo());

	}

}
