package com.travel.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.entity.Travel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
@Sql(scripts = "/db/test-schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class TravelMapperTest {

    @Autowired
    private TravelMapper travelMapper;

    private Travel createTestTravel(String title) {
        Travel t = new Travel();
        t.setTitle(title);
        t.setSubtitle("test subtitle");
        t.setCoverImage("http://example.com/img.jpg");
        t.setExperienceTypes("[\"culture\"]");
        t.setVisualStyle("minimal");
        t.setRarityLevel(2);
        t.setDestination("Japan");
        t.setContent("test content");
        t.setHighlights("[\"highlight1\"]");
        t.setCreatedAt(LocalDateTime.now());
        t.setUpdatedAt(LocalDateTime.now());
        return t;
    }

    @Test
    void insertAndSelectById() {
        Travel travel = createTestTravel("Test Insert");
        int rows = travelMapper.insert(travel);
        assertEquals(1, rows);
        assertNotNull(travel.getId());

        Travel found = travelMapper.selectById(travel.getId());
        assertNotNull(found);
        assertEquals("Test Insert", found.getTitle());
    }

    @Test
    void updateById() {
        Travel travel = createTestTravel("Before Update");
        travelMapper.insert(travel);

        travel.setTitle("After Update");
        travelMapper.updateById(travel);

        Travel updated = travelMapper.selectById(travel.getId());
        assertEquals("After Update", updated.getTitle());
    }

    @Test
    void deleteById() {
        Travel travel = createTestTravel("To Delete");
        travelMapper.insert(travel);

        int rows = travelMapper.deleteById(travel.getId());
        assertEquals(1, rows);

        Travel found = travelMapper.selectById(travel.getId());
        assertNull(found);
    }

    @Test
    void selectPageWithFilterByVisualStyle() {
        travelMapper.insert(createTestTravel("Minimal 1"));
        travelMapper.insert(createTestTravel("Minimal 2"));

        Page<Travel> page = new Page<>(1, 10);
        IPage<Travel> result = travelMapper.selectPageWithFilter(page, null, "minimal", null);

        assertTrue(result.getRecords().size() >= 2);
        result.getRecords().forEach(t -> assertEquals("minimal", t.getVisualStyle()));
    }

    @Test
    void selectPageWithFilterByRarityLevel() {
        Travel t1 = createTestTravel("Rare 3");
        t1.setRarityLevel(3);
        travelMapper.insert(t1);

        Travel t2 = createTestTravel("Rare 1");
        t2.setRarityLevel(1);
        travelMapper.insert(t2);

        Page<Travel> page = new Page<>(1, 10);
        IPage<Travel> result = travelMapper.selectPageWithFilter(page, null, null, 3);

        assertTrue(result.getRecords().size() >= 1);
        result.getRecords().forEach(t -> assertTrue(t.getRarityLevel() >= 3));
    }

    @Test
    void selectPageWithFilterPagination() {
        for (int i = 0; i < 5; i++) {
            travelMapper.insert(createTestTravel("Page Test " + i));
        }

        Page<Travel> page = new Page<>(1, 3);
        IPage<Travel> result = travelMapper.selectPageWithFilter(page, null, null, null);

        assertEquals(3, result.getRecords().size());
        assertTrue(result.getTotal() >= 5);
    }
}
