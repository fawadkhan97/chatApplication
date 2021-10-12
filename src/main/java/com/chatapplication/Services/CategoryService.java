package com.chatapplication.Services;

import com.chatapplication.Model.entity.Category;
import com.chatapplication.Repository.CategoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

	final private CategoryRepository categoryRepository;
	private static final Logger log = LogManager.getLogger(CategoryService.class);

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	/**
	 *
	 * @return
	 */
	public ResponseEntity<Object> listAllCategory() {
		try {
			List<Category> categorys = categoryRepository.findAllByStatus(true);
			log.info("categorys in db are ", categorys);
			// check if database is empty
			if (categorys.isEmpty()) {
				return new ResponseEntity<>("Message:  Categorys are empty", HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(categorys, HttpStatus.OK);
			}

		} catch (Exception e) {
			log.error(
					"some error has occurred trying to Fetch categorys, in Class  CategoryService and its function listAllCategory ",
					e.getMessage());
			return new ResponseEntity<>("Category could not be found", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public ResponseEntity<Object> getCategoryById(Long id) {
		try {
			Optional<Category> category = categoryRepository.findById(id);
			if (category.isPresent())
				return new ResponseEntity<>(category, HttpStatus.FOUND);
			else
				return new ResponseEntity<>("could not found category , Check id", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(
					"some error has occurred during fetching Category by id , in class CategoryService and its function getCategoryById ",
					e);

			return new ResponseEntity<>("Unable to find Category, an error has occurred",
					HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	/**
	 *
	 * @param categories
	 * @return
	 */
	public ResponseEntity<Object> saveCategory(List<Category> categories) {
		try {
			for (Category category : categories) {
				String pattern = "dd-MM-yyyy hh:mm:ss";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String date = simpleDateFormat.format(new Date());
				category.setCreatedDate(date);
				categoryRepository.save(category);
				categories.toString();
			}
			return new ResponseEntity<>(categories, HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("Data maybe duplicate ,Categories name should be unique  ",
					HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error(
					"some error has occurred while trying to save categories,, in class CategoryService and its function saveCategory ",
					e);
			return new ResponseEntity<>("Categories could not be added , Data maybe incorrect",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 *
	 * @param categories
	 * @return
	 */
	public ResponseEntity<Object> updateCategory(List<Category> categories) {
		try {
			for (Category category : categories) {
				String pattern = "dd-MM-yyyy hh:mm:ss";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String date = simpleDateFormat.format(new Date());
				category.setCreatedDate(date);
				categoryRepository.save(category);
				category.toString();
			}
			return new ResponseEntity<>(categories, HttpStatus.OK);
		} catch (Exception e) {
			log.error(
					"some error has occurred while trying to update category,, in class CategoryService and its function updateCategory ",
					e.getMessage());
			return new ResponseEntity<>("Categories could not be Updated , Data maybe incorrect",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 *
	 * @param id
	 * @return
	 */
	public ResponseEntity<Object> deleteCategory(Long id) {
		try {
			Optional<Category> category = categoryRepository.findById(id);
			if (category.isPresent()) {

				// set status false
				category.get().setStatus(false);
				// set updated date
				String pattern = "dd-MM-yyyy hh:mm:ss";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String date = simpleDateFormat.format(new Date());
				category.get().setUpdatedDate(date);
				categoryRepository.save(category.get());
				return new ResponseEntity<>("Message: Category deleted successfully", HttpStatus.OK);
			} else
				return new ResponseEntity<>("Message: Category does not exists ", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error(
					"some error has occurred while trying to Delete category,, in class CategoryService and its function deleteCategory ",
					e.getMessage(), e.getCause(), e);
			return new ResponseEntity<>("Category could not be Deleted.......", HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
}
