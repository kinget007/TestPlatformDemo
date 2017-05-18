//
//package com.king.yyl.service.handler.http;
//
//import com.king.yyl.service.dto.ApiRequestDTO;
//import com.king.yyl.service.dto.ApiResponseDTO;
//import com.king.yyl.service.handler.http.auth.BasicAuthHandler;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class GetHandler extends GenericHandler {
//
//    Logger logger = LoggerFactory.getLogger(GetHandler.class);
//
//    public ApiResponseDTO process(ApiRequestDTO apiRequestDTO) throws IOException {
//	ApiResponseDTO response = null;
//	CloseableHttpClient httpclient = HttpClients.createDefault();
//	HttpGet httpGet = new HttpGet(apiRequestDTO.getScheme() + "://" + apiRequestDTO.getHost() + apiRequestDTO.getPort() + apiRequestDTO.getPath());
//	try {
//	    response = processHttpRequest(httpGet, httpclient);
//
//	} finally {
//	    httpclient.close();
//	}
//	return response;
//    }
//
//    public ApiResponseDTO process(String apiUrl, String userName, String password, boolean useBasic64Auth) throws IOException {
//	ApiResponseDTO response = null;
//
//	CloseableHttpClient httpclient = null;
//	BasicAuthHandler basicHttpAuthHandler = new BasicAuthHandler();
//	HttpGet httpRequest = new HttpGet(apiUrl);
//	// TODO : Add auth logic.
//	if (useBasic64Auth) {
//	    //httpclient = basicHttpAuthHandler.prepareBasicAuthWithBase64Encode(httpRequest, userName, password);
//	} else {
//	    // httpclient = basicHttpAuthHandler.prepareBasicAuth(userName, password);
//	}
//	try {
//	    response = processHttpRequest(httpRequest, httpclient);
//	} finally {
//	    httpclient.close();
//	}
//
//	return response;
//    }
//}
