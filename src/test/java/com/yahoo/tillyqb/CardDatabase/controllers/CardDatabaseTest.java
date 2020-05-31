package com.yahoo.tillyqb.CardDatabase.controllers;

import com.yahoo.tillyqb.CardDatabase.entity.CardInfo;
import com.yahoo.tillyqb.CardDatabase.repository.CardInfoRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CardDatabaseTest
{
    @Mock
    private CardInfoRepository repository;
    private CardDatabase testController;
    private CardInfo testCard;

    @Rule
    public ExpectedException exceptionChecker = ExpectedException.none();

    @Before
    public void setup()
    {
        testCard = new CardInfo();
        testCard.cost = "bullpuckey";
        testCard.text = "You win the game, but painfully and slowly because it's more fun to watch people suffer";
        testCard.id = new CardInfo.CardInfoId();
        testCard.id.name = "Jace, you know who this is";
        testCard.id.set = "I only care about blue cards";
        testCard.id.game = "star trek";
        testController = new CardDatabase(repository);

        Mockito.when(repository.save(testCard)).thenReturn(testCard);
    }

    @Test
    public void testPostCardInfoSavesToDatabase()
    {
        CardInfo actual = testController.postCardInfo(testCard);

        Mockito.verify(repository).save(testCard);
        Assert.assertEquals(testCard, actual);
    }

    @Test
    public void testDuplicateCardInfoThrowsError()
    {
        exceptionChecker.expect(ResponseStatusException.class);
        exceptionChecker.expectMessage("Card Already Exists");
        Mockito.when(repository.findById(testCard.id)).thenReturn(Optional.of(testCard));

        testController.postCardInfo(testCard);
    }
}
