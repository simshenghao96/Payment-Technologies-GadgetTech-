/**
 * 
 * I declare that this code was written by me, simsh. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Sim Sheng Hao
 * Student ID: 22012312
 * Class: E63C
 * Date created: 2024-Feb-03 5:20:12 pm 
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
public class BrandController {
	
	@Autowired
	private BrandRepository brandRepo;
	
	@GetMapping("/brands")
	public String viewBrands(Model model) {
		
		List<Brand> listBrands = brandRepo.findAll();
		
		model.addAttribute("listBrands", listBrands);
		return "view_brands";
	}
	
	@GetMapping("/brands/add")
	public String addBrand(Model model) {
		model.addAttribute("brand", new Brand());
		return "add_brands";
	}
	
	@PostMapping("/brands/save")
	public String saveBrand(@Valid Brand brand, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "add_brands";
		}
		
		brandRepo.save(brand);
		return "redirect:/brands";
	}
	
	@GetMapping("/brands/edit/{id}")
	public String editBrand(@PathVariable("id") Integer id, Model model) {
		Brand brand = brandRepo.getReferenceById(id);
		model.addAttribute("brand", brand);
		
		return "edit_brand";
	}
	
	@PostMapping("/brands/edit/{id}")
	public String saveUpdatedBrand(@PathVariable("id") Integer id, @Valid Brand brand, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "edit_brand";
		}
		
		brandRepo.save(brand);
		return "redirect:/brands";
	}
	
	@GetMapping("/brands/delete/{id}")
	public String deleteBrand(@PathVariable("id") Integer id) {
		brandRepo.deleteById(id);
		
		return "redirect:/brands";
	}
}
