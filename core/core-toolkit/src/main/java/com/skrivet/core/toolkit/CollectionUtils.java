package com.skrivet.core.toolkit;


import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CollectionUtils {
    public static <T> String asString(Collection<T> collection, Convert<T> convert) {
        if (org.springframework.util.CollectionUtils.isEmpty(collection)) {
            return null;
        }
        String str = null;
        Iterator<T> iterable = collection.iterator();
        while (iterable.hasNext()) {
            T t = iterable.next();
            if (str == null) {
                str = convert.convert(t);
            } else {
                str = str + "," + convert.convert(t);
            }

        }
        return str;
    }

    public static <T> T findOne(List<T> datas, MatchCallBack<T> callBack) {
        if (!org.springframework.util.CollectionUtils.isEmpty(datas)) {
            for (T data : datas) {
                if (callBack.matches(data)) {
                    return data;
                }
            }
        }
        return null;
    }

    public static <T> void sort(List<T> datas, boolean asc, SortCallBack<T> callBack) {
        if (!org.springframework.util.CollectionUtils.isEmpty(datas)) {
            Collections.sort(datas, (o1, o2) -> {

                int p = 0;
                long[] sortOrderNums1 = callBack.sortOrderNums(o1);
                long[] sortOrderNums2 = callBack.sortOrderNums(o2);
                for (int i = 0; i < sortOrderNums1.length; i++) {
                    long sortOrderNum1 = sortOrderNums1[i];
                    long sortOrderNum2 = sortOrderNums2[i];
                    if (asc) {
                        if (sortOrderNum1 > sortOrderNum2) {
                            p = 1;
                            break;
                        } else if (sortOrderNum1 < sortOrderNum2) {
                            p = -1;
                            break;
                        }
                    } else {
                        if (sortOrderNum1 > sortOrderNum2) {
                            p = -1;
                            break;
                        } else if (sortOrderNum1 < sortOrderNum2) {
                            p = 1;
                            break;
                        }
                    }

                }
                return p;
            });
        }
    }

    public static interface Convert<T> {
        public String convert(T t);
    }


    public static interface MatchCallBack<T> {
        boolean matches(T data);
    }

    public static interface SortCallBack<T> {
        long[] sortOrderNums(T data);
    }
}
