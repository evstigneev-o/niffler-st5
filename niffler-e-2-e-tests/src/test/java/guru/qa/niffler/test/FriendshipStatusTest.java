package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.pages.PeoplePage;
import guru.qa.niffler.pages.WelcomePage;
import org.junit.jupiter.api.Test;

import static guru.qa.niffler.jupiter.annotation.User.Selector.*;


@WebTest
public class FriendshipStatusTest extends BaseWebTest {

    private final PeoplePage peoplePage = new PeoplePage();


    @Test
    void userShouldHavePendingInvitationsStatus(@User(selector = INVITE_SENT) UserJson userForTest, @User(selector = INVITE_RECEIVED) UserJson anotherUserForTest) {
        var user = Selenide.open(WelcomePage.URL, WelcomePage.class)
                .openLoginPage()
                .signIn(userForTest.username(), userForTest.testData().password())
                .openPeoplePage()
                .findUserByName(anotherUserForTest.username());
        peoplePage.checkSendedInvite(user);
    }

    @Test
    void userShouldHavePendingInvitationsStatus2(@User(selector = INVITE_SENT) UserJson userForTest, @User(selector = INVITE_RECEIVED) UserJson anotherUserForTest) {
        var user = Selenide.open(WelcomePage.URL, WelcomePage.class)
                .openLoginPage()
                .signIn(userForTest.username(), userForTest.testData().password())
                .openPeoplePage()
                .findUserByName(anotherUserForTest.username());
        peoplePage.checkSendedInvite(user);
    }

    @Test
    void userShouldHavePendingInvitationsStatus3(@User(selector = INVITE_SENT) UserJson userForTest, @User(selector = INVITE_RECEIVED) UserJson anotherUserForTest) {
        var user = Selenide.open(WelcomePage.URL, WelcomePage.class)
                .openLoginPage()
                .signIn(userForTest.username(), userForTest.testData().password())
                .openPeoplePage()
                .findUserByName(anotherUserForTest.username());
        peoplePage.checkSendedInvite(user);
    }

    @Test
    void userShouldHaveFriendshipStatus(@User(selector = WITH_FRIEND) UserJson userForTest) {
        Selenide.open(WelcomePage.URL, WelcomePage.class)
                .openLoginPage()
                .signIn(userForTest.username(), userForTest.testData().password())
                .openPeoplePage()
                .checkFriendship();
    }

    @Test
    void userShouldHaveFriendshipStatus2(@User(selector = WITH_FRIEND) UserJson userForTest) {
        Selenide.open(WelcomePage.URL, WelcomePage.class)
                .openLoginPage()
                .signIn(userForTest.username(), userForTest.testData().password())
                .openPeoplePage()
                .checkFriendship();
    }

    @Test
    void userShouldHaveFriendshipStatus3(@User(selector = WITH_FRIEND) UserJson userForTest) {
        Selenide.open(WelcomePage.URL, WelcomePage.class)
                .openLoginPage()
                .signIn(userForTest.username(), userForTest.testData().password())
                .openPeoplePage()
                .checkFriendship();
    }

    @Test
    void userShouldHaveReceivedInvite(@User(selector = INVITE_RECEIVED) UserJson userForTest, @User(selector = INVITE_SENT) UserJson anotherUserForTest) {
        var user = Selenide.open(WelcomePage.URL, WelcomePage.class)
                .openLoginPage()
                .signIn(userForTest.username(), userForTest.testData().password())
                .openPeoplePage()
                .findUserByName(anotherUserForTest.username());
        peoplePage.checkReceivedInvite(user);
    }

    @Test
    void userShouldHaveReceivedInvite2(@User(selector = INVITE_RECEIVED) UserJson userForTest, @User(selector = INVITE_SENT) UserJson anotherUserForTest) {
        var user = Selenide.open(WelcomePage.URL, WelcomePage.class)
                .openLoginPage()
                .signIn(userForTest.username(), userForTest.testData().password())
                .openPeoplePage()
                .findUserByName(anotherUserForTest.username());
        peoplePage.checkReceivedInvite(user);
    }

    @Test
    void userShouldHaveReceivedInvite3(@User(selector = INVITE_RECEIVED) UserJson userForTest, @User(selector = INVITE_SENT) UserJson anotherUserForTest) {
        var user = Selenide.open(WelcomePage.URL, WelcomePage.class)
                .openLoginPage()
                .signIn(userForTest.username(), userForTest.testData().password())
                .openPeoplePage()
                .findUserByName(anotherUserForTest.username());
        peoplePage.checkReceivedInvite(user);
    }
}
