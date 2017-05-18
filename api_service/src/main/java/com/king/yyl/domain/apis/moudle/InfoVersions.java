package com.king.yyl.domain.apis.moudle;

import java.util.List;

/**
 * Created by apple on 16/10/16.
 */
public class InfoVersions {

    private String _id;//title

    private List<String> versions;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<String> getVersions() {
        return versions;
    }

    public void setVersions(List<String> versions) {
        this.versions = versions;
    }
}
