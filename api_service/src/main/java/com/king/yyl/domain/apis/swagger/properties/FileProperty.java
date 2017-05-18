package com.king.yyl.domain.apis.swagger.properties;

import com.king.yyl.domain.apis.swagger.Xml;

public class FileProperty extends AbstractProperty implements Property {
    public FileProperty() {
        super.type = "file";
    }

    public static boolean isType(String type, String format) {
        if (type != null && "file".equals(type.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    public FileProperty xml(Xml xml) {
        this.setXml(xml);
        return this;
    }
}
