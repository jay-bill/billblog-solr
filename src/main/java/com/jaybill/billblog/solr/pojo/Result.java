package com.jaybill.billblog.solr.pojo;

/**
 * 查询结果
 * @author jaybill
 *
 */
public class Result {
	private long weiboId;
	public long getWeiboId() {
		return weiboId;
	}
	public void setWeiboId(long weiboId) {
		this.weiboId = weiboId;
	}
	public String getWeiboContent() {
		return weiboContent;
	}
	public void setWeiboContent(String weiboContent) {
		this.weiboContent = weiboContent;
	}
	private String weiboContent;
}
