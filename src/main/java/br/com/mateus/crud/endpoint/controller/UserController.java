package br.com.mateus.crud.endpoint.controller;

import br.com.mateus.crud.endpoint.dto.UserDTO;
import br.com.mateus.crud.endpoint.dto.UserSaveUpdateDTO;
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
    public ResponseEntity<Page<UserDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") final Integer page,
            @RequestParam(value = "size", defaultValue = "12") final Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
            @RequestParam(value = "sort", defaultValue = "id") final String sort) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sort);
        Page<UserDTO> list = userService.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> findOne(@PathVariable final String email) {
        return ResponseEntity.ok().body(userService.findUserByEmail(email));
    }

    @PostMapping
    public ResponseEntity<UserDTO> insert(@RequestBody final UserSaveUpdateDTO userDto) {
        UserDTO user = userService.saveUser(userDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{email}").buildAndExpand(user).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping
    public ResponseEntity<UserDTO> update(@RequestBody final UserSaveUpdateDTO userDto) {
        UserDTO user = userService.mergeUser(userDto);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<UserDTO> deactivate(@PathVariable final String email) {
        UserDTO user = userService.deactivateUser(email);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{email}")
    public ResponseEntity<UserDTO> activate(@PathVariable final String email) {
        UserDTO user = userService.activateUser(email);
        return ResponseEntity.ok().body(user);
    }

}