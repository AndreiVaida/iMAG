package controller;

import dto.PageDto;
import dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.ProductService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/product")
public class ProductController extends AbstractController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAll() {
        return new ResponseEntity<>(productService.getAll(), OK);
    }

    @GetMapping
    public ResponseEntity<PageDto<ProductDto>> getPaginated(@RequestParam(defaultValue = "1") final Integer pageNumber,
                                                            @RequestParam(defaultValue = "10") final Integer itemsPerPage) {
        return new ResponseEntity<>(productService.getPaginated(pageNumber, itemsPerPage), OK);
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody final ProductDto productDto) {
        productService.addProduct(productDto);
        return new ResponseEntity<>(null, CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable final Integer id) {
        return new ResponseEntity<>(productService.get(id), OK);
    }

}
