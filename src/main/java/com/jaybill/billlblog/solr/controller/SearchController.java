package com.jaybill.billlblog.solr.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jaybill.billblog.solr.pojo.Result;
import com.jaybill.billblog.solr.service.SearchService;

@Controller
@RequestMapping("search")
public class SearchController {
	@Autowired
	private SearchService searchService;
	public SearchController(){
		System.out.println("solr控制器启动了");
	}
	
	/**
	 * 导入weibo里weiboId和weiboContent两个字段到solr的索引库中，并返回总记录数
	 * @return
	 */
	@RequestMapping("importall")
	@ResponseBody
	public int importAllResult(){
		int num=0;
		try {
			num = searchService.importAllResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
	/**
	 * 按输入查询,因为是跨域访问，所以使用jsonp实现
	 * @param keyword
	 * @param request
	 * @return
	 */
//	@RequestMapping(value="queryOnCondition")
//	@ResponseBody
//	public JSONPObject queryOnCondition(@RequestParam(value="keyword",required=true) String keyword,
//			HttpServletRequest request,String callbackparam){
//		List<Result> list = searchService.queryOnCondition(keyword);
//		JSONPObject jsonp =new JSONPObject(callbackparam,list);
//		return jsonp;
//	}
	@RequestMapping(value="queryOnCondition")
	@ResponseBody
	public List<Result> queryOnCondition(@RequestParam(value="keyword",required=true) String keyword,
			HttpServletRequest request,String callbackparam){
		List<Result> list = searchService.queryOnCondition(keyword);
		return list;
	}
}
