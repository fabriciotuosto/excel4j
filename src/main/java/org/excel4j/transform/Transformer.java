package org.excel4j.transform;

public interface Transformer<T> {

	/**
	 * 
	 * @param string to transform
	 * @return transformed string into object of the type <T>
	 * @throws {@link IllegalArgumentException} when it cannot tranformed the parameter
	 */
	T transform(String string);
}
