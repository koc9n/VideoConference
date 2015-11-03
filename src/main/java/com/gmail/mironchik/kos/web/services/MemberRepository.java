package com.gmail.mironchik.kos.web.services;

import com.gmail.mironchik.kos.web.dao.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kos on 13.02.2015.
 */
@Repository("memberRepository")
public class MemberRepository {
    @Autowired
    private MongoOperations mongoOperations;

    public List<Member> findAll() {
        return mongoOperations.findAll(Member.class);
    }

    public void save(Member member) {
        mongoOperations.save(member);
    }
}
