//
//package com.king.yyl.service.handler.http;
//
//import com.king.yyl.domain.parameters.BodyParameter;
//import com.king.yyl.service.dto.ApiRequestDTO;
//import com.king.yyl.service.dto.ApiResponseDTO;
//import org.apache.http.client.methods.HttpPut;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class PutHandler extends GenericHandler {
//
//    public ApiResponseDTO process(ApiRequestDTO apiRequestDTO) throws IOException {
//	ApiResponseDTO response = null;
//    BodyParameter bodyParameter = new BodyParameter();
//    CloseableHttpClient httpclient = HttpClients.createDefault();
//	HttpPut httpPut = new HttpPut(apiRequestDTO.getScheme() + "://" + apiRequestDTO.getHost() + apiRequestDTO.getPort() + apiRequestDTO.getPath());
//	httpPut.addHeader("Content-Type", "application/json");
//
//        for(int i = 0 ; i <= apiRequestDTO.getParameters().size();i++){
//            if(apiRequestDTO.getParameters().get(i).getIn().equals("body")){
//                bodyParameter = ((BodyParameter)apiRequestDTO.getParameters().get(i));
//            }
//        }
//
//	httpPut.setEntity(new StringEntity(bodyParameter.getValue()));
//	try {
//	    response = processHttpRequest(httpPut, httpclient);
//	} finally {
//	    httpclient.close();
//	}
//	return response;
//    }
//
//}
