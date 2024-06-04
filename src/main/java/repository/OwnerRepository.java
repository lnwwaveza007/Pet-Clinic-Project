package repository;

import domain.Owner;

import java.util.stream.Stream;

public interface OwnerRepository {
    public boolean addOwner(int idCard, String password, String name, String address, String phone);
    public Owner updateOwner(Owner owner);
    public Owner findOwner(int idCard);
    public Stream<Owner> getAllOwners();
}
