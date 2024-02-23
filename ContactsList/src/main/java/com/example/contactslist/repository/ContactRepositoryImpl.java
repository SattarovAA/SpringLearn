package com.example.contactslist.repository;

import com.example.contactslist.entity.Contact;
import com.example.contactslist.mapper.ContactMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ContactRepositoryImpl implements ContactRepository {
    private final JdbcTemplate jdbcTemplate;
    private long idCount;

    @EventListener(ApplicationReadyEvent.class)
    public void databaseInit() {
        createDb();
        countInit();
    }

    private void countInit() {
        idCount = findAll()
                .stream()
                .map(Contact::getId)
                .max(Comparator.comparing(Long::longValue))
                .orElse((long) 0);
    }

    private void createDb() {
        String sql = "CREATE TABLE " +
                "IF NOT EXISTS " +
                "contacts(" +
                "id integer constraint contacts_pk " +
                "primary key, " +
                "first_name varchar (255)," +
                "last_name varchar(255)," +
                "email varchar(255)," +
                "phone varchar(255)" +
                ")";
        jdbcTemplate.execute(sql);
    }

    @Override
    public Collection<Contact> findAll() {
        String sql = "SELECT * FROM contacts";
        return jdbcTemplate.query(sql, new ContactMapper());
    }

    @Override
    public Optional<Contact> findById(long id) {
        String sql = "SELECT * FROM contacts WHERE id=?";
        Contact contact = DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new ArgumentPreparedStatementSetter(new Object[]{id}),
                        new RowMapperResultSetExtractor<>(new ContactMapper(), 1)
                )
        );
        return Optional.ofNullable(contact);
    }

    @Override
    public Contact save(Contact contact) {
        String sql = "INSERT INTO contacts (" +
                "id,first_name,last_name,email,phone) " +
                "VALUES(?,?,?,?,?)";
        jdbcTemplate.update(sql, ++idCount, contact.getFirstName(),
                contact.getLastName(), contact.getEmail(), contact.getPhone());
        return contact;
    }

    @Override
    public Contact update(long id, Contact contact) {
        String sql = "UPDATE contacts " +
                "SET first_name=?, last_name=?, " +
                "email=?, phone=? " +
                "WHERE id=?";
        jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(),
                contact.getEmail(), contact.getPhone(), id);
        return contact;
    }

    @Override
    public void deleteById(long contactId) {
        String sql = "DELETE FROM contacts WHERE id=?";
        jdbcTemplate.update(sql, contactId);
    }
}
