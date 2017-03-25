package com.jaybill.billblog.solr.service;

import java.util.List;

import com.jaybill.billblog.solr.pojo.Result;

public interface SearchService {
	/**
	 * 将weibo数据库里全部内容导入索引库
	 * @return
	 * @throws  
	 * @throws Exception 
	 */
	int importAllResult() throws Exception;

	/**
	 * 按输入查询
	 * @param keyword
	 * @return
	 */
	List<Result> queryOnCondition(String keyword);
}
