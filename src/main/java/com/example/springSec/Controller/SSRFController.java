package com.example.springSec.Controller;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ssrf")
public class SSRFController {

//    Vuln code
//    @GetMapping
//    public String fetchUrl(@RequestParam String url) {
//        RestTemplate restTemplate = new RestTemplate();
//        return restTemplate.getForObject(url, String.class);
//    }


//    safe code
//    @GetMapping
//    public String getUrl(@RequestParam String url) throws Exception {
//        URL parsedUrl = new URL(url);
//        String host = parsedUrl.getHost();
//
//        if(host.equals("localhost") || host.equals("127.0.0.1") || host.equals("fbi.com") || host.startsWith("192.168")) {
//            throw  new SecurityException("Access denied");
//        }
//
//        RestTemplate restTemplate = new RestTemplate();
//        return restTemplate.getForObject(url, String.class);
//    }

//    vuln code
//    @GetMapping
//    public String fetchUrl(@RequestParam String url) throws Exception {
//        URL urlObj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
//        con.setRequestMethod("GET");
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while((line = in.readLine()) != null) {
//            sb.append(line);
//        }
//        in.close();
//        return sb.toString();
//    }

//    vuln code
//    @GetMapping
//    public String fetchUrl(@RequestParam String url) throws Exception {
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//
//        HttpGet httpGet = new HttpGet(url);
//        CloseableHttpResponse response = httpClient.execute(httpGet);
//
//        return EntityUtils.toString(response.getEntity());
//    }

    private final OkHttpClient client = new OkHttpClient();

    @GetMapping
    public String fetchUrl(@RequestParam String url) throws Exception {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    @Controller
    @ResponseBody
    public static class CSRFController {

    }
}
