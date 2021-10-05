package com.chatapplication.Services;

import com.chatapplication.Model.Category;
import com.chatapplication.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

	final private CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}


	public List<Category> listAllCategories() {

		return categoryRepository.findAll();
	}

	public Category addCategory(Category category){
		return categoryRepository.save(category);
	}

}
