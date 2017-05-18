//
//package com.king.yyl.service.handler.http;
//
//import com.king.yyl.service.dto.ApiRequestDTO;
//import com.king.yyl.service.dto.ApiResponseDTO;
//import org.apache.http.client.methods.HttpDelete;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class DeleteHandler extends GenericHandler {
//
//    public ApiResponseDTO process(ApiRequestDTO apiRequestDTO) throws IOException {
//	ApiResponseDTO response = null;
//	CloseableHttpClient httpclient = HttpClients.createDefault();
//	HttpDelete httpDelete = new HttpDelete(apiRequestDTO.getScheme() + "://" + apiRequestDTO.getHost() + apiRequestDTO.getPort() + apiRequestDTO.getPath());
//	httpDelete.addHeader("Content-Type", "application/json");
//	try {
//	    response = processHttpRequest(httpDelete, httpclient);
//	} finally {
//	    httpclient.close();
//	}
//	return response;
//    }
//
//}
