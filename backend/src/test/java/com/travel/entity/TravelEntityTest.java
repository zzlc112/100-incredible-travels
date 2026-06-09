package com.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class TravelEntityTest {

    @Test
    void travelShouldHaveTableNameAnnotation() {
        TableName annotation = Travel.class.getAnnotation(TableName.class);
        assertNotNull(annotation);
        assertEquals("travel", annotation.value());
    }

    @Test
    void travelIdShouldHaveTableIdAnnotation() throws NoSuchFieldException {
        Field idField = Travel.class.getDeclaredField("id");
        TableId annotation = idField.getAnnotation(TableId.class);
        assertNotNull(annotation);
        assertEquals(IdType.AUTO, annotation.type());
    }

    @Test
    void travelShouldHaveAllFields() {
        Field[] fields = Travel.class.getDeclaredFields();
        assertTrue(fields.length >= 13, "Travel should have at least 13 fields");
    }

    @Test
    void adminUserShouldHaveTableNameAnnotation() {
        TableName annotation = AdminUser.class.getAnnotation(TableName.class);
        assertNotNull(annotation);
        assertEquals("admin_user", annotation.value());
    }

    @Test
    void adminUserIdShouldHaveTableIdAnnotation() throws NoSuchFieldException {
        Field idField = AdminUser.class.getDeclaredField("id");
        TableId annotation = idField.getAnnotation(TableId.class);
        assertNotNull(annotation);
        assertEquals(IdType.AUTO, annotation.type());
    }

    @Test
    void adminUserShouldHaveThreeFields() {
        Field[] fields = AdminUser.class.getDeclaredFields();
        assertEquals(3, fields.length, "AdminUser should have 3 fields: id, username, password");
    }
}
