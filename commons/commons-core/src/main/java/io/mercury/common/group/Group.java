package io.mercury.common.group;

import java.io.Serializable;

import javax.annotation.Nonnull;

import org.eclipse.collections.api.list.ImmutableList;

public interface Group<K, V> extends Serializable {

	@Nonnull
	V acquireMember(@Nonnull K k);

	@Nonnull
	ImmutableList<V> memberList();

}
