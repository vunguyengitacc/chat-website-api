package com.anhvu.it.chatapp.respository.access;

import com.anhvu.it.chatapp.respository.model.Member;
import com.anhvu.it.chatapp.respository.model.id.MemberID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDAL extends CrudRepository<Member, MemberID> {

}
