package spring.elldimi.bookshopadvquerying.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.elldimi.bookshopadvquerying.entities.AgeRestriction;
import spring.elldimi.bookshopadvquerying.entities.Book;
import spring.elldimi.bookshopadvquerying.entities.EditionType;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByReleaseDateAfterOrderByReleaseDateDesc(LocalDate localDate);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByAgeRestrictionOrderByTitle(AgeRestriction ageRestriction);

    List<Book> findAllByEditionAndCopiesLessThanEqual(EditionType editionType, int copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal bottomPrice, BigDecimal topPrice);

    @Query("Select b from Book as b Where substring(b.releaseDate,0,4) NOT LIKE :year")
    List<Book> findAllByReleaseDateNotInYear(@Param("year") String year);

    List<Book> findAllByTitleContains(String substr);

    @Query("SELECT b FROM Book AS b WHERE b.author.lastName LIKE :str")
    List<Book> findBooksWhichAuthorsLastNameStartsWith(@Param("str") String str);

    @Query("SELECT count (b) FROM Book AS b WHERE length (b.title) > :length")
    int findBooksWithTitleLongerThan(@Param("length") int titleLength);

    Book findBookByTitle(String title);

    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.copies = b.copies + :copies WHERE b.releaseDate > :date")
    int increaseBookCopiesWithGivenValue(@Param("copies") int copies,
                                         @Param("date") LocalDate date);


}
