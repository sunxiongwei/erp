package com.sun.framework.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractManualEntity extends AbstractEntity<String> {
    private static final long serialVersionUID = -5101117479287902543L;
    // 一般主键不要太长
    @Id
    @Column(name = "id", length = 64)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
