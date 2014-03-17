package com.github.chunkmerge.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractIterator<T> implements Iterator<T> {

  @Override
  public final void remove() {
    throw new UnsupportedOperationException("remove() is not supported.");
  }

  protected final void validateHasNext() {
    if (!hasNext()) {
      throw new NoSuchElementException(toString());
    }
  }
}
