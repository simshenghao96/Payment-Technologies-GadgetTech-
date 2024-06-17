/**
 * 
 * I declare that this code was written by me, simsh. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Sim Sheng Hao
 * Student ID: 22012312
 * Class: E63C
 * Date created: 2023-Nov-07 9:35:11 pm 
 * 
 */

package e63c.shenghao.ga;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;

/**
 * @author simsh
 *
 */
@Controller
public class GadgetController {

	@Autowired
	private GadgetRepository gadgetsRepository;

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private BrandRepository brandRepo;

	// For viewing all the gadgets added into the model
	@GetMapping("/gadgets")
	public String viewGadgets(Model model) {

		List<Gadget> listGadgets = gadgetsRepository.findAll();

		model.addAttribute("listGadgets" ,listGadgets);
		return "view_gadgets";
	}

	// For adding gadgets page which returns add_gadgets as the view
	@GetMapping("/gadgets/add")
	public String addGadgets(Model model) {
		List<Brand> listBrands = brandRepo.findAll();
		List<Category> categoryList = categoryRepo.findAll();

		model.addAttribute("gadget", new Gadget());
		model.addAttribute("listBrands", listBrands);
		model.addAttribute("categoryList", categoryList);
		return "add_gadgets";
	}

	// For Saving Gadgets upon form submission 
	@PostMapping("/gadgets/save")
	public String saveGadgets(@Valid Gadget gadgets, BindingResult bindingResult, @RequestParam("itemImage") MultipartFile imgFile, Model model) {

		if(bindingResult.hasErrors()) {
			List<Category> catList = categoryRepo.findAll();
			List<Brand> listBrands = brandRepo.findAll();
			
			model.addAttribute("catList", catList);
			model.addAttribute("listBrands", listBrands);
			
			return "add_gadgets";
		}

		String imageName = imgFile.getOriginalFilename();

		// Set the image name in item object
		gadgets.setImgName(imageName);

		// Save the item object to the database
		Gadget savedItem = gadgetsRepository.save(gadgets);

		try {
			// Preparing the directory path
			String uploadDir = "uploads/gadgets/" + savedItem.getId();
			Path uploadPath = Paths.get(uploadDir);
			System.out.println("Directory path: " + uploadPath);

			// Checking if the upload path exists, if not it will be created.
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			// Prepare path for file
			Path fileToCreatePath = uploadPath.resolve(imageName);
			System.out.println("File path: " + fileToCreatePath);

			Files.copy(imgFile.getInputStream(), fileToCreatePath, StandardCopyOption.REPLACE_EXISTING);

		} catch (IOException io) {
			io.printStackTrace();
		}

		return "redirect:/gadgets";
	}


	// For viewing gadget on its own by each item
	@GetMapping("/gadgets/{id}")
	public String viewSingleGadget(@PathVariable("id") Integer id, Model model) {
		Gadget gadget = gadgetsRepository.getReferenceById(id);
		model.addAttribute("gadget", gadget);

		return "view_single_gadget";
	}

	// GA (For Edit)
	@GetMapping("gadgets/edit/{id}")
	public String editItem(@PathVariable("id") Integer id, Model model) {
		Gadget gadget = gadgetsRepository.getReferenceById(id);
		List<Brand> listBrands = brandRepo.findAll();
		List<Category> catList = categoryRepo.findAll();

		model.addAttribute("gadget", gadget);
		model.addAttribute("listBrands", listBrands);
		model.addAttribute("catList", catList);

		return "edit_gadget";
	}

	// GA (For Edit) --> PostMapping to process the image for this case as items edit has images involved compared to category **
	@PostMapping("/gadgets/edit/{id}")
	public String saveUpdatedItem(@PathVariable("id") Integer id, @Valid Gadget gadget, BindingResult bindingResult, @RequestParam("itemImage") MultipartFile imgFile, Model model) {

		if (bindingResult.hasErrors()) {
			List<Category> catList = categoryRepo.findAll();
			List<Brand> listBrands = brandRepo.findAll();
			
			model.addAttribute("catList", catList);
			model.addAttribute("listBrands", listBrands);
			return "edit_gadget";
		}

		if (!imgFile.isEmpty()) {
			String imageName = imgFile.getOriginalFilename();
			System.out.println("Image name from imgFile: "+imageName);

			gadget.setImgName(imageName);

			Gadget savedGadget = gadgetsRepository.save(gadget);

			try {
				String uploadDir = "uploads/gadgets/" + savedGadget.getId();
				Path uploadPath = Paths.get(uploadDir);
				System.out.println("Directory path: " + uploadPath);

				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				Path fileToCreatePath = uploadPath.resolve(imageName);
				System.out.println("File path: " + fileToCreatePath);

				Files.copy(imgFile.getInputStream(), fileToCreatePath, StandardCopyOption.REPLACE_EXISTING);

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Image name from item object: "+ gadget.getImgName());
			gadgetsRepository.save(gadget);
		}

		return "redirect:/gadgets";
	}




	// GA (For Delete) 
	@GetMapping("/gadgets/delete/{id}")
	public String deleteCategory(@PathVariable("id") Integer id) {

		gadgetsRepository.deleteById(id);

		return "redirect:/gadgets";
	}


}
