package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.SubscriptionCreateDto;
import com.openclassrooms.mddapi.dtos.SubscriptionDto;
import com.openclassrooms.mddapi.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/{id}")
    public SubscriptionDto getSubscription(@PathVariable String id) {
        return subscriptionService.getSubscriptionById(Long.valueOf(id));
    }

    @PostMapping
    public SubscriptionDto createSubscription(@RequestBody SubscriptionCreateDto subscriptionCreateDto) {
        return subscriptionService.createSubscription(subscriptionCreateDto);
    }

    @PutMapping("/{id}")
    public SubscriptionDto updateSubscription(@PathVariable String id, @RequestBody SubscriptionDto subscriptionUpdateDto) {
        return subscriptionService.updateSubscription(Long.valueOf(id), subscriptionUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteSubscription(@PathVariable String id) {
        subscriptionService.deleteSubscription((Long.valueOf(id)));
    }

    @GetMapping
    public List<SubscriptionDto> getAllSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }
}
