package net.halflite.hiq.jpa;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_UNDERSCORE;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.Collection;

import javax.persistence.Table;

import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.internal.helper.DatabaseTable;
import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.sessions.Session;
import org.eclipse.persistence.tools.schemaframework.IndexDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaNameMappingCustomizer implements SessionCustomizer {

	private static final Logger LOGGER = LoggerFactory.getLogger(JpaNameMappingCustomizer.class);

	@Override
	public void customize(final Session session) throws Exception {
		// テーブルのマッピング
		session.getDescriptors()
				.values()
				.stream()
				.parallel()
				.filter(this::isTableUpdatable)
				.forEach(this::updateTableMappings);
		// カラムのマッピング
		session.getDescriptors()
				.values()
				.stream()
				.parallel()
				.map(ClassDescriptor::getMappings)
				.flatMap(Collection::stream)
				.filter(this::isColumnUpdatable)
				.forEach(this::updateColumnMappings);
		// インデクス
		session.getDescriptors()
				.values()
				.stream()
				.parallel()
				.map(ClassDescriptor::getTables)
				.flatMap(Collection::stream)
				.map(DatabaseTable::getIndexes)
				.flatMap(Collection::stream)
				.forEach(this::updateIndexIndexDefinition);
	}

	protected boolean isTableUpdatable(ClassDescriptor descriptor) {
		Class<?> javaClass = descriptor.getJavaClass();
		Table tableAnnotation = javaClass.getAnnotation(Table.class);
		boolean customTableNameDisabled = tableAnnotation == null || isNullOrEmpty(tableAnnotation.name());
		boolean tableEnabled = descriptor.getTables().isEmpty() == false;
		return customTableNameDisabled && tableEnabled;
	}

	protected void updateTableMappings(ClassDescriptor descriptor) {
		final String className = descriptor.getJavaClass().getSimpleName();
		final String tableName = UPPER_CAMEL.to(UPPER_UNDERSCORE, className);
		LOGGER.debug("JavaClassName:{}, TableName:{}", className, tableName);
		descriptor.setTableName(tableName);
		// テーブルに関連したインデックスに変更したテーブル名を反映させる
		descriptor.getTable(tableName)
				.getIndexes()
				.stream()
				.forEach(id -> id.setTargetTable(tableName));
	}

	protected void updateIndexIndexDefinition(IndexDefinition index) {
		String upperIndexName = index.getFullName().toUpperCase();
		LOGGER.debug("TableName:{}, IndexName:{}", index.getTargetTable(), upperIndexName);
		index.setName(upperIndexName);
	}

	protected boolean isColumnUpdatable(DatabaseMapping databaseMapping) {
		DatabaseField field = databaseMapping.getField();
		String attributeName = databaseMapping.getAttributeName();
		return field != null
				&& attributeName.isEmpty() == false
				&& attributeName.toUpperCase().equals(field.getName());
	}

	protected void updateColumnMappings(DatabaseMapping databaseMapping) {
		final String attributeName = databaseMapping.getAttributeName();
		final String columnName = LOWER_CAMEL.to(UPPER_UNDERSCORE, attributeName);
		LOGGER.debug("AttributeName:{}, ColumnName:{}", attributeName, columnName);
		final DatabaseField field = databaseMapping.getField();
		field.setName(columnName);
	}
}
