package com.crosstime.backend.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@PropertySource("classpath:/images/CDNImagesApi.properties")
data class CDNImagesProperties(
    @Value("\${cdn.images.api.url}")
    val url: String
)
