package com.vincenzolisi.sneakverseordersservice.Clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="products-client", url = "${gateway.url:http://sneakverse-gateway:8000}")
public interface ProductsClient {

    
    class ShoeInfo {
        public String shoeName;
        public java.math.BigDecimal shoePrice;
    }

    @GetMapping("/api/shoes/{id}")
    ShoeInfo getShoeById(@PathVariable("id") int id);
}
