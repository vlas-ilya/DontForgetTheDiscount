package su.tease.core.mvi.navigation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import su.tease.core.mvi.navigation.stub.StubApp
import su.tease.core.mvi.navigation.stub.StubFeature
import su.tease.core.mvi.navigation.stub.StubPage
import su.tease.project.core.utils.stack.makeStack

class AppNavigationTest {

    private val stubApp = AppNavigation(
        name = StubApp.App1,
        initFeature = FeatureNavigation(
            name = StubFeature.Feature1,
            initPage = PageNavigation(StubPage.Page5("5")),
        ),
        stack = makeStack(
            FeatureNavigation(
                name = StubFeature.Feature1,
                initPage = PageNavigation(StubPage.Page5("5")),
                stack = makeStack(
                    PageNavigation(StubPage.Page5("5")),
                    PageNavigation(StubPage.Page6("6")),
                    PageNavigation(StubPage.Page7("7")),
                    PageNavigation(StubPage.Page1),
                ),
            ),
            FeatureNavigation(
                name = StubFeature.Feature2,
                initPage = PageNavigation(StubPage.Page5("5")),
                stack = makeStack(
                    PageNavigation(StubPage.Page5("5")),
                    PageNavigation(StubPage.Page6("6")),
                    PageNavigation(StubPage.Page7("7")),
                ),
            ),
            FeatureNavigation(
                name = StubFeature.Feature3,
                initPage = PageNavigation(StubPage.Page5("5")),
                stack = makeStack(
                    PageNavigation(StubPage.Page5("5")),
                    PageNavigation(StubPage.Page6("6")),
                    PageNavigation(StubPage.Page7("7")),
                ),
            ),
        )
    )

    @Test
    fun `AppNavigation top returns last page`() {
        val top = stubApp.page

        assertEquals(PageNavigation(StubPage.Page7("7")), top)
    }

