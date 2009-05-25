package org.excel4j.utils;

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;

public class LazyList<E> extends AbstractList<E>{

	private static final String UNSUPORTED_MESSAGE = "This kind of list is not meant to add elements, because them are provided by the Function";
	private final Function<Integer, ? extends E> creator;
	private final int maximum;
	private final int minimum;
	
	public static <E> LazyList<E> newLazyList(Function<Integer, ? extends E> creator,int maxValue)
	{
		return new LazyList<E>(creator,maxValue);
	}
	
	public LazyList(Function<Integer, ? extends E> creator,int maxValue) {
		this(creator,0,maxValue);
	}
	
	private LazyList(Function<Integer, ? extends E> creator,int minValue,int maxValue)
	{
		minimum = minValue;
		maximum = maxValue-minValue;
		this.creator = creator;
	}
	
	
	@Override
	public E get(int index) {
		return creator.apply(index+minimum);
	}

	@Override
	public boolean add(E e) {
		throw new UnsupportedOperationException(UNSUPORTED_MESSAGE);
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException(UNSUPORTED_MESSAGE);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException(UNSUPORTED_MESSAGE);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return new LazyList<E>(creator,fromIndex,toIndex);
	}

	@Override
	public int size() {
		return maximum;
	}

	
}
