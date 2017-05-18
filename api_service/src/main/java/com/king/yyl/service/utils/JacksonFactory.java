package com.king.yyl.service.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by apple on 16/10/10.
 */
public class JacksonFactory {

        private static ObjectMapper mapper;

        /**
         * mapper.setSerializationInclusion(Include.NON_NULL);
         * 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
         * Include.Include.ALWAYS 默认
         * Include.NON_DEFAULT 属性为默认值不序列化
         * Include.NON_EMPTY 属性为 空（“”） 或者为 NULL 都不序列化
         * Include.NON_NULL 属性为NULL 不序列化
         * @param createNew 是否创建一个新的Mapper
         * @return
         */
        public static synchronized ObjectMapper getMapperInstance(boolean createNew) {
            if (createNew) {
                return new ObjectMapper();
            } else if (mapper == null) {
                mapper = new ObjectMapper();
            }
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper;
        }
}
