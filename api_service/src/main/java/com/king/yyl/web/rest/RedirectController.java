//package com.king.yyl.web.rest;
//
//import com.king.yyl.domain.users.UserInfo;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import springfox.documentation.annotations.ApiIgnore;
//
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
///**
// * Created by yilongyu on 2016/10/11.
// */
//@Controller
//public class RedirectController {
//
//    @ApiIgnore
//    @GetMapping("/apis")
//    public String apis() throws IOException {
//        return "redirect:/swagger/index.html";
//    }
//
//    @ApiIgnore
//    @GetMapping("/apiEditPage")
//    public String apiEditPage(HttpSession session) {
//        UserInfo user = (UserInfo) session.getAttribute(("UserInfo"));
//        if (null == user) {
//            return "common/LoginPage";
//        }
//        return "APIEdit";
//
//    }
//
//    @ApiIgnore
//    @GetMapping("/loginPage")
//    public String loginPage() {
//        return "common/LoginPage";
//    }
//
//    @ApiIgnore
//    @GetMapping("/")
//    public String home() {
//        return "home";
//    }
//
//    @ApiIgnore
//    @GetMapping("/registerPage")
//    public String registerPage() {
//        return "common/RegisterPage";
//    }
//
//    @ApiIgnore
//    @GetMapping("/apiDocPage2")
//    public String apiDocPage2(HttpSession session) {
//        UserInfo user = (UserInfo) session.getAttribute(("UserInfo"));
//        if (null == user) {
//            return "common/LoginPage";
//        }
//        return "apis/doc/ApiDocs";
//    }
//
//    @ApiIgnore
//    @GetMapping("/apiDocPage")
//    public String apiDocPage(HttpSession session) {
//        UserInfo user = (UserInfo) session.getAttribute(("UserInfo"));
//        if (null == user) {
//            return "common/LoginPage";
//        }
//        return "apis/doc/ApiDocs2";
//
//    }
//    @ApiIgnore
//    @GetMapping("/apiDesignPage")
//    public String apiDesignPage(HttpSession session) {
//        UserInfo user = (UserInfo) session.getAttribute(("UserInfo"));
//        if (null == user) {
//            return "common/LoginPage";
//        }
//        return "apis/design/ApiDesign";
//    }
//
//    @ApiIgnore
//    @GetMapping("/scenarioViewPage/{scenarioId}")
//    public String scenarioViewPage(@PathVariable("scenarioId") String scenarioId, HttpSession session) {
//        UserInfo user = (UserInfo) session.getAttribute(("UserInfo"));
//        if (null == user) {
//            return "common/LoginPage";
//        }
//        return "scenarios/view/ScenarioView";
//    }
//
//    @ApiIgnore
//    @GetMapping("/scenariosPage")
//    public String scenariosPage(HttpSession session) {
//        UserInfo user = (UserInfo) session.getAttribute(("UserInfo"));
//        if (null == user) {
//            return "common/LoginPage";
//        }
//        return "scenarios/add/Scenarios";
//    }
//
//    @ApiIgnore
//    @GetMapping(value = "/userSignout")
//    public String userSignout(HttpSession session) {
//        session.invalidate();
//        return "common/LoginPage";
//    }
//}
