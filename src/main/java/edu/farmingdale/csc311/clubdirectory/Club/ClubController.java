package edu.farmingdale.csc311.clubdirectory.Club;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    private final ClubRepository repository;

    public ClubController(ClubRepository repository) {
        this.repository = repository;
    }

    // GET /api/clubs  or  GET /api/clubs?category=Sports
    @GetMapping
    public List<Club> getClubs(@RequestParam(required = false) String category) {
        if (category == null || category.isBlank()) {
            return repository.findAll();
        }
        return repository.findByCategoryIgnoreCase(category);
    }

    // GET /api/clubs/{id}
    @GetMapping("/{id}")
    public Club getClub(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No club with id " + id));
    }

    // POST /api/clubs
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Club addClub(@Valid @RequestBody Club club) {
        Club newClub = new Club(club.getName(), club.getCategory(),
                club.getMeetingDay(), club.getRoom());
        return repository.save(newClub);
    }
}