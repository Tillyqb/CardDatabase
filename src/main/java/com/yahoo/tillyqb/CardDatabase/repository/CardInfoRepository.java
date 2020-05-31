package com.yahoo.tillyqb.CardDatabase.repository;

import com.yahoo.tillyqb.CardDatabase.entity.CardInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardInfoRepository extends CrudRepository<CardInfo, CardInfo.CardInfoId>
{
}
