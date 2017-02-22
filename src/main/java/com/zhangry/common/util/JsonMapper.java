package com.zhangry.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import com.zhangry.common.page.Page;
import com.zhangry.common.page.QueryParameter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Created by zhangry on 2017/2/20.
 */
class JsonMapper {
    private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);
    private ObjectMapper mapper;
    private SimpleDateFormat sdf;

    public JsonMapper() {
        this("yyyy-MM-dd hh:mm:ss");
    }

    public JsonMapper(String customDatePattern) {
        this.sdf = new SimpleDateFormat(customDatePattern);
        this.mapper = new ObjectMapper();
        this.mapper.setDateFormat(this.sdf);
        this.mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        this.mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    }

    public String convertToJson(Object object) {
        try {
            return this.mapper.writeValueAsString(object);
        } catch (IOException var3) {
            logger.warn("write to json string error:" + object, var3);
            return null;
        }
    }

    public <T> T convertToObject(String jsonString, Class<T> clazz) {
        if(StringUtils.isEmpty(jsonString)) {
            return null;
        } else {
            try {
                return this.mapper.readValue(jsonString, clazz);
            } catch (IOException var4) {
                logger.warn("parse json string error:" + jsonString, var4);
                return null;
            }
        }
    }

    public <T> T convertToObject(String jsonString, JavaType javaType) {
        if(StringUtils.isEmpty(jsonString)) {
            return null;
        } else {
            try {
                return this.mapper.readValue(jsonString, javaType);
            } catch (IOException var4) {
                logger.warn("parse json string error:" + jsonString, var4);
                return null;
            }
        }
    }

    public JavaType createCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return this.mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public <T> T update(String jsonString, T object) {
        try {
            return this.mapper.readerForUpdating(object).readValue(jsonString);
        } catch (JsonProcessingException var4) {
            logger.warn("update json string:" + jsonString + " to object:" + object + " error.", var4);
        } catch (IOException var5) {
            logger.warn("update json string:" + jsonString + " to object:" + object + " error.", var5);
        }

        return null;
    }

    public void enableEnumUseToString() {
        this.mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        this.mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    }

    public ObjectMapper getMapper() {
        return this.mapper;
    }

    public String convertToJson(Object obj, String... columns) {
        return this.objectToJson(obj, columns);
    }

    private String objectToJson(Object obj, String... columns) {
        if(obj == null) {
            return "";
        } else {
            Page objMap;
            String objValue;
            if(columns != null && columns.length != 0) {
                if(this.isListObject(obj)) {
                    List var13 = (List)obj;
                    objValue = this.convertToJson(this.parseToListMap(var13, columns));
                    return objValue.replace(":\"[", ":[").replace("\\\"", "\"").replace("]\"", "]");
                } else if(this.isSetObject(obj)) {
                    Set var12 = (Set)obj;
                    return this.objectToJson(Lists.newArrayList(var12), columns);
                } else if(this.isPageObject(obj)) {
                    objMap = (Page)obj;
                    if(objMap.getResult() != null) {
                        objMap.setResult(this.parseToListMap(objMap.getResult(), columns));
                    }

                    objValue = this.convertToJson(objMap.getResult());
                    return this.processPageToJson(objMap, objValue);
                } else {
                    HashMap var11 = new HashMap();
                    objValue = null;
                    String[] arr$ = columns;
                    int len$ = columns.length;

                    for(int i$ = 0; i$ < len$; ++i$) {
                        String p = arr$[i$];
                        Map var14 = this.getValue(obj, p);
                        if(var14 != null) {
                            String key = (String)var14.keySet().iterator().next();
                            Object objNext = var14.values().iterator().next();
                            if(objNext == null) {
                                var11.put(key, "");
                            } else {
                                var11.put(key, objNext.toString());
                            }
                        }
                    }

                    return this.convertToJson(var11).replace(":\"[", ":[").replace("\\\"", "\"").replace("]\"", "]");
                }
            } else if(!this.isPageObject(obj)) {
                return this.convertToJson(obj);
            } else {
                objMap = (Page)obj;
                objValue = this.convertToJson(objMap.getResult());
                return this.processPageToJson(objMap, objValue);
            }
        }
    }

    private boolean isSetObject(Object obj) {
        boolean result = false;

        try {
            Set ex = (Set)obj;
            result = true;
        } catch (Exception var4) {
            ;
        }

        return result;
    }

    private String processPageToJson(Page objPage, String resultJson) {
        new StringBuilder();
        int totalPages = objPage.getTotalPages();
        int totalCount = objPage.getTotalCount();
        if(totalPages == 0) {
            objPage.setPageNo(0);
        }

        Integer pageSize = Integer.valueOf(objPage.getPageSize());
        if(totalPages == 0 && pageSize != null && pageSize.intValue() != 0) {
            totalPages = totalCount % pageSize.intValue() == 0?totalCount / pageSize.intValue():totalCount / pageSize.intValue() + 1;
        }

        StringBuilder strBuilder = (new StringBuilder("{\"totalpages\":")).append(totalPages).append(",\"currpage\":").append(objPage.getPageNo()).append(",\"totalrecords\":").append(totalCount).append(",\"total\":").append(totalCount).append(", \"result\":").append(resultJson).append("}");
        return strBuilder.toString();
    }

    public String listJsonToPageJson(String listJson, QueryParameter parameter) {
        List lstTmp = this.convertToListMap(listJson);
        Page page = new Page(parameter.getPageSize(), true);
        page.setPageNo(parameter.getPageNo());
        page.setTotalCount(lstTmp != null?lstTmp.size():0);
        if(lstTmp != null && lstTmp.size() != 0) {
            int startIndex = (page.getPageNo() - 1) * page.getPageSize();
            int endIndex = page.getPageNo() * page.getPageSize();
            if(startIndex < 0) {
                startIndex = 0;
            }

            if(endIndex >= lstTmp.size()) {
                endIndex = lstTmp.size() - 1;
            }

            lstTmp.subList(startIndex, endIndex);
            return this.processPageToJson(page, this.convertToJson(lstTmp));
        } else {
            return this.convertToJson(page);
        }
    }

    private List<Map<Object, Object>> parseToListMap(List<Object> objects, String[] columns) {
        ArrayList list = new ArrayList(objects.size());
        Iterator i$ = objects.iterator();

        while(i$.hasNext()) {
            Object item = i$.next();
            HashMap objMap = new HashMap(columns.length);
            String[] arr$ = columns;
            int len$ = columns.length;

            for(int i$1 = 0; i$1 < len$; ++i$1) {
                String column = arr$[i$1];
                Map objValue = this.getValue(item, column);
                String tmpValue = "";
                if(objValue.values().iterator().next() != null) {
                    tmpValue = String.valueOf(objValue.values().iterator().next());
                }

                if(!this.checkSpecialType(column)) {
                    objMap.put(objValue.keySet().iterator().next(), tmpValue);
                } else {
                    int index = column.indexOf(".{");
                    String propertyName = column.substring(0, index);
                    objMap.put(propertyName, tmpValue);
                }
            }

            list.add(objMap);
        }

        return list;
    }

    private boolean isListObject(Object obj) {
        boolean result = false;

        try {
            List ex = (List)obj;
            result = true;
        } catch (Exception var4) {
            ;
        }

        return result;
    }

    private boolean isPageObject(Object obj) {
        return obj.getClass().equals(Page.class);
    }

    private Map<String, Object> getValue(Object obj, String fieldName) {
        fieldName = fieldName.trim();
        String strFirstObjects;
        String strOtherObjects;
        if(!this.checkSpecialType(fieldName)) {
            Object index1 = null;

            try {
                if(fieldName.contains(":")) {
                    String[] strFirstObjects1 = fieldName.split(":");
                    Assert.isTrue(strFirstObjects1.length == 2 && !StringUtil.isNullOrEmpty(strFirstObjects1[1]));
                    fieldName = strFirstObjects1[0];
                    strOtherObjects = strFirstObjects1[1];
                    index1 = PropertyUtils.getProperty(obj, fieldName);
                    Assert.isTrue(index1 instanceof Date);
                    SimpleDateFormat map1 = new SimpleDateFormat();
                    if(strOtherObjects.equals(DATATYPE_DATETIME.DATE.name())) {
                        map1.applyPattern("yyyy-MM-dd");
                    } else if(strOtherObjects.equals(DATATYPE_DATETIME.TIME.name())) {
                        map1.applyPattern("HH:mm:ss");
                    } else if(strOtherObjects.equals(DATATYPE_DATETIME.CUSTOM.name())) {
                        map1 = this.sdf;
                    } else {
                        map1.applyPattern("yyyy-MM-dd HH:mm:ss");
                    }

                    index1 = map1.format(index1);
                } else {
                    index1 = PropertyUtils.getProperty(obj, fieldName);
                    if(index1 instanceof Date) {
                        index1 = this.sdf.format(index1);
                    }
                }
            } catch (Exception var9) {
                logger.warn("field value is null. fieldName = " + fieldName);
            }

            if(index1 instanceof String) {
                strFirstObjects = String.valueOf(index1);
                index1 = strFirstObjects.contains("\"")?strFirstObjects.replaceAll("\"", "\\\\\\\""):strFirstObjects;
            }

            HashMap strFirstObjects2 = new HashMap();
            strFirstObjects2.put(fieldName, index1);
            return strFirstObjects2;
        } else {
            int index = fieldName.indexOf(".{");
            strFirstObjects = fieldName.substring(0, index);
            strOtherObjects = fieldName.substring(index + 1);
            strOtherObjects = strOtherObjects.substring(1, strOtherObjects.length() - 1);
            Map map = this.getValue(obj, strFirstObjects);
            if(map == null) {
                return null;
            } else {
                String[] strParams = strOtherObjects.contains("{")?this.getParams(strOtherObjects):strOtherObjects.split(",");
                HashMap rtnObject = new HashMap();
                rtnObject.put(map.keySet().iterator().next(), this.convertToJson(map.values().iterator().next(), strParams));
                return rtnObject;
            }
        }
    }

    private String[] getParams(String property) {
        ArrayList result = new ArrayList();
        ArrayDeque czDeque = new ArrayDeque();
        StringBuilder tmpResult = new StringBuilder();
        StringBuilder str = new StringBuilder(property);
        int length = str.length();

        for(int i = 0; i < length; ++i) {
            char c = str.charAt(i);
            if(c == 44) {
                if(czDeque.isEmpty()) {
                    result.add(tmpResult.toString());
                    tmpResult.setLength(0);
                } else {
                    tmpResult.append(c);
                }
            } else {
                if(c == 123) {
                    czDeque.addLast(Character.valueOf(c));
                } else if(c == 125) {
                    czDeque.removeLast();
                }

                tmpResult.append(c);
            }

            if(i == length - 1) {
                czDeque = null;
                result.add(tmpResult.toString());
            }
        }

        return (String[])result.toArray(new String[result.size()]);
    }

    private boolean checkSpecialType(String fieldName) {
        return fieldName.contains(".{");
    }

    public List<Map<String, Object>> convertToListMap(String jsonData) {
        if(!jsonData.startsWith("[") && !jsonData.endsWith("]")) {
            jsonData = "[" + jsonData + "]";
        }

        return (List)this.convertToObject(jsonData, (JavaType)this.createCollectionType(List.class, new Class[]{Map.class}));
    }

    public <T extends Map> Map convertToMap(String jsonData, String[] parameters, boolean isReserved) {
        Map result = this.convertToMap(jsonData);
        if(result == null) {
            return null;
        } else if(parameters != null && parameters.length != 0) {
            ArrayList lstParameters = new ArrayList();
            String[] kv = parameters;
            int lstRemovedKeys = parameters.length;

            String k;
            for(int i$ = 0; i$ < lstRemovedKeys; ++i$) {
                k = kv[i$];
                lstParameters.add(k);
            }

            Map var11 = (Map)result;
            ArrayList var12 = new ArrayList();
            if(isReserved) {
                Set var13 = var11.keySet();
                Assert.isTrue(var13 != null && var13.size() > 0);
                Iterator var15 = var13.iterator();

                while(var15.hasNext()) {
                    String k1 = (String)var15.next();
                    if(!lstParameters.contains(k1)) {
                        var12.add(k1);
                    }
                }
            } else {
                var12 = lstParameters;
            }

            if(var12 != null && var12.size() > 0) {
                Iterator var14 = var12.iterator();

                while(var14.hasNext()) {
                    k = (String)var14.next();
                    if(var11.containsKey(k)) {
                        var11.remove(k);
                    }
                }
            }

            return var11;
        } else {
            return result;
        }
    }

    public Map<String, Object> convertToMap(String jsonData) {
        return (Map)this.convertToObject(jsonData, (JavaType)this.createCollectionType(Map.class, new Class[]{String.class, Object.class}));
    }

    public static enum DATATYPE_DATETIME {
        FULL,
        DATE,
        TIME,
        CUSTOM;

        private DATATYPE_DATETIME() {
        }
    }
}

