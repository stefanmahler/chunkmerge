package com.github.chunkmerge.model.impl;

import java.util.List;

import org.apache.commons.lang3.Validate;

import com.github.chunkmerge.model.Chunk;
import com.github.chunkmerge.model.Chunkholder;

public class ChunkholderImpl<T extends Comparable<T>> implements Chunkholder<T> {

	private final String name;

	private final Iterable<List<T>> internal;

	public ChunkholderImpl(final String name, final Iterable<List<T>> iterable) {
		this.name = name;
		this.internal = Validate.noNullElements(iterable);
	}

	@Override
	public Chunk<T> iterator() {
		return new ChunkImpl<T>(this.internal);
	}

	@Override
	public String toString() {
		return String.format("%s:%s", this.name, iterator());
	}

}