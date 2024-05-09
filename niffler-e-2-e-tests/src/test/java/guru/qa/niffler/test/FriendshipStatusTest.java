package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.pages.MainPage;
import guru.qa.niffler.pages.PeoplePage;
import guru.qa.niffler.pages.WelcomePage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static guru.qa.niffler.jupiter.annotation.User.Selector.*;


@WebTest
public class FriendshipStatusTest {

    private final WelcomePage welcomePage = new WelcomePage();
    private final MainPage mainPage = new MainPage();
    private final PeoplePage peoplePage = new PeoplePage();


    static {
        Configuration.browserSize = "1920x1080";
        Configuration.browserCapabilities = new ChromeOptions()
                .addArguments("--incognito");
    }


    @Test
    void userShouldHavePendingInvitationsStatus(@User(selector = INVITE_SENT) UserJson userForTest, @User(selector = INVITE_RECEIVED) UserJson anotherUserForTest) {
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage
                .openLoginPage()
                .signIn(userForTest.username(), userForTest.testData().password());
        mainPage.openPeoplePage();
        var user = peoplePage.findUserByName(anotherUserForTest.username());
        peoplePage.checkSendedInvite(user);
    }

    @Test
    void userShouldHavePendingInvitationsStatus2(@User(selector = INVITE_SENT) UserJson userForTest, @User(selector = INVITE_RECEIVED) UserJson anotherUserForTest) {
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage
                .openLoginPage()
                .signIn(userForTest.username(), userForTest.testData().password());
        mainPage.openPeoplePage();
        var user = peoplePage.findUserByName(anotherUserForTest.username());
        peoplePage.checkSendedInvite(user);
    }

    @Test
    void userShouldHavePendingInvitationsStatus3(@User(selector = INVITE_SENT) UserJson userForTest, @User(selector = INVITE_RECEIVED) UserJson anotherUserForTest) {
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage
                .openLoginPage()
                .signIn(userForTest.username(), userForTest.testData().password());
        mainPage.openPeoplePage();
        var user = peoplePage.findUserByName(anotherUserForTest.username());
        peoplePage.checkSendedInvite(user);
    }

    @Test
    void userShouldHaveFriendshipStatus(@User(selector = WITH_FRIEND) UserJson userForTest) {
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage
                .openLoginPage()
                .signIn(userForTest.username(), userForTest.testData().password());
        mainPage.openPeoplePage();
        peoplePage.checkFriendship();
    }

    @Test
    void userShouldHaveFriendshipStatus2(@User(selector = WITH_FRIEND) UserJson userForTest) {
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage
                .openLoginPage()
                .signIn(userForTest.username(), userForTest.testData().password());
        mainPage.openPeoplePage();
        peoplePage.checkFriendship();
    }

    @Test
    void userShouldHaveFriendshipStatus3(@User(selector = WITH_FRIEND) UserJson userForTest) {
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage
                .openLoginPage()
                .signIn(userForTest.username(), userForTest.testData().password());
        mainPage.openPeoplePage();
        peoplePage.checkFriendship();
    }

    @Test
    void userShouldHaveReceivedInvite(@User(selector = INVITE_RECEIVED) UserJson userForTest, @User(selector = INVITE_SENT) UserJson anotherUserForTest) {
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage
                .openLoginPage()
                .signIn(userForTest.username(), userForTest.testData().password());
        mainPage.openPeoplePage();
        var user = peoplePage.findUserByName(anotherUserForTest.username());
        peoplePage.checkReceivedInvite(user);
    }

    @Test
    void userShouldHaveReceivedInvite2(@User(selector = INVITE_RECEIVED) UserJson userForTest, @User(selector = INVITE_SENT) UserJson anotherUserForTest) {
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage
                .openLoginPage()
                .signIn(userForTest.username(), userForTest.testData().password());
        mainPage.openPeoplePage();
        var user = peoplePage.findUserByName(anotherUserForTest.username());
        peoplePage.checkReceivedInvite(user);
    }

    @Test
    void userShouldHaveReceivedInvite3(@User(selector = INVITE_RECEIVED) UserJson userForTest, @User(selector = INVITE_SENT) UserJson anotherUserForTest) {
        Selenide.open("http://127.0.0.1:3000/");
        welcomePage
                .openLoginPage()
                .signIn(userForTest.username(), userForTest.testData().password());
        mainPage.openPeoplePage();
        var user = peoplePage.findUserByName(anotherUserForTest.username());
        peoplePage.checkReceivedInvite(user);
    }
}
