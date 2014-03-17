package com.github.chunkmerge.model;

import java.util.Iterator;
import java.util.List;

public interface Chunk<T extends Comparable<T>> extends Iterator<List<T>> {

}
