package com.sun.framework.persistence.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Transient;

public abstract class AbstractEntity<PK extends Serializable> implements Serializable {
    private static final long serialVersionUID = 607413790083487783L;

    private PK id;

    public PK getId() {
        return id;
    }

    protected void setId(final PK id) {
        this.id = id;
    }

    @Transient
    private Map<String, Object> others = new HashMap<String, Object>(0);

    @Transient
    public boolean isNew() {
        return null == getId();
    }

    public void setNew(boolean n) {
        return;
    }

    @Override
    public String toString() {
        return String.format("Entity of type %s with id: %s", this.getClass().getName(), getId());
    }

    @Override
    public boolean equals(Object obj) {

        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        AbstractEntity<?> that = (AbstractEntity<?>) obj;
        return null == this.getId() ? false : this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += null == getId() ? 0 : getId().hashCode() * 31;
        return hashCode;
    }

    public Map<String, Object> getOthers() {
        return others;
    }

    public void setOthers(Map<String, Object> others) {
        this.others = others;
    }
}