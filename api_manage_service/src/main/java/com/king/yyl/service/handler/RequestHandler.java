//
//package com.king.yyl.service.handler;
//
//import com.king.yyl.service.handler.http.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RequestHandler extends AbstractRequestHandler {
//
//    @Autowired
//    GetHandler getHandler;
//
//    @Autowired
//    PostHandler postHandler;
//
//    @Autowired
//    PutHandler putHandler;
//
//    @Autowired
//    DeleteHandler deleteHandler;
//
//    public GenericHandler getHandler(String methodType) {
//	switch (methodType.toLowerCase()) {
//	case "get":
//	    return getHandler;
//	case "put":
//	    return putHandler;
//	case "delete":
//	    return deleteHandler;
//	case "post":
//	    return postHandler;
//	}
//	return getHandler;
//    }
//
//}
