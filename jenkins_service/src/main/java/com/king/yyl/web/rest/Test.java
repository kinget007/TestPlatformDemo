//package com.king.yyl.web.rest;
//
//import com.offbytwo.jenkins.JenkinsServer;
//import com.offbytwo.jenkins.model.JobWithDetails;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;
//
///**
// * Created by king.yu on 2016/12/13.
// */
//public class Test {
//    public static void main(String[] args) throws URISyntaxException, IOException {
//        URI uri = new URI("http://localhost:32769");
//
//        JenkinsServer server = new JenkinsServer(uri,"king.yu","num1isMe");
//        String xmlString = "<?xml version='1.0' encoding='UTF-8'?>\n" +
//            "<project>\n" +
//            "  <description>testJob2</description>\n" +
//            "  <keepDependencies>false</keepDependencies>\n" +
//            "  <properties/>\n" +
//            "  <scm class=\"hudson.scm.NullSCM\"/>\n" +
//            "  <canRoam>true</canRoam>\n" +
//            "  <disabled>false</disabled>\n" +
//            "  <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>\n" +
//            "  <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>\n" +
//            "  <triggers/>\n" +
//            "  <concurrentBuild>false</concurrentBuild>\n" +
//            "  <builders>\n" +
//            "    <hudson.tasks.Shell>\n" +
//            "      <command>env </command>\n" +
//            "    </hudson.tasks.Shell>\n" +
//            "  </builders>\n" +
//            "  <publishers/>\n" +
//            "  <buildWrappers/>\n" +
//            "</project>";
//        String jobName = "testJob2";
//        server.createJob(jobName,xmlString,false);
//        JobWithDetails jobWithDetails = server.getJob(jobName);
//
//        System.out.println(jobWithDetails.getDisplayName());
//
//
//    }
//}
