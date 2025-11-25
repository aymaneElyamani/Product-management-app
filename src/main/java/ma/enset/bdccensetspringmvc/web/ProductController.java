package ma.enset.bdccensetspringmvc.web; // Je place ici mes contrôleurs web

import java.util.List; // Pour gérer la session (logout)

import org.springframework.beans.factory.annotation.Autowired; // Pour valider les données du formulaire
import org.springframework.security.access.prepost.PreAuthorize; // Je sécurise mes méthodes avec les rôles
import org.springframework.stereotype.Controller; // Pour envoyer des données à la vue
import org.springframework.ui.Model; // Mon entité Product
import org.springframework.validation.BindingResult; // J'accède à la base via ce repository
import org.springframework.web.bind.annotation.GetMapping; // Injection automatique du repository
import org.springframework.web.bind.annotation.PostMapping; // J'indique que cette classe est un contrôleur Spring MVC
import org.springframework.web.bind.annotation.RequestParam; // Permet de récupérer les erreurs de validation

import jakarta.validation.Valid; // Pour gérer les requêtes GET
import ma.enset.bdccensetspringmvc.entities.Product; // Pour gérer les requêtes POST
import ma.enset.bdccensetspringmvc.repository.ProductRepository; // Pour récupérer un paramètre dans la requête

@Controller // Je déclare cette classe comme contrôleur Spring
public class ProductController {

    @Autowired // J'injecte automatiquement mon ProductRepository
    private ProductRepository productRepository;

    @GetMapping("/user/index") // Cette méthode affiche la liste des produits
    @PreAuthorize("hasRole('USER')") // Seul un USER ou ADMIN peut y accéder
    public String index(Model model) {
        List<Product> products = productRepository.findAll(); // Je récupère tous les produits
        model.addAttribute("productListe", products); // J'envoie la liste à la vue
        return "products"; // Je retourne la page products.html
    }

    @GetMapping("/") // Quand quelqu'un va sur "/", je le redirige vers /user/index
    public String home() {
        return "redirect:/user/index"; // Je redirige vers la page principale
    }

    @PostMapping("/admin/delete") // Pour supprimer un produit
    @PreAuthorize("hasRole('ADMIN')") // Je limite l’accès aux admins
    public String delete(@RequestParam(name="id") Long id) {
        productRepository.deleteById(id); // Je supprime le produit avec son id
        return "redirect:/user/index"; // Je reviens vers la liste
    }

    @GetMapping("/admin/newProduct") // Formulaire pour créer un produit
    @PreAuthorize("hasRole('ADMIN')") // Accessible uniquement par ADMIN
    public String newProduct(Model model) {
        model.addAttribute("product", new Product()); // Je prépare un objet vide pour le formulaire
        return "newProduct"; // Je retourne la page du formulaire
    }

    @PostMapping("/admin/saveProduct") // Pour enregistrer un produit
    @PreAuthorize("hasRole('ADMIN')") // Réservé aux admins
    public String saveProduct(@Valid Product product, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) return "newProduct"; // Je reste sur le formulaire si erreurs
        productRepository.save(product); // J'enregistre le produit
        return "redirect:/admin/newProduct"; // Je recharge le formulaire
    }

    @GetMapping("/notAuthorized") // Page affichée si quelqu’un n’a pas la permission
    public String notAuthorized() {
        return "notAuthorized";
    }

    @GetMapping("/login") // La page login
    public String login() {
        return "login";
    }
    
    // Spring Security gère automatiquement le logout via /logout en POST
    // Pas besoin de méthode GET pour /logout

}