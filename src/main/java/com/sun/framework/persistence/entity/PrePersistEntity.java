package com.sun.framework.persistence.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import com.sun.framework.persistence.exception.PersistenceException;

@MappedSuperclass
public class PrePersistEntity<PK extends Serializable> extends AbstractEntity<PK> {

    private static final long serialVersionUID = -4485015840156690999L;
    private static final String emptyString = "";

    /**
     * @Transient private Integer version;
     * 
     *            public Integer getVersion() { return version; }
     * 
     * 
     *            public void setVersion(Integer version) { this.version = version; }
     **/

    @PrePersist
    @PreUpdate
    public void prePersist() {
        // 鑾峰彇鍊间负绌虹殑灞炴��
        Class<?> clazz = this.getClass();
        // Field[] fds = clazz.getDeclaredFields();
        List<Field> allField = new ArrayList<Field>();
        while (true) {
            if (clazz.equals(PrePersistEntity.class)) {
                break;
            }
            Field[] fds = clazz.getDeclaredFields();
            for (Field f : fds) {
                allField.add(f);
            }
            clazz = clazz.getSuperclass();
        }

        // 鐗堟湰鑷姩鍔�1
        // this.version = this.version + 1;
        Object v = null;
        try {
            for (Field f : allField) {
                // 鏍规嵁娉ㄨВ妫�鏌ユ洿鏂版椂闂寸瓑灞炴��
                f.setAccessible(true);
                v = f.get(this);
                if (v == null) {
                    if (f.getType().equals(String.class)) {
                        f.set(this, emptyString);
                    } else if (f.getType().equals(Date.class)) {
                        f.set(this, new Date());
                    } else if (f.getType().equals(Integer.class)) {
                        f.set(this, 0);
                    } else if (f.getType().equals(Long.class)) {
                        f.set(this, 0L);
                    } else if (f.getType().equals(Boolean.class)) {
                        f.set(this, Boolean.FALSE);
                    }
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new PersistenceException(e);
        }
    }
}
