package co.edu.cun.virtualbook.service;

import co.edu.cun.virtualbook.domain.Category;
import co.edu.cun.virtualbook.repository.CategoryRepository;

import java.io.IOException;
import java.util.List;

public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService() throws IOException {
        this.categoryRepository = new CategoryRepository();
    }

    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }
}
