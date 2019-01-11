package sk.pixwell.android.core

import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit
import kotlin.math.abs

/**
 * Created by Tomáš Baránek on 11.1.2019.
 * Pixwell, s.r.o.
 * tomas.baranek@pixwell.sk
 */
fun LocalDateTime.getDelta(other: LocalDateTime) =
        abs(ChronoUnit.SECONDS.between(this, other))