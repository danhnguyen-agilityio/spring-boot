package com.agility.resourceserver.services;

import com.agility.resourceserver.dto.UserProfileResponse;
import com.agility.resourceserver.exceptions.CustomError;
import com.agility.resourceserver.exceptions.ResourceNotFoundException;
import com.agility.resourceserver.mappers.UserProfileMapper;
import com.agility.resourceserver.models.UserProfile;
import com.agility.resourceserver.repositorys.UserProfileRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserProfileServiceTest {

    private UserProfile userProfile;
    private UserProfileResponse userProfileResponse;
    private UserProfileMapper userProfileMapper;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserProfileService userProfileService;

    @Before
    public void setUp() {
        userProfile = UserProfile.builder()
            .id(1L)
            .username("user")
            .firstName("firstName")
            .lastName("lastName")
            .address("address")
            .active(true)
            .build();

        userProfileResponse = UserProfileResponse.builder()
            .id(1L)
            .username("user")
            .firstName("firstName")
            .lastName("lastName")
            .address("address")
            .active(true)
            .build();

        userProfileMapper = Mappers.getMapper(UserProfileMapper.class);

        userProfileService = new UserProfileService(userProfileMapper, userProfileRepository);
    }

    @Test
    public void testFindUserByNameWhenExists() {
        // given
        given(userProfileRepository.findByUsername("user")).willReturn(userProfile);

        // when
        UserProfileResponse response = userProfileService.findByUsername("user");

        // then
        assertEquals(userProfileResponse, response);
    }

    @Test
    public void testFindUserByNameWhenDoesNotExists() {
        // then
        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage(CustomError.USER_NOT_FOUND.message());

        // given
        given(userProfileRepository.findByUsername("user")).willReturn(null);

        // when
        userProfileService.findByUsername("user");
    }
}