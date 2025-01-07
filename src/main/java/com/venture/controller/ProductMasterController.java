package com.venture.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.venture.dtos.ProductMasterDto;
import com.venture.resmodel.ResponseWithObject;
import com.venture.service.ProductMasterService;
import com.venture.utils.AppConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin(origins = "*")
@Tag(name = "Productmaster-API")
@RequestMapping(value = "/productMaster")
public class ProductMasterController {

	private final ResponseWithObject responseWithObject;
	private final ProductMasterService service;

	public ProductMasterController(ResponseWithObject responseWithObject, ProductMasterService service) {
		super();
		this.responseWithObject = responseWithObject;
		this.service = service;
	}

	@PostMapping(value = "/addProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> addPrdouctDetails(@ModelAttribute ProductMasterDto dto) {

		ProductMasterDto productMasterDto = service.addPrductDetails(dto, dto.getFile());

		if (productMasterDto == null) {
			return responseWithObject.generateResponse(AppConstants.ERROR, HttpStatus.BAD_REQUEST,
					"invalid input data");

		}

		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, dto);

	}

	@GetMapping("/getAllProduct")
	public ResponseEntity<Object> getAllProductDetails() {
		List<ProductMasterDto> products = service.getAllProduct();

		if (products.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No products available");
		}

		return ResponseEntity.ok(products);
	}

	@GetMapping("/getProductById")
	public ResponseEntity<Object> getProductById(@RequestParam Long param) {
		ProductMasterDto dto = this.service.getProductById(param);
		if (dto == null) {
			return this.responseWithObject.generateResponse(AppConstants.NO_DATA_FOUND, HttpStatus.BAD_GATEWAY,
					"product id invalid");
		}

		return this.responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, dto);

	}

	@PutMapping(value = "/addProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> updatePrdouctDetails(@ModelAttribute ProductMasterDto dto) {

		ProductMasterDto productMasterDto = service.updateProductMaster(dto, dto.getFile());

		if (productMasterDto == null) {
			return responseWithObject.generateResponse(AppConstants.NO_DATA_FOUND, HttpStatus.BAD_GATEWAY,
					"invalid product id");

		}

		return responseWithObject.generateResponse(AppConstants.ACCEPT, HttpStatus.OK, dto);

	}
}
