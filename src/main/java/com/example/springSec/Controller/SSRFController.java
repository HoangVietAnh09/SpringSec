package com.example.springSec.Controller;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ssrf")
public class SSRFController {

    private static final List<String> BLOCK_IPS = Arrays.asList( "127.0.0.1", "0.0.0.0", "::1", "localhost",
            "169.254.169.254", "169.254.169.253");

    private static final List<String> BLOCKED_CIDRS = Arrays.asList(
            "10.0.0.0/8", "172.16.0.0/12", "192.168.0.0/16"
    );

    public static boolean isIPInRange(String ip, String cidr) {
        String[] parts = cidr.split("/");
        String subnet = parts[0];
        int prefix = Integer.parseInt(parts[1]);

        byte[] ipBytes = ipToBytes(ip);
        byte[] subnetBytes = ipToBytes(subnet);

        int mask = 0xFFFFFFFF << (32 - prefix);
        int ipInt = bytesToInt(ipBytes);
        int subnetInt = bytesToInt(subnetBytes);

        return (ipInt & mask) == subnetInt;
    }
    public static byte[] ipToBytes(String ip) {
        String[] octets = ip.split("\\.");
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            bytes[i] = (byte) Integer.parseInt(octets[i]);
        }
        return bytes;
    }
    public static int bytesToInt(byte[] bytes) {
        int result = 0;
        for(byte b : bytes) {
            result = (result << 8) + (b & 0xff);
        }
        return result;
    }

    public static boolean isBlocked(String url) throws Exception{
        URL uri = new URL(url);
        String host = uri.getHost();

        InetAddress addr = InetAddress.getByName(host);
        String ip = addr.getHostAddress();

        if(BLOCK_IPS.contains(ip) || BLOCK_IPS.contains(host)){
            return true;
        }

        for (String blockedIp : BLOCKED_CIDRS) {
            if(isIPInRange(ip, blockedIp)){
                return true;
            }
        }

        return false;
    }
    @GetMapping("/vuln1")
    public String vuln_1(@RequestParam String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }


    @GetMapping("/vuln2")
    public String vuln_2(@RequestParam String url) throws Exception {
        URL urlObj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = in.readLine()) != null) {
            sb.append(line);
        }
        in.close();
        return sb.toString();
    }

    @GetMapping("/vuln3")
    public String vuln_3(@RequestParam String url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);

        return EntityUtils.toString(response.getEntity());
    }

    private final OkHttpClient client = new OkHttpClient();

    @GetMapping
    public String fetchUrl(@RequestParam String url) throws Exception {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @GetMapping("/safe1")
    public void safe_1(@RequestParam String url) throws Exception {
       if(isBlocked(url)){
           throw new SecurityException();
       }

       HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
       con.setRequestMethod("GET");
       con.setConnectTimeout(5000);
       con.setReadTimeout(5000);

       int responseCode = con.getResponseCode();
       System.out.println(responseCode);
    }
}
