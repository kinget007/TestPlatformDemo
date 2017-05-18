
package com.king.yyl.domain;

public class Config extends NamedEntity {
    private static final long serialVersionUID = 1L;

    // Note : Name field is used for display purpose whereas key is the unique name for a particular configuration.
    private String configKey;

    private String configValue;

    public String getConfigKey() {
	return configKey;
    }

    public void setConfigKey(String configKey) {
	this.configKey = configKey;
    }

    public String getConfigValue() {
	return configValue;
    }

    public void setConfigValue(String configValue) {
	this.configValue = configValue;
    }

}
