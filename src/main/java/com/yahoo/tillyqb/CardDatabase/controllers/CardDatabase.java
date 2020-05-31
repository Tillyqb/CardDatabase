package com.yahoo.tillyqb.CardDatabase.controllers;

import com.yahoo.tillyqb.CardDatabase.entity.CardInfo;
import com.yahoo.tillyqb.CardDatabase.repository.CardInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("")
@AllArgsConstructor
public class CardDatabase
{
    private CardInfoRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardInfo postCardInfo(@RequestBody CardInfo postedInfo)
    {
        if (repo.findById(postedInfo.id).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card Already Exists");

        return repo.save(postedInfo);
    }
}
