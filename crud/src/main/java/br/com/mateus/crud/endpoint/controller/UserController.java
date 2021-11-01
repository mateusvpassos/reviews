package br.com.mateus.crud.endpoint.controller;

import br.com.mateus.crud.endpoint.dto.UserDTO;
import br.com.mateus.crud.endpoint.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                 @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                                                 @RequestParam(value = "sort", defaultValue = "id") String sort) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sort);
        Page<UserDTO> list = userService.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findAll(@PathVariable String id) {
        return ResponseEntity.ok().body(userService.findUser(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> insert(@RequestBody UserDTO userDto) {
        UserDTO newDto = userService.saveUser(userDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping()
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDto) {
        UserDTO newDto = userService.mergeUser(userDto);
        return ResponseEntity.ok().body(newDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}