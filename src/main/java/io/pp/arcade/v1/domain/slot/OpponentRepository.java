package io.pp.arcade.v1.domain.slot;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpponentRepository extends JpaRepository<Opponent, Integer> {

    Optional<Opponent> findByIntraId(String intraId);
}
