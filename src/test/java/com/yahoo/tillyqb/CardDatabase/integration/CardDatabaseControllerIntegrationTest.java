package com.yahoo.tillyqb.CardDatabase.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yahoo.tillyqb.CardDatabase.entity.CardInfo;
import com.yahoo.tillyqb.CardDatabase.repository.CardInfoRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class CardDatabaseControllerIntegrationTest
{
    @Autowired
    private MockMvc mvc;
    @Autowired
    private CardInfoRepository cardInfoRepository;
    private ObjectMapper mapper = new ObjectMapper();
    private CardInfo testCard;

    @Before
    public void setup()
    {
        testCard = new CardInfo();
        testCard.id = new CardInfo.CardInfoId();
        testCard.id.game = "mtg";
        testCard.id.name = "incendiary flow";
        testCard.id.set = "promo fnm";
        testCard.cost = "1r";
        testCard.text = "deals 3 damage to any target, if a creature would die this way, exile it instead.";
    }

    @Test
    @DirtiesContext
    public void canPostCardsToDatabase() throws Exception
    {
        String result = mvc.perform(MockMvcRequestBuilders.post("").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(testCard)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();

        CardInfo actual = mapper.readValue(result, CardInfo.class);

        Assert.assertEquals(testCard, actual);
        cardInfoRepository.findById(testCard.id).ifPresentOrElse(card -> Assert.assertEquals(testCard, card), Assert::fail);
    }

    @Test
    @DirtiesContext
    public void throwsErrorIfCardAlreadyInDatabase() throws Exception
    {
        cardInfoRepository.save(testCard);

        String result = mvc.perform(MockMvcRequestBuilders.post("").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(testCard)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse().getErrorMessage();

        Assert.assertEquals("Card Already Exists", result);
    }
}
