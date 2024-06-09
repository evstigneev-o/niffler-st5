package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.jupiter.annotation.Spends;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class AbstractSpendExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(AbstractSpendExtension.class);


//    @Override
//    public void beforeEach(ExtensionContext context) {
//        CategoryJson category = context.getStore(AbstractCategoryExtension.NAMESPACE).get(context.getUniqueId(), CategoryJson.class);
//        AnnotationSupport.findAnnotation(
//                        context.getRequiredTestMethod(),
//                        GenerateSpend.class)
//                .ifPresent(
//                        spend -> {
//                            SpendJson spendJson = new SpendJson(
//                                    null,
//                                    new Date(),
//                                    category.category(),
//                                    spend.currency(),
//                                    spend.amount(),
//                                    spend.description(),
//                                    category.username()
//                            );
//                            context.getStore(NAMESPACE).put(context.getUniqueId(), createSpend(spendJson));
//                        }
//                );
//    }
//
//    @Override
//    public void afterEach(ExtensionContext context) {
//        SpendJson spendJson = context.getStore(NAMESPACE).get(context.getUniqueId(), SpendJson.class);
//        removeSpend(spendJson);
//    }
//
//    @Override
//    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
//        return parameterContext
//                .getParameter()
//                .getType()
//                .isAssignableFrom(SpendJson.class);
//    }
//
//    @Override
//    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
//        return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId());
//    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        CategoryJson category = extensionContext.getStore(AbstractCategoryExtension.NAMESPACE).get(
                extensionContext.getUniqueId(),
                CategoryJson.class
        );

        List<GenerateSpend> potentialSpend = new ArrayList<>();

        AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                Spends.class
        ).ifPresentOrElse(
                spends -> potentialSpend.addAll(Arrays.stream(spends.value()).toList()),
                () -> AnnotationSupport.findAnnotation(
                        extensionContext.getRequiredTestMethod(),
                        GenerateSpend.class
                ).ifPresent(potentialSpend::add)
        );

        if (!potentialSpend.isEmpty()) {
            List<SpendJson> created = new ArrayList<>();
            for (GenerateSpend spend : potentialSpend) {
                SpendJson spendJson = new SpendJson(
                        null,
                        new Date(),
                        category.category(),
                        spend.currency(),
                        spend.amount(),
                        spend.description(),
                        category.username()
                );
                created.add(createSpend(spendJson));
            }
            extensionContext.getStore(NAMESPACE)
                    .put(extensionContext.getUniqueId(), created);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void afterEach(ExtensionContext context) throws Exception {
        context.getStore(NAMESPACE)
                .get(context.getUniqueId(), List.class)
                .forEach(spend -> removeSpend((SpendJson) spend));
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> type = parameterContext.getParameter()
                .getType();
        return type.isAssignableFrom(SpendJson.class) || type.isAssignableFrom(SpendJson[].class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> type = parameterContext
                .getParameter()
                .getType();

        List<SpendJson> createdSpends = extensionContext.getStore(NAMESPACE)
                .get(extensionContext.getUniqueId(), List.class);

        return type.isAssignableFrom(SpendJson.class)
                ? createdSpends.getFirst()
                : createdSpends.toArray(SpendJson[]::new);
    }

    protected abstract SpendJson createSpend(SpendJson spend);

    protected abstract SpendJson editSpend(SpendJson spend);

    protected abstract void removeSpend(SpendJson spend);
}
