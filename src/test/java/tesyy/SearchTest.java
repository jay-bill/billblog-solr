package tesyy;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class SearchTest {
	@Test
	public void queryDocument() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://192.168.241.130:8080/solr");
		//创建一个查询对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery("WEIBO_CONTENT:不能");
		query.setHighlight(true);
		query.addHighlightField("WEIBO_CONTENT");
		query.setHighlightSimplePre("<font style='color:red;font-weight:blod'>");
		query.setHighlightSimplePost("</font>");
		//执行查询
		QueryResponse response = solrServer.query(query);
		//取查询结果
		 Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		SolrDocumentList solrDocumentList = response.getResults();
		System.out.println("共查询到记录：" + solrDocumentList.getNumFound());
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("WEIBO_CONTENT"));	
			System.out.println(highlighting.get(solrDocument.get("id")).get("WEIBO_CONTENT").get(0));
			System.out.println("-----------------------------------------------");
			System.out.println();
		}
	}
}
