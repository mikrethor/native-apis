package com.xavierbouclet.nativeapi

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.FORBIDDEN,
        reason = "How dare you add this movie ? Only Kung Fury movies are worthy.")
class KungFuryException  : RuntimeException()
