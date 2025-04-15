package su.tease.core.mvi.navigation

import org.junit.Assert.assertEquals
import org.junit.Test
import su.tease.core.mvi.navigation.stub.StubApp
import su.tease.core.mvi.navigation.stub.StubFeature
import su.tease.core.mvi.navigation.stub.StubPage
import su.tease.project.core.utils.stack.makeStack

class RootNavigationTest {

    private val stubApp1Feature1 = FeatureNavigation(
        name = StubFeature.Feature1,
        initPage = StubPage.Page5("App1 - Feature1 - Page 5"),
        stack = makeStack(
            PageNavigation(StubPage.Page5("App1 - Feature1 - Page 5")),
            PageNavigation(StubPage.Page6("App1 - Feature1 - Page 6")),
            PageNavigation(StubPage.Page7("App1 - Feature1 - Page 7")),
            PageNavigation(StubPage.Page1),
        ),
    )

    private val stubApp1Feature2 = FeatureNavigation(
        name = StubFeature.Feature2,
        initPage = StubPage.Page5("App1 - Feature2 - Page 5"),
        stack = makeStack(
            PageNavigation(StubPage.Page5("App1 - Feature2 - Page 5")),
            PageNavigation(StubPage.Page6("App1 - Feature2 - Page 6")),
            PageNavigation(StubPage.Page7("App1 - Feature2 - Page 7")),
        ),
    )

    private val stubApp1Feature3 = FeatureNavigation(
        name = StubFeature.Feature3,
        initPage = StubPage.Page5("App1 - Feature3 - Page 5"),
        stack = makeStack(
            PageNavigation(StubPage.Page5("App1 - Feature3 - Page 5")),
            PageNavigation(StubPage.Page6("App1 - Feature3 - Page 6")),
            PageNavigation(StubPage.Page7("App1 - Feature3 - Page 7")),
        ),
    )

    private val stubApp1 = AppNavigation(
        name = StubApp.App1,
        initFeature = FeatureNavigation(
            name = StubFeature.Feature1,
            initPage = StubPage.Page5("App1 - Feature1 - Page 5"),
        ),
        stack = makeStack(
            stubApp1Feature1,
            stubApp1Feature2,
            stubApp1Feature3,
        )
    )

    private val stubApp2Feature1 = FeatureNavigation(
        name = StubFeature.Feature1,
        initPage = StubPage.Page5("App2 - Feature1 - Page 5"),
        stack = makeStack(
            PageNavigation(StubPage.Page5("App2 - Feature1 - Page 5")),
            PageNavigation(StubPage.Page6("App2 - Feature1 - Page 6")),
            PageNavigation(StubPage.Page7("App2 - Feature1 - Page 7")),
            PageNavigation(StubPage.Page1),
        ),
    )

    private val stubApp2Feature2 = FeatureNavigation(
        name = StubFeature.Feature2,
        initPage = StubPage.Page5("App2 - Feature2 - Page 5"),
        stack = makeStack(
            PageNavigation(StubPage.Page5("App2 - Feature2 - Page 5")),
            PageNavigation(StubPage.Page6("App2 - Feature2 - Page 6")),
            PageNavigation(StubPage.Page7("App2 - Feature2 - Page 7")),
        ),
    )

    private val stubApp2Feature3 = FeatureNavigation(
        name = StubFeature.Feature3,
        initPage = StubPage.Page5("App2 - Feature3 - Page 5"),
        stack = makeStack(
            PageNavigation(StubPage.Page5("App2 - Feature3 - Page 5")),
            PageNavigation(StubPage.Page6("App2 - Feature3 - Page 6")),
            PageNavigation(StubPage.Page7("App2 - Feature3 - Page 7")),
        ),
    )

    private val stubApp2 = AppNavigation(
        name = StubApp.App2,
        initFeature = FeatureNavigation(
            name = StubFeature.Feature1,
            initPage = StubPage.Page5("App2 - Feature1 - Page 5"),
        ),
        stack = makeStack(
            stubApp2Feature1,
            stubApp2Feature2,
            stubApp2Feature3,
        )
    )

    private val stubRoot = RootNavigation(
        initApp = AppNavigation(
            name = StubApp.App1,
            initFeature = FeatureNavigation(
                name = StubFeature.Feature1,
                initPage = StubPage.Page5("App1 - 5"),
            ),
        ),
        stack = makeStack(stubApp1, stubApp2)
    )

    @Test
    fun `RootNavigation top returns last page`() {
        val top = stubRoot.page

        assertEquals(PageNavigation(StubPage.Page7("App2 - Feature3 - Page 7")), top)
    }

