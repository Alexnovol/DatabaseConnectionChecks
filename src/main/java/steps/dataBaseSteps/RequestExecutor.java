package steps.dataBaseSteps;

import entity.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
        tr.commit();
    }

    public void insertBook(String bookTitle, Long authorId) {
        final String hql = """
                INSERT INTO book
                (book_title, author_id)
                VALUES(:bookTitle, :authorId)
                """;

        Transaction tr = session.beginTransaction();
        session.createNativeQuery(hql, Book.class)
                .setParameter("bookTitle", bookTitle)
                .setParameter("authorId", authorId)
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
}
