package com.FilmHub.feign;
import com.FilmHub.payload.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-user", path = "/users") //  Nombre registrado en Eureka
    public interface UserClient {

        @GetMapping("/{id}")
        UserDTO getUserById(@PathVariable Long id);


}

