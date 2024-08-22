package com.petweb.sponge.pet.repository;

import com.petweb.sponge.pet.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet,Long> {
}
