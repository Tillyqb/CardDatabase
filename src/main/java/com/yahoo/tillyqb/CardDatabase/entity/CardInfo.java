package com.yahoo.tillyqb.CardDatabase.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@EqualsAndHashCode
@ToString
public class CardInfo
{
    @EmbeddedId
    public CardInfoId id;
    public String cost;
    public String text;

    @Embeddable
    @EqualsAndHashCode
    @ToString
    public static class CardInfoId implements Serializable
    {
        public String name;
        public String set;
        public String game;
    }
}
