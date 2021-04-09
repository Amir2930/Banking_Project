package com.bank.transaction.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bank.transaction.api.dto.CustomerTransactionDTO;
import com.bank.transaction.api.dto.CustomerVO;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/customer")
public class CustomerTransactionController {
	
	@Value("${bank.service.name}")
	private String bankServiceName;
	
	static {
	    //for localhost testing only
	    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
	    new javax.net.ssl.HostnameVerifier(){

	        public boolean verify(String hostname,
	                javax.net.ssl.SSLSession sslSession) {
	            if (hostname.equals("localhost")) {
	                return true;
	            }
	            return false;
	        }
	    });
	}
	
	@GetMapping("/hellot")
	public String tempa(){
		return "hellot";
	}
	
	//@PreAuthorize("hasRole('ROLE_CLIENT')")
	@GetMapping("/transaction/{tid}")
	public CustomerTransactionDTO findCustomerTransaction(@PathVariable String tid){
		CustomerTransactionDTO customerTransactionDTO=new CustomerTransactionDTO();
		customerTransactionDTO.setAmount(1223D);
		customerTransactionDTO.setTxid("T1762626");
		customerTransactionDTO.setUserid("technohunk100@gmail.com");
		return customerTransactionDTO;
	}
	
   @LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/web/all")
	public List<CustomerVO> findAllCustomer() {
		//File file = new File("F:/workspace/customer-transaction-api/src/main/resources/bank_keystore.p12");
       // System.setProperty("javax.net.ssl.trustStore", file.getAbsolutePath());
       // System.setProperty("javax.net.ssl.trustStorePassword", "root@123");
		//Calling Micro services from customer to banking api micro services
		HttpHeaders getHeaders = new HttpHeaders();
        getHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity entity = new HttpEntity(getHeaders);
        List<CustomerVO> customerVOs=new ArrayList<>();
		ResponseEntity<? extends List>  result = restTemplate.exchange(bankServiceName+"/v4/customers",HttpMethod.GET,entity,customerVOs.getClass());
		return result.getBody();
	}

}
