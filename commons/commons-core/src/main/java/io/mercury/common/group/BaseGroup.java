package io.mercury.common.group;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.map.ConcurrentMutableMap;

import io.mercury.common.annotation.lang.AbstractFunction;
import io.mercury.common.collections.Capacity;
import io.mercury.common.collections.ImmutableLists;
import io.mercury.common.collections.MutableMaps;

@ThreadSafe
public abstract class BaseGroup<K, V> implements Group<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8384604279192455942L;

	protected ConcurrentMutableMap<K, V> savedMap = MutableMaps.newConcurrentHashMap(Capacity.L04_SIZE_16);

	@Override
	public V acquireMember(K k) {
		return savedMap.getIfAbsentPutWithKey(k, this::createMember);
	}

	@Override
	public ImmutableList<V> memberList() {
		return ImmutableLists.newImmutableList(savedMap.values());
	}

	@Nonnull
	@AbstractFunction
	protected abstract V createMember(@Nonnull K k);

}
