package com.bank.admin.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bank.admin.controller.vo.ApplicationVO;
import com.bank.admin.controller.vo.CustomerTransactionDTO;


//http://localhost:8098/admin/customer/trasaction/t7722
@RestController
@RequestMapping("/admin")
public class BankAdminController {
	

	@Autowired
	private RestTemplate restTemplate;
	
	/*public static void main(String[] args) {
		RestTemplate restTemplate=new RestTemplate();
		//Calling customer-transaction-api - this service name is registered into the Eureka
		CustomerTransactionDTO response = 
				restTemplate.getForObject("http://localhost:9098/customer/transaction/t883837",CustomerTransactionDTO.class);
		System.out.println("Response Received as " + response);
	}
	
*/
	
	@PostMapping("/test/modest")
	public ApplicationVO postTest(@RequestBody ApplicationVO applicationVO,@RequestHeader("authorization") String authorization,@RequestHeader("location") String location) {
		System.out.println("authorization  = "+authorization);
		System.out.println("location  = "+location);
		
		System.out.println(applicationVO);
		applicationVO.setOid(new Random().nextInt(1233));
		return applicationVO;
	}
	
	//  ->> /admin/ttest/modest/T029292/abc
	//This style is called sending data as a part of URI
	@GetMapping("/ttest/modest/{code}/{name}")
	public ApplicationVO processTest(@PathVariable String code,@PathVariable String name) {
		ApplicationVO applicationVO=new  ApplicationVO();
		applicationVO.setColor(code);
		applicationVO.setName(name);
		applicationVO.setPrice(900);
		return applicationVO;
	}
	
	///admin/test/modest?code=T029292&name=abc
	@GetMapping("/test/modest")
	public ApplicationVO findTest(@RequestParam String code,@RequestParam String name) {
		ApplicationVO applicationVO=new  ApplicationVO();
		applicationVO.setColor(code);
		applicationVO.setName(name);
		applicationVO.setPrice(900);
		return applicationVO;
	}
	
	@RequestMapping(value = "/ctrasaction/{tid}", method = RequestMethod.GET)
	public CustomerTransactionDTO getCustomerTransaction(@PathVariable String tid) {
		System.out.println("Getting details for " + tid);
		//Calling customer-transaction-api - this service name is registered into the Eureka
		CustomerTransactionDTO response = //GET
				restTemplate.getForObject("http://transaction-services/customer/transaction/"+tid,CustomerTransactionDTO.class);
		System.out.println("Response Received as " + response);
		return response;
	}
	
	
	
    //Bean annotation is used to create RestTemplate object and
	//finally it is pushed inside spring container
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}