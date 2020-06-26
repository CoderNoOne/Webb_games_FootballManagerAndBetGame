package org.example.core;

import org.example.model.core.UserDto;
import org.example.model.core.enums.GenderDto;
import org.example.security.LoggedUsersRegistry;
import org.example.security.SecurityService;
import org.example.util.ControllerUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
class HomeControllerUnitTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private ControllerUtil controllerUtil;

    @Mock
    private LoggedUsersRegistry loggedUsersRegistry;


    @Mock
    private EmailService emailService;

    @Mock
    private VerificationTokenService verificationTokenService;

    @Mock
    private SecurityService securityService;

    @InjectMocks
    private HomeController homeController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(homeController)
                .build();
    }


    @Test
    @DisplayName("/ mapped to return view with name: 'home'")
    void test1() throws Exception {

        //given
        MockHttpServletRequestBuilder requestBuilder = get("/");

        //when
        mockMvc
                .perform(requestBuilder)
                //then
                .andExpect(view().name("core/home"))
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Test
    @DisplayName("/home mapped to return view with name: 'home")
    void test2() throws Exception {

        //given

        MockHttpServletRequestBuilder requestBuilder = get("/home");

        //when
        mockMvc
                .perform(requestBuilder)
                //then
                .andExpect(view().name("core/home"))
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Test
    @DisplayName("/userPage mapped to return view with name: 'core/user_page")
    void test3() throws Exception {

        //given
        String username = "Alaba";
        MockHttpServletRequestBuilder requestBuilder = get("/userPage");

        given(securityService.getActiveUser())
                .willReturn(username);

        given(loggedUsersRegistry.getLoggedUsers())
                .willReturn(Set.of(username));

        //when
        mockMvc
                .perform(requestBuilder)
                //then
                .andExpect(view().name("core/user_page"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(model().attributeExists("loggedUsers", "username"))
                .andExpect(model().attribute("username", equalTo(username)))
                .andExpect(model().attribute("loggedUsers", Set.of(username)));


        InOrder order = Mockito.inOrder(userService, loggedUsersRegistry, securityService);
        order.verify(securityService).getActiveUser();
        order.verify(userService).getPhotoUrlForUsername(username);
        order.verify(loggedUsersRegistry).getLoggedUsers();
        order.verifyNoMoreInteractions();


    }

    @Test
    @DisplayName("/register mapped to return view with name: core/register_form")
    void test4() throws Exception {

        //given
        MockHttpServletRequestBuilder requestBuilder =
                get("/register");

        //when
        mockMvc
                .perform(requestBuilder)
                //then
                .andExpect(view().name("core/register_form"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(model().attributeExists("userDto", "genders"))
                .andExpect(model().attribute("userDto", Matchers.is(equalTo(new UserDto()))))
                .andExpect(model().attribute("genders", GenderDto.values()));

    }

    @Test
    @DisplayName("POST /register mapped to return view with name: core/register_form")
    void test5() throws Exception {

        //given

        MockMultipartFile file = new MockMultipartFile("file", new byte[]{});
        UserDto user = UserDto.builder()
                .firstName("a")
                .lastName("")
                .file(file)
                .build();

        System.out.println("CONTENT TYPE:" + file.getContentType());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/register")
                .flashAttr("userDto", user);


        //when
        mockMvc
                .perform(request)
                //then
                .andDo(print())
                .andExpect(view().name("core/register_form"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(model().attributeHasFieldErrors(
                        "userDto",
                        "gender", "username", "firstName", "lastName", "email", "password", "birthDate"));
    }

    @Test
    @DisplayName("login")
    void test6() throws Exception {

        //given

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/login");


        //when
        mockMvc
                .perform(request)
                //then
                .andExpect(view().name("core/login_page"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(model().hasNoErrors());
    }

    @Test
    @DisplayName("logout")
    void test7() throws Exception {

        //given

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/logout");


        //when
        mockMvc
                .perform(request)
                //then
                .andExpect(view().name("core/login_page"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(model().hasNoErrors());
    }

    @Test
    @DisplayName("GET /confirm. Case - no user associated with specified token")
    void test8() throws Exception {

        //given
        String token = "token val";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/confirm")
                .param("token", token);


        given(verificationTokenService.getUserAssociatedWithToken(token))
                .willReturn(Optional.empty());

        //when
        mockMvc
                .perform(request)
                .andDo(print())
                //then
                .andExpect(view().name("core/confirm"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("invalidToken"))
                .andExpect(model().attribute("invalidToken", equalTo("This is an invalid confirmation link")));

        then(verificationTokenService).should(times(1)).getUserAssociatedWithToken(token);
        then(verificationTokenService).shouldHaveNoMoreInteractions();

    }
}

