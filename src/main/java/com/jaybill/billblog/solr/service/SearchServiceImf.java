package com.jaybill.billblog.solr.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jaybill.billblog.solr.mapper.ResultMapper;
import com.jaybill.billblog.solr.pojo.Result;

@Service
public class SearchServiceImf implements SearchService {

	@Autowired
	private ResultMapper resultMap;
	@Autowired
	private SolrServer server;
	
	@Override
	public int importAllResult() throws Exception {
		//获取weibo表里的weiboId和weiboContent
		List<Result> resList = resultMap.getResultList();
		//写进solr索引库
		for(Result res : resList){
			//创建一个SolrInputDocument对象
			SolrInputDocument doc = new SolrInputDocument();
			doc.setField("id", res.getWeiboId());
			doc.setField("WEIBO_ID", res.getWeiboId());
			doc.setField("WEIBO_CONTENT", res.getWeiboContent());
			//写入索引库
			server.add(doc);
		}
		//提交修改
		server.commit();
		return resList.size();//返回记录数
	}

	/**
	 * 从solr索引中获取值，返回
	 */
	@Override
	public List<Result> queryOnCondition(String key){
//		String keyword="";
//		try {
//			keyword = new String(key.getBytes("ISO-8859-1"),"utf-8");
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
		String keyword = "";
		try {
			keyword = URLDecoder.decode(key,"utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		//创建一个查询对象
		SolrQuery query = new SolrQuery();
		query.setQuery("WEIBO_CONTENT:"+keyword);
		query.setHighlight(true);
		query.addHighlightField("WEIBO_CONTENT");
		query.setHighlightSimplePre("<font style='color:red;font-weight:bold'>");
		query.setHighlightSimplePost("</font>");
		//执行查询
		QueryResponse response = null;
		try {
			response = server.query(query);
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		//取查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		//存储结果的List
		ArrayList<Result> arr = new ArrayList<Result>();
		for (SolrDocument solrDocument : solrDocumentList) {
			Result res = new Result();
			res.setWeiboId(Integer.valueOf(solrDocument.getFieldValue("WEIBO_ID").toString()));
			res.setWeiboContent(highlighting.get(solrDocument.get("id")).get("WEIBO_CONTENT").get(0));
			arr.add(res);
		}
		return arr;
	}
}
