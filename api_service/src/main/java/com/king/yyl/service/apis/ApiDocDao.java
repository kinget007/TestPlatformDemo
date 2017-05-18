package com.king.yyl.service.apis;

import com.king.yyl.domain.apis.custom.ApiDoc;
import com.king.yyl.domain.apis.custom.ApiListInfo;
import com.king.yyl.domain.apis.moudle.InfoVersions;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by apple on 16/10/18.
 */
public interface ApiDocDao {

    List<ApiDoc> selectApiDocAll();

    List<ApiDoc> selectApiDocByTitle(String title);

    List<ApiDoc> selectApiDocByVersion(String version);

    ApiDoc selectApiDocByTitleAndVersion(String title, String version);

    List<InfoVersions> selectInfosAll();

    ApiDoc selectApiDocOne();

    Boolean insertApiDoc(JSONObject apiInfo);

    Boolean removeApiDoc(ApiDoc apiInfo);

    Boolean updateApiDoc(ApiDoc apiInfo);

    List<ApiListInfo> selectApisAll();

    List<ApiListInfo> selectApisByTitleAndVersion(String title, String version);

    List<ApiListInfo> selectApisByVersion(String version);

    List<ApiListInfo> selectApisByTitle(String title);

}
