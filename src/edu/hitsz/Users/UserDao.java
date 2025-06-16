package edu.hitsz.Users;

import java.util.List;

public interface UserDao {
    void findByName(String userName);
    List<User> getAllUsers();
    void showRankingList();
    void doAdd(User user);
    void doDelet(int id);
    void writeIn();
}
