package com.king.yyl.domain.apis.custom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.king.yyl.domain.apis.swagger.HttpMethod;
import com.king.yyl.service.utils.swagger.core.util.Json;

import java.util.List;
import java.util.Map;

/**
 * Created by apple on 16/10/22.
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class ScenarioApiInfo  implements Cloneable ,Serializable {

//private static final long serialVersionUID = 1L;
public class ScenarioApiInfo{

    protected String scheme = "http";
    protected String host;
    protected String basePath;
    protected String pathUrl;
    protected HttpMethod httpMethod;
    protected Proxy proxy;
    protected List<Check> checks;
    protected List<FieldValue> fieldValues;
    protected Map<String,String> headers;

    protected String title;
    protected String version;
    protected String response;


    public String getPathUrl() {
        return pathUrl;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public List<Check> getChecks() {
        return checks;
    }

    public void setChecks(List<Check> checks) {
        this.checks = checks;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
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

    public List<FieldValue> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<FieldValue> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public boolean equals(Object obj) { if (this == obj) {
        return true;
    }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ScenarioApiInfo other = (ScenarioApiInfo) obj;
        if (pathUrl == null) {
            if (other.pathUrl != null) {
                return false;
            }
        } else if (!pathUrl.equals(other.pathUrl)) {
            return false;
        }

        if (scheme == null) {
            if (other.scheme != null) {
                return false;
            }
        } else if (!scheme.equals(other.scheme)) {
            return false;
        }

        if (response == null) {
            if (other.response != null) {
                return false;
            }
        } else if (!response.equals(other.response)) {
            return false;
        }

        if (proxy == null) {
            if (other.proxy != null) {
                return false;
            }
        } else if (!proxy.equals(other.proxy)) {
            return false;
        }

        if (httpMethod == null) {
            if (other.httpMethod != null) {
                return false;
            }
        } else if (!httpMethod.equals(other.httpMethod)) {
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

        if (headers == null) {
            if (other.headers != null) {
                return false;
            }
        } else if (!headers.equals(other.headers)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pathUrl == null) ? 0 : pathUrl.hashCode());
        result = prime * result + ((scheme == null) ? 0 : scheme.hashCode());
        result = prime * result + ((response == null) ? 0 : response.hashCode());
        result = prime * result + ((proxy == null) ? 0 : proxy.hashCode());
        result = prime * result + ((httpMethod == null) ? 0 : httpMethod.hashCode());
        result = prime * result + ((host == null) ? 0 : host.hashCode());
        result = prime * result + ((basePath == null) ? 0 : basePath.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        result = prime * result + ((headers == null) ? 0 : headers.hashCode());
        return result;
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
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
