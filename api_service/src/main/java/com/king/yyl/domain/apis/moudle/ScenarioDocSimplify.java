package com.king.yyl.domain.apis.moudle;

import org.springframework.data.annotation.Id;

/**
 * Created by apple on 16/10/3.
 */
public class ScenarioDocSimplify {
    @Id
    private String _id;
    protected String tagName;
    protected String scenarioName;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }
}
