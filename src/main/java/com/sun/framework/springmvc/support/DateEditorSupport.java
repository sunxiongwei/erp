package com.ris.framework.springmvc.support;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateEditorSupport extends PropertyEditorSupport {
    protected final Logger logger = LoggerFactory.getLogger(DateEditorSupport.class);
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat minFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void setAsText(String text) {
        if (text == null) {
            return;
        }
        // 先验证long类型
        if (text.length() == 13 && text.indexOf(":") < 0 && text.indexOf("-") < 0) {
            this.setValue(new Date(Long.parseLong(text)));
            return;
        }
        // 其他格式后续考虑, 下面的判断不严谨，但只要系统统一约定，可以保证系统的正确性
        DateFormat df = null;
        if (text.length() == 19) {
            df = timeFormat;
        } else if (text.length() == 16) {
            df = minFormat;
        } else if (text.length() == 10) {
            df = dateFormat;
        } else {
            return;
        }
        try {
            this.setValue(df.parse(text));
        } catch (ParseException e) {
            logger.error("时间格式不符合要求, {}", text);
            // 格式不符合要求
            throw new java.lang.IllegalArgumentException(text);
        }
    }
}
