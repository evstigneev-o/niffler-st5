package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

public abstract class AbstractSpendExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(AbstractSpendExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        AnnotationSupport.findAnnotation(
                        context.getRequiredTestMethod(),
                        GenerateSpend.class)
                .ifPresent(
                        spend -> context.getStore(NAMESPACE).put(context.getUniqueId(), createSpend(spend))
                );
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
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

    protected abstract SpendJson createSpend(GenerateSpend spend);

    protected abstract void removeSpend(SpendJson spend);
}
