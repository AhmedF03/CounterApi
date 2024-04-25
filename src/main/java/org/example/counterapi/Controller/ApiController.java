package org.example.counterapi.Controller;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ApiController {

    // Request body gets the body need to be same type, then use get
    // Postconstruct to initialize the dictionary before starting requests

    //Dictionnary using Map, <String,Int>
    static Map<String,Integer> dictionary = new HashMap<>();

    //adds values in dictionary
    @PostConstruct
    public void initialize(){
        dictionary.put("abc",5);
        dictionary.put("xyz",3);
    }

    // Gets all the counters
    @GetMapping("/Counters/")
    public ResponseEntity<Map<String, Integer>> getCounters() {
        return new ResponseEntity<>(dictionary, HttpStatus.OK);
    }
    //Create a new counter with initial value
    @PostMapping("/Counters")
    public ResponseEntity<Map<String, Integer>> createCounter(@RequestBody Map<String, Integer> request) {
        // what if it's not named initial value?
        //dictionary.put("initialValue", request.get("initialValue"));
        //request has the body in a Map type
        if (!request.isEmpty()) {
            Map.Entry<String,Integer> entry = request.entrySet().iterator().next();
            dictionary.put(entry.getKey(),entry.getValue());
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(dictionary, HttpStatus.OK);
    }
    // increment counter by counter name
    @PutMapping("/Counters/{counterName}")
    public ResponseEntity<Map<String, Integer>> incrementCounter(@PathVariable String counterName) {
        if (dictionary.containsKey(counterName)) {
            dictionary.put(counterName, dictionary.get(counterName)+1);
            return new ResponseEntity<>(dictionary, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //Decrement counter by counter name
    @DeleteMapping("/Counters/{counterName}")
    public ResponseEntity<Map<String, Integer>> decrementCounter(@PathVariable String counterName) {
        if (dictionary.containsKey(counterName)) {
            if (dictionary.get(counterName) ==1) {
                dictionary.remove(counterName);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                dictionary.put(counterName, dictionary.get(counterName)-1);
                return new ResponseEntity<>(dictionary, HttpStatus.OK);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // Gets value of counter
    @GetMapping("/Counters/{counterName}")
    public ResponseEntity<Map<String, Integer>> getCounterValue(@PathVariable String counterName) {
        if (dictionary.containsKey(counterName)) {
            Map<String,Integer> counter = new HashMap<>();
            counter.put(counterName,dictionary.get(counterName));
            return new ResponseEntity<>(counter, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
