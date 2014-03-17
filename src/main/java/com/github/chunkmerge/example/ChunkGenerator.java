package com.github.chunkmerge.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.Validate;

import com.github.chunkmerge.model.Chunkholder;
import com.github.chunkmerge.model.impl.ChunkholderImpl;

public class ChunkGenerator {

  private static Integer nextInt(final Range<Integer> itemRange) {
    final int length = getRangeLength(itemRange);
    return itemRange.getMinimum() + new Random().nextInt(length);
  }

  private static int getRangeLength(final Range<Integer> range) {
    return range.getMaximum() - range.getMinimum();
  }

  private final List<Integer> keyList;

  private final int maxChunkSize;

  public ChunkGenerator(final int itemCount, final int maxChunksize, final Range<Integer> itemRange) {
    Validate.inclusiveBetween(0, 100, itemCount, "itemCount must be in [0;100]");
    Validate.inclusiveBetween(1, 25, maxChunksize, "maxChunkSize must be in [1;25]");

    Validate.notNull(itemRange, "itemRange");
    final int rangeLength = getRangeLength(itemRange);
    Validate.isTrue(rangeLength >= 1, "length of %s must be at least %d", itemRange, 1);
    Validate.isTrue(rangeLength >= itemCount, "length of %s must be at least %d", itemRange, itemCount);

    final Collection<Integer> keys = new TreeSet<Integer>();
    while (keys.size() < itemCount) {
      keys.add(nextInt(itemRange));
    }
    this.keyList = new LinkedList<Integer>(keys);

    this.maxChunkSize = maxChunksize;
  }

  public Chunkholder<Item> getChunks(final String name) {
    final List<List<Item>> rawItemPackages = getRawItemPackages(name);
    Validate.notNull(rawItemPackages);

    return new ChunkholderImpl<Item>(name, rawItemPackages);
  }

  private List<List<Item>> getRawItemPackages(final String name) {
    final int size = this.keyList.size();
    final List<List<Item>> chunks = new LinkedList<List<Item>>();

    int chunkIndex = 1;
    int splitIndex = 0;
    while (splitIndex < size) {
      final int currentChunkEnd = Math.min(splitIndex + this.maxChunkSize, size);
      final List<Item> items = createItems(name,
                                           chunkIndex,
                                           splitIndex + 1,
                                           this.keyList.subList(splitIndex, currentChunkEnd));
      chunks.add(items);

      splitIndex += items.size();
      chunkIndex++;
    }

    return chunks;
  }

  private List<Item> createItems(final String name, final int chunkIdx, final int offset, final List<Integer> keys) {
    final List<Item> items = new ArrayList<Item>(keys.size());
    for (final Integer k : keys) {
      items.add(new Item(k, String.format("chunk %s.%2d item %2d", name, chunkIdx, offset + items.size())));
    }
    return items;
  }

}
