package com.king.yyl.domain.parameters;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value = "query")
public class QueryParameter extends AbstractParameter implements Parameter{

    public QueryParameter() {
        super.setIn("query");
    }

}
