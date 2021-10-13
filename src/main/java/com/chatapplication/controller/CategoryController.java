package com.chatapplication.controller;

import com.chatapplication.Model.entity.Category;
import com.chatapplication.Services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
	private final CategoryService categoryService;
	private static final String defaultAuthValue = "category12345";

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	/**
	 * check category is authorized or not
	 *
	 * @param authValue
	 * @return
	 */
	public Boolean authorize(String authValue) {
		return defaultAuthValue.equals(authValue);
	}

	/**
	 *
	 * @param authValue
	 * @return
	 */
	@GetMapping("/all")
	public ResponseEntity<Object> listAllCategories(@RequestHeader("Authorization") String authValue) {
		if (authValue != null) {
			if (authorize(authValue)) {
				return categoryService.listAllCategory();
			} else {
				return new ResponseEntity<>("SMS: Not authorize", HttpStatus.UNAUTHORIZED);
			}
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 *
	 * @param authValue
	 * @param categories
	 * @return
	 */
	@PostMapping("/add")
	public ResponseEntity<Object> addCategory(@RequestHeader("Authorization") String authValue,
			@RequestBody List<Category> categories) {
		if (authValue != null) {
			if (authorize(authValue)) {
				return categoryService.saveCategory(categories);
			} else {
				return new ResponseEntity<>("SMS: Not authorize", HttpStatus.UNAUTHORIZED);
			}
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 *
	 * @param authValue
	 * @param id
	 * @return
	 */
	@GetMapping("/get/{id}")
	public ResponseEntity<Object> getCategory(@RequestHeader("Authorization") String authValue, @PathVariable Long id) {
		if (authValue != null) {
			if (authorize(authValue)) {
				return categoryService.getCategoryById(id);
			} else {
				return new ResponseEntity<>("SMS: Not authorize", HttpStatus.UNAUTHORIZED);
			}
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 *
	 * @param authValue
	 * @param categories
	 * @return
	 */
	@PutMapping("/update")
	public ResponseEntity<Object> updateCategory(@RequestHeader("Authorization") String authValue,
			@RequestBody List<Category> categories) {
		if (authValue != null) {
			if (authorize(authValue)) {
				return categoryService.updateCategory(categories);
			} else
				return new ResponseEntity<>("SMS:  not authorize ", HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 *
	 * @param authValue
	 * @param id
	 * @return
	 */
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object> deleteCategory(@RequestHeader("Authorization") String authValue,
			@PathVariable Long id) {
		if (authValue != null) {
			if (authorize(authValue)) {
				return categoryService.deleteCategory(id);
			} else
				return new ResponseEntity<>("SMS:  not authorize ", HttpStatus.UNAUTHORIZED);
		} else {
			return new ResponseEntity<>("Incorrect authorization key ", HttpStatus.UNAUTHORIZED);
		}
	}

}