package io.writerme.app.net

import it.skrape.core.htmlDocument
import it.skrape.fetcher.*
import it.skrape.selects.html5.meta

data class MetaTags(
    val ogImage: String?, //still need nullability as its possible a site has none of these tags
    val ogTitle: String?,
    val ogUrl: String?,
    val ogDescription: String?,
    val twitterImage: String?,
    val twitterTitle: String?,
    val twitterDescription: String?,
    val twitterUrl: String?
)

class MetaTagScraper {

    protected data class TempMetaTags(
        var ogImage: String? = null,
        var ogTitle: String? = null,
        var ogUrl: String? = null,
        var ogDescription: String? = null,
        var twitterImage: String? = null,
        var twitterTitle: String? = null,
        var twitterDescription: String? = null,
        var twitterUrl: String? = null
    )

    suspend fun scrape(givenUrl: String): MetaTags {

        val tags = skrape(AsyncFetcher) {
            request {
                url = givenUrl
                timeout = 120000
            }
            extractIt<TempMetaTags> {
                htmlDocument {
                    relaxed = true
                    it.ogTitle = meta {
                        withAttribute = "property" to "og:title"
                        findFirst {
                            attribute("content")
                        }
                    }
                    it.ogImage = meta {
                        withAttribute = "property" to "og:image"
                        findFirst {
                            attribute("content")
                        }
                    }
                    it.ogUrl = meta {
                        withAttribute = "property" to "og:url"
                        findFirst {
                            attribute("content")
                        }
                    }
                    it.ogDescription = meta {
                        withAttribute = "property" to "og:description"
                        findFirst {
                            attribute("content")
                        }
                    }

                    it.twitterImage = meta {
                        withAttribute = "name" to "twitter:image"
                        findFirst {
                            attribute("content")
                        }
                    }
                    it.twitterTitle = meta {
                        withAttribute = "name" to "twitter:title"
                        findFirst {
                            attribute("content")
                        }
                    }
                    it.twitterDescription = meta {
                        withAttribute = "name" to "twitter:description"
                        findFirst {
                            attribute("content")
                        }
                    }
                    it.twitterUrl = meta {
                        withAttribute = "name" to "twitter:url"
                        findFirst {
                            attribute("content")
                        }

                    }
                }
            }
        }

        return MetaTags(
            tags.ogImage, tags.ogTitle, tags.ogUrl, tags.ogDescription,
            tags.twitterImage, tags.twitterTitle, tags.twitterDescription,
            tags.twitterUrl
        )
    }
}