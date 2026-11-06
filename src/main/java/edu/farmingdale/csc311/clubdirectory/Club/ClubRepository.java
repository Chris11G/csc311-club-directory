package edu.farmingdale.csc311.clubdirectory.Club;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {

    List<Club> findByCategoryIgnoreCase(String category);
}