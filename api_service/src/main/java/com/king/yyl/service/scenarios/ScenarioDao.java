package com.king.yyl.service.scenarios;

import com.king.yyl.domain.apis.custom.ApiListInfo;
import com.king.yyl.domain.apis.custom.ScenarioDoc;
import com.king.yyl.domain.apis.moudle.ScenarioDocSimplify;
import com.king.yyl.domain.apis.moudle.ScenarioTagName;

import java.util.List;

/**
 * Created by apple on 16/10/17.
 */
public interface ScenarioDao {

    Boolean createScenarioDocWithEmailTagNameAndScenarioName(String email, String tagName, String scenarioName, String title, String version);

    List<ScenarioDoc> selectScenarioDocByEmail(String email);

    List<ScenarioDoc> selectScenarioDocByEmailAndTagName(String email, String tagName);

    List<ScenarioDoc> selectScenarioDocByEmailAndScenarioName(String email, String scenarioName);

    ScenarioDoc selectScenarioDocByEmailTagNameAndScenarioName(String email, String tagName, String scenarioName);

    ScenarioDoc selectScenarioDocByScenarioId(String idScenario);

    Boolean upateScenarioDoc(ScenarioDoc scenarioDoc);

    List<ScenarioTagName> selectTagNameAndScenarioNamesByEmail(String email);

    List<ApiListInfo> selectApisByScenarioId(String scenarioId);

    List<ScenarioDocSimplify> selectScenarioDocSimplifyByEmail(String email);

}
