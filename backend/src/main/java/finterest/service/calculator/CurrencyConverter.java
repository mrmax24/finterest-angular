package finterest.service.calculator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;

public class CurrencyConverter {
    private static final String API_BASE_URL =
            "https://api.apilayer.com/exchangerates_data/latest?"
                    + "apikey=n8j1GFjRKE8xNs2HOOQoMGw5CO3zkdt6";

    public static BigDecimal convertToUah(BigDecimal amount, String currencyCode)
            throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(API_BASE_URL);
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity);
        JSONObject jsonResponse = new JSONObject(responseString);
        BigDecimal exchangeRate = jsonResponse.getJSONObject("rates").getBigDecimal("UAH");
        return amount.multiply(exchangeRate);
    }
}


