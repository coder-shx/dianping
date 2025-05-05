package org.com.dianping.controller;

import org.com.dianping.entity.SearchHistory;
import org.com.dianping.service.SearchHistoryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller for managing search history operations.
 */
@CrossOrigin(origins = "http://localhost:8081",
        allowCredentials = "true",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
@RestController
@RequestMapping("/api/search")
public class SearchController {
    private final SearchHistoryService service;

    /**
     * Constructs a new {@link SearchController}.
     *
     * @param service the search history service
     */
    public SearchController(SearchHistoryService service) {
        this.service = service;
    }

    /**
     * Saves a search keyword for a user.
     *
     * @param keyword the search keyword
     * @param userId  the ID of the user
     * @return the saved search history
     */
    @PostMapping
    public SearchHistory saveSearch(@RequestParam String keyword,
                                    @RequestHeader("UserId") Long userId) {
        return service.saveSearch(userId, keyword);
    }

    /**
     * Retrieves the search history for a user.
     *
     * @param userId the ID of the user
     * @return a list of search history
     */
    @GetMapping
    public List<SearchHistory> getHistory(@RequestHeader("UserId") Long userId) {
        return service.getHistory(userId);
    }

    /**
     * Deletes a specific search history entry for a user.
     *
     * @param id     the ID of the search history entry
     * @param userId the ID of the user
     */
    @DeleteMapping("/{id}")
    public void deleteHistory(@PathVariable Long id,
                              @RequestHeader("UserId") Long userId) {
        System.out.println("Deleting history - userId: " + userId + ", id: " + id);
        service.deleteHistory(userId, id);
    }

    /**
     * Clears all search history for a user.
     *
     * @param userId the ID of the user
     */
    @DeleteMapping
    public void clearAll(@RequestHeader("UserId") Long userId) {
        System.out.println("Clearing all history - userId: " + userId);
        service.clearAll(userId);
    }
}