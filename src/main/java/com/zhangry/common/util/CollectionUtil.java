package com.zhangry.common.util;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;


/**
 * Created by zhangry on 2017/2/20.
 */
public class CollectionUtil {
    private CollectionUtil() {
    }

    public static <T> List<T> filter(List<T> unfiltered, Predicate<? super T> predicate) {
        return Lists.newArrayList(Iterables.filter(unfiltered, predicate));
    }

    public static <T> T find(List<T> list, Predicate<? super T> predicate) {
        return Iterables.find(list, predicate, (Object) null);
    }

    public static Map<String, Object> queryStringToMap(String queryString) {
        AssertUtil.notNull(queryString, "queryString cannot be null.");
        if (queryString.contains("#")) {
            queryString = queryString.substring(0, queryString.indexOf("#"));
        }

        ArrayList list = Lists.newArrayList(queryString.split("&"));
        HashMap result = new HashMap(list.size());
        Iterator i$ = list.iterator();

        while (i$.hasNext()) {
            String item = (String) i$.next();
            String[] keyValuePair = item.split("=");
            result.put(keyValuePair[0], keyValuePair[1]);
        }

        return result;
    }

    public static Map extractToMap(Collection collection, String keyPropertyName, String valuePropertyName) {
        HashMap map = new HashMap(collection.size());

        try {
            Iterator e = collection.iterator();

            while (e.hasNext()) {
                Object obj = e.next();
                map.put(PropertyUtils.getProperty(obj, keyPropertyName), PropertyUtils.getProperty(obj, valuePropertyName));
            }

            return map;
        } catch (Exception var6) {
            throw ExceptionUtil.unchecked(var6);
        }
    }

    public static <T> List<T> union(Collection<T> a, Collection<T> b) {
        ArrayList result = new ArrayList(a);
        result.addAll(b);
        return result;
    }

    public static <T> List<T> subtract(Collection<T> a, Collection<T> b) {
        ArrayList list = new ArrayList(a);
        Iterator i$ = b.iterator();

        while (i$.hasNext()) {
            Object element = i$.next();
            list.remove(element);
        }

        return list;
    }

    public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
        ArrayList list = new ArrayList();
        Iterator i$ = a.iterator();

        while (i$.hasNext()) {
            Object element = i$.next();
            if (b.contains(element)) {
                list.add(element);
            }
        }

        return list;
    }

    public static List<Map<String, Object>> listToTree(List<Map<String, Object>> datas, String idField, String pIdField) {
        if (datas != null && datas.size() != 0) {
            ArrayList rootDatas = new ArrayList(datas.size());
            int length = datas.size();
            Map data = null;

            for (int i = 0; i < length; ++i) {
                data = (Map) datas.get(i);
                if (data.get(pIdField) == null || data.get(pIdField).toString().trim().length() == 0) {
                    rootDatas.add(data);
                    datas.remove(i);
                    --i;
                    --length;
                }
            }

            iterativeData(datas, rootDatas, idField, pIdField);
            return rootDatas;
        } else {
            return datas;
        }
    }

    private static void iterativeData(List<Map<String, Object>> datas, List<Map<String, Object>> parentDatas, String idField, String parentIdFiled) {
        int length = parentDatas.size();
        Map data = null;

        for (int i = 0; i < length; ++i) {
            data = (Map) parentDatas.get(i);
            ArrayList children = new ArrayList();
            data.put("children", children);
            int len = datas.size();
            Map d = null;

            for (int j = 0; j < len; ++j) {
                d = (Map) datas.get(j);
                if (data.get(idField).equals(d.get(parentIdFiled))) {
                    children.add(d);
                    datas.remove(j);
                    --j;
                    --len;
                }
            }

            iterativeData(datas, children, idField, parentIdFiled);
        }

    }
}
