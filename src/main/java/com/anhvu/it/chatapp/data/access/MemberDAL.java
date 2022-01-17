package com.anhvu.it.chatapp.data.access;

import com.anhvu.it.chatapp.data.model.Member;
import com.anhvu.it.chatapp.data.model.id.MemberID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDAL extends CrudRepository<Member, MemberID> {

}
