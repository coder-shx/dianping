package org.com.dianping.service;

import org.com.dianping.entity.SearchHistory;
import org.com.dianping.repository.SearchHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchHistoryServiceTest {

    @Mock
    private SearchHistoryRepository searchHistoryRepository;

    private SearchHistoryService searchHistoryService;

    @BeforeEach
    void setUp() {
        searchHistoryService = new SearchHistoryService(searchHistoryRepository);
    }

    @Test
    void saveSearch_ShouldSaveAndReturnSearchHistory() {
        // Arrange
        Long userId = 1L;
        String keyword = "test keyword";
        SearchHistory savedHistory = new SearchHistory(1L, userId, keyword, LocalDateTime.now());
        when(searchHistoryRepository.save(any(SearchHistory.class))).thenReturn(savedHistory);

        // Act
        SearchHistory result = searchHistoryService.saveSearch(userId, keyword);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(keyword, result.getKeyword());
        verify(searchHistoryRepository).save(any(SearchHistory.class));
    }

    @Test
    void getHistory_ShouldReturnUserSearchHistory() {
        // Arrange
        Long userId = 1L;
        List<SearchHistory> histories = Arrays.asList(
            new SearchHistory(1L, userId, "keyword1", LocalDateTime.now()),
            new SearchHistory(2L, userId, "keyword2", LocalDateTime.now())
        );
        when(searchHistoryRepository.findByUserIdOrderByCreatedTimeDesc(userId)).thenReturn(histories);

        // Act
        List<SearchHistory> results = searchHistoryService.getHistory(userId);

        // Assert
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("keyword1", results.get(0).getKeyword());
        assertEquals("keyword2", results.get(1).getKeyword());
    }

    @Test
    void deleteHistory_ShouldCallRepositoryDelete() {
        // Arrange
        Long userId = 1L;
        Long historyId = 1L;

        // Act
        searchHistoryService.deleteHistory(userId, historyId);

        // Assert
        verify(searchHistoryRepository).deleteByUserIdAndId(userId, historyId);
    }

    @Test
    void clearAll_ShouldDeleteAllUserHistory() {
        // Arrange
        Long userId = 1L;

        // Act
        searchHistoryService.clearAll(userId);

        // Assert
        verify(searchHistoryRepository).deleteAllByUserId(userId);
    }
}
