package com.travel.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.travel.TravelApplication;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TravelApplication.class, properties = {
    "spring.sql.init.mode=never"
})
class TravelConfigTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private MybatisPlusInterceptor mybatisPlusInterceptor;

    @Test
    void contextLoads() {
        assertNotNull(context);
        assertTrue(context.containsBean("travelApplication"));
    }

    @Test
    void mybatisPlusInterceptorContainsPaginationInterceptor() {
        assertNotNull(mybatisPlusInterceptor);
        boolean hasPageInterceptor = mybatisPlusInterceptor.getInterceptors().stream()
                .anyMatch(i -> i instanceof PaginationInnerInterceptor);
        assertTrue(hasPageInterceptor);
    }

    @Test
    void metaObjectHandlerInsertFill() {
        MetaObjectHandler handler = context.getBean(MetaObjectHandler.class);
        Map<String, Object> map = new HashMap<>();
        TestEntity entity = new TestEntity();
        MetaObject metaObject = SystemMetaObject.forObject(entity);

        handler.insertFill(metaObject);

        assertNotNull(entity.getCreatedAt());
        assertNotNull(entity.getUpdatedAt());
    }

    @Test
    void metaObjectHandlerUpdateFill() {
        MetaObjectHandler handler = context.getBean(MetaObjectHandler.class);
        TestEntity entity = new TestEntity();
        MetaObject metaObject = SystemMetaObject.forObject(entity);

        handler.updateFill(metaObject);

        assertNotNull(entity.getUpdatedAt());
    }

    static class TestEntity {
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        public LocalDateTime getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    }
}
