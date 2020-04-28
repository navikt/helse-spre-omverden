package no.nav.helse

import no.nav.helse.dto.UtbetalingDTO
import no.nav.helse.rapids_rivers.JsonMessage
import no.nav.helse.rapids_rivers.MessageProblems
import no.nav.helse.rapids_rivers.RapidsConnection
import no.nav.helse.rapids_rivers.River
import org.apache.kafka.clients.producer.KafkaProducer
import java.time.LocalDate

class UtbetalingEventMessage(
    rapidsConnection: RapidsConnection,
    producer: KafkaProducer<String, String>
) : River.PacketListener {
    data class Utbetalingslinje(
        val fom: LocalDate,
        val tom: LocalDate,
        val dagsats: Int,
        val beløp: Int,
        val grad: Int,
        val enDelAvPerioden: Boolean,
        val mottaker: String,
        val konto: String
    )

    init {
        River(rapidsConnection).apply {
            validate {
                it.demandValue("@event_name", "utbetalt")
                it.requireKey(
                    "fødselsnummer", "førsteFraværsdag", "vedtaksperiodeId", "hendelser", "utbetalingslinjer",
                    "forbrukteSykedager", "gjenståendeSykedager", "opprettet"
                )
            }
        }.register(this)
    }

    override fun onPacket(packet: JsonMessage, context: RapidsConnection.MessageContext) {
        val fødselsnummer = packet["fødselsnummer"]
        val førsteFraværsdag = packet["førsteFraværsdag"]
        val vedtaksperiodeId = packet["vedtaksperiodeId"]
        val hendelser = packet["hendelser"]
        val utbetalingslinjer = packet["utbetalingslinjer"].map {
            UtbetalingDTO(
                fom = it["fom"].asText().let(LocalDate::parse),
                tom = it["tom"].asText().let(LocalDate::parse),
                dagsats = it["dagsats"].asInt(),
                beløp = it["beløp"].asInt(),
                grad = it["grad"].asInt(),
                enDelAvPerioden = it["enDelAvPeriode"].asBoolean(),
                mottaker = it["mottaker"].asText(),
                konto = it["konto"].asText()
            )


        }
        val forbrukteSykedager = packet["forbrukteSykedager"]
        val gjenståendeSykedager = packet["gjenståendeSykedager"]
        val opprettet = packet["opprettet"]
    }

    override fun onError(problems: MessageProblems, context: RapidsConnection.MessageContext) {

    }
}