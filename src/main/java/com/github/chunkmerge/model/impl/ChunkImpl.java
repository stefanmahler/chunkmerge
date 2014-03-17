package com.github.chunkmerge.model.impl;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.Validate;

import com.github.chunkmerge.model.Chunk;
import com.github.chunkmerge.util.AbstractIterator;

class ChunkImpl<T extends Comparable<T>> extends AbstractIterator<List<T>> implements Chunk<T> {

  private Iterable<List<T>> internal;

  ChunkImpl(final Iterable<List<T>> chunks) {
    Validate.noNullElements(chunks);
    this.internal = chunks;
  }

  @Override
  public boolean hasNext() {
    return this.internal.iterator().hasNext();
  }

  @Override
  public List<T> next() throws NoSuchElementException {
    validateHasNext();

    final Iterator<List<T>> iterator = this.internal.iterator();
    final List<T> items = iterator.next();
    this.internal = new Iterable<List<T>>(){

      @Override
      public Iterator<List<T>> iterator() {
        return iterator;

      }

    };
    return items;
  }

  @Override
  public String toString() {
    final StringBuilder content = new StringBuilder();
    for (final Object chunk : this.internal) {
      content.append("\n -> ");
      content.append(chunk);
    }
    return content.toString();
  }

}
