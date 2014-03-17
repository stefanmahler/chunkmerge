package com.github.chunkmerge.merge;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.github.chunkmerge.model.Chunk;
import com.github.chunkmerge.util.AbstractIterator;

class SingleItemChunkWrapper<T extends Comparable<T>> extends AbstractIterator<T> {

  private final Chunk<T> internalChunk;

  private List<T> currentItems;

  private int cursor;

  SingleItemChunkWrapper(final Chunk<T> originalChunk) {
    this.internalChunk = Validate.notNull(originalChunk);
    this.cursor = 0;
    this.currentItems = Collections.emptyList();
  }

  @Override
  public boolean hasNext() {
    return (hasNextInCurrentInternalChunk() || this.internalChunk.hasNext());
  }

  @Override
  public T next() {
    validateHasNext();

    final T next = getCurrentItem();
    this.cursor++;
    return next;
  }

  public T getCurrentItem() {
    validateHasNext();

    while (!hasNextInCurrentInternalChunk()) {
      this.cursor = 0;
      this.currentItems = this.internalChunk.next();
    }

    return this.currentItems.get(this.cursor);
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }

  private boolean hasNextInCurrentInternalChunk() {
    return (this.cursor < this.currentItems.size());
  }

}
