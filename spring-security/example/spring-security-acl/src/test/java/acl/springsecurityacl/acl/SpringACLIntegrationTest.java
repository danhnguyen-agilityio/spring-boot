package acl.springsecurityacl.acl;

import acl.springsecurityacl.persistence.dao.NoticeMessageRepository;
import acl.springsecurityacl.persistence.entity.NoticeMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringACLIntegrationTest {

    private static Integer FIRST_MESSAGE_ID = 1;
    private static Integer SECOND_MESSAGE_ID = 2;
    private static Integer THIRD_MESSAGE_ID = 3;
    private static String EDITTED_CONTENT = "EDITED";

    @Autowired
    NoticeMessageRepository repo;

    @Test
    @WithMockUser(username = "manager")
    public void
    givenUserManager_whenFindAllMessage_thenReturnFirstMessage(){
        List<NoticeMessage> details = repo.findAll();

        assertNotNull(details);
        assertEquals(1,details.size());
        assertEquals(FIRST_MESSAGE_ID,details.get(0).getId());
    }

    @Test
    @WithMockUser(roles = {"EDITOR"})
    public void
    givenRoleEditor_whenFindAllMessage_thenReturn3Message(){
        List<NoticeMessage> details = repo.findAll();

        assertNotNull(details);
        assertEquals(3,details.size());
    }

    @Test
    @WithMockUser(username = "manager")
    public void
    givenUserManager_whenFind1stMessageByIdAndUpdateItsContent_thenOK(){
        NoticeMessage firstMessage = repo.findById(FIRST_MESSAGE_ID);
        assertNotNull(firstMessage);
        assertEquals(FIRST_MESSAGE_ID,firstMessage.getId());

        firstMessage.setContent(EDITTED_CONTENT);
        repo.save(firstMessage);

        NoticeMessage editedFirstMessage = repo.findById(FIRST_MESSAGE_ID);

        assertNotNull(editedFirstMessage);
        assertEquals(FIRST_MESSAGE_ID,editedFirstMessage.getId());
        assertEquals(EDITTED_CONTENT,editedFirstMessage.getContent());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles = {"EDITOR"})
    public void
    givenRoleEditor_whenFind1stMessageByIdAndUpdateContent_thenFail(){
        NoticeMessage firstMessage = repo.findById(FIRST_MESSAGE_ID);

        assertNotNull(firstMessage);
        assertEquals(FIRST_MESSAGE_ID,firstMessage.getId());

        firstMessage.setContent(EDITTED_CONTENT);
        repo.save(firstMessage);
    }

    @Test
    @WithMockUser(username = "hr")
    public void givenUsernameHr_whenFindMessageById2_thenOK(){
        NoticeMessage secondMessage = repo.findById(SECOND_MESSAGE_ID);
        assertNotNull(secondMessage);
        assertEquals(SECOND_MESSAGE_ID,secondMessage.getId());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(username = "hr")
    public void givenUsernameHr_whenUpdateMessageWithId2_thenFail(){
        NoticeMessage secondMessage = new NoticeMessage();
        secondMessage.setId(SECOND_MESSAGE_ID);
        secondMessage.setContent(EDITTED_CONTENT);
        repo.save(secondMessage);
    }
}
