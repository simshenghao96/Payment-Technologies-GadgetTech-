/**
 * 
 * I declare that this code was written by me, simsh. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Sim Sheng Hao
 * Student ID: 22012312
 * Class: E63C
 * Date created: 2024-Feb-03 5:17:32 pm 
 * 
 */

package e63c.shenghao.ga;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;

/**
 * @author simsh
 *
 */
@Entity
public class Brand {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty(message="Brand name cannot be empty !")
	private String name;
	
	@OneToMany(mappedBy="brand")
	private Set<Gadget> gadgets;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Gadget> getGadgets() {
		return gadgets;
	}

	public void setGadgets(Set<Gadget> gadgets) {
		this.gadgets = gadgets;
	}

}
