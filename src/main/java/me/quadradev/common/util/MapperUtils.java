package me.quadradev.common.util;

import org.springframework.beans.BeanUtils;

public final class MapperUtils {

    private MapperUtils() {
    }

    public static <S, T> T map(S source, T target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }
}
