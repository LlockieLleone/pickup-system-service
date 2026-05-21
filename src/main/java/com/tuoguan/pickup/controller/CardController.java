package com.tuoguan.pickup.controller;

import com.tuoguan.pickup.dto.ApiResponse;
import com.tuoguan.pickup.entity.Card;
import com.tuoguan.pickup.repository.CardRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardRepository cardRepository;

    public CardController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @PostMapping
    public ApiResponse<Card> createCard(@RequestBody Card card) {
        return ApiResponse.ok("Card created", cardRepository.save(card));
    }

    @GetMapping
    public ApiResponse<List<Card>> getAllCards() {
        return ApiResponse.ok(cardRepository.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Card> getCardById(@PathVariable Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        return ApiResponse.ok(card);
    }
}