    @Test
    fun `AppNavigation forward to page`() {
        val newApp = stubApp.forward(PageNavigation(StubPage.Page8("8")))

        assertEquals(PageNavigation(StubPage.Page7("7")), stubApp.page)
        assertEquals(PageNavigation(StubPage.Page8("8")), newApp?.page)

        assertEquals(
            AppNavigation(
                name = StubApp.App1,
                initFeature = FeatureNavigation(
                    name = StubFeature.Feature1,
                    initPage = PageNavigation(StubPage.Page5("5")),
                ),
                stack = makeStack(
                    FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page1),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature2,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature3,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page8("8")),
                        ),
                    ),
                )
            ),
            newApp
        )
    }

    @Test
    fun `AppNavigation forward to new page with singleTop`() {
        val newApp = stubApp.forward(PageNavigation(StubPage.Page8("8")), singleTop = true)

        assertEquals(PageNavigation(StubPage.Page7("7")), stubApp.page)
        assertEquals(PageNavigation(StubPage.Page8("8")), newApp?.page)

        assertEquals(
            AppNavigation(
                name = StubApp.App1,
                initFeature = FeatureNavigation(
                    name = StubFeature.Feature1,
                    initPage = PageNavigation(StubPage.Page5("5")),
                ),
                stack = makeStack(
                    FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page1),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature2,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature3,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page8("8")),
                        ),
                    ),
                )
            ),
            newApp
        )
    }

    @Test
    fun `AppNavigation forward to existed in other feature page with singleTop`() {
        val newApp = stubApp.forward(PageNavigation(StubPage.Page1), singleTop = true)

        assertEquals(PageNavigation(StubPage.Page7("7")), stubApp.page)
        assertEquals(PageNavigation(StubPage.Page1), newApp?.page)

        assertEquals(
            AppNavigation(
                name = StubApp.App1,
                initFeature = FeatureNavigation(
                    name = StubFeature.Feature1,
                    initPage = PageNavigation(StubPage.Page5("5")),
                ),
                stack = makeStack(
                    FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page1),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature2,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature3,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page1),
                        ),
                    ),
                )
            ),
            newApp
        )
    }

    @Test
    fun `AppNavigation forward to existed in last feature page with singleTop`() {
        val newApp = stubApp.forward(PageNavigation(StubPage.Page6("6")), singleTop = true)

        assertEquals(PageNavigation(StubPage.Page7("7")), stubApp.page)
        assertEquals(PageNavigation(StubPage.Page6("6")), newApp?.page)

        assertEquals(
            AppNavigation(
                name = StubApp.App1,
                initFeature = FeatureNavigation(
                    name = StubFeature.Feature1,
                    initPage = PageNavigation(StubPage.Page5("5")),
                ),
                stack = makeStack(
                    FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page1),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature2,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature3,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page6("6")),
                        ),
                    ),
                )
            ),
            newApp
        )
    }

    @Test
    fun `AppNavigation forward to existed in last feature last page with singleTop`() {
        val newApp = stubApp.forward(PageNavigation(StubPage.Page7("7")), singleTop = true)

        assertEquals(PageNavigation(StubPage.Page7("7")), stubApp.page)
        assertEquals(PageNavigation(StubPage.Page7("7")), newApp?.page)

        assertEquals(
            AppNavigation(
                name = StubApp.App1,
                initFeature = FeatureNavigation(
                    name = StubFeature.Feature1,
                    initPage = PageNavigation(StubPage.Page5("5")),
                ),
                stack = makeStack(
                    FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page1),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature2,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature3,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                )
            ),
            newApp
        )
    }

    @Test
    fun `AppNavigation forward to existed in last feature last page without singleTop`() {
        val newApp = stubApp.forward(PageNavigation(StubPage.Page7("7")))

        assertEquals(PageNavigation(StubPage.Page7("7")), stubApp.page)
        assertEquals(PageNavigation(StubPage.Page7("7")), newApp?.page)

        assertEquals(
            AppNavigation(
                name = StubApp.App1,
                initFeature = FeatureNavigation(
                    name = StubFeature.Feature1,
                    initPage = PageNavigation(StubPage.Page5("5")),
                ),
                stack = makeStack(
                    FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page1),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature2,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature3,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                )
            ),
            newApp
        )
    }

    @Test
    fun `AppNavigation forward to feature`() {
        val newApp = stubApp.forward(
            FeatureNavigation(
                name = StubFeature.Feature4,
                initPage = PageNavigation(StubPage.Page5("5")),
                stack = makeStack(
                    PageNavigation(StubPage.Page5("5")),
                    PageNavigation(StubPage.Page6("6")),
                    PageNavigation(StubPage.Page7("7")),
                    PageNavigation(StubPage.Page7("7.1")),
                ),
            ),
        )

        assertEquals(PageNavigation(StubPage.Page7("7")), stubApp.page)
        assertEquals(PageNavigation(StubPage.Page7("7.1")), newApp.page)

        assertEquals(
            AppNavigation(
                name = StubApp.App1,
                initFeature = FeatureNavigation(
                    name = StubFeature.Feature1,
                    initPage = PageNavigation(StubPage.Page5("5")),
                ),
                stack = makeStack(
                    FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page1),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature2,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature3,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature4,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page7("7.1")),
                        ),
                    ),
                )
            ),
            newApp
        )
    }

    @Test
    fun `AppNavigation forward to existed feature with singleTop`() {
        val newApp = stubApp.forward(
            FeatureNavigation(
                name = StubFeature.Feature1,
                initPage = PageNavigation(StubPage.Page5("5")),
            ),
            singleTop = true
        )

        assertEquals(PageNavigation(StubPage.Page7("7")), stubApp.page)
        assertEquals(PageNavigation(StubPage.Page5("5")), newApp.page)

        assertEquals(
            AppNavigation(
                name = StubApp.App1,
                initFeature = FeatureNavigation(
                    name = StubFeature.Feature1,
                    initPage = PageNavigation(StubPage.Page5("5")),
                ),
                stack = makeStack(
                    FeatureNavigation(
                        name = StubFeature.Feature2,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature3,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = PageNavigation(StubPage.Page5("5")),
                    ),
                )
            ),
            newApp
        )
    }

    @Test
    fun `AppNavigation switch to new feature`() {
        val newApp = stubApp.switchTo(
            FeatureNavigation(
                name = StubFeature.Feature4,
                initPage = PageNavigation(StubPage.Page5("5")),
                stack = makeStack(
                    PageNavigation(StubPage.Page5("5")),
                    PageNavigation(StubPage.Page6("6")),
                    PageNavigation(StubPage.Page7("7")),
                    PageNavigation(StubPage.Page7("7.1")),
                ),
            ),
        )

        assertEquals(PageNavigation(StubPage.Page7("7")), stubApp.page)
        assertEquals(PageNavigation(StubPage.Page7("7.1")), newApp?.page)

        assertEquals(
            AppNavigation(
                name = StubApp.App1,
                initFeature = FeatureNavigation(
                    name = StubFeature.Feature1,
                    initPage = PageNavigation(StubPage.Page5("5")),
                ),
                stack = makeStack(
                    FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page1),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature2,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature3,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature4,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page7("7.1")),
                        ),
                    ),
                )
            ),
            newApp
        )
    }

    @Test
    fun `AppNavigation switch to existed feature`() {
        val newApp = stubApp.switchTo(
            FeatureNavigation(
                name = StubFeature.Feature2,
                initPage = PageNavigation(StubPage.Page5("5")),
            ),
        )

        assertEquals(PageNavigation(StubPage.Page7("7")), stubApp.page)
        assertEquals(PageNavigation(StubPage.Page7("7")), newApp?.page)

        assertEquals(
            AppNavigation(
                name = StubApp.App1,
                initFeature = FeatureNavigation(
                    name = StubFeature.Feature1,
                    initPage = PageNavigation(StubPage.Page5("5")),
                ),
                stack = makeStack(
                    FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page1),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature3,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature2,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                )
            ),
            newApp
        )
    }

    @Test
    fun `AppNavigation switch to existed feature with cleanStack`() {
        val newApp = stubApp.switchTo(
            FeatureNavigation(
                name = StubFeature.Feature2,
                initPage = PageNavigation(StubPage.Page5("5")),
            ),
            cleanStack = true,
        )

        assertEquals(PageNavigation(StubPage.Page7("7")), stubApp.page)
        assertEquals(PageNavigation(StubPage.Page5("5")), newApp?.page)

        assertEquals(
            AppNavigation(
                name = StubApp.App1,
                initFeature = FeatureNavigation(
                    name = StubFeature.Feature1,
                    initPage = PageNavigation(StubPage.Page5("5")),
                ),
                stack = makeStack(
                    FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page1),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature3,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature2,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                        ),
                    ),
                )
            ),
            newApp
        )
    }

    @Test
    fun `AppNavigation back`() {
        val newApp = stubApp.back()

        assertEquals(PageNavigation(StubPage.Page7("7")), stubApp.page)
        assertEquals(PageNavigation(StubPage.Page6("6")), newApp?.page)

        assertEquals(
            AppNavigation(
                name = StubApp.App1,
                initFeature = FeatureNavigation(
                    name = StubFeature.Feature1,
                    initPage = PageNavigation(StubPage.Page5("5")),
                ),
                stack = makeStack(
                    FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page1),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature2,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature3,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                        ),
                    ),
                )
            ),
            newApp
        )
    }

    @Test
    fun `AppNavigation back and close the feature`() {
        val newApp = stubApp.back()?.back()?.back()

        assertEquals(PageNavigation(StubPage.Page7("7")), stubApp.page)
        assertEquals(PageNavigation(StubPage.Page7("7")), newApp?.page)

        assertEquals(
            AppNavigation(
                name = StubApp.App1,
                initFeature = FeatureNavigation(
                    name = StubFeature.Feature1,
                    initPage = PageNavigation(StubPage.Page5("5")),
                ),
                stack = makeStack(
                    FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page1),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature2,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                )
            ),
            newApp
        )
    }

    @Test
    fun `AppNavigation back and close the app`() {
        val newApp = stubApp
            .back()?.back()?.back()
            ?.back()?.back()?.back()
            ?.back()?.back()?.back()
            ?.back()

        assertNull(newApp)
    }

    @Test
    fun `AppNavigation back to first page`() {
        val newApp = stubApp
            .back()?.back()?.back()
            ?.back()?.back()?.back()
            ?.back()?.back()?.back()

        assertEquals(PageNavigation(StubPage.Page5("5")), newApp?.page)

        assertEquals(
            AppNavigation(
                name = StubApp.App1,
                initFeature = FeatureNavigation(
                    name = StubFeature.Feature1,
                    initPage = PageNavigation(StubPage.Page5("5")),
                ),
                stack = makeStack(
                    FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                        ),
                    ),
                )
            ),
            newApp
        )
    }

    @Test
    fun `AppNavigation back to last page`() {
        val newApp = stubApp.backToPage(PageNavigation(StubPage.Page7("?")))

        assertEquals(PageNavigation(StubPage.Page7("7")), stubApp.page)
        assertEquals(PageNavigation(StubPage.Page7("7")), newApp?.page)

        assertEquals(
            AppNavigation(
                name = StubApp.App1,
                initFeature = FeatureNavigation(
                    name = StubFeature.Feature1,
                    initPage = PageNavigation(StubPage.Page5("5")),
                ),
                stack = makeStack(
                    FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page1),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature2,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature3,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                )
            ),
            newApp
        )
    }

    @Test
    fun `AppNavigation back to some existed page`() {
        val newApp = stubApp.backToPage(PageNavigation(StubPage.Page6("?")))

        assertEquals(PageNavigation(StubPage.Page7("7")), stubApp.page)
        assertEquals(PageNavigation(StubPage.Page6("6")), newApp?.page)

        assertEquals(
            AppNavigation(
                name = StubApp.App1,
                initFeature = FeatureNavigation(
                    name = StubFeature.Feature1,
                    initPage = PageNavigation(StubPage.Page5("5")),
                ),
                stack = makeStack(
                    FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page1),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature2,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature3,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                        ),
                    ),
                )
            ),
            newApp
        )
    }

    @Test
    fun `AppNavigation back to not existed page`() {
        val newApp = stubApp.backToPage(PageNavigation(StubPage.Page1))

        assertEquals(PageNavigation(StubPage.Page7("7")), stubApp.page)
        assertEquals(PageNavigation(StubPage.Page7("7")), newApp?.page)

        assertEquals(
            AppNavigation(
                name = StubApp.App1,
                initFeature = FeatureNavigation(
                    name = StubFeature.Feature1,
                    initPage = PageNavigation(StubPage.Page5("5")),
                ),
                stack = makeStack(
                    FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page1),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature2,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                )
            ),
            newApp
        )
    }

    @Test
    fun `AppNavigation back to last feature`() {
        val newApp = stubApp.backToFeature(
            FeatureNavigation(
                StubFeature.Feature3,
                initPage = PageNavigation(StubPage.Page5("?"))
            )
        )

        assertEquals(PageNavigation(StubPage.Page7("7")), stubApp.page)
        assertEquals(PageNavigation(StubPage.Page7("7")), newApp?.page)

        assertEquals(
            AppNavigation(
                name = StubApp.App1,
                initFeature = FeatureNavigation(
                    name = StubFeature.Feature1,
                    initPage = PageNavigation(StubPage.Page5("5")),
                ),
                stack = makeStack(
                    FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page1),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature2,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature3,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                )
            ),
            newApp
        )
    }

    @Test
    fun `AppNavigation back to some existed feature`() {
        val newApp = stubApp.backToFeature(
            FeatureNavigation(
                StubFeature.Feature2,
                initPage = PageNavigation(StubPage.Page5("?"))
            )
        )

        assertEquals(PageNavigation(StubPage.Page7("7")), stubApp.page)
        assertEquals(PageNavigation(StubPage.Page7("7")), newApp?.page)

        assertEquals(
            AppNavigation(
                name = StubApp.App1,
                initFeature = FeatureNavigation(
                    name = StubFeature.Feature1,
                    initPage = PageNavigation(StubPage.Page5("5")),
                ),
                stack = makeStack(
                    FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page1),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature2,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                )
            ),
            newApp
        )
    }

    @Test
    fun `AppNavigation back to not existed feature`() {
        val newApp = stubApp.backToFeature(
            FeatureNavigation(
                StubFeature.Feature4,
                initPage = PageNavigation(StubPage.Page5("?"))
            )
        )
        assertNull(newApp)
    }



    @Test
    fun `AppNavigation finish last feature`() {
        val newApp = stubApp.finishFeature(
            FeatureNavigation(
                StubFeature.Feature3,
                initPage = PageNavigation(StubPage.Page5("?"))
            )
        )

        assertEquals(PageNavigation(StubPage.Page7("7")), stubApp.page)
        assertEquals(PageNavigation(StubPage.Page7("7")), newApp?.page)

        assertEquals(
            AppNavigation(
                name = StubApp.App1,
                initFeature = FeatureNavigation(
                    name = StubFeature.Feature1,
                    initPage = PageNavigation(StubPage.Page5("5")),
                ),
                stack = makeStack(
                    FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page1),
                        ),
                    ),
                    FeatureNavigation(
                        name = StubFeature.Feature2,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                        ),
                    ),
                )
            ),
            newApp
        )
    }

    @Test
    fun `AppNavigation finish some existed feature`() {
        val newApp = stubApp.finishFeature(
            FeatureNavigation(
                StubFeature.Feature2,
                initPage = PageNavigation(StubPage.Page5("?"))
            )
        )

        assertEquals(PageNavigation(StubPage.Page7("7")), stubApp.page)
        assertEquals(PageNavigation(StubPage.Page1), newApp?.page)

        assertEquals(
            AppNavigation(
                name = StubApp.App1,
                initFeature = FeatureNavigation(
                    name = StubFeature.Feature1,
                    initPage = PageNavigation(StubPage.Page5("5")),
                ),
                stack = makeStack(
                    FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = PageNavigation(StubPage.Page5("5")),
                        stack = makeStack(
                            PageNavigation(StubPage.Page5("5")),
                            PageNavigation(StubPage.Page6("6")),
                            PageNavigation(StubPage.Page7("7")),
                            PageNavigation(StubPage.Page1),
                        ),
                    ),
                )
            ),
            newApp
        )
    }

    @Test
    fun `AppNavigation finish first feature`() {
        val newApp = stubApp.finishFeature(
            FeatureNavigation(
                StubFeature.Feature1,
                initPage = PageNavigation(StubPage.Page5("?"))
            )
        )
        assertNull(newApp)
    }

    @Test
    fun `AppNavigation finish not existed feature`() {
        val newApp = stubApp.finishFeature(
            FeatureNavigation(
                StubFeature.Feature4,
                initPage = PageNavigation(StubPage.Page5("?"))
            )
        )
        assertNull(newApp)
    }
}
