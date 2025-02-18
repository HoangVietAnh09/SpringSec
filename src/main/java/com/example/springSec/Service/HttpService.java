package com.example.springSec.Service;


import org.springframework.stereotype.Service;

import java.net.http.HttpHeaders;

@Service
public interface HttpService {

    String requestHttp(String url, HttpHeaders headers);

    String requestHttpBanRedirects(String url, HttpHeaders headers);


}
