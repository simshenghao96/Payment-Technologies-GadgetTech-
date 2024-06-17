/**
 * 
 * I declare that this code was written by me, simsh. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Sim Sheng Hao
 * Student ID: 22012312
 * Class: E63C
 * Date created: 2023-Dec-04 3:48:25 pm 
 * 
 */

package e63c.shenghao.ga;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;


/**
 * @author simsh
 *
 */
@Controller
public class CategoryController {
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@GetMapping("/categories")
	public String viewCategories(Model model) {
		
		List<Category> listCategories = categoryRepo.findAll();
		
		model.addAttribute("listCategories", listCategories);
		return "view_categories";
	}
	
	@GetMapping("/categories/add")
	public String addCategory(Model model) {
		model.addAttribute("category" , new Category());
		return "add_category";
	}
	
	@PostMapping("/categories/save")
	public String saveCategory(@Valid Category category, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "add_category";
		}
		
		categoryRepo.save(category);
		return "redirect:/categories";
	}
	
	@GetMapping("/categories/edit/{id}")
	public String editCategory(@PathVariable("id") Integer id, Model model) {
		Category category = categoryRepo.getReferenceById(id);
		model.addAttribute("category", category);
		
		return "edit_category";
	}
	
	@PostMapping("/categories/edit/{id}")
	public String saveUpdatedCategory(@PathVariable("id") Integer id, @Valid Category category, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "edit_category";
		}
		
		categoryRepo.save(category);
		return "redirect:/categories";
	}
	
	@GetMapping("/categories/delete/{id}")
	public String deleteCategory(@PathVariable("id") Integer id) {
		
		categoryRepo.deleteById(id);
		
		return "redirect:/categories";
	}
}
