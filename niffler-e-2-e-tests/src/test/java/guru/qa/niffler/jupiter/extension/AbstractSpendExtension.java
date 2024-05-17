package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.Date;

public abstract class AbstractSpendExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(AbstractSpendExtension.class);


    @Override
    public void beforeEach(ExtensionContext context) {
        CategoryJson category = context.getStore(AbstractCategoryExtension.NAMESPACE).get(context.getUniqueId(), CategoryJson.class);
        AnnotationSupport.findAnnotation(
                        context.getRequiredTestMethod(),
                        GenerateSpend.class)
                .ifPresent(
                        spend -> {
                            SpendJson spendJson = new SpendJson(
                                    null,
                                    new Date(),
                                    category.category(),
                                    spend.currency(),
                                    spend.amount(),
                                    spend.description(),
                                    category.username()
                            );
                            context.getStore(NAMESPACE).put(context.getUniqueId(), createSpend(spendJson));
                        }
                );
    }

    @Override
    public void afterEach(ExtensionContext context) {
        SpendJson spendJson = context.getStore(NAMESPACE).get(context.getUniqueId(), SpendJson.class);
        removeSpend(spendJson);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext
                .getParameter()
                .getType()
                .isAssignableFrom(SpendJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId());
    }

    protected abstract SpendJson createSpend(SpendJson spend);

    protected abstract SpendJson editSpend(SpendJson spend);

    protected abstract void removeSpend(SpendJson spend);
}
