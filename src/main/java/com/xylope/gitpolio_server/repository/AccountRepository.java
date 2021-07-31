package com.xylope.gitpolio_server.repository;

import com.xylope.gitpolio_server.entity.Account;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
    Account getByName(@Length(max = 20) String name);
}
