package edu.iu.c322.customerservice.controller;

import edu.iu.c322.customerservice.model.Customer;
import edu.iu.c322.customerservice.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Spring creates this object, restController is what does this
//Aspect oriented programming, the annotations
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerRepository repository;

    // this is bad it is binding this class the customerRepository so instead we use spring dependency injection
    /*
    public CustomerController() {
        this.repository = new CustomerRepository();
    }
     */

    //this is dpeendency injection
    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }


    @GetMapping
    public List<Customer> findAll(){

        return repository.findAll();

    }

    //@valid tells spring to ensure validations are checked, our validation is currently in the customer class
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public int create(@Valid @RequestBody Customer customer){
        Customer addedCustomer = repository.save(customer);
        return addedCustomer.getId();
    }

    // PUT lcoalhost:8080.customers/2
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@Valid @RequestBody Customer customer, @PathVariable int id){
        customer.setId(id);
        repository.save(customer);
    }

    //path variables shown in url
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        Customer customer = new Customer();
        customer.setId(id);
        repository.delete(customer);
    }


}

