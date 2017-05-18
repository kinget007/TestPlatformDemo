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
public abstract class AbstractParameter {
    protected String in;
    protected String name;
    protected String description;
    protected boolean required = false;
    protected String example;
    protected String value;

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((in == null) ? 0 : in.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((example == null) ? 0 : example.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        result = prime * result + (required ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractParameter other = (AbstractParameter) obj;
        if (example == null) {
            if (other.example != null) {
                return false;
            }
        } else if (!example.equals(other.example)) {
            return false;
        }
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (in == null) {
            if (other.in != null) {
                return false;
            }
        } else if (!in.equals(other.in)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }

        if (required != other.required) {
            return false;
        }
        return true;
    }
}
