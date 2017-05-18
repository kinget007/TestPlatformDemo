//
//package com.king.yyl.service.handler.http;
//
//import com.king.yyl.domain.parameters.BodyParameter;
//import com.king.yyl.service.dto.ApiRequestDTO;
//import com.king.yyl.service.dto.ApiResponseDTO;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class PostHandler extends GenericHandler {
//
//    Logger logger = LoggerFactory.getLogger(PostHandler.class);
//
//    public ApiResponseDTO process(ApiRequestDTO apiRequestDTO) throws IOException {
//	ApiResponseDTO response = null;
//    BodyParameter bodyParameter = new BodyParameter();
//	CloseableHttpClient httpclient = HttpClients.createDefault();
//	HttpPost httpPost = new HttpPost(apiRequestDTO.getScheme() + "://" + apiRequestDTO.getHost() + apiRequestDTO.getPort() + apiRequestDTO.getPath());
//	httpPost.addHeader("Content-Type", "application/json");
//
//	for(int i = 0 ; i <= apiRequestDTO.getParameters().size();i++){
//	    if(apiRequestDTO.getParameters().get(i).getIn().equals("body")){
//            bodyParameter = ((BodyParameter)apiRequestDTO.getParameters().get(i));
//        }
//    }
//	httpPost.setEntity(new StringEntity(bodyParameter.getValue()));
//	try {
//	    response = processHttpRequest(httpPost, httpclient);
//	} finally {
//	    httpclient.close();
//	}
//	return response;
//    }
//}
