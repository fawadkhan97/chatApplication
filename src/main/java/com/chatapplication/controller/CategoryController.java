package com.chatapplication.controller;

import com.chatapplication.Model.entity.Category;
import com.chatapplication.Services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Fawad khan
 * @createdDate 11-oct-2021
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
	private final CategoryService categoryService;
	private static final String defaultAuthValue = "category12345";

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	/**
	 * @description check user is authorized or not
	 * @createdDate 11-oct-2021
	 * @param authValue
	 * @return
	 */
	public Boolean authorize(String authValue) {
		return defaultAuthValue.equals(authValue);
	}

	/**
	 *
	 * @param authValue
	 * @createdDate 11-oct-2021
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
	 * @author fawad khan
	  * @createdDate 11-oct-2021
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
	 * @author fawad khan
	 * @createdDate 11-oct-2021

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
	 * @author fawad khan
	 * @createdDate 11-oct-2021
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
	 * @author fawad khan
	 * @createdDate 11-oct-2021
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