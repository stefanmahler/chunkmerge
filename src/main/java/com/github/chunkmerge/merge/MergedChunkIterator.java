package com.github.chunkmerge.merge;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.github.chunkmerge.model.Chunk;
import com.github.chunkmerge.util.AbstractIterator;

class MergedChunkIterator<T extends Comparable<T>> extends AbstractIterator<List<T>> implements Chunk<T> {

  static <T extends Comparable<T>> Chunk<T> create(final int maxChunkSize, final Chunk<T> chunkA, final Chunk<T> chunkB) {
    final MergeItemIterator<T> itemIterator = MergeItemIterator.create(chunkA, chunkB);
    return new MergedChunkIterator<T>(maxChunkSize, itemIterator);
  }

  private final int maxChunkSize;

  private final MergeItemIterator<T> restItemIterator;

  private MergedChunkIterator(final int maxChunkSize, final MergeItemIterator<T> restItemIterator) {
    this.maxChunkSize = maxChunkSize;
    this.restItemIterator = restItemIterator;
  }

  @Override
  public boolean hasNext() {
    return this.restItemIterator.hasNext();
  }

  @Override
  public List<T> next() {
    validateHasNext();

    final List<T> items = new ArrayList<T>(this.maxChunkSize);
    while (items.size() < this.maxChunkSize && this.restItemIterator.hasNext()) {
      items.add(this.restItemIterator.next());
    }

    return items;
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }

}