package com.zhangry.data.hibernate;

import org.hibernate.transform.ResultTransformer;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by zhangry on 2017/2/20.
 */
class ResultToMap implements ResultTransformer {
    private static final long serialVersionUID = -6126968741247252813L;

    ResultToMap() {
    }

    public Object transformTuple(Object[] tuple, String[] aliases) {
        LinkedHashMap result = new LinkedHashMap(aliases.length);

        for(int i = 0; i < aliases.length; ++i) {
            result.put(aliases[i], tuple[i]);
        }

        return result;
    }

    public List transformList(List collection) {
        return collection;
    }
}

