package com.github.chunkmerge.merge;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.github.chunkmerge.model.Chunk;
import com.github.chunkmerge.model.Chunkholder;

public class TestMerger {

  @Test
  public void testMergeTwoEmptyChunks_resultsInEmptyChunk() {
    final Chunkholder<String> chunkA = create();
    final Chunkholder<String> chunkB = create();

    final Merger<?> merger = new Merger<String>(1, chunkA, chunkB);
    Assertions.assertThat(merger).isEmpty();
  }

  @Test
  public void testMergeNormalWithEmpty() {
    final Chunkholder<String> chunkA = create(chunk("A", "B", "C"),
        chunk("K", "L"), chunk("S"), chunk("X", "Y", "Z"));
    final Chunkholder<String> chunkB = create();

    final Merger<String> merger = new Merger<String>(4, chunkA, chunkB);
    Assertions.assertThat(toChunkArray(merger)).containsExactly(
        chunk("A", "B", "C", "K"), chunk("L", "S", "X", "Y"), chunk("Z"));
  }

  @Test
  public void testMergeEmptyWithNormal() {
    final Chunkholder<String> chunkA = create();
    final Chunkholder<String> chunkB = create(chunk("A", "B", "C"),
        chunk("K", "L"), chunk("S"), chunk("X", "Y", "Z"));

    final Merger<String> merger = new Merger<String>(4, chunkA, chunkB);
    Assertions.assertThat(toChunkArray(merger)).containsExactly(
        chunk("A", "B", "C", "K"), chunk("L", "S", "X", "Y"), chunk("Z"));
  }

  @Test
  public void mergeNoCollisions() {
    final Chunkholder<String> chunkA = create(chunk("A", "C"),
        chunk("L", "S", "Y"));
    final Chunkholder<String> chunkB = create(chunk("B"), chunk("K", "X", "Z"));

    final Merger<String> merger = new Merger<String>(4, chunkA, chunkB);
    Assertions.assertThat(toChunkArray(merger)).containsExactly(
        chunk("A", "B", "C", "K"), chunk("L", "S", "X", "Y"), chunk("Z"));
  }

  @Test
  public void mergeWithCollisions() {
    final Chunkholder<String> chunkA = create(chunk("A", "C"), chunk("L", "M"),
        chunk("Q", "S"), chunk("W", "Y"));
    final Chunkholder<String> chunkB = create(chunk("B", "C"), chunk("K", "L"),
        chunk("W", "X"), chunk("Y", "Z"));

    final Merger<String> merger = new Merger<String>(4, chunkA, chunkB);
    Assertions.assertThat(toChunkArray(merger)).containsExactly(
        chunk("A", "B", "C", "K"), chunk("L", "M", "Q", "S"),
        chunk("W", "X", "Y", "Z"));
  }

  private String[][] toChunkArray(final Merger<String> merger) {
    final List<String[]> result = new ArrayList<String[]>();
    for (final List<String> chunk : merger) {
      result.add(chunk.toArray(new String[0]));
    }
    return result.toArray(new String[0][0]);
  }

  private <T extends Comparable<T>> Chunkholder<T> create(final T[]... chunksArray) {
    final List<List<T>> chunksList = new ArrayList<List<T>>(chunksArray.length);
    for (final T[] chunk : chunksArray) {
      chunksList.add(asList(chunk));
    }
    final Iterator<List<T>> iterator = chunksList.iterator();
    return new Chunkholder<T>() {

      @Override
      public Chunk<T> iterator() {
        return new Chunk<T>() {

          @Override
          public boolean hasNext() {
            return iterator.hasNext();

          }

          @Override
          public List<T> next() {
            return iterator.next();

          }

          @Override
          public void remove() {
            iterator.remove();
          }
        };
      }

    };
  }

  private <T> T[] chunk(final T... items) {
    return items;
  }
}