    @Test
    fun `RootNavigation forward to page`() {
        val newRoot = stubRoot.forward(StubPage.Page8("App2 - Feature3 - Page 8"))

        assertEquals(PageNavigation(StubPage.Page7("App2 - Feature3 - Page 7")), stubRoot.page)
        assertEquals(PageNavigation(StubPage.Page8("App2 - Feature3 - Page 8")), newRoot?.page)

        assertEquals(
            RootNavigation(
                initApp = AppNavigation(
                    name = StubApp.App1,
                    initFeature = FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = StubPage.Page5("App1 - 5"),
                    ),
                ),
                stack = makeStack(
                    stubApp1,
                    AppNavigation(
                        name = StubApp.App2,
                        initFeature = FeatureNavigation(
                            name = StubFeature.Feature1,
                            initPage = StubPage.Page5("App2 - Feature1 - Page 5"),
                        ),
                        stack = makeStack(
                            stubApp2Feature1,
                            stubApp2Feature2,
                            FeatureNavigation(
                                name = StubFeature.Feature3,
                                initPage = StubPage.Page5("App2 - Feature3 - Page 5"),
                                stack = makeStack(
                                    PageNavigation(StubPage.Page5("App2 - Feature3 - Page 5")),
                                    PageNavigation(StubPage.Page6("App2 - Feature3 - Page 6")),
                                    PageNavigation(StubPage.Page7("App2 - Feature3 - Page 7")),
                                    PageNavigation(StubPage.Page8("App2 - Feature3 - Page 8")),
                                ),
                            ),
                        )
                    )
                )
            ),
            newRoot
        )
    }

    @Test
    fun `RootNavigation forward to new page with singleTop`() {
        val newRoot = stubRoot.forward(
            StubPage.Page8("App2 - Feature3 - Page 8"),
            singleTop = true
        )

        assertEquals(PageNavigation(StubPage.Page7("App2 - Feature3 - Page 7")), stubRoot.page)
        assertEquals(PageNavigation(StubPage.Page8("App2 - Feature3 - Page 8")), newRoot?.page)

        assertEquals(
            RootNavigation(
                initApp = AppNavigation(
                    name = StubApp.App1,
                    initFeature = FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = StubPage.Page5("App1 - 5"),
                    ),
                ),
                stack = makeStack(
                    stubApp1,
                    AppNavigation(
                        name = StubApp.App2,
                        initFeature = FeatureNavigation(
                            name = StubFeature.Feature1,
                            initPage = StubPage.Page5("App2 - Feature1 - Page 5"),
                        ),
                        stack = makeStack(
                            stubApp2Feature1,
                            stubApp2Feature2,
                            FeatureNavigation(
                                name = StubFeature.Feature3,
                                initPage = StubPage.Page5("App2 - Feature3 - Page 5"),
                                stack = makeStack(
                                    PageNavigation(StubPage.Page5("App2 - Feature3 - Page 5")),
                                    PageNavigation(StubPage.Page6("App2 - Feature3 - Page 6")),
                                    PageNavigation(StubPage.Page7("App2 - Feature3 - Page 7")),
                                    PageNavigation(StubPage.Page8("App2 - Feature3 - Page 8")),
                                ),
                            ),
                        )
                    )
                )
            ),
            newRoot
        )
    }

    @Test
    fun `RootNavigation forward to last page with singleTop`() {
        val newRoot = stubRoot.forward(
            StubPage.Page7("App2 - Feature3 - Page 7"),
            singleTop = true
        )

        assertEquals(PageNavigation(StubPage.Page7("App2 - Feature3 - Page 7")), stubRoot.page)
        assertEquals(PageNavigation(StubPage.Page7("App2 - Feature3 - Page 7")), newRoot?.page)

        assertEquals(
            stubRoot,
            newRoot
        )
    }

    @Test
    fun `RootNavigation forward to existed page with singleTop`() {
        val newRoot = stubRoot.forward(
            StubPage.Page6("App2 - Feature3 - New Page 6"),
            singleTop = true
        )

        assertEquals(PageNavigation(StubPage.Page7("App2 - Feature3 - Page 7")), stubRoot.page)
        assertEquals(PageNavigation(StubPage.Page6("App2 - Feature3 - New Page 6")), newRoot?.page)

        assertEquals(
            RootNavigation(
                initApp = AppNavigation(
                    name = StubApp.App1,
                    initFeature = FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = StubPage.Page5("App1 - 5"),
                    ),
                ),
                stack = makeStack(
                    stubApp1,
                    AppNavigation(
                        name = StubApp.App2,
                        initFeature = FeatureNavigation(
                            name = StubFeature.Feature1,
                            initPage = StubPage.Page5("App2 - Feature1 - Page 5"),
                        ),
                        stack = makeStack(
                            stubApp2Feature1,
                            stubApp2Feature2,
                            FeatureNavigation(
                                name = StubFeature.Feature3,
                                initPage = StubPage.Page5("App2 - Feature3 - Page 5"),
                                stack = makeStack(
                                    PageNavigation(StubPage.Page5("App2 - Feature3 - Page 5")),
                                    PageNavigation(StubPage.Page7("App2 - Feature3 - Page 7")),
                                    PageNavigation(StubPage.Page6("App2 - Feature3 - New Page 6")),
                                ),
                            ),
                        )
                    )
                )
            ),
            newRoot
        )
    }

    @Test
    fun `RootNavigation forward to new feature`() {
        val newRoot = stubRoot.forward(
            FeatureNavigation(
                name = StubFeature.Feature4,
                initPage = StubPage.Page6("App2 - Feature4 - New Page 6")
            )
        )

        assertEquals(PageNavigation(StubPage.Page7("App2 - Feature3 - Page 7")), stubRoot.page)
        assertEquals(PageNavigation(StubPage.Page6("App2 - Feature4 - New Page 6")), newRoot?.page)

        assertEquals(
            RootNavigation(
                initApp = AppNavigation(
                    name = StubApp.App1,
                    initFeature = FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = StubPage.Page5("App1 - 5"),
                    ),
                ),
                stack = makeStack(
                    stubApp1,
                    AppNavigation(
                        name = StubApp.App2,
                        initFeature = FeatureNavigation(
                            name = StubFeature.Feature1,
                            initPage = StubPage.Page5("App2 - Feature1 - Page 5"),
                        ),
                        stack = makeStack(
                            stubApp2Feature1,
                            stubApp2Feature2,
                            stubApp2Feature3,
                            FeatureNavigation(
                                name = StubFeature.Feature4,
                                initPage = StubPage.Page6("App2 - Feature4 - New Page 6")
                            )
                        )
                    )
                )
            ),
            newRoot
        )
    }

    @Test
    fun `RootNavigation forward to new feature with singleTop`() {
        val newRoot = stubRoot.forward(
            FeatureNavigation(
                name = StubFeature.Feature4,
                initPage = StubPage.Page6("App2 - Feature4 - New Page 6")
            ),
            singleTop = true,
        )

        assertEquals(PageNavigation(StubPage.Page7("App2 - Feature3 - Page 7")), stubRoot.page)
        assertEquals(PageNavigation(StubPage.Page6("App2 - Feature4 - New Page 6")), newRoot?.page)

        assertEquals(
            RootNavigation(
                initApp = AppNavigation(
                    name = StubApp.App1,
                    initFeature = FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = StubPage.Page5("App1 - 5"),
                    ),
                ),
                stack = makeStack(
                    stubApp1,
                    AppNavigation(
                        name = StubApp.App2,
                        initFeature = FeatureNavigation(
                            name = StubFeature.Feature1,
                            initPage = StubPage.Page5("App2 - Feature1 - Page 5"),
                        ),
                        stack = makeStack(
                            stubApp2Feature1,
                            stubApp2Feature2,
                            stubApp2Feature3,
                            FeatureNavigation(
                                name = StubFeature.Feature4,
                                initPage = StubPage.Page6("App2 - Feature4 - New Page 6")
                            )
                        )
                    )
                )
            ),
            newRoot
        )
    }

    @Test
    fun `RootNavigation forward to last feature with singleTop`() {
        val newRoot = stubRoot.forward(
            FeatureNavigation(
                name = StubFeature.Feature3,
                initPage = StubPage.Page5("App2 - Feature3 - Page 5"),
            ),
            singleTop = true,
        )

        assertEquals(PageNavigation(StubPage.Page7("App2 - Feature3 - Page 7")), stubRoot.page)
        assertEquals(PageNavigation(StubPage.Page5("App2 - Feature3 - Page 5")), newRoot?.page)

        assertEquals(
            RootNavigation(
                initApp = AppNavigation(
                    name = StubApp.App1,
                    initFeature = FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = StubPage.Page5("App1 - 5"),
                    ),
                ),
                stack = makeStack(
                    stubApp1,
                    AppNavigation(
                        name = StubApp.App2,
                        initFeature = FeatureNavigation(
                            name = StubFeature.Feature1,
                            initPage = StubPage.Page5("App2 - Feature1 - Page 5"),
                        ),
                        stack = makeStack(
                            stubApp2Feature1,
                            stubApp2Feature2,
                            FeatureNavigation(
                                name = StubFeature.Feature3,
                                initPage = StubPage.Page5("App2 - Feature3 - Page 5"),
                            ),
                        )
                    )
                )
            ),
            newRoot
        )
    }

    @Test
    fun `RootNavigation forward to existed feature without singleTop`() {
        val newRoot = stubRoot.forward(
            FeatureNavigation(
                name = StubFeature.Feature3,
                initPage = StubPage.Page5("App2 - Feature3 - Page 5"),
            ),
        )

        assertEquals(PageNavigation(StubPage.Page7("App2 - Feature3 - Page 7")), stubRoot.page)
        assertEquals(PageNavigation(StubPage.Page5("App2 - Feature3 - Page 5")), newRoot?.page)

        assertEquals(
            RootNavigation(
                initApp = AppNavigation(
                    name = StubApp.App1,
                    initFeature = FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = StubPage.Page5("App1 - 5"),
                    ),
                ),
                stack = makeStack(
                    stubApp1,
                    AppNavigation(
                        name = StubApp.App2,
                        initFeature = FeatureNavigation(
                            name = StubFeature.Feature1,
                            initPage = StubPage.Page5("App2 - Feature1 - Page 5"),
                        ),
                        stack = makeStack(
                            stubApp2Feature1,
                            stubApp2Feature2,
                            stubApp2Feature3,
                            FeatureNavigation(
                                name = StubFeature.Feature3,
                                initPage = StubPage.Page5("App2 - Feature3 - Page 5"),
                            ),
                        )
                    )
                )
            ),
            newRoot
        )
    }

    @Test
    fun `RootNavigation forward to existed feature with singleTop`() {
        val newRoot = stubRoot.forward(
            FeatureNavigation(
                name = StubFeature.Feature2,
                initPage = StubPage.Page5("App2 - Feature2 - Page 5"),
            ),
            singleTop = true
        )

        assertEquals(PageNavigation(StubPage.Page7("App2 - Feature3 - Page 7")), stubRoot.page)
        assertEquals(PageNavigation(StubPage.Page5("App2 - Feature2 - Page 5")), newRoot?.page)

        assertEquals(
            RootNavigation(
                initApp = AppNavigation(
                    name = StubApp.App1,
                    initFeature = FeatureNavigation(
                        name = StubFeature.Feature1,
                        initPage = StubPage.Page5("App1 - 5"),
                    ),
                ),
                stack = makeStack(
                    stubApp1,
                    AppNavigation(
                        name = StubApp.App2,
                        initFeature = FeatureNavigation(
                            name = StubFeature.Feature1,
                            initPage = StubPage.Page5("App2 - Feature1 - Page 5"),
                        ),
                        stack = makeStack(
                            stubApp2Feature1,
                            stubApp2Feature3,
                            FeatureNavigation(
                                name = StubFeature.Feature2,
                                initPage = StubPage.Page5("App2 - Feature2 - Page 5"),
                            ),
                        )
                    )
                )
            ),
            newRoot
        )
    }

    @Test
    fun `RootNavigation forward to App`() {

    }

    @Test
    fun `RootNavigation forward to new App with singleTop`() {

    }

    @Test
    fun `RootNavigation forward to last App with singleTop`() {

    }

    @Test
    fun `RootNavigation forward to existed App with singleTop`() {

    }

    @Test
    fun `RootNavigation forward to existed App without singleTop`() {

    }

    @Test
    fun `RootNavigation switch to new app`() {

    }

    @Test
    fun `RootNavigation switch to last app`() {

    }

    @Test
    fun `RootNavigation switch to last app with cleanStack`() {

    }

    @Test
    fun `RootNavigation switch to existed app`() {

    }

    @Test
    fun `RootNavigation switch to existed app with cleanStack`() {

    }

    @Test
    fun `RootNavigation back`() {

    }

    @Test
    fun `RootNavigation back and close feature`() {

    }

    @Test
    fun `RootNavigation back and close app`() {

    }

    @Test
    fun `RootNavigation back and close root`() {

    }

    @Test
    fun `RootNavigation back to last page`() {

    }

    @Test
    fun `RootNavigation back to existed page`() {

    }

    @Test
    fun `RootNavigation back to first page`() {

    }

    @Test
    fun `RootNavigation back to not existed page`() {

    }

    @Test
    fun `RootNavigation back to last feature`() {

    }

    @Test
    fun `RootNavigation back to existed feature`() {

    }

    @Test
    fun `RootNavigation back to first feature`() {

    }

    @Test
    fun `RootNavigation back to not existed feature`() {

    }

    @Test
    fun `RootNavigation back to last app`() {

    }

    @Test
    fun `RootNavigation back to existed app`() {

    }

    @Test
    fun `RootNavigation back to first app`() {

    }

    @Test
    fun `RootNavigation back to not existed app`() {

    }

    @Test
    fun `RootNavigation finish last feature`() {

    }

    @Test
    fun `RootNavigation finish existed feature`() {

    }

    @Test
    fun `RootNavigation finish first feature`() {

    }

    @Test
    fun `RootNavigation finish not existed feature`() {

    }

    @Test
    fun `RootNavigation finish last app`() {

    }

    @Test
    fun `RootNavigation finish existed app`() {

    }

    @Test
    fun `RootNavigation finish first app`() {

    }

    @Test
    fun `RootNavigation finish not existed app`() {

    }
}