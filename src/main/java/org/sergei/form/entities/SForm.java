package org.sergei.form.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

/**
 * Domain entity that represents a single message submitted via the HTML form
 * in the demo application.
 *
 * <p>The {@code SForm} record is stored in the {@code sform} table.
 * A random {@link java.util.UUID} is generated on construction and used as
 * the primary key.  Besides the technical identifier the entity keeps
 * the following business attributes:</p>
 *
 * <ul>
 *   <li>{@code username} – the name entered by the visitor</li>
 *   <li>{@code email} – the visitor’s e‑mail address</li>
 *   <li>{@code message} – the message body itself</li>
 * </ul>
 *
 * <p>The class is a regular JPA entity and can therefore be managed by any
 * Jakarta Persistence provider (e.g. Hibernate).</p>
 */

@Entity
@Table(name ="sform")
public class SForm {

    /** Primary key stored as a 16‑byte binary value in MySQL. */
    @Id
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID uuid;

    /** The visitor’s chosen display name. */
    @Column(name = "username", nullable = false)
    private String username;

    /** The visitor’s e‑mail address. */
    @Column(name = "email", nullable = false)
    private String email;

    /** Free‑text message submitted by the visitor. */
    @Column(name = "message", nullable = false)
    private String message;

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /** Creates a new, empty instance with a freshly generated UUID. */
    public SForm() {
        uuid = UUID.randomUUID();
    }

    /**
     * Creates a fully initialised instance.
     *
     * @param username the visitor’s name
     * @param email    the visitor’s e‑mail address
     * @param message  the message body
     */
    public SForm(String username, String email, String message) {
        uuid = UUID.randomUUID();
        this.username = username;
        this.email = email;
        this.message = message;
    }

    // -------------------------------------------------------------------------
    // Getters & setters
    // -------------------------------------------------------------------------

    /** @return the technical identifier (primary key) */
    public UUID getUuid() {
        return uuid;
    }

    /** Allows frameworks to inject a UUID; normally not used by application code. */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // -------------------------------------------------------------------------
    // Object overrides
    // -------------------------------------------------------------------------
    @Override
    public String toString() {
        return "SForm{" +
                "uuid=" + uuid +
                ", name='" + username + '\'' +
                ", email='" + email + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
