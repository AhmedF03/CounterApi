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
    //Dictionnary using Map, <String,Int>
    Map<String,Integer> dictionary = new HashMap<>();

    //adds values in dictionary
    @PostConstruct
    public void initialize(){
        dictionary.put("abc",5);
        dictionary.put("xyz",3);
    }

    @GetMapping("/Counters/")
    public ResponseEntity<String> getCounters() {
        return new ResponseEntity<>(dictionary.toString(), HttpStatus.OK);
    }
    @PostMapping("/Counters")
    public ResponseEntity<String> getCounters(@RequestParam int initialValue) {
        int size = dictionary.size();
        String counterName = "counter"+size;
        dictionary.put(counterName,initialValue);
        return new ResponseEntity<>(dictionary.toString(), HttpStatus.OK);
    }

    @PutMapping("/Counters/{counterName}")
    public ResponseEntity<String> incrementCounter(@PathVariable String counterName) {
        if (dictionary.containsKey(counterName)) {
            dictionary.put(counterName, dictionary.get(counterName)+1);
            return new ResponseEntity<>(dictionary.toString(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("404 Not found", HttpStatus.OK);
        }
    }

    @DeleteMapping("/Counters/{counterName}")
    public ResponseEntity<String> decrement(@PathVariable String counterName) {
        if (dictionary.containsKey(counterName)) {
            if (dictionary.get(counterName) ==0) {
                dictionary.remove(counterName);
                return new ResponseEntity<>("404 Not found", HttpStatus.OK);
            }
            else {
                dictionary.put(counterName, dictionary.get(counterName)-1);
                return new ResponseEntity<>(dictionary.toString(), HttpStatus.OK);
            }
        }
        else {
            return new ResponseEntity<>("404 Not found", HttpStatus.OK);
        }
    }
    @GetMapping("/Counters/{counterName}")
    public ResponseEntity<String> getCounterName(@PathVariable String counterName) {
        if (dictionary.containsKey(counterName)) {
            return new ResponseEntity<>("{" + counterName + ": " + dictionary.get(counterName) + "}"
                    , HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("404 Not found", HttpStatus.OK);
        }
    }

}
