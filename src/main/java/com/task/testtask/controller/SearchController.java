package com.task.testtask.controller;

import com.task.testtask.entity.Product;
import com.task.testtask.service.ElasticSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Slf4j
public class SearchController {

    private final ElasticSearchService elasticSearchService;

    @GetMapping
    public String searchProducts(@RequestParam(value = "query", required = false) String query,
                                 @RequestParam(name = "active", required = false) Boolean active,
                                 @RequestParam(name = "startDate", required = false) String startDate,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "10") int size,
                                 Model model) {
        Pageable pageable = PageRequest.of(page, size);
        try {
            Page<Product> productsPage = elasticSearchService.searchProducts(query, pageable, active, startDate);

            if (productsPage.isEmpty()) {
                model.addAttribute("infoMessage", "No products found matching your criteria.");
            } else {
                model.addAttribute("productsPage", productsPage);
            }

            model.addAttribute("query", query);
            model.addAttribute("active", active);
            model.addAttribute("startDate", startDate);
        } catch (Exception e) {
            log.error("! Error during product search: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "An error occurred during search: " + e.getMessage());
        }
        return "search";
    }
}
