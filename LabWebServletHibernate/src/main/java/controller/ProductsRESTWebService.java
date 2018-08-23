package controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import model.ProductBean;
import model.ProductDAO;

@RestController
public class ProductsRESTWebService {
	@Autowired
	private ProductDAO productDao;
	
	@GetMapping("/products")
	public List<ProductBean> findAll() {
		List<ProductBean> result = productDao.select();
//		成功：200 (OK)、message body包含所有resource資料
		return result;
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<?> findByPrimaryKey(@PathVariable int id) {
		ProductBean result = productDao.select(id);
		if(result!=null) {
			return new ResponseEntity<ProductBean>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/products")
	public ResponseEntity<?> create(@RequestBody ProductBean bean) throws URISyntaxException {
		ProductBean result = productDao.insert(bean);
		if(result!=null) {
			int id = bean.getId();
			return ResponseEntity.created(new URI("/products/"+id)).body(result);
		} else {
			return new ResponseEntity(HttpStatus.NO_CONTENT);			
		}
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<?> update(@PathVariable int id, @RequestBody ProductBean bean) {
		ProductBean result = null;
		if(bean!=null) {
			result = productDao.update(bean.getName(),
					bean.getPrice(), bean.getMake(), bean.getExpire(), id);			
		}
		if(result!=null) {
			return new ResponseEntity<ProductBean>(result, HttpStatus.OK) ;
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		boolean result = productDao.delete(id);
		if(result) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
