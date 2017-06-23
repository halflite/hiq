package net.halflite.hiq.jpa;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.mappings.DatabaseMapping;

/** エンティティの順番の重み付け実装 */
public class OrderCustomizer implements DescriptorCustomizer {

	@Override
	public void customize(ClassDescriptor descriptor) throws Exception {
		descriptor.setShouldOrderMappings(true);
		addWeight(descriptor);
	}

	/**
	 * データベースのカラムに、フィールドと同じ順番の重みを付けます
	 *
	 * @param descriptor
	 */
	protected void addWeight(ClassDescriptor descriptor) {
		final Field[] fields = descriptor.getJavaClass().getFields();
		// フィールド名のUpperCaseがキー、順番が値の連投配列を作ります
		Map<String, Integer> order = IntStream.range(0, fields.length)
				.boxed()
				.collect(toMap(fields));
		descriptor.getMappings()
				.stream()
				.forEach(new DatabaseMappingAction(order));
	}

	/** エンティティのフィールドに重み付けするConsumer実装クラス */
	protected static class DatabaseMappingAction implements Consumer<DatabaseMapping> {

		/** フィールド名のUpperCaseがキー、順番が値の連投配列 */
		private final Map<String, Integer> order;

		public DatabaseMappingAction(Map<String, Integer> order) {
			this.order = order;
		}

		@Override
		public void accept(DatabaseMapping mapping) {
			String key = mapping.getAttributeName().toUpperCase();
			int weight = order.getOrDefault(key, Integer.MAX_VALUE);
			mapping.setWeight(weight);
		}
	}

	protected static Collector<Integer, ?, Map<String, Integer>> toMap(final Field[] fields) {
		return Collectors.toMap(
				i -> fields[i].getName().toUpperCase(),
				i -> i);
	}

}
