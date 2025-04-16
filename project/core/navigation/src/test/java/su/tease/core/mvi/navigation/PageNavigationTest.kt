package su.tease.core.mvi.navigation

import org.junit.Assert.assertEquals
import org.junit.Test
import su.tease.core.mvi.navigation.stub.StubPage

class PageNavigationTest {
    @Test
    fun `PageNavigation top returns this`() {
        val pageNavigation = PageNavigation(StubPage.Page1)

        val top = pageNavigation.page

        assertEquals(pageNavigation, top)
    }
}
