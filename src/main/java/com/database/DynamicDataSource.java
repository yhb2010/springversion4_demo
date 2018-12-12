package com.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
	@Override
	protected Object determineCurrentLookupKey() {
		if (DbContextHolder.getDbType() == null
				|| DbContextHolder.getDbType().equals("")) {
			DbContextHolder.setDbType("xxxReader");
		}
		return DbContextHolder.getDbType();
	}
}
