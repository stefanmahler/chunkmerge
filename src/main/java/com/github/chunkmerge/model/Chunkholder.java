package com.github.chunkmerge.model;

import java.util.List;

public interface Chunkholder<T extends Comparable<T>> extends Iterable<List<T>> {

	@Override
	Chunk<T> iterator();

}
