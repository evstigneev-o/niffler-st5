package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static guru.qa.niffler.model.UserJson.simpleUser;

public class UsersQueueExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(UsersQueueExtension.class);

    private static final Map<User.Selector, Queue<UserJson>> USERS = new ConcurrentHashMap<>();

    static {
        USERS.put(User.Selector.WITH_FRIEND, new ConcurrentLinkedQueue<>(
                List.of(
                        simpleUser("oleg","1234"),
                        simpleUser("alex","1234"),
                        simpleUser("igor","1234")
                ))
        );
        USERS.put(User.Selector.INVITE_SENT, new ConcurrentLinkedQueue<>(
                List.of(
                       simpleUser("jim_send","1234"),
                        simpleUser("tim_send","1234"),
                        simpleUser("jin_send","1234")
                ))
        );
        USERS.put(User.Selector.INVITE_RECEIVED, new ConcurrentLinkedQueue<>(
                List.of(
                        simpleUser("ben_rcvd","1234"),
                        simpleUser("den_rcvd","1234"),
                        simpleUser("july_rcvd","1234")
                ))
        );
        USERS.put(User.Selector.COMMON, new ConcurrentLinkedQueue<>(
                List.of(
                        simpleUser("serg","1234")
                ))
        );
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        //получение тестового метода @Test
        Method testMethod = context.getRequiredTestMethod();

        //получение beforeEach методов
        List<Method> beforeEachMethods = Arrays.stream(
                context.getRequiredTestClass().getDeclaredMethods()
        ).filter(i -> i.isAnnotationPresent(BeforeEach.class)).toList();

        //Общий список методов, которые требуется обработать
        List<Method> methods = new ArrayList<>();
        methods.add(testMethod);
        methods.addAll(beforeEachMethods);

        //Общий список параметров, которые требуется обработать
        List<Parameter> parameters = methods.stream()
                .flatMap(m -> Arrays.stream(m.getParameters()))
                .filter((p -> p.isAnnotationPresent(User.class)))
                .toList();

        //Мапа где хранятся пользователи и их типы для сохранения в стор
        Map<User.Selector, UserJson> users = new HashMap<>();

        // Обработка полученных параметров
        for (Parameter parameter : parameters) {
            User.Selector selector = parameter.getAnnotation(User.class).selector();
            if(users.containsKey(selector)) continue;
            UserJson userForTest = null;
            Queue<UserJson> queue = USERS.get(selector);
            while (userForTest == null) {
                userForTest= queue.poll();
            }
            users.put(selector, userForTest);
        }
        Allure.getLifecycle().updateTestCase(testCase ->
                testCase.setStart(new Date().getTime()));
        context.getStore(NAMESPACE).put(context.getUniqueId(), users);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        Map<User.Selector,UserJson> usersAfterTest = context.getStore(NAMESPACE).get(context.getUniqueId(),Map.class);
        for(Map.Entry<User.Selector, UserJson> user:usersAfterTest.entrySet()){
            USERS.get(user.getKey()).add(user.getValue());
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        User.Selector selector = parameterContext.getParameter().getAnnotation(User.class).selector();
        return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), Map.class).get(selector);
    }
}
