package pro.sky.coursework3.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.coursework3.model.SockWarehouse;
import pro.sky.coursework3.service.StoreService;

@RestController
@RequestMapping("/api/socks")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<?> coming(@RequestBody SockWarehouse sockWarehouse) {
        storeService.coming(sockWarehouse);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> expenditure(@RequestBody SockWarehouse sockWarehouse) {
        storeService.expenditure(sockWarehouse);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Integer> count(@RequestParam String color,
                                         @RequestParam float size,
                                         @RequestParam(required = false, defaultValue = "0")
                                         int cottonMin,
                                         @RequestParam(required = false, defaultValue = "100")
                                         int cottonMax) {
        int available = storeService.count(color, size, cottonMin, cottonMax);
        return ResponseEntity.ok(available);
    }

    @DeleteMapping
    public ResponseEntity<?> writeDowns(@RequestBody SockWarehouse sockWarehouse) {
        storeService.expenditure(sockWarehouse);
        return ResponseEntity.ok().build();
    }

}
