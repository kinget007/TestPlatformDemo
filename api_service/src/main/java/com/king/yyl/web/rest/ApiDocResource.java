package com.king.yyl.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.king.yyl.service.apis.ApiDocDao;
import com.king.yyl.service.apis.ApiDocImplMongo;
import com.king.yyl.domain.apis.custom.ApiDoc;
import com.king.yyl.domain.apis.custom.ApiListInfo;
import com.king.yyl.domain.apis.moudle.InfoVersions;
import com.king.yyl.domain.apis.swagger.Swagger;
import com.king.yyl.domain.commons.ResultData;
import net.sf.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by yilongyu on 2016/10/26.
 */
@RestController
@RequestMapping("/api")
public class ApiDocResource {

    final static ApiDocDao apiDocDao = new ApiDocImplMongo();

    @GetMapping("/apiDoc/infos")
    public ResponseEntity<ResultData> getApiDocInfos() {
        List<InfoVersions> infoVersionsList = apiDocDao.selectInfosAll();
        return ResponseEntity.ok().body(ResultData.getSuccessInstance(infoVersionsList, ""));
    }

    @GetMapping("/apiDoc")
    @Timed
    public ResponseEntity<Swagger> getApiDoc() {
//        log.debug("REST request to get JobJenkins : {}", id);
        ApiDoc apiDoc = apiDocDao.selectApiDocOne();
            return Optional.ofNullable(apiDoc.getSwagger())
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/apiDoc/{title}/{version}")
    public ResponseEntity<Swagger> getApiDocByTitleAndVersion(@PathVariable("title")  String title, @PathVariable("version") String version) {
        ApiDoc apiDoc = apiDocDao.selectApiDocByTitleAndVersion(title, version);
        return ResponseEntity.ok().body(apiDoc.getSwagger());
    }


    @GetMapping("/apiDoc2")
    public ResponseEntity<Swagger> getApiDocByTitleAndVersion2(@RequestParam("title")  String title, @RequestParam("version") String version) {
        ApiDoc apiDoc = apiDocDao.selectApiDocByTitleAndVersion(title, version);
        return ResponseEntity.ok().body(apiDoc.getSwagger());
    }


    @GetMapping("/apiDocs")
    public ResponseEntity<ResultData> getApiDocs() {
        List<ApiDoc> apiDocs = apiDocDao.selectApiDocAll();
        return ResponseEntity.ok().body(ResultData.getSuccessInstance(apiDocs, ""));
    }

    @GetMapping("/apiDocs/title/{title}")
    public ResponseEntity<ResultData> getApiDocsByTitle(@PathVariable("title") String title) {
        List<ApiDoc> apiDocs = apiDocDao.selectApiDocByTitle(title);
        return ResponseEntity.ok().body(ResultData.getSuccessInstance(apiDocs, ""));
    }

    @GetMapping("/apiDocs/version/{version}")
    public ResponseEntity<ResultData> getApiDocsByVersion(@PathVariable("version") String version) {
        List<ApiDoc> apiDocs = apiDocDao.selectApiDocByTitle(version);
        return ResponseEntity.ok().body(ResultData.getSuccessInstance(apiDocs, ""));
    }

    @GetMapping("/apis")
    public ResponseEntity<ResultData> getApis() {
        List<ApiListInfo> apiListInfoList = apiDocDao.selectApisAll();
        return ResponseEntity.ok().body(ResultData.getSuccessInstance(apiListInfoList, ""));
    }


    @GetMapping("/apis/title/{title}")
    public ResponseEntity<ResultData> getApisByTitle(@PathVariable("title")  String title) {
        List<ApiListInfo> apiListInfoList = apiDocDao.selectApisByTitle(title);
        return ResponseEntity.ok().body(ResultData.getSuccessInstance(apiListInfoList, ""));
    }


    @GetMapping("/apis/version/{version}")
    public ResponseEntity<ResultData> getApisByVersion(@PathVariable("version") String version) {
        List<ApiListInfo> apiListInfoList = apiDocDao.selectApisByVersion(version);
        return ResponseEntity.ok().body(ResultData.getSuccessInstance(apiListInfoList, ""));
    }

    @GetMapping("/apis/{title}/{version}")
    public ResponseEntity<ResultData> getApisByTitleAndVersion(@PathVariable("title") String title, @PathVariable("version") String version) {
        List<ApiListInfo> apiListInfoList = apiDocDao.selectApisByTitleAndVersion(title, version);
        return ResponseEntity.ok().body(ResultData.getSuccessInstance(apiListInfoList, ""));
    }

    @PostMapping("/apiDoc")
    public ResponseEntity<ResultData> addApiDoc(@RequestBody String apiInfo) throws IOException {
        JSONObject jsonObject = JSONObject.fromObject(apiInfo);
        Boolean flag = apiDocDao.insertApiDoc(jsonObject);
        return ResponseEntity.ok().body(ResultData.getSuccessInstance(flag, ""));
    }

//    @ApiOperation(notes = "ApiDoc Update", value = "apiDocUpdate", nickname = "apiDocUpdate", tags = {"ApiDocResource"})
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "Success", response = Swagger.class),
//            @ApiResponse(code = 400, message = "Invalid Data", response = Swagger.class),
//            @ApiResponse(code = 404, message = "Not Found", response = Swagger.class)
//    })
//    @RequestMapping(value = "/apiDoc", method = RequestMethod.PUT, produces = "application/json")
//    public ResponseEntity<ResultData> updateApiDoc() {
//        return ResponseEntity.ok().body(ResultData.getSuccessInstance("",""));
//    }
//
//    @ApiOperation(notes = "ApiDoc Delete", value = "apiDocDelete", nickname = "apiDocDelete", tags = {"ApiDocResource"})
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "Success", response = Swagger.class),
//            @ApiResponse(code = 400, message = "Invalid Data", response = Swagger.class),
//            @ApiResponse(code = 404, message = "Not Found", response = Swagger.class)
//    })
//    @RequestMapping(value = "/apiDoc/{apiDocId}", method = RequestMethod.DELETE, produces = "application/json")
//    public ResponseEntity<ResultData> removeApiDocByApiId(@PathVariable("apiDocId") String apiId) {
//        return ResponseEntity.ok().body(ResultData.getSuccessInstance("",""));
//
//    }

}
