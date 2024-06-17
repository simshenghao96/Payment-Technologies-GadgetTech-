package e63c.shenghao.ga;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartItemController {

	@Autowired
	private CartItemRepository cartItemRepo;

	@Autowired
	private GadgetRepository gadgetRepo;

	@Autowired
	private MembershipRepository membershipRepo;

	@Autowired
	private OrderItemRepository orderRepo;

	@Autowired
	private JavaMailSender javaMailSender;

	@GetMapping("/cart")
	public String showCart(Model model, Principal principal) {

		/** 
			Complete the code for the showCart method. 
			The comments below are meant as a guide.
			Step 1 has been completed for you. 
		 **/
		// Step 1: Get currently logged in user
		MembershipDetails loggedInMember = (MembershipDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		int loggedInMemberId = loggedInMember.getMember().getId();

		// Step 2: Get shopping cart items added by this user
		// *Hint: You will need to use the method we added in the CartItemRepository
		List<CartItem> cartItemList = cartItemRepo.findByMembershipId(loggedInMemberId);

		// Step 3: Add the shopping cart items to the model
		model.addAttribute("cartItemList", cartItemList);

		// Step 4: Calculate the total cost of all items in the shopping cart
		double cartTotal = 0.0;

		for(int i = 0; i < cartItemList.size(); i++) {
			CartItem currentCartItem = cartItemList.get(i);
			int itemQuantityInCart = currentCartItem.getQuantity();

			Gadget gadget = currentCartItem.getGadget();
			double itemPrice = gadget.getPrice();

			double subTotal = itemPrice * itemQuantityInCart;

			currentCartItem.setSubTotal(subTotal);
			cartTotal += subTotal;
		}


		// Step 5: Add the shopping cart total to the model
		model.addAttribute("cartTotal", cartTotal);
		model.addAttribute("memberId", loggedInMemberId);



		return "cart";
	}

	@PostMapping("/cart/add/{itemId}")
	public String addToCart(@PathVariable("itemId") int itemId, @RequestParam("quantity") int quantity,
			Principal principal) {

		/** 
			Complete the code for the addCart method. 
			The comments below are meant as a guide. 
		 **/

		// Step 1: Get currently logged in user
		MembershipDetails loggedInMember = (MembershipDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		int loggedInMemberId = loggedInMember.getMember().getId();


		// Step 2: Check in the cartItemRepo if item was previously added by user.
		// *Hint: we will need to write a new method in the CartItemRepository
		CartItem cartItem = cartItemRepo.findByMembershipIdAndGadgetId(loggedInMemberId, itemId);


		// Step 3: if the item was previously added, then we get the quantity that was
		// previously added and increase that
		// Save the CartItem object back to the repository
		if(cartItem != null) {
			int currentQuantity = cartItem.getQuantity();
			cartItem.setQuantity(currentQuantity + quantity);
			cartItemRepo.save(cartItem);
		}
		else {

			// Step 4: if the item was NOT previously added,
			// then prepare the item and member objects
			Gadget gadget = gadgetRepo.getReferenceById(itemId);
			Membership member = membershipRepo.getReferenceById(loggedInMemberId);

			// Step 5: Create a new CartItem object
			CartItem newCartItem = new CartItem();

			// Step 6: Set the item and member as well as the new quantity in the new CartItem
			// object
			newCartItem.setGadget(gadget);
			newCartItem.setMembership(member);
			newCartItem.setQuantity(quantity);


			// Step 7: Save the new CartItem object to the repository
			cartItemRepo.save(newCartItem);
		}

		return "redirect:/cart";
	}

	// Method for updating cart in the controller
	@PostMapping("/cart/update/{id}")
	public String updateCart(@PathVariable("id") int cartItemId, @RequestParam("qty") int qty) {

		/** 
			Complete the code for the updateCart method. 
			The comments below are meant as a guide. 
		 **/

		// Step 1: Get cartItem object by cartItem's id
		CartItem cartItem = cartItemRepo.getReferenceById(cartItemId);

		// Step 2: Set the quantity of the carItem object retrieved
		cartItem.setQuantity(qty);

		// Step 3: Save the cartItem back to the cartItemRepo
		cartItemRepo.save(cartItem);

		return "redirect:/cart";
	}

	// Method for removing item from cart in controller
	@GetMapping("/cart/remove/{id}")
	public String removeFromCart(@PathVariable("id") int cartItemId) {
		/** 
			Complete the code for the removeCart method. 
			The comments below are meant as a guide. 
		 **/

		//Step 1: Remove item from cartItemRepo 
		cartItemRepo.deleteById(cartItemId);



		return "redirect:/cart";
	}


	@PostMapping("/cart/process_order")
	public String processOrder(Model model, @RequestParam("cartTotal") double cartTotal,
			@RequestParam("memberId") int memberId, @RequestParam("orderId") String orderId,
			@RequestParam("transactionId") String transactionId) {

		// 1. Retrieve the member object 
		Membership member = membershipRepo.getReferenceById(memberId);

		// 2. Retrieve all cart items, paid for by the member
		List<CartItem> cartItemList = cartItemRepo.findByMembershipId(memberId);

		// Loop to iterate through all cart items
		for (int i = 0; i < cartItemList.size(); i++) {

			// 3. For each cart item paid, 

			// a) Retrieve the item id, item and quantity of the item purchased
			CartItem currentCartItem = cartItemList.get(i);
			Gadget gadgetToUpdate = currentCartItem.getGadget();
			int quantityPurchased = currentCartItem.getQuantity();
			int gadgetToUpdateId = gadgetToUpdate.getId();
			currentCartItem.setSubTotal(quantityPurchased * currentCartItem.getGadget().getPrice());

			// b) Update the item’s quantity in the item repository
			Gadget gadgetInventory = gadgetRepo.getReferenceById(gadgetToUpdateId);
			int currentInventoryQuantity = gadgetInventory.getQuantity();
			System.out.println("Current Inventory Quantity: " + gadgetInventory.getQuantity());
			gadgetInventory.setQuantity(currentInventoryQuantity - quantityPurchased);
			gadgetRepo.save(gadgetInventory);

			// c) Add the member, item, quantity of item purchased, order id and transaction id 
			// into the order repository
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderId(orderId);
			orderItem.setTransactionId(transactionId);
			orderItem.setGadget(gadgetToUpdate);
			orderItem.setMember(member);
			orderItem.setQuantity(quantityPurchased);
			orderRepo.save(orderItem);

			// d) Clear the items purchased by the member from the cart item repository
			cartItemRepo.deleteById(currentCartItem.getId());


		}

		// 4. Add the cart total, cart item list, member object, order id and transaction id to the model 
		// for display on the acknowledgement page
		cartTotal = ((double)Math.round(cartTotal * 100)/100);

		model.addAttribute("cartTotal", cartTotal);
		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("member", member);
		model.addAttribute("orderId", orderId);
		model.addAttribute("transactionId", transactionId);

		// 5. Send email
		String subject = "Your order is confirmed from GadgetTech!";
		String body = "Thank you for your order!\n" + "Order ID: " + orderId + "\n" + "Transaction ID: "
				+ transactionId;
		String to = member.getEmail();

		sendEmail(to, subject, body);


		return "success";
	}

	public void sendEmail(String to, String subject, String body) {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(to);
		msg.setSubject(subject);
		msg.setText(body);
		//msg.setFrom("YOUR EMAIL");

		System.out.println("Sending");
		javaMailSender.send(msg);
		System.out.println("Sent");
	}

	// For customers to view their own purchase history 
	@GetMapping("/purchase_history")
	public String viewHistory(Model model) {
		MembershipDetails loggedInMember = (MembershipDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		int loggedInMemberId = loggedInMember.getMember().getId();

		List<OrderItem> itemPurchase = orderRepo.findByMemberId(loggedInMemberId);
		model.addAttribute("itemPurchased", itemPurchase);

		for (int i=0; i < itemPurchase.size(); i++) {

			OrderItem currentItem = itemPurchase.get(i);
			int itemQuantityInCart = currentItem.getQuantity();
			Gadget gadget = currentItem.getGadget();
			double itemPrice = gadget.getPrice();
			currentItem.setSubTotal(itemQuantityInCart * itemPrice);
		}
		return "purchase_history";
	}

	// For viewing each member's transaction history
	@GetMapping("/members/purchase_history/{memberId}")
	public String viewMemberTransactions(@PathVariable("memberId") int memberId, Model model) {
	    // Fetch transactions for the specified member
	    List<OrderItem> transactions = orderRepo.findByMemberId(memberId);

	    // Optional: Enhance OrderItem with subtotal calculation if not already present
	    transactions.forEach(transaction -> {
	        double subtotal = transaction.getQuantity() * transaction.getGadget().getPrice();
	        // This assumes that you have a method setSubTotal or similar in your OrderItem class
	        transaction.setSubTotal(subtotal);
	    });

	    // Add data to the model for Thymeleaf
	    model.addAttribute("transactions", transactions);

	    // The view name here corresponds to the Thymeleaf template name.
	    // Change "transactions" to "member_transactions" or whatever your file is named.
	    return "member_transactions";
	}


}
