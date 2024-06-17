/**
 * 
 * I declare that this code was written by me, simsh. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Sim Sheng Hao
 * Student ID: 22012312
 * Class: E63C
 * Date created: 2023-Nov-07 9:30:11 pm 
 * 
 */

package e63c.shenghao.ga;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * @author simsh
 *
 */
@Entity
public class Gadget {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@NotNull(message="Gadget name cannot be empty!")
	@NotEmpty(message="Gadget name cannot be empty!")
	@Size(min=5, max=50, message="Gadget name length must be between 5 and 50 characters!")
	private String gadgetName;
	
	@NotEmpty(message="Description cannot be empty")
    @Size(min=5, max=100, message="Description must be between 5 and 100 characters")
	private String description;
	
	@Positive(message="Price must be a positive number")
	private double price;
	
	private String imgName;
	
	@Positive(message="Quantity must be a positive whole number")
	private int quantity;
	
	@NotEmpty(message="Colour cannot be empty")
	private String colour;
	
	@NotNull(message="Category is required")
	@ManyToOne
	@JoinColumn(name="category_id", nullable=false)
	private Category category;
	
	@NotNull(message="Brand is required")
	@ManyToOne
	@JoinColumn(name="brand_id", nullable=false)
	private Brand brand;
	
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public String getGadgetName() {
		return gadgetName;
	}
	public void setGadgetName(String gadgetName) {
		this.gadgetName = gadgetName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	
	

}
