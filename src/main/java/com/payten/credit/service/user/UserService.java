package com.payten.credit.service.user;

public interface UserService {
    Long create(User user);
    User retrieve(Long id);
    void delete(Long id);
    Long update(User user, Long userId);

}
