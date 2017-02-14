package org.example.service;


import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class BillService {
    public String bill(String fromDate, String toDate) throws Exception {

        String hostName="cloudmgt.cloud.wso2.com";
        String schemeName="https";
        String loginPath="/cloudmgt/site/blocks/user/authenticate/ajax/login.jag";
        String billPath="/cloudmgt/site/blocks/billing/usage/get/ajax/get.jag";
        String loginAction= "login";
        String usageAction="getTenantUsage";
        String userName="stcwso2@gmail.com@stc1384";
        String password="password123#@!";

        URIBuilder loginBuilder = new URIBuilder();
        loginBuilder.setScheme(schemeName).setHost(hostName).setPath(loginPath)
                .setParameter("action", loginAction)
                .setParameter("userName", userName)
                .setParameter("password", password);

        URIBuilder billBuilder = new URIBuilder();
        billBuilder.setScheme(schemeName).setHost(hostName).setPath(billPath)
                .setParameter("action", usageAction)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate);


        RequestConfig globalConfig = RequestConfig.custom().build();

        HttpClientContext context = HttpClientContext.create();
        CookieStore cookieStore = new BasicCookieStore();
        context.setCookieStore(cookieStore);

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig).setDefaultCookieStore(cookieStore).build();
        HttpPost httpPost = new HttpPost(loginBuilder.build());
        CloseableHttpResponse loginResponse = httpClient.execute(httpPost, context);

        HttpPost httpPost1 = new HttpPost(billBuilder.build());
        HttpResponse billResponse = httpClient.execute(httpPost1, context);
        System.out.println(billResponse.getStatusLine());
        InputStreamReader is = new InputStreamReader(billResponse.getEntity().getContent());
        BufferedReader rd = new BufferedReader(is);
        String line = "";
        StringBuilder sb = new StringBuilder();
        while ((line = rd.readLine()) != null) {
            sb.append(line + "\n");
            System.out.println(line);
            
        }
        System.out.println(context.getCookieStore().getCookies());
        return sb.toString();
    }


}
	    


	
	
	
	
 

