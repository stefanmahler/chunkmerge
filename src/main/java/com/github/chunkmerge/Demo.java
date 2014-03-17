package com.github.chunkmerge;

import java.util.List;

import org.apache.commons.lang3.Range;

import com.github.chunkmerge.example.ChunkGenerator;
import com.github.chunkmerge.example.Item;
import com.github.chunkmerge.merge.Merger;
import com.github.chunkmerge.model.Chunkholder;

public class Demo {

  public static void main(final String[] args) {
    final Chunkholder<Item> chunkA = getChunks("A");
    final Chunkholder<Item> chunkB = getChunks("B");
    final Merger<Item> merger = new Merger<Item>(3, chunkA, chunkB);

    System.out.println("*** Merged ***");
    for (final List<Item> merged : merger) {
      System.out.println(merged);
    }

    final Chunkholder<Item> chunkO = getChunks("O");
    final Merger<Item> mergerSuper = new Merger<Item>(5, chunkO, merger);

    System.out.println("*** Merged Super ***");
    for (final List<Item> merged : mergerSuper) {
      System.out.println(merged);
    }
  }

  private static Chunkholder<Item> getChunks(final String name) {
    final ChunkGenerator generator = new ChunkGenerator(10, 3, Range.between(10, 30));
    final Chunkholder<Item> chunks = generator.getChunks(name);
    System.out.println(chunks);
    return chunks;
  }

}
