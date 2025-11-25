
package ma.enset.bdccensetspringmvc.web;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ma.enset.bdccensetspringmvc.entities.Product;
import ma.enset.bdccensetspringmvc.repository.ProductRepository;

@Controller
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @GetMapping("/index")
    public  String index(Model model){
        List<Product> products = productRepository.findAll();
        model.addAttribute("listProduits", products);
        return "products";
    }
    @GetMapping("/")
    public  String home(){
        return "redirect:/index";
    }
    @GetMapping("/deleteProduct")
    public String delete(@RequestParam(name = "id") Long id){
        productRepository.deleteById(id);
        return "redirect:/index";
    }
   @GetMapping("/NewProduct")
    public String newProduct(Model model){
       model.addAttribute("product", new Product());
       return "newProduct";
    }
}