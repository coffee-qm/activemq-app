package com.coffee.activemq.common.db.jdbc.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author QM
 */
public class JdbcDAO {

	private static Logger LOGGER = LoggerFactory.getLogger(JdbcDAO.class);

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Object queryForObject(final String sql, final Object[] args, final RowMapper requiredType)
			throws SQLException {
		Object o = null;
		try {
			o = jdbcTemplate.queryForObject(sql, args, requiredType);
		} catch (final DataAccessException e) {
			LOGGER.error("get error in " + this.getClass().getName()
					+ ".queryForObject(String sql, Object[] args, RowMapper requiredType)", e);
			throw new SQLException("执行SQL：" + sql + " 异常，异常信息：" + e.getMessage());
		}
		return o;
	}

	/**
	 * 根据SQL查询数据
	 * 
	 * @param sql
	 * @param args
	 * @return Object
	 * */
	public Object queryForObject(final String sql, final Object[] args) throws SQLException {
		Object o = null;
		try {
			o = jdbcTemplate.queryForObject(sql, args, Object.class);
		} catch (final Exception e) {
			LOGGER.error("get error in " + this.getClass().getName()
					+ ".queryForObject(String sql, Object[] args)", e);
			throw new SQLException("执行SQL：" + sql + " 异常，异常信息：" + e.getMessage());
		}
		return o;
	}

	/**
	 * 根据SQL查询数据
	 * 
	 * @param sql
	 * @return List
	 * */
	public List queryForList(final String sql) throws SQLException {
		List o = null;
		try {
			o = jdbcTemplate.queryForList(sql);
		} catch (final Exception e) {
			LOGGER.error("get error in " + this.getClass().getName() + ".queryForList(String sql)",
					e);
			throw new SQLException("执行SQL：" + sql + " 异常，异常信息：" + e.getMessage());
		}
		return o;
	}

	/**
	 * 根据SQL查询数据
	 * 
	 * @param sql
	 * @param args
	 * @return List
	 * */
	public List queryForList(final String sql, final Object[] args) throws SQLException {
		List o = null;
		try {
			o = jdbcTemplate.queryForList(sql, args);
		} catch (final Exception e) {
			LOGGER.error("get error in " + this.getClass().getName()
					+ ".queryForList(String sql, Object[] args)", e);
			throw new SQLException("执行SQL：" + sql + " 异常，异常信息：" + e.getMessage());
		}
		return o;
	}

	/**
	 * 根据SQL查询数据
	 * 
	 * @param sql
	 * @param args
	 * @return Map
	 * */
	public Map queryForMap(final String sql, final Object[] args) throws SQLException {
		Map o = null;
		try {
			o = jdbcTemplate.queryForMap(sql, args);
		} catch (final Exception e) {
			LOGGER.error("get error in " + this.getClass().getName()
					+ ".queryForMap(String sql, Object[] args)", e);
			throw new SQLException("执行SQL：" + sql + " 异常，异常信息：" + e.getMessage());
		}
		return o;
	}

	public void insertBySql(final String latnId, final String sql, final List<Object> val)
			throws SQLException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("enter mothed:" + this.getClass().getName()
					+ ".insertBySql(String latnId, String sql, List<Object> val)");
		}
		if (StringUtils.isEmpty(latnId) || StringUtils.isEmpty(sql) || CollectionUtils.isEmpty(val)) {
			return;
		}
		try {
			final List<Object> _val = new ArrayList<Object>();
			for (final Object o : val) {
				_val.add(o == null ? "" : o);
			}
			jdbcTemplate.update(sql, _val.toArray());
		} catch (final Exception e) {
			LOGGER.error("get error in " + this.getClass().getName()
					+ ".insertBySql(String latnId, String sql, List<Object> val)", e);
			throw new SQLException("执行SQL：" + sql + " 异常，异常信息：" + e.getMessage());
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("exit mothed:" + this.getClass().getName()
					+ ".insertBySql(String latnId, String sql, List<Object> val)");
		}
	}
}
