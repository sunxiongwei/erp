/**
 * 文件名: QLBuilder.java 2012-7-9 ©Copyright 2012 北京广信智远信息技术有限责任公司. All rights
 * reserved. 如果需要使用此文件，请参考北京广信智远信息技术有限责任公司的授权协议文件
 */
package com.sun.framework.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 查询语句构建器，可用于JPQL和SQL
 * 
 * @版本 1.0
 */
public class QLBuilder {

    /**
     * mainClause 主语句，可以是INSERT/DELETE/UPDATE/SELECT部分
     */
    private String mainClause;

    /**
     * whereClause 条件部分
     */
    private StringBuffer whereClause;

    /**
     * setClause 如果是更新语句，那么是设置部分，只有在UPDATE的时候才有此部分
     */
    private StringBuffer setClause;

    /**
     * appendClause 一些附加语句，比如排序之类的
     */
    private StringBuffer appendClause;

    private Map<String, Object> params = new HashMap<String, Object>();

    /**
     * 生成完整的QL语句
     * 
     * @return 完整的QL语句
     */
    public String getFullQL() {
        StringBuffer ql = new StringBuffer("");

        ql.append(mainClause);

        if (setClause != null && StringUtils.isNoneEmpty(setClause.toString())) {
            ql.append(" SET ").append(setClause);
        }

        if (whereClause != null && StringUtils.isNoneEmpty(whereClause.toString())) {
            ql.append(" WHERE ").append(whereClause);
        }
        if (appendClause != null && StringUtils.isNoneEmpty(appendClause.toString())) {
            ql.append(" ").append(appendClause);
        }

        return ql.toString();
    }

    /**
     * 给属性{@link #mainClause mainClause}设置新值
     */
    public void setMainClause(String mainClause) {
        this.mainClause = mainClause;
    }

    /**
     * 获取属性{@link #mainClause mainClause}的值
     */
    public String getMainClause() {
        return this.mainClause;
    }

    /**
     * 获取属性{@link #whereClause whereClause}的值
     */
    public StringBuffer getWhereClause() {
        return whereClause;
    }

    /**
     * 获取属性{@link #params params}的值
     */
    public Map<String, Object> getParams() {
        return params;
    }

    /**
     * 增加一个Where条件，并且用逻辑AND运算符连接
     * 
     * @param ql 查询语句，可以是SQL或者是JPQL
     * @param params 查询语句中对应变量的值
     */
    public void and(String ql, Object...params) {
        setVarlibles(ql, params);

        if (whereClause == null) {
            whereClause = new StringBuffer("");
        }

        if (StringUtils.isNotEmpty(whereClause.toString())) {
            whereClause.append(" AND ");
        }

        whereClause.append(" (").append(ql).append(") ");
    }

    /**
     * 增加一个Where条件，并且用逻辑AND运算符连接
     * 
     * @param ql 查询语句，可以是SQL或者是JPQL
     * @param params 查询语句中对应变量的值
     */
    public void or(String ql, Object...params) {
        setVarlibles(ql, params);

        if (whereClause == null) {
            whereClause = new StringBuffer("");
        }

        if (StringUtils.isNotEmpty(whereClause.toString())) {
            whereClause.append(" OR ");
        }

        whereClause.append(" (").append(ql).append(") ");
    }

    /**
     * 在UPDATE的情况下添加一个更新列
     * 
     * @param ql 查询语句，可以是SQL或者是JPQL
     * @param params 查询语句中对应变量的值
     */
    public void set(String ql, Object...params) {
        setVarlibles(ql, params);

        if (setClause == null) {
            setClause = new StringBuffer("");
        }

        if (StringUtils.isNotEmpty(setClause.toString())) {
            setClause.append(",");
        }

        setClause.append(" ").append(ql).append(" ");
    }

    /**
     * 追加语句，包括排序、分组等
     * 
     * @param ql QL语句
     */
    public void add(String ql) {
        if (appendClause == null) {
            appendClause = new StringBuffer("");
        }

        appendClause.append(" ").append(ql);
    }

    /**
     * 获取查询语句中的所有变量名，并以数组形式返回
     * 
     * @param ql 查询语句，可以是jpql或者是sql
     * @return 参数名列表，如果没有参数返回为null
     */
    public String[] resolveVarlible(String ql) {
        List<String> list = new ArrayList<String>();
        String regex = "\\:\\w+";

        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(ql);

        String val = null;

        while (m.find()) {
            val = m.group();

            list.add(val);
        }

        if (val == null) {
            return null;
        } else {
            String[] result = new String[list.size()];

            for (int i = 0; i < result.length; i++) {
                result[i] = list.get(i).substring(1);
            }

            return result;
        }
    }

    /**
     * 分析ql语句中的变量并放入到params中
     */
    public void setVarlibles(String ql, Object...params) {
        String[] paramNames = this.resolveVarlible(ql);

        if (paramNames == null || paramNames.length == 0) {
            return;
        }

        if (paramNames.length != params.length) {
            throw new RuntimeException("参数个数和参数值个数不相等");
        }

        for (int i = 0; i < paramNames.length; i++) {
            this.params.put(paramNames[i], params[i]);
        }
    }
}
