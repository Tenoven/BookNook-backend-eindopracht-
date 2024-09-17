package nl.tenoven.BookNook.Repositories;

import nl.tenoven.BookNook.Models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, String> {
}
