package net.halflite.hiq.util;

import com.google.common.base.Joiner;

import net.halflite.hiq.type.LabelKeyType;

public class LabelKeyTypeUtils {

	public static final String LABEL_KEY_PREFIX = "label";

	private LabelKeyTypeUtils() {
	}

	public static <E extends Enum<E> & LabelKeyType> String getKey(E e) {
		String className = e.getDeclaringClass().getSimpleName().toLowerCase();
		String elementName = e.name().toLowerCase();
		return Joiner.on('.').join(LABEL_KEY_PREFIX, className, elementName);
	}
}
