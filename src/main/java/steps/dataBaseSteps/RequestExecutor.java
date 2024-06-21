package steps.dataBaseSteps;

import entity.Author;
import entity.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.OptimisticLockException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class RequestExecutor {

    Session session;

    public RequestExecutor() {
        session = ConnectionExecutor.getSession();
    }

    public List<Book> findAll() {
        final String hql = """
                FROM Book
                """;

        return session.createQuery(hql, Book.class)
                .getResultList();
    }

    public void deleteAll() {
        final String hql = """
                DELETE FROM book
                """;
        Transaction tr = session.beginTransaction();
        session.createNativeQuery(hql)
                .executeUpdate();
        try {
            tr.commit();
        } catch (OptimisticLockException e) {
            tr.rollback();
        }
    }

    public void insertBook(String bookTitle, Long authorId, Timestamp updated) {
        final String hql = """
                INSERT INTO book
                (book_title, author_id, updated)
                VALUES(:bookTitle, :authorId, :updated)
                """;

        Transaction tr = session.beginTransaction();
        session.createNativeQuery(hql, Book.class)
                .setParameter("bookTitle", bookTitle)
                .setParameter("authorId", authorId)
                .setParameter("updated", updated)
                .executeUpdate();
        tr.commit();

    }

    public List<Book> findByBookTitle(String bookTitle) {
        final String hql = """
                FROM Book
                WHERE book_title = :bookTitle
                """;

        return session.createQuery(hql, Book.class)
                .setParameter("bookTitle", bookTitle)
                .getResultList();

    }

    public void deleteByBookTitle(String bookTitle) {
        final String hql = """
                DELETE FROM book
                WHERE book_title = :bookTitle
                """;

        Transaction tr = session.beginTransaction();
        session.createNativeQuery(hql)
                .setParameter("bookTitle", bookTitle)
                .executeUpdate();
        tr.commit();
    }

    public void insertAuthor(String firstName, String familyName, String secondName, Date birthDate) {
        final String hql = """
                INSERT INTO author
                (first_name, family_name, second_name, birth_date)
                VALUES(:firstName, :familyName, :secondName, :birthDate)
                """;

        Transaction tr = session.beginTransaction();
        session.createNativeQuery(hql)
                .setParameter("firstName", firstName)
                .setParameter("familyName", familyName)
                .setParameter("secondName", secondName)
                .setParameter("birthDate", birthDate)
                .executeUpdate();
        tr.commit();
    }

    public Author findAuthor(String firstName, String familyName, String secondName, Date birthDate) {
        final String hql = """
                FROM Author
                WHERE first_name = :firstName AND family_name = :familyName AND second_name = :secondName
                AND birth_date = :birthDate
                """;

        return session.createQuery(hql, Author.class)
                .setParameter("firstName", firstName)
                .setParameter("familyName", familyName)
                .setParameter("secondName", secondName)
                .setParameter("birthDate", birthDate)
                .getSingleResult();
    }
}
