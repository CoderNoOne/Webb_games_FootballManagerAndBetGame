package org.example.core.integration;

import org.example.MainTestConfiguration;
import org.example.core.EmailService;
import org.example.model.core.PasswordDto;
import org.example.model.core.UserDto;
import org.example.model.core.enums.GenderDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = MainTestConfiguration.class,
        properties = "server.port=2222")
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class HomeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/home get")
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
    @Sql(scripts = {"/scripts/user_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/truncate_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails(value = "Alaba")
    void test3() throws Exception {

        //given
        String username = "Alaba";
        String photoUrl = "www.photoUrl.com";
        MockHttpServletRequestBuilder requestBuilder = get("/userPage");

        //when
        MvcResult result = mockMvc
                .perform(requestBuilder)
                //then
                .andExpect(view().name("core/user_page"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(model().attributeExists("loggedUsers", "username"))
                .andExpect(model().attribute("username", equalTo(username)))
                .andReturn();

        HttpSession session = result.getRequest().getSession();

        assert session != null;
        assertThat(session.getAttribute("userPhoto"), is(equalTo(photoUrl)));

    }

    @Test
    @DisplayName("GET /register")
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
                .andExpect(model().attribute("userDto", is(equalTo(UserDto.builder().password(new PasswordDto()).build()))))
                .andExpect(model().attribute("genders", GenderDto.values()));
    }

    @Test
    @DisplayName("POST /register unsuccessful register - not valid userDto details")
    void test5() throws Exception {

        //given

        MockMultipartFile file = new MockMultipartFile("file", new byte[]{});
        UserDto user = UserDto.builder()
                .firstName("a")
                .lastName("")
                .file(file)
                .build();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/register")
                .with(csrf())
                .flashAttr("userDto", user);


        //when
        mockMvc
                .perform(request)
                //then
                .andExpect(view().name("core/register_form"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(model().attributeHasFieldErrors(
                        "userDto",
                        "gender", "username", "firstName", "lastName", "email", "password", "birthDate"))
                .andExpect(model().attributeDoesNotExist("confirmationMessage"));

    }

    @Test
    @DisplayName("POST /register unsuccessful register - username taken")
    @Sql(scripts = {"/scripts/user_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/truncate_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test6() throws Exception {
        //given

        MockMultipartFile file = new MockMultipartFile("file", new byte[]{});
        UserDto user = UserDto.builder()
                .firstName("Adam")
                .lastName("Nowak")
                .password(PasswordDto.builder()
                        .password("NoPassword123")
                        .confirmationPassword("NoPassword123")
                        .build())
                .username("Alaba") /*already defined in user_test_data.sql*/
                .gender(GenderDto.MALE)
                .birthDate(LocalDate.of(1990, 1, 1))
                .email("myemailASD@gmail.com")
                .file(file)
                .build();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/register")
                .with(csrf())
                .flashAttr("userDto", user);


        //when
        mockMvc
                .perform(request)
                //then
                .andExpect(view().name("core/register_form"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(model().attributeErrorCount(
                        "userDto",
                        1))
                .andExpect(model().attribute("usernameTaken", is(equalTo("There is already a user registered with the username provided."))))
                .andExpect(model().attributeDoesNotExist("confirmationMessage"));

    }

    @Test
    @DisplayName("POST /register unsuccessful register - email taken")
    @Sql(scripts = {"/scripts/user_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/truncate_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test7() throws Exception {
        //given

        MockMultipartFile file = new MockMultipartFile("file", new byte[]{});
        UserDto user = UserDto.builder()
                .firstName("Adam")
                .lastName("Nowak")
                .password(PasswordDto.builder()
                        .password("NoPassword123")
                        .confirmationPassword("NoPassword123")
                        .build())
                .username("Alaba2")
                .gender(GenderDto.MALE)
                .birthDate(LocalDate.of(1990, 1, 1))
                .email("alaba@gmail.com") /*already defined in user_test_data.sql*/
                .file(file)
                .build();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/register")
                .with(csrf())
                .flashAttr("userDto", user);


        //when
        mockMvc
                .perform(request)
                //then
                .andExpect(view().name("core/register_form"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(model().attributeErrorCount(
                        "userDto",
                        1))
                .andExpect(model().attributeDoesNotExist("usernameTaken"))
                .andExpect(model().attribute("emailTaken", is(equalTo("There is already a user registered with the email provided."))))
                .andExpect(model().attributeDoesNotExist("confirmationMessage"));

    }


    @Test
    @DisplayName("POST /register successful register")
    @Sql(scripts = {"/scripts/user_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/truncate_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test8() throws Exception {

        //given
        MockMultipartFile file = new MockMultipartFile("file", new byte[]{});
        UserDto user = UserDto.builder()
                .firstName("Adam")
                .lastName("Nowak")
                .password(PasswordDto.builder()
                        .password("NoPassword123")
                        .confirmationPassword("NoPassword123")
                        .build())
                .username("ANowakasdf")
                .gender(GenderDto.MALE)
                .birthDate(LocalDate.of(1990, 1, 1))
                .email("myemailASD@gmail.com")
                .file(file)
                .build();
        String confirmationMessage = "A confirmation e-mail has been sent to " + user.getEmail();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/register")
                .with(csrf())
                .flashAttr("userDto", user);


        //when
        mockMvc
                .perform(request)
                //then
                .andDo(print())
                .andExpect(view().name("core/register_form"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeDoesNotExist("usernameTaken", "emailTaken"))
                .andExpect(model().attribute("confirmationMessage", is(equalTo(confirmationMessage))));

    }

    @Test
    @DisplayName("login denied")
    @Sql(scripts = {"/scripts/user_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/truncate_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test9() throws Exception {


        //given

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/login")
                .with(csrf());

        //when
        mockMvc
                .perform(request)
                //then
                .andExpect(MockMvcResultMatchers.status().is(302));
    }

    @Test
    @DisplayName("login successfull - ROLE_USER")
    @Sql(scripts = {"/scripts/user_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/truncate_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test10() throws Exception {


        //given

        RequestBuilder requestBuilder = formLogin()
                .userParameter("nick")
                .user("Alaba")
                .passwordParam("pass")
                .password("Alaba123");

        //when
        MvcResult result = mockMvc
                .perform(requestBuilder)
                //then
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andReturn();

        assertThat(result.getResponse().getRedirectedUrl(), is(equalTo("/userPage")));
    }

    @Test
    @DisplayName("login successfull - ROLE_ADMIN")
    @Sql(scripts = {"/scripts/user_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/truncate_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test11() throws Exception {


        //given

        RequestBuilder requestBuilder = formLogin()
                .userParameter("nick")
                .user("Admin")
                .passwordParam("pass")
                .password("Admin123");

        //when
        MvcResult result = mockMvc
                .perform(requestBuilder)
                //then
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andReturn();

        assertThat(result.getResponse().getRedirectedUrl(), is(equalTo("/admin")));
    }

    @Test
    @DisplayName("logout")
    void test12() throws Exception {

        //given

        var request = logout()
                .logoutUrl("/logout");

        //when
        MvcResult result = mockMvc
                .perform(request)
                //then
                .andReturn();

        assertThat(result.getResponse().getRedirectedUrl(), is(equalTo("/logout?logout")));
        assertThat(result.getResponse().getStatus(), is(equalTo(302)));

    }

    @Test
    @DisplayName("GET confirm account Case no user associated with token")
    void test13() throws Exception {

        //given

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/confirm")
                .param("token", "noOne");

        //when
        MvcResult result = mockMvc
                .perform(request)
                //then
                .andExpect(status().isOk())
                .andExpect(model().attribute("invalidToken", is(equalTo("This is an invalid confirmation link"))))
                .andExpect(view().name(is(equalTo("core/confirm"))))
                .andReturn();

    }

    @Test
    @DisplayName("GET confirm account Case there is a user associated with token")
    @Sql(scripts = {"/scripts/user_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into verification_tokens (token, user_username) values ('tokenVal', 'Alaba')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/truncate_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test14() throws Exception {

        //given

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/confirm")
                .param("token", "tokenVal");

        //when
        MvcResult result = mockMvc
                .perform(request)
                //then
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("invalidToken"))
                .andExpect(model().attribute("password", is(equalTo(PasswordDto.builder().build()))))
                .andExpect(model().attribute("token", is(equalTo("tokenVal"))))
                .andExpect(model().attribute("userName", is(equalTo("Alaba"))))
                .andExpect(model().attribute("activatedAlready", is(true)))
                .andExpect(model().attributeExists("tokenExpired"))
                .andExpect(view().name(is(equalTo("core/confirm"))))
                .andReturn();

    }

    @Test
    @DisplayName("POST confirm account Case no user associated with token")
    void test15() throws Exception {

        //given
        PasswordDto passwordDto = PasswordDto.builder()
                .password("pass")
                .confirmationPassword("pass")
                .build();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/confirm")
                .flashAttr("password", passwordDto)
                .param("token", "noOne")
                .with(csrf());


        //when
        mockMvc
                .perform(request)
                //then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("password", is(passwordDto)))
                .andExpect(model().attributeDoesNotExist("token"))
                .andExpect(view().name(is(equalTo("core/confirm"))));
    }

    @Test
    @DisplayName("POST confirm account Case user associated with token, password and confirma are different")
    @Sql(scripts = {"/scripts/user_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into verification_tokens (token, user_username) values ('tokenVal', 'Alaba')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/truncate_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test16() throws Exception {

        //given
        PasswordDto passwordDto = PasswordDto.builder()
                .password("pass")
                .confirmationPassword("other")
                .build();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/confirm")
                .param("password", passwordDto.getPassword())
                .param("confirmationPassword", passwordDto.getConfirmationPassword())
                .param("token", "tokenVal")
                .with(csrf());


        //when
        mockMvc
                .perform(request)
                //then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("password", is(PasswordDto.builder().build())))
                .andExpect(model().attribute("token", is(equalTo("tokenVal"))))
                .andExpect(model().attribute("NotSamePasswords", is(equalTo("Passwords must be the same"))))
                .andExpect(view().name(is(equalTo("core/confirm"))));
    }

    @Test
    @DisplayName("POST confirm account Case user associated with token: bad password")
    @Sql(scripts = {"/scripts/user_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into verification_tokens (token, user_username) values ('tokenVal', 'Alaba')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/truncate_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test17() throws Exception {

        //given
        PasswordDto passwordDto = PasswordDto.builder()
                .password("pass")
                .confirmationPassword("pass")
                .build();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/confirm")
                .param("password", passwordDto.getPassword())
                .param("confirmationPassword", passwordDto.getConfirmationPassword())
                .param("token", "tokenVal")
                .with(csrf());


        //when
        mockMvc
                .perform(request)
                //then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("password", is(PasswordDto.builder().build())))
                .andExpect(model().attribute("token", is(equalTo("tokenVal"))))
                .andExpect(model().attributeDoesNotExist("NotSamePasswords"))
                .andExpect(model().attribute("passwordError", is(equalTo("Provided password is not correct"))))
                .andExpect(view().name(is(equalTo("core/confirm"))));
    }

    @Test
    @DisplayName("POST confirm account Case user associated with token: success activation")
    @Sql(scripts = {"/scripts/user_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into verification_tokens (token, user_username) values ('tokenVal', 'Alaba')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/truncate_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test18() throws Exception {

        //given
        PasswordDto passwordDto = PasswordDto.builder()
                .password("Alaba123")
                .confirmationPassword("Alaba123")
                .build();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/confirm")
                .param("password", passwordDto.getPassword())
                .param("confirmationPassword", passwordDto.getConfirmationPassword())
                .param("token", "tokenVal")
                .with(csrf());


        //when
        mockMvc
                .perform(request.requestAttr("password", passwordDto))
                //then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("password", is(PasswordDto.builder().build())))
                .andExpect(model().attribute("token", is(equalTo("tokenVal"))))
                .andExpect(model().attributeDoesNotExist("NotSamePasswords", "passwordError"))
                .andExpect(model().attribute("successMessage", is(equalTo("Your account has been activated!"))))
                .andExpect(model().attribute("activatedAlready", is(true)))
                .andExpect(view().name(is(equalTo("core/confirm"))));
    }

    @Test
    @DisplayName("GET accesDenied")
    void test19() throws Exception {

        //given
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/accessDenied")
                .header("REQUESTED-URL", "someUrl");

        //when
        mockMvc
                .perform(request)
                //then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(is(equalTo("core/access_denied"))))
                .andExpect(model().attribute("headerInfo", is(equalTo("someUrl"))));
    }

    @Test
    @DisplayName("GET /changePassword, case: token not associated with any user")
    void test20() throws Exception {

        //given
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/changePassword")
                .param("token", "noUserAssociated");

        //when
        mockMvc
                .perform(request)
                //then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(is(equalTo("core/change_password"))))
                .andExpect(model().attribute("invalidLink", is(equalTo("This is an invalid link"))))
                .andExpect(model().attributeDoesNotExist("password", "token", "username"));

    }

    @Test
    @DisplayName("GET /changePassword, case: token associated with user")
    @Sql(scripts = {"/scripts/user_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into verification_tokens (token, user_username) values ('tokenVal', 'Alaba')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/truncate_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test21() throws Exception {

        //given
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/changePassword")
                .param("token", "tokenVal");

        //when
        mockMvc
                .perform(request)
                //then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(is(equalTo("core/change_password"))))
                .andExpect(model().attributeDoesNotExist("invalidLink"))
                .andExpect(model().attributeExists("password", "token", "userName"))
                .andExpect(model().attribute("password", PasswordDto.builder().build()))
                .andExpect(model().attribute("token", is(equalTo("tokenVal"))))
                .andExpect(model().attribute("userName", is(equalTo("Alaba"))));
    }

    @Test
    @DisplayName("POST /changePassword, case: token not associated with user")
    void test22() throws Exception {

        //given
        PasswordDto password = PasswordDto.builder()
                .password("pass")
                .confirmationPassword("pass")
                .build();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/changePassword")
                .param("token", "tokenVal")
                .param("password", password.getPassword())
                .param("confirmationPassword", password.getConfirmationPassword())
                .with(csrf());

        //when
        mockMvc
                .perform(request)
                //then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(is(equalTo("core/change_password"))))
                .andExpect(model().attribute("invalidToken", is(equalTo("Invalid token. Please provide a valid one"))))
                .andExpect(model().attribute("password", is(equalTo(PasswordDto.builder().build()))))
                .andExpect(model().attributeDoesNotExist("userName", "token", "successMessage", "notSamePassword"));
    }

    @Test
    @DisplayName("POST /changePassword, case: token associated with user, password and his confirmation not the same")
    @Sql(scripts = {"/scripts/user_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into verification_tokens (token, user_username) values ('tokenVal', 'Alaba')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/truncate_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test23() throws Exception {

        //given
        PasswordDto password = PasswordDto.builder()
                .password("pass")
                .confirmationPassword("otherPass")
                .build();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/changePassword")
                .param("token", "tokenVal")
                .param("password", password.getPassword())
                .param("confirmationPassword", password.getConfirmationPassword())
                .with(csrf());

        //when
        mockMvc
                .perform(request)
                //then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(is(equalTo("core/change_password"))))
                .andExpect(model().attributeDoesNotExist("invalidToken", "successMessage"))
                .andExpect(model().attribute("token", is("tokenVal")))
                .andExpect(model().attribute("userName", is(equalTo("Alaba"))));
    }

    @Test
    @DisplayName("POST /changePassword, case: token associated with user, password and his confirmation are the same")
    @Sql(scripts = {"/scripts/user_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "insert into verification_tokens (token, user_username) values ('tokenVal', 'Alaba')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/truncate_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test24() throws Exception {

        //given
        PasswordDto password = PasswordDto.builder()
                .password("newPassword123")
                .confirmationPassword("newPassword123")
                .build();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/changePassword")
                .param("token", "tokenVal")
                .param("password", password.getPassword())
                .param("confirmationPassword", password.getConfirmationPassword())
                .with(csrf());

        //when
        mockMvc
                .perform(request)
                //then
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name(is(equalTo("core/change_password"))))
                .andExpect(model().attributeDoesNotExist("invalidToken"))
                .andExpect(model().attribute("token", is("tokenVal")))
                .andExpect(model().attribute("userName", is(equalTo("Alaba"))))
                .andExpect(model().attribute("successMessage", is(equalTo("Your password has been changed!"))));
    }

    @Test
    @DisplayName("GET /forgotPassword")
    void test25() throws Exception {

        //given

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/forgotPassword");

        //when
        mockMvc
                .perform(request)
                //then
                .andExpect(status().isOk())
                .andExpect(view().name(is(equalTo("core/forgot"))))
                .andExpect(model().attribute("email", is("")));
    }

    @Test
    @DisplayName("POST /sendLinkToChangePassword case: no email in db")
    void test26() throws Exception {

        //given
        String email = "example@gmail.com";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/sendLinkToChangePassword")
                .param("email", email)
                .with(csrf());

        //when
        mockMvc
                .perform(request)
                //then
                .andExpect(status().isOk())
                .andExpect(view().name(is(equalTo("core/forgot"))))
                .andExpect(model().attribute("wrongEmail", is(equalTo("Email " + email + " doesn't exist"))));
    }

    @Test
    @DisplayName("POST /sendLinkToChangePassword case: no email in db")
    @Sql(scripts = {"/scripts/user_test_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/scripts/truncate_data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test27() throws Exception {

        //given
        String email = "alaba@gmail.com";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/sendLinkToChangePassword")
                .param("email", email)
                .with(csrf());

        //when
        mockMvc
                .perform(request)
                //then
                .andExpect(status().isOk())
                .andExpect(view().name(is(equalTo("core/forgot"))))
                .andExpect(model().attributeDoesNotExist("wrongEmail"))
                .andExpect(model().attribute("confirmationMessage", is(equalTo("Link has been sent to " + email))));
    }

}
