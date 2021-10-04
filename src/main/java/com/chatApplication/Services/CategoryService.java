package com.chatApplication.Services;

import com.chatApplication.Model.Category;
import com.chatApplication.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

	final private CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}


	public void listAllCategories() {

		categoryRepository.findAll();
	}

	public Category addCategory(Category category){
		return categoryRepository.save(category);
	}

}
