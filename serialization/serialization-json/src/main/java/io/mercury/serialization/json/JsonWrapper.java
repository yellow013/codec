package io.mercury.serialization.json;

import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.mercury.common.serialization.JsonSerializable;

public final class JsonWrapper {

	// 普通JSON序列化
	private static final Gson Gson = new GsonBuilder().create();

	// JSON序列化, 包含Null值
	private static final Gson GsonHasNulls = new GsonBuilder().serializeNulls().create();

	// 以较高可视化的格式返回JSON
	private final static Gson GsonPrettyPrinting = new GsonBuilder().setPrettyPrinting().create();

	// 以漂亮的格式返回JSON, 包含Null值
	private static final Gson GsonPrettyPrintingHasNulls = new GsonBuilder().serializeNulls().setPrettyPrinting()
			.create();

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static final String toJson(@Nullable Object obj) {
		if (obj == null)
			return "null";
		if (obj instanceof JsonSerializable)
			return ((JsonSerializable) obj).toJson();
		return Gson.toJson(obj);
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static final String toJsonHasNulls(@Nullable Object obj) {
		if (obj == null)
			return "null";
		if (obj instanceof JsonSerializable)
			return ((JsonSerializable) obj).toJson();
		return GsonHasNulls.toJson(obj);
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static final String toPrettyJson(@Nullable Object obj) {
		if (obj == null)
			return "null";
		if (obj instanceof JsonSerializable)
			return ((JsonSerializable) obj).toJson();
		return GsonPrettyPrinting.toJson(obj);
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static final String toPrettyJsonHasNulls(@Nullable Object obj) {
		if (obj == null)
			return "null";
		if (obj instanceof JsonSerializable)
			return ((JsonSerializable) obj).toJson();
		return GsonPrettyPrintingHasNulls.toJson(obj);
	}

}
