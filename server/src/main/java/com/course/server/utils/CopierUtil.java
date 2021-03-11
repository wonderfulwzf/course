package com.course.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * s
 * 对象拷贝工具
 * 使用cglib 的BeanCopier 性能相比其他的更好
 *
 * @author： Gam Saan
 * @date： 2020/7/1 10:00
 */
public class CopierUtil {

    private CopierUtil() {
    }

    private static final Logger logger = LoggerFactory.getLogger("businessLog");

    private static final String MESSAGE = "对象拷贝失败, source:{}, target:{}";

    /**
     * 单个对象属性拷贝， 属性名一样，类型不一样会报错
     *
     * @param source           源对象
     * @param targetClass      目标对象Class
     * @param <T>              目标对象类型
     * @param ignoreProperties 忽略的属性
     * @return 目标对象
     */
    public static <T, M> T copyProperties(M source, Class<T> targetClass, String... ignoreProperties) {
        if (source == null || targetClass == null) {
            return null;
        }
        T target = null;
        try {
            //Class的newInstance() 1.9时被废弃了
            target = targetClass.newInstance();
            BeanUtils.copyProperties(source, target, ignoreProperties);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error(MESSAGE, source, targetClass);
        }
        return target;
    }

    /**
     * 单个对象属性拷贝， 属性名一样，类型不一样会报错
     *
     * @param source           源对象
     * @param target      目标对象
     * @param <T>              目标对象类型
     * @param ignoreProperties 忽略的属性
     * @return 目标对象
     */
    public static <T, M> T copyProperties(M source, T target, String... ignoreProperties) {
        if (source == null || target == null) {
            return null;
        }
        try {
            BeanUtils.copyProperties(source, target, ignoreProperties);
        } catch (Exception e) {
            logger.error(MESSAGE, source, target);
        }
        return target;
    }


    /**
     * 单个对象属性拷贝   属性名一样，类型不一样不会报错，直接忽略
     *
     * @param source      源对象
     * @param targetClass 目标对象Class
     * @param <T>         目标对象类型
     * @return 目标对象
     */
    public static <T, M> T copyBean(M source, Class<T> targetClass) {
        if (source == null || targetClass == null) {
            return null;
        }
        return copy(source, targetClass, null);
    }

    /**
     * 列表对象拷贝
     *
     * @param sources 源列表
     * @param clazz   源列表对象Class
     * @param <T>     目标列表对象类型
     * @param <M>     源列表对象类型
     * @return 目标列表
     */
    public static <T, M> List<T> copyList(List<M> sources, Class<T> clazz) {
        if (CollectionUtils.isEmpty(sources) || clazz == null) {
            return Collections.emptyList();
        }
        BeanCopier copier = BeanCopier.create(sources.get(0).getClass(), clazz, false);
        return sources.stream().map(m -> copy(m, clazz, copier)).collect(Collectors.toList());
    }

    /**
     * 对象的拷贝
     *
     * @param source      源
     * @param targetClass 目标
     * @param copier      拷贝器
     * @param <T>         目标对象CLASS
     * @return 拷贝后的实例
     */
    private static <T, M> T copy(M source, Class<T> targetClass, BeanCopier copier) {
        if (null == copier) {
            copier = BeanCopier.create(source.getClass(), targetClass, false);
        }
        T target = null;
        try {
            //Class的newInstance() 1.9时被废弃了
            target = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error(MESSAGE, source, targetClass);
        }
        copier.copy(source, target, null);
        return target;
    }


}
