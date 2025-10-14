package org.example.pokemon.database;

import org.example.pokemon.entity.User;
import org.example.pokemon.utils.CloningUtility;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Datastore {

    private final CloningUtility cloningUtility;

    private final Set<User> users = new HashSet<>();

    public Datastore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }


    public synchronized void createUser(User user) {
        if (users.stream().anyMatch(character -> character.getId().equals(user.getId()))) {
            throw new IllegalArgumentException("The user id \"%s\" is not unique".formatted(user.getId()));
        }
        users.add(cloningUtility.clone(user));
    }

    public List<User> findAllUsers(){
        return users.stream()
                .map(cloningUtility::clone)
                .toList();
    }

    public User findUser(UUID id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(cloningUtility::clone)
                .orElse(null);
    }
}
