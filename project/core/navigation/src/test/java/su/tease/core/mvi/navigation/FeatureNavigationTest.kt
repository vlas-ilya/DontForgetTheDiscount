package su.tease.core.mvi.navigation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import su.tease.core.mvi.navigation.stub.StubFeature
import su.tease.core.mvi.navigation.stub.StubPage
import su.tease.project.core.utils.stack.makeStack

class FeatureNavigationTest {

    @Test
    fun `FeatureNavigation top returns last page`() {
        val feature = FeatureNavigation(
            name = StubFeature.Feature1,
            initPage = PageNavigation(StubPage.Page1),
            stack = makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
        )

        val top = feature.page

        assertEquals(PageNavigation(StubPage.Page3), top)
    }

    @Test
    fun `FeatureNavigation forward`() {
        val feature = FeatureNavigation(
            name = StubFeature.Feature1,
            initPage = PageNavigation(StubPage.Page1),
            stack = makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
        )

        val newFeature = feature.forward(PageNavigation(StubPage.Page4))

        assertEquals(PageNavigation(StubPage.Page3), feature.page)
        assertEquals(PageNavigation(StubPage.Page4), newFeature.page)
    }

    @Test
    fun `FeatureNavigation forward new page with singleTop`() {
        val feature = FeatureNavigation(
            name = StubFeature.Feature1,
            initPage = PageNavigation(StubPage.Page1),
            stack = makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
        )

        val newFeature = feature.forward(PageNavigation(StubPage.Page4), singleTop = true)

        assertEquals(PageNavigation(StubPage.Page3), feature.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
            feature.stack
        )

        assertEquals(PageNavigation(StubPage.Page4), newFeature.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
                PageNavigation(StubPage.Page4),
            ),
            newFeature.stack
        )
    }

    @Test
    fun `FeatureNavigation forward existed page with singleTop`() {
        val feature = FeatureNavigation(
            name = StubFeature.Feature1,
            initPage = PageNavigation(StubPage.Page1),
            stack = makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
        )

        val newFeature = feature.forward(PageNavigation(StubPage.Page2), singleTop = true)

        assertEquals(PageNavigation(StubPage.Page3), feature.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
            feature.stack
        )

        assertEquals(PageNavigation(StubPage.Page2), newFeature.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page3),
                PageNavigation(StubPage.Page2),
            ),
            newFeature.stack
        )
    }

    @Test
    fun `FeatureNavigation forward existed page with data with singleTop`() {
        val feature = FeatureNavigation(
            name = StubFeature.Feature1,
            initPage = PageNavigation(StubPage.Page1),
            stack = makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page5("test")),
                PageNavigation(StubPage.Page3),
            ),
        )

        val newFeature = feature.forward(PageNavigation(StubPage.Page5("test 1")), singleTop = true)

        assertEquals(PageNavigation(StubPage.Page3), feature.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page5("test")),
                PageNavigation(StubPage.Page3),
            ),
            feature.stack
        )

        assertEquals(PageNavigation(StubPage.Page5("test 1")), newFeature.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page3),
                PageNavigation(StubPage.Page5("test 1")),
            ),
            newFeature.stack
        )
    }

    @Test
    fun `FeatureNavigation forward last page with singleTop`() {
        val feature = FeatureNavigation(
            name = StubFeature.Feature1,
            initPage = PageNavigation(StubPage.Page1),
            stack = makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
        )

        val newFeature = feature.forward(PageNavigation(StubPage.Page3), singleTop = true)

        assertEquals(PageNavigation(StubPage.Page3), feature.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
            feature.stack
        )

        assertEquals(PageNavigation(StubPage.Page3), newFeature.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
            newFeature.stack
        )
    }

    @Test
    fun `FeatureNavigation forward last page without singleTop`() {
        val feature = FeatureNavigation(
            name = StubFeature.Feature1,
            initPage = PageNavigation(StubPage.Page1),
            stack = makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
        )

        val newFeature = feature.forward(PageNavigation(StubPage.Page3))

        assertEquals(PageNavigation(StubPage.Page3), feature.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
            feature.stack
        )

        assertEquals(PageNavigation(StubPage.Page3), newFeature.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
                PageNavigation(StubPage.Page3),
            ),
            newFeature.stack
        )
    }

    @Test
    fun `FeatureNavigation forward existed page without singleTop`() {
        val feature = FeatureNavigation(
            name = StubFeature.Feature1,
            initPage = PageNavigation(StubPage.Page1),
            stack = makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
        )

        val newFeature = feature.forward(PageNavigation(StubPage.Page2))

        assertEquals(PageNavigation(StubPage.Page3), feature.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
            feature.stack
        )

        assertEquals(PageNavigation(StubPage.Page2), newFeature.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
                PageNavigation(StubPage.Page2),
            ),
            newFeature.stack
        )
    }

    @Test
    fun `FeatureNavigation back`() {
        val feature = FeatureNavigation(
            name = StubFeature.Feature1,
            initPage = PageNavigation(StubPage.Page1),
            stack = makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
        )

        val newFeature = feature.back()

        assertEquals(PageNavigation(StubPage.Page3), feature.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
            feature.stack
        )

        assertEquals(PageNavigation(StubPage.Page2), newFeature?.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
            ),
            newFeature?.stack
        )
    }

    @Test
    fun `FeatureNavigation back last page`() {
        val feature = FeatureNavigation(
            name = StubFeature.Feature1,
            initPage = PageNavigation(StubPage.Page1),
            stack = makeStack(
                PageNavigation(StubPage.Page1),
            ),
        )

        val newFeature = feature.back()

        assertEquals(PageNavigation(StubPage.Page1), feature.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
            ),
            feature.stack
        )

        assertNull(newFeature)
    }

    @Test
    fun `FeatureNavigation backTo last page`() {
        val feature = FeatureNavigation(
            name = StubFeature.Feature1,
            initPage = PageNavigation(StubPage.Page1),
            stack = makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
        )

        val newFeature = feature.backTo(PageNavigation(StubPage.Page3))

        assertEquals(PageNavigation(StubPage.Page3), feature.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
            feature.stack
        )

        assertEquals(PageNavigation(StubPage.Page3), newFeature?.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
            newFeature?.stack
        )
    }

    @Test
    fun `FeatureNavigation backTo existed page`() {
        val feature = FeatureNavigation(
            name = StubFeature.Feature1,
            initPage = PageNavigation(StubPage.Page1),
            stack = makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
        )

        val newFeature = feature.backTo(PageNavigation(StubPage.Page2))

        assertEquals(PageNavigation(StubPage.Page3), feature.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
            feature.stack
        )

        assertEquals(PageNavigation(StubPage.Page2), newFeature?.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
            ),
            newFeature?.stack
        )
    }

    @Test
    fun `FeatureNavigation backTo first page`() {
        val feature = FeatureNavigation(
            name = StubFeature.Feature1,
            initPage = PageNavigation(StubPage.Page1),
            stack = makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
        )

        val newFeature = feature.backTo(PageNavigation(StubPage.Page1))

        assertEquals(PageNavigation(StubPage.Page3), feature.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
            feature.stack
        )

        assertEquals(PageNavigation(StubPage.Page1), newFeature?.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
            ),
            newFeature?.stack
        )
    }

    @Test
    fun `FeatureNavigation backTo not existed page`() {
        val feature = FeatureNavigation(
            name = StubFeature.Feature1,
            initPage = PageNavigation(StubPage.Page1),
            stack = makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
        )

        val newFeature = feature.backTo(PageNavigation(StubPage.Page4))

        assertEquals(PageNavigation(StubPage.Page3), feature.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page2),
                PageNavigation(StubPage.Page3),
            ),
            feature.stack
        )

        assertNull(newFeature)
    }



    @Test
    fun `FeatureNavigation backTo existed page with data`() {
        val feature = FeatureNavigation(
            name = StubFeature.Feature1,
            initPage = PageNavigation(StubPage.Page1),
            stack = makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page5("test")),
                PageNavigation(StubPage.Page3),
            ),
        )

        val newFeature = feature.backTo(PageNavigation(StubPage.Page5("")))

        assertEquals(PageNavigation(StubPage.Page3), feature.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page5("test")),
                PageNavigation(StubPage.Page3),
            ),
            feature.stack
        )

        assertEquals(PageNavigation(StubPage.Page5("test")), newFeature?.page)
        assertEquals(
            makeStack(
                PageNavigation(StubPage.Page1),
                PageNavigation(StubPage.Page5("test")),
            ),
            newFeature?.stack
        )
    }
}
