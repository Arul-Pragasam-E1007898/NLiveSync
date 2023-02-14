import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Writer {
    private final LinkedBlockingQueue q;

    Writer(LinkedBlockingQueue q){
        this.q = q;
    }

    public void write(){
        try {
            String payload = (String) q.take();
            while (!payload.equals(Constants.END)) {
                publish(payload, 0);
                payload = (String) q.take();
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void publish(String payload, int attempt){
        try {
            if(attempt<Constants.MAX_ATTEMPTS) {
                final int statusCode = bulkUpsert(payload);
                if(statusCode>299) {
                    publish(payload, attempt + 1);
                    Thread.sleep(30000);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private int bulkUpsert(String payload) throws IOException, JSONException {
        final HttpPost httpPost = new HttpPost(Constants.ENDPOINT);

        final StringEntity entity = new StringEntity(payload);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Authorization", Constants.TOKEN_VALUE);

        httpPost.setEntity(entity);

        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(httpPost);
        final int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity responseEntity = response.getEntity();
        processResponse(statusCode, responseEntity);
        return statusCode;
    }

    private void processResponse(int statusCode, HttpEntity responseEntity) throws IOException, JSONException {
        if(statusCode==200) {
            String responseString = EntityUtils.toString(responseEntity, "UTF-8");
            JSONObject json =new JSONObject(responseString);
            System.out.println(Thread.currentThread().getName() +
                " - " + Constants.ENDPOINT + " Response from FreshSales: " + statusCode + ". Jobstatus: " + json.get("job_status_url"));
        } else
            System.out.println(Thread.currentThread().getName() +
                " - " + Constants.ENDPOINT + " - Response from FreshSales: " + statusCode);
    }
}
