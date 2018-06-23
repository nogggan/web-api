package com.gxf.webapi.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.gxf.webapi.aspect.RoutingDataSource;
import com.gxf.webapi.util.DataSourceContext;
import com.gxf.webapi.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource{

	@Override
	protected Object determineCurrentLookupKey() {
		String lookupKey = DataSourceContext.get();
		log.debug("LookupKey : {}",lookupKey);
		return StringUtils.isEmpty(lookupKey)?RoutingDataSource.MASTER:lookupKey;
	}

}
