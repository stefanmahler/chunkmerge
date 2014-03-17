package com.github.chunkmerge.merge;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.github.chunkmerge.model.Chunk;
import com.github.chunkmerge.util.AbstractIterator;

class MergeItemIterator<T extends Comparable<T>> extends AbstractIterator<T> {

  static <T extends Comparable<T>> MergeItemIterator<T> create(final Chunk<T> chunkA, final Chunk<T> chunkB) {
    Validate.notNull(chunkA, "chunkA");
    Validate.notNull(chunkB, "chunkB");

    final SingleItemChunkWrapper<T> a = new SingleItemChunkWrapper<T>(chunkA);
    final SingleItemChunkWrapper<T> b = new SingleItemChunkWrapper<T>(chunkB);

    return new MergeItemIterator<T>(a, b);
  }

  private static <T extends Comparable<T>> boolean isBefore(final SingleItemChunkWrapper<T> a,
                                                            final SingleItemChunkWrapper<T> b) {
    if (!a.hasNext()) {
      return false;
    }

    if (!b.hasNext()) {
      return true;
    }

    return (new CompareToBuilder().append(a.getCurrentItem(), b.getCurrentItem()).toComparison() < 0);
  }

  private final SingleItemChunkWrapper<T> a;

  private final SingleItemChunkWrapper<T> b;

  private MergeItemIterator(final SingleItemChunkWrapper<T> a, final SingleItemChunkWrapper<T> b) {
    this.a = a;
    this.b = b;
  }

  @Override
  public boolean hasNext() {
    return (this.a.hasNext() || this.b.hasNext());
  }

  @Override
  public T next() {
    validateHasNext();

    final T result;
    if (isBefore(this.a, this.b)) {
      result = this.a.getCurrentItem();
      this.a.next();
    } else if (isBefore(this.b, this.a)) {
      result = this.b.getCurrentItem();
      this.b.next();
    } else {
      // both keys equal => take A only
      result = this.a.getCurrentItem();
      this.a.next();
      // shift *all* B with current key: this is need if this key occurs
      // multiple in B
      // in unique-key case we can simple shift *the one* current B
      // unconditionally
      while (this.b.hasNext() && result.compareTo(this.b.getCurrentItem()) == 0) {
        this.b.next();
      }
    }
    return result;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("a", this.a).append('\n').append("b", this.b).toString();
  }

}
