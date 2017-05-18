package com.king.yyl.domain.parameters;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "in")
@JsonSubTypes({
    @JsonSubTypes.Type(name = "body", value = BodyParameter.class),
    @JsonSubTypes.Type(name = "path", value = PathParameter.class),
    @JsonSubTypes.Type(name = "query", value = QueryParameter.class),
    @JsonSubTypes.Type(name = "form", value = FormParameter.class),
    @JsonSubTypes.Type(name = "cookie", value = CookieParameter.class),
    @JsonSubTypes.Type(name = "header", value = HeaderParameter.class)}
)
public interface Parameter {
    String getIn();

    void setIn(String in);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    String getExample();

    void setExample(String example);

    String getValue();

    void setValue(String value);

    boolean getRequired();

    void setRequired(boolean required);


}
