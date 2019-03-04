package com.packt.webstore.controller;

import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;
import com.packt.webstore.exception.NoProductsFoundUnderCategoryException;
import com.packt.webstore.exception.ProductNotFoundException;
import com.packt.webstore.service.ProductService;
import com.packt.webstore.validator.ProductValidator;
import com.packt.webstore.validator.UnitsInStockValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/market")
public class ProductController {
    @Autowired
    private ProductValidator productValidator;
    @Autowired
    private UnitsInStockValidator unitsInStockValidator;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;


    @InitBinder
    public void initialiseBinder(WebDataBinder binder) {
        binder.setAllowedFields("productId",
                "name",
                "unitPrice",
                "description",
                "manufacturer",
                "category",
                "unitsInStock",
                "unitsInOrder",
                "condition",
                "productImage",
                "productManual","language");
//        binder.setValidator(unitsInStockValidator);
        binder.setValidator(productValidator);
    }
//
//    @RequestMapping("/")
//    public String welcome(Model model) {
//        model.addAttribute("greeting", "Welcome to Web Store!");
//        model.addAttribute("tagline", "The one and only amazing web store");
//
//        return "welcome";
//    }

    @RequestMapping("/products")
    public String list(Model model) {
        model.addAttribute("products",
                productRepository.getAllProducts());
        return "products";
    }

    @RequestMapping("/update/stock")
    public String updateStock(Model model) {
        productService.updateAllStock();
        return "redirect:/products";
    }

//    @RequestMapping("/products/{category}")
//    public String getProductsByCategory(Model model,@PathVariable("category") String productCategory) {
//        model.addAttribute("products", productService.getProductsByCategory(productCategory));
//        return "products";
//    }

    //    modify   @RequestMapping("/products/{category}")
    @RequestMapping("/products/{category}")
    public String getProductsByCategory(Model model, @PathVariable("category") String category) {
        List<Product> products =
                productService.getProductsByCategory(category);
        if (products == null || products.isEmpty()) {
            throw new NoProductsFoundUnderCategoryException();
        }
        model.addAttribute("products", products);
        return "products";
    }

    @RequestMapping("/products/filter/{params}")
    public String
    getProductsByFilter(@MatrixVariable(pathVar = "params") Map<String, List<String>> filterParams, Model model) {
        model.addAttribute("products",
                productService.getProductsByFilter(filterParams));
        return "products";
    }

    @RequestMapping("/product")
    public String getProductById(@RequestParam("id") String productId, Model model) {
        model.addAttribute("product", productService.getProductById(productId));
        return "product";
    }

    //    @RequestMapping(value = "/products/add", method = RequestMethod.GET)
//    public String getAddNewProductForm(Model model) {
//        Product newProduct = new Product();
//        model.addAttribute("newProduct", newProduct);
//        return "addProduct";
//    }
    @RequestMapping(value = "/products/add", method = RequestMethod.GET)
    public String getAddNewProductForm(@ModelAttribute("newProduct") Product newProduct) {

        return "addProduct";
    }

    //    @RequestMapping(value = "/products/add", method =RequestMethod.POST)
//    public String processAddNewProductForm(@ModelAttribute("newProduct") Product newProduct) {
//        productService.addProduct(newProduct);
//        return "redirect:/market/products";
//    }
    @RequestMapping(value = "/products/add", method = RequestMethod.POST)
    public String processAddNewProductForm(@ModelAttribute("newProduct")@Valid Product newProduct, BindingResult result, HttpServletRequest request) {
        if(result.hasErrors()) {
            return "addProduct";
        }
        String[] suppressedFields = result.getSuppressedFields();
        if (suppressedFields.length > 0) {
            throw new RuntimeException("Attempting to bind disallowed fields: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
        }

        MultipartFile productImage = newProduct.getProductImage();
        MultipartFile productManual = newProduct.getProductManual();
        String rootDirectory = request.getSession().getServletContext().getRealPath("/");
        if (productImage != null && !productImage.isEmpty()) {
            try {
                productImage.transferTo(new File(rootDirectory + "resources\\images\\" + newProduct.getProductId() + ".jpg"));
            } catch (Exception e) {
                throw new RuntimeException("Product Image saving failed", e);
            }
        }


        if (productManual != null && !productManual.isEmpty()) {
            System.out.println("innnnnnnnn");
            try {
                productManual.transferTo(new File(rootDirectory + "resources\\pdf\\" + newProduct.getProductId() + ".pdf"));
            } catch (Exception e) {
                throw new RuntimeException("Product Manual saving failed", e);
            }
        }

        productService.addProduct(newProduct);
        return "redirect:/market/products";
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ModelAndView handleError(HttpServletRequest req, ProductNotFoundException exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("invalidProductId", exception.getProductId());
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL()+"?"+req.getQueryString());
        mav.setViewName("productNotFound");
        return mav;
    }

    @RequestMapping("/products/invalidPromoCode")
    public String invalidPromoCode() {
        return "invalidPromoCode";
    }
}