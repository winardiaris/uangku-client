package id.my.arwin.uangku;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

public class CustomHTTPClient {
   /** The time it takes for our client to timeout */
    public static final int HTTP_TIMEOUT = 30000; // milliseconds
 
    /** Single instance of our HttpClient */
    private static HttpClient mHttpClient;

    /**
     * Get our single instance of our HttpClient object.
     *
     * @return an HttpClient object with connection parameters set
     */
    private static HttpClient getHttpClient() {
        try {
            /*
            // Load CAs from an InputStream
            // (could be from a resource or ByteArrayInputStream or ...)
            SSLSocketFactory socketFactory;
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            if (Global.cerFile != null) {
                InputStream caInput = Global.cerFile;
                Certificate certificate = cf.generateCertificate(caInput);

                // Create a KeyStore containing our trusted CAs
                String keyStoreType = KeyStore.getDefaultType();
                KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                keyStore.load(null, null);
                keyStore.setCertificateEntry("ca", certificate);
                socketFactory = new CustomSSLSocketFactory(keyStore, true);
                socketFactory.setHostnameVerifier(new X509HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        HostnameVerifier hv =
                                HttpsURLConnection.getDefaultHostnameVerifier();
                        return hv.verify(Const.RAW_URL, sslSession);
                    }

                    @Override
                    public void verify(String s, SSLSocket sslSocket) throws IOException {}

                    @Override
                    public void verify(String s, X509Certificate x509Certificate) throws SSLException {}

                    @Override
                    public void verify(String s, String[] strings, String[] strings1) throws SSLException {}
                });
            } else {
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(null, null);

                socketFactory = new CustomSSLSocketFactory(keyStore, false);
                socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            }

            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 15000);
            HttpConnectionParams.setSoTimeout(params, 5000);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", socketFactory, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
            mHttpClient = new DefaultHttpClient(ccm, params);
*/
            if (mHttpClient == null) {
                mHttpClient = new DefaultHttpClient();
                final HttpParams param = mHttpClient.getParams();
                HttpConnectionParams.setConnectionTimeout(param, HTTP_TIMEOUT);
                HttpConnectionParams.setSoTimeout(param, HTTP_TIMEOUT);
                ConnManagerParams.setTimeout(param, HTTP_TIMEOUT);
            }

            return mHttpClient;
        } catch (Exception e) {
            return mHttpClient;
        }
    }
 
    /**
     * Performs an HTTP Post request to the specified url with the
     * specified parameters.
     *
     * @param url The web address to post the request to
     * @param postParameters The parameters to send via the request
     * @return The result of the request
     * @throws Exception
     */
    public static String executeHttpPost(String url, ArrayList<NameValuePair> postParameters) throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            HttpPost request = new HttpPost(url);
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
            request.setEntity(formEntity);
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
 
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
 
            String result = sb.toString();
            return result;
        }finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
 
    /**
     * Performs an HTTP GET request to the specified url.
     *
     * @param url The web address to post the request to
     * @return The result of the request
     * @throws Exception
     */
    public static String executeHttpGet(String url) throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
 
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
 
            String result = sb.toString();
            return result;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}