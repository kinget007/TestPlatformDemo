package com.king.yyl.domain.apis.moudle;

import java.util.List;

/**
 * Created by apple on 16/10/18.
 */
public class ScenarioTagName {
    private String _id;//tag

    private List<String> scenarioNames;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<String> getScenarioNames() {
        return scenarioNames;
    }

    public void setScenarioNames(List<String> scenarioNames) {
        this.scenarioNames = scenarioNames;
    }
}
