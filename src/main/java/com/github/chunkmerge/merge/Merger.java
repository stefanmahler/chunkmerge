package com.github.chunkmerge.merge;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.github.chunkmerge.model.Chunk;
import com.github.chunkmerge.model.Chunkholder;

public class Merger<T extends Comparable<T>> implements Chunkholder<T> {

  private final int maxChunkSize;

  private final Chunkholder<T> a;

  private final Chunkholder<T> b;

  public Merger(final int maxChunkSize, final Chunkholder<T> chunkA, final Chunkholder<T> chunkB) {
    Validate.inclusiveBetween(1, 100, maxChunkSize, "maxChunkSize must be in [1;100]");
    Validate.notNull(chunkA, "chunkA");
    Validate.notNull(chunkB, "chunkB");

    this.maxChunkSize = maxChunkSize;
    this.a = chunkA;
    this.b = chunkB;
  }

  @Override
  public Chunk<T> iterator() {
    return MergedChunkIterator.create(this.maxChunkSize, this.a.iterator(), this.b.iterator());
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }
}
