package com.king.yyl.domain.apis.custom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.king.yyl.domain.apis.swagger.HttpMethod;
import com.king.yyl.domain.apis.swagger.Model;
import com.king.yyl.domain.apis.swagger.Operation;
import com.king.yyl.domain.apis.swagger.Scheme;
import com.king.yyl.service.utils.swagger.core.util.Json;

import java.util.List;
import java.util.Map;

/**
 * Created by apple on 16/10/22.
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class ApiListInfo implements Serializable {
//
//    private static final long serialVersionUID = 1L;
public class ApiListInfo{
    protected String urlPath;
    protected HttpMethod httpMethod;
    protected Operation operation;
    protected Map<String, Model> definitions;
    protected String host;
    protected String basePath;
    protected String title;
    protected String version;
    protected List<Scheme> scheme;

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Map<String, Model> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(Map<String, Model> definitions) {
        this.definitions = definitions;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Scheme> getScheme() {
        return scheme;
    }

    public void setScheme(List<Scheme> scheme) {
        this.scheme = scheme;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((urlPath == null) ? 0 : urlPath.hashCode());
        result = prime * result + ((httpMethod == null) ? 0 : httpMethod.hashCode());
        result = prime * result + ((operation == null) ? 0 : operation.hashCode());
        result = prime * result + ((definitions == null) ? 0 : definitions.hashCode());
        result = prime * result + ((host == null) ? 0 : host.hashCode());
        result = prime * result + ((basePath == null) ? 0 : basePath.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        result = prime * result + ((scheme == null) ? 0 : scheme.hashCode());

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
        ApiListInfo other = (ApiListInfo) obj;
        if (urlPath == null) {
            if (other.urlPath != null) {
                return false;
            }
        } else if (!urlPath.equals(other.urlPath)) {
            return false;
        }

        if (httpMethod == null) {
            if (other.httpMethod != null) {
                return false;
            }
        } else if (!httpMethod.equals(other.httpMethod)) {
            return false;
        }

        if (operation == null) {
            if (other.operation != null) {
                return false;
            }
        } else if (!operation.equals(other.operation)) {
            return false;
        }

        if (definitions == null) {
            if (other.definitions != null) {
                return false;
            }
        } else if (!definitions.equals(other.definitions)) {
            return false;
        }

        if (host == null) {
            if (other.host != null) {
                return false;
            }
        } else if (!host.equals(other.host)) {
            return false;
        }

        if (basePath == null) {
            if (other.basePath != null) {
                return false;
            }
        } else if (!basePath.equals(other.basePath)) {
            return false;
        }

        if (title == null) {
            if (other.title != null) {
                return false;
            }
        } else if (!title.equals(other.title)) {
            return false;
        }

        if (version == null) {
            if (other.version != null) {
                return false;
            }
        } else if (!version.equals(other.version)) {
            return false;
        }

        if (scheme == null) {
            if (other.scheme != null) {
                return false;
            }
        } else if (!scheme.equals(other.scheme)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        String result = super.toString();
        try {
            result = Json.pretty(Json.mapper().writeValueAsString(this));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
