package com.king.yyl.domain.apis.swagger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.king.yyl.domain.apis.swagger.refs.GenericRef;
import com.king.yyl.domain.apis.swagger.refs.RefFormat;
import com.king.yyl.domain.apis.swagger.refs.RefType;

/**
 * Created by Helmsdown on 7/8/15.
 */
public class RefPath extends Path {

    private GenericRef genericRef;

    public RefPath() {
    }

    public RefPath(String ref) {
        set$ref(ref);
    }

    public void set$ref(String ref) {
        this.genericRef = new GenericRef(RefType.PATH, ref);
    }

    public String get$ref() {
        return genericRef.getRef();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RefPath refPath = (RefPath) o;

        return !(genericRef != null ? !genericRef.equals(refPath.genericRef) : refPath.genericRef != null);

    }

    @Override
    public int hashCode() {
        return genericRef != null ? genericRef.hashCode() : 0;
    }

    @JsonIgnore
    public RefFormat getRefFormat() {
        return this.genericRef.getFormat();
    }

}
