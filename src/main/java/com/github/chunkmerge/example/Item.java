package com.github.chunkmerge.example;

import java.util.Map;

import org.apache.commons.lang3.Validate;

public class Item implements Map.Entry<Integer, String>, Comparable<Item> {
	private final Integer key;

	private final String value;

	Item(final Integer key, final String value) {
		this.key = Validate.notNull(key, "key");
		this.value = value;
	}

	@Override
	public Integer getKey() {
		return this.key;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public String setValue(final String value) {
		throw new UnsupportedOperationException(
				"Immutable: setValue() is not supported.");
	}

	@Override
	public int compareTo(final Item o) {
		return getKey().compareTo(o.getKey());
	}

	@Override
	public String toString() {
		return String.format("{%3s: %s}", getKey(), getValue());
	}

}
