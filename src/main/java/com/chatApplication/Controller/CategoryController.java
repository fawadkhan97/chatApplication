package com.chatApplication.Controller;

import com.chatApplication.Model.Category;
import com.chatApplication.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

	final private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("")
	public void listAllCategories() {
		categoryService.listAllCategories();

	}

	@PostMapping("/add")
	public Category addCategory(@RequestBody Category category) {
		return categoryService.addCategory(category);
	}

}
