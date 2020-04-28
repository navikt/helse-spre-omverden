package no.nav.helse.dto

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class UtgåendeVedtakDTO(
    val vedtaksperiodeOpprettet: LocalDateTime,
    val søknadId: UUID,
    val sykmeldingId: UUID,
    val fødselsnummer: String,
    val fom: LocalDate,
    val tom: LocalDate,
    val utbetalinger: List<UtbetalingDTO>,
    val sendtTilUtbetaling: LocalDateTime,
    val gjenståendeSykedager: Int?
)

data class UtbetalingDTO(
    val fom: LocalDate,
    val tom: LocalDate,
    val mottaker: String,
    val konto: String,
    val grad: Int,
    val dagsats: Int,
    val beløp: Int,
    val enDelAvPerioden: Boolean
)
/*
STARTDATO EGENMELDING (Hvis første fraværsdag er egenmelding)
STARTDATO SYKMELDING (Hvis første fraværsdag er sykmelding)
 */