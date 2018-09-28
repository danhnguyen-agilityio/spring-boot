package com.agility.spring.security.acl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookServiceImpl implements BookService {
    private Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private Map<Long, Book> books = new HashMap<>();

    // AclService: This is used to receive the ACL of the given Object Identity
    @Resource
    private MutableAclService aclService;

    @Override
    public void createBook(Book book) {
        log.info("Create Book: {}", book);
        books.put(book.getId(), book);
    }

    @Override
    @PreAuthorize("hasPermission(#id, 'com.agility.spring.security.acl.Book', 'read') or hasRole('ADMIN')")
    public Book findBookById(long id) {
        return books.get(id);
    }

    @Override
    @PostFilter("hasPermission(filterObject, 'read') or hasRole('ADMIN')")
    public List<Book> findAllBooks() {
        return new ArrayList<>(books.values());
    }

    @PreAuthorize("hasPermission(#book, 'write') or hasRole('ADMIN')")
    @Override
    public void updateBook(Book book) {

    }

    @Override
    @Transactional
    public void grantPermission(String principal, Book book, Permission[] permissions) {
        log.info("Grant {} permission to principal {} on Book {}", permissions, principal, book );

        // Object Identity is used to internally hold the domain object in the ACL module
        ObjectIdentity oi = new ObjectIdentityImpl(Book.class, book.getId());

        // Sid: Security identifier is a common class that represents the principal in an authentication object
        Sid sid = new PrincipalSid(principal);

        // Acl: Each domain object is associated with only one ACL object.
        // ACLs do not refer directly to the domain object. Instead, they refer the Object Identity
        MutableAcl acl;

        try {
            acl = (MutableAcl) aclService.readAclById(oi);
        } catch (NotFoundException e) {
            acl = aclService.createAcl(oi);
        }

        for (Permission permission : permissions) {
            switch (permission) {
                case READ:
                    // Permission: This is a bit masking information to specify the operation
                    acl.insertAce(acl.getEntries().size(), BasePermission.READ, sid, true);
                    break;
                case WRITE:
                    acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, sid, true);
                    break;
            }
        }

        aclService.updateAcl(acl);
    }
}
