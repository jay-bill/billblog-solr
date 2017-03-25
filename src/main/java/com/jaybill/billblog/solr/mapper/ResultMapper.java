package com.jaybill.billblog.solr.mapper;

import java.util.List;

import com.jaybill.billblog.solr.pojo.Result;

/**
 * 查询接口
 * @author jaybill
 *
 */
public interface ResultMapper {
	/**
	 * 获取weibo表中全部id和微博内容，放到solr索引里面
	 * @return
	 */
	List<Result> getResultList();
}
