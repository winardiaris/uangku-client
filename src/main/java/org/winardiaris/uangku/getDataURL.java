/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.winardiaris.uangku;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author arst
 */
public class getDataURL {
    String getData(String url) throws IOException{
         CloseableHttpClient httpclient = HttpClients.createDefault();
        try {           
            HttpGet httpget = new HttpGet(url);
            System.out.println("Executing request " + httpget.getRequestLine());

            ResponseHandler<String> responseHandler;
             responseHandler = new ResponseHandler<String>() {
                 
                 @Override
                 public String handleResponse(
                         final HttpResponse response) throws ClientProtocolException, IOException {
                     int status = response.getStatusLine().getStatusCode();
                     if (status >= 200 && status < 300) {
                         HttpEntity entity = response.getEntity();
                         return entity != null ? EntityUtils.toString(entity) : null;
                     } else {
                         throw new ClientProtocolException("Unexpected response status: " + status);
                     }
                 }
             };
             
            String responseURL = httpclient.execute(httpget, responseHandler);
            return responseURL;
        } finally {
            httpclient.close();
        }
    }
}